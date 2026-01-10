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
public class Wishlist {
    private int wistlistID;
    private user user;
    private product product;
    private LocalDateTime added_at;

    public Wishlist() {
    }

    public Wishlist(int wistlistID, user user, product product, LocalDateTime added_at) {
        this.wistlistID = wistlistID;
        this.user = user;
        this.product = product;
        this.added_at = added_at;
    }

    public int getWistlistID() {
        return wistlistID;
    }

    public void setWistlistID(int wistlistID) {
        this.wistlistID = wistlistID;
    }

    public user getUser() {
        return user;
    }

    public void setUser(user user) {
        this.user = user;
    }

    public product getProduct() {
        return product;
    }

    public void setProduct(product product) {
        this.product = product;
    }

    public LocalDateTime getAdded_at() {
        return added_at;
    }

    public void setAdded_at(LocalDateTime added_at) {
        this.added_at = added_at;
    }

    @Override
    public String toString() {
        return "Wishlist{" + "wistlistID=" + wistlistID + ", user=" + user + ", product=" + product + ", added_at=" + added_at + '}';
    }
    
    
}
