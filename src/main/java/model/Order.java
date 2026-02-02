/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 *
 * @author WIN11
 */
public class Order {

    private int orderId;
    private int customerId;
    private Integer voucherId;
    private int paymentMethodId;
    private String shippingAddress;
    private BigDecimal totalAmount;
    private String paymentStatus;
    private String status;
    private Timestamp createdAt;

    //INSERT constructor
    public Order(int customerId, Integer voucherId, int paymentMethodId,
            String shippingAddress, BigDecimal totalAmount) {
        this.customerId = customerId;
        this.voucherId = voucherId;
        this.paymentMethodId = paymentMethodId;
        this.shippingAddress = shippingAddress;
        this.totalAmount = totalAmount;
    }

    //READ constructor 
    public Order(int orderId, int customerId, Integer voucherId, int paymentMethodId,
            String shippingAddress, BigDecimal totalAmount,
            String paymentStatus, String status, Timestamp createdAt) {
        this.orderId = orderId;
        this.customerId = customerId;
        this.voucherId = voucherId;
        this.paymentMethodId = paymentMethodId;
        this.shippingAddress = shippingAddress;
        this.totalAmount = totalAmount;
        this.paymentStatus = paymentStatus;
        this.status = status;
        this.createdAt = createdAt;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public Integer getVoucherId() {
        return voucherId;
    }

    public void setVoucherId(Integer voucherId) {
        this.voucherId = voucherId;
    }

    public int getPaymentMethodId() {
        return paymentMethodId;
    }

    public void setPaymentMethodId(int paymentMethodId) {
        this.paymentMethodId = paymentMethodId;
    }

    public String getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(String shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "Order{"
                + "orderId=" + orderId
                + ", customerId=" + customerId
                + ", voucherId=" + voucherId
                + ", paymentMethodId=" + paymentMethodId
                + ", shippingAddress='" + shippingAddress + '\''
                + ", totalAmount=" + totalAmount
                + ", paymentStatus='" + paymentStatus + '\''
                + ", status='" + status + '\''
                + ", createdAt=" + createdAt
                + '}';
    }

}
