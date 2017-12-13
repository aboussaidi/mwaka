package io.mwaka.service.impl;

import com.jcabi.aspects.Loggable;
import io.mwaka.dao.UserDao;
import io.mwaka.dao.factory.DaoFactory;
import io.mwaka.model.User;
import io.mwaka.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.text.IniRealm;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@Loggable
public class UserServiceImpl implements UserService {
    final static Logger logger = LoggerFactory.getLogger(new ProcessBuilder().environment().get("DEBUG"));
    private SecurityManager securityManager;

    @Override
    @Loggable(value = Loggable.DEBUG,prepend = true)
    public String login(String email, String password) throws Exception {
        IniRealm iniRealm = new IniRealm("classpath:/shiro.ini");
        SecurityManager securityManager = new DefaultSecurityManager(iniRealm);
        SecurityUtils.setSecurityManager(securityManager);

        Subject subject = SecurityUtils.getSubject();
        subject.getSession(true);
        UsernamePasswordToken token = new UsernamePasswordToken(email, password);
        try {
            subject.login(token);
        } catch (UnknownAccountException uae) {
            logger.error(uae.getMessage());
            return "";
        } catch (IncorrectCredentialsException ice) {
            logger.error(ice.getMessage());
            return "";
        } catch (LockedAccountException lae) {
            logger.error(lae.getMessage());
            return "";
        } catch (ExcessiveAttemptsException eae) {
            logger.error(eae.getMessage());
            return "";
        } catch (AuthenticationException ae) {
            logger.error(ae.getMessage());
            return "";
        }
        DaoFactory mongoFactory = DaoFactory.getDaoFactory(DaoFactory.MONGO);
        UserDao userDao = mongoFactory.getUserDao();
        List<User> users = userDao.findById(email);
        if (users != null && users.size() > 0) {
            subject.getSession().setAttribute("user", users.get(0));
        }
        return (String) subject.getSession().getId();
    }

    @Override
    @Loggable(value = Loggable.DEBUG,prepend = true)
    public String register(String userName, String password, String firstName, String lastName) throws Exception {
        User user = new User(userName, userName, password,
                new SecureRandomNumberGenerator().nextBytes(), firstName, lastName);
        DaoFactory mongoFactory = DaoFactory.getDaoFactory(DaoFactory.MONGO);
        UserDao userDao = mongoFactory.getUserDao();
        return userDao.createUser(user);
    }
}
