from random import random
from game import Game

class HogerLager(Game):
    def play(self):
        num = int(random()*100)
        print 'Raad maar mijn getal'
        guessed = False
        while not guessed:
            guess = int(self.input().split()[-1].split('?')[0])
            if guess == num:
                guessed = True
            elif guess < num:
                print 'hoger!'
            elif guess > num:
                print 'lager!'
        print 'Goed zo!'