package database;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

public class DatabaseWork {
    private final static MongoClient mongo = DatabaseConnection.connect();
    private final static MongoDatabase db = mongo.getDatabase("money-manager");
    private static String userEmail;

    private DatabaseWork() {}

    //TODO: check if user exists already in the database
    public static boolean addUser(String firstname, String lastname, String email, String password) {
        Document user = new Document("firstName", firstname)
                .append("lastName", lastname)
                .append("email", email)
                .append("password", password)
                .append("accounts", null);
        db.getCollection("users").insertOne(user);
        userEmail = email;

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
