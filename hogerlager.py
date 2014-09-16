import random
from game import Game

class HogerLager(Game):
    def play(self):
        num = random.randint(1,100)
        self.say('Raad maar mijn getal')
        guessed = False
        while not guessed:
            guess = int(self.input().split()[-1].split('?')[0])
            if guess == num:
                guessed = True
            elif guess < num:
                self.say('Hoger!')
            elif guess > num:
                self.say('Lager!')
        self.say('Goed zo!')