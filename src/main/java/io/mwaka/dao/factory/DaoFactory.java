package io.mwaka.dao.factory;

import io.mwaka.dao.UserDao;
import io.mwaka.dao.factory.mongo.MongoDaoFactory;
import io.mwaka.dao.factory.postgreSql.PostgreSqlDaoFactory;

public abstract class DaoFactory {
    public static final int MONGO = 1;
    public static final int POSTGRESQL = 2;

    public abstract UserDao getUserDao();

    public static DaoFactory getDaoFactory(int whichFactory) {
        switch (whichFactory) {
            case MONGO:
                return new MongoDaoFactory();
            case POSTGRESQL:
                return new PostgreSqlDaoFactory();
            default:
                return null;
        }
    }
}
