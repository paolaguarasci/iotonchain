ad oggi 5 agosto e' piu aggiornata online

https://it.overleaf.com/project/66939ee034c12d8cf27fcb89

# APPUNTI RANDOM - DA CHATTINO - RIMASTRUCCARE

## ASP

Answer Set Programming (ASP) è una metodologia di programmazione dichiarativa per risolvere problemi complessi in vari domini, tra cui l'intelligenza artificiale, la robotica e la teoria dei giochi. ASP si basa su una logica non monotona, in particolare sulla logica di default e sulla logica dei modelli stabili, per rappresentare conoscenze incomplete o incerte e per ragionare su di esse.

### Fondamenti teorici

ASP utilizza un linguaggio formale per descrivere problemi attraverso regole logiche. Un programma ASP è costituito da un insieme di regole, ciascuna delle quali può essere vista come un'implicazione logica. Le regole hanno una forma simile a quella utilizzata nella logica dei predicati, ma con una semantica diversa, che è governata dal concetto di _modello stabile_. Un modello stabile di un programma ASP è un insieme di atomi che soddisfano tutte le regole del programma, considerando anche le negazioni presenti nelle regole stesse.

La logica sottostante a ASP è fortemente non monotona, il che significa che l'aggiunta di nuove informazioni può invalidare conclusioni precedentemente valide. Questo è un aspetto chiave per la rappresentazione di conoscenza dinamica e per la gestione di informazioni incomplete. La teoria dei modelli stabili, introdotta da Gelfond e Lifschitz nel 1988, fornisce la base semantica di ASP, permettendo di definire con precisione cosa si intenda per soluzione di un programma logico non monotono .

### Sintassi e Semantica

La sintassi di ASP è caratterizzata da regole della forma:

```
a :- b1, b2, ..., bn, not c1, not c2, ..., not cm.
```

dove `a` è un letterale positivo (una proposizione), `b1, b2, ..., bn` sono letterali positivi che formano il corpo della regola, e `not c1, not c2, ..., not cm` sono letterali negati attraverso la negazione classica. L'interpretazione di una regola è tale che se tutti i letterali `b1, b2, ..., bn` sono veri e nessuno dei letterali `c1, c2, ..., cm` è vero, allora `a` deve essere vero.

La semantica di ASP è definita attraverso i modelli stabili, che rappresentano le soluzioni del programma. Un modello stabile è un insieme di atomi che rispetta tutte le regole del programma. La determinazione dei modelli stabili può essere vista come un processo di ricerca di soluzioni per un insieme di vincoli, il che rende ASP particolarmente adatto per problemi di ottimizzazione e soddisfazione di vincoli .

### Applicazioni e Implementazioni

ASP è ampiamente utilizzato in diversi campi, grazie alla sua capacità di gestire problemi complessi e di esprimere ragionamenti non monotoni. Ad esempio, è stato applicato con successo in ambiti come il planning automatico, la gestione della conoscenza, la bioinformatica e la verifica e validazione del software. Gli strumenti di ASP, come _clingo_, _DLV_, e _smodels_, offrono potenti solutori per la computazione di modelli stabili e includono varie tecniche di ottimizzazione per migliorare l'efficienza del calcolo .

### Vantaggi e Limitazioni

Uno dei principali vantaggi di ASP è la sua espressività e flessibilità. Permette di modellare in modo naturale problemi complessi che coinvolgono ragionamenti con conoscenza incompleta o incerta. Inoltre, grazie alla sua natura dichiarativa, i programmatori possono concentrarsi sulla descrizione del problema piuttosto che sui dettagli algoritmici della soluzione.

Tuttavia, ASP presenta anche alcune limitazioni. La computazione di modelli stabili può essere computazionalmente costosa, soprattutto per programmi di grandi dimensioni. Questo limita la scalabilità di ASP in alcuni contesti applicativi. Inoltre, la mancanza di un ampio supporto industriale e di standardizzazione può rendere più difficile l'adozione di ASP in ambito commerciale .

### Conclusione

Answer Set Programming rappresenta una potente metodologia per la rappresentazione e risoluzione di problemi complessi in vari domini. La sua capacità di esprimere ragionamenti non monotoni e di gestire conoscenze incomplete lo rende uno strumento prezioso per molte applicazioni. Nonostante le sue limitazioni, continua a essere un'area attiva di ricerca e sviluppo, con promettenti applicazioni future.

---

**Fonti:**

1. Gelfond, M., & Lifschitz, V. (1988). The Stable Model Semantics for Logic Programming. In Proceedings of the Fifth International Conference and Symposium on Logic Programming.
2. Baral, C. (2003). Knowledge Representation, Reasoning and Declarative Problem Solving. Cambridge University Press.
3. Gebser, M., Kaminski, R., Kaufmann, B., & Schaub, T. (2012). Answer Set Solving in Practice. Synthesis Lectures on Artificial Intelligence and Machine Learning, Morgan & Claypool Publishers.
4. Leone, N., Pfeifer, G., Faber, W., Eiter, T., Gottlob, G., Perri, S., & Scarcello, F. (2006). The DLV System for Knowledge Representation and Reasoning. ACM Transactions on Computational Logic (TOCL), 7(3), 499-562.
5. Eiter, T., Gottlob, G., & Mannila, H. (1997). Disjunctive Datalog. ACM Transactions on Database Systems (TODS), 22(3), 364-418.

#############################################################################

Si e' implementato un sistema di gestione di filiera che consente di tracciare un prodotto, identificato tramite un lotto di produzione, all'interno della supply chain. Il lotto di produzione e' stato modellato utilizzando le seguenti proprieta:

#############################################################################

