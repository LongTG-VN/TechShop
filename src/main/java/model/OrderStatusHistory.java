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
public class OrderStatusHistory {
    private int historyId;
    private Order order;
    private String status;
    private String note;
    private LocalDateTime updated_at;

    public OrderStatusHistory() {
    }

    @Override
    public String toString() {
        return "OrderStatusHistory{" + "historyId=" + historyId + ", order=" + order + ", status=" + status + ", note=" + note + ", updated_at=" + updated_at + '}';
    }

    
    public OrderStatusHistory(int historyId, Order order, String status, String note, LocalDateTime updated_at) {
        this.historyId = historyId;
        this.order = order;
        this.status = status;
        this.note = note;
        this.updated_at = updated_at;
    }

    public int getHistoryId() {
        return historyId;
    }

    public void setHistoryId(int historyId) {
        this.historyId = historyId;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public LocalDateTime getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(LocalDateTime updated_at) {
        this.updated_at = updated_at;
    }

    
}
