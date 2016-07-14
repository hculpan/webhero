package org.culpan.webnomic.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {
    int count = 0;

    @RequestMapping("/")
    String index(Model model){
        model.addAttribute("greet_text", "Hello " + Integer.toString(++count));
        return "index";
    }
}