Fase preliminare di studio:

- ricerca background
- studio sui costi delle blockchain
  - valutazioni
- valutazioni di sicurezza
  - blockchain
  - digital twin

Fase progettuale:

- analisi dei requisiti

  - requisiti funzionali/non funzionali
  - casi d'uso definizione
  - casi d'uso UML

- disegno dell'architettura software nel suo complesso

  - definizione dello stack tecnologico e motivazioni
  - definizione della parte chain
  - definizione della parte dt

- modellazione dei dati e disegno della base di dati
  - definizione delle proprieta' di un singolo lotto che si vuole tracciare e scrivere sulla chan

Fase implementativa:

- Stozzi di codice commentati
- Dettaglio utilizzo Azure DT
- Dettaglio utilizzo Web3j
- Dettaglio utilizzo Clingo (Potassco)

#############################################################################

Requisiti:

- Ogni utente registrato opera per conto di una compagnia
- Un utente registrato deve poter creare tipologie di prodotto, indicando un template di ricetta e di processo produttivo
- Un utente registrato deve poter creare un nuovo lotto di prodotti di una determinata tipologia, con una ricetta, uno o più documenti allegati ed indicando le fasi di produzione di cui si vuole tenere traccia
- Un utente registrato deve poter trasferire (in modo atomico) un lotto di prodotti ad un'altra compagnia
- Un utente registrato deve poter inserire un documento e notarizzarlo
- Un utente registrato deve poter accedere in lettura ai dati di notarizzazione dei documenti caricati
- Un utente registrato deve poter trasferire con accettazione un lotto di prodotti ad un'altra compagnia
- Un utente registrato deve poter rifiutare il trasferimento con accettazione di un lotto trasferito alla compagnia per cui opera
- Un utente registrato deve poter accettare il trasferimento con accettazione di un lotto trasferito alla compagnia per cui opera
- Un utente registrato deve poter annullare il trasferimento con accettazione di un lotto disposto dalla sua compagnia
- Un utente registrato deve poter visualizzare le informazioni di tracciamento relative ai lotti di cui è in possesso la sua compagnia
- Un utente registrato deve poter avviare una spedizione per i trasferimenti conclusi e avviati dalla sua compagnia
- Un utente registrato deve poter visualizzare informazioni telemetriche delle spedizioni in corso
- Un utente non registrato deve poter visualizzare le informazioni pubbliche di tracciamento di un lotto di produzione

Casi d'uso:

