package com.example.redditpositive.repository;

import com.example.redditpositive.model.RedditPost;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ReddItPostRepository extends MongoRepository<RedditPost, Long> {




}
