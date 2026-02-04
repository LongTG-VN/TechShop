/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import model.InventoryItem;
import utils.DBContext;

/**
 *
 * @author LE HOANG NHAN
 */
public class InventoryItemDAO extends DBContext {

    // 1. GET ALL
    public List<InventoryItem> getAllInventory() {
        List<InventoryItem> list = new ArrayList<>();
        String sql = "SELECT * FROM inventory_items";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new InventoryItem(
                        rs.getInt("inventory_id"),
                        rs.getInt("variant_id"),
                        rs.getInt("receipt_item_id"),
                        rs.getString("imei"),
                        rs.getDouble("import_price"),
                        rs.getString("status")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    // 2. GET BY ID
    public InventoryItem getInventoryById(int id) {
        String sql = "SELECT * FROM inventory_items WHERE inventory_id = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new InventoryItem(
                        rs.getInt("inventory_id"),
                        rs.getInt("variant_id"),
                        rs.getInt("receipt_item_id"),
                        rs.getString("imei"),
                        rs.getDouble("import_price"),
                        rs.getString("status")
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // 3. INSERT (Thêm sản phẩm vào kho)
    // Lưu ý: IMEI không được trùng
    public void insertInventory(InventoryItem item) {
        String sql = "INSERT INTO inventory_items (variant_id, receipt_item_id, imei, import_price, status) VALUES (?, ?, ?, ?, ?)";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, item.getVariant_id());
            ps.setInt(2, item.getReceipt_item_id());
            ps.setString(3, item.getImei());
            ps.setDouble(4, item.getImport_price());
            // Nếu status null thì mặc định set là IN_STOCK
            ps.setString(5, (item.getStatus() == null || item.getStatus().isEmpty()) ? "IN_STOCK" : item.getStatus());

            ps.executeUpdate();
        } catch (Exception e) {
            // In ra lỗi nếu trùng IMEI hoặc sai khóa ngoại
            e.printStackTrace();
        }
    }

    // 4. UPDATE
    public void updateInventory(InventoryItem item) {
        String sql = "UPDATE inventory_items SET variant_id=?, receipt_item_id=?, imei=?, import_price=?, status=? WHERE inventory_id=?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, item.getVariant_id());
            ps.setInt(2, item.getReceipt_item_id());
            ps.setString(3, item.getImei());
            ps.setDouble(4, item.getImport_price());
            ps.setString(5, item.getStatus());
            ps.setInt(6, item.getInventory_id());
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 5. DELETE
    public void deleteInventory(int id) {
        String sql = "DELETE FROM inventory_items WHERE inventory_id = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // --- MAIN TEST ---
    public static void main(String[] args) {
        InventoryItemDAO dao = new InventoryItemDAO();

        // 1. List All
        System.out.println("--- LIST ALL ---");
        List<InventoryItem> list = dao.getAllInventory();
        for (InventoryItem i : list) {
            System.out.println(i);
        }

        // 2. Insert Test
        // LƯU Ý KHI CHẠY: 
        // - variant_id (số 1 đầu tiên) phải có trong bảng product_variants
        // - receipt_item_id (số 1 thứ hai) phải có trong bảng import_receipt_items
        // - IMEI "IMΕΙ-001" chưa từng tồn tại
        System.out.println("--- INSERT ---");
        InventoryItem newItem = new InventoryItem(0, 1, 1, "IMEI-TEST-001", 15000000, "IN_STOCK");
        dao.insertInventory(newItem);
        System.out.println("Da them san pham vao kho.");

        // 3. Update Test
        // Giả sử update item có ID = 1
        // InventoryItem upItem = new InventoryItem(1, 1, 1, "IMEI-TEST-001", 15000000, "SOLD");
        // dao.updateInventory(upItem);
        // System.out.println("Da update trang thai.");
        // 4. Delete Test
        // dao.deleteInventory(2);
        // System.out.println("Da xoa item ID 2.");
    }
}
