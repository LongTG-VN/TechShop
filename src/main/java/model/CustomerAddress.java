package model;

/**
 * Model đại diện cho bảng customer_addresses
 *
 * @author ASUS
 */
public class CustomerAddress {

    private int addressId;
    private int customerId;
    private String address;
    private String phoneReceiver;
    private String nameReceiver;
    private boolean isDefault;

    public CustomerAddress() {
    }

    public CustomerAddress(int addressId, int customerId, String address, String phoneReceiver, String nameReceiver, boolean isDefault) {
        this.addressId = addressId;
        this.customerId = customerId;
        this.address = address;
        this.phoneReceiver = phoneReceiver;
        this.nameReceiver = nameReceiver;
        this.isDefault = isDefault;
    }

    // Getter và Setter
    public int getAddressId() {
        return addressId;
    }

    public void setAddressId(int addressId) {
        this.addressId = addressId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneReceiver() {
        return phoneReceiver;
    }

    public void setPhoneReceiver(String phoneReceiver) {
        this.phoneReceiver = phoneReceiver;
    }

    public String getNameReceiver() {
        return nameReceiver;
    }

    public void setNameReceiver(String nameReceiver) {
        this.nameReceiver = nameReceiver;
    }

    public boolean isIsDefault() {
        return isDefault;
    }

    public void setIsDefault(boolean isDefault) {
        this.isDefault = isDefault;
    }

    @Override
    public String toString() {
        return "Address{" + "id=" + addressId + ", customer=" + customerId
                + ", receiver=" + nameReceiver + ", phone=" + phoneReceiver
                + ", default=" + isDefault + '}';
    }
}
