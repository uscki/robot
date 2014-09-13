import threading

class Bot():
    def run(self):
        print 'started %s' % self.__class__.__name__
        self.running = True
        while self.running:
            try:
                self.loop()
            except threading.ThreadError:
                self.running = False
                self.kill()
                print 'killed %s' % self.__class__.__name__

    def kill(self):
        self.running = False