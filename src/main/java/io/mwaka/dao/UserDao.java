package io.mwaka.dao;

import io.mwaka.model.User;

import java.util.List;

public interface UserDao {
    List<User> findById(String id) throws Exception;
    String createUser(User user) throws Exception;
}
