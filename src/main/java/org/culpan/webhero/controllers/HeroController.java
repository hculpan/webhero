package org.culpan.webhero.controllers;

import org.culpan.webhero.entity.Hero;
import org.culpan.webhero.repositories.HeroDAO;
import org.culpan.webhero.repositories.HeroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.UUID;

/**
 * Created by harryculpan on 7/14/16.
 */
@Controller
public class HeroController {
    private HeroRepository heroRepository;

    private HeroDAO heroDAO;

    @Autowired
    public void setHeroRepository(HeroRepository heroRepository) {
        this.heroRepository = heroRepository;
    }

    @Autowired
    public void setHeroDAO(HeroDAO heroDAO) {
        this.heroDAO = heroDAO;
    }

    @RequestMapping("hero/edit/{id}")
    public String edit(@PathVariable String id, Model model){
        model.addAttribute("hero", heroRepository.findOne(id));
        return "heroform";
    }

    @RequestMapping("hero/delete/{id}")
    public String delete(@PathVariable String id){
        heroRepository.delete(id);
        return "redirect:/heroes";
    }

    @RequestMapping(value = "hero", method = RequestMethod.POST)
    public String saveHero(Hero hero){
        if (StringUtils.isEmpty(hero.getId())) {
            hero.setId(UUID.randomUUID().toString());
       }
        heroRepository.save(hero);
        return "redirect:/heroes";
    }

    @RequestMapping("hero/new")
    public String newHero(Model model) {
        model.addAttribute("hero", new Hero());
        return "heroform";
    }

    @RequestMapping("/hero/{id}")
    String index(@PathVariable String id, Model model){
        model.addAttribute("hero", heroRepository.findOne(id));
        return "heroshow";
    }

    @RequestMapping("heroes")
    public String listHeroes(Model model) {
        model.addAttribute("heroes", heroRepository.findAll());
        return "heroes";
    }
}
