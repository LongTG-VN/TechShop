/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import model.Supplier;
import utils.DBContext;

/**
 *
 * @author LE HOANG NHAN
 */
public class SupplierDAO extends DBContext {

    /**
     * Đọc toàn bộ supplier. Ghi chú: trả về list rỗng nếu DB lỗi/kết nối null
     * để UI vẫn chạy được.
     */
    public List<Supplier> getAllSuppliers() {
        List<Supplier> list = new ArrayList<>();
        if (conn == null) {
            return list;
        }
        String sql = "SELECT supplier_id, supplier_name, phone, is_active FROM suppliers";
        try (PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(mapRow(rs));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * Lấy supplier theo id. Trả về null nếu không tồn tại.
     */
    public Supplier getSupplierById(int id) {
        if (conn == null) {
            return null;
        }
        String sql = "SELECT supplier_id, supplier_name, phone, is_active FROM suppliers WHERE supplier_id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapRow(rs);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Thêm supplier mới.
     */
    public void insertSupplier(Supplier s) {
        if (conn == null || s == null) {
            return;
        }
        String sql = "INSERT INTO suppliers (supplier_name, phone, is_active) VALUES (?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, s.getSupplier_name());
            ps.setString(2, s.getPhone());
            ps.setBoolean(3, s.getIs_active());
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Cập nhật supplier theo id.
     */
    public void updateSupplier(Supplier s) {
        if (conn == null || s == null) {
            return;
        }
        String sql = "UPDATE suppliers SET supplier_name = ?, phone = ?, is_active = ? WHERE supplier_id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, s.getSupplier_name());
            ps.setString(2, s.getPhone());
            ps.setBoolean(3, s.getIs_active());
            ps.setInt(4, s.getSupplier_id());
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Tìm supplier theo tên hoặc số điện thoại (LIKE).
     */
    public List<Supplier> searchSuppliers(String keyword) {
        List<Supplier> list = new ArrayList<>();
        if (conn == null || keyword == null || keyword.trim().isEmpty()) {
            return getAllSuppliers();
        }
        String sql = "SELECT * FROM suppliers WHERE supplier_name LIKE ? OR phone LIKE ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            String pattern = "%" + keyword.trim() + "%";
            ps.setString(1, pattern);
            ps.setString(2, pattern);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(mapRow(rs));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * Soft delete: set is_active = 0.
     */
    public void deactivateSupplier(int id) {
        if (conn == null) {
            return;
        }
        String sql = "UPDATE suppliers SET is_active = 0 WHERE supplier_id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Khôi phục: set is_active = 1.
     */
    public void restoreSupplier(int id) {
        if (conn == null) {
            return;
        }
        String sql = "UPDATE suppliers SET is_active = 1 WHERE supplier_id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Kiểm tra supplier có đang được dùng trong import_receipts không. Nếu có
     * FK reference thì không nên hard delete.
     */
    public boolean isReferencedByReceipts(int supplierId) {
        if (conn == null) {
            return true;
        }
        String sql = "SELECT 1 FROM import_receipts WHERE supplier_id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, supplierId);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return true; // safe: assume referenced on error
        }
    }

    /**
     * Hard delete chỉ khi không bị FK reference. Trả về true nếu xóa thành
     * công.
     */
    public boolean deleteSupplierIfNoReferences(int id) {
        if (conn == null || isReferencedByReceipts(id)) {
            return false;
        }
        String sql = "DELETE FROM suppliers WHERE supplier_id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private Supplier mapRow(ResultSet rs) throws Exception {
        return new Supplier(
                rs.getInt("supplier_id"),
                rs.getString("supplier_name"),
                rs.getString("phone"),
                rs.getBoolean("is_active")
        );
    }
}
