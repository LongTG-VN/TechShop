/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import model.Supplier;
import utils.DBContext;

/**
 *
 * @author LE HOANG NHAN
 */
public class SupplierDAO extends DBContext {

    public boolean hasConnection() { // có conn mới gọi DB được
        return conn != null;
    }

    private static final String SQL_SELECT_BASE = "SELECT supplier_id, supplier_name, phone, email, address, is_active";
    private static final String SQL_FROM = " FROM suppliers";

    public List<Supplier> getAllSuppliers() { // lỗi cột created_at → legacy không có cột
        List<Supplier> list = new ArrayList<>();
        if (conn == null) {
            return list;
        }
        String sql = SQL_SELECT_BASE + ", created_at" + SQL_FROM;
        try (PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(mapRow(rs, true));
            }
        } catch (Exception e) {
            e.printStackTrace();
            list.addAll(getAllSuppliersLegacy());
        }
        return list;
    }

    private List<Supplier> getAllSuppliersLegacy() { // bảng cũ chưa có created_at
        List<Supplier> list = new ArrayList<>();
        if (conn == null) {
            return list;
        }
        String sql = SQL_SELECT_BASE + SQL_FROM;
        try (PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(mapRow(rs, false));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public Supplier getSupplierById(int id) {
        if (conn == null) {
            return null;
        }
        String sql = SQL_SELECT_BASE + ", created_at" + SQL_FROM + " WHERE supplier_id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapRow(rs, true);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return getSupplierByIdLegacy(id);
        }
        return null;
    }

    private Supplier getSupplierByIdLegacy(int id) { // không có cột created_at
        if (conn == null) {
            return null;
        }
        String sql = SQL_SELECT_BASE + SQL_FROM + " WHERE supplier_id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapRow(rs, false);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // trùng cặp (tên + địa chỉ) — cùng tên khác địa chỉ vẫn ok; excludeId khi sửa
    public boolean existsSameNameAndAddress(String trimmedName, String addressRaw, int excludeSupplierId) {
        if (conn == null || trimmedName == null) {
            return false;
        }
        String n = trimmedName.trim();
        if (n.isEmpty()) {
            return false;
        }
        String addrKey = addressRaw == null ? "" : addressRaw.trim();
        String sql = excludeSupplierId > 0
                ? "SELECT 1 FROM suppliers WHERE supplier_id <> ? AND LOWER(LTRIM(RTRIM(supplier_name))) = LOWER(?)"
                + " AND LOWER(LTRIM(RTRIM(ISNULL(address,'')))) = LOWER(?)"
                : "SELECT 1 FROM suppliers WHERE LOWER(LTRIM(RTRIM(supplier_name))) = LOWER(?)"
                + " AND LOWER(LTRIM(RTRIM(ISNULL(address,'')))) = LOWER(?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            if (excludeSupplierId > 0) {
                ps.setInt(1, excludeSupplierId);
                ps.setString(2, n);
                ps.setString(3, addrKey);
            } else {
                ps.setString(1, n);
                ps.setString(2, addrKey);
            }
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // email đã lowercase từ servlet; excludeId khi update
    public boolean existsSupplierEmail(String emailLower, int excludeSupplierId) {
        if (conn == null || emailLower == null || emailLower.isEmpty()) {
            return false;
        }
        String sql = excludeSupplierId > 0
                ? "SELECT 1 FROM suppliers WHERE supplier_id <> ? AND LOWER(LTRIM(RTRIM(ISNULL(email,'')))) = ?"
                : "SELECT 1 FROM suppliers WHERE LOWER(LTRIM(RTRIM(ISNULL(email,'')))) = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            if (excludeSupplierId > 0) {
                ps.setInt(1, excludeSupplierId);
                ps.setString(2, emailLower);
            } else {
                ps.setString(1, emailLower);
            }
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean insertSupplier(Supplier s) {
        if (conn == null || s == null) {
            return false;
        }
        String sql = "INSERT INTO suppliers (supplier_name, phone, email, address, is_active, created_at) VALUES (?, ?, ?, ?, ?, SYSDATETIME())";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, s.getSupplier_name());
            ps.setString(2, s.getPhone());
            ps.setString(3, emptyToNull(s.getEmail()));
            ps.setString(4, emptyToNull(s.getAddress()));
            ps.setBoolean(5, s.getIs_active());
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            String legacy = "INSERT INTO suppliers (supplier_name, phone, email, address, is_active) VALUES (?, ?, ?, ?, ?)"; // DB chưa có created_at
            try (PreparedStatement ps = conn.prepareStatement(legacy)) {
                ps.setString(1, s.getSupplier_name());
                ps.setString(2, s.getPhone());
                ps.setString(3, emptyToNull(s.getEmail()));
                ps.setString(4, emptyToNull(s.getAddress()));
                ps.setBoolean(5, s.getIs_active());
                return ps.executeUpdate() > 0;
            } catch (Exception e2) {
                e2.printStackTrace();
                return false;
            }
        }
    }

    public boolean updateSupplier(Supplier s) {
        if (conn == null || s == null) {
            return false;
        }
        String sql = "UPDATE suppliers SET supplier_name = ?, phone = ?, email = ?, address = ?, is_active = ? WHERE supplier_id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, s.getSupplier_name());
            ps.setString(2, s.getPhone());
            ps.setString(3, emptyToNull(s.getEmail()));
            ps.setString(4, emptyToNull(s.getAddress()));
            ps.setBoolean(5, s.getIs_active());
            ps.setInt(6, s.getSupplier_id());
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // Tìm theo tên hoặc SĐT (không theo email/địa chỉ). Keyword rỗng → list đầy đủ.
    public List<Supplier> searchSuppliers(String keyword) {
        List<Supplier> list = new ArrayList<>();
        if (conn == null || keyword == null || keyword.trim().isEmpty()) {
            return getAllSuppliers();
        }
        String sql = SQL_SELECT_BASE + ", created_at" + SQL_FROM
                + " WHERE supplier_name LIKE ? OR phone LIKE ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            String pattern = "%" + keyword.trim() + "%";
            ps.setString(1, pattern);
            ps.setString(2, pattern);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(mapRow(rs, true));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return searchSuppliersLegacy(keyword); // DB không có created_at
        }
        return list;
    }

    private List<Supplier> searchSuppliersLegacy(String keyword) { // fallback không select created_at
        List<Supplier> list = new ArrayList<>();
        if (conn == null) {
            return list;
        }
        String sql = SQL_SELECT_BASE + SQL_FROM
                + " WHERE supplier_name LIKE ? OR phone LIKE ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            String pattern = "%" + keyword.trim() + "%";
            ps.setString(1, pattern);
            ps.setString(2, pattern);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(mapRow(rs, false));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean deactivateSupplier(int id) { // is_active = 0
        if (conn == null) {
            return false;
        }
        String sql = "UPDATE suppliers SET is_active = 0 WHERE supplier_id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean restoreSupplier(int id) { // is_active = 1
        if (conn == null) {
            return false;
        }
        String sql = "UPDATE suppliers SET is_active = 1 WHERE supplier_id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean isReferencedByReceipts(int supplierId) {
        if (conn == null) {
            return true; // không query được → coi như đang dùng, khỏi xóa nhầm
        }
        String sql = "SELECT 1 FROM import_receipts WHERE supplier_id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, supplierId);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next(); // có ít nhất 1 phiếu nhập → đang reference
            }
        } catch (Exception e) {
            e.printStackTrace();
            return true; // lỗi thì không cho xóa
        }
    }

    public boolean deleteSupplierIfNoReferences(int id) { // hard delete, có FK thì không xóa
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

    private Supplier mapRow(ResultSet rs, boolean hasCreatedAt) throws Exception { // hasCreatedAt: bảng có cột created_at
        Timestamp created = null;
        if (hasCreatedAt) {
            created = rs.getTimestamp("created_at");
        }
        return new Supplier(
                rs.getInt("supplier_id"),
                rs.getString("supplier_name"),
                rs.getString("phone"),
                rs.getString("email"),
                rs.getString("address"),
                rs.getBoolean("is_active"),
                created
        );
    }

    private static String emptyToNull(String s) {
        if (s == null) {
            return null;
        }
        String t = s.trim();
        return t.isEmpty() ? null : t;
    }
}
