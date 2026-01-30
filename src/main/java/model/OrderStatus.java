/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author WIN11
 */
public class OrderStatus {

    private int statusId;
    private String statusCode;
    private String statusName;
    private int stepOrder;
    private boolean isFinal;

    //Insert constructor
    public OrderStatus(String statusCode, String statusName, int stepOrder, boolean isFinal) {
        this.statusCode = statusCode;
        this.statusName = statusName;
        this.stepOrder = stepOrder;
        this.isFinal = isFinal;
    }

    public OrderStatus(int statusId, String statusCode, String statusName, int stepOrder, boolean isFinal) {
        this.statusId = statusId;
        this.statusCode = statusCode;
        this.statusName = statusName;
        this.stepOrder = stepOrder;
        this.isFinal = isFinal;
    }

    public int getStatusId() {
        return statusId;
    }

    public void setStatusId(int statusId) {
        this.statusId = statusId;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public int getStepOrder() {
        return stepOrder;
    }

    public void setStepOrder(int stepOrder) {
        this.stepOrder = stepOrder;
    }

    public boolean isIsFinal() {
        return isFinal;
    }

    public void setIsFinal(boolean isFinal) {
        this.isFinal = isFinal;
    }

    @Override
    public String toString() {
        return "OrderStatus{"
                + "statusId=" + statusId
                + ", statusCode='" + statusCode + '\''
                + ", statusName='" + statusName + '\''
                + ", stepOrder=" + stepOrder
                + ", isFinal=" + isFinal
                + '}';
    }
}
