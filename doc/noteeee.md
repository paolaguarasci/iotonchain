\begin{itemize}
  \item Il sistema consente ad un'azienda registrata di creare nuove tipologie di prodotti e registra questa operazione sulla blockchain.
  \item Il sistema consente ad un'azienda registrata di creare nuovi lotti di produzione di prodotti di un tipo specifico tra le tipologie gestite dall'azienda e registra questa operazione sulla blockchain.
  \item Il sistema consente ad un'azienda registrata di caricare un documento in piattaforma. Questo documento può essere legato all'azienda stessa, ad un tipo di prodotto oppure ad un lotto di produzione.
  \item Il sistema notarizza il documento caricato dall'azienda registrata per implementare il non-ripudio dello stesso ed utilizza la blockchain con funzione di timestamp.
  \item Il sistema consente ad un'azienda registrata di trasferire prodotti verso un'altra azienda registrata e registra questa operazione sulla blockchain.
  \item Il sistema consente ad un'azienda registrata di trasformare prodotti e registra questa operazione sulla blockchain.
  \item Il sistema consente ad un utente non registrato in possesso di un apposito link e di un identificativo valido di lotto di produzione di visualizzare la storia di quel lotto di produzione
  \item Il sistema gestisce dati provenienti da sensori relativi ad uno specifico lotto di produzione e ne registra i dati all'interno della blockchain.
  \item Nel sistema operano utenti per conto delle aziende registrate. Ogni utente opera al più per una azienda. Ogni azienda può avere più di un operatore.
\end{itemize}


\begin{itemize}
\item Il caso d'uso inizia quando l'utente visualizza la lista delle transazioni
\item Se l'utente seleziona una transazione completata e clicca su "Invia"
  \begin{itemize}
  \item Il sistema cerca un camion disponibile
  \item Se ci sono camion disponibili
    \begin{itemize}
    \item il sistema associa il camion al lotto oggetto della transazione
    \item il sistema aggiorna periodicamente le letture dei sensori associati al camion
    \item il sistema salva in modo immutabile le letture dei sensori
    \end{itemize}
  \item Se non ci sono camion disponibili 
    \begin{itemize}
    \item Il sistema notifica un errore
    \end{itemize}
  \end{itemize}
\end{itemize}




- Company - Batch:
  - Cardinalità: uno a molti con partecipazione opzionale
  - Descrizione: Ogni Company può avere da 0 a n Batch prodotti. Ogni Batch ha una ed una sola Company che lo produce.
  - Ruolo: Produttore

- Company - Batch:
  - Cardinalità: uno a molti con partecipazione opzionale
  - Descrizione: Ogni Company può avere da 0 a n Batch detenuti. Ogni Batch ha una ed una sola Company che lo detiene. 
  - Ruolo: Proprietario

- ProductType - Batch:
  - Cardinalità: uno a molti con partecipazione opzionale
  - Descrizione: Ogni ProductType può avere uno o più Batch che lo materializza. Un Batch ha esattamente un ProductType.

- Location - Batch;
  - Cardinalità: uno a molti con partecipazione opzionale
  - Descrizione: Ogni Location può essere il luogo di produzione di zero o più Batch. Un Batch ha esattamente una Location.

- Document - Batch;
  - Cardinalità: molti a molti con partecipazione opzionale
  - Descrizione: Ogni Document puà essere legato a più Batch, ogni Batch può avere collegati zero o più Document.

- RecipeBatch - Batch:
  - Cardinalità: 
  - Descrizione: 

- ProductionProcessBatch - Batch:
  - Cardinalità: 
  - Descrizione:  

- ProductType - Company:
  - Cardinalità: Molti a molti
  - Descrizione: Ogni tipo di prodotto può essere legato ad una o più Company ed ogni Company può utilizzare uno o più tipi di prodotto

- UserInfo - Company:
  - Cardinalità: uno a molti con partecipazione obbligatoria
  - Descrizione: Un utente (UserInfo) ha esattamente una compagnia (Company) collegata. Ogni compagnia (Company) ha almeno un utente collegato.

