package stepdefinitions;

import database.HibernateUtil;
import database.Users;
import io.cucumber.java.en.And;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
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

    static final String aesKey = "AES";

    @And("User with email under test is present in DB")
    public Users selectUserByEmail() {
        Session session = HibernateUtil.getSessionFactory().openSession();

        try {
            TypedQuery<Users> query = session.createQuery("FROM Users u WHERE u.email = :email", Users.class);
            query.setParameter("email", ScenarioContext.getContext(ContextKey.EMAIL).toString());
            return query.getSingleResult();
        } catch (NoResultException ex) {
            System.out.println("User with email " + getContext(ContextKey.EMAIL).toString() + " not found in the DB.");
            return null;
        }
    }

    public void insertRegisteredUser(String email, String password, String firstName, String lastName) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();

            Users existingUser = selectUserByEmail();
            if (existingUser != null) {
                System.out.println("User with email " + email + " already exists.");
            } else {
                Users newUser = new Users();
                newUser.setFirstName(firstName);
                newUser.setLastName(lastName);
                newUser.setEmail(email);
                newUser.setPassword(password);
                newUser.setCreatedAt(LocalDateTime.now());

                session.persist(newUser);
                System.out.println(email + " is successfully inserted.");
            }
            transaction.commit();
        }
    }

    public static String encryptAesKey(String data) throws Exception {
        SecretKey key = new SecretKeySpec(Base64.getDecoder().decode("TlgnhCsaMWUk6ev/367w1g=="), aesKey);
        Cipher cipher = Cipher.getInstance(aesKey);
        cipher.init(Cipher.ENCRYPT_MODE, key);
        byte[] encryptedBytes = cipher.doFinal(data.getBytes());
        return Base64.getEncoder().encodeToString(encryptedBytes);
    }

    public static String decryptAesKey(String hashedPasswordFromDb) throws Exception {
        SecretKey key = new SecretKeySpec(Base64.getDecoder().decode("TlgnhCsaMWUk6ev/367w1g=="), aesKey);
        Cipher cipher = Cipher.getInstance(aesKey);
        cipher.init(Cipher.DECRYPT_MODE, key);
        byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(hashedPasswordFromDb));
        return new String(decryptedBytes);
    }
}