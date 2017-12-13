package io.mwaka.dao.factory.mongo;


import static java.lang.String.format;

import io.mwaka.model.User;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoClientURI;
import com.mongodb.MongoException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MongoConnection {

    final static Logger logger = LoggerFactory.getLogger(new ProcessBuilder().environment().get("DEBUG"));
    private static MongoConnection instance = new MongoConnection();

    private MongoClient mongo = null;
    private Datastore dataStore = null;
    private Morphia morphia = null;

    private MongoConnection() {}
    protected MongoClient getMongo() throws Exception {
        if (mongo == null) {
            //TODO : wait for confirmation after writing on the DB
            MongoClientOptions.Builder options = MongoClientOptions.builder()
                    .connectionsPerHost(20)
                    .maxConnectionIdleTime((60 * 1_000))
                    .maxConnectionLifeTime((120 * 1_000));
            ProcessBuilder processBuilder = new ProcessBuilder();
            String db = processBuilder.environment().get("MONGODB_URI");
            MongoClientURI uri = new MongoClientURI(db, options);
            logger.info("About to connect to MongoDB @ " + uri.toString());
            try {
                mongo = new MongoClient(uri);
            } catch (MongoException me) {
                logger.error("An error occoured when connecting to MongoDB", me);
                throw new Exception("An error occoured when connecting to MongoDB");
            } catch (Exception e) {
                logger.error("An error occoured when connecting to MongoDB", e);
                throw new Exception("An error occoured when connecting to MongoDB");
            }
        }
        return mongo;
    }

    protected Morphia getMorphia() {
        if (morphia == null) {
            morphia = new Morphia();
            morphia.mapPackageFromClass(User.class);
        }
        return morphia;
    }

    public Datastore getDatastore() throws Exception {
        if (dataStore == null) {
            String dbName = "heroku_wh7ntrsm";
            dataStore = getMorphia().createDatastore(getMongo(), dbName);
        }
        return dataStore;
    }

    protected void init() throws Exception {
        getMongo();
        getMorphia();
        getDatastore();
    }

    protected void close() {
        if (mongo != null) {
            try {
                mongo.close();
                mongo = null;
                morphia = null;
                dataStore = null;
            } catch (Exception e) {
                logger.error(format("An error occurred when closing the MongoDB connection\n%s", e.getMessage()));
            }
        } else {
            logger.warn("Mongo Object was null, wouldn't close connection");
        }
    }

    protected static MongoConnection getInstance() {
        return instance;
    }
}