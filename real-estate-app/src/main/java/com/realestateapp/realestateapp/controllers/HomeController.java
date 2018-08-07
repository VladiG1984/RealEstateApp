package com.realestateapp.realestateapp.controllers;

import com.sun.org.apache.xpath.internal.operations.Mod;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String homePage(Model model) {
        model.addAttribute("view","home/index");

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
