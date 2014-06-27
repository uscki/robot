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
        running = True
        while running:
            try:
                self.loop()
            except threading.ThreadError:
                running = False
                self.kill()
                print 'killed %s' % self.__class__.__name__

    def kill(self): pass

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

class CameraClient(Bot):
    def __init__(self):
        self.webcam = cv2.VideoCapture(0)
        self.webcam.set(cv2.cv.CV_CAP_PROP_FRAME_WIDTH, 320)
        self.webcam.set(cv2.cv.CV_CAP_PROP_FRAME_HEIGHT, 240)

    def loop(self):
        val, image = self.webcam.read()
        ev = ImageEvent()
        ev.image = image
        EventBus.trigger(ev)
        time.sleep(1)

class ImageUploader(Bot):
    def loop(self):
        imgevent = EventBus.await(ImageEvent)
        im = imgevent.image
        ima = Image.fromarray(im)
        output = StringIO.StringIO()
        ima.save(output, 'jpeg')
        url = 'http://192.168.1.42/~benno/webcam.php'
        resp = requests.post(url, files={'uploadedfile': output.getvalue() }, allow_redirects=True, verify=False)
        output.close()
        # print resp.text


gtalk = ChatClient(
    jid = '',
    password = '',
    server = ('talk.google.com', 5222)
)

# fbchat = ChatClient(
#     jid = 'x',
#     password = '',
#     server = ('chat.facebook.com', 5222),
# )

# EventBus.add(CameraClient())
# EventBus.add(ImageUploader())

EventBus.add(SSHBot())

raw_input('Press enter to disconnect')
EventBus.kill()

gtalk.end()
# fbchat.end()
