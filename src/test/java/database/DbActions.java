package database;

import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class DbActions {

    private final Logger LOG = LogManager.getLogger(DbActions.class);
    Session session = HibernateUtil.getSessionFactory().openSession();
    Transaction transaction = null;

    public void insertUser(Users user) {
        try {
            transaction = session.beginTransaction();
            session.persist(user);
            transaction.commit();
        } catch (Exception ex) {
            LOG.error("Error occurred while inserting user:", ex);
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }

    public Users selectUserByEmail(String email) {
        try {
            TypedQuery<Users> query = session.createQuery("FROM Users u WHERE u.email = :email", Users.class);
            query.setParameter("email", email);
            Users user = query.getSingleResult();
            LOG.info("User with the following email {} retrieved from DB", user.getEmail());
            return user;
        } catch (NoResultException ex) {
            LOG.debug("User with email {} not found in the DB.", email);
            return null;
        }
    }


    public void removeUser(Users user) {
        try {
            transaction = session.beginTransaction();
            session.remove(user);
            transaction.commit();
        } catch (Exception ex) {
            LOG.error("Error occurred while deleting user: ", ex);
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }


    public Users selectLastInsertedUser() {
        try {
            TypedQuery<Users> query = session.createQuery("FROM Users ORDER BY id DESC", Users.class);
            query.setMaxResults(1);
            return query.getSingleResult();
        } catch (NoResultException ex) {
            LOG.error("Users table has no records, therefore no result can be returned.");
            return null;
        }
    }
}