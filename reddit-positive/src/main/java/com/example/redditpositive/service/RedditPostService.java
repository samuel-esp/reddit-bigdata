package com.example.redditpositive.service;

import com.example.redditpositive.model.RedditPost;
import com.example.redditpositive.repository.ReddItPostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RedditPostService {

    @Autowired
    private ReddItPostRepository reddItPostRepository;

    public void savePost(RedditPost redditPost){
        reddItPostRepository.save(redditPost);
    }

    public List<RedditPost> retrieveAllPost(){
        return reddItPostRepository.findAll();
    }

    public void retrieveMostLikedPost(){

    }

    public void retrieveLeastLikedPost(){

    }

    public void retrieveMostPresentSubreddit(){

    }

    public void retrieveMostLikedSubreddit(){

    }

    public void retrieveLeastLikedSubreddit(){

    }

    public void retrieve(){

    }


}
