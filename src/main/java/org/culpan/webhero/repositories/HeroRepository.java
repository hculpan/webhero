package org.culpan.webhero.repositories;

import org.culpan.webhero.domain.Hero;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by harryculpan on 7/14/16.
 */
public interface HeroRepository extends CrudRepository<Hero, Integer> {
}
