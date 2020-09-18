package database;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

public class ValidInput {

    private ValidInput() {}

    public static boolean isEmailValid(String email) {
        try {
            InternetAddress emailAddress = new InternetAddress(email);
            emailAddress.validate();
            return true;
        } catch (AddressException e) {
            return false;
        }
    }

    public static boolean isPasswordMatching(String pass1, String pass2) {
        return pass1.equals(pass2);
    }
}
