/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author ASUS
 */
public class role {
    private int role_id;
    private String role_name;
    private String description;
    private byte is_active;

    public role() {
    }

    public role(int role_id, String role_name, String description, byte is_active) {
        this.role_id = role_id;
        this.role_name = role_name;
        this.description = description;
        this.is_active = is_active;
    }

    public int getRole_id() {
        return role_id;
    }

    public void setRole_id(int role_id) {
        this.role_id = role_id;
    }

    public String getRole_name() {
        return role_name;
    }

    public void setRole_name(String role_name) {
        this.role_name = role_name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public byte getIs_active() {
        return is_active;
    }

    public void setIs_active(byte is_active) {
        this.is_active = is_active;
    }

    @Override
    public String toString() {
        return "role{" + "role_id=" + role_id + ", role_name=" + role_name + ", description=" + description + ", is_active=" + is_active + '}';
    }

    
}
