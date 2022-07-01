package com.example.redditpositive.service;

import com.example.redditpositive.model.RedditPost;
import com.example.redditpositive.repository.ReddItPostRepository;
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
