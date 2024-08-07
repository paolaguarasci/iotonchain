{ scelto(E, Q) } :- batch(E, X, Q), type(X).
tot(T) :- T = #sum { Q, E : scelto(E, Q) }.
:- amount(A), tot(B), A > B.
#minimize { 1, Q : scelto(E,Q) }.
#show scelto/2.
