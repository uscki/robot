import requests
from lxml import etree
settings = {l.split('=')[0].strip(): l.split('=')[1].strip() for l in list(open('../settings.txt', 'r')) if l[0] != '#' }

class UsckiAPI:
	def __init__(self, page, post = {}, get = {}):
		rooturl = 'https://www.uscki.nl/?api'
		post['login'] = settings['uscki-login']
		post['password'] = settings['uscki-password']
		get['pagina'] = page

		# doe een http request
		content = requests.post(rooturl, params=get, data=post, allow_redirects=True, verify=False).content
		self.tree = etree.fromstring(content)

		# haal de namespaces eruit
		namespace_uris = (v for elem in self.tree.getiterator() for v in elem.nsmap.values())
		namespace_prefix = 'http://www.uscki.nl/wicki/'
		self.ns = { v.split('/')[-1]: v for v in namespace_uris if namespace_prefix in v }

	def __getitem__(self,index):
		return self.tree.xpath(index, namespaces = self.ns)
