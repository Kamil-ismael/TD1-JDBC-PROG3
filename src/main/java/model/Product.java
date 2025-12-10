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

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCreationDateTime(Instant creationDateTime) {
        this.creationDateTime = creationDateTime;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", creationDateTime=" + creationDateTime +
                ", category=" + category +
                '}';
    }
}
