package org.culpan.webhero.repositories;

import org.culpan.webhero.entity.Hero;
import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by harryculpan on 7/14/16.
 */
public interface HeroRepository extends CrudRepository<Hero, String> {
    @EnableScan
    List<Hero> findByName(String name);

    @Override
    @EnableScan
    List<Hero> findAll();

    List<Hero> findBySpeed(Integer speed);
}
