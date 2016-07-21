package org.culpan.webhero.entity;

import com.amazonaws.services.dynamodbv2.datamodeling.*;
import org.springframework.data.annotation.Id;

/**
 * Created by USUCUHA on 7/14/2016.
 */
@DynamoDBTable(tableName = "heroes")
public class Hero {
    private String heroName;

    private Integer speed;

    private Integer dex;

    private Integer con;

    private Integer recovery;

    private Integer stun;

    private Integer body;

    @DynamoDBHashKey
    public String getHeroName() {
        return heroName;
    }

    public void setHeroName(String heroName) {
        this.heroName = heroName;
    }

    @DynamoDBRangeKey
    public Integer getSpeed() {
        return speed;
    }

    public void setSpeed(Integer speed) {
        this.speed = speed;
    }

    @DynamoDBAttribute
    public Integer getDex() {
        return dex;
    }

    public void setDex(Integer dex) {
        this.dex = dex;
    }

    @DynamoDBAttribute
    public Integer getCon() {
        return con;
    }

    public void setCon(Integer con) {
        this.con = con;
    }

    @DynamoDBAttribute
    public Integer getRecovery() {
        return recovery;
    }

    public void setRecovery(Integer recovery) {
        this.recovery = recovery;
    }

    @DynamoDBAttribute
    public Integer getStun() {
        return stun;
    }

    public void setStun(Integer stun) {
        this.stun = stun;
    }

    @DynamoDBAttribute
    public Integer getBody() {
        return body;
    }

    public void setBody(Integer body) {
        this.body = body;
    }
}
