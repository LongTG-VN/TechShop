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
import model.CartItem;
import model.ProductVariants; // Giả sử bạn có class này
import model.ShoppingSession; // Giả sử bạn có class này
import utils.DBContext;

/**
 *
 * @author ASUS
 */
public class CartItemDAO extends DBContext {
    
    // Khai báo các DAO phụ thuộc để lấy thông tin chi tiết
    private ShoppingSessionDAO sessionDAO = new ShoppingSessionDAO();
    private ProductVariantDAO productVariantDAO = new ProductVariantDAO();

    // 1. READ: Lấy tất cả item trong kho giỏ hàng (Chỉ dùng cho Admin check data)
    public List<CartItem> getAllCartItems() {
        List<CartItem> list = new ArrayList<>();
        String sql = "SELECT * FROM cart_items";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(mapRowToCartItem(rs));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    // 2. READ: Lấy giỏ hàng của MỘT người dùng cụ thể (Theo SessionID)
    // Đây là hàm quan trọng nhất để hiển thị trang "Giỏ hàng"
    public List<CartItem> getCartItemsBySessionId(int sessionId) {
        List<CartItem> list = new ArrayList<>();
        String sql = "SELECT * FROM cart_items WHERE session_id = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, sessionId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(mapRowToCartItem(rs));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    // 3. CREATE: Thêm vào giỏ hàng (Xử lý logic cộng dồn)
    public boolean addToCart(int sessionId, int variantId, int quantity, double price) {
        // Bước 1: Kiểm tra xem sản phẩm này đã có trong giỏ của khách chưa
        CartItem existingItem = checkItemExist(sessionId, variantId);

        if (existingItem != null) {
            // Trường hợp A: Đã có -> Cộng dồn số lượng
            int newQuantity = existingItem.getQuantity() + quantity;
            return updateItemQuantity(existingItem.getCartItemId(), newQuantity);
        } else {
            // Trường hợp B: Chưa có -> Tạo dòng mới
            String sql = "INSERT INTO [cart_items] ([session_id], [variant_id], [quantity], [price], [added_at]) VALUES (?, ?, ?, ?, GETDATE())";
            try {
                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setInt(1, sessionId);
                ps.setInt(2, variantId);
                ps.setInt(3, quantity);
                ps.setDouble(4, price);
                return ps.executeUpdate() > 0;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    // Hàm phụ: Kiểm tra tồn tại (Trả về CartItem nếu tìm thấy, null nếu không)
    public CartItem checkItemExist(int sessionId, int variantId) {
        String sql = "SELECT * FROM cart_items WHERE session_id = ? AND variant_id = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, sessionId);
            ps.setInt(2, variantId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return mapRowToCartItem(rs);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // 4. UPDATE: Cập nhật số lượng (Khi khách bấm nút +/- trong giỏ)
    public boolean updateItemQuantity(int cartItemId, int newQuantity) {
        // Nếu số lượng <= 0 thì xóa luôn sản phẩm đó
        if (newQuantity <= 0) {
            return deleteCartItem(cartItemId);
        }
        
        String sql = "UPDATE [cart_items] SET [quantity] = ? WHERE [cart_item_id] = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, newQuantity);
            ps.setInt(2, cartItemId);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // 5. DELETE: Xóa 1 sản phẩm khỏi giỏ
    public boolean deleteCartItem(int cartItemId) {
        String sql = "DELETE FROM [cart_items] WHERE [cart_item_id] = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, cartItemId);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // 6. DELETE ALL: Xóa sạch giỏ hàng (Dùng sau khi Thanh Toán thành công)
    public boolean clearCart(int sessionId) {
        String sql = "DELETE FROM [cart_items] WHERE [session_id] = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, sessionId);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    
    // 7. Utility: Tính tổng tiền tạm tính của giỏ hàng
    public double getTotalPrice(int sessionId) {
        double total = 0;
        String sql = "SELECT SUM(price * quantity) as total FROM cart_items WHERE session_id = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, sessionId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                total = rs.getDouble("total");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return total;
    }

    // Helper: Map ResultSet sang Object để code gọn hơn
    private CartItem mapRowToCartItem(ResultSet rs) throws Exception {
        int cart_item_id = rs.getInt("cart_item_id");
        int sessionId = rs.getInt("session_id");
        int variantId = rs.getInt("variant_id");
        int quantity = rs.getInt("quantity");
        double price = rs.getDouble("price");
        Timestamp ts = rs.getTimestamp("added_at");
        LocalDateTime added_at = (ts != null) ? ts.toLocalDateTime() : LocalDateTime.now();

        // Lấy object phụ thuộc
        ShoppingSession session = sessionDAO.getSessionById(sessionId);
        ProductVariants variant = productVariantDAO.getVariantById(variantId);
        
        return new CartItem(cart_item_id, session, variant, quantity, price, added_at);
    }
    
    // ================= MAIN TEST CASE =================
    public static void main(String[] args) {
        CartItemDAO dao = new CartItemDAO();
        int testSessionId = 2; // Giả sử đang test với Session ID 2 (Khách hàng)

        System.out.println("--- TEST 1: XEM GIỎ HÀNG ---");
        List<CartItem> myCart = dao.getCartItemsBySessionId(testSessionId);
        for (CartItem item : myCart) {
            System.out.println(item.getCartItemId() + " - " + item.getProductVarant().getColor() + " - SL: " + item.getQuantity());
        }

        System.out.println("\n--- TEST 2: THÊM SẢN PHẨM (Test Cộng Dồn) ---");
        // Giả sử thêm VariantID = 1, Số lượng 1, Giá 32tr
        // Nếu trong DB đã có VariantID 1, số lượng sẽ tăng lên
        // :D ko bik sao add cai trung id la tu dong tang len :D;
//        boolean added = dao.addToCart(testSessionId, 1, 1, 32990000); 
//        System.out.println("Added status: " + added);
        
//        System.out.println("Check lại số lượng sau khi thêm:");
//        CartItem checkItem = dao.checkItemExist(testSessionId, 1);
//        if(checkItem != null) System.out.println("New Quantity: " + checkItem.getQuantity());

//        System.out.println("\n--- TEST 3: CẬP NHẬT SỐ LƯỢNG (Thành 10) ---");
//        if(checkItem != null) {
//            dao.updateItemQuantity(checkItem.getCartItemId(), 10);
//            System.out.println("Updated quantity to 10.");
//        }
//        
//        System.out.println("\n--- TEST 4: TỔNG TIỀN ---");
//        System.out.println("Total: " + dao.getTotalPrice(testSessionId));

//         System.out.println("\n--- TEST 5: XÓA ITEM ---");
//         if(checkItem != null) dao.deleteCartItem(checkItem.getCartItemId());
        
//         System.out.println("\n--- TEST 6: XÓA SẠCH GIỎ ---");
//         dao.clearCart(testSessionId);
    }
}