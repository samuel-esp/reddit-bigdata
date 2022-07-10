# Introduzione
L'obiettivo di questo progetto è stato realizzare ed analizzare un'architettura Big Data, impiegando conoscenze apprese e tecnologie studiate durante il corso.
L'architettura realizzata è la seguente:

![Big Data Architecture](img/big-data-architecture.png)

- **Data source**: come fonte dei dati è stato scelto il forum "Reddit", esso permette agli utenti di pubblicare post relativi agli argomenti più disparati. La piattorma è strutturata in modo tale da separare i macro-argomenti nei cossiddetti "subreddit", ogni subreddit (che ha la sua pagina specifica) si contraddistingue dall'argomento che viene trattato all'interno dei post pubblicati dagli utenti.

- **Data ingestion / Message ingestion**: questa fase è stata gestita attraverso in primo luogo le API ufficiali offerte dalla piattaforma Reddit, successivamente attraverso uno scambio di messaggi Kafka è stato effettuato il salvataggio in locale dei dati recuperati. In questa fase è stato anche eseguito un processo di analisi del sentiment dei dati, dividendo quelli considerati come "postivi" da quelli "negativi".

- **Analytical Data Store**: la tecnologia scelta per memorizzare i dati recuperati è stata MongoDB, un database NoSQL Document-based. Una volta memorizzati, i dati sono subito pronti per la fase di analisi tramite query specifiche.

- **Analytics and Report**: le query di analisi sono state sviluppate utilizzando il linguaggio R, compatibile con MongoDB e molto efficiente. Oltre a ottenere i risultati delle query, R offre la possibilità di costruire in tempo reale dei grafici con i risultati ottenuti, per una loro comprensione più semplice e più immediata.

<br>

# Data Source
I dati recuperati sono stati relativi ai post di alcuni subreddit, questi subreddit sono stati selezionati in base alla loro popolarità e attività: la piattaforma mette a disposizione un sistema di classifica che segnala quali sono i subreddit più in voga al momento, cioè quelli con maggior affluenza di utenti e nuovi post giornalieri, selezionando questi subreddit in poco tempo si arriva ad avere un buon numero di dati da utilizzare in un'architettura big data. 

I subreddit scelti sono stati:

