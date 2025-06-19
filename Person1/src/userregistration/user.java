package userregistration;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Scanner;

public class UserRegistration {

    // File to store user data
    private static final String USER_DATA_FILE = "users.txt";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("=== User Registration ===");
        System.out.print("Enter username: ");
        String username = scanner.nextLine();

        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        if (isUsernameTaken(username)) {
            System.out.println("Username already taken. Please try again.");
            return;
        }

        if (!isPasswordComplex(password)) {
            System.out.println("Password must be at least 8 characters and include letters and numbers.");
            return;
        }

        // Register the user
        registerUser(username, password);
        System.out.println("Registration successful!");
    }

    // ✅ Check if username already exists
    private static boolean isUsernameTaken(String username) {
        try (BufferedReader reader = new BufferedReader(new FileReader(USER_DATA_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts.length >= 1 && parts[0].equals(username)) {
                    return true;
                }
            }
        } catch (IOException e) {
            // File might not exist yet
        }
        return false;
    }

    // ✅ Ensure password has minimum complexity
    private static boolean isPasswordComplex(String password) {
        return password.length() >= 8 && password.matches(".*[A-Za-z].*") && password.matches(".*[0-9].*");
    }

    // ✅ Register the user with hashing and salting
    private static void registerUser(String username, String password) {
        try {
            // Generate salt
            byte[] salt = generateSalt();

            // Hash password with salt
            String hashedPassword = hashPassword(password, salt);

            // Convert salt to hex
            String saltHex = bytesToHex(salt);

            // Store in format: username|salt|hashedPassword
            String entry = username + "|" + saltHex + "|" + hashedPassword;

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(USER_DATA_FILE, true))) {
                writer.write(entry);
                writer.newLine();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ✅ Generate random salt
    private static byte[] generateSalt() {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        return salt;
    }

    // ✅ Hash password using SHA-256 + salt
    private static String hashPassword(String password, byte[] salt) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(salt);
        byte[] hashedBytes = md.digest(password.getBytes(StandardCharsets.UTF_8));
        return bytesToHex(hashedBytes);
    }

    // ✅ Convert bytes to hex string
    private static String bytesToHex(byte[] bytes) {
        StringBuilder hex = new StringBuilder();
        for (byte b : bytes) {
            hex.append(String.format("%02x", b));
        }
        return hex.toString();
    }
}
