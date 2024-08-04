batch(batch_id_3, type2, 6).
batch(batch_id_4, type2, 1).
batch(batch_id_5, type2, 5).

batch(batch_id_10, type1, 3).
batch(batch_id_11, type1, 3).
batch(batch_id_12, type1, 3).

amount(2).
type(type2).

{ scelto(E, Q) } :- batch(E, X, Q), type(X).
tot(T) :- T = #sum { Q, E : scelto(E, Q) }.
:- amount(A), tot(B), A > B.
#minimize { 1, Q : scelto(E,Q) }.
#show scelto/2.
