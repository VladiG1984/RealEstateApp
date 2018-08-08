package com.realestateapp.realestateapp.controllers;

import com.realestateapp.realestateapp.models.Property;
import com.realestateapp.realestateapp.services.base.PropertiesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class HomeController {

    @GetMapping("/")
    public String homePage(Model model) {
        model.addAttribute("view", "home/index");

        return "base";
    }

    @GetMapping("/about")
    public String aboutPage(Model model) {
        model.addAttribute("view","company/about");

        return "base";
    }

    @GetMapping("/contact")
    public String contactPage(Model model) {
        model.addAttribute("view","others/contact");

        return "base";
    }

    @GetMapping("/terms-and-conditions")
    public String termsPage(Model model) {
        model.addAttribute("view","others/terms");

        return "base";
    }

    @GetMapping("/faqs")
    public String faqs(Model model) {
        model.addAttribute("view","others/faqs");

        return "base";
    }

}