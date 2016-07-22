package org.culpan.webhero.repositories;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.ItemCollection;
import com.amazonaws.services.dynamodbv2.document.QueryOutcome;
import com.amazonaws.services.dynamodbv2.document.spec.QuerySpec;
import com.amazonaws.services.dynamodbv2.document.utils.ValueMap;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ScanRequest;
import com.amazonaws.services.dynamodbv2.model.ScanResult;
import org.culpan.webhero.entity.Hero;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Created by usucuha on 7/19/2016.
 */
@Repository
public class HeroDAO {
    private DynamoDB client;

    private AmazonDynamoDB dynamoDB;

    private DynamoDBMapper mapper;

    @Autowired
    public void setClient(DynamoDB client) {
        this.client = client;
    }

    @Autowired
    public void setDynamoDB(AmazonDynamoDB dynamoDB) {
        this.dynamoDB = dynamoDB;
    }

    @Autowired
    public void setMapper(DynamoDBMapper mapper) {
        this.mapper = mapper;
    }

    public List<Hero> findByName(String name)
    {
        Map<String, AttributeValue> lastKeyEvaluated;
        do
        {
            Map<String, AttributeValue> values = new HashMap<>();
            values.put(":val", new AttributeValue().withS(name));
            ScanRequest scanRequest = new ScanRequest()
                    .withLimit(100)
                    .withTableName("heroes")
                    .withExpressionAttributeValues(values)
                    .withFilterExpression("name = :val")
                    .withProjectionExpression("id");

            ScanResult scanResult = dynamoDB.scan(scanRequest);

            scanResult.getItems().stream().forEach(System.out::println);

            lastKeyEvaluated = scanResult.getLastEvaluatedKey();
        }
        while (null != lastKeyEvaluated);

        return null;
    }

    public List<Hero> findSpeed()
    {
        QuerySpec spec = new QuerySpec();

        spec
                .withKeyConditionExpression("speed = :val")
                .withValueMap(new ValueMap().withNumber(":val", 1));

        ItemCollection<QueryOutcome> customers = client.getTable("heroes").getIndex("speedIndex").query(spec);

        customers.forEach(System.out::println);

        return null;
    }

    public Hero saveWithMapper(Hero customer)
    {
        mapper.save(customer);
        return customer;
    }

    public Hero saveWithItemApi(Hero customer)
    {
        customer.setId(UUID.randomUUID().toString());
        client.getTable("customers").putItem(
                new Item().withPrimaryKey("id", customer.getId())
                        .with("name", customer.getName())
                        .with("speed", customer.getSpeed()));
        return customer;
    }
}
