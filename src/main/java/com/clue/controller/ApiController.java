package com.clue.controller;

import org.springframework.web.bind.annotation.*;

@RestController
public class ApiController {
    @RequestMapping("/")
    String home() {
        return "Hello World!";
    }
}
