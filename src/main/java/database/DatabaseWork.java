package database;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import org.bson.Document;

import java.util.*;

import static com.mongodb.client.model.Filters.*;
import static com.mongodb.client.model.Updates.*;

public class DatabaseWork {

    private final static MongoClient mongo = DatabaseConnection.connect();

    private DatabaseWork() {}

    public static Document findUser(String email) {
        MongoCollection<Document> db = mongo.getDatabase("money-manager").getCollection("users");
        FindIterable<Document> usersFound = db.find(eq("email", email));

        Iterator<Document> iterator = usersFound.iterator();
        if (iterator.hasNext()) {
            return iterator.next();
        } else {
            return null;
        }
    }

    public static boolean addUser(String firstname, String lastname, String email, String password) {
        if (findUser(email) != null) {
            return false;
        }

        Document user = new Document("firstName", firstname)
                .append("lastName", lastname)
                .append("email", email)
                .append("password", password)
                .append("accounts", new Document());

        mongo.getDatabase("money-manager")
                .getCollection("users")
                .insertOne(user);
        return true;
    }

    //TODO
    public static boolean deleteUser(String userEmail) {
        return false;
    }

    public static void accountsBalance(String userEmail) {
        Document user = findUser(userEmail);
        assert user != null;
        Document accounts = (Document) user.get("accounts");

        System.out.println();
        if (!accounts.isEmpty()) {
            for (String key : accounts.keySet()) {
                System.out.printf("%s: %f\n", key, (Double) accounts.get(key));
            }
        } else {
            System.out.println("You don't have any bank accounts open yet.");
        }
    }

    public static void totalBalance(String userEmail) {
        Document user = findUser(userEmail);
        assert user != null;
        Document accounts = (Document) user.get("accounts");

        double balance = 0;

        if (accounts != null) {
            for (String key : accounts.keySet()) {
                balance += (Double) accounts.get(key);
            }
        }

        System.out.printf("Your total balance is %f RON\n\n", balance);
    }

    public static void registerExpense(String userEmail, String accountName, double amount) {
        Document user = findUser(userEmail);
        assert user != null;

        Document accounts = (Document) user.get("accounts");

        if (!accounts.containsKey(accountName)) {
            System.out.printf("\nBank account %s doesn't exist.\n", accountName);
            return;
        }

        double currentBalance = accounts.getDouble(accountName);
        if (currentBalance >= amount) {
            accounts.replace(accountName, currentBalance, currentBalance - amount);
            mongo.getDatabase("money-manager")
                    .getCollection("users")
                    .updateOne(eq("email", userEmail), set("accounts", accounts));

            System.out.printf("%f RON were withdrawn from %s account. Current balance: %f.\n",
                    amount, accountName, currentBalance - amount);
        } else {
            System.out.printf("Bank account %s has less than %f RON. Bank account %1$s balance is %f RON.\n",
                    accountName, amount, currentBalance);
        }
    }

    public static void registerDeposit(String userEmail, String accountName, double amount) {
        Document user = findUser(userEmail);
        assert user != null;

        Document accounts = (Document) user.get("accounts");

        if (!accounts.containsKey(accountName)) {
            System.out.printf("\nBank account %s doesn't exist.\n", accountName);
            return;
        }

        double currentBalance = accounts.getDouble(accountName);
        accounts.replace(accountName, currentBalance, currentBalance + amount);
        mongo.getDatabase("money-manager")
                .getCollection("users")
                .updateOne(eq("email", userEmail), set("accounts", accounts));

        System.out.printf("%f RON were deposited in %s account. Current balance: %f.\n",
                    amount, accountName, currentBalance + amount);
    }

    public static void addBankAccount(String userEmail, String accountName, double amount) {
        Document user = findUser(userEmail);
        assert user != null;

        Document accounts = (Document) user.get("accounts");

        if (accounts.containsKey(accountName)) {
            System.out.printf("Bank account %s already exists.\n", accountName);
            return;
        }

        accounts.append(accountName, amount);

        mongo.getDatabase("money-manager")
                .getCollection("users")
                .updateOne(eq("email", userEmail), set("accounts", accounts));

        System.out.printf("\nBank account %s with the amount of %f RON added successfully.\n", accountName, amount);
    }

    //TODO
    public static boolean deleteBankAccount(String userEmail, String accountName) { return false; }
}
