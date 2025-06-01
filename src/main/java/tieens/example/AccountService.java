package tieens.example;

import java.util.regex.Pattern;

public class AccountService {

    // Pattern để validate email
    private static final String EMAIL_PATTERN =
            "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@" +
                    "(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";

    private static final Pattern pattern = Pattern.compile(EMAIL_PATTERN);

    /**
     * Đăng ký tài khoản mới
     * @param username tên đăng nhập
     * @param password mật khẩu
     * @param email địa chỉ email
     * @return true nếu đăng ký thành công, false nếu thất bại
     */
    public boolean registerAccount(String username, String password, String email) {
        // Kiểm tra username không được null hoặc rỗng
        if (username == null || username.trim().isEmpty()) {
            return false;
        }

        // Kiểm tra password phải lớn hơn 6 ký tự
        if (password == null || password.length() <= 6) {
            return false;
        }

        // Kiểm tra email hợp lệ
        if (!isValidEmail(email)) {
            return false;
        }

        // Nếu tất cả điều kiện đều thỏa mãn
        return true;
    }

    /**
     * Kiểm tra email có hợp lệ không
     * @param email địa chỉ email cần kiểm tra
     * @return true nếu email hợp lệ, false nếu không hợp lệ
     */
    public boolean isValidEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            return false;
        }
        return pattern.matcher(email).matches();
    }

    /**
     * Kiểm tra username có hợp lệ không
     * @param username tên đăng nhập
     * @return true nếu username hợp lệ
     */
    public boolean isValidUsername(String username) {
        return username != null && !username.trim().isEmpty();
    }

    /**
     * Kiểm tra password có hợp lệ không
     * @param password mật khẩu
     * @return true nếu password hợp lệ (> 6 ký tự)
     */
    public boolean isValidPassword(String password) {
        return password != null && password.length() > 6;
    }
}
