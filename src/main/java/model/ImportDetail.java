/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author ASUS
 */
public class ImportDetail {
     private int ImportDetailId;
     private ImportReceipts ImportReceipts;
     private ProductVariants productVariants;
     private int quantity;
     private double importPrice;

    public ImportDetail() {
    }

    public ImportDetail(int ImportDetailId, ImportReceipts ImportReceipts, ProductVariants productVariants, int quantity, double importPrice) {
        this.ImportDetailId = ImportDetailId;
        this.ImportReceipts = ImportReceipts;
        this.productVariants = productVariants;
        this.quantity = quantity;
        this.importPrice = importPrice;
    }

    @Override
    public String toString() {
        return "ImportDetail{" + "ImportDetailId=" + ImportDetailId + ", ImportReceipts=" + ImportReceipts + ", productVariants=" + productVariants + ", quantity=" + quantity + ", importPrice=" + importPrice + '}';
    }

    
    
    public int getImportDetailId() {
        return ImportDetailId;
    }

    public void setImportDetailId(int ImportDetailId) {
        this.ImportDetailId = ImportDetailId;
    }

    public ImportReceipts getImportReceipts() {
        return ImportReceipts;
    }

    public void setImportReceipts(ImportReceipts ImportReceipts) {
        this.ImportReceipts = ImportReceipts;
    }

    public ProductVariants getProductVariants() {
        return productVariants;
    }

    public void setProductVariants(ProductVariants productVariants) {
        this.productVariants = productVariants;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getImportPrice() {
        return importPrice;
    }

    public void setImportPrice(double importPrice) {
        this.importPrice = importPrice;
    }

     
     
}
