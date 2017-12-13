package io.mwaka.authentication;

import com.mongodb.MongoClient;
import io.mwaka.dao.UserDao;
import io.mwaka.dao.factory.DaoFactory;
import io.mwaka.model.User;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class MongoRealm extends AuthorizingRealm{
    private MongoClient mongoClient = null;
    private Datastore ds = null;
    final static Logger logger = LoggerFactory.getLogger(new ProcessBuilder().environment().get("DEBUG"));

    public MongoRealm() {
        mongoClient = new MongoClient();
        ds = new Morphia().createDatastore(mongoClient, "LoginHistory");
    }

    @Override
    public boolean supports(AuthenticationToken token) {
        if (token instanceof UsernamePasswordToken) {
            return true;
        }
        return true;
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection pc) {
        return null;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken at) throws AuthenticationException {
        DaoFactory mongoFactory = DaoFactory.getDaoFactory(DaoFactory.MONGO);
        UserDao userDao = mongoFactory.getUserDao();
        try {
            List<User> users = userDao.findById(at.getPrincipal().toString());
            if (users != null && users.size()>0) {
                return new SimpleAuthenticationInfo(users.get(0).getAlias(), users.get(0).getPasswordHash(), ByteSource.Util.bytes(users.get(0).getPasswordSalt()), this.getName());
            }
            throw new AuthenticationException();
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new AuthenticationException();
        }
    }
}
