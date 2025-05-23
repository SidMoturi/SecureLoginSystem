package utils;

public class InputValidator {

    // Validates the username format
    public boolean validateUsername(String username) {
        return username != null && username.matches("^[a-zA-Z0-9._-]{3,}$");
    }

    // Validates the password format
    public boolean validatePassword(String password) {
        return password != null && password.length() >= 8;
    }
}
