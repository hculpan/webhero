package org.culpan.webhero.controllers;

import org.culpan.webhero.domain.Hero;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class IndexController {
    int count = 0;

    @RequestMapping("/")
    String index(Model model){
        model.addAttribute("greet_text", "Hello " + Integer.toString(++count));
        return "index";
    }

    @RequestMapping("/index.html")
    String index_html(Model model){
        model.addAttribute("greet_text", "Hello " + Integer.toString(++count));
        return "index";
    }

}
