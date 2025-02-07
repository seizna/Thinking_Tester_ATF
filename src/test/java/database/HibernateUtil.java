package database;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {

    private final Logger LOGGER = LogManager.getLogger(HibernateUtil.class);
    private static HibernateUtil instance;
    private SessionFactory sessionFactory;


    private HibernateUtil() {
        try {
            Configuration config = new Configuration();
            config.configure("hibernate.cfg.xml");
            sessionFactory = config.buildSessionFactory();
            LOGGER.debug("SessionFactory initialized successfully.");
        } catch (Throwable ex) {
            LOGGER.error("Error initializing SessionFactory: {}.", ex.getMessage());
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static SessionFactory getSessionFactory() {
        if (instance == null || instance.sessionFactory == null || instance.sessionFactory.isClosed()) {
            instance = new HibernateUtil();
        }
        return instance.sessionFactory;
    }

    public static void shutdownSession() {
        if (instance != null && instance.sessionFactory != null && !instance.sessionFactory.isClosed()) {
            instance.sessionFactory.close();
            instance.sessionFactory = null;
        }
    }
}
