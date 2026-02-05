package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import model.Product;
import utils.DBContext;

public class ProductDAO extends DBContext {

    // 1. Lấy tất cả sản phẩm
    // Trong ProductDAO.java
    public List<Product> getAllProduct() {
        List<Product> list = new ArrayList<>();
        // Thực hiện JOIN để lấy Category Name và Brand Name
        String sql = "SELECT p.*, c.category_name, b.brand_name "
                + "FROM products p "
                + "LEFT JOIN categories c ON p.category_id = c.category_id "
                + "LEFT JOIN brands b ON p.brand_id = b.brand_id "
                + "ORDER BY p.product_id DESC";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Product p = mapResultSetToProduct(rs);
                // Gán thêm tên từ kết quả JOIN
                p.setCategoryName(rs.getString("category_name"));
                p.setBrandName(rs.getString("brand_name"));
                list.add(p);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    // 2. Lấy sản phẩm theo ID
    public Product getProductById(int id) {
        // SỬA: Phải JOIN với bảng categories và brands để lấy tên hiển thị
        String sql = "SELECT p.*, c.category_name, b.brand_name "
                + "FROM products p "
                + "LEFT JOIN categories c ON p.category_id = c.category_id "
                + "LEFT JOIN brands b ON p.brand_id = b.brand_id "
                + "WHERE p.product_id = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Product p = mapResultSetToProduct(rs);
                // QUAN TRỌNG: Phải gán thêm tên từ kết quả truy vấn vào object
                p.setCategoryName(rs.getString("category_name"));
                p.setBrandName(rs.getString("brand_name"));
                return p;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // 3. Kiểm tra sản phẩm đã có trong đơn hàng chưa (Để quyết định xóa mềm/cứng)
    public int countProductInOrderDetails(int productId) {
        String sql = "SELECT COUNT(*) FROM order_items oi "
                + "JOIN inventory_items ii ON oi.inventory_id = ii.inventory_id "
                + "JOIN product_variants pv ON ii.variant_id = pv.variant_id "
                + "WHERE pv.product_id = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, productId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    // 4. Kiểm tra trùng tên
    public boolean isProductDuplicate(String name, int categoryId, int brandId, int excludeId) {
        // Câu lệnh SQL kiểm tra trùng tên + danh mục + thương hiệu
        // nhưng bỏ qua bản ghi có ID hiện tại (dùng cho Update)
        String sql = "SELECT COUNT(*) FROM products "
                + "WHERE name = ? AND category_id = ? AND brand_id = ? AND product_id <> ?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, name);
            ps.setInt(2, categoryId);
            ps.setInt(3, brandId);
            ps.setInt(4, excludeId); // Nếu là Add, truyền vào số 0

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // 5. Thêm mới sản phẩm
    public void insertProduct(Product p) {
        String sql = "INSERT INTO products (name, category_id, brand_id, description, status, created_at, created_by) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, p.getName());
            ps.setInt(2, p.getCategoryId());
            ps.setInt(3, p.getBrandId());
            ps.setString(4, p.getDescription());
            ps.setString(5, p.getStatus());
            ps.setTimestamp(6, java.sql.Timestamp.valueOf(java.time.LocalDateTime.now()));
            ps.setObject(7, p.getCreatedBy()); // ID của admin đang đăng nhập
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 6. Cập nhật sản phẩm
    public void updateProduct(Product p) {
        String sql = "UPDATE products SET name=?, category_id=?, brand_id=?, description=?, status=?, updated_at=? WHERE product_id=?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, p.getName());
            ps.setInt(2, p.getCategoryId());
            ps.setInt(3, p.getBrandId());
            ps.setString(4, p.getDescription());
            ps.setString(5, p.getStatus());
            ps.setTimestamp(6, Timestamp.valueOf(LocalDateTime.now()));
            ps.setInt(7, p.getProductId());
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 7. Xóa cứng
    public void deleteProduct(int id) {
        String sql = "DELETE FROM products WHERE product_id = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 8. Xóa mềm (Chuyển Inactive)
    public void softDeleteProduct(int id) {
        String sql = "UPDATE products SET status = 'Inactive', updated_at = ? WHERE product_id = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setTimestamp(1, Timestamp.valueOf(LocalDateTime.now()));
            ps.setInt(2, id);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Product mapResultSetToProduct(ResultSet rs) throws Exception {
        Product p = new Product();
        p.setProductId(rs.getInt("product_id"));
        p.setName(rs.getString("name"));
        p.setCategoryId(rs.getInt("category_id"));
        p.setBrandId(rs.getInt("brand_id"));
        p.setDescription(rs.getString("description"));
        p.setStatus(rs.getString("status"));
        Timestamp ct = rs.getTimestamp("created_at");
        if (ct != null) {
            p.setCreatedAt(ct.toLocalDateTime());
        }
        Timestamp ut = rs.getTimestamp("updated_at");
        if (ut != null) {
            p.setUpdatedAt(ut.toLocalDateTime());
        }
        return p;
    }
}
