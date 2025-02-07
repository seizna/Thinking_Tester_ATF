package stepdefinitions;

import database.HibernateUtil;
import database.Users;
import io.cucumber.java.en.And;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;
import scenariocontext.ContextKey;
import scenariocontext.ScenarioContext;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.time.LocalDateTime;
import java.util.Base64;

import static scenariocontext.ScenarioContext.getContext;

public class DbSteps {

    private final Logger LOGGER = LogManager.getLogger(DbSteps.class);
    static final String AES_KEY = "AES";


    @And("User with email under test is present in DB")
    public void insertRegisteredUser() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();

            Users existingUser = selectUserByEmail();
            if (existingUser != null) {
                LOGGER.error("User with {} already exists.", getContext(ContextKey.EMAIL).toString());
            } else {
                Users newUser = new Users();
                newUser.setFirstName(getContext(ContextKey.FIRST_NAME).toString());
                newUser.setLastName(getContext(ContextKey.LAST_NAME).toString());
                newUser.setEmail(getContext(ContextKey.EMAIL).toString());
                newUser.setPassword(getContext(ContextKey.ENCRYPTED_PASSWORD).toString());
                newUser.setCreatedAt(LocalDateTime.now());

                session.persist(newUser);
                LOGGER.info("User with email {} is successfully inserted.", getContext(ContextKey.EMAIL).toString());
            }
            transaction.commit();
        } catch (Exception ex) {
            LOGGER.error("Database transaction failed while inserting user with email {}.", getContext(ContextKey.EMAIL), ex);
        }
    }

    private Users selectUserByEmail() {
        Session session = HibernateUtil.getSessionFactory().openSession();

        try {
            TypedQuery<Users> query = session.createQuery("FROM Users u WHERE u.email = :email", Users.class);
            query.setParameter("email", ScenarioContext.getContext(ContextKey.EMAIL).toString());
            Users user = query.getSingleResult();
            LOGGER.info("User with the following email {} retrieved from DB", user.getEmail());
            return user;
        } catch (NoResultException ex) {
            LOGGER.warn("User with email {} not found in the DB.", getContext(ContextKey.EMAIL).toString());
            return null;
        }
    }

    public Users selectLastInsertedUser() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            TypedQuery<Users> query = session.createQuery("FROM Users ORDER BY id DESC", Users.class);
            query.setMaxResults(1);
            Users lastUser = query.getSingleResult();
            LOGGER.info("Last inserted user retrieved with the following email: {}", lastUser.getEmail());
            return lastUser;
        } catch (NoResultException ex) {
            LOGGER.error("Something went wrong while retrieving last inserted user.");
            return null;
        }
    }

    public String encryptAesKey(String data) throws Exception {
        SecretKey key = new SecretKeySpec(Base64.getDecoder().decode("TlgnhCsaMWUk6ev/367w1g=="), AES_KEY);
        Cipher cipher = Cipher.getInstance(AES_KEY);
        cipher.init(Cipher.ENCRYPT_MODE, key);
        byte[] encryptedBytes = cipher.doFinal(data.getBytes());
        return Base64.getEncoder().encodeToString(encryptedBytes);
    }

    public String decryptAesKey(String hashedPasswordFromDb) throws Exception {
        SecretKey key = new SecretKeySpec(Base64.getDecoder().decode("TlgnhCsaMWUk6ev/367w1g=="), AES_KEY);
        Cipher cipher = Cipher.getInstance(AES_KEY);
        cipher.init(Cipher.DECRYPT_MODE, key);
        byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(hashedPasswordFromDb));
        return new String(decryptedBytes);
    }
}