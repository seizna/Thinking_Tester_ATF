package database;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {

    private final Logger LOG = LogManager.getLogger(HibernateUtil.class);
    private static HibernateUtil instance;
    private SessionFactory sessionFactory;


    private HibernateUtil() {
        try {
            Configuration config = new Configuration();
            config.configure("hibernate.cfg.xml");
            sessionFactory = config.buildSessionFactory();
            LOG.debug("SessionFactory initialized successfully.");
        } catch (HibernateException ex) {
            LOG.error("Error initializing SessionFactory. This might be due to invalid configuration or invalid mapping information. Exception details: {}", ex.getMessage());
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
