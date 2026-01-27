package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import model.CustomerAddress;
import utils.DBContext;

public class CustomerAddressDAO extends DBContext {

    private CustomerAddress mapResultSetToAddress(ResultSet rs) throws Exception {
        return new CustomerAddress(
                rs.getInt("address_id"),
                rs.getInt("customer_id"),
                rs.getNString("address"),
                rs.getString("phone_receiver"),
                rs.getNString("name_receiver"),
                rs.getBoolean("is_default")
        );
    }

    public List<CustomerAddress> getAddressesByCustomerId(int customerId) {
        List<CustomerAddress> list = new ArrayList<>();
        String sql = "SELECT * FROM customer_addresses WHERE customer_id = ? ORDER BY is_default DESC";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, customerId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(mapResultSetToAddress(rs));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean insertAddress(CustomerAddress ad) {
        if (ad.isIsDefault()) {
            resetDefaultAddress(ad.getCustomerId());
        }
        String sql = "INSERT INTO customer_addresses (customer_id, address, phone_receiver, name_receiver, is_default) VALUES (?, ?, ?, ?, ?)";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, ad.getCustomerId());
            ps.setNString(2, ad.getAddress());
            ps.setString(3, ad.getPhoneReceiver());
            ps.setNString(4, ad.getNameReceiver());
            ps.setBoolean(5, ad.isIsDefault());
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean updateAddress(CustomerAddress ad) {
        if (ad.isIsDefault()) {
            resetDefaultAddress(ad.getCustomerId());
        }
        String sql = "UPDATE customer_addresses SET address = ?, phone_receiver = ?, name_receiver = ?, is_default = ? WHERE address_id = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setNString(1, ad.getAddress());
            ps.setString(2, ad.getPhoneReceiver());
            ps.setNString(3, ad.getNameReceiver());
            ps.setBoolean(4, ad.isIsDefault());
            ps.setInt(5, ad.getAddressId());
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean deleteAddress(int addressId) {
        String sql = "DELETE FROM customer_addresses WHERE address_id = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, addressId);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    private void resetDefaultAddress(int customerId) {
        String sql = "UPDATE customer_addresses SET is_default = 0 WHERE customer_id = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, customerId);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean setAsDefault(int addressId, int customerId) {
        resetDefaultAddress(customerId);
        String sql = "UPDATE customer_addresses SET is_default = 1 WHERE address_id = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, addressId);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static void main(String[] args) {
        CustomerAddressDAO dao = new CustomerAddressDAO();
        int testCustomerId = 1; // Đảm bảo ID này tồn tại trong bảng customers

        System.out.println("--- 1. TEST LẤY DANH SÁCH BAN ĐẦU ---");
        List<CustomerAddress> list = dao.getAddressesByCustomerId(testCustomerId);
        list.forEach(System.out::println);

        System.out.println("\n--- 2. TEST THÊM ĐỊA CHỈ MỚI ---");
        CustomerAddress newAd = new CustomerAddress(0, testCustomerId, "999 Đường Bưởi, Ba Đình, Hà Nội", "0333555777", "Người Nhận Test", true);
        if (dao.insertAddress(newAd)) {
            System.out.println("Thêm thành công!");
        }

        System.out.println("\n--- 3. TEST CẬP NHẬT TÊN NGƯỜI NHẬN ---");
        list = dao.getAddressesByCustomerId(testCustomerId); // Load lại list mới
        if (!list.isEmpty()) {
            CustomerAddress toUpdate = list.get(0);
            toUpdate.setNameReceiver("Tên Đã Sửa");
            dao.updateAddress(toUpdate);
            System.out.println("Cập nhật thành công ID: " + toUpdate.getAddressId());
        }

        System.out.println("\n--- 4. DANH SÁCH CUỐI CÙNG ---");
        dao.getAddressesByCustomerId(testCustomerId).forEach(System.out::println);
    }
}