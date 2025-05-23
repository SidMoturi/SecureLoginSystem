package security;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

public class HashUtil {

    private static final int SALT_LENGTH = 16; // Salt size in bytes
    private static final int ITERATIONS = 65536; // Number of hashing rounds
    private static final int KEY_LENGTH = 256; // Hash length in bits

    // Generates a random salt and returns it as a Base64 string
    public static String generateSalt() {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[SALT_LENGTH];
        random.nextBytes(salt);
        return Base64.getEncoder().encodeToString(salt);
    }

    // Hashes the password with the given salt using PBKDF2
    public static String hashPassword(String password, String salt) {
        try {
            byte[] saltBytes = Base64.getDecoder().decode(salt);
            PBEKeySpec spec = new PBEKeySpec(password.toCharArray(), saltBytes, ITERATIONS, KEY_LENGTH);
            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
            byte[] hash = factory.generateSecret(spec).getEncoded();
            return Base64.getEncoder().encodeToString(hash);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new RuntimeException("Error while hashing password", e);
        }
    }

    // Verifies if the password matches the expected hash
    public static boolean verifyPassword(String password, String salt, String expectedHash) {
        String hashedPassword = hashPassword(password, salt);
        return hashedPassword.equals(expectedHash);
    }
}
