package databse;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private final String url;
    private final String user;
    private final String password;
    private Connection conn;

    public DatabaseConnection(String url, String user, String password, Connection conn) {
        this.url = url;
        this.user = user;
        this.password = password;
        this.conn = null;
    }

    public Connection getConnection() {
        try {
            if (conn == null || getConnection().isClosed()) {
                Class.forName("org.postgresql.Driver");

                conn = DriverManager.getConnection(url, user, password);
                System.out.println("Connected to PostgreSQL database");
            }
        } catch (ClassNotFoundException e) {
            System.out.println("PostgreSQL JDBC Driver not found : " + e.getMessage());
        } catch (SQLException e) {
            System.out.println("Connection fail: " + e.getMessage());
        }
        return conn;
    }


}
