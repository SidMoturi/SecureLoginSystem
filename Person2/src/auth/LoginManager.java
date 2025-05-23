package auth;

import security.BruteForceProtector;
import storage.CredentialStore;
import storage.Credential;
import java.util.Optional;

public class LoginManager {

    private final Authenticator authenticator;
    private final BruteForceProtector bruteForceProtector;
    private final CredentialStore credentialStore;

    public LoginManager() {
        this.authenticator = new Authenticator();
        this.bruteForceProtector = new BruteForceProtector();
        this.credentialStore = new CredentialStore();
    }

    // Handles the login process
    public boolean login(String username, String password) {
        if (bruteForceProtector.isAccountLocked(username)) {
            System.out.println("Account is temporarily locked due to multiple failed login attempts.");
            return false;
        }

        Optional<Credential> credentialOpt = credentialStore.getCredentials(username);
        if (credentialOpt.isEmpty()) {
            System.out.println("Invalid credentials.");
            bruteForceProtector.recordFailedAttempt(username);
            return false;
        }

        Credential credential = credentialOpt.get();
        boolean isAuthenticated = authenticator.verifyPassword(password, credential.getSalt(), credential.getHashedPassword());

        if (isAuthenticated) {
            System.out.println("Login successful.");
            bruteForceProtector.resetAttempts(username);
            return true;
        } else {
            System.out.println("Invalid credentials.");
            bruteForceProtector.recordFailedAttempt(username);
            return false;
        }
    }
}

