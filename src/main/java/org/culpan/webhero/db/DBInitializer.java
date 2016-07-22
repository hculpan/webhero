package org.culpan.webhero.db;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.model.*;
import org.apache.log4j.Logger;
import org.culpan.webhero.entity.Hero;
import org.culpan.webhero.repositories.HeroDAO;
import org.culpan.webhero.repositories.HeroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * Created by usucuha on 7/19/2016.
 */
@Component
public class DBInitializer
{
    private Logger log = Logger.getLogger(DBInitializer.class);

    private DynamoDBMapper mapper;

    private DynamoDB client;

    private HeroRepository heroRepository;

    @Autowired
    public DBInitializer(DynamoDBMapper mapper, DynamoDB client, HeroRepository heroRepository)
    {
        this.mapper = mapper;
        this.client = client;
        this.heroRepository = heroRepository;
    }

    @PostConstruct
    public void init() throws InterruptedException
    {
        //Uncomment to use the low level api
        //createCustomersTable();

        //use DynamoDBMapper
        createHeroesTableWithMapper();

        List<Hero> heroes = heroRepository.findByHeroName("Arachnid");
        if (heroes == null || heroes.size() == 0) {
            Hero arachnid = new Hero();
            arachnid.setHeroName("Arachnid");
            arachnid.setSpeed(new Integer(8));
            arachnid.setDex(new Integer(30));
            arachnid.setCon(new Integer(20));
            mapper.save(arachnid);

            log.info("Inserted Arachnid - id: " + arachnid.getId());
        }

        heroes = heroRepository.findByHeroName("Night Shadow");
        if (heroes == null || heroes.size() == 0) {
            Hero nightShadow = new Hero();
            nightShadow.setHeroName("Night Shadow");
            nightShadow.setSpeed(new Integer(6));
            nightShadow.setDex(new Integer(20));
            nightShadow.setCon(new Integer(30));
            mapper.save(nightShadow);

            log.info("Saved Night Shadow - id:" + nightShadow.getId());
        }
    }

    private void createHeroesTableWithMapper() throws InterruptedException {
        CreateTableRequest request = mapper.generateCreateTableRequest(Hero.class);
        ProvisionedThroughput provisionedThroughput = new ProvisionedThroughput(1L, 1L);
        request.setProvisionedThroughput(provisionedThroughput);
        request.getGlobalSecondaryIndexes().forEach(index->{
                    index.setProvisionedThroughput(provisionedThroughput);
                    index.setProjection(new Projection().withProjectionType(ProjectionType.ALL));
                }
        );

        try {
            Table table = client.createTable(request);
            table.waitForActive();
        } catch (ResourceInUseException e) {
            log.info("Table already exists: " + request.getTableName());
        }
    }
}