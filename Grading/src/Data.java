import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Data {
    private static final String URL = "jdbc:mysql://localhost:3306/StudentGradingSystem";
    private static final String USER = "root";  // Replace with your MySQL username
    private static final String PASSWORD = "1234";  // Replace with your MySQL password

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}

