package com.cqw.community.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HelloController {
    @GetMapping("/hello")
    public String hello(@RequestParam(name = "name", defaultValue = "World") String name, Model model) {
        System.out.println("hello");
        model.addAttribute("name", name);
        return "hello";
    }

}
