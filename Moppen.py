from game import Game
from Memory import Memory
import random

# laad shit if __name__ == '__main__':
class Moppen(Game):
    def play(self):
    	if Memory.hasMemory("moppen"):
    		# load moppen memory
    		moppen = Memory.recall()

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
