package dao;

import databse.DatabaseConnection;
import model.Category;
import model.Product;

import java.sql.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class DataRetriever {
     DatabaseConnection dbConnection;

    public DataRetriever(DatabaseConnection dbConnection) {
        this.dbConnection = dbConnection;
    }

    public List<Category> getAllCategories() {
        List<Category> categories = new ArrayList<>();
        String sql = "SELECT * FROM product_category";

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

    public List<Product> getProductList(int page, int size) {
        List<Product> products = new ArrayList<>();
        int offset = (page - 1) * size;

        String sql = """
                SELECT product_id, name, creation_datetime
                FROM product
                ORDER BY product_id
                LIMIT ? OFFSET ?
                """;

        try (Connection conn = dbConnection.getDBConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, size);
            stmt.setInt(2, offset);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Product product = new Product();
                    product.setId(rs.getInt("product_id"));
                    product.setName(rs.getString("name"));
                    product.setCreationDateTime(
                            rs.getTimestamp("creation_datetime").toInstant()
                    );
                    products.add(product);
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Recuperation error", e);
        }

        return products;
    }


    public List<Product> getProductsByCriteria(
            String productName,
            String categoryName,
            Instant creationMin,
            Instant creationMax
    ) throws SQLException {
        List<Product> list = new ArrayList<>();

        String sql = """
    SELECT
        p.product_id AS product_id,
        p.name AS name,
        p.creation_datetime AS creation_datetime,
        pc.product_category AS category_id,
        pc.name AS category_name
    FROM product p
    LEFT JOIN product_category pc ON pc.product_id = p.product_id
    WHERE 1=1
""";



        List<Object> params = new ArrayList<>();

        if (productName != null && !productName.isEmpty()) {
            sql += " AND p.name ILIKE ? ";
            params.add("%" + productName + "%");
        }

        if (categoryName != null && !categoryName.isEmpty()) {
            sql += " AND pc.name ILIKE ? ";
            params.add("%" + categoryName + "%");
        }

        if (creationMin != null) {
            sql += " AND p.creation_datetime >= ? ";
            params.add(Timestamp.from(creationMin));
        }

        if (creationMax != null) {
            sql += " AND p.creation_datetime <= ? ";
            params.add(Timestamp.from(creationMax));
        }

        try (Connection conn = dbConnection.getDBConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            for (int i = 0; i < params.size(); i++) {
                stmt.setObject(i + 1, params.get(i));
            }

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Product p = new Product();
                p.setId(rs.getInt("product_id"));
                p.setName(rs.getString("name"));
                p.setCreationDateTime(rs.getTimestamp("creation_datetime").toInstant());
                list.add(p);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error SQL", e);
        }

        return list;
    }

    public List<Product> getProductsByCriteria(
            String productName,
            String categoryName,
            Instant creationMin,
            Instant creationMax,
            int page,
            int size
    ) throws SQLException {

        List<Product> filtered = getProductsByCriteria(
                productName, categoryName, creationMin, creationMax
        );

        int start = (page - 1) * size;
        int end = Math.min(start + size, filtered.size());

        if (start >= filtered.size()) {
            return new ArrayList<>();
        }

        return filtered.subList(start, end);
    }


}