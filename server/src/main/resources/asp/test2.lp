batch(b1, b, 3). batch(b2, b, 5). batch(b3, b, 10). batch(a1, a, 10). batch(a1, a, 10). batch(c1, c, 10).
scelto(X,Y) :- batch(X, b, Y).
tot(X) :- X = #sum { Y: scelto(_,Y) }.
