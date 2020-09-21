package database;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import org.bson.Document;

import java.util.*;

import static com.mongodb.client.model.Filters.*;

public class DatabaseWork {

    private final static MongoClient mongo = DatabaseConnection.connect();
    private static String userEmail;

    private DatabaseWork() {}

    public static Document findUser() {
        MongoCollection<Document> db = mongo.getDatabase("money-manager").getCollection("users");
        FindIterable<Document> usersFound = db.find(eq("email", userEmail));

        Iterator<Document> iterator = usersFound.iterator();
        if (iterator.hasNext()) {
            return iterator.next();
        } else {
            return null;
        }
    }

    public static boolean addUser(String firstname, String lastname, String email, String password) {
        userEmail = email;

        if (findUser() != null) {
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
    public static boolean deleteUser() {
        return false;
    }

    //TODO
    public static void accountsBalance() {}

    //TODO
    public static void totalBalance() {}

    //TODO
    public static boolean registerExpense(String accountName, double amount) { return false; }

    //TODO
    public static boolean registerDeposit(String accountName, double amount) { return false; }

    //TODO
    public static boolean addBankAccount(Document bankAccount) { return false; }

    //TODO
    public static boolean deleteBankAccount(String accountName) { return false; }
}
