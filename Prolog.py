from game import Game

""" Prolog built-ins """

def var(term):
    return (term[0].isupper() or term[0] == '_')

def nonvar(term):
    return not var(term)

def compound(term):
    return '(' in term

def atom(term):
    return not compound(term)

def univ(term):
    if atom(term):
        return [term]
    i = term.find('(')
    functor, args = term[:i], term[i+1:-2]
    return [functor] + args.split(',')


# TODO: check conflicts in aliases, do occurence checks!
def unification(term1, term2, aliases):

    if len(term2) == 1:

        term2 = term2[0]

    if len(term1) == 1:

        term1 = term1[0]

        # Two identical atoms

        if atom(term1) and atom(term2) and term1 == term2:  
            return True, aliases

        # Uninstantiated Var can be unified with atom, term or other uninstantiated var
        elif var(term1):
            if not term1 in aliases:
                if isinstance(term2, list) or atom(term2) or (var(term2) and not term2 in aliases):
                    aliases[term1] = term2
                    return True, aliases

    elif len(term2) == 1:

        if var(term2): 
            if not term2 in aliases:
                aliases[term2] = term1
                return True, aliases

    # Recursive: term with term if functor and arity are identical, and args can be unified

    else:
        #decomp1 = univ(term1)
        #functor1, args1 = decomp1[0], decomp1[1:]
        #decomp2 = univ(term2)
        #functor2, args2 = decomp2[0], decomp2[1:]
        functor1, args1 = term1[0], term1[1:]
        functor2, args2 = term2[0], term2[1:]
        if functor1 == functor2 and len(args1) == len(args2):
            for i in range(0, len(args)):
                boolean, al = unification(args1[i], args2[i], aliases)
                if boolean:
                    aliases = al
                else:
                    return False, {}

    return False, {}

""" End built-ins """

class Prolog(Game):

    def play(self):

        self.memory = {('man',1) : [['sjoerd'], ['benno']]}
        #self.builtin = [('var', 1), ('nonvar', 1), ('compound', 1), ('atom', 1), ('univ', 1)]

        done = False

        while not done:
            self.say('?-')
            
            inp = self.input()

            # TODO: Check validity of input

            if inp == "quit":
                done = True
                break

            if not inp[-1] == '.':
                self.say('Don\'t forget that period!')
                continue

            # TODO: Check validity!
            if inp.startswith("assert("):
                decomp = univ(inp[7:-1])
                functor, args = decomp[0], decomp[1:]
                if (functor, len(args)) in self.memory:
                    self.memory[(functor, len(args))] = self.memory[(functor, len(args))] + [args]
                else:
                    self.memory[(functor, len(args))] = [args]

            # Handle input : functor ( comma-separated args )
            # TODO: arity/0

            stack = [[inp]]

            while stack:

                aliases = {}

                current = stack.pop()

                for term in current:

                    if compound(term):

                        decomp = univ(term)
                        functor, args = decomp[0], decomp[1:]

                        if not (functor, len(args)) in self.memory:
                            break
                        
                        for memargs in self.memory[(functor, len(args))]:
                            unifies, al = unification(args, memargs, {})
                            if unifies:
                                for k,v in al.items:
                                    if k in aliases:
                                        # clash
                                        break
                                    else:
                                        aliases[k] = v

                    else:
                        self.say('TODO')

            if not stack:
                for k,v in aliases.items():
                    self.say(k + ' = ' + v)
                self.say('True.')
            else:
                self.say('False.')
                        