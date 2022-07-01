package com.example.redditnegative.service;

import com.example.redditnegative.model.RedditPost;
import com.example.redditnegative.repository.ReddItPostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RedditPostService {

    @Autowired
    private ReddItPostRepository reddItPostRepository;

    public void savePost(RedditPost redditPost){
        reddItPostRepository.save(redditPost);
    }

}
