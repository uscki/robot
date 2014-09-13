import threading
evt = threading.Event()
threads = []

def add(bot):
    t = threading.Thread(target=bot.run)
    t.bot = bot
    threads.append(t)
    t.start()

def kill():
    trigger(None)
    for t in threads:
        t.bot.kill()
        t.join()

def trigger(obj):
    evt.value = obj
    evt.set()
    evt.clear()

def awaitall():
    evt.wait()
    obj = evt.value
    if obj:
        return obj
    else:
        # this is really not robust
        # if a bot is not waiting, it will never get the kill signal
        raise threading.ThreadError()

def await(event_type):
    e = None
    while not isinstance(e, event_type):
        e = awaitall()
    return e