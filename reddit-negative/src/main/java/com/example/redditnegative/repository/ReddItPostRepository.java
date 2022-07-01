package com.example.redditnegative.repository;

import com.example.redditnegative.model.RedditPost;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ReddItPostRepository extends MongoRepository<RedditPost, Long> {




}
