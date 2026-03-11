package org.pharmacy.model;

public class Category {

    private int id;
    private String categoryName;

    // Constructor (boş)
    public Category() {
    }

    public Category(int id, String categoryName) {
        this.id = id;
        this.categoryName = categoryName;
    }


    public int getId() {
        return id;
    }


    public void setId(int id) {
        this.id = id;
    }

    // Getter
    public String getCategoryName() {
        return categoryName;
    }

    // Setter
    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
}
