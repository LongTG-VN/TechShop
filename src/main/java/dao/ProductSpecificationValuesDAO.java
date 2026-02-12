package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import model.ProductSpecificationValues;
import utils.DBContext;

public class ProductSpecificationValuesDAO extends DBContext {

    // 1. LẤY TẤT CẢ (JOIN lấy tên để hiện bảng Admin)
    public List<ProductSpecificationValues> getAllProductSpecs() {
        List<ProductSpecificationValues> list = new ArrayList<>();
        String sql = "SELECT v.*, p.name as product_name, s.spec_name "
                + "FROM product_specification_values v "
                + "JOIN products p ON v.product_id = p.product_id "
                + "JOIN specification_definitions s ON v.spec_id = s.spec_id "
                + "ORDER BY p.product_id DESC";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                ProductSpecificationValues v = mapResultSet(rs);
                // Gán tên hiển thị từ lệnh JOIN
                // v.setProductName(rs.getString("product_name"));
                // v.setSpecName(rs.getString("spec_name"));
                list.add(v);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    // 2. LẤY THEO KHÓA KÉP (Dùng cho trang Edit/Detail)
    public ProductSpecificationValues getSpecValueById(int productId, int specId) {
        String sql = "SELECT * FROM product_specification_values WHERE product_id = ? AND spec_id = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, productId);
            ps.setInt(2, specId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return mapResultSet(rs);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // 3. THÊM MỚI
    public void insertProductSpec(ProductSpecificationValues v) {
        String sql = "INSERT INTO product_specification_values (product_id, spec_id, spec_value) VALUES (?, ?, ?)";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, v.getProductId());
            ps.setInt(2, v.getSpecId());
            ps.setString(3, v.getSpecValue());
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 4. CẬP NHẬT
    public void updateProductSpec(ProductSpecificationValues v) {
        String sql = "UPDATE product_specification_values SET spec_value = ? WHERE product_id = ? AND spec_id = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, v.getSpecValue());
            ps.setInt(2, v.getProductId());
            ps.setInt(3, v.getSpecId());
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 5. XÓA CỨNG (Vì không có is_active nên xóa thẳng)
    public void deleteProductSpec(int productId, int specId) {
        String sql = "DELETE FROM product_specification_values WHERE product_id = ? AND spec_id = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, productId);
            ps.setInt(2, specId);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 6. XÓA TẤT CẢ THÔNG SỐ CỦA 1 SẢN PHẨM (Dùng khi xóa Product)
    public void deleteAllSpecsByProduct(int productId) {
        String sql = "DELETE FROM product_specification_values WHERE product_id = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, productId);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private ProductSpecificationValues mapResultSet(ResultSet rs) throws Exception {
        ProductSpecificationValues v = new ProductSpecificationValues();
        v.setProductId(rs.getInt("product_id"));
        v.setSpecId(rs.getInt("spec_id"));
        v.setSpecValue(rs.getString("spec_value"));
        return v;
    }
}
