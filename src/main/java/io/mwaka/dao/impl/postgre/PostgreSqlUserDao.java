package io.mwaka.dao.impl.postgre;

import io.mwaka.dao.UserDao;
import io.mwaka.model.User;

import java.util.List;

public class PostgreSqlUserDao implements UserDao{
    @Override
    public List<User> findById(String id) {
        return null;
    }

    @Override
    public String createUser(User user) {
        return null;
    }
}
