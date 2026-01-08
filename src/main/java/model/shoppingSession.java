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
public class shoppingSession {
    private int sessionID;
    private user user;
    private double totalPrice;
    private LocalDateTime updated_at;

    public shoppingSession() {
    }

    public shoppingSession(int sessionID, user user, double totalPrice, LocalDateTime updated_at) {
        this.sessionID = sessionID;
        this.user = user;
        this.totalPrice = totalPrice;
        this.updated_at = updated_at;
    }

    public int getSessionID() {
        return sessionID;
    }

    public void setSessionID(int sessionID) {
        this.sessionID = sessionID;
    }

    public user getUser() {
        return user;
    }

    public void setUser(user user) {
        this.user = user;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public LocalDateTime getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(LocalDateTime updated_at) {
        this.updated_at = updated_at;
    }

    @Override
    public String toString() {
        return "shoppingSession{" + "sessionID=" + sessionID + ", user=" + user + ", totalPrice=" + totalPrice + ", updated_at=" + updated_at + '}';
    }
    
    
}