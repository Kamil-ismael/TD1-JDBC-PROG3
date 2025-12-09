import databse.DatabaseConnection;
import model.Category;

import java.sql.Connection;
import java.util.List;

public class Main {
    public static void main(String[] args) {
     DatabaseConnection dbConnection = new DatabaseConnection(
             "jdbc:postgresql://localhost:5432/product_management_db",
             "product_manager_user",
             "123456"
     );
     Connection conn = dbConnection.getDBConnection();
    }
}
