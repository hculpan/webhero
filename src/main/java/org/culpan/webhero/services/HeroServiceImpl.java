package org.culpan.webhero.services;

import org.culpan.webhero.domain.Hero;
import org.culpan.webhero.repositories.HeroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by harryculpan on 7/14/16.
 */
@Service
public class HeroServiceImpl implements HeroService {
    private HeroRepository heroRepository;

    @Autowired
    public void setHeroRepository(HeroRepository heroRepository) {
        this.heroRepository = heroRepository;
    }

    @Override
    public Iterable<Hero> listAllHeroes() {
        return heroRepository.findAll();
    }

    @Override
    public Hero getHeroById(Integer id) {
        return heroRepository.findOne(id);
    }

    @Override
    public Hero saveHero(Hero hero) {
        return heroRepository.save(hero);
    }

    @Override
    public void deleteHero(Integer id) {
        heroRepository.delete(id);
    }
}
