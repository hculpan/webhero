package org.culpan.webhero.db;

import org.culpan.webhero.entity.Hero;
import org.mapdb.DB;
import org.mapdb.Serializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.UUID;

/**
 * Created by harryculpan on 7/22/16.
 */
@Component
public class HeroTable {
    private DB db;

    @Autowired
    public void setDb(DB db) {
        this.db = db;
    }

    public Map<String, String> getTable() {
        return db.hashMap("heroes", Serializer.STRING, Serializer.STRING).createOrOpen();
    };

    public void save(String key, String value) {
        if (key == null) {
            key = UUID.randomUUID().toString();
        }
        getTable().put(key, value);
        db.commit();
    }

    public Collection<Hero> all() {
        Collection<Hero> result = new ArrayList<>();
        for (String json : getTable().values()) {
            result.add(Hero.fromJson(json));
        }
        return result;
    }

    public Hero findOne(String id) {
        String json = getTable().get(id);
        if (!StringUtils.isEmpty(json)) {
            return Hero.fromJson(json);
        }
        return null;
    }

    public void delete(String id) {
        getTable().remove(id);
        db.commit();
    }
}
