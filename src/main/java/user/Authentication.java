package user;

import database.DatabaseWork;
import org.bson.Document;
import utils.PasswordEncrypting;
import utils.ValidInput;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Auth {
    private static final Scanner scanner = new Scanner(System.in);
    private static String userEmail;
    public static void authentication() {
        System.out.println("Please enter a number to choose an option:");
        System.out.println("1. Create a new account.\n2. Log into your account.\n");

        do {
            try {
                int option = scanner.nextInt();
                switch(option) {
                    case 1 : register(); break;
                    case 2 : login(); break;
                }
            } catch (InputMismatchException e) {
                System.out.println("\nTry again.\n");
            }
        } while(true);
    }

    public static void register() {
        System.out.println("Enter your firstname:");
        String firstName = scanner.next();

        System.out.println("Enter your lastname:");
        String lastName = scanner.next();

        String email;
        do {
            System.out.println("Enter your email:");
            email = scanner.next();
        } while(!ValidInput.isEmailValid(email));

        String password;
        do {
            System.out.println("Enter a password:");
            password = scanner.next();
        } while(password == null);

        boolean isMatching;
        do {
            System.out.println("Re-enter the password:");
            isMatching = ValidInput.isPasswordMatching(scanner.next(), password);
        } while(!isMatching);

        password = PasswordEncrypting.encryptPassword(password);

        if (DatabaseWork.addUser(firstName, lastName, email, password)) {
            System.out.println("\nYour account has been registered!");
            userEmail = email;
            UserInteraction.run();
        } else {
            System.out.println("\nUser already exists! Please try again.\n");
            register();
        }
    }

    public static void login() {
        System.out.println("Enter your email:");
        String email = scanner.next();

        System.out.println("Enter a password:");
        String password = scanner.next();

        Document user = DatabaseWork.findUser(email);
        if (user == null) {
            System.out.println("User doesn't exist. Try again.\n");
            login();
        } else {
            if (PasswordEncrypting.verifyPassword(password, (String) user.get("password"))) {
                System.out.println("\nSuccessfully logged in!");
                userEmail = email;
                UserInteraction.run();
            } else {
                System.out.println("Wrong password. Try again.\n");
                login();
            }
        }
    }
}
