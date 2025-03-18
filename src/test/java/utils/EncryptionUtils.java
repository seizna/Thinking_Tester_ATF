package utils;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

/**
 * The {@code EncryptionUtils} class provides utility methods for encrypting and decrypting data using AES encryption.
 *
 * <p>This class includes the following methods:</p>
 *
 * <ul>
 *   <li>
 *     <strong>encryptAesKey</strong> - Encrypts a given string using AES encryption.
 *     <ul>
 *       <li><strong>Parameter:</strong> {@code rawPassword} - the plaintext password to be encrypted.</li>
 *       <li><strong>Returns:</strong> a Base64-encoded string representing the encrypted rawPassword.</li>
 *       <li><strong>Throws:</strong> {@code Exception} if encryption fails.</li>
 *     </ul>
 *   </li>
 *   <li>
 *     <strong>decryptAesKey</strong> - Decrypts a Base64-encoded, AES-encrypted string.
 *     <ul>
 *       <li><strong>Parameter:</strong> {@code encryptedPassword} - encrypted password retrieved from the database.</li>
 *       <li><strong>Returns:</strong> the decrypted plaintext password.</li>
 *       <li><strong>Throws:</strong> {@code Exception} if decryption fails.</li>
 *     </ul>
 *   </li>
 * </ul>
 */

public class EncryptionUtils {
    static final String AES_KEY = "AES";

    public static String encryptAesKey(String rawPassword) throws Exception {
        SecretKey key = new SecretKeySpec(Base64.getDecoder().decode(ConfigReader.getProperty("base64_encoded_key")), AES_KEY);
        Cipher cipher = Cipher.getInstance(AES_KEY);
        cipher.init(Cipher.ENCRYPT_MODE, key);
        byte[] encryptedBytes = cipher.doFinal(rawPassword.getBytes());
        return Base64.getEncoder().encodeToString(encryptedBytes);
    }

    public static String decryptAesKey(String encryptedPassword) throws Exception {
        SecretKey key = new SecretKeySpec(Base64.getDecoder().decode(ConfigReader.getProperty("base64_encoded_key")), AES_KEY);
        Cipher cipher = Cipher.getInstance(AES_KEY);
        cipher.init(Cipher.DECRYPT_MODE, key);
        byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(encryptedPassword));
        return new String(decryptedBytes);
    }
}

