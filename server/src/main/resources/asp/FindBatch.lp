batch(batch_id_3, type2, 6).
batch(batch_id_4, type2, 1).
batch(batch_id_5, type2, 5).

batch(batch_id_10, type1, 3).
batch(batch_id_11, type1, 3).
batch(batch_id_12, type1, 3).

amount(1).
type(type1).

scelto(E, Q) :- batch(E, X, Q), type(X).
tot(T) :- T = #sum { Q, E : scelto(E, Q) }.
:- amount(A), tot(B), A > B.

% La funzione di minimizzazione non va bene, sistemare!
% #minimize { 1,Q : scelto(E,Q) }.
% #minimize { T : tot(T) }.
% #minimize { B-A : amount(A), tot(B) }.

% Mi aspetto che mi esca solo un atomo invece
% mi escono tutti e tre, come se il minimize non entrasse in funzione.
% scelto(batch_id_10,3) scelto(batch_id_11,3) scelto(batch_id_12,3)
