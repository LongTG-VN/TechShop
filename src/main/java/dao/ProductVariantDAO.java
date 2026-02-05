package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import model.ProductVariant;
import utils.DBContext;

/**
 *
 * @author CT
 */
public class ProductVariantDAO extends DBContext {

    // 1. Lấy tất cả biến thể
    public List<ProductVariant> getAllVariants() {
        List<ProductVariant> list = new ArrayList<>();
        String sql = "SELECT v.*, p.name as product_name FROM product_variants v "
                + "JOIN products p ON v.product_id = p.product_id "
                + "ORDER BY v.variant_id DESC";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                ProductVariant v = mapResultSet(rs);
                v.setProductName(rs.getString("product_name"));
                list.add(v);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    // 2. Lấy theo ID
    public ProductVariant getVariantById(int id) {
        String sql = "SELECT v.*, p.name as product_name FROM product_variants v "
                + "JOIN products p ON v.product_id = p.product_id "
                + "WHERE v.variant_id = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                ProductVariant v = mapResultSet(rs);
                v.setProductName(rs.getString("product_name"));
                return v;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // 3. Kiểm tra biến thể đã có đơn hàng chưa (Chặn xóa cứng)
    public int countVariantInOrderDetails(int variantId) {
        String sql = "SELECT COUNT(*) FROM order_items oi "
                + "JOIN inventory_items ii ON oi.inventory_id = ii.inventory_id "
                + "WHERE ii.variant_id = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, variantId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    // 4. Thêm mới
    public void insertVariant(ProductVariant v) {
        String sql = "INSERT INTO product_variants (product_id, sku, selling_price, is_active) VALUES (?, ?, ?, ?)";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, v.getProductId());
            ps.setString(2, v.getSku());
            ps.setLong(3, v.getSellingPrice());
            ps.setBoolean(4, v.isIsActive());
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 5. Cập nhật (Sửa biến thể)
    public void updateVariant(ProductVariant v) {
        String sql = "UPDATE product_variants SET product_id=?, sku=?, selling_price=?, is_active=? WHERE variant_id=?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, v.getProductId());
            ps.setString(2, v.getSku());
            ps.setLong(3, v.getSellingPrice());
            ps.setBoolean(4, v.isIsActive());
            ps.setInt(5, v.getVariantId());
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 6. Xóa cứng (Hard Delete)
    public void deleteVariant(int id) {
        String sql = "DELETE FROM product_variants WHERE variant_id = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 7. Xóa mềm (Soft Delete)
    public void softDeleteVariant(int id) {
        String sql = "UPDATE product_variants SET is_active = 0 WHERE variant_id = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private ProductVariant mapResultSet(ResultSet rs) throws Exception {
        return new ProductVariant(
                rs.getInt("variant_id"),
                rs.getInt("product_id"),
                rs.getString("sku"),
                rs.getLong("selling_price"),
                rs.getBoolean("is_active")
        );
    }
}
