package auth;

import security.HashUtil;
import storage.Credential;
import storage.CredentialStore;

public class Main {

    public static void main(String[] args) {
        // Initialize components
        CredentialStore credentialStore = new CredentialStore();
        LoginManager loginManager = new LoginManager();

        // Create a test user
        String username = "testUser";
        String password = "testPassword";
        String salt = HashUtil.generateSalt();
        String hashedPassword = HashUtil.hashPassword(password, salt);
        Credential credential = new Credential(salt, hashedPassword);
        credentialStore.addCredentials(username, credential);

        // Attempt to login
        boolean loginSuccess = loginManager.login(username, password);
        System.out.println("Login success: " + loginSuccess);
    }
}
