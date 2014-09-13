import sleekxmpp
import cv2
import time
import logging
import subprocess
import re
import EventBus
import threading
import requests
import StringIO
from PIL import Image

# logging.basicConfig(level=logging.DEBUG)


class ChatClient(sleekxmpp.ClientXMPP):
    def __init__(self, jid, password, server):
        super(ChatClient, self).__init__(jid, password)
        self.add_event_handler('session_start', self.start)
        self.add_event_handler('message', self.message)
        self.auto_reconnect = True
        self.connect(server)
        self.process(block=False)

    def start(self, event):
        self.send_presence()
        print('Session started: ', self.server)
        self.get_roster()

    def message(self, msg):
        if msg['type'] in ('chat','normal'):
            ev = ChatEvent()
            ev.message = msg['body']
            ev.sender = msg['from']
            ev.reply = lambda body: self.send_message(mto=ev.sender, mbody=body)
            EventBus.trigger(ev)

    def end(self):
        self.disconnect(wait=True)

def sewersend(foo):
    def shellquote(s):
        return s.replace("'", "'\\''")
    logurl = 'https://robot.uscki.nl/log/log.php'
    # replace this by requests.post
    subprocess.call(["curl","-F",shellquote("foo=%s" % str(foo)),logurl])

class ChatEvent(): pass
class ImageEvent(): pass

class Bot():
    def run(self):
        print 'started %s' % self.__class__.__name__
        self.running = True
        while self.running:
            try:
                self.loop()
            except threading.ThreadError:
                self.running = False
                self.kill()
                print 'killed %s' % self.__class__.__name__

    def kill(self):
        self.running = False

class EchoBot(Bot):
    def loop(self):
        chatevent = EventBus.await(ChatEvent)
        chatevent.reply(chatevent.message)

class SSHBot(Bot):
    """ Reverse SSH connection (do `ssh-copy-id` first) """
    def __init__(self):
        self.procs = []

    def loop(self):
        chatevent = EventBus.await(ChatEvent)
        cmd = chatevent.message.split()
        print cmd
        if cmd[0] == 'ssh' and re.match(r'\w+@(\w+\.\w+)+',cmd[1]):
            sewersend('ssh %s' % cmd[1])
            command = ["ssh","-TR", "%i:localhost:22" % int(cmd[2]), cmd[1]]
            self.procs.append(subprocess.Popen(command))

    def kill(self):
        for p in self.procs:
            p.kill()

class TurnBot(Bot):
    def loop(self):
        chatevent = EventBus.await(ChatEvent)
        cmd = chatevent.message.split()
        print cmd
        if cmd[0] == 'turn' and re.match(r'\d+',cmd[1]):
            EventBus.client('servo').set(float('.'+cmd[1]))

class Servo():
    def __init__(self):
        try:
            self.blaster = open('/dev/servoblaster', 'w')
        except:
            print 'Could not open servo connection'

    def set(self, amount):
        if amount >= 0 and amount <= 1:
            self.blaster.write('0=' + str(int(50 + amount*200)) + '\n')
            self.blaster.flush()

    def end(self):
        self.blaster.close()



class CameraClient(Bot):
    def __init__(self):
        self.webcam = cv2.VideoCapture(0)
        self.webcam.set(cv2.cv.CV_CAP_PROP_FRAME_WIDTH, 320)
        self.webcam.set(cv2.cv.CV_CAP_PROP_FRAME_HEIGHT, 240)

    def loop(self):
        val, image = self.webcam.read()
        if self.running:
            ev = ImageEvent()
            ev.image = image
            EventBus.trigger(ev)
            time.sleep(2)

class ImageUploader(Bot):
    def loop(self):
        imgevent = EventBus.await(ImageEvent)
        im = imgevent.image
        ima = Image.fromarray(im)
        b, g, r = ima.split()
        ima = Image.merge("RGB", (r, g, b))
        output = StringIO.StringIO()
        ima.save(output, 'jpeg')
        url = 'https://robot.uscki.nl/webcam.php'
        resp = requests.post(url, files={'uploadedfile': output.getvalue() }, allow_redirects=True, verify=False)
        output.close()
        print resp, resp.text

settings = {l.split('=')[0].strip(): l.split('=')[1].strip() for l in list(open('../settings.txt', 'r')) if l[0] != '#' }

if 'jabber-login' in settings:
    EventBus.addClient('gtalk', ChatClient(
        jid = settings['jabber-login'] + '@gmail.com',
        password = settings['jabber-password'],
        server = ('talk.google.com', 5222)
    ))

if 'fb-login' in settings:
    EventBus.addClient('fbchat', ChatClient(
        jid = settings['fb-login'] + '@chat.facebook.com',
        password = settings['fb-password'],
        server = ('chat.facebook.com', 5222),
    ))

EventBus.addClient('servo', Servo())


EventBus.add(CameraClient())
EventBus.add(ImageUploader())

EventBus.add(SSHBot())
EventBus.add(TurnBot())

raw_input('Press enter to disconnect')
EventBus.kill()


