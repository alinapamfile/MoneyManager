package user;

import database.DatabaseWork;

import java.util.Scanner;

public class UserInteraction {
    private static final Scanner scanner = new Scanner(System.in);
    private static String userEmail;

    private UserInteraction() {}

    public static void start() {
        System.out.println("Welcome to MoneyManager!\n");
        sleep(1);
        Authentication.auth();
    }

    //TODO
    static void run() {
        userEmail = Authentication.userEmail;

        while (true) {
            sleep(1);
            System.out.println("\nPlease enter a number to choose an option:");
            System.out.println("\n1. Check accounts balance.\n" +
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
                case 2 -> DatabaseWork.totalBalance(userEmail);
                case 3 -> System.out.println("option 3");
                case 4 -> System.out.println("option 4");
                case 5 -> {
                    System.out.println("Enter the name of the new bank account:");
                    String accountName = scanner.next();
                    System.out.println("Enter the initial amount of money in the new bank account:");
                    double amount = scanner.nextDouble();
                    DatabaseWork.addBankAccount(userEmail, accountName, amount);
                }
                case 6 -> System.out.println("option 6");
                case 7 -> System.out.println("option 7");
                case 8 -> exit();
            }
        }
    }

    private static void sleep(int seconds) {
        try {
            Thread.sleep((int)seconds * 1000);
        } catch (InterruptedException e) {
            System.err.println("InterruptedException in sleep()");
        }
    }

    private static void exit() {
        System.out.println("\nHope to see you back soon!");
        System.exit(0);
    }
}
