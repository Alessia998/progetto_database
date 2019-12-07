Gli strumenti che servono:

- JVM (versione 1.8 dovrebbe andare bene, se non funziona aggiornare almeno a 9)
- Postgres SQL DataBase.

Come inizializzare:

- Creare un server(localhost:5432) usando PgAdmin o un altro tool per postgreSQL database management
- Creare un database sul server ES: project
  IMPORTANTE: - Ricordarsi credenziali dell'Admin. ES: postgres postgres
              - Ricordarsi il nome del database creato. ES: project

Come inizializzare DB:

- Lanciare db_init: java -jar db_init.jar <Nome DB> <username> <password>
                    ES: java -jar db_init.jar project postgres postgres
  usare l'interfaccia CLI con l'opzione 1 per caricare i dati.
  Troubleshooting: Il database potrebbe non andare se si conette con JDBC per prima volta. Quindi connettersi da PgAdmin cliccando semplicemente sul database creato in precedenza.
  
- Lanciare progetto.jar
   java -jar progetto.jar <Nome DB>
   ES: java -jar progetto.jar project
   
   credenziali: Per Admin sono quelli di postgres.
                Per gli altri utenti vedere define_users nella cartella db_init_exec
                username: codice fiscale
                Password: prime 3 lettere del codice fiscale

NOTA: db_init.jar dà anche la possibilità di eliminare tutti gli utenti, tabelle e funzioni presenti nel DB con l'opzione 2.
      Non veranno rimossi gli utenti aggiunti manualmente. l'opzione 2 non funziona se è stato rimosso manualmente almeno 
      uno degli utenti del postgres creati con db_init.