nome,
gli attori principali e secondari,
un obiettivo (il motivo per il quale gli attori principali avviano il caso d'uso),
la precondizione nella quale è eseguibile,
la sequenza delle azioni svolte dagli attori e dal sistema (considerato come una scatola nera, quindi senza entrare nel dettaglio del suo funzionamento interno),
le eventuali eccezioni e come esse devono essere gestite.

- CU1: Registrazione di un tipo di prodotto
- CU2: Registrazione di un lotto prodotto
- CU3: Tracciamento di un lotto (utente registrato)
- CU4: Tracciamento di un lotto (utente non registrato)
- CU5: Trasferimento di un lotto (atomico) [INCLUDE trasferimento]
- CU6: Trasferimento di un lotto (con accettazione) [INCLUDE trasferimento]
- CU7: Accettazione di un trasferimento
- CU8: Ritiro di un trasferimento
- CU9: Rifiuto di un trasferimento
- CU10: Notarizzazione di un documento
- CU11: Avvio di una spedizione
- CU12: Registrazione di un utente
- CU13: Login di un utente

Specifiche dei casi d'uso:

La specifica è composta da:

- attori
- **precondizioni**: condizioni che devono essere vere prima che il caso d’uso si possa eseguire
- **sequenza degli eventi**: i passi che compongono il caso d’uso
- **postcondizioni**: condizioni che devono essere vere quando il caso d’uso termina l’esecuzione

### Registrazione di un tipo di prodotto

- Nome: CU1: Registrazione di un tipo di prodotto
- Attori: Utente registrato
- Precondizioni: Utente autenticato in piattaforma
- Sequenza degli eventi:
  - Il caso d'uso inizia quando l'utente clicca su "crea nuovo tipo di prodotto"
  - L'utente inserisce un nome
  - Se l'utente clicca su "aggiungi ricetta"
    - Il sistema avvia il caso d'uso CU1a
  - Se l'utente clicca su salva
    - il sistema salva il nuovo tipo di prodotto
  - Se l'utente clicca su cancella
    - il sistema annulla l'inserimento e non salva nessun dato
- Postcondizioni: --

### Creazione di una ricetta

- Nome: CU1a: Creazione di una ricetta (estende CU1 Registrazione di un tipo di prodotto)
- Attori: Utente registrato
- Precondizioni: Utente autenticato in piattaforma
- Sequenza degli eventi:
  - Il caso d'uso inizia quando l'utente clicca su "aggiungi ricetta" durante la creazione di un tipo di prodotto
  - L'utente seleziona un tipo di prodotto tra quelli già registrati e la quantità percentuale desiderata.
  - Il sistema memoriza temporaneamente i dati inseriti
  - Il sistema consente di inserire un nuovo item di ricetta
  - Il sistema consente di rimuovere ogni item inserito
  - Se l'utente clicca su aggiungi item
    - vai al passo 2
  - Se l'utente clicca su rimuovi item
    - il sistema elimina l'item selezionato
    - vai al passo 1
  - Se l'utente clicca su cancella
    - il sistema annulla l'inserimento della ricetta, termina il caso d'uso e prosegue il caso d'uso CU1
  - Se l'utente clicca su salva
    - il sistema salva temporaneamente la ricetta, termina il caso d'uso e prosegue il caso d'uso CU1
- Postcondizioni:
  - Se l'utente ha cliccato su salva la ricetta è stata salva correttamente nei dati temporanei del caso d'uso CU1

### Registrazione di un lotto prodotto

- Nome: CU2: Registrazione di un lotto prodotto
- Attori: Utente registrato
- Precondizioni: L'utente è autenticato in piattaforma e la compagnia per cui opera l'utente ha almeno un tipo di prodotto registrato.
- Sequenza degli eventi:

  - Il caso d'uso inizia quando l'utente clicca su "Inserisci nuovo lotto di prodotto"
  - L'utente inserisce identificiativo, descrizione e quantità del lotto
  - L'utente seleziona il tipo di prodotto tra i tipi di prodotto giaà inseriti in piattaforma
  - Il sistema carica il processo produttivo del tipo di prodotto selezionato.
  - L'utente può selezionare uno o più step del processo produttivo del tipo selezionato
  - Se il tipo di prodotto ha una ricetta
    - il sistema consente all'utente di selezionare uno o più item di ricetta da inserire nella ricetta specifica del lotto che si sta creando
  - L'utente inserisce le coordinate geografiche della produduzione
  - Se l'utente clicca su salva
    - Se il prodotto ha una ricetta
      - Il sistema verifica che siano presenti sufficienti risorse per la creazione dell lotto (ASP)
        - Se le risorse non sono sufficienti
          - Il sistema notifica l'utente
        - Se le risorse sono sufficienti
          - Il sistema seleziona i lotti di materie prime da utilizzare nella creazione del lotto (ASP) (usando la ricetta)
          - Il sistema aggiorna le quantità dei lotti selezionati (usando la ricetta)
          - Il sistema crea il lotto di prodotto
          - Il sistema notarizza la creazione del lotto di prodotto

- Postcondizioni:
  - Il lotto di prodotto è stato creato
  - Le quantità delle materie prime sono state correttamente aggiornate
  - La creazione del lotto di prodotto è stata notarizzata
  - L'utente può visualizzare la notarizzazione nell'elenco notarizzazioni

### Tracciamento di un lotto (utente registrato)

- Nome: CU3: Tracciamento di un lotto (utente registrato)
- Attori: Utente registrato
- Precondizioni: L'utente è autenticato in piattaforma e la compagnia per cui opera l'utente ha almeno un lotto di prodotto registrato.
- Sequenza degli eventi:
  - Il caso d'uso inizia quando l'utente clicca su "Traccia" di un lotto di produzione
  - Il sistema fornisce informazioni di tracciamento combinando la lista di materie prime, i singoli processi di ogni materia prima e le informazioni di notarizzazione con processi e notarizzazione del prodotto principale
  - Se l'utente seleziona Notarizza in uno qualunque degli step visibili inizia il caso d'uso CU3a
- Postcondizioni: --

### Notarizzazione di uno step di processo

- Nome: CU3a: Notarizzazione di uno step di processo (Estende: CU3 Tracciamento di un lotto (utente registrato))
- Attori: Utente registrato
- Precondizioni: L'utente è autenticato in piattaforma, la compagnia per cui opera l'utente ha almeno un lotto di prodotto registrato e l'utente sta visualizzando le informazioni di tracciamento del lotto di produzione prodotto dalla compagnia
- Sequenza degli eventi:
  - Il caso d'uso inizia quando l'utente seleziona "Notarizza" di uno step visualizzato nel tracciamento di un lotto di produzione (CU3)
  - Il sistema salva su un registro immutabile una descrizione dello step
  - Il sistema attende l'operazione di salvataggio
  - Il sistema allega allo step che si sta notarizzando un riferimento univoco all'operazione di salvataggio nel registro immutabile
- Postcondizioni:
  - L'identificativo unicovo è visualizzato in CU3

### Tracciamento di un lotto (utente non registrato)

- Nome: CU4: Tracciamento di un lotto (utente non registrato)
- Attori:
- Precondizioni:
- Sequenza degli eventi:
- Postcondizioni:

### Trasferimento di un lotto (atomico) [INCLUDE trasferimento]

- Nome: CU5: Trasferimento di un lotto (atomico) [INCLUDE trasferimento]
- Attori: Utente registrato che opera per conto di una compagnia A
- Precondizioni:
  - L'utente è autenticato in piattaforma
  - Esiste una compagnia B registrata in piattaforma
  - La compagnia A ha un lotto di prodotto registrato
- Sequenza degli eventi:
  - Il caso d'uso inizia quando l'utente seleziona un lotto e clicca su "Trasferisci"
  - L'utente seleziona la compagnia destinataria, la quantità di prodotto da inviare e sceglie il tipo di trasferimento atomico
  - Il sistema controlla che i dati inseriti siano corretti
  - Se la quantità di prodotto è sufficiente allora C6x
  - Se la quantità di prodotto non è sufficiente
    - Il sistema avvisa l'utente
    - Il caso d'uso termina
- Postcondizioni:
  - Se C6x
    - Il lotto creato è visibile alla compagnia destinataria
    - Il lotto di partenza ha la quantità aggiornata
  - Altrimenti
    - Il lotto di partenza non ha subito variazioni
    - La compagnia destinataria non visualizza nuovi lotti
- Flusso alternativo:
  - Il caso d'uso può ripetersi finchè l'utente non seleziona una quantità di risorse che ha effettivamente a disposizione

### Trasferimento

- Nome: CU6x: Trasferimento
- Attori: --
- Precondizioni: --
- Sequenza degli eventi:
  - Il caso d'uso inizia quando al sistema arriva una richiesta di trasferimento di un lotto o porzione di esso dal proprietario attuale verso un nuovo proprietario
  - Il sistema crea un nuovo lotto a partere dalle informazioni del lotto originale e lo assegna alla compagnia destinataria
  - Il sistema aggiorna la quantità di prodotto nel lotto originale
  - Il sistema rende immutabile il trasferimento (notarizzazione)
  - Il sistema allega la notarizzazione al trasferimento
- Postcondizioni: --

### Trasferimento di un lotto (con accettazione) [INCLUDE trasferimento]

- Nome: CU6: Trasferimento di un lotto (con accettazione) [INCLUDE trasferimento]
- Attori:
  - Utente A registrato che opera per conto di una compagnia A
  - Utente B registrato che opera per conto di una compagnia B
- Precondizioni:
  - L'utente A è autenticato in piattaforma
  - L'utente B è autenticato in piattaforma
  - La compagnia A ha un lotto di prodotto registrato
- Sequenza degli eventi:
  - Il caso d'uso inizia quando l'utente seleziona un lotto e clicca su "Trasferisci"
  - L'utente seleziona la compagnia destinataria, la quantità di prodotto da inviare e sceglie il tipo di trasferimento con accettazione
  - Il sistema controlla che i dati inseriti siano corretti
  - Se la quantità di prodotto non è sufficiente
    - Il sistema avvisa l'utente
  - Se la quantità di prodotto è sufficiente
    - Il sistema blocca la risorsa da trasferire
    - Il sistema notifica all'utente B la presenza di un trasferimento in ingresso (casi d'uso CU7 e CU9)
    - Il sistema rende annullabile trasferimento da parte dell'utente A (caso d'uso CU8)
    - Se si verifica CU7 allora CU6x
    - Se si verifica CU9 o CU8
      - Il sistema ripristina la quantità di prodotto bloccata nel lotto originale
      - Il caso d'uso termina
