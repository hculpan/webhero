package org.culpan.webhero.services;

import org.culpan.webhero.domain.Hero;

/**
 * Created by harryculpan on 7/14/16.
 */
public interface HeroService {
    Iterable<Hero> listAllHeroes();

    Hero getHeroById(Integer id);

    Hero saveHero(Hero hero);

    void deleteHero(Integer id);
}
