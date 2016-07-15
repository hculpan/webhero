package org.culpan.webhero.controllers;

import org.culpan.webhero.domain.Hero;
import org.culpan.webhero.services.HeroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by harryculpan on 7/14/16.
 */
@Controller
public class HeroController {
    HeroService heroService;

    @Autowired
    public void setHeroService(HeroService heroService) {
        this.heroService = heroService;
    }

    @RequestMapping("hero/edit/{id}")
    public String edit(@PathVariable Integer id, Model model){
        model.addAttribute("hero", heroService.getHeroById(id));
        return "heroform";
    }

    @RequestMapping("hero/delete/{id}")
    public String delete(@PathVariable Integer id){
        heroService.deleteHero(id);
        return "redirect:/heroes";
    }

    @RequestMapping(value = "hero", method = RequestMethod.POST)
    public String saveHero(Hero hero){
        heroService.saveHero(hero);
        return "redirect:/hero/" + hero.getId();
    }

    @RequestMapping("hero/new")
    public String newHero(Model model) {
        model.addAttribute("hero", new Hero());
        return "heroform";
    }

    @RequestMapping("/hero/{id}")
    String index(@PathVariable Integer id, Model model){
        model.addAttribute("hero", heroService.getHeroById(id));
        return "heroshow";
    }

    @RequestMapping("heroes")
    public String listHeroes(Model model) {
        model.addAttribute("heroes", heroService.listAllHeroes());
        return "heroes";
    }
}
