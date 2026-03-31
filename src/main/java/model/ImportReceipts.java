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
public class ImportReceipts {

    private int receipt_id;
    private int supplier_id;
    private int employee_id;
    private double total_cost;
    private Timestamp import_date;
    private String receipt_code;
    private String status;
    private String note;
    private int created_by;
    private Timestamp created_at;

    public ImportReceipts() {
    }

    public ImportReceipts(int receipt_id, int supplier_id, int employee_id, double total_cost, Timestamp import_date) {
        this.receipt_id = receipt_id;
        this.supplier_id = supplier_id;
        this.employee_id = employee_id;
        this.total_cost = total_cost;
        this.import_date = import_date;
    }

    public ImportReceipts(int receipt_id, int supplier_id, int employee_id, double total_cost, Timestamp import_date,
            String receipt_code, String status, String note, int created_by, Timestamp created_at) {
        this.receipt_id = receipt_id;
        this.supplier_id = supplier_id;
        this.employee_id = employee_id;
        this.total_cost = total_cost;
        this.import_date = import_date;
        this.receipt_code = receipt_code;
        this.status = status;
        this.note = note;
        this.created_by = created_by;
        this.created_at = created_at;
    }

    public int getReceipt_id() {
        return receipt_id;
    }

    public int getReceiptId() {
        return receipt_id;
    }

    public void setReceipt_id(int receipt_id) {
        this.receipt_id = receipt_id;
    }

    public void setReceiptId(int receiptId) {
        this.receipt_id = receiptId;
    }

    public int getSupplier_id() {
        return supplier_id;
    }

    public int getSupplierId() {
        return supplier_id;
    }

    public void setSupplier_id(int supplier_id) {
        this.supplier_id = supplier_id;
    }

    public void setSupplierId(int supplierId) {
        this.supplier_id = supplierId;
    }

    public int getEmployee_id() {
        return employee_id;
    }

    public int getEmployeeId() {
        return employee_id;
    }

    public void setEmployee_id(int employee_id) {
        this.employee_id = employee_id;
    }

    public void setEmployeeId(int employeeId) {
        this.employee_id = employeeId;
    }

    public double getTotal_cost() {
        return total_cost;
    }

    public double getTotalCost() {
        return total_cost;
    }

    public void setTotal_cost(double total_cost) {
        this.total_cost = total_cost;
    }

    public void setTotalCost(double totalCost) {
        this.total_cost = totalCost;
    }

    public Timestamp getImport_date() {
        return import_date;
    }

    public Timestamp getImportDate() {
        return import_date;
    }

    public void setImport_date(Timestamp import_date) {
        this.import_date = import_date;
    }

    public void setImportDate(Timestamp importDate) {
        this.import_date = importDate;
    }

    public String getReceipt_code() {
        return receipt_code;
    }

    public String getReceiptCode() {
        return receipt_code;
    }

    public void setReceipt_code(String receipt_code) {
        this.receipt_code = receipt_code;
    }

    public void setReceiptCode(String receiptCode) {
        this.receipt_code = receiptCode;
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

    public int getCreated_by() {
        return created_by;
    }

    public int getCreatedBy() {
        return created_by;
    }

    public void setCreated_by(int created_by) {
        this.created_by = created_by;
    }

    public void setCreatedBy(int createdBy) {
        this.created_by = createdBy;
    }

    public Timestamp getCreated_at() {
        return created_at;
    }

    public Timestamp getCreatedAt() {
        return created_at;
    }

    public void setCreated_at(Timestamp created_at) {
        this.created_at = created_at;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.created_at = createdAt;
    }

    @Override
    public String toString() {
        return "ImportReceipt{"
                + "receipt_id=" + receipt_id
                + ", receipt_code=" + receipt_code
                + ", supplier_id=" + supplier_id
                + ", employee_id=" + employee_id
                + ", total_cost=" + total_cost
                + ", import_date=" + import_date
                + ", status=" + status
                + ", note=" + note
                + ", created_by=" + created_by
                + ", created_at=" + created_at
                + '}';
    }
}
