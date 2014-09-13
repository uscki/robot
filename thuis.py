import EventBus
from game import GameStarter
from hogerlager import HogerLager
from wieishet import WieIsHet
from EventBus import ChatEvent

def reply(b):
    print b

EventBus.add(GameStarter(HogerLager, lambda m: (m == 'hogerlager') ))
EventBus.add(GameStarter(WieIsHet, lambda m: (m == 'wie is het') ))

inp = ''
while inp is not 'q':
    inp = raw_input(':')
    ev = ChatEvent()
    ev.message = inp
    ev.sender = 'benno'
    ev.reply = reply
    EventBus.trigger(ev)


EventBus.kill()