- Notarize - Document:
  - Cardinalità: uno ad uno con partecipazione opzionale
  - Descrizione: Ogni Document ha alpiù una notarizzazione ed ogni Notarize può avere alpiù un Document

- Company - Document: 
  - Cardinalità: uno a molti con partecipazione opzionale
  - Descrizione: Ogni Document da riferimento ad esattamente una Company. Una Company puoò avere zero o più Document collegati.

- Company - Notarize:
  - Cardinalità: uno a molti con partecipazione obbligatoria
  - Descrizione: Una Notarize ha esattamente una Company che l'ha creata ed una Company può creare zero o più Notarize.

- ChainTransaction - Notarize:
  - Cardinalità: uno a molti con partecipazione obbligatoria
  - Descrizione: Una Notarize ha almeno una ChainTransaction (transazione effettiva sulla blockchain). Ogni ChainTransaction fa riferimento ad una sola Notarize.

- ProductionStep - ProductionProcess
  - Cardinalità: uno a molti con partecipazione obbligatoria
  - Descrizione: Un processo produttivo di tipo (ProductionProcess) ha almeno uno step di produzione di tipo (ProductionStep). Ogni step (ProductionStep) fa parte di un singolo processo produttivo (ProductionProcess).

- ProductionStepBatch - ProductionProcessBatch
  - Cardinalità: uno a molti con partecipazione obbligatoria
  - Descrizione: Un processo produttivo di lotto (ProductionProcessBatch) ha almeno uno step di produzione di lotto (ProductionStepBatch). Ogni step (ProductionStepBatch) fa parte di un singolo processo produttivo (ProductionProcessBatch).

- ProductionStepBatch - Notarize
  - Cardinalità: uno ad uno con partecipazione opzionale
  - Descrizione: Ogni processo produttivo di lotto (ProductionStepBatch) può avere al più un atto di notarizzazione (Notarize).

- Recipe - ProductType:
  - Cardinalità: 
  - Descrizione: 

- ProductionProcess - ProductType:
  - Cardinalità: 
  - Descrizione:  

- Recipe - RecipeRow:
  - Cardinalità: uno a molti con partecipazione obligatoria
  - Descrizione: Ogni ricetta di tipo (Recipe) ha almeno un ingrediente di tipo(RecipeRow).

- RecipeBatch - RecipeRowBatch:
  - Cardinalità: uno a molti con partecipazione obligatoria
  - Descrizione: Ogni ricetta di lotto (RecipeBatch) ha almeno un ingrediente di lotto (RecipeRowBatch).

- RecipeRow - ProductType:
  - Cardinalità: uno a molti con partecipazione obbligatoria
  - Descrizione: Ogni riga di ricetta di tipo (RecipeRow) si riferisce ad esattamente un tipo di prodotto (ProductType). Ogni ProductType può fare parte di zero o più ricette di tipo.

- RecipeRowBatch - Batch
  - Cardinalità: uno a molti con partecipazione obbligatoria
  - Descrizione: Ogni riga di ricetta di lotto (RecipeRowBatch) si riferisce ad esattamente un lotto di prodotto (Batch). Ogni Batch può fare parte di zero o più ricette di lotto.

- SensorsLog - Notarize
  - Cardinalità: uno a molti con partecipazione opzionale
  - Descrizione: Gruppi di item di log dei sensori (SensorsLog) possono fare riferimento ad uno stesso item di notarizzazione (Notarize) se sono stati raggruppati e notarizzati nello stesso momento, per esempio per letture ravvicinate nel tempo.

- ChainTransaction - Transfer
  - Cardinalità: uno a molti con partecipazione opzionale
  - Descrizione: Ogni trasferimento di lotti (Transfer) si riferisce ad una o più transazioni effettive sulla chain (ChainTransaction). Per esempio, il tipo con accettazione prevede tre transazioni. 

- Truck - Company
  - Cardinalità: uno a molti
  - Descrizione: Ogni Truck fa riferimento ad una sola Company

- Truck - MyDt
  - Cardinalità: uno a molti
  - Descrizione: Ogni Truck ha una lista di gemelli digitali collegati 