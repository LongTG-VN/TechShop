/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.math.BigDecimal;


public class OrderItem {

    private int orderItemId;
    private int orderId;
    private int inventoryId;
    private BigDecimal sellingPrice;

    public OrderItem() {
    }

    // ===== INSERT constructor =====
    public OrderItem(int orderId, int inventoryId, BigDecimal sellingPrice) {
        this.orderId = orderId;
        this.inventoryId = inventoryId;
        this.sellingPrice = sellingPrice;
    }

    // ===== READ constructor =====
    public OrderItem(int orderItemId, int orderId, int inventoryId, BigDecimal sellingPrice) {
        this.orderItemId = orderItemId;
        this.orderId = orderId;
        this.inventoryId = inventoryId;
        this.sellingPrice = sellingPrice;
    }

    public int getOrderItemId() {
        return orderItemId;
    }

    public void setOrderItemId(int orderItemId) {
        this.orderItemId = orderItemId;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getInventoryId() {
        return inventoryId;
    }

    public void setInventoryId(int inventoryId) {
        this.inventoryId = inventoryId;
    }

    public BigDecimal getSellingPrice() {
        return sellingPrice;
    }

    public void setSellingPrice(BigDecimal sellingPrice) {
        this.sellingPrice = sellingPrice;
    }

    @Override
    public String toString() {
        return "OrderItem{"
                + "orderItemId=" + orderItemId
                + ", orderId=" + orderId
                + ", inventoryId=" + inventoryId
                + ", sellingPrice=" + sellingPrice
                + '}';
    }
}
