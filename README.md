# Reddit Big Data
Progetto per il corso di Big Data, Università degli Studi di Roma Tre, a cura di:
| Nome| Matricola | E-mail | Profilo Github | Profilo Linkedin |
|:---|:---|:---|:---|:---|
| Simone Chilosi|522155|sim.chilosi@stud.uniroma3.it|[https://github.com/simochilo](https://github.com/simochilo)| https://www.linkedin.com/in/simone-chilosi-575260239/|
| Samuel Esposito|000000|sam.esposito@stud.uniroma3.it|[https://github.com/samuel-esp](https://github.com/samuel-esp)| [https://www.linkedin.com/in/simone-chilosi-575260239/](https://www.linkedin.com/in/samuel-esposito-016036115/)|

<br><br>
L'obiettivo di questo progetto è stato realizzare ed analizzare un'architettura Big Data, impiegando conoscenze apprese e tecnologie studiate durante il corso.
L'architettura realizzata è la seguente:

![Big Data Architecture](docs/img/big-data-architecture.png)

- **Data source**: come fonte dei dati è stato scelto il forum "Reddit", esso permette agli utenti di pubblicare post relativi agli argomenti più disparati. La piattorma è strutturata in modo tale da separare i macro-argomenti nei cossiddetti "subreddit", ogni subreddit (che ha la sua pagina specifica) si contraddistingue dall'argomento che viene trattato all'interno dei post pubblicati dagli utenti.

- **Data ingestion / Message ingestion**: questa fase è stata gestita attraverso in primo luogo le API ufficiali offerte dalla piattaforma Reddit, successivamente attraverso uno scambio di messaggi Kafka è stato effettuato il salvataggio in locale dei dati recuperati. In questa fase è stato anche eseguito un processo di analisi del sentiment dei dati, dividendo quelli considerati come "postivi" da quelli "negativi".

- **Analytical Data Store**: la tecnologia scelta per memorizzare i dati recuperati è stata MongoDB, un database NoSQL Document-based. Una volta memorizzati, i dati sono subito pronti per la fase di analisi tramite query specifiche.

- **Analytics and Report**: le query di analisi sono state sviluppate utilizzando il linguaggio R, compatibile con MongoDB e molto efficiente. Oltre a ottenere i risultati delle query, R offre la possibilità di costruire in tempo reale dei grafici con i risultati ottenuti, per una loro comprensione più semplice e più immediata.