- Postcondizioni:
  - Se C6x
    - Il lotto creato è visibile alla compagnia destinataria
    - Il lotto di partenza ha la quantità aggiornata
  - Altrimenti
    - Il lotto di partenza non ha subito variazioni
    - La compagnia destinataria non visualizza nuovi lotti

### Accettazione di un trasferimento

- Nome: CU7: Accettazione di un trasferimento
- Attori: Utente registrato che opera per conto della compagnia destinataria del trasferimento
- Precondizioni: La compagnia per cui opera l'utente registrato ha un trasferimento in ingresso di tipo "con accettazione"
- Sequenza degli eventi:
  - Il caso d'uso inizia quando l'utente visualizza la lista di trasferimenti della compagnia per cui opera
  - L'utente seleziona accetta un trasferimento di tipo con accettazione e
  - il sistema notifica l'accettazione alla compangnia mittente
- Postcondizioni:
  - Inizia CU6x

### Ritiro di un trasferimento

- Nome: CU8: Ritiro di un trasferimento
- Attori: Utente registrato che opera per conto della compagnia mittente del trasferimento
- Precondizioni:
  - La compagnia per cui opera l'utente registrato ha un trasferimento in ingresso di tipo "con accettazione"
  - Il trasferimento non è stato ancora accettato dalla compagnia desctinataria
- Sequenza degli eventi:
  - Il caso d'uso inizia quando l'utente visualizza la lista di trasferimenti della compagnia per cui opera
  - L'utente seleziona Ritira un trasferimento di tipo con accettazione
  - Il sistema notifica il ritiro alla compangnia destinataria
  - Il sistema aggiorna la quantità del lotto originario
- Postcondizioni: --

### Rifiuto di un trasferimento

- Nome: CU9: Rifiuto di un trasferimento
- Attori: Utente registrato che opera per conto della compagnia destinataria del trasferimento
- Precondizioni: La compagnia per cui opera l'utente registrato ha un trasferimento in ingresso di tipo "con accettazione"
- Sequenza degli eventi:
  - Il caso d'uso inizia quando l'utente visualizza la lista di trasferimenti della compagnia per cui opera
  - L'utente seleziona rifiuta un trasferimento di tipo con accettazione e
  - il sistema notifica il rifiuto alla compagnia mittente
  - Il sistema aggiorna la quantità del lotto originario
- Postcondizioni: --

### Notarizzazione di un documento

- Nome: CU10: Notarizzazione di un documento
- Attori: Utente registrato in piattaforma
- Precondizioni: Utente autenticato e in possesso di un documento in formato pdf o jpg/png da notarizzare
- Sequenza degli eventi:
  - Il caso d'uso inizia quando l'utente seleziona "Carica documento"
  - L'utente sceglie dal suo dispositivo un documento nei formati consentiti (pdf, jpg o png)
  - L'utente clicca su carica
  - Il sistema effettua l'upload della risorsa
  - Il sistema salva un'impronta digitale della risorsa all'interno del registro immutabile
  - Il sistema memorizza un riferimento univoco al salvataggio del punto precedente
  - Il sistema restituisce un indirizzo web per la verifica della transazione sul registro immutabile
- Postcondizioni:
  - La risorsa è disponibile per il download
  - L'operazione di notarizzazione è pubblicamente verificabile

### Avvio di una spedizione

- Nome: CU11: Avvio di una spedizione
- Attori: Utente registrato in piattaforma
- Precondizioni: Utente autenticato in piattaforma, esiste una transazione completata (accettata o atomica) avviata dalla compagnia per cui opera l'utente e non è stato ancora disposto il trasferimento per la transazione
- Sequenza degli eventi:
  - Il caso d'uso inizia quando l'utente visualizza la lista delle transazioni
  - Se l'utente seleziona una transazione completata e clicca su "Invia"
    - Il sistema cerca un camion disponibile
    - Se ci sono camion disponibili
      - il sistema associa il camion al lotto oggetto della transazione
      - il sistema aggiorna periodicamente le letture dei sensori associati al camion
      - il sistema salva in modo immutabile le letture dei sensori
    - Se non ci sono camion disponibili
      - Il sistema notifica un errore
