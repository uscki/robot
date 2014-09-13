import EventBus
from EventBus import ChatEvent
from bot import Bot
import threading

class GameStarter(Bot):
    def __init__(self, game_type, game_starter):
        self.games = {}
        self.game_type = game_type
        self.starter = game_starter

    def loop(self):
        chatevent = EventBus.await(ChatEvent)

        # delete old games
        for partner in list(self.games.keys()):
            if not self.games[partner].thread.isAlive():
                del self.games[partner]

        if chatevent.sender not in self.games and self.starter(chatevent.message):
            # make a game, add it to the pool, start it in its own thread
            game = self.game_type(chatevent.sender, chatevent.reply)
            self.games[chatevent.sender] = game
            game.thread = threading.Thread(target=game.play)
            game.thread.start()

    def kill(self):
        for g in self.games.values():
            g.end()
            g.thread.join()

class Game():
    def __init__(self, partner, send_function):
        self.playing = True
        self.say = send_function
        self.partner = partner
        self.thread = None

    def play(self): pass

    def input(self):
        while self.playing:
            chatevent = EventBus.await(ChatEvent)
            if chatevent.sender == self.partner:
                return chatevent.message

    def end(self):
        self.playing = False

