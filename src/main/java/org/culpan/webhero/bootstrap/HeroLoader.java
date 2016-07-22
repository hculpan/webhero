package org.culpan.webhero.bootstrap;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.model.*;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class HeroLoader implements ApplicationListener<ContextRefreshedEvent> {
    private Logger log = Logger.getLogger(HeroLoader.class);

    private DynamoDBMapper mapper;

    private DynamoDB client;

    @Autowired
    public HeroLoader(DynamoDBMapper mapper, DynamoDB client) {
        this.mapper = mapper;
        this.client = client;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event)  {
/*        try {
            createHeroesTableWithMapper();
        } catch (InterruptedException e) {
            log.error("Got exception: " + e.getLocalizedMessage());
        }

        Hero arachnid = new Hero();
        arachnid.setName("Arachnid");
        arachnid.setSpeed(new Integer(8));
        //heroRepository.save(arachnid);

        log.info("Saved Arachnid - id: " + arachnid.getId());

        Hero nightShadow = new Hero();
        nightShadow.setName("Night Shadow");
        nightShadow.setSpeed(new Integer(6));
        //heroRepository.save(nightShadow);

        log.info("Saved Night Shadow - id:" + nightShadow.getId());*/
    }

    private void createHeroesTable() throws InterruptedException {
        List<AttributeDefinition> attributeDefinitions = new ArrayList<>(1);
        attributeDefinitions.add(new AttributeDefinition().withAttributeName("id").withAttributeType(ScalarAttributeType.S));
        attributeDefinitions.add(new AttributeDefinition().withAttributeName("premium").withAttributeType(ScalarAttributeType.N));

        List<KeySchemaElement> keyDefinitions = new ArrayList<>(2);
        keyDefinitions.add(new KeySchemaElement().withAttributeName("id").withKeyType(KeyType.HASH));

        ProvisionedThroughput provisionedThroughput = new ProvisionedThroughput(1L, 1L);

        GlobalSecondaryIndex globalSecondaryIndex =
                new GlobalSecondaryIndex().withIndexName("premiumIndex")
                        .withProjection(new Projection().withProjectionType(ProjectionType.ALL))
                        .withKeySchema(
                                new KeySchemaElement("premium", KeyType.HASH),
                                new KeySchemaElement("id", KeyType.RANGE)
                        )
                        .withProvisionedThroughput(provisionedThroughput);

        CreateTableRequest request =
                new CreateTableRequest().withTableName("heroes")
                        .withKeySchema(keyDefinitions)
                        .withAttributeDefinitions(attributeDefinitions)
                        .withProvisionedThroughput(provisionedThroughput)
                        .withGlobalSecondaryIndexes(globalSecondaryIndex);

        try {
            Table table = client.createTable(request);
            table.waitForActive();
        } catch (ResourceInUseException e) {
            log.info("Table already exists: " + request.getTableName());
        }
    }

/*    private void createHeroesTableWithMapper() throws InterruptedException {
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
    }*/
}