
# RICCARDO SPANO 449367
# FABRIZIO EGIDDI 489136

## Nome dell'applicazione:
FERS-FORMATION-SERVICE

## Info:

Abbiamo lavorato sempre insieme in sede universitaria per facilitare l'organizzazione ed ottimizzare il tempo, utilizzando GitHub solo per il commit finale del progetto.

## Descrizione del sistema:

Il fers-formation-service è un sistema informativo per la gestione di centri di formazione.
Inizialmente, il sistema viene inizializzato (nel main dell'applicazione) nel seguente modo:

- Viene creata un'azienda di nome "FERS", che è l'oggetto singleton di tutto il contesto.
- Vengono creati due centri di nome "Centro01" (email per ricerca: centro01@gmail.com) e 
  nome "Centro02" (email per ricerca:  centro02@gmail.com)
- Vengono creati due responsabili, che si occupano del "Centro01" e del "Centro02"
- Viene creato un direttore
- Viene creato un allievo di email "bianchi@gmail.com" associato al centro di nome "Centro01" 
- Vengono create due attività di nome "Attività01" ed "Attività02", associate rispettivamente al "Centro01" e a "Centro02"

C'è una pagina index, da dove si arriva ad una pagina di login. La pagina di login differenzia due tipologie di utenti in base ai
dati immessi nella form:

- Responsabile: accedendo come responsabile, si accede al pannello di controllo del responsabile
- Direttore: accedendo come direttore, si accede al panello di controllo del direttore

## Dati per l'accesso:

è possibile fare il login con le seguenti credenziali:

- Modalità direttore
  Username: dir00 
  Password: passdir
  
- Modalità responsabile
  Username: resp01
  Password: pass01
  
  Username: resp02
  Password: pass02
  
## Considerazioni sul sistema:
  
  Il sistema agisce in questo modo:
  
  - L'azienda gestisce un insieme di centri
  - Il generico centro gestisce un insieme di attività ed un insieme di allievi
  - Ogni allievo può iscriversi a due centri diversi utilizzando le stesse credenziali, in quanto l'iscrizione è VOLUTAMENTE resa locale     ovvero il controllo sull'esistenza dell'allievo è svolta al livello del singolo centro e non a livello aziendale.
  
## Casi d'uso:
  
  UC1: RICERCA ALLIEVO - ATTORE PRIMARIO: RESPONSABILE
  1. L'allievo arriva al desk del centro per richiedere informazioni riguardante il proprio profilo
  2. Il responsabile clicca sulla barra di navigazione l'operazione "Ricerca allievo"
  3. Il responsabile inserisce l'email dell'allievo
  4. Il sistema mostra i dati anagrafici e le attività a cui partecipa l'allievo
  5. Il responsabile riferisce all'allievo i dati mostrati dal sistema
  
  UC2: ISCRIVI ALLIEVO AD ATTIVITA' - ATTORE PRIMARIO: RESPONSABILE
  1. L'allievo arriva al desk del centro per richiedere di iscriversi ad una attività
  2. Il responsabile clicca sulla barra di navigazione l'operazione "Iscrivi allievo ad attività"
  3. Il sistema mostra le attività presenti nel centro, con relativi allievi iscritti a quell'attività
  4. Il responsabile inserisce il codice identificativo dell'attività e l'email dell'allievo
  5. Il sistema mostra una pagina della corretta iscrizione all'attività
  6. Il responsabile riferisce l'esito all'allievo
  
  UC3: REGISTRA NUOVO ALLIEVO - ATTORE PRIMARIO: RESPONSABILE
  1. L'allievo arriva al desk del centro per richiedere di iscriversi al centro
  2. Il responsabile clicca sulla barra di navigazione l'operazione "Registra nuovo allievo"
  3. Il responsabile immette i dati anagrifici dell'allievo
  4. Il sistema mostra una pagina della corretta iscrizione all'attività
  5. Il responsabile riferisce l'esito all'allievo
  
  UC4: CREA NUOVA ATTIVITA' - ATTORE PRIMARIO: RESPONSABILE
  1. Il responsabile clicca sulla barra di navigazione l'operazione "Crea nuova attività"
  2. Il responsabile immette le informazioni dell'attività
  3. Il sistema mostra una pagina della corretta iscrizione dell'attività
  
  UC5: RICERCA CENTRO - ATTORE PRIMARIO: DIRETTORE
  1. Il direttore clicca sulla barra di navigazione l'operazione "Ricerca centro"
  2. Il direttore inserisce l'email del centro
  3. Il sistema mostra le informaizioni del centro, le attività del centro e gli allievi iscritti al centro

  UC6: AGGIUNGI CENTRO - ATTORE PRIMARIO: DIRETTORE
  1. Il direttore clicca sulla barra di navigazione l'operazione "Aggiungi centro"
  2. Il direttore immette i dati del centro
  3. Il sistema mostra una pagina della corretta iscrizione del centro

  
 
  
      

  
 
  
      
