package com.legacy.inventory.model;

import java.io.Serializable;

/**
 * Represents a sellable product in inventory.
 */
public class Product implements Serializable {

    private static final long serialVersionUID = 1L;

    private int id;
    private String name;
    private String category;
    private double price;
    private int quantity;
    private String description;
    private String supplierName;

    public Product() {
    }

    public Product(int id, String name, String category, double price, int quantity) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.price = price;
        this.quantity = quantity;
    }

    public Product(int id, String name, String category, double price, int quantity,
                   String description, String supplierName) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.price = price;
        this.quantity = quantity;
        this.description = description;
        this.supplierName = supplierName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public void increaseQuantity(int amount) {
        if (amount > 0) {
            this.quantity = this.quantity + amount;
        }
    }

    public boolean decreaseQuantity(int amount) {
        if (amount <= 0) {
            return false;
        }
        if (this.quantity < amount) {
            return false;
        }
        this.quantity = this.quantity - amount;
        return true;
    }

    public boolean isLowStock(int threshold) {
        return this.quantity <= threshold;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Product{");
        sb.append("id=").append(id);
        sb.append(", name='").append(name).append('\'');
        sb.append(", category='").append(category).append('\'');
        sb.append(", price=").append(price);
        sb.append(", quantity=").append(quantity);
        if (description != null) {
            sb.append(", description='").append(description).append('\'');
        }
        if (supplierName != null) {
            sb.append(", supplierName='").append(supplierName).append('\'');
        }
        sb.append('}');
        return sb.toString();
    }
}
