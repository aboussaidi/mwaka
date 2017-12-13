package io.mwaka.dao.impl.mongo;

import io.mwaka.dao.UserDao;
import io.mwaka.dao.factory.mongo.MongoConnection;
import io.mwaka.dao.factory.mongo.MongoDaoFactory;
import io.mwaka.model.User;
import org.mongodb.morphia.Datastore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;

public class MongoUserDao implements UserDao {
    final static Logger logger = LoggerFactory.getLogger(new ProcessBuilder().environment().get("DEBUG"));
    @Override
    public String createUser(User user) throws Exception {
        MongoConnection connection = MongoDaoFactory.createConnection();
        Datastore ds = connection.getDatastore();
        return ds.save(user).getId().toString();
    }

    @Override
    public List<User> findById(String id) throws Exception {
        MongoConnection connection = MongoDaoFactory.createConnection();
        Datastore ds = connection.getDatastore();
        User user = ds.find(User.class).field("_id").equal(id).get();
        return Arrays.asList(user);
    }
}
