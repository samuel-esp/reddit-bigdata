import json
from kafka import KafkaConsumer
from kafka import KafkaProducer
from json import loads
from textblob import TextBlob

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

        print(sentiment)
        print('{}'.format(message))

