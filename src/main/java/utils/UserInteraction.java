package utils;

import org.bson.Document;

import java.util.InputMismatchException;
import java.util.Scanner;

public class UserInteraction {
    private static final Scanner scanner = new Scanner(System.in);
    private static Document user;

    private UserInteraction() {}

    public static void start() {
        System.out.println("Welcome to MoneyManager!\n");
        sleep(1);
        authentication();
    }

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

        user = DatabaseWork.addUser(firstName, lastName, email, password);
        System.out.printf("\nWelcome, %s!\n", firstName);
        run();
    }

    //TODO
    public static void login() {
        System.exit(0);
    }

    //TODO
    public static void run() {
        while (true) {
            System.out.println("Please enter a number to choose an option:");
            System.out.println("1. Check all accounts balances.\n" +
                    "2. Check account balance.\n" +
                    "3. Check total balance.\n" +
                    "4. Logout.\n"
            );

            //temporary functionality
            do {
                int option = scanner.nextInt();
                switch(option) {
                    case 1 : System.out.println("option 1"); break;
                    case 2 : System.out.println("option 2"); break;
                    case 3 : System.out.println("option 3"); break;
                    case 4 : exit();
                }
            } while(true);
        }
    }

    public static void sleep(int seconds) {
        try {
            Thread.sleep((int)seconds * 1000);
        } catch (InterruptedException e) {
            System.err.println("InterruptedException in sleep()");
        }
    }

    public static void exit() {
        System.out.println("\nHope to see you back soon!");
        System.exit(0);
    }
}
