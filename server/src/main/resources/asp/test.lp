peso(oggetto1, 5).
peso(oggetto2, 3).
peso(oggetto3, 8).

peso_totale(Totale) :- Totale = #sum { Peso : peso(_, Peso) }.
