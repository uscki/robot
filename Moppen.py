from game import Game
from Memory import recall
import random

class Moppen(Game):
    def play(self):
    	if "moppen" in recall:
    		# load moppen memory
    		moppen = recall["moppen"]

    		if random.random() > 0.8:
    			self.say(random.choice(moppen))
 			else:
 				self.say("Nee, vertel jij mij nu maar eens een mop!")
 				mop = self.input()
 				moppen.add(mop)   			
    	else:
    		moppen = []
    		self.say("Verduiveld, ik weet nog geen moppen! Vertel mij er eens een. :-)")
    		mop = self.input()
    		moppen.add(mop)
