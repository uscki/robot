import EventBus
from EventBus import ChatEvent
from bot import Bot
from UsckiAPI import UsckiAPI
import re

class SmoBot(Bot):
    def loop(self):
        chatevent = EventBus.await(ChatEvent)
        m = re.match('waar woont ([\w ]+)\?*', chatevent.message, flags = re.I)
        if m:
            smobo = UsckiAPI('Smobo', get={'query': m.group(1)})
            ad = smobo['//smobo:person/smobo:address1/text()']
            names = smobo['//smobo:person/smobo:fullname/text()']
            if len(ad) == 1:
                chatevent.reply('%s woont op de %s' % (names[0], ad[0]))
            else:
                names = ' of '.join(names)
                chatevent.reply('bedoel je %s?' % names)