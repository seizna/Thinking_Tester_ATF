package utils;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

public class EncryptionUtils {
    static final String AES_KEY = "AES";

    public static String encryptAesKey(String data) throws Exception {
        SecretKey key = new SecretKeySpec(Base64.getDecoder().decode(ConfigReader.getProperty("base64_encoded_key")), AES_KEY);
        Cipher cipher = Cipher.getInstance(AES_KEY);
        cipher.init(Cipher.ENCRYPT_MODE, key);
        byte[] encryptedBytes = cipher.doFinal(data.getBytes());
        return Base64.getEncoder().encodeToString(encryptedBytes);
    }

    public static String decryptAesKey(String hashedPasswordFromDb) throws Exception {
        SecretKey key = new SecretKeySpec(Base64.getDecoder().decode(ConfigReader.getProperty("base64_encoded_key")), AES_KEY);
        Cipher cipher = Cipher.getInstance(AES_KEY);
        cipher.init(Cipher.DECRYPT_MODE, key);
        byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(hashedPasswordFromDb));
        return new String(decryptedBytes);
    }
}

