:- dynamic person/2.

person(1, 'Je Moeder').
person(2, 'Benno Kruit').
person(3, 'Sjoerd Dost').
person(4, 'Rick Sen').
person(5, 'Susan Brommer').
person(6, 'Sophie Zuiderduin').
person(7, 'Jetze Baumfalk').
person(8, 'Paul Verheul').

:- dynamic fact/3.

fact(1, _, ja).
fact(2, 1, ja).
fact(2, 3, ja).
fact(3, 1, ja).
fact(3, 3, nee).
fact(2, 2, nee).
fact(4, 1, ja).
fact(4, 2, nee).
fact(4, 3, nee).
fact(3, 2, ja).
fact(5, 1, nee).
fact(6, 1, nee).
fact(5, 3, ja).
fact(2, 4, nee).
fact(3, 5, nee).
fact(4, 6, nee).
fact(5, 6, ja).
fact(7, 1, 'Ja').
fact(2, 6, nee).
fact(7, 2, nee).
fact(2, 5, nee).
fact(6, 5, nee).
fact(5, 4, nee).
fact(4, 5, ja).
fact(3, 6, nee).
fact(7, 6, nee).
fact(6, 2, nee).
fact(3, 4, nee).
fact(5, 5, nee).
fact(6, 3, ja).
fact(7, 5, ja).
fact(6, 6, ja).
fact(7, 3, nee).
fact(1, 1, nee).
fact(4, 4, ja).
fact(3, 7, ja).
fact(6, 7, ja).
fact(2, 3, ja).
fact(2, 8, ja).
fact(2, 7, ja).
fact(2, 9, nee).
fact(8, 1, ja).
fact(8, 5, nee).
fact(8, 4, ja).
fact(8, 7, ja).
fact(8, 8, nee).
fact(8, 2, nee).
fact(8, 10, nee).

:- dynamic question/3.

question(1, is, [een, man]).
question(2, is, ['19', jaar, oud]).
question(3, is, ['20', jaar, oud]).
question(4, is, [blond]).
question(5, is, ['21', jaar, oud]).
question(6, is, [een, vrouw]).
question(7, heeft, [een, fiets]).
question(8, woont, [in, 'Utrecht']).
question(9, heeft, [een, hond]).
question(10, heeft, [een, rode, fiets]).

