import java.util.logging.Level;
import java.util.logging.Logger;

public class Application {
    public static void main(String[] args) {
        Logger.getLogger("org.mongodb.driver").setLevel(Level.WARNING);
        UserInteraction.start();
    }
}