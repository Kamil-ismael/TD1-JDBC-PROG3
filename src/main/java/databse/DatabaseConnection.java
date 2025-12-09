package databse ;

import java.sql.Connection;
import java.sql.DriverManager;

public class DatabaseConnection {
    private final String url;
    private final String user;
    private final String password;

    public DatabaseConnection(String url, String user, String password) {
        this.url = url;
        this.user = user;
        this.password = password;
    }

    public Connection getDBConnection(){
        try {
            Class.forName("org.postgresql.Driver");
            return DriverManager.getConnection(url, user, password);
        } catch (Exception e) {
            throw new RuntimeException("Connection error : " + e.getMessage(),e);
        }
    }
}
