package hooks;

import database.HibernateUtil;
import driversetup.WebDriverManager;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import org.hibernate.HibernateException;

public class Hooks {

    @Before(order = 1, value = "@UI")
    public static void openBrowser() {
        WebDriverManager.getDriver();
        System.out.println("Browser is opened");
    }

    @Before(order = 2, value = "@DB")
    public static void setUpHibernateSession() {
        try {
            HibernateUtil.getSessionFactory();
            System.out.println("Hibernate instance created");
        } catch (HibernateException e) {
            System.out.println("Failed to set up Hibernate instance");
            e.printStackTrace();
        }
    }

    @After(order = 1, value = "@UI")
    public static void closeBrowser() {
        WebDriverManager.closeDriver();
        System.out.println("Browser is closed");
    }

    @After(order = 2, value = "@DB")
    public static void shutDownHibernateSession() {
        HibernateUtil.shutdownSession();
        System.out.println("Hibernate session is closed");
    }

}
