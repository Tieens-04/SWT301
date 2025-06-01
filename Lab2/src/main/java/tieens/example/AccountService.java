package tieens.example;

import java.util.regex.Pattern;

public class AccountService {

    private static final String EMAIL_PATTERN =
            "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@" +
                    "(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";

    private static final Pattern pattern = Pattern.compile(EMAIL_PATTERN);

    public boolean registerAccount(String username, String password, String email) {

        if (username == null || username.trim().isEmpty()) {
            return false;
        }

        if (password == null || password.length() <= 6) {
            return false;
        }

        if (!isValidEmail(email)) {
            return false;
        }

        return true;
    }

    public boolean isValidEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            return false;
        }
        return pattern.matcher(email).matches();
    }

    public boolean isValidUsername(String username) {
        return username != null && !username.trim().isEmpty();
    }

    public boolean isValidPassword(String password) {
        return password != null && password.length() > 6;
    }
}
