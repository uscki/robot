from random import choice
from game import Game

class Knowledge():
    def __init__(self):
        # (verb, object) x (person)
        self.facts = [('woont', 'in Utrecht'), ('heeft', 'een baard')]
        self.people = ['benno', 'sjoerd']
        self.knowledge = {0: {0: True}, 1: {0: True}}

    def getfact(self, v,o):
        for i, (kv,ko) in enumerate(self.facts):
            if v.lower() == kv.lower() and o.lower() == ko.lower():
                return i
        else:
            fact_id = len(self.facts)
            self.facts.append((v, o))
            self.knowledge[fact_id] = {}
            return fact_id

    def getperson(self, p):
        for i, kp in enumerate(self.people):
            if p.lower() == kp.lower():
                return i
        else:
            self.people.append(p)
            return len(self.people) - 1


    def match(self, yes, no):
        for person, _ in enumerate(self.people):
            if (all((self.knowledge[y][person] if person in self.knowledge[y] else False) for y in yes) and
                    not any((self.knowledge[n][person] if person in self.knowledge[n] else True) for n in no)):
                yield person

    def best_question(self, yes, no):
        return choice(list(set(range(len(self.facts))) - set(yes + no)))

    def update(self, p, yes, no):
        for y in yes:
            if p in self.knowledge[y] and not self.knowledge[y][p]:
                v,o = self.facts[y]
                print 'Hee, maar ik dacht dat %s niet %s %s!' % (self.people[p].capitalize(), o, v)
                return
            else:
                self.knowledge[y][p] = True
        for n in no:
            if p in self.knowledge[n] and self.knowledge[n][p]:
                v,o = self.facts[n]
                print 'Hee, maar ik dacht dat %s %s %s!' % (self.people[p].capitalize(), o, v)
                return
            else:
                self.knowledge[n][p] = False




class WieIsHet(Game):
    def play(self):
        k = Knowledge()

        def question_input(q):
            # return (v, s, o)
            if q:
                q = q.strip()
                if q[-1] is '?':
                    q = q[:-1]
                q = q.split()
                return q[0].lower(), q[1].lower(), ' '.join(q[2:])
            else:
                return None

        def ask((v,o)):
            print v.capitalize() + ' ie ' + o + '?'
            return (self.input().lower() == 'ja')
        def guess(p):
            print 'Is het ' + p + '?'
            return (self.input().lower() == 'ja')
        def give_up():
            print 'Ik geef het op. Wie had jij?'
            k.update(k.getperson(self.input()), yes, no)

        stop = False
        while not stop:
            print 'Neem maar iemand in gedachten.'        
            yes, no = [], []
            my_turn = True
            winner = None
            it = choice(k.people)
            while winner is None:
                if my_turn:
                    print 'Ik ben.'
                    m = list(k.match(yes, no))
                    if len(m) is 1:
                        if guess(k.people[m[0]]):
                            print 'Joepie!'
                            winner = 0
                        else:
                            print 'Helaas.'
                            give_up()
                            winner = 1
                    elif not m:
                        give_up()
                        winner = 1
                    else:
                        q = k.best_question(yes, no)
                        if ask(k.facts[q]):
                            yes.append(q)
                        else:
                            no.append(q)
                else:
                    print 'Jij bent.'
                    v, s, o = question_input(self.input())
                    if v is 'is' and s is 'het':
                        if o.lower() is it.lower():
                            winner = 1
                            print 'Ja! Jij wint!'
                        else:
                            # misschien extra feiten toevoegen?
                            print 'Nee, helaas, blijf raden.'
                    else:
                        f, p = k.getfact(v,o), k.getperson(it)
                        if p in k.knowledge[f]:
                            print 'Ja' if k.knowledge[f][p] else 'Nee'
                        else:
                            print 'Weet ik niet! Ik had %s.' % it.capitalize()
                            print '%s %s %s?' % (v.capitalize(), it.capitalize(), o)
                            k.knowledge[f][p] = (self.input().lower() == 'ja')
                            print 'En wie had jij?'
                            k.update(k.getperson(self.input()), yes, no)
                            winner = 1
                my_turn = not my_turn
            print 'Nog een potje?'
            stop = (self.input().lower() == 'nee')


