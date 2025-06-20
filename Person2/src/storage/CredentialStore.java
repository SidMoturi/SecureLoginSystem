package storage;

import java.util.Optional;
import java.util.HashMap;
import java.util.Map;

public class CredentialStore {

    private Map<String, Credential> credentials = new HashMap<>();

    // Retrieves credentials for a given username
    public Optional<Credential> getCredentials(String username) {
        return Optional.ofNullable(credentials.get(username));
    }

    // Adds credentials for a user (for testing purposes)
    public void addCredentials(String username, Credential credential) {
        credentials.put(username, credential);
    }
}
