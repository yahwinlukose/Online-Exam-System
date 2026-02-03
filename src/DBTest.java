import java.sql.Connection;
import java.sql.DriverManager;

public class DBTest {

    public static void main(String[] args) {

        try {
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/online_exam",
                    "root",
                    "yahwin"   // use your real password
            );

            if (con != null) {
                System.out.println("CONNECTED TO DATABASE SUCCESSFULLY!");
            }

            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
