/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import model.Review;
import utils.DBContext;

/**
 *
 * @author CT
 */
public class ReviewDAO extends DBContext {

    private userDAO userDAO = new userDAO();
    private ProductDAO productDAO = new ProductDAO();

    public List<Review> getAllReview() {
        List<Review> list = new ArrayList<>();
        String sql = "SELECT        reviews.*\n"
                + "FROM            reviews";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {

                int ReviewId = rs.getInt("review_id");
                int userId = rs.getInt("user_id");
                int productId = rs.getInt("product_id");
                int rating = rs.getInt("rating");
                String commnet = rs.getString("comment");
                byte is_active = rs.getByte("is_active");
                String review_IMG_URL = rs.getString("review_image_url");
                LocalDateTime created_at = rs.getTimestamp("created_at").toLocalDateTime();

                list.add(new Review(ReviewId, userDAO.getUserById(userId), productDAO.getProductById(productId), rating, commnet, is_active, review_IMG_URL, created_at));
            }
        } catch (Exception e) {
        }
        return list;
    }

    private Review mapRowToReview(ResultSet rs) throws Exception {
        int reviewId = rs.getInt("review_id");
        int userId = rs.getInt("user_id");
        int productId = rs.getInt("product_id");
        int rating = rs.getInt("rating");
        String comment = rs.getString("comment");
        byte isActive = rs.getByte("is_active");
        String reviewImgUrl = rs.getString("review_image_url");

        // Lấy thời gian thực từ DB
        LocalDateTime created_at = rs.getTimestamp("created_at").toLocalDateTime();

        return new Review(reviewId, userDAO.getUserById(userId), productDAO.getProductById(productId), rating, comment, isActive, reviewImgUrl, created_at);
    }
    
    public Review getReviewById(int reviewId) {
        String sql = "SELECT * FROM reviews WHERE review_id = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, reviewId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return mapRowToReview(rs);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public boolean updateReview(Review review) {
        // Chỉ cho phép sửa: Điểm số, Nội dung, Ảnh, và Trạng thái ẩn/hiện
        // Không sửa: user_id, product_id, created_at (vì mấy cái này cố định)
        String sql = "UPDATE [reviews] SET "
                + "[rating] = ?, "
                + "[comment] = ?, "
                + "[review_image_url] = ?, "
                + "[is_active] = ? "
                + "WHERE [review_id] = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            
            ps.setInt(1, review.getRating());
            ps.setString(2, review.getComment());
            ps.setString(3, review.getReview_image_url()); // Đảm bảo model có getter này
            ps.setByte(4, review.getIs_active());         // Đảm bảo model có getter này
            ps.setInt(5, review.getReviewID());

            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static void main(String[] args) {
       ReviewDAO dao = new ReviewDAO();
        
        System.out.println("--- TEST 1: LIST ALL ---");
        List<Review> list = dao.getAllReview();
        for (Review review : list) {
            System.out.println(review);
        }

        System.out.println("\n--- TEST 2: EDIT REVIEW (ID = 1) ---");
        // Bước 1: Lấy review cũ lên
        int idToEdit = 1; 
        Review r = dao.getReviewById(idToEdit);

        if (r != null) {
            System.out.println("BEFORE UPDATE: " + r.getRating() + " sao - " + r.getComment());

            // Bước 2: Thay đổi thông tin
            r.setRating(1); // Sửa thành 1 sao
            r.setComment("Đã sửa: Sản phẩm dùng chán quá, trả lại tiền đây!");
            r.setReview_image_url("new_image_sad.jpg");
            
            // Bước 3: Gọi hàm update
            boolean isUpdated = dao.updateReview(r);
            
            if (isUpdated) {
                System.out.println("Update thành công!");
                // Bước 4: Kiểm tra lại trong DB
                Review after = dao.getReviewById(idToEdit);
                System.out.println("AFTER UPDATE:  " + after.getRating() + " sao - " + after.getComment());
            } else {
                System.out.println("Update thất bại!");
            }
        } else {
            System.out.println("Không tìm thấy Review có ID = " + idToEdit);
        }
    }

    }

