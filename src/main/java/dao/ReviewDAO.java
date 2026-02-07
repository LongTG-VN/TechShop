package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import model.Review;
import utils.DBContext;

public class ReviewDAO extends DBContext {

    // 1. Lấy tất cả đánh giá để Admin quản lý
    public List<Review> getAllReviews() {
        List<Review> list = new ArrayList<>();
        String sql = "SELECT r.*, c.full_name as customer_name, p.name as product_name " +
                     "FROM reviews r " +
                     "JOIN customers c ON r.customer_id = c.customer_id " +
                     "JOIN order_items oi ON r.order_item_id = oi.order_item_id " +
                     "JOIN inventory_items ii ON oi.inventory_id = ii.inventory_id " +
                     "JOIN product_variants pv ON ii.variant_id = pv.variant_id " +
                     "JOIN products p ON pv.product_id = p.product_id " +
                     "ORDER BY r.created_at DESC";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Review r = new Review(
                    rs.getInt("review_id"),
                    rs.getInt("customer_id"),
                    rs.getInt("order_item_id"),
                    rs.getInt("rating"),
                    rs.getString("comment"),
                    rs.getTimestamp("created_at")
                );
                r.setCustomerName(rs.getString("customer_name"));
                r.setProductName(rs.getString("product_name"));
                list.add(r);
            }
        } catch (Exception e) { e.printStackTrace(); }
        return list;
    }

    // 2. Xóa đánh giá (Admin xóa nếu comment vi phạm)
    public void deleteReview(int id) {
        String sql = "DELETE FROM reviews WHERE review_id = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (Exception e) { e.printStackTrace(); }
    }

    // 3. Lấy chi tiết một đánh giá
    public Review getReviewById(int id) {
        String sql = "SELECT r.*, c.full_name as customer_name FROM reviews r " +
                     "JOIN customers c ON r.customer_id = c.customer_id WHERE r.review_id = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Review r = new Review(
                    rs.getInt("review_id"),
                    rs.getInt("customer_id"),
                    rs.getInt("order_item_id"),
                    rs.getInt("rating"),
                    rs.getString("comment"),
                    rs.getTimestamp("created_at")
                );
                r.setCustomerName(rs.getString("customer_name"));
                return r;
            }
        } catch (Exception e) { e.printStackTrace(); }
        return null;
    }
}