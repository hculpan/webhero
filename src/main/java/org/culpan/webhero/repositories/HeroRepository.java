package org.culpan.webhero.repositories;

import org.culpan.webhero.db.HeroTable;
import org.culpan.webhero.entity.Hero;
import org.mapdb.DB;
import org.mapdb.Serializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ConcurrentMap;

/**
 * Created by harryculpan on 7/14/16.
 */
@Component
public class HeroRepository {
    private HeroTable heroTable;

    @Autowired
    public void setHeroTable(HeroTable heroTable) {
        this.heroTable = heroTable;
    }

    public List<Hero> findByHeroName(String heroName) {
        List<Hero> result = new ArrayList<>();
        heroTable.all().parallelStream().
                filter( h -> org.apache.commons.lang.StringUtils.equalsIgnoreCase(heroName, h.getHeroName())).
                forEach( h -> result.add(h));
        return result;
    };

    public List<Hero> findAll() {
        List<Hero> result = new ArrayList<>();
        result.addAll(heroTable.all());
        return result;
    };

    public List<Hero> findBySpeed(Integer speed) {
        List<Hero> result = new ArrayList<>();
        heroTable.all().parallelStream().
                filter( h -> speed.equals(h.getSpeed())).
                forEach( h -> result.add(h));
        return result;
    };

    public Hero findOne(String id) {
        return heroTable.findOne(id);
    }

    public void delete(String id) {
        heroTable.delete(id);

    }

    public void save(Hero hero) {
        if (org.apache.commons.lang.StringUtils.isEmpty(hero.getId())) {
            hero.setId(UUID.randomUUID().toString());
        }
        heroTable.save(hero.getId(), hero.toJson());
    }
}
