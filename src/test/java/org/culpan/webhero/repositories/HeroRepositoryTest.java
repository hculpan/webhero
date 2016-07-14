package org.culpan.webhero.repositories;

import org.culpan.webhero.configuration.RepositoryConfiguration;
import org.culpan.webhero.domain.Hero;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = {RepositoryConfiguration.class})
public class HeroRepositoryTest {

    private HeroRepository heroRepository;

    @SuppressWarnings("SpringJavaAutowiringInspection")
    @Autowired
    public void setHeroRepository(HeroRepository heroRepository) {
        this.heroRepository = heroRepository;
    }

    @Test
    public void testSaveHero(){
        //setup hero
        Hero hero = new Hero();
        hero.setName("Arachnid");
        hero.setSpeed(new Integer(8));

        //save hero, verify has ID value after save
        assertNull(hero.getId()); //null before save
        heroRepository.save(hero);
        assertNotNull(hero.getId()); //not null after save

        //fetch from DB
        Hero fetchedProduct = heroRepository.findOne(hero.getId());

        //should not be null
        assertNotNull(fetchedProduct);

        //should equal
        assertEquals(hero.getId(), fetchedProduct.getId());
        assertEquals(hero.getName(), fetchedProduct.getName());

        //update description and save
        fetchedProduct.setName("Night Shadow");
        heroRepository.save(fetchedProduct);

        //get from DB, should be updated
        Hero fetchedUpdatedProduct = heroRepository.findOne(fetchedProduct.getId());
        assertEquals(fetchedProduct.getName(), fetchedUpdatedProduct.getName());

        //verify count of products in DB
        long productCount = heroRepository.count();
        assertEquals(productCount, 1);

        //get all products, list should only have one
        Iterable<Hero> products = heroRepository.findAll();

        int count = 0;

        for(Hero p : products){
            count++;
        }

        assertEquals(count, 1);
    }
}