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
public class Order {
    private int orderId;
    private user user;
    private Voucher voucher;
    private PaymentMethod paymentMethod;
    private double totalAmount;
    private String shippingAddress;
    private String status;
    private byte isPaid;
    private LocalDateTime created_at;

    public Order() {
    }

    public Order(int orderId, user user, Voucher voucher, PaymentMethod paymentMethod, double totalAmount, String shippingAddress, String status, byte isPaid, LocalDateTime created_at) {
        this.orderId = orderId;
        this.user = user;
        this.voucher = voucher;
        this.paymentMethod = paymentMethod;
        this.totalAmount = totalAmount;
        this.shippingAddress = shippingAddress;
        this.status = status;
        this.isPaid = isPaid;
        this.created_at = created_at;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public user getUser() {
        return user;
    }

    public void setUser(user user) {
        this.user = user;
    }

    public Voucher getVoucher() {
        return voucher;
    }

    public void setVoucher(Voucher voucher) {
        this.voucher = voucher;
    }

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(String shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public byte getIsPaid() {
        return isPaid;
    }

    public void setIsPaid(byte isPaid) {
        this.isPaid = isPaid;
    }

    public LocalDateTime getCreated_at() {
        return created_at;
    }

    public void setCreated_at(LocalDateTime created_at) {
        this.created_at = created_at;
    }

    @Override
    public String toString() {
        return "Order{" + "orderId=" + orderId + ", user=" + user + ", voucher=" + voucher + ", paymentMethod=" + paymentMethod + ", totalAmount=" + totalAmount + ", shippingAddress=" + shippingAddress + ", status=" + status + ", isPaid=" + isPaid + ", created_at=" + created_at + '}';
    }
    
    
   
}
