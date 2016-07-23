package org.culpan.webhero.db;

import org.apache.log4j.Logger;
import org.culpan.webhero.entity.Hero;
import org.culpan.webhero.repositories.HeroRepository;
import org.mapdb.DB;
import org.mapdb.Serializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.concurrent.ConcurrentMap;

/**
 * Created by usucuha on 7/19/2016.
 */
@Component
public class DBInitializer
{
    private Logger log = Logger.getLogger(DBInitializer.class);


    private HeroRepository heroRepository;

    private DB db;

    @Autowired
    public DBInitializer(DB db, HeroRepository heroRepository) {
        this.heroRepository = heroRepository;
    }

    @PostConstruct
    public void init() throws InterruptedException {
        List<Hero> heroes = heroRepository.findByHeroName("Arachnid");
        if (heroes == null || heroes.size() == 0) {
            Hero arachnid = new Hero();
            arachnid.setHeroName("Arachnid");
            arachnid.setSpeed(new Integer(8));
            arachnid.setDex(new Integer(30));
            arachnid.setCon(new Integer(20));
            heroRepository.save(arachnid);

            log.info("Inserted Arachnid - id: " + arachnid.getId());
        }

        heroes = heroRepository.findByHeroName("Night Shadow");
        if (heroes == null || heroes.size() == 0) {
            Hero nightShadow = new Hero();
            nightShadow.setHeroName("Night Shadow");
            nightShadow.setSpeed(new Integer(6));
            nightShadow.setDex(new Integer(20));
            nightShadow.setCon(new Integer(30));
            heroRepository.save(nightShadow);

            log.info("Inserted Night Shadow - id:" + nightShadow.getId());
        }
    }
}