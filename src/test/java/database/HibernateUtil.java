package database;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {

    private static HibernateUtil instance;
    private final SessionFactory sessionFactory;


    private HibernateUtil() {
        try {
            Configuration config = new Configuration();
            config.configure("hibernate.cfg.xml");
            sessionFactory = config.buildSessionFactory();
        } catch (Throwable ex) {
            System.err.println("Error initializing SessionFactory: " + ex.getMessage());
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static SessionFactory getSessionFactory() {
        if (instance == null) {
            instance = new HibernateUtil();
        }
        return instance.sessionFactory;
    }

    public static void shutdownSession() {
        if (instance != null && instance.sessionFactory != null && !instance.sessionFactory.isClosed()) {
            instance.sessionFactory.close();
        }
    }
}