- [r/python](https://www.reddit.com/r/Python/)
- [r/cscareerquestions](https://www.reddit.com/r/cscareerquestions/)
- [r/news](https://www.reddit.com/r/news/)
- [r/nba](https://www.reddit.com/r/nba/)
- [r/spotify](https://www.reddit.com/r/spotify/)
- [r/jobs](https://www.reddit.com/r/jobs/)
- [r/tennis](https://www.reddit.com/r/tennis/)
- [r/movies](https://www.reddit.com/r/movies/)
- [r/offmychest](https://www.reddit.com/r/offmychest/)
- [r/depression](https://www.reddit.com/r/depression/)
- [r/foreveralone](https://www.reddit.com/r/ForeverAlone/)
- [r/anger](https://www.reddit.com/r/Anger/)
- [r/europe](https://www.reddit.com/r/europe/)
- [r/gaming](https://www.reddit.com/r/gaming/)
- [r/formula1](https://www.reddit.com/r/formula1/)
- [r/todayilearned](https://www.reddit.com/r/todayilearned/)
- [r/marvelstudios](https://www.reddit.com/r/marvelstudios/)
- [r/healtyfood](https://www.reddit.com/r/HealthyFood/)
- [r/politics](https://www.reddit.com/r/politics/)
- [r/askreddit](https://www.reddit.com/r/AskReddit/)
- [r/discordapp](https://www.reddit.com/r/discordapp/)
- [r/twitch](https://www.reddit.com/r/Twitch/)
- [r/tinder](https://www.reddit.com/r/Tinder/)
- [r/techsupport](https://www.reddit.com/r/techsupport/)
- [r/music](https://www.reddit.com/r/Music/)
- [r/android](https://www.reddit.com/r/Android/)
- [r/baseball](https://www.reddit.com/r/baseball/)
- [r/nostupidquestions](https://www.reddit.com/r/NoStupidQuestions/)
- [r/explainlikeimfive](https://www.reddit.com/r/explainlikeimfive/)
- [r/outoftheloop](https://www.reddit.com/r/OutOfTheLoop/)
- [r/instagram](https://www.reddit.com/r/Instagram/)

Questi subreddit appartengono a categorie molto diverse fra loro (news, attualità, sport, intrattenimento, salute, relazioni ecc.), questo perché può essere interessante confrontare realtà diverse e osservare come varia il comportamento e/o il sentimento generale di diverse comunità. Come fonti di dati si sono scelti sempre le pagine già filtrate per post più recenti, in modo da avere sempre nuovi post a disposizione.

<br>

# Data Ingestion / Message Ingestion
## Reddit API
Questa è la fase in cui i dati sono stati attivamente recuperati e salvati in un database locale. La prima fase è stata quella di recuperare i dati dalla piattaforma tramite le [API ufficiali](https://www.reddit.com/dev/api/). Reddit infatti permette agli utenti di scaricare dal sito post tramite delle semplici richieste GET; una volta creato un account apposito e recuperato le sue credenziali necessarie all'autenticazione, è possibile inviare delle richieste ai server di Reddit con alcune limitazioni: massimo 60 richieste al minuto, massimo 100 post per ogni richiesta, token di autenticazione valido per massimo 2 ore. Questi limiti però, almeno per questo tipo di progetto, non sono un problema, infatti con la possibilità di recuperare 6000 post al minuto non ci vuole molto per creare un database dal volume sufficiente per essere analizzato nell'ambito dei Big Data. Lo script di estrazione è stato lanciato più volte ma comunque a distanza di ore/giorni, questo perché anche nei subreddit più attivi con milioni di utenti, per avere un certo numero di nuovi post da aggiungere al database è stato opportuno aspettare che venissero effettivamente pubblicati.

Questa prima fase di estrazione dati è stata realizzata mediante un semplice script python, che consiste di tre parti principali:

1) Autenticazione
2) Estrazione dati (esempio a seguire)
```python
pythonSub = requests.get("https://oauth.reddit.com/r/python/new", headers=headers, params={'limit':'100'})
```
3) Salvataggio deli dati recuperati in file JSON, filtrando solo le informazioni utili agli scopi di analisi prefissati e invio sul canale kafka (questo script si comporta da producer, il consumer sarà lo script di sentiment)
```python
for response in responseData:
        for post in response.json()["data"]["children"]:
            dataPost = {
                'subreddit': post['data']['subreddit'],
                'user': post['data']['author'],
                'title': post['data']['title'],
                'selftext': post['data']['selftext'],
                'score': post['data']['score'],
                'time': post['data']['created_utc'],
                'commentsCount': post['data']['num_comments']
            }
            producer.send("reddit-posts-dev", dataPost)
```
<br>

## Sentiment analysis
Prima di salvare i dati su un database in locale, è stata effettuata un'analisi del sentiment per dividere i post negativi da quelli positivi. Ciò è stato fatto perché può essere utile monitorare l'andamento del sentiment generale e confrontare i vari subreddit da un punto di vista del coinvolgimento degli utenti.

Lo script di sentiment è stato realizzato sempre in python, con l'utilizzo della libreria [TextBlob](https://github.com/sloria/TextBlob), che offre un metodo basato su machine learning per processare in modo semplice un testo e assegnare uno score in base al sentiment registrato. L'analisi del sentiment è stata eseguita sul campo "title" dei post estratti, che è stato ritenuto il più adatto per estrapolare l'umore dell'autore.

Questo microservizio inizialmente consuma i messaggi dal canale kafka per ottenere accesso ai post estratti, successivamente esegue la sentiment analysis e infine sulla base del risultato ottenuto invia come producer un nuovo messaggio sul canale kafka su due topic diversi: "reddit-positive-dev" se lo score ottenuto è maggiore o uguale a 0, "reddit-negative-dev" altrimenti. Questa suddivisione di topic verrà utilizzata dal microservizio che si occupa di salvare i dati in un DB locale, effettivamente si avranno due database distinti, uno per i post considerati positivi, uno per quelli considerati negativi.

```python
if __name__ == '__main__':

    consumer = KafkaConsumer("reddit-posts-dev", auto_offset_reset='earliest', enable_auto_commit=True, group_id=None,
                             value_deserializer=lambda x: loads(x.decode('utf-8')))

    producer = KafkaProducer(value_serializer=lambda v: json.dumps(v).encode('utf-8'))

    for message in consumer:

        message = message.value
        postText = message["title"] + message["selftext"]

        # converting to blob and sentiment analysis of the post
        blob = TextBlob(postText)
        sentiment = blob.sentiment.polarity

        message["scoreSentiment"] = sentiment

        # sending posts to other kafka microservices
        if sentiment >= 0:
            producer.send("reddit-positive-dev", message)
        else:
            producer.send("reddit-negative-dev", message)

```