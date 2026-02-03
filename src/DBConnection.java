import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/online_exam",
                    "root",
                    "yahwin"   // your real password
            );
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
