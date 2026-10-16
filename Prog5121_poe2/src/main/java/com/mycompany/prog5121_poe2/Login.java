package com.mycompany.prog5121_poe2;

import java.util.regex.Pattern;

/**
 *
 * @author leago
 */
public class Login {
    
    // South African cell numbers: the '+' plus the '27' international
    // country code, followed by no more than ten further digits.
     static final Pattern CELL_PHONE_PATTERN = Pattern.compile("^\\+27\\d{1,10}$");

    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private String cellPhoneNumber;

    public Login() {
    }

    //Makes sure that the username is not longer than five characters and that it has an underscore '_'
    
    public boolean checkUserName(String username) {
        if (username == null) {
            return false;
        }
        return username.contains("_") && username.length() <= 5;
    }
    
    // Makes sure that a password is at least eight characters long and contains a
    // capital letter, a number, and a special character.
    
    public boolean checkPasswordComplexity(String password) {
        if (password == null || password.length() < 8) {
            return false;
        }
        boolean hasCapital = false;
        boolean hasDigit = false;
        boolean hasSpecial = false;

        for (char c : password.toCharArray()) {
            if (Character.isUpperCase(c)) {
                hasCapital = true;
            } else if (Character.isDigit(c)) {
                hasDigit = true;
            } else if (!Character.isLetterOrDigit(c)) {
                hasSpecial = true;
            }
        }
        return hasCapital && hasDigit && hasSpecial;
    }

    // a cell phone number is the correct length and contains the
    // South African international country code (+27).
    
    public boolean checkCellPhoneNumber(String cellPhoneNumber) {
        return cellPhoneNumber != null && CELL_PHONE_PATTERN.matcher(cellPhoneNumber).matches();
    }

    //Validates and stores a new user's registration details.
     //Username, then password, then cell phone number registration checks are in order.
     //@return a message describing the outcome of registration.
    
    public String registerUser(String firstName, String lastName, String username,
                                String password, String cellPhoneNumber) {

        if (!checkUserName(username)) {
            return "Username is not correctly formatted; please ensure that your "
                    + "username contains an underscore and is no more than five "
                    + "characters in length.";
        }
        if (!checkPasswordComplexity(password)) {
            return "Password is not correctly formatted; please ensure that the "
                    + "password contains at least eight characters, a capital "
                    + "letter, a number, and a special character.";
        }
        if (!checkCellPhoneNumber(cellPhoneNumber)) {
            return "Cell phone number incorrectly formatted or does not contain "
                    + "international code.";
        }

        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
        this.cellPhoneNumber = cellPhoneNumber;

        return "Username successfully captured. Password successfully captured. "
                + "Cell phone number successfully added. Registration successful, "
                + "welcome " + firstName + " " + lastName + "!";
    }

     //Verifies that the supplied username and password match the details
     //logged in by the user.
    
    public boolean loginUser(String username, String password) {
        return this.username != null
                && this.username.equals(username)
                && this.password != null
                && this.password.equals(password);
    }

    // Appropriate message is returned for a login attempt.
    
    public String returnLoginStatus(String username, String password) {
        if (loginUser(username, password)) {
            return "Welcome " + firstName + ", " + lastName + " it is great to see you again.";
        }
        return "Username or password incorrect, please try again.";
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getUsername() {
        return username;
    }

    public String getCellPhoneNumber() {
        return cellPhoneNumber;
    }
    
}
