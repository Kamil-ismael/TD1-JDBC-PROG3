package dao;

import databse.DatabaseConnection;
import model.Category;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DataRetriever {
    private final DatabaseConnection dbConnection;

    public DataRetriever(DatabaseConnection dbConnection) {
        this.dbConnection = dbConnection;
    }

    public List<Category> getAllCategories() {
        List<Category> categories = new ArrayList<>();
        String sql = "SELECT id, name FROM product_category";

        try (Connection conn = dbConnection.getDBConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Category category = new Category();
                category.setId(rs.getInt("id"));
                category.setName(rs.getString("name"));
                categories.add(category);
            }
        } catch (SQLException e) {
            System.out.println("recuperation error : " + e.getMessage());
        }
        return categories;
    }
}
