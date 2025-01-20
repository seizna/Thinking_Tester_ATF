package database;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {
    private static SessionFactory sessionFactory;

    private static SessionFactory buildSessionFactory() {
        if (sessionFactory == null) {
            try {
                Configuration config = new Configuration();
                config.configure("hibernate.cfg.xml");
                sessionFactory = config.buildSessionFactory();
            } catch (Throwable ex) {
                System.err.println("Error initializing SessionFactory: " + ex.getMessage());
                throw new ExceptionInInitializerError(ex);
            }
        }
        return sessionFactory;
    }

    public static SessionFactory getSessionFactory() {
        return buildSessionFactory();
    }

    public static void shutdownSession() {
        if (sessionFactory != null && !sessionFactory.isClosed()) {
            sessionFactory.close();
        }
    }
}
