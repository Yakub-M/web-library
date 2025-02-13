package com.yakub.weblibrary.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
public class FallbackController {

    @GetMapping("/")
    public String welcomeMessage() {
        return "You seem lost. Try using the API.";
    }

    @RequestMapping("/**")
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleUnknownRoutes() {
        return "You seem lost. Try using the API.";
    }
}
