package com.example.redditpositive.controller;

import com.example.redditpositive.service.RedditPostService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashboardController {

    private RedditPostService redditPostService;

    @GetMapping("/dashboard")
    public void showDashboard(){

    }

}
