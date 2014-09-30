import pickle

fname = "memory.p"

recall = {}
try:
    recall = pickle.load( open( fname, "rb" ) )
except:
    pass

def remember():
    pickle.dump( recall, open( fname, "wb" ) )