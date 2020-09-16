package utils;

import models.User;

import java.util.Scanner;

public class UserInteraction {
    private static final Scanner scanner = new Scanner(System.in);
    private static final User user = null;

    private UserInteraction() {}

    public static void start() {
        System.out.println("Welcome to MoneyManager!\n");
        authentication();
    }

    public static void authentication() {
        System.out.println("Please enter a number to choose an option:");
        System.out.println("1. Create a new account.\n2. Log into your account.\n");

        do {
            int option = scanner.nextInt();
            switch(option) {
                case 1 -> register();
                case 2 -> login();
            }
        } while(true);
    }

    //TODO
    public static void register() {
    }

    //TODO
    public static void login() {

    }

    //TODO
    public static void run() {
        while (true) {
            System.out.println("Please enter a number to choose an option:");
            System.out.println("""
                    1. Check all accounts balances.
                    2. Check account balance.
                    3. Check total balance.
                    4. Logout.
                    """
            );

            //temporary functionality
            do {
                int option = scanner.nextInt();
                switch(option) {
                    case 1 -> System.out.println("option 1");
                    case 2 -> System.out.println("option 2");
                    case 3 -> System.out.println("option 3");
                    case 4 -> exit();
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
