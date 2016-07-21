package org.culpan.webhero.repositories;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
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

import java.util.*;

/**
 * Created by usucuha on 7/19/2016.
 */
@Repository
public class HeroDAO {
    private DynamoDB client;

    private AmazonDynamoDB dynamoDB;

    private HeroRepository heroRepository;

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
    public void setHeroRepository(HeroRepository heroRepository) {
        this.heroRepository = heroRepository;
    }

    @Autowired
    public void setMapper(DynamoDBMapper mapper) {
        this.mapper = mapper;
    }

    public List<Hero> findByHeroName(String heroName) {
        Map<String, AttributeValue> eav = new HashMap<>();
        eav.put(":val1", new AttributeValue().withS(heroName));

        DynamoDBQueryExpression<Hero> queryExpression = new DynamoDBQueryExpression<Hero>()
                .withKeyConditionExpression("heroName = :val1")
                .withExpressionAttributeValues(eav);

        return mapper.query(Hero.class, queryExpression);
    }
/*        List<Hero> result = new ArrayList<>();
        Map<String, AttributeValue> lastKeyEvaluated = null;
        do {
            Map<String, AttributeValue> values = new HashMap<>();
            values.put(":val", new AttributeValue().withS(heroName));
            ScanRequest scanRequest = new ScanRequest()
                    .withLimit(100)
                    .withTableName("heroes")
                    .withExpressionAttributeValues(values)
                    .withFilterExpression("heroName = :val")
                    .withProjectionExpression("id");

            ScanResult scanResult = dynamoDB.scan(scanRequest);

            for (Map<String, AttributeValue> item : scanResult.getItems()) {
                Hero hero = heroRepository.findOne(item.get("id").getS());
                if (hero != null) {
                    result.add(hero);
                }
            }

            lastKeyEvaluated = scanResult.getLastEvaluatedKey();
        } while (null != lastKeyEvaluated);

        return result;
    }*/

    public List<Hero> findSpeed() {
        QuerySpec spec = new QuerySpec();

        spec
                .withKeyConditionExpression("speed = :val")
                .withValueMap(new ValueMap().withNumber(":val", 1));

        ItemCollection<QueryOutcome> customers = client.getTable("heroes").getIndex("speedIndex").query(spec);

        customers.forEach(System.out::println);

        return null;
    }

    public Hero saveWithMapper(Hero hero) {
        heroRepository.save(hero);
        return hero;
    }
}
