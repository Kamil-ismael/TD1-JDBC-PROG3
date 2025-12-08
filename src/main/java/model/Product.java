package model;

import java.time.Instant;

public class Product {
    private int id;
    private String name;
    private Instant creationDateTime;
    private Category category;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Instant getCreationDateTime() {
        return creationDateTime;
    }

    public Category getCategory() {
        return category;
    }

    public String getCategoryName() {
        if (category != null) {
            return category.getName();
        }
        return null;
    }
}
