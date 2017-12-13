package io.mwaka.dao.factory.mongo;

import io.mwaka.dao.UserDao;
import io.mwaka.dao.factory.DaoFactory;
import io.mwaka.dao.impl.mongo.MongoUserDao;

public class MongoDaoFactory extends DaoFactory {
    public static MongoConnection createConnection() throws Exception {
        MongoConnection connection = null;
        try {
            connection = MongoConnection.getInstance();
            connection.init();
        } catch (Exception e) {
            throw new Exception("An error occoured when connecting to MongoDB");
        }
        return connection;
    }
    @Override
    public UserDao getUserDao() {
        return new MongoUserDao();
    }
}
