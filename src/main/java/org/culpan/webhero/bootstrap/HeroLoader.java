package org.culpan.webhero.bootstrap;

import org.apache.log4j.Logger;
import org.culpan.webhero.domain.Hero;
import org.culpan.webhero.repositories.HeroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Component
public class HeroLoader implements ApplicationListener<ContextRefreshedEvent> {

    private HeroRepository heroRepository;

    private Logger log = Logger.getLogger(HeroLoader.class);

    @Autowired
    public void setProductRepository(HeroRepository heroRepository) {
        this.heroRepository = heroRepository;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {

        Hero arachnid = new Hero();
        arachnid.setName("Arachnid");
        arachnid.setSpeed(new Integer(8));
        heroRepository.save(arachnid);

        log.info("Saved Arachnid - id: " + arachnid.getId());

        Hero nightShadow = new Hero();
        nightShadow.setName("Night Shadow");
        nightShadow.setSpeed(new Integer(6));
        heroRepository.save(nightShadow);

        log.info("Saved Night Shadow - id:" + nightShadow.getId());
    }
}