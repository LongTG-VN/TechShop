/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.sql.Timestamp;

/**
 *
 * @author LE HOANG NHAN
 */
public class CartItem {

    private int cart_item_id;
    private int customer_id;
    private int variant_id;
    private int quantity;
    private Timestamp created_at;

    public CartItem() {
    }

    public CartItem(int cart_item_id, int customer_id, int variant_id, int quantity, Timestamp created_at) {
        this.cart_item_id = cart_item_id;
        this.customer_id = customer_id;
        this.variant_id = variant_id;
        this.quantity = quantity;
        this.created_at = created_at;
    }

    public int getCart_item_id() {
        return cart_item_id;
    }

    public void setCart_item_id(int cart_item_id) {
        this.cart_item_id = cart_item_id;
    }

    public int getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(int customer_id) {
        this.customer_id = customer_id;
    }

    public int getVariant_id() {
        return variant_id;
    }

    public void setVariant_id(int variant_id) {
        this.variant_id = variant_id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Timestamp getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Timestamp created_at) {
        this.created_at = created_at;
    }

    @Override
    public String toString() {
        return "CartItem{" + "id=" + cart_item_id + ", cus_id=" + customer_id + ", var_id=" + variant_id + ", qty=" + quantity + ", date=" + created_at + '}';
    }
}
