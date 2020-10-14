package user;

import database.DatabaseWork;

import java.util.InputMismatchException;
import java.util.Scanner;

public class UserInteraction {
    private static final Scanner scanner = new Scanner(System.in);

    private UserInteraction() {}

    public static void start() {
        System.out.println("Welcome to MoneyManager!\n");
        sleep(1);
        Authentication.auth();
    }

    //TODO
    static void run() {
        String userEmail = Authentication.userEmail;

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
            try {
                int option = scanner.nextInt();

                switch (option) {
                    case 1 -> DatabaseWork.accountsBalance(userEmail);
                    case 2 -> DatabaseWork.totalBalance(userEmail);
                    case 3 -> {
                        System.out.println("Enter the name of the bank account:");
                        String accountName = scanner.next();
                        System.out.println("Enter the amount of money spent:");
                        double amount = scanner.nextDouble();
                        DatabaseWork.registerExpense(userEmail, accountName, amount);
                    }
                    case 4 -> {
                        System.out.println("Enter the name of the bank account:");
                        String accountName = scanner.next();
                        System.out.println("Enter the amount of money deposited:");
                        double amount = scanner.nextDouble();
                        DatabaseWork.registerDeposit(userEmail, accountName, amount);
                    }
                    case 5 -> {
                        System.out.println("Enter the name of the new bank account:");
                        String accountName = scanner.next();
                        System.out.println("Enter the initial amount of money in the new bank account:");
                        double amount = scanner.nextDouble();
                        DatabaseWork.addBankAccount(userEmail, accountName, amount);
                    }
                    case 6 -> {
                        System.out.println("Enter the name of the bank account you want to delete:");
                        String accountName = scanner.next();
                        System.out.println("Are you sure? Re-enter the name of the bank account you want to delete:");
                        if (accountName.equals(scanner.next())) {
                            DatabaseWork.deleteBankAccount(userEmail, accountName);
                        } else {
                            System.out.println("\nAccount names aren't matching. No bank account was deleted.\n");
                        }
                    }
                    case 7 -> System.out.println("option 7");
                    case 8 -> exit();
                    default -> System.out.printf("\nOption %d doesn't exist. Try again.\n", option);
                }
            } catch (InputMismatchException e) {
                System.out.println("\nTry again.\n");
                scanner.next();
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
