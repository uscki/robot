/*
	FSA Chat Framework
	
	The automaton does a step on an input from one state to another.
	States send messages when you enter them.
	Transitions have conditions for transitioning, and their own output.
	
*/

:- op(1050, yfx, [:~]).
:- op(800, yfx, [~>]).

step(User, CurrentState, Input, Output, NextState) :-
	words_concat(InputList, Input),
	(CurrentState :~ (_, User:Transitions)),
		% Given Input, there's a transition to NextState saying TransOut
	member(  (InputParse ~> NextState : TransParse), Transitions),
	phrase(InputParse, InputList, []), % herkent
	phrase(TransParse, TransOut, NextOut), % genereert
		% There's a state NextState saying NextOut
	(NextState :~ (NextParse, User:_)),
	phrase(NextParse, NextOut, []), % genereert
		% Concatenation of the difference-list
	words_concat(TransOut, Output).


% Collapse sentence-list
words_concat(TransOut, Output) :-
	(nonvar(TransOut), fuse_punctuation(TransOut, L), atomic_list_concat(L, ' ', OutputL), capitalized([OutputL], [], [Output], []));
	(nonvar(Output),   capitalized([OutputL], [], [Output], []), atomic_list_concat(L, ' ', OutputL), fuse_punctuation(TransOut, L)).

fuse_punctuation([], []).
fuse_punctuation([H, X|T], [H2|T2]) :-
	(X='?'; X='!'; X='.'),
	atom_concat(H, X, H2),
	capitalized(T, R, TH, R), % Capitalize after punctuation
	fuse_punctuation(TH, T2), !.
fuse_punctuation([H, X|T], [H2|T2]) :-
	(X=','; X=')'; X=';'),
	atom_concat(H, X, H2),
	fuse_punctuation(T, T2), !.
fuse_punctuation([H|T], [H|T2]) :-
	fuse_punctuation(T, T2).

capitalized([], [], [], []).
capitalized([Lo|R], R) -->
	[Hi], { 
		(	(	atomic(Hi),	name(Hi, [H|T]), 64 @< H, H @< 91,  H2 is H+32, name(Lo, [H2|T]) ) ; 
			( atomic(Lo), name(Lo, [H|T]), 96 @< H, H @< 123, H2 is H-32, name(Hi, [H2|T]) ) ) ;
		( Hi=Lo	) }, {!}.