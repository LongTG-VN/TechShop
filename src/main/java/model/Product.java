/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.time.LocalDateTime;

/**
 *
 * @author CT
 */
public class Product {
    private int productId ;
    private Category category ;
    private Brand brand;
    private String productName;
    private String description;
    private int warranty_months;
    private byte is_active;
    private LocalDateTime created_at;

    public Product() {
    }

    public Product(int productId, Category category, Brand brand, String productName, String description, int warranty_months, byte is_active, LocalDateTime created_at) {
        this.productId = productId;
        this.category = category;
        this.brand = brand;
        this.productName = productName;
        this.description = description;
        this.warranty_months = warranty_months;
        this.is_active = is_active;
        this.created_at = created_at;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Brand getBrand() {
        return brand;
    }

    public void setBrand(Brand brand) {
        this.brand = brand;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getWarranty_months() {
        return warranty_months;
    }

    public void setWarranty_months(int warranty_months) {
        this.warranty_months = warranty_months;
    }

    public byte getIs_active() {
        return is_active;
    }

    public void setIs_active(byte is_active) {
        this.is_active = is_active;
    }

    public LocalDateTime getCreated_at() {
        return created_at;
    }

    public void setCreated_at(LocalDateTime created_at) {
        this.created_at = created_at;
    }

    @Override
    public String toString() {
        return "product{" + "productId=" + productId + ", category=" + category + ", brand=" + brand + ", productName=" + productName + ", description=" + description + ", warranty_months=" + warranty_months + ", is_active=" + is_active + ", created_at=" + created_at + '}';
    }
    
}
