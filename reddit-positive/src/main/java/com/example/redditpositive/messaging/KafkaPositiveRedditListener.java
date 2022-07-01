package com.example.redditpositive.messaging;

import com.example.redditpositive.model.RedditPost;
import com.example.redditpositive.service.RedditPostService;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.protocol.types.Field;
import org.apache.tomcat.util.json.JSONParser;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class KafkaPositiveRedditListener {

    @Autowired
    private RedditPostService redditPostService;

    private static final String TOPIC_NAME = "reddit-positive";

    @KafkaListener(topics = TOPIC_NAME, groupId = "group-id")
    public void consumeMessage(@Payload String post) {
        JSONObject jsonObj = new JSONObject(post);

        RedditPost r = RedditPost.builder()
                .title(jsonObj.getString("title"))
                .user(jsonObj.getString("user"))
                .commentsCount(jsonObj.getFloat("commentsCount"))
                .selftext(jsonObj.getString("selftext"))
                .subreddit(jsonObj.getString("subreddit"))
                .score(jsonObj.getFloat("score"))
                .time(new java.util.Date((long)jsonObj.getFloat("time")*1000))
                .build();

        redditPostService.savePost(r);
        log.info(r.toString());
    }

}
