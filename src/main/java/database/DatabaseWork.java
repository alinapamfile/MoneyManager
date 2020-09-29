package database;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import org.bson.Document;

import java.util.*;

import static com.mongodb.client.model.Filters.*;

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

        mongo.getDatabase("money-manager").getCollection("users").insertOne(user);
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

        double balance = 0;

        if (accounts != null) {
            for (String key : accounts.keySet()) {
                balance += (Double) accounts.get(key);
            }
        }

        System.out.printf("Your accounts balance is %f RON\n\n", balance);
    }

    //TODO
    public static void totalBalance(String userEmail) {}

    //TODO
    public static boolean registerExpense(String userEmail, String accountName, double amount) { return false; }

    //TODO
    public static boolean registerDeposit(String userEmail, String accountName, double amount) { return false; }

    //TODO
    public static boolean addBankAccount(String userEmail, Document bankAccount) { return false; }

    //TODO
    public static boolean deleteBankAccount(String userEmail, String accountName) { return false; }
}
