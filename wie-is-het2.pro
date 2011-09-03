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

question_unknown(QNr) -->
	{ .(_,_)=P }, 
	[V], [_], P, ['?'],
	{	(\+question(_, V, P), add_question(V,P, QNr), !);
		(question(QNr, V, P), temp(_, chosen(PNr)), \+fact(PNr, QNr, _), !) }.

question_final(PNr) -->
	[is, het, Naam, '?'],
	{ person(PNr, Naam) }.

%% The Game
gesteldeVraag(PNr, QNr) :~
	(	{	person(PNr, Name) }, question(QNr, Name) ),
	_:[
		stop ~> main : [],
		[In] ~> gesteldeVraag(PNr2, QNr2) : ( {add_fact(PNr,QNr, In)}, ['Ok!'],
			{	find_random((person(PNr2), question(QNr2, _, _), \+fact(PNr2, QNr2, _)), (PNr2, QNr2)) } )
	].

% Wortel-predikaat
main :~
	['Zullen we een spelletje spelen? Neem maar iemand in gedachte.'],
	_:[
		ja ~> mijnBeurt(1) : ( ['Ik begin','.'],
			{	retractall(temp(_,_)),
				find_random(person(Choice, _), Choice), 
				add(_, temp(_, chosen(Choice))) } ),
		nee ~> gesteldeVraag(_, _) : ['Dan heb ik wat vragen voor je. Zeg maar stop als je genoeg hebt gehad.'],
		['save'] ~> main : ({save}, ['Alles is opgeslagen.'])
	].


mijnBeurt(QNr) :~
	(question(QNr, Sub),
	{ (((temp(_, fact(1,Man)),((Man=ja,Sub=hij);(Man=nee,Sub=ze)), !);Sub='het'), !) } ),
	_: [
		[In] ~> opGeef : ( 
			{ addtemp(_, fact(QNr, In)), people_from_tempfacts([]), chosen(PNrc), person(PNrc,Pc) },
			['Ik geef het op. Ik had',Pc,'.'] ), % Niemand voldoet
		[In] ~> persoonGok(PNrg) : % Precies 1 iemand voldoet
			{ addtemp(_, fact(QNr, In)), people_from_tempfacts([PNrg]) }, 
		[In] ~> jouwBeurt :
			({addtemp(_, fact(QNr, In)) }),
		['stop'] ~> main : []
	].

jouwBeurt :~
	['Jij bent','.'],
	User: [
		question_final(PNr) ~> main : ({ chosen(PNr) }, ['Ja! Jij wint!']),
		question_final(PNr) ~> main : ({ not(chosen(PNr)) }, ['Nee, helaas','.']),
		question_unknown(QNr) ~> weetIkNiet(QNr) :
			({ temp(User, chosen(PNr)), person(PNr,P) }, ['Geen idee! Ik had',P,'.']),
		question(QNr, _) ~> mijnBeurt(Best) : 
			({ temp(User, chosen(PNr)), fact(QNr, PNr, Answer) }, [Answer, '.'], { best_question(Best) }),
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
	_: [
		add_from_temp ~> main : [],
		['stop'] ~> main : []
	].

weetIkNiet(QNr) :~
	( {	chosen(PNr), person(PNr, Sub) }, question(QNr, Sub) ), 
	_: [
		[In] ~> opGeef : {add_fact(PNr, QNr, In)},
		['stop'] ~> main : []
	].

%% Strategie %%
best_question(QNr) :-
	findall(Qn, (question(Qn, _, _), \+temp(_, fact(Qn, _))), QL),
	length(QL, N), random(0, N, LNr),
	nth0(LNr, QL, QNr).

people_from_tempfacts(PersonNrList) :- % Vindt alle personen die voldoen aan alle eisen
	findall(PersonNr, (
		person(PersonNr, _),
		findall(QNr, (temp(_, fact(QNr, A)),fact(PersonNr,QNr,A)), QList ),
		findall(QNr2, (temp(_, fact(QNr2,_))), QList )
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
add(Nr, Pred) :-
	not( Pred ),
	findall(Nr, Pred, L),
	length([L|[_]], Nr), % One extra
	assertz( Pred ).
addtemp(User, Pred) :-
	assertz( temp(User, Pred) ).

% Tijdelijk geheugen
add_from_temp(Name, []) :-
	add_person(Name, PersonNr),
	findall(_, (temp(_, fact(N,A)),add_fact(PersonNr,N,A)), _).
add_from_temp(Name) :-
	person(PersonNr, Name),
	findall(_, (temp(_, fact(N,A)),add_fact(PersonNr,N,A)), _).