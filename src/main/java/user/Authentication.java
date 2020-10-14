package user;

import database.DatabaseWork;
import org.bson.Document;
import utils.PasswordEncrypting;
import utils.ValidInput;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Authentication {
    private static final Scanner scanner = new Scanner(System.in);
    static String userEmail;

    private Authentication() {}

    static void auth() {
        System.out.println("Please enter a number to choose an option:");
        System.out.println("1. Create a new account.\n2. Log into your account.\n");

        do {
            try {
                int option = scanner.nextInt();
                switch(option) {
                    case 1 -> register();
                    case 2 -> login();
                    default -> System.out.printf("\nOption %d doesn't exist. Try again.\n", option);
                }
            } catch (InputMismatchException e) {
                System.out.println("\nTry again.\n");
                scanner.next();
            }
        } while(true);
    }

    private static void register() {
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

    private static void login() {
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
