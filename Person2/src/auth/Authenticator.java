package auth;

import security.HashUtil;

public class Authenticator {

    // Verifies the password using the stored salt and hash
    public boolean verifyPassword(String password, String salt, String storedHash) {
        return HashUtil.verifyPassword(password, salt, storedHash);
    }
}
