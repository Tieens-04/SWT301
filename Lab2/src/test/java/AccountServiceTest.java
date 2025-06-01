import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import tieens.example.AccountService;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.*;

public class AccountServiceTest {

    private AccountService accountService;
    private StringBuilder testResults;

    @BeforeEach
    void setUp() {
        accountService = new AccountService();
        testResults = new StringBuilder();
        testResults.append("=== UNIT TEST RESULTS ===\n");
        testResults.append("Test Date: ").append(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))).append("\n\n");
    }

    @Test
    @DisplayName("Test đăng ký với dữ liệu hợp lệ")
    void testRegisterAccount_ValidData() {
        // Arrange
        String username = "validuser";
        String password = "validpass123";
        String email = "valid@example.com";

        // Act
        boolean result = accountService.registerAccount(username, password, email);

        // Assert
        assertTrue(result, "Đăng ký với dữ liệu hợp lệ phải thành công");

        // Log kết quả
        testResults.append("✓ Test Valid Data: PASSED\n");
    }

    @Test
    @DisplayName("Test đăng ký với username rỗng")
    void testRegisterAccount_EmptyUsername() {
        // Arrange & Act & Assert
        assertFalse(accountService.registerAccount("", "password123", "test@email.com"));
        assertFalse(accountService.registerAccount(null, "password123", "test@email.com"));

        testResults.append("✓ Test Empty Username: PASSED\n");
    }

    @Test
    @DisplayName("Test đăng ký với password ngắn")
    void testRegisterAccount_ShortPassword() {
        // Arrange & Act & Assert
        assertFalse(accountService.registerAccount("user", "short", "test@email.com"));
        assertFalse(accountService.registerAccount("user", "123456", "test@email.com")); // đúng 6 ký tự

        testResults.append("✓ Test Short Password: PASSED\n");
    }

    @Test
    @DisplayName("Test đăng ký với email không hợp lệ")
    void testRegisterAccount_InvalidEmail() {
        // Arrange & Act & Assert
        assertFalse(accountService.registerAccount("user", "password123", "invalid-email"));
        assertFalse(accountService.registerAccount("user", "password123", "test@"));
        assertFalse(accountService.registerAccount("user", "password123", "@test.com"));

        testResults.append("✓ Test Invalid Email: PASSED\n");
    }

    @Test
    @DisplayName("Test validation email")
    void testIsValidEmail() {
        // Valid emails
        assertTrue(accountService.isValidEmail("test@example.com"));
        assertTrue(accountService.isValidEmail("user.name@domain.co.uk"));
        assertTrue(accountService.isValidEmail("user+tag@example.org"));

        // Invalid emails
        assertFalse(accountService.isValidEmail("invalid-email"));
        assertFalse(accountService.isValidEmail("test@"));
        assertFalse(accountService.isValidEmail("@test.com"));
        assertFalse(accountService.isValidEmail(""));
        assertFalse(accountService.isValidEmail(null));

        testResults.append("✓ Test Email Validation: PASSED\n");
    }

    @ParameterizedTest
    @DisplayName("Test với dữ liệu từ CSV file")
    @CsvFileSource(resources = "/test-data.csv", numLinesToSkip = 1)
    void testRegisterAccountWithCsvData(String username, String password, String email, boolean expected) {
        // Act
        boolean actualResult = accountService.registerAccount(username, password, email);

        // Assert
        assertEquals(expected, actualResult,
                String.format("Test failed for data: username='%s', password='%s', email='%s'. Expected: %s, Actual: %s",
                        username, password, email, expected, actualResult));

        // Log chi tiết kết quả
        String status = (expected == actualResult) ? "PASSED" : "FAILED";
        testResults.append(String.format("✓ CSV Test [%s,%s,%s] Expected: %s, Actual: %s - %s\n",
                (username != null ? username : "null"), password, email, expected, actualResult, status));
    }

    @Test
    @DisplayName("Test các trường hợp biên")
    void testEdgeCases() {
        // Password đúng 7 ký tự (boundary case)
        assertTrue(accountService.registerAccount("user", "1234567", "test@email.com"));

        // Email với nhiều dấu chấm
        assertTrue(accountService.registerAccount("user", "password123", "test.user@sub.domain.com"));

        // Username chỉ có khoảng trắng
        assertFalse(accountService.registerAccount("   ", "password123", "test@email.com"));

        testResults.append("✓ Test Edge Cases: PASSED\n");
    }

}
