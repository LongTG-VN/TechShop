package model;

/**
 *
 * @author ASUS
 */
public class ProductImage {

    private int imageID;
    private Product product;
    private String imageUrl;
    private byte is_thumbnail;

    public ProductImage() {
    }

    public ProductImage(int imageID, Product product, String imageUrl, byte is_thumbnail) {
        this.imageID = imageID;
        this.product = product;
        this.imageUrl = imageUrl;
        this.is_thumbnail = is_thumbnail;
    }

    public int getImageID() {
        return imageID;
    }

    public void setImageID(int imageID) {
        this.imageID = imageID;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public byte getIs_thumbnail() {
        return is_thumbnail;
    }

    public void setIs_thumbnail(byte is_thumbnail) {
        this.is_thumbnail = is_thumbnail;
    }

    @Override
    public String toString() {
        return "ProductImage{" + "imageID=" + imageID + ", productl=" + product + ", imageUrl=" + imageUrl + ", is_thumbnail=" + is_thumbnail + '}';
    }

}
