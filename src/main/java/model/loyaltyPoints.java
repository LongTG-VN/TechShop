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
public class loyaltyPoints {
    private int pointID;
    private user user;
    private int points;
    private String reason;
    private LocalDateTime created_at;

    public loyaltyPoints() {
    }

    public loyaltyPoints(int pointID, user user, int points, String reason, LocalDateTime created_at) {
        this.pointID = pointID;
        this.user = user;
        this.points = points;
        this.reason = reason;
        this.created_at = created_at;
    }

    public int getPointID() {
        return pointID;
    }

    public void setPointID(int pointID) {
        this.pointID = pointID;
    }

    public user getUser() {
        return user;
    }

    public void setUser(user user) {
        this.user = user;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public LocalDateTime getCreated_at() {
        return created_at;
    }

    public void setCreated_at(LocalDateTime created_at) {
        this.created_at = created_at;
    }

    @Override
    public String toString() {
        return "loyaltyPoints{" + "pointID=" + pointID + ", user=" + user + ", points=" + points + ", reason=" + reason + ", created_at=" + created_at + '}';
    }
    
}
