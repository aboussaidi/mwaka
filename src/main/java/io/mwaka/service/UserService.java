package io.mwaka.service;

import com.jcabi.aspects.Loggable;
@Loggable
public interface UserService {
    @Loggable(value = Loggable.DEBUG,prepend = true)
    public String login(String user, String password) throws Exception;
    @Loggable(value = Loggable.DEBUG,prepend = true)
    public String register(String userName, String password, String firstName, String lastName) throws Exception;
}
