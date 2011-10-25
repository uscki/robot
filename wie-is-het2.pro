%% Wie is het? %%
:- ['fsa.pro'].
:- ['database.pro'].
:- dynamic temp/2.
:- dynamic chosen/1.
temp(_, fact(0,_)).


%% DCG
ja --> ['ja'];['Ja'];['ok'];['Ok'];['OK'];['oke'];['Oke'].
nee --> ['nee'].

question(QNr, Sub) -->
	{ question(QNr, Verb, Predicate) },
	[Verb], [Sub], Predicate, ['?'].

question_unknown(User, QNr) -->
	{ .(_,_)=P }, 
	[V], [_], P, ['?'],
	{	(not( question(_, V, P) ), add(QNr, question(QNr, V, P)), !);
		(question(QNr, V, P), temp(User, chosen(PNr)), \+fact(PNr, QNr, _), !) }.

question_final(PNr) -->
	[is, het, Naam, '?'],
	{ person(PNr, Naam) }.

%% The Game
gesteldeVraag(PNr, QNr) :~
	(	{	person(PNr, Name) }, question(QNr, Name) ),
	_:[
		[stop] ~> main : [],
		[In] ~> gesteldeVraag(PNr2, QNr2) : ( {add(fact(PNr,QNr, In))}, ['Ok!'],
			{	find_random((person(PNr2, _), question(QNr2, _, _), \+fact(PNr2, QNr2, _)), (PNr2, QNr2)) } )
	].

% Wortel-predikaat
main :~
	['Zullen we een spelletje spelen? Neem maar iemand in gedachte.'],
	User:[
		ja ~> mijnBeurt(1) : ( ['Ik begin','.'],
			{	retractall(temp(_,_)),
				find_random(person(Choice, _), Choice), 
				addtemp(User, chosen(Choice)) } ),
		nee ~> gesteldeVraag(_, _) : ['Dan heb ik wat vragen voor je. Zeg maar stop als je genoeg hebt gehad.'],
		['save'] ~> main : ({save}, ['Alles is opgeslagen.'])
	].


mijnBeurt(QNr) :~
	(question(QNr, Sub),
	{ (((temp(_, fact(1,Man)),((Man=ja,Sub=hij);(Man=nee,Sub=ze)), !);Sub='het'), !) } ),
	User: [
		[In] ~> opGeef : ( 
			{ addtemp(User, fact(QNr, In)), people_from_tempfacts(User, []), chosen(PNrc), person(PNrc,Pc) },
			['Ik geef het op. Ik had',Pc,'.'] ), % Niemand voldoet
		[In] ~> persoonGok(PNrg) : % Precies 1 iemand voldoet
			{ addtemp(User, fact(QNr, In)), people_from_tempfacts(User, [PNrg]) }, 
		[In] ~> jouwBeurt :
			({addtemp(User, fact(QNr, In)) }),
		['stop'] ~> main : []
	].

jouwBeurt :~
	['Jij bent','.'],
	User: [
		question_final(PNr) ~> main : ({ temp(User, chosen(PNr)) }, ['Ja! Jij wint!']),
		question_final(PNr) ~> main : ({ not(temp(User, chosen(PNr))) }, ['Nee, helaas','.']),
		question_unknown(User, QNr) ~> weetIkNiet(QNr) :
			({ temp(User, chosen(PNr)), person(PNr,P) }, ['Geen idee! Ik had',P,'.']),
		question(QNr, _) ~> mijnBeurt(Best) : 
			({ temp(User, chosen(PNr)), fact(QNr, PNr, Answer) }, [Answer, '.'], { best_question(User, Best) }),
		['stop'] ~> main : []
	].

persoonGok(PNr) :~
	question_final(PNr),
	_: [
		ja ~> main : ['Ik win!'],
		nee ~> opGeef : ['Helaas!'],
		['stop'] ~> main : []
	].

opGeef :~
	['Wie had jij?'],
	User: [
		add_from_temp(User) ~> main : [],
		['stop'] ~> main : []
	].

weetIkNiet(QNr) :~
	( {	temp(User, chosen(PNr)), person(PNr, Subject) }, question(QNr, Subject) ), 
	User: [
		[In] ~> opGeef : {temp(User, chosen(PNr)), add(fact(PNr, QNr, In))},
		['ok===='] ~> main : []
	].

%% Strategie %%
best_question(User, QNr) :-
	findall(Qn, (question(Qn, _, _), \+temp(User, fact(Qn, _))), QL),
	length(QL, N), random(0, N, LNr),
	nth0(LNr, QL, QNr).

people_from_tempfacts(User, PersonNrList) :- % Vindt alle personen die voldoen aan alle eisen
	findall(PersonNr, (
		person(PersonNr, _),
		findall(QNr, (temp(User, fact(QNr, A)),fact(PersonNr,QNr,A)), QList ),
		findall(QNr2, (temp(User, fact(QNr2,_))), QList )
	), PersonNrList).


%% Utils %%
:- use_module(library(random)).
find_random(FindPred, Result) :-
	findall(Result, FindPred, ResultList),
	length(ResultList, Length),
	random(0, Length, Index),
	nth0(Index, ResultList, Result).

% Database
save :-
	tell('database.pro'),
	listing(person/2),
	listing(fact/3),
	listing(question/3),
	told.

% Toevoegen aan geheugen
add(Pred) :-
	not(Pred) -> assertz(Pred).
add(Nr, Pred) :-
	not( Pred ),
	functor(Pred, A, B), functor(Pred2, A, B), Pred2 =.. [A, Nr|_],
	findall(Nr, Pred2, L),
	length([_|L], Nr), % One extra
	assertz( Pred ).
addtemp(User, Pred) :-
	assertz( temp(User, Pred) ).

% Tijdelijk geheugen
add_from_temp(User, Name, []) :-
	words_concat(Name, Opgeslagen),
	( (add(PersonNr, person(PersonNr, Opgeslagen)),!) ; person(PersonNr, Opgeslagen)),
	findall(_, (temp(User, fact(N,A)), add(fact(PersonNr, N, A)) ), _).