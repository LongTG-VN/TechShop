/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.time.LocalDateTime;

/**
 *
 * @author ASUS
 */
public class CartItem {
    private int cartItemId;
    private shoppingSession ShoppingSession;
    private ProductVariants productVarant;
    private int quantity;
    private double price;
    private LocalDateTime added_at;

    public CartItem() {
    }

    public CartItem(int cartItemId, shoppingSession ShoppingSession, ProductVariants productVarant, int quantity, double price, LocalDateTime added_at) {
        this.cartItemId = cartItemId;
        this.ShoppingSession = ShoppingSession;
        this.productVarant = productVarant;
        this.quantity = quantity;
        this.price = price;
        this.added_at = added_at;
    }

    @Override
    public String toString() {
        return "CartItem{" + "cartItemId=" + cartItemId + ", ShoppingSession=" + ShoppingSession + ", productVarant=" + productVarant + ", quantity=" + quantity + ", price=" + price + ", added_at=" + added_at + '}';
    }
    

    public int getCartItemId() {
        return cartItemId;
    }

    public void setCartItemId(int cartItemId) {
        this.cartItemId = cartItemId;
    }

    public shoppingSession getShoppingSession() {
        return ShoppingSession;
    }

    public void setShoppingSession(shoppingSession ShoppingSession) {
        this.ShoppingSession = ShoppingSession;
    }

    public ProductVariants getProductVarant() {
        return productVarant;
    }

    public void setProductVarant(ProductVariants productVarant) {
        this.productVarant = productVarant;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public LocalDateTime getAdded_at() {
        return added_at;
    }

    public void setAdded_at(LocalDateTime added_at) {
        this.added_at = added_at;
    }

  
    
    
}
