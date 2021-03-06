package utils;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Base64;

public class PasswordEncrypting {
    private static final int ITERATIONS = 65536;
    private static final int KEY_LENGTH = 512;
    private static final String ALGORITHM = "PBKDF2WithHmacSHA512";
    private static final byte[] SALT = {15,5,3,46,23,5,43,127,3,14};
    private PasswordEncrypting() {

    }

    public static String encryptPassword(String password) {
        KeySpec spec = new PBEKeySpec(password.toCharArray(), SALT, ITERATIONS, KEY_LENGTH);

        try {
            SecretKeyFactory factory = SecretKeyFactory.getInstance(ALGORITHM);
            byte[] hash = factory.generateSecret(spec).getEncoded();
            return Base64.getEncoder().encodeToString(hash);
        } catch (NoSuchAlgorithmException e) {
            System.err.println("NoSuchAlgorithmException in encryptPassword()");
            return null;
        } catch (InvalidKeySpecException e) {
            System.err.println("InvalidKeySpecException in encryptPassword()");
            return null;
        }
    }

    public static boolean verifyPassword(String input, String encryptedPassword) {
        String encryptedInput = encryptPassword(input);
        if (encryptedInput != null)
            return encryptedInput.equals(encryptedPassword);
        else
            return false;
    }
}