- Postcondizioni:
  - Si può vedere la lista delle letture dei sensori associati al camion

--------------------------------- latex qui ----------------------------------
https://it.overleaf.com/project/66939ee034c12d8cf27fcb89

Diagramma dei casi d'uso:

- DISEGNARE (fallo bene, includi le relazioni tipo estende/include)
  - Include: "Ritiro fondi" include "Aggiorna saldo" (è obbligatorio e si fa sempre, ogni volta che si ritirano fondi)
  - Estende: "Calcola bonus" estende "Deposita fondi" (è facoltatito e si attiva solo con determinate condizioni - es. deposito oltre i 10000€ o età oltre i 65)

###############################################################################

- disegno dell'architettura software nel suo complesso
  - definizione dello stack tecnologico e motivazioni
  - definizione della parte chain
  - definizione della parte dt

Il software presenta un'architettura client-server con un componente principale, il server, ed un componente secondario, il client. Il client è un client non corrisponde del tutto alla definizione di client stupido. Si avvicina infatti più alla definizione di fat client.
Il sistema implementa un'architettura a tre livelli in cui il livello di presentazione è stratificato. I livelli di un'applicazione a tre tier sono:

- Livello di presentazione
- Livello applicazione (business logic)
- Livello dati

(immagine architettura)
(descrizione dei livelli)

