/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author CT
 */
public class Category {

    private int categoryId;
    private String categoryName;
    private boolean isActive;

    public Category() {
    }
    
    public Category(int categoryId, String categoryName, boolean isActive) {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.isActive = isActive;
    }

    public Category(String categoryName, boolean isActive) {
        this.categoryName = categoryName;
        this.isActive = isActive;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public boolean isIsActive() {
        return isActive;
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }

    @Override
    public String toString() {
        return "Category{" + "categoryId=" + categoryId + ", categoryName=" + categoryName + ", isActive=" + isActive + '}';
    }
}
