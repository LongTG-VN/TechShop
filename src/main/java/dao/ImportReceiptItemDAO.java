/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import model.ImportReceiptItem;
import utils.DBContext;

/**
 *
 * @author LE HOANG NHAN
 */
public class ImportReceiptItemDAO extends DBContext {

    private String itemsTable;

    private String resolveItemsTable() {
        if (itemsTable != null) {
            return itemsTable;
        }
        itemsTable = "import_receipt_items";
        try {
            DatabaseMetaData meta = conn.getMetaData();
            String[] candidates = {"import_receipt_items", "import_receipt_item"};
            for (String candidate : candidates) {
                try (ResultSet rs = meta.getTables(null, null, candidate, new String[]{"TABLE"})) {
                    if (rs.next()) {
                        itemsTable = rs.getString("TABLE_NAME");
                        return itemsTable;
                    }
                }
                try (ResultSet rs = meta.getTables(null, null, candidate.toUpperCase(), new String[]{"TABLE"})) {
                    if (rs.next()) {
                        itemsTable = rs.getString("TABLE_NAME");
                        return itemsTable;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return itemsTable;
    }

    // 1. READ ALL
    public List<ImportReceiptItem> getAllItems() {
        List<ImportReceiptItem> list = new ArrayList<>();
        String sql = "SELECT * FROM " + resolveItemsTable();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new ImportReceiptItem(
                        rs.getInt("receipt_item_id"),
                        rs.getInt("receipt_id"),
                        rs.getInt("variant_id"),
                        rs.getDouble("import_price"),
                        rs.getInt("quantity")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    /** Lấy tất cả item theo receipt_id (để hiển thị trong detail phiếu nhập), sắp xếp tăng dần theo ID. */
    public List<ImportReceiptItem> getItemsByReceiptId(int receiptId) {
        List<ImportReceiptItem> list = new ArrayList<>();
        String sql = "SELECT * FROM " + resolveItemsTable() + " WHERE receipt_id = ? ORDER BY receipt_item_id ASC";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, receiptId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(new ImportReceiptItem(
                            rs.getInt("receipt_item_id"),
                            rs.getInt("receipt_id"),
                            rs.getInt("variant_id"),
                            rs.getDouble("import_price"),
                            rs.getInt("quantity")
                    ));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    /** Returns first receipt_item_id for default use (e.g. add inventory without choosing receipt). 0 if none. */
    public int getFirstReceiptItemId() {
        String sql = "SELECT TOP 1 receipt_item_id FROM " + resolveItemsTable() + " ORDER BY receipt_item_id ASC";
        try (PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            if (rs.next()) return rs.getInt("receipt_item_id");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    // 2. GET BY ID
    public ImportReceiptItem getItemById(int id) {
        String sql = "SELECT * FROM " + resolveItemsTable() + " WHERE receipt_item_id = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new ImportReceiptItem(
                        rs.getInt("receipt_item_id"),
                        rs.getInt("receipt_id"),
                        rs.getInt("variant_id"),
                        rs.getDouble("import_price"),
                        rs.getInt("quantity")
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // 3. INSERT
    public void insertItem(ImportReceiptItem item) {
        String sql = "INSERT INTO " + resolveItemsTable() + " (receipt_id, variant_id, import_price, quantity) VALUES (?, ?, ?, ?)";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, item.getReceipt_id());
            ps.setInt(2, item.getVariant_id());
            ps.setDouble(3, item.getImport_price());
            ps.setInt(4, item.getQuantity());
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ImportReceiptItem getFirstItemByReceiptAndVariant(int receiptId, int variantId) {
        String sql = "SELECT TOP 1 receipt_item_id, receipt_id, variant_id, import_price, quantity "
                + "FROM " + resolveItemsTable()
                + " WHERE receipt_id = ? AND variant_id = ? ORDER BY receipt_item_id ASC";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, receiptId);
            ps.setInt(2, variantId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new ImportReceiptItem(
                            rs.getInt("receipt_item_id"),
                            rs.getInt("receipt_id"),
                            rs.getInt("variant_id"),
                            rs.getDouble("import_price"),
                            rs.getInt("quantity")
                    );
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Cùng một phiếu nhập: cùng SKU luôn gộp quantity vào một dòng (không tách theo giá nhập).
     */
    public void mergeOrInsertLine(int receiptId, int variantId, double importPrice, int qty) {
        if (qty <= 0) {
            return;
        }
        boolean oldAutoCommit = true;
        try {
            oldAutoCommit = conn.getAutoCommit();
            conn.setAutoCommit(false);

            String updateSql = "UPDATE " + resolveItemsTable()
                    + " SET quantity = quantity + ? WHERE receipt_id = ? AND variant_id = ?";
            int updatedRows;
            try (PreparedStatement u = conn.prepareStatement(updateSql)) {
                u.setInt(1, qty);
                u.setInt(2, receiptId);
                u.setInt(3, variantId);
                updatedRows = u.executeUpdate();
            }

            if (updatedRows == 0) {
                String insertSql = "INSERT INTO " + resolveItemsTable()
                        + " (receipt_id, variant_id, import_price, quantity) VALUES (?, ?, ?, ?)";
                try (PreparedStatement i = conn.prepareStatement(insertSql)) {
                    i.setInt(1, receiptId);
                    i.setInt(2, variantId);
                    i.setDouble(3, importPrice);
                    i.setInt(4, qty);
                    i.executeUpdate();
                }
            }

            conn.commit();
        } catch (Exception e) {
            try {
                conn.rollback();
            } catch (Exception ignored) {
            }
            e.printStackTrace();
        } finally {
            try {
                conn.setAutoCommit(oldAutoCommit);
            } catch (Exception ignored) {
            }
        }
    }

    // 4. UPDATE
    public void updateItem(ImportReceiptItem item) {
        String sql = "UPDATE " + resolveItemsTable() + " SET receipt_id=?, variant_id=?, import_price=?, quantity=? WHERE receipt_item_id=?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, item.getReceipt_id());
            ps.setInt(2, item.getVariant_id());
            ps.setDouble(3, item.getImport_price());
            ps.setInt(4, item.getQuantity());
            ps.setInt(5, item.getReceipt_item_id());
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 5. DELETE - trả về true nếu xóa thành công, false nếu lỗi (ví dụ dính khóa ngoại)
    public boolean deleteItem(int id) {
        String sql = "DELETE FROM " + resolveItemsTable() + " WHERE receipt_item_id = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // --- MAIN TEST ---
    public static void main(String[] args) {
        ImportReceiptItemDAO dao = new ImportReceiptItemDAO();

        // 1. Xem danh sach
        System.out.println("--- LIST ALL ---");
        List<ImportReceiptItem> list = dao.getAllItems();
        for (ImportReceiptItem item : list) {
            System.out.println(item);
        }

        // 2. Insert (Chèn)
        // LƯU Ý: receipt_id = 1 và variant_id = 1 PHẢI TỒN TẠI trong DB rồi mới chạy được lệnh này
        // Nếu chưa có, bạn hãy sửa số 1 thành ID nào đang có thật.
        ImportReceiptItem newItem = new ImportReceiptItem(0, 1, 1, 5000000, 10);
        dao.insertItem(newItem);
        System.out.println("Da them item moi.");

        // 3. Update (Sửa)
        // Sửa item có ID = 1 (Nhớ thay ID thực tế của bạn vào)
        ImportReceiptItem updateItem = new ImportReceiptItem(1, 1, 1, 4500000, 20);
        dao.updateItem(updateItem);
        System.out.println("Da update item ID = 1.");

        // 4. Delete (Xóa)
        // Xóa item có ID = 2
        dao.deleteItem(2);
        System.out.println("Da xoa item ID = 2.");

        // 5. Get by ID
        System.out.println("--- GET BY ID 1 ---");
        System.out.println(dao.getItemById(1));
    }
}
