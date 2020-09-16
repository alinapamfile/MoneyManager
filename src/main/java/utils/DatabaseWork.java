package utils;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;


public class DatabaseWork {
    private final static MongoClient mongo = DatabaseConnection.connect();
    private final static MongoDatabase db = mongo.getDatabase("money-manager");

    private DatabaseWork() {}

    public static Document addUser(String firstname, String lastname, String email, String password) {
        Document user = new Document("firstName", firstname)
                .append("lastName", lastname)
                .append("email", email)
                .append("password", password);
        db.getCollection("users").insertOne(user);

        return user;
    }

    //TODO
    public static boolean deleteUser(Document user) {
        return false;
    }
}
