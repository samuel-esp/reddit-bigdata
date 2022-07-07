package com.example.redditnegative.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder @ToString
@Document(collection = "negative_reddit_posts")
public class RedditPost {

    private String id;

    private String subreddit;

    private String user;

    @Id
    private String title;

    private String selftext;

    private Float score;

    private Date time;

    private Float commentsCount;

    private Float scoreSentiment;


}
