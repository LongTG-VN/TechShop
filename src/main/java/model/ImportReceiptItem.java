/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author LE HOANG NHAN
 */
public class ImportReceiptItem {

    private int receipt_item_id;
    private int receipt_id;
    private int variant_id;
    private double import_price;
    private int quantity;

    public ImportReceiptItem() {
    }

    public ImportReceiptItem(int receipt_item_id, int receipt_id, int variant_id, double import_price, int quantity) {
        this.receipt_item_id = receipt_item_id;
        this.receipt_id = receipt_id;
        this.variant_id = variant_id;
        this.import_price = import_price;
        this.quantity = quantity;
    }

    public int getReceipt_item_id() {
        return receipt_item_id;
    }

    public int getReceiptItemId() {
        return receipt_item_id;
    }

    public void setReceipt_item_id(int receipt_item_id) {
        this.receipt_item_id = receipt_item_id;
    }

    public void setReceiptItemId(int receiptItemId) {
        this.receipt_item_id = receiptItemId;
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

    public int getVariant_id() {
        return variant_id;
    }

    public int getVariantId() {
        return variant_id;
    }

    public void setVariant_id(int variant_id) {
        this.variant_id = variant_id;
    }

    public void setVariantId(int variantId) {
        this.variant_id = variantId;
    }

    public double getImport_price() {
        return import_price;
    }

    public double getImportPrice() {
        return import_price;
    }

    public void setImport_price(double import_price) {
        this.import_price = import_price;
    }

    public void setImportPrice(double importPrice) {
        this.import_price = importPrice;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "ImportReceiptItem{" + "id=" + receipt_item_id + ", receipt_id=" + receipt_id + ", variant_id=" + variant_id + ", price=" + import_price + ", qty=" + quantity + '}';
    }
}
