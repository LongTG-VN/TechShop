/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp; // Cần thêm cái này để xử lý ngày giờ SQL
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import model.Order; // Import model Order
import model.OrderStatusHistory;
import utils.DBContext;

/**
 *
 * @author ASUS
 */
public class OrderStatusHistoryDAO extends DBContext {
    
    private OrderDAO orderDAO = new OrderDAO();
    
    // ================================================================
    // 1. [READ] LẤY TẤT CẢ LỊCH SỬ (Dùng cho Admin Dashboard)
    // ================================================================
    public List<OrderStatusHistory> getAllOrderStatusHistory() {
        List<OrderStatusHistory> list = new ArrayList<>();
        String sql = "SELECT * FROM order_status_history ORDER BY updated_at DESC"; // Nên sắp xếp mới nhất lên đầu
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int OrderHistoryId = rs.getInt("history_id");
                int orderId = rs.getInt("order_id");
                String status = rs.getString("status");
                String note = rs.getString("note");
                
                // Xử lý timestamp an toàn
                Timestamp ts = rs.getTimestamp("updated_at");
                LocalDateTime updated_at = (ts != null) ? ts.toLocalDateTime() : null;

                // Lưu ý: Gọi orderDAO.getOrderById ở đây có thể gây chậm nếu list quá dài (N+1 query)
                Order order = orderDAO.getOrderById(orderId);
                
                list.add(new OrderStatusHistory(OrderHistoryId, order, status, note, updated_at));
            }
        } catch (Exception e) {
            System.out.println("Error get all history: " + e.getMessage());
        }
        return list;
    }

    // ================================================================
    // 2. [READ] LẤY LỊCH SỬ CỦA 1 ĐƠN HÀNG (Quan trọng: Để hiển thị Tracking)
    // ================================================================
    public List<OrderStatusHistory> getHistoryByOrderId(int orderId) {
        List<OrderStatusHistory> list = new ArrayList<>();
        // Sắp xếp ASC để xem quy trình từ lúc đặt -> giao hàng
        String sql = "SELECT * FROM order_status_history WHERE order_id = ? ORDER BY updated_at ASC"; 
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, orderId);
            ResultSet rs = ps.executeQuery();
            
            // Lấy order 1 lần để tái sử dụng, tránh query DB nhiều lần trong vòng lặp
            Order order = orderDAO.getOrderById(orderId);
            
            while (rs.next()) {
                int OrderHistoryId = rs.getInt("history_id");
                String status = rs.getString("status");
                String note = rs.getString("note");
                Timestamp ts = rs.getTimestamp("updated_at");
                LocalDateTime updated_at = (ts != null) ? ts.toLocalDateTime() : null;

                list.add(new OrderStatusHistory(OrderHistoryId, order, status, note, updated_at));
            }
        } catch (Exception e) {
            System.out.println("Error get history by order ID: " + e.getMessage());
        }
        return list;
    }

    // ================================================================
    // 3. [CREATE] THÊM MỘT DÒNG LỊCH SỬ MỚI
    // (Hàm này thường được gọi ngay sau khi update trạng thái bên OrderDAO)
    // ================================================================
    public boolean addOrderStatusHistory(int orderId, String status, String note) {
        String sql = "INSERT INTO order_status_history (order_id, status, note, updated_at) VALUES (?, ?, ?, ?)";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, orderId);
            ps.setString(2, status);
            ps.setString(3, note);
            ps.setTimestamp(4, Timestamp.valueOf(LocalDateTime.now())); // Lấy thời gian hiện tại

            int rows = ps.executeUpdate();
            return rows > 0;
        } catch (Exception e) {
            System.out.println("Error adding history: " + e.getMessage());
            return false;
        }
    }

    // ================================================================
    // 4. [DELETE] XÓA LỊCH SỬ (Dùng dọn dẹp, ít dùng thực tế)
    // ================================================================
    public boolean deleteHistory(int historyId) {
        String sql = "DELETE FROM order_status_history WHERE history_id = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, historyId);
            int rows = ps.executeUpdate();
            return rows > 0;
        } catch (Exception e) {
            System.out.println("Error deleting history: " + e.getMessage());
            return false;
        }
    }
    
    // ================================================================
    // MAIN TEST
    // ================================================================
    public static void main(String[] args) {
        OrderStatusHistoryDAO historyDAO = new OrderStatusHistoryDAO();
        
//        System.out.println("========= TEST 1: ADD NEW HISTORY =========");
//        // Giả sử có Order ID = 1 đang tồn tại
//        int testOrderId = 1; 
//        boolean isAdded = historyDAO.addOrderStatusHistory(testOrderId, "SHIPPING", "Đơn hàng đang được giao cho shipper");
//        System.out.println("Thêm lịch sử mới: " + (isAdded ? "Thành công" : "Thất bại"));
//
//        System.out.println("\n========= TEST 2: GET HISTORY BY ORDER ID =========");
//        List<OrderStatusHistory> histories = historyDAO.getHistoryByOrderId(testOrderId);
//        if (histories.isEmpty()) {
//            System.out.println("Không có lịch sử nào cho Order ID " + testOrderId);
//        } else {
//            for (OrderStatusHistory h : histories) {
//                System.out.println(h.getUpdated_at()+ " | " + h.getStatus() + " | Note: " + h.getNote());
//            }
//        }
//        
//        System.out.println("\n========= TEST 3: GET ALL HISTORY =========");
//        List<OrderStatusHistory> all = historyDAO.getAllOrderStatusHistory();
//        System.out.println("Tổng số dòng lịch sử trong hệ thống: " + all.size());
        
        
//       historyDAO.deleteHistory(9);
    }
}