package model;

/**
 * Summary of inventory by variant (imported / sold / in stock).
 */
public class InventorySummary { 

    private int variantId;
    private String productName;
    private String sku;
    private int imported;
    private int sold;
    private int inStock;

    public InventorySummary(int variantId, String productName, String sku,
                            int imported, int sold, int inStock) {
        this.variantId = variantId;
        this.productName = productName;
        this.sku = sku;
        this.imported = imported;
        this.sold = sold;
        this.inStock = inStock;
    }

    public int getVariantId() {
        return variantId;
    }

    public String getProductName() {
        return productName;
    }

    public String getSku() {
        return sku;
    }

    public int getImported() {
        return imported;
    }

    public int getSold() {
        return sold;
    }

    public int getInStock() {
        return inStock;
    }
}

