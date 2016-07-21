package org.culpan.webhero.db;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.model.*;
import org.apache.log4j.Logger;
import org.culpan.webhero.entity.Hero;
import org.culpan.webhero.repositories.HeroDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * Created by usucuha on 7/19/2016.
 */
@Component
public class DBInitializer {
    private Logger log = Logger.getLogger(DBInitializer.class);

    private DynamoDBMapper mapper;

    private DynamoDB client;

    private HeroDAO heroDAO;

    @Autowired
    public DBInitializer(DynamoDBMapper mapper, DynamoDB client, HeroDAO heroDAO)
    {
        this.mapper = mapper;
        this.client = client;
        this.heroDAO = heroDAO;
    }

    @PostConstruct
    public void init() throws InterruptedException
    {
        //Uncomment to use the low level api
        //createCustomersTable();

        //use DynamoDBMapper
        createHeroesTableWithMapper();

        List<Hero> heroes = heroDAO.findByHeroName("Arachnid");
        if (heroes == null || heroes.size() < 1) {
            Hero arachnid = new Hero();
            arachnid.setHeroName("Arachnid");
            arachnid.setSpeed(new Integer(8));
            arachnid.setCon(new Integer(20));
            arachnid.setDex(new Integer(30));
            arachnid.setBody(new Integer(12));
            arachnid.setStun(new Integer(40));
            heroDAO.saveWithMapper(arachnid);
            log.info("Inserted Arachnid");
        } else {
            Hero arachnid = heroes.get(0);
            arachnid.setHeroName("Arachnid");
            arachnid.setSpeed(new Integer(8));
            arachnid.setCon(new Integer(20));
            arachnid.setDex(new Integer(30));
            arachnid.setBody(new Integer(12));
            arachnid.setStun(new Integer(40));
            heroDAO.saveWithMapper(arachnid);
            log.info("Updated Arachnid");
        }


        heroes = heroDAO.findByHeroName("Night Shadow");
        if (heroes == null || heroes.size() < 1) {
            Hero nightShadow = new Hero();
            nightShadow.setHeroName("Night Shadow");
            nightShadow.setSpeed(new Integer(6));
            nightShadow.setCon(new Integer(30));
            nightShadow.setDex(new Integer(20));
            nightShadow.setBody(new Integer(25));
            nightShadow.setStun(new Integer(80));
            heroDAO.saveWithMapper(nightShadow);
            log.info("Inserted Night Shadow");
        } else {
            Hero nightShadow = heroes.get(0);
            nightShadow.setHeroName("Night Shadow");
            nightShadow.setSpeed(new Integer(6));
            nightShadow.setCon(new Integer(30));
            nightShadow.setDex(new Integer(20));
            nightShadow.setBody(new Integer(25));
            nightShadow.setStun(new Integer(80));
            heroDAO.saveWithMapper(nightShadow);
            log.info("Updated Night Shadow");
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