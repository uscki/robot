import EventBus
from EventBus import ImageEvent
from game import GameStarter
from hogerlager import HogerLager
from wieishet import WieIsHet
from EventBus import ChatEvent
from CameraClient import CameraClient
import cv2

def reply(b):
    print b

EventBus.add(GameStarter(HogerLager, lambda m: (m == 'hogerlager') ))
EventBus.add(GameStarter(WieIsHet, lambda m: (m == 'wie is het') ))

""" Camera Test """

from bot import Bot

EventBus.add(CameraClient())

class ImageSaver(Bot):
    def loop(self):
        imgevent = EventBus.await(ImageEvent)
        im = imgevent.image
        cv2.imwrite("plaatje.jpeg", im)

EventBus.add(ImageSaver())

""" End Camera Test """

inp = ''
while inp is not 'q':
    inp = raw_input(':')
    ev = ChatEvent()
    ev.message = inp
    ev.sender = 'benno'
    ev.reply = reply
    EventBus.trigger(ev)


EventBus.kill()