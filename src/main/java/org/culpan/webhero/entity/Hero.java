package org.culpan.webhero.entity;

import com.google.gson.Gson;

public class Hero {
    private String id;

    private String heroName;

    private Integer speed;

    private Integer dex;

    private Integer con;

    private Integer recovery;

    private Integer stun;

    private Integer body;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getHeroName() {
        return heroName;
    }

    public void setHeroName(String heroName) {
        this.heroName = heroName;
    }

    public Integer getSpeed() {
        return speed;
    }

    public void setSpeed(Integer speed) {
        this.speed = speed;
    }

    public Integer getDex() {
        return dex;
    }

    public void setDex(Integer dex) {
        this.dex = dex;
    }

    public Integer getCon() {
        return con;
    }

    public void setCon(Integer con) {
        this.con = con;
    }

    public Integer getRecovery() {
        return recovery;
    }

    public void setRecovery(Integer recovery) {
        this.recovery = recovery;
    }

    public Integer getStun() {
        return stun;
    }

    public void setStun(Integer stun) {
        this.stun = stun;
    }

    public Integer getBody() {
        return body;
    }

    public void setBody(Integer body) {
        this.body = body;
    }

    public String toJson() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }

    public static Hero fromJson(String json) {
        Gson gson = new Gson();
        System.out.println(json);
        return gson.fromJson(json, Hero.class);
    }
}
