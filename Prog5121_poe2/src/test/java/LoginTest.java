import com.mycompany.prog5121_poe2.Login;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


/**
 *
 * @author leago
 */
public class LoginTest {
    
   private Login login;

    @BeforeEach
    void setUp() {
        login = new Login();
    }

    // ---------- assertTrue / assertFalse tests ----------

    @Test
    void checkUserName_CorrectlyFormatted_ReturnsTrue() {
        assertTrue(login.checkUserName("kyl_1"));
    }

    @Test
    void checkUserName_IncorrectlyFormatted_ReturnsFalse() {
        assertFalse(login.checkUserName("kyle!!!!!!"));
    }

    @Test
    void checkPasswordComplexity_MeetsRequirements_ReturnsTrue() {
        assertTrue(login.checkPasswordComplexity("Ch&&sec@ke99!"));
    }

    @Test
    void checkPasswordComplexity_DoesNotMeetRequirements_ReturnsFalse() {
        assertFalse(login.checkPasswordComplexity("password"));
    }

    @Test
    void checkCellPhoneNumber_CorrectlyFormatted_ReturnsTrue() {
        assertTrue(login.checkCellPhoneNumber("+27838968976"));
    }

    @Test
    void checkCellPhoneNumber_IncorrectlyFormatted_ReturnsFalse() {
        assertFalse(login.checkCellPhoneNumber("08966553"));
    }

    @Test
    void loginUser_CorrectCredentials_ReturnsTrue() {
        login.registerUser("Kyle", "Peters", "kyl_1", "Ch&&sec@ke99!", "+27838968976");
        assertTrue(login.loginUser("kyl_1", "Ch&&sec@ke99!"));
    }

    @Test
    void loginUser_IncorrectCredentials_ReturnsFalse() {
        login.registerUser("Kyle", "Peters", "kyl_1", "Ch&&sec@ke99!", "+27838968976");
        assertFalse(login.loginUser("kyl_1", "wrongPassword1!"));
    }

    // ---------- assertEquals tests (registerUser messages) ----------

    @Test
    void registerUser_UsernameIncorrectlyFormatted_ReturnsUsernameMessage() {
        String result = login.registerUser("Kyle", "Peters", "kyle!!!!!!",
                "Ch&&sec@ke99!", "+27838968976");
        assertEquals("Username is not correctly formatted; please ensure that "
                + "your username contains an underscore and is no more than "
                + "five characters in length.", result);
    }

    @Test
    void registerUser_PasswordDoesNotMeetComplexity_ReturnsPasswordMessage() {
        String result = login.registerUser("Kyle", "Peters", "kyl_1",
                "password", "+27838968976");
        assertEquals("Password is not correctly formatted; please ensure that "
                + "the password contains at least eight characters, a capital "
                + "letter, a number, and a special character.", result);
    }

    @Test
    void registerUser_CellPhoneIncorrectlyFormatted_ReturnsCellMessage() {
        String result = login.registerUser("Kyle", "Peters", "kyl_1",
                "Ch&&sec@ke99!", "08966553");
        assertEquals("Cell phone number incorrectly formatted or does not "
                + "contain international code.", result);
    }

    @Test
    void registerUser_AllFieldsValid_ReturnsSuccessMessage() {
        String result = login.registerUser("Kyle", "Peters", "kyl_1",
                "Ch&&sec@ke99!", "+27838968976");
        assertEquals("Username successfully captured. Password successfully "
                + "captured. Cell phone number successfully added. "
                + "Registration successful, welcome Kyle Peters!", result);
    }

    //---------- assertEquals (login welcome message) ----------
    //test table pairs this exact welcome-message
    // output with a row labelled "Username is correctly formatted" and
    // test data "kyl_1"
    @Test
    void returnLoginStatus_CorrectCredentials_ReturnsWelcomeMessage() {
        login.registerUser("Kyle", "Peters", "kyl_1", "Ch&&sec@ke99!", "+27838968976");
        String result = login.returnLoginStatus("kyl_1", "Ch&&sec@ke99!");
        assertEquals("Welcome Kyle, Peters it is great to see you again.", result);
    }

    @Test
    void returnLoginStatus_IncorrectCredentials_ReturnsErrorMessage() {
        login.registerUser("Kyle", "Peters", "kyl_1", "Ch&&sec@ke99!", "+27838968976");
        String result = login.returnLoginStatus("kyl_1", "wrongPassword1!");
        assertEquals("Username or password incorrect, please try again.", result);
    } 
   
}
