/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author LE HOANG NHAN
 */
public class InventoryItem {

    private int inventory_id;
    private int variant_id;
    private int receipt_item_id;
    private String imei;
    private double import_price;
    private String status;

    public InventoryItem() {
    }

    public InventoryItem(int inventory_id, int variant_id, int receipt_item_id, String imei, double import_price, String status) {
        this.inventory_id = inventory_id;
        this.variant_id = variant_id;
        this.receipt_item_id = receipt_item_id;
        this.imei = imei;
        this.import_price = import_price;
        this.status = status;
    }

    public int getInventory_id() {
        return inventory_id;
    }

    public void setInventory_id(int inventory_id) {
        this.inventory_id = inventory_id;
    }

    public int getVariant_id() {
        return variant_id;
    }

    public void setVariant_id(int variant_id) {
        this.variant_id = variant_id;
    }

    public int getReceipt_item_id() {
        return receipt_item_id;
    }

    public void setReceipt_item_id(int receipt_item_id) {
        this.receipt_item_id = receipt_item_id;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public double getImport_price() {
        return import_price;
    }

    public void setImport_price(double import_price) {
        this.import_price = import_price;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "InventoryItem{" + "id=" + inventory_id + ", variant=" + variant_id + ", receipt_item=" + receipt_item_id + ", imei=" + imei + ", price=" + import_price + ", status=" + status + '}';
    }
}
