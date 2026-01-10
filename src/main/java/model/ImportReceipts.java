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
public class ImportReceipts {
    private int receiptId;
    private Supplier supplier;
    private user user;
    private double totalCost;
    private LocalDateTime importDate;

    public ImportReceipts() {
    }

    public ImportReceipts(int receiptId, Supplier supplier, user user, double totalCost, LocalDateTime importDate) {
        this.receiptId = receiptId;
        this.supplier = supplier;
        this.user = user;
        this.totalCost = totalCost;
        this.importDate = importDate;
    }

    public int getReceiptId() {
        return receiptId;
    }

    public void setReceiptId(int receiptId) {
        this.receiptId = receiptId;
    }

    public Supplier getSupplier() {
        return supplier;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }

    public user getUser() {
        return user;
    }

    public void setUser(user user) {
        this.user = user;
    }

    public double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(double totalCost) {
        this.totalCost = totalCost;
    }

    public LocalDateTime getImportDate() {
        return importDate;
    }

    public void setImportDate(LocalDateTime importDate) {
        this.importDate = importDate;
    }

    @Override
    public String toString() {
        return "ImportReceipts{" + "receiptId=" + receiptId + ", supplier=" + supplier + ", user=" + user + ", totalCost=" + totalCost + ", importDate=" + importDate + '}';
    }
    
    
}
