package org.culpan.webhero.db;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.model.*;
import org.apache.log4j.Logger;
import org.culpan.webhero.entity.Hero;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * Created by usucuha on 7/19/2016.
 */
@Component
public class DBInitializer
{
    private Logger logger = Logger.getLogger(DBInitializer.class);

    private DynamoDBMapper mapper;
    private DynamoDB client;

    @Autowired
    public DBInitializer(DynamoDBMapper mapper, DynamoDB client)
    {
        this.mapper = mapper;
        this.client = client;
    }

    @PostConstruct
    public void init() throws InterruptedException
    {
        //Uncomment to use the low level api
        //createCustomersTable();

        //use DynamoDBMapper
        createHeroesTableWithMapper();
    }

/*    private void createHeroesTable() throws InterruptedException
    {
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
                new CreateTableRequest().withTableName("customers")
                        .withKeySchema(keyDefinitions)
                        .withAttributeDefinitions(attributeDefinitions)
                        .withProvisionedThroughput(provisionedThroughput)
                        .withGlobalSecondaryIndexes(globalSecondaryIndex);

        try
        {
            Table table = client.createTable(request);
            table.waitForActive();
        }
        catch (ResourceInUseException e)
        {
            logger.info("Table {} already exists", request.getTableName());
        }
    }
*/
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
            logger.info("Table already exists: " + request.getTableName());
        }
    }
}