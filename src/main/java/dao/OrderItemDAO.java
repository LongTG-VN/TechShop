/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import model.Order;
import model.OrderItem;
import model.ProductVariants;
import utils.DBContext;

/**
 *
 * @author ASUS
 */
public class OrderItemDAO extends DBContext {

    private ProductVariantDAO productVariantDAO = new ProductVariantDAO();

    // ================== PHẦN 1: CÁC CHỨC NĂNG CƠ BẢN (BASIC CRUD) ==================

    // 1. Lấy tất cả (Dùng cho Admin thống kê)
    public List<OrderItem> getAllOrderItem() {
        List<OrderItem> list = new ArrayList<>();
        String sql = "SELECT * FROM order_items";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(mapRowToOrderItem(rs));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    // 2. Lấy chi tiết của một Đơn hàng (QUAN TRỌNG: Để hiển thị hóa đơn)
    public List<OrderItem> getItemsByOrderId(int orderId) {
        List<OrderItem> list = new ArrayList<>();
        String sql = "SELECT * FROM order_items WHERE order_id = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, orderId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(mapRowToOrderItem(rs));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    // 3. Thêm 1 Item (Dùng lẻ)
    public boolean insertOrderItem(OrderItem item) {
        String sql = "INSERT INTO order_items (order_id, variant_id, quantity, price) VALUES (?, ?, ?, ?)";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            if (item.getOrder() != null) {
                ps.setInt(1, item.getOrder().getOrderId());
            } else {
                return false; 
            }
            ps.setInt(2, item.getProductVariants().getVariantId());
            ps.setInt(3, item.getQuantity());
            ps.setDouble(4, item.getPrice());
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // 4. Xóa Item (Dùng khi Admin sửa đơn hàng cho khách)
    public boolean deleteOrderItem(int itemId) {
        String sql = "DELETE FROM order_items WHERE order_item_id = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, itemId);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // ================== PHẦN 2: CÁC CHỨC NĂNG NÂNG CAO (ADVANCED) ==================

    // 5. THỐNG KÊ: Top N Sản phẩm bán chạy nhất (Best Sellers)
    // Trả về Map<Variant, Tổng số lượng bán>
    public Map<ProductVariants, Integer> getBestSellingVariants(int top) {
        Map<ProductVariants, Integer> map = new HashMap<>();
        // Query: Tính tổng quantity group by variant_id, sắp xếp giảm dần
        String sql = "SELECT TOP (?) variant_id, SUM(quantity) as total_sold "
                   + "FROM order_items "
                   + "GROUP BY variant_id "
                   + "ORDER BY total_sold DESC";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, top);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int variantId = rs.getInt("variant_id");
                int totalSold = rs.getInt("total_sold");
                
                // Lấy thông tin chi tiết sản phẩm
                ProductVariants variant = productVariantDAO.getVariantById(variantId);
                map.put(variant, totalSold);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

    // 6. VALIDATION: Kiểm tra User đã mua sản phẩm này chưa (Để cho phép Review)
    // Điều kiện: Phải có trong order_items VÀ đơn hàng đó phải trạng thái 'COMPLETED'
    public boolean hasUserBoughtProduct(int userId, int productId) {
        String sql = "SELECT COUNT(*) FROM order_items oi "
                   + "JOIN orders o ON oi.order_id = o.order_id "
                   + "JOIN product_variants pv ON oi.variant_id = pv.variant_id "
                   + "WHERE o.user_id = ? "      // Của user này
                   + "AND pv.product_id = ? "    // Mua sản phẩm này
                   + "AND o.status = 'COMPLETED'"; // Đơn đã thành công
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, userId);
            ps.setInt(2, productId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // 7. BATCH INSERT: Thêm hàng loạt (Tối ưu hiệu năng khi đơn hàng có nhiều món)
    // Thay vì gọi insert 10 lần, ta gọi hàm này 1 lần.
    public void insertBatch(int orderId, List<OrderItem> items) {
        String sql = "INSERT INTO order_items (order_id, variant_id, quantity, price) VALUES (?, ?, ?, ?)";
        try {
            conn.setAutoCommit(false); // Bắt đầu batch
            PreparedStatement ps = conn.prepareStatement(sql);
            
            for (OrderItem item : items) {
                ps.setInt(1, orderId);
                ps.setInt(2, item.getProductVariants().getVariantId());
                ps.setInt(3, item.getQuantity());
                ps.setDouble(4, item.getPrice());
                ps.addBatch(); // Thêm vào hàng đợi
            }
            
            ps.executeBatch(); // Thực thi 1 lần
            conn.commit();
            conn.setAutoCommit(true);
        } catch (Exception e) {
            e.printStackTrace();
            try { conn.rollback(); } catch (SQLException ex) {}
        }
    }

    // Helper: Map ResultSet
    private OrderItem mapRowToOrderItem(ResultSet rs) throws SQLException {
        int orderItemId = rs.getInt("order_item_id");
        int orderId = rs.getInt("order_id");
        int variantId = rs.getInt("variant_id");
        int quantity = rs.getInt("quantity");
        double price = rs.getDouble("price");

        Order simpleOrder = new Order();
        simpleOrder.setOrderId(orderId);
        ProductVariants variant = productVariantDAO.getVariantById(variantId);

        return new OrderItem(orderItemId, simpleOrder, variant, quantity, price);
    }
    
    // 5. UPDATE: Cập nhật số lượng hoặc giá của 1 dòng sản phẩm trong đơn
    public boolean updateOrderItem(OrderItem item) {
        String sql = "UPDATE order_items SET quantity = ?, price = ? WHERE order_item_id = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, item.getQuantity());
            ps.setDouble(2, item.getPrice());
            ps.setInt(3, item.getOrderItemId());
            
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // ================= MAIN TEST CASE =================
    public static void main(String[] args) {
        OrderItemDAO dao = new OrderItemDAO();

        System.out.println("--- 1. LIST BY ORDER ID (ID = 1) ---");
        List<OrderItem> items = dao.getItemsByOrderId(1);
        for (OrderItem i : items) {
            System.out.println(i.getProductVariants().getProduct().getProductName() + " - SL: " + i.getQuantity());
        }
        
        
        // Cần 2 DAO này để lấy dữ liệu thật từ DB
        OrderDAO orderDAO = new OrderDAO();
        ProductVariantDAO variantDAO = new ProductVariantDAO();

//        System.out.println("--- TEST INSERT ORDER ITEM ---");

        // BƯỚC 1: Lấy dữ liệu thật (Giả sử thêm vào Order ID 1, Sản phẩm ID 3)
        // (Bạn phải chắc chắn Order ID 1 và Variant ID 3 đang có trong DB nhé)
        int targetOrderId = 1;
        int targetVariantId = 3; // Ví dụ: Samsung S23

        model.Order order = orderDAO.getOrderById(targetOrderId);
        model.ProductVariants variant = variantDAO.getVariantById(targetVariantId);
//  OrderItem newItem = new OrderItem(0, order, variant, 2, 20000000);
//              boolean isInserted = dao.insertOrderItem(newItem);
//                          System.out.println("Kết quả Insert: " + isInserted);


        

//         dao.deleteOrderItem(4);
        
        dao.updateOrderItem(new OrderItem(5, order, variant, 10, 2000200));
              
    }
}