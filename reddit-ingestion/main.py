from kafka import KafkaProducer
import requests
import json
import time

if __name__ == '__main__':

    producer = KafkaProducer(value_serializer=lambda v: json.dumps(v).encode('utf-8'))

    # Adding ClientID and SecretID
    auth = requests.auth.HTTPBasicAuth('o5c5M_eQeJ0znBGBaMWrIw', 'f8U_kN23Zbb3JMTraalfUdYaAYrTTA')
    # Reddit Login
    data = {'grant_type': 'password', 'username': 'Swimming-Ad-9082', 'password': 'bigdata22'}
    # Adding Header and App API Reference
    headers = {'User-Agent': 'MyBot/0.0.1'}
    # Sending Auth Access Request
    res = requests.post('https://www.reddit.com/api/v1/access_token', auth=auth, data=data, headers=headers)
    # Access Token
    TOKEN = res.json()['access_token']
    # Add Token To Header
    headers = {**headers, **{'Authorization': f"bearer {TOKEN}"}}

    # While the token is valid (~2 hours) we just add headers=headers to our requests
    requests.get('https://oauth.reddit.com/api/v1/me', headers=headers, params={'limit':'100'})

    # Getting 100 Posts From Each Subreddit
    i = 0
    pythonSub = requests.get("https://oauth.reddit.com/r/python/hot", headers=headers, params={'limit':'100'})
    i = i + 1
    print("subreddit " + str(i) + " finished")
    time.sleep(60)
    csCareerSub = requests.get("https://oauth.reddit.com/r/cscareerquestions/hot", headers=headers, params={'limit':'100'})
    i = i + 1
    print("subreddit " + str(i) + " finished")
    time.sleep(60)
    newsSub = requests.get("https://oauth.reddit.com/r/news/hot", headers=headers, params={'limit':'100'})
    i = i + 1
    print("subreddit " + str(i) + " finished")
    time.sleep(60)
    nbaSub = requests.get("https://oauth.reddit.com/r/nba/hot", headers=headers, params={'limit':'100'})
    i = i + 1
    print("subreddit " + str(i) + " finished")
    time.sleep(60)
    appleSub = requests.get("https://oauth.reddit.com/r/spotify/hot", headers=headers, params={'limit':'100'})
    i = i + 1
    print("subreddit " + str(i) + " finished")
    time.sleep(60)
    jobsSub = requests.get("https://oauth.reddit.com/r/jobs/hot", headers=headers, params={'limit':'100'})
    i = i + 1
    print("subreddit " + str(i) + " finished")
    time.sleep(60)
    tennisSub = requests.get("https://oauth.reddit.com/r/tennis/hot", headers=headers, params={'limit':'100'})
    i = i + 1
    print("subreddit " + str(i) + " finished")
    time.sleep(60)
    moviesSub = requests.get("https://oauth.reddit.com/r/movies/hot", headers=headers, params={'limit':'100'})
    i = i + 1
    print("subreddit " + str(i) + " finished")
    time.sleep(60)
    offMyChestSub = requests.get("https://oauth.reddit.com/r/offmychest/hot", headers=headers, params={'limit':'100'})
    i = i + 1
    print("subreddit " + str(i) + " finished")
    time.sleep(60)
    depressionSub = requests.get("https://oauth.reddit.com/r/depression/hot", headers=headers, params={'limit':'100'})
    i = i + 1
    print("subreddit " + str(i) + " finished")
    time.sleep(60)
    foreverAloneSub = requests.get("https://oauth.reddit.com/r/foreveralone/hot", headers=headers, params={'limit':'100'})
    i = i + 1
    print("subreddit " + str(i) + " finished")
    print("extraction completed")

    responseData = [pythonSub, csCareerSub, newsSub, nbaSub, appleSub, jobsSub, tennisSub, moviesSub, offMyChestSub,
                    foreverAloneSub, depressionSub]

    for response in responseData:
        for post in response.json()["data"]["children"]:
            dataPost = {
                'subreddit': post['data']['subreddit'],
                'title': post['data']['title'],
                'selftext': post['data']['selftext'],
                'upvote_ratio': post['data']['upvote_ratio'],
                'ups': post['data']['ups'],
                'downs': post['data']['downs'],
                'score': post['data']['score']
            }
            print("post sent")
            producer.send("reddit-posts", dataPost)

        print("message sent")

    producer.flush()
    producer.close()