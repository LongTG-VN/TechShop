/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import model.User;
import model.Wishlist;
import model.Product;
import utils.DBContext;

/**
 *
 * @author ASUS
 */
public class WishlistDAO extends DBContext {

    private UserDAO userDAO = new UserDAO();
    private ProductDAO productDAO = new ProductDAO();

    public List<Wishlist> getAllWishlists() {

        List<Wishlist> list = new ArrayList<>();

        String sql = "SELECT        wishlists.*\n"
                + "FROM            wishlists";

        try {

            PreparedStatement ps = conn.prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                int wishlistId = rs.getInt("wishlist_id");

                int userId = rs.getInt("user_id");

                int productId = rs.getInt("product_id");

                LocalDateTime added_at = rs.getTimestamp("added_at").toLocalDateTime();

                list.add(new Wishlist(wishlistId, userDAO.getUserById(userId), productDAO.getProductById(productId), added_at));

            }

        } catch (Exception e) {

        }

        return list;

    }

    // 1. Lấy danh sách yêu thích của MỘT User cụ thể (Hiển thị trang Wishlist)
    public List<Wishlist> getWishlistByUserId(int userId) {
        List<Wishlist> list = new ArrayList<>();
        String sql = "SELECT * FROM wishlists WHERE user_id = ? ORDER BY added_at DESC";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(mapRowToWishlist(rs));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    // 2. Kiểm tra sản phẩm đã được thích chưa (Để hiển thị trái tim đỏ/trắng trên giao diện)
    public boolean isProductInWishlist(int userId, int productId) {
        String sql = "SELECT COUNT(*) FROM wishlists WHERE user_id = ? AND product_id = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, userId);
            ps.setInt(2, productId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0; // Trả về true nếu count > 0
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // 3. Thêm vào Wishlist (CREATE)
    public boolean addToWishlist(int userId, int productId) {
        // Kiểm tra nếu đã thích rồi thì không thêm nữa để tránh lỗi trùng lặp
        if (isProductInWishlist(userId, productId)) {
            return false;
        }

        String sql = "INSERT INTO wishlists (user_id, product_id, added_at) VALUES (?, ?, GETDATE())";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, userId);
            ps.setInt(2, productId);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // 4. Xóa khỏi Wishlist (DELETE) - Bỏ thích
    public boolean removeWishlist(int userId, int productId) {
        String sql = "DELETE FROM wishlists WHERE user_id = ? AND product_id = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, userId);
            ps.setInt(2, productId);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // 5. Đếm số lượng yêu thích của User (Dùng để hiện số trên icon trái tim ở Header)
    public int countWishlistItems(int userId) {
        String sql = "SELECT COUNT(*) FROM wishlists WHERE user_id = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    // Helper: Map ResultSet sang Object (Code gọn hơn)
    private Wishlist mapRowToWishlist(ResultSet rs) throws Exception {
        int wishlistId = rs.getInt("wishlist_id");
        int userId = rs.getInt("user_id");
        int productId = rs.getInt("product_id");

        // Xử lý chuyển đổi thời gian an toàn
        Timestamp ts = rs.getTimestamp("added_at");
        LocalDateTime addedAt = (ts != null) ? ts.toLocalDateTime() : LocalDateTime.now();

        // Lấy thông tin User và Product đầy đủ
        User user = userDAO.getUserById(userId);
        Product product = productDAO.getProductById(productId);

        return new Wishlist(wishlistId, user, product, addedAt);
    }

    // ================= TEST CASE =================
    public static void main(String[] args) {
        WishlistDAO dao = new WishlistDAO();
        int testUserId = 2; // Giả sử User là Customer01

        System.out.println("--- 1.Lấy tất cả DANH SÁCH user HIỆN TẠI ---");
        List<Wishlist> myWishlist = dao.getAllWishlists();
        for (Wishlist w : myWishlist) {
            System.out.println(w);
        }

        System.out.println("--- 1.Lấ  DANH SÁCH HIỆN TẠI của user ---");
        List<Wishlist> b = dao.getWishlistByUserId(testUserId);
        for (Wishlist w : b) {
            System.out.println(w);
        }

//        System.out.println("\n--- 2. THỬ THÊM SẢN PHẨM ID 10 (Galaxy Tab S9) ---");
//        int productToAdd = 11;
//        boolean isAdded = dao.addToWishlist(testUserId, productToAdd);
//        System.out.println("Kết quả thêm: " + isAdded);

        // Kiểm tra lại bằng hàm check
//        System.out.println("Check tồn tại: " + dao.isProductInWishlist(testUserId, productToAdd));
//        System.out.println("\n--- 3. SỐ LƯỢNG YÊU THÍCH ---");
//        System.out.println("Tổng số lượng: " + dao.countWishlistItems(testUserId));
//        System.out.println("\n--- 4. THỬ XÓA SẢN PHẨM ID 10 ---");
//        boolean isRemoved = dao.removeWishlist(testUserId, productToAdd);
//        System.out.println("Kết quả xóa: " + isRemoved);
//
//        System.out.println("Check tồn tại sau xóa: " + dao.isProductInWishlist(testUserId, productToAdd));
    }
}
