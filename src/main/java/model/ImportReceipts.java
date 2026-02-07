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

    public ImportReceipts() {
    }

    public ImportReceipts(int receipt_id, int supplier_id, int employee_id, double total_cost, Timestamp import_date) {
        this.receipt_id = receipt_id;
        this.supplier_id = supplier_id;
        this.employee_id = employee_id;
        this.total_cost = total_cost;
        this.import_date = import_date;
    }

    public int getReceipt_id() {
        return receipt_id;
    }

    public void setReceipt_id(int receipt_id) {
        this.receipt_id = receipt_id;
    }

    public int getSupplier_id() {
        return supplier_id;
    }

    public void setSupplier_id(int supplier_id) {
        this.supplier_id = supplier_id;
    }

    public int getEmployee_id() {
        return employee_id;
    }

    public void setEmployee_id(int employee_id) {
        this.employee_id = employee_id;
    }

    public double getTotal_cost() {
        return total_cost;
    }

    public void setTotal_cost(double total_cost) {
        this.total_cost = total_cost;
    }

    public Timestamp getImport_date() {
        return import_date;
    }

    public void setImport_date(Timestamp import_date) {
        this.import_date = import_date;
    }

    @Override
    public String toString() {
        return "ImportReceipt{" + "receipt_id=" + receipt_id + ", supplier_id=" + supplier_id + ", employee_id=" + employee_id + ", total_cost=" + total_cost + ", import_date=" + import_date + '}';
    }
}
