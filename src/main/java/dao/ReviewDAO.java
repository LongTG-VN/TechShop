package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import model.Review;
import utils.DBContext;

public class ReviewDAO extends DBContext {

    // 1. Lấy tất cả đánh giá để Admin quản lý
    public List<Review> getAllReviews() {
        List<Review> list = new ArrayList<>();
        // Đảm bảo SQL JOIN đầy đủ để lấy product_name
        String sql = "SELECT r.*, c.full_name as customer_name, p.name as product_name "
                + "FROM reviews r "
                + "JOIN customers c ON r.customer_id = c.customer_id "
                + "JOIN order_items oi ON r.order_item_id = oi.order_item_id "
                + "JOIN inventory_items ii ON oi.inventory_id = ii.inventory_id "
                + "JOIN product_variants pv ON ii.variant_id = pv.variant_id "
                + "JOIN products p ON pv.product_id = p.product_id "
                + "ORDER BY r.created_at DESC";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                // Lấy timestamp từ DB và chuyển đổi
                java.sql.Timestamp ts = rs.getTimestamp("created_at");
                LocalDateTime ldt = (ts != null) ? ts.toLocalDateTime() : null;

                Review r = new Review(
                        rs.getInt("review_id"),
                        rs.getInt("customer_id"),
                        rs.getInt("order_item_id"),
                        rs.getInt("rating"),
                        rs.getString("comment"),
                        ldt // Truyền LocalDateTime vào đây, hết lỗi gạch đỏ!
                );
                r.setCustomerName(rs.getString("customer_name"));
                r.setProductName(rs.getString("product_name")); // Đã có tên sản phẩm
                list.add(r);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    // 2. Xóa đánh giá (Admin xóa nếu comment vi phạm)
    public void deleteReview(int id) {
        String sql = "DELETE FROM reviews WHERE review_id = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 3. Lấy chi tiết một đánh giá
    public Review getReviewById(int id) {
        // JOIN thêm chuỗi bảng từ order_items đến products để lấy tên sản phẩm
        String sql = "SELECT r.*, c.full_name as customer_name, p.name as product_name "
                + "FROM reviews r "
                + "JOIN customers c ON r.customer_id = c.customer_id "
                + "JOIN order_items oi ON r.order_item_id = oi.order_item_id "
                + "JOIN inventory_items ii ON oi.inventory_id = ii.inventory_id "
                + "JOIN product_variants pv ON ii.variant_id = pv.variant_id "
                + "JOIN products p ON pv.product_id = p.product_id "
                + "WHERE r.review_id = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Review r = mapResultSet(rs);
                r.setCustomerName(rs.getString("customer_name"));
                r.setProductName(rs.getString("product_name")); // Đảm bảo gán giá trị này
                return r;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<String> getReviewedProductNames() {
        List<String> list = new ArrayList<>();
        String sql = "SELECT DISTINCT p.name FROM reviews r "
                + "JOIN order_items oi ON r.order_item_id = oi.order_item_id "
                + "JOIN inventory_items ii ON oi.inventory_id = ii.inventory_id "
                + "JOIN product_variants pv ON ii.variant_id = pv.variant_id "
                + "JOIN products p ON pv.product_id = p.product_id";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(rs.getString(1));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    // 4. ĐẾM TỔNG SỐ ĐÁNH GIÁ
    public int countTotalReviews() {
        String sql = "SELECT COUNT(*) FROM reviews";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    // Hàm map dữ liệu nội bộ để tái sử dụng, khớp kiểu int cho ID
    private Review mapResultSet(ResultSet rs) throws Exception {
        Review r = new Review();
        r.setReviewId(rs.getInt("review_id"));
        r.setCustomerId(rs.getInt("customer_id"));
        r.setOrderItemId(rs.getInt("order_item_id"));
        r.setRating(rs.getInt("rating"));
        r.setComment(rs.getString("comment"));
        java.sql.Timestamp ts = rs.getTimestamp("created_at");
        if (ts != null) {
            r.setCreatedAt(ts.toLocalDateTime());
        }
        return r;
    }
}
