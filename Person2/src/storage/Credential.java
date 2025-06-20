package storage;

public class Credential {
    private final String salt;
    private final String hashedPassword;

    public Credential(String salt, String hashedPassword) {
        this.salt = salt;
        this.hashedPassword = hashedPassword;
    }

    public String getSalt() {
        return salt;
    }

    public String getHashedPassword() {
        return hashedPassword;
    }
}
