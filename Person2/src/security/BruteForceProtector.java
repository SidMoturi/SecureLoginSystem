package security;

import java.util.HashMap;
import java.util.Map;

public class BruteForceProtector {

    private static final int MAX_ATTEMPTS = 5; // Max allowed attempts
    private Map<String, Integer> attempts = new HashMap<>();

    // Checks if the account is locked
    public boolean isAccountLocked(String username) {
        return attempts.getOrDefault(username, 0) >= MAX_ATTEMPTS;
    }

    // Records a failed login attempt
    public void recordFailedAttempt(String username) {
        attempts.put(username, attempts.getOrDefault(username, 0) + 1);
    }

    // Resets the attempt count for a user
    public void resetAttempts(String username) {
        attempts.put(username, 0);
    }
}
