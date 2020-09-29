import database.DatabaseWork;
import utils.PasswordEncrypting;
import utils.ValidInput;

import org.bson.Document;
import java.util.InputMismatchException;
import java.util.Scanner;

public class UserInteraction {
    private static final Scanner scanner = new Scanner(System.in);
    private static String userEmail;

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

        if (DatabaseWork.addUser(firstName, lastName, email, password)) {
            System.out.println("\nYour account has been registered!");
            userEmail = email;
            run();
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
                run();
            } else {
                System.out.println("Wrong password. Try again.\n");
                login();
            }
        }
    }

    //TODO
    public static void run() {
        while (true) {
            System.out.println("Please enter a number to choose an option:");
            System.out.println("1. Check accounts balance.\n" +
                    "2. Check total balance.\n" +
                    "3. Register an expense.\n" +
                    "4. Register an income.\n" +
                    "5. Add a bank account.\n" +
                    "6. Delete a bank account.\n" +
                    "7. Delete your MoneyManager account.\n" +
                    "8. Logout.\n"
            );

            //temporary functionality
            int option = scanner.nextInt();
            switch (option) {
                case 1 -> DatabaseWork.accountsBalance(userEmail);
                case 2 -> System.out.println("option 2");
                case 3 -> System.out.println("option 3");
                case 4 -> System.out.println("option 4");
                case 5 -> System.out.println("option 5");
                case 6 -> System.out.println("option 6");
                case 7 -> System.out.println("option 7");
                case 8 -> exit();
            }
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
