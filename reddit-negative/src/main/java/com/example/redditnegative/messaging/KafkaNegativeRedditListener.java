package com.example.redditnegative.messaging;

import com.example.redditnegative.model.RedditPost;
import com.example.redditnegative.service.RedditPostService;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class KafkaNegativeRedditListener {

    @Autowired
    private RedditPostService redditPostService;

    private static final String TOPIC_NAME = "reddit-negative-dev";

    @KafkaListener(topics = TOPIC_NAME, groupId = "group-id")
    public void consumeMessage(@Payload String post){
        JSONObject jsonObj = new JSONObject(post);

        RedditPost r = RedditPost.builder()
                .title(jsonObj.getString("title"))
                .user(jsonObj.getString("user"))
                .commentsCount(jsonObj.getFloat("commentsCount"))
                .selftext(jsonObj.getString("selftext"))
                .subreddit(jsonObj.getString("subreddit"))
                .score(jsonObj.getFloat("score"))
                .time(new java.util.Date((long)jsonObj.getFloat("time")*1000))
                .scoreSentiment(jsonObj.getFloat("scoreSentiment"))
                .build();

        redditPostService.savePost(r);
        log.info(r.toString());
    }

}
