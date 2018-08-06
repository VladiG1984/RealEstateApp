package com.realestateapp.realestateapp.controllers;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
@EnableAutoConfiguration
public class HomeController {
    @GetMapping("")
    public String index() {
        return "index";
    }
}
