package org.culpan.webhero.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {
    int count = 0;

    @RequestMapping("/")
    String index(Model model){
        return "redirect:/heroes";
    }

    @RequestMapping("/index.html")
    String index_html(Model model){
        return "redirect:/heroes";
    }

}
