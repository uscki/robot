package bots;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import library.UsckiSite;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * 
 * @author Benno Kruit
 * @category Bots
 *
 * Vraag iemands nummer of adres uit het smoelenboek op
 */
public class SmoBot extends AnswerBot {
	public String ask(String in, String who) {
		Pattern p = null;
		Matcher m = null;
		// Zoek patronen
		p = Pattern.compile("wat is (\\w+)\\'*s nummer\\?*", Pattern.CASE_INSENSITIVE);
		m = p.matcher(in);
		if (m.matches()) {
			return smoboZoek(m.group(1), "mobilenumber", "mobiele nummer");
		}
		p = Pattern.compile("waar woont (\\w+)\\?*", Pattern.CASE_INSENSITIVE);
		m = p.matcher(in);
		if (m.matches()) {
			return smoboZoek(m.group(1), "address1", "adres");
		}
		p = Pattern.compile("wat is het nummer van (\\w+)\\?*", Pattern.CASE_INSENSITIVE);
		m = p.matcher(in);
		if (m.matches()) {
			return smoboZoek(m.group(1), "mobilenumber", "mobiele nummer");
		}
		p = Pattern.compile("wat is het adres van (\\w+)\\?*", Pattern.CASE_INSENSITIVE);
		m = p.matcher(in);
		if (m.matches()) {
			return smoboZoek(m.group(1), "address1", "adres");
		}
		return null;
	}
	
	private String smoboZoek(String name, String tag, String property) {
		String results = "";
		
		Document doc;
		try {
			doc = UsckiSite.request(
					String.format("pagina=Smobo&query=%s", 
							URLEncoder.encode(name, "UTF-8")));
		} catch (UnsupportedEncodingException e) { return null; }
		
		// Neem van het resultaat alle person-tags
		NodeList nList = doc.getElementsByTagName("person");		 
		for (int i = 0; i < nList.getLength(); i++) {
			Node nNode = nList.item(i);		 
			if (nNode.getNodeType() == Node.ELEMENT_NODE) {
				Element eElement = (Element) nNode;
				// neem van deze tag wat subtags
				String voornaam = eElement.getElementsByTagName("firstname").item(0).getTextContent();
				String achternaam = eElement.getElementsByTagName("lastname").item(0).getTextContent();
				String prop = eElement.getElementsByTagName(tag).item(0).getTextContent();
				if (prop != "") {
					results += voornaam + " " + achternaam + "'s " + property + " is " + prop + "\n";
				}
			}
		}
		return results;
	}
}