Si è deciso di utilizzare una struttura monolitica e non a microservizi.
La comunicazione tra componente server e componente client avviene tramite RestAPI. La costruzione di api rest implica alcuni vincoli architeturali (https://restfulapi.net/rest-architectural-constraints/)

- Bisogna definire un'interfaccia uniforme
- Avere un'architettura client server in cui un componente chiede risorse (client) e un altro le espone (server)
- Il server è senza stato ovvero il server non ha memoria delle connessioni precedenti
- Per le richieste in lettura si può prevedere un meccanismo di cache in cui il server propone gli stessi contenuti. Bisogna prevedere anche criteri di aggiornamento e invalidamento dei contenuti in cache.
- Implementare un sistema a livelli, in cui il client si connette ad un servizio senza conoscere l'architettura interna
- Codice on demand (opzionale) quando necessario, oltre alle risorse statiche, è possibile inserire del codice eseguibile nella risposta del server

L'autenticazione utilizza il protocollo Oauth2 ed un'istanza di Keycloak come autentication server.
(https://datatracker.ietf.org/doc/html/rfc6749)

![alt text](image-1.png)

Lo stack tecnologico utilizzato comprende
Sono stati presi in esame diversi framework per la componente server. In ambito NodeJS sono stati presi in considerazione NestJS ed Express.

Descrizione di Nest

Nest (NestJS) is a framework for building efficient, scalable Node.js server-side applications. It uses progressive JavaScript, is built with and fully supports TypeScript (yet still enables developers to code in pure JavaScript) and combines elements of OOP (Object Oriented Programming), FP (Functional Programming), and FRP (Functional Reactive Programming).

[...]

In recent years, thanks to Node.js, JavaScript has become the "lingua franca" of the web for both front and backend applications. This has given rise to awesome projects like Angular, React and Vue, which improve developer productivity and enable the creation of fast, testable, and extensible frontend applications. However, while plenty of superb libraries, helpers, and tools exist for Node (and server-side JavaScript), none of them effectively solve the main problem of - Architecture.

Nest provides an out-of-the-box application architecture which allows developers and teams to create highly testable, scalable, loosely coupled, and easily maintainable applications. The architecture is heavily inspired by Angular.
https://docs.nestjs.com/

Descrizione di Express

Express è un framework per applicazioni web Node.js flessibile e leggero che fornisce una serie di funzioni avanzate per le applicazioni web e per dispositivi mobili. Con una miriade di metodi di utilità HTTP e middleware a disposizione, la creazione di un'API affidabile è un processo facile e veloce. Express fornisce uno strato sottile di funzionalità di base per le applicazioni web, senza nascondere le funzioni Node.js. da https://expressjs.com/it/

Descrizione di Spring e Spring Boot

Spring è un framework java. Spring Boot è una sua estensione. Tipicamente si utilizzano insieme, è ormai difficile immaginare di usare Spring senza Spring Boot. Spring si basa sull'inversione del controllo e sull'injection delle dipendenze. Il principio di design di inversione del controllo, IoC, descrive quelle situazioni in cui un componente software applicativo riceve il controllo da parte di un componente di libreria e non il contrario come avviene in una programmazione procedurare tradizionale in cui il software applicativo utilizza (e controlla) le componenti di libreria. In questo contesto si inserisce l'iniezione delle dipendenze che è un altro aspetto fondamentale del framework Spring, che elimina dall'applicazione ogni logica di inizializzazione. E' il framework stesso che quando necessario inietta un riferimento all'implementazione del servizio richiesto.
https://it.wikipedia.org/wiki/Inversione_del_controllo

Spring Boot crea applicazioni Spring standalone, con un application server embedded a scelta tra Tomcat o Jetty.
Spring Boot semplifica il processo di sviluppo perché abbraccia lo stile Convention-over-configuration ed utilizza configurazioni standard la dove il programmatore non ha definito una configurazione personalizzata per quel particolare aspetto del software in svilupo. Esistono difatti moduli di spring boot che fanno da "starter" per i diversi moduli di spring. La configurazione, quando è necessaria, non deve in ogni caso essere redatta in XML. https://en.wikipedia.org/wiki/Spring_Boot

Scelta del framework e considerazioni personali

Express.JS si è rivelato eccessivamente granulare e di basso livello per gli scopi del progetto. NestJS e Spring, sebbene molto simili tra di loro, soprattutto su alcuni aspetti di desing quali l'inversione del controllo e l'injection delle dipendenze, che sono concetti che si possono ritrovare in entrambi i framework, utilizzano due ecosistemi differenti, il primo NodeJS ed il secondo Java. Si è scelto di utilizzare Java per poter utilizzare a pieno i vantaggi di una programmazione orientata agli oggetti in un ambiente multithread. NodeJs ha una natura asincrona e dalla documentazione di node.js si legge https://nodejs.org/en/learn/asynchronous-work/dont-block-the-event-loop#worker-pool-conclusions

In a one-thread-per-client system like Apache, each pending client is assigned its own thread. If a thread handling one client blocks, the operating system will interrupt it and give another client a turn. The operating system thus ensures that clients that require a small amount of work are not penalized by clients that require more work.

Because Node.js handles many clients with few threads, if a thread blocks handling one client's request, then pending client requests may not get a turn until the thread finishes its callback or task. The fair treatment of clients is thus the responsibility of your application. This means that you shouldn't do too much work for any client in any single callback or task.

This is part of why Node.js can scale well, but it also means that you are responsible for ensuring fair scheduling. The next sections talk about how to ensure fair scheduling for the Event Loop and for the Worker Pool.

Il server utilizza il pattern repository ed altri pattern in maniera più o meno esplicita (vedi https://www.baeldung.com/spring-programmatic-transaction-management#transaction-template)

Gestione delle transazioni
Una transazione è un'operazione atomica che avviene all'interno di un'applicazione server o su una base di dati. Tipicamente le transazioni hanno

Altro aspetto importante è Hibernate

LATO AUTENTICAZIONE

LATO CLIENT

I framework lato client presi in esame sono Vue.js, Angular e React.
I dati di diffusione di questi framework li pongono ai primi tre posti di una ipotetica classifica basata sull'utilizzo misurato in numero di repository pubblici. Se si guarda all'utilizzo al primo posto troviamo React seguito da Vue e poi da Angular. Riguardo l'awareness (conoscenza) invece Angular passsa in prima posizione, Vue mantiene il secondo posto e React scivola in terza posizione. (fonte https://2023.stateofjs.com/en-US/libraries/front-end-frameworks/).

Vue.js

Vue (pronounced /vjuː/, like view) is a JavaScript framework for building user interfaces. It builds on top of standard HTML, CSS, and JavaScript and provides a declarative, component-based programming model that helps you efficiently develop user interfaces of any complexity. [...] Vue is a framework and ecosystem that covers most of the common features needed in frontend development. But the web is extremely diverse - the things we build on the web may vary drastically in form and scale. With that in mind, Vue is designed to be flexible and incrementally adoptable. [...] Despite the flexibility, the core knowledge about how Vue works is shared across all these use cases. Even if you are just a beginner now, the knowledge gained along the way will stay useful as you grow to tackle more ambitious goals in the future. If you are a veteran, you can pick the optimal way to leverage Vue based on the problems you are trying to solve, while retaining the same productivity. This is why we call Vue "The Progressive Framework": it's a framework that can grow with you and adapt to your needs. https://vuejs.org/guide/introduction.html#the-progressive-framework

React

React (noto anche come React.js o ReactJS) è una libreria open-source, front-end, JavaScript per la creazione di interfacce utente. È mantenuto da Meta (già Facebook) e da una comunità di singoli sviluppatori e aziende.
React può essere utilizzato come base nello sviluppo di applicazioni a pagina singola ma è utilizzabile anche su mobile tramite React Native, una libreria sempre sviluppata da Meta che tramuta i componenti React in componenti nativi (iOS e Android). Tuttavia, React si occupa solo del rendering dei dati sul DOM, pertanto la creazione di applicazioni React richiede generalmente l'uso di librerie aggiuntive per lo state management e il routing. Redux e React Router sono i rispettivi esempi di tali librerie. A questo fine è possibile utilizzare anche dei framework terzi, come ad esempio Next.js. https://it.wikipedia.org/wiki/React_(web_framework)

Angular.JS

Angular is a web framework that empowers developers to build fast, reliable applications. Maintained by a dedicated team at Google, Angular provides a broad suite of tools, APIs, and libraries to simplify and streamline your development workflow. Angular gives you a solid platform on which to build fast, reliable applications that scale with both the size of your team and the size of your codebase. https://angular.dev/overview

Scelta e motivazioni

LATO CHAIN

Web3j

Affinchè la piattaforma supporti pienamente le operazioni di lettura e scrittura su un registro distribuito è necessario utilizzare alcuni componenti software aggiuntivi. Nello stack tecnlogico adottato è posisbile inserire a questo proposito il progetto Web3j, una libreria java che supporta gli SmartContract e consente di interagire con la rete Etherium tramite un client JSON-RPC, utilizzando chiamate API su protocollo HTTP o IPC. https://docs.web3j.io/4.8.7/quickstart/

![alt text](image-2.png)

https://docs.web3j.io/4.8.7/img/web3j_network.png

Essendo Java un linguaggio fortemente tipizzato necessita di un tipo, di una classe, che rappresenti lo smart contract per poter essere utilizzato all'interno dell'applicazione. La libreria offre un tool a riga di comando che consente la generazione automatica di un wrapper, una classe java, per ogni contratto scritto in solidity che si vuole utilizzare.

Benchè Web3j supporti ogni aspetto del ciclo di vita degli smart contract, si è deciso di adottare una soluzione software differenze per la distribuzione, il testing e la verifica dei contratti, creando di fatto un modulo separato e isolato dal resto dell'applicazione e lasciando alla parte Spring-Web3j solo la responsabilità dell'utilizzo vero e proprio dei contratti.

Il modulo di creazione dei contratti risiede in ambiente NodeJS ed utilizza il framework HardHat.js.

Hardhat è un framework modulare composto da diverse parti tra loro interconnesse ma comunque autonome. Nella documentazione del progetto si definisce il framework come un ambiente di sviluppo (Hardhat is a development environment for Ethereum software. It consists of different components for editing, compiling, debugging and deploying your smart contracts and dApps, all of which work together to create a complete development environment.) https://hardhat.org/hardhat-runner/docs/getting-started#overview
Le parti principali che compongono l'ambiente di sviluppo sono:

- runner: è il componente principale di hardhat, consente di compilare, distribuire, testare e verificare gli smart contract
- ignition: consente di utilizzare un approccio dichiarativo alla gestione degli smart contract
- net: una rete locale di test
- estensione per visual studio code

Degna di nota è sicuramente la parte di testing degli smart contract che attraverso l'integrazione di librerie ben note in ambito Test Drive Development (TDD)su NodeJs quali Mocha https://mochajs.org/ e Chai https://www.chaijs.com/ consente un rapido sviluppo di smart contract sicuri e testati.
I contratti utilizzati in piattaforma sono stati sviluppati seguendo un approccio TDD, raggiungendo un valore di coverage superiore al 90%.

Una delle motivazioni principali che hanno spinto verso questa scelta di separazione è stata il pieno supporto di questo altro stack tecnologico alla libreria OpenZeppelin, la cui adozione riveste un'importante scelta in ambito di sicurezza degli smart contract. Basti pensare che uno smart contract che non adotta strategie difensive di alcun tipo può essere utilizzato da chiunque ne conosca l'indirizzo (pubblico!). Questo sicuramente non è un comportamento voluto o desiderato dalla maggior parte dei contesti applicativi. Un primo rimedio che offre OpenZeppelin è la super classe Owanable che se implementata consente di porre vincoli sull'utente che può effettivamente utilizzare il contratto, anche se ne conosce l'indirizzo. Esistono livelli più avanzati di gestione degli accessi e dei ruoli ed OpenZeppelin offre supporto anche a questi casi d'uso.

LATO DIGITAL TWIN

I gemelli digitali utilizzati nel progetto sono stati implementati sfruttando la piattaforma Digital Twin di Microsoft Azure. Questo servizio è una piattaforme a servizi (PaaS) che consente la gestione di gemelli digitali sia autonomi che eventualmente integrati con altri servizi, non necessariamente limitati al contesto Azure, poichè vengono esposti degli endpoint che, al netto di un'eventuale stretta sulle politiche di accesso definita dall'utente, sono pubblici ed implementano lo standard RESTAPI.
L'integrazione e l'utilizzo dei gemelli digitali in una applicazione Java necessita di una componente software specializzata. In questo caso ci si è diretti verso il Software Development Kit (SDK) fornito da Microsoft Azure.
L'SDK fornisce un approccio modulare consentendo l'adozione solo delle parti necessarie ed in particolare si è utilizzato il modulo "Azure IoT Digital Twins client library for Java".

LATO ASP

Il modulo di answer set programming utilizza, tramite un wrapper non ufficiale open source, il pacchetto clingo di Potasco https://github.com/potassco/clingo che rappresenta una soluzione completa all'esecuzione di programmi ASP perché combina insieme entrambe le componenti necessarie ad una esecuzione corretta di un programma ASP, ovvero il grounder gringo che il solutore clasp.

Ad un livello precedente troviamo un servizio middleware costruito ad hoc che prepara le strutture dati Java in programmi eseguibili da clingo, quindi compie un operazione di serializzazione.

L'output di clingo è poi opportunamente gestito da un altra porzione del servizio middleware che si occupa della deserializzazione della stringa che rappresenta l'output.

################################################################################

- modellazione dei dati e disegno della base di dati
  - definizione delle proprietà di un singolo lotto che si vuole tracciare e scrivere sulla chan

La piattaforma che si sta sviluppando utilizza un sistema di gestione della persistenza che è di tipo relazionale. Nello specifico utilizza il database managment system MariaDB con Hibernate come strato di comunicazione con l'applicazione. Hibernate implementa le API Java Persistence (JPA).

Hibernate è un ORM, un Object/Relational Mapping, un modulo software che gestisce la persistenza in contesti in cui si utilizza una base di dati relazionale.

Hibernate not only takes care of the mapping from Java classes to database tables (and from Java data types to SQL data types), but also provides data query and retrieval facilities. It can significantly reduce development time otherwise spent with manual data handling in SQL and JDBC. Hibernate’s design goal is to relieve the developer from 95% of common data persistence-related programming tasks by eliminating the need for manual, hand-crafted data processing using SQL and JDBC. However, unlike many other persistence solutions, Hibernate does not hide the power of SQL from you and guarantees that your investment in relational technology and knowledge is as valid as always. https://docs.jboss.org/hibernate/stable/orm/userguide/html_single/Hibernate_User_Guide.html

![alt text](image-3.png)

https://docs.jboss.org/hibernate/stable/orm/userguide/html_single/Hibernate_User_Guide.html#architecture

La definizione di un modello di dominio a partire dai concetti principali che caratterizzano il modello di business individuato per l'applicazione.
Sono stati individuati i seguenti concetti propri dell'ambito applicativo.

Entità e attributi:

- Batch

  - Descrizione: Il lotto rappresenta un'unità di produzione di un particolare tipo di prodotto, in una determinata quantità.
  - Attributi: id, batchId, quantity, isFinal, productionDate, processType

- ProductType

  - Descrizione: Il tipo di prodotto è un generico prodotto inteso come l'astrazione, la generalizzazione, di lotti di produzione.
  - Attributi: id, name, unity, state

- Recipe (di ProductType)

  - Descrizione: Lista di ingredienti che compongono in generico prodotto. E' il modello da utilizzare quando si materializza la ricetta vera e propria durante la produzione di un lotto.
  - Attributi: id, note, recipeRow

- RecipeBatch (di Batch)

  - Descrizione: Lista di ingredienti effettivamente utilizzati nella produzione di un lotto di produzione.
  - Attributi: id, note, recipeRow

- ProductionProcess (di ProductType)

  - Descrizione: Lista di passi da seguire durante il processo produttivo del tipo cui l'entità si riferisce
  - Attributi: id, note, steps

- ProductionProcessBatch (di Batch)

  - Descrizione: Lista di passi da seguire durante il processo produttivo del tipo cui l'entità si riferisce
  - Attributi: id, note, steps

- Document

  - Descrizione: Risorsa che rappresenta un certificato, un'analisi o una generica attestazione da legare al lotto di produzione
  - Attributi: id, title, description, link, path

- Notarize

  - Descrizione: Entità che modella il concetto di notarizzazione di una qualunque risorsa all'interno della piattaforma. Consente di raccogliere insieme una o più transazioni reali sulla blockchain che hanno un legame logico tra di loro.
  - Attributi: id, hash, notarizedAt, data

- Transfer

  - Descrizione: Rappresentazione del trasferimento di un Batch o porzione di esso tra due Company registrate in piattaforma, si in modo atomico che con accettazione.
  - Attributi: id, type, status, oldBatchID, newBatchID, quantity, unity, companySenderID, companySenderUsername, companyRecipientID, companyRecipientUsername, transferDateStart, lastUpdate

- Transport

  - Descrizione:
  - Attributi: id, truckId, batchId, dateStart, dateEnd, location, companyFrom, companyTo

- Truck:

  - Descrizione: Modellazione del mezzo fisico (camion) che effettua il trasporto, ipotetico luogo dove si collocano i sensori che si vuole utilizzare
  - Attributi: id, lastSensorsUpdate

- UserInfo

  - Descrizione: Entità che rappresenta l'utente in piattaforma. Ha un riferimento al'auth server di Oauth2s.
  - Attributi: id, username, keycloakUsername, keycloakId, email, firstName, lastName

- Company

  - Descrizione: Modellazione dell'azienda che produce lotto di prodotti e che effettua operazioni su di essi in piattaforma.
  - Attributi: id, name

- ChainTransaction

  - Descrizione: Rappresenta la transazione sulla blockchain
  - Attributi: id, txId

- Location
  - Descrizione:
  - Attributi:

Relazioni:

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

Le entità principali che si sono individuate sono le seguenti:

--------- UML ------------------

--------- SCHEMA ENTITA-RELAZIONE ------------------

--------- SCHEMA classi ------------------

TRANSAZIONI HIBERNATE e TRANSAZIONI DI SPRING

https://docs.jboss.org/hibernate/stable/orm/userguide/html_single/Hibernate_User_Guide.html#transactions

################################################################################

https://it.overleaf.com/project/66939ee034c12d8cf27fcb89
https://www.getbibtex.com/

################################################################################


- implementativo
  - server
    - stozzi di codice commentato
    - elenco delle api


  - client
    - stozzi di codice commentato
    - screen sull'interfaccia

################################################################################
\item \texttt{GET /api/v1/company/batch}
\item \texttt{POST /api/v1/company/batch}
\item \texttt{GET /api/v1/company/batch/{{id}}}
\item \texttt{GET /api/v1/company/batch/{{id}}/track}
\item \texttt{GET /api/v1/company/product-type}
\item \texttt{POST /api/v1/company/product-type}
\item \texttt{GET /api/v1/company/client}
\item \texttt{GET /api/v1/company/transfer}
\item \texttt{POST /api/v1/company/transfer}
\item \texttt{GET /api/v1/company/transfer/{{batch_id}}}
\item \texttt{GET /api/v1/company/transfer/{{trans_id}}/abort}
\item \texttt{GET /api/v1/company/transfer/{{trans_id}}/accept}
\item \texttt{GET /api/v1/company/transfer/{{trans_id}}/reject}
\item \texttt{GET /api/v1/company/doc}
\item \texttt{GET /api/v1/company/doc/notarize/{{doc_id}}}
\item \texttt{POST /api/v1/company/doc/upload}
\item \texttt{GET /api/v1/company/doc/{{doc_id}}}
\item \texttt{GET /api/v1/company/doc/{{doc_id}}/resource}
\item \texttt{POST /api/v1/public/doc/check/{{hash}}}
\item \texttt{GET /api/v1/company/transport}
\item \texttt{POST /api/v1/company/transport}
\item \texttt{GET /api/v1/company/transport/{{batch_id}}}
\item \texttt{GET /api/v1/company/transport/{{transport_id}}/truck}
\item \texttt{GET /api/v1/company/truck}
\item \texttt{GET /api/v1/company/truck/update}
\item \texttt{GET /api/v1/company/notarization}
\item \texttt{POST /api/v1/company/notarization/step/{{step_id}}}
################################################################################
