/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.sql.Array;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import model.Order;
import model.OrderItem;
import model.PaymentMethod;
import model.user;
import model.Voucher;
import utils.DBContext;

/**
 *
 * @author ASUS
 */
public class OrderDAO extends DBContext {

    private userDAO userDAO = new userDAO();
    private VoucherDAO voucherDAO = new VoucherDAO();
    private PaymentMethodDAO paymentMethodDAO = new PaymentMethodDAO();

    // 1. READ: Lấy tất cả đơn hàng (Admin Dashboard)
    public List<Order> getAllOrder() {
        List<Order> list = new ArrayList<>();
        String sql = "SELECT * FROM orders ORDER BY created_at DESC"; // Nên sắp xếp mới nhất lên đầu
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(mapRowToOrder(rs));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    // 2. READ: Lấy đơn hàng theo ID (Xem chi tiết đơn hàng)
    public Order getOrderById(int orderId) {
        String sql = "SELECT * FROM orders WHERE order_id = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, orderId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return mapRowToOrder(rs);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // 3. READ: Lấy lịch sử đơn hàng của 1 User (Quan trọng cho khách hàng)
    public List<Order> getOrdersByUserId(int userId) {
        List<Order> list = new ArrayList<>();
        String sql = "SELECT * FROM orders WHERE user_id = ? ORDER BY created_at DESC";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(mapRowToOrder(rs));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    // 4. CREATE: Tạo đơn hàng mới (TRANSACTION - Quan trọng nhất)
    // Cần insert vào bảng 'orders' VÀ bảng 'order_items' cùng lúc.
    
    public int createOrder(Order order, List<OrderItem> items) {
        String sqlOrder = "INSERT INTO [orders] (user_id, voucher_id, payment_method_id, total_amount, shipping_address, status, is_paid, created_at) VALUES (?, ?, ?, ?, ?, ?, ?, GETDATE())";
        String sqlItem = "INSERT INTO [order_items] (order_id, variant_id, quantity, price) VALUES (?, ?, ?, ?)";
        
        try {
            // Tắt auto commit để quản lý giao dịch
            conn.setAutoCommit(false);

            // BƯỚC 1: Insert bảng Order
            PreparedStatement psOrder = conn.prepareStatement(sqlOrder, Statement.RETURN_GENERATED_KEYS);
            psOrder.setInt(1, order.getUser().getUserId());
            
            // Xử lý Voucher (có thể null)
            if (order.getVoucher() != null) {
                psOrder.setInt(2, order.getVoucher().getVoucherId());
            } else {
                psOrder.setNull(2, java.sql.Types.INTEGER);
            }
            
            psOrder.setInt(3, order.getPaymentMethod().getMethod_id());
            psOrder.setDouble(4, order.getTotalAmount());
            psOrder.setString(5, order.getShippingAddress());
            psOrder.setString(6, "PENDING"); // Mặc định là PENDING
            psOrder.setByte(7, (byte) 0); // Mặc định chưa thanh toán (0)
            
            psOrder.executeUpdate();

            // Lấy ID đơn hàng vừa tạo
            ResultSet rs = psOrder.getGeneratedKeys();
            int newOrderId = -1;
            if (rs.next()) {
                newOrderId = rs.getInt(1);
            }

            // BƯỚC 2: Insert bảng OrderItems
            PreparedStatement psItem = conn.prepareStatement(sqlItem);
            for (OrderItem item : items) {
                psItem.setInt(1, newOrderId);
                psItem.setInt(2, item.getProductVariants().getVariantId());
                psItem.setInt(3, item.getQuantity());
                psItem.setDouble(4, item.getPrice()); // Giá tại thời điểm mua
                psItem.addBatch(); // Gom lại chạy 1 lần
            }
            psItem.executeBatch();

            // Nếu mọi thứ ok -> Lưu lại
            conn.commit();
            return newOrderId;

        } catch (Exception e) {
            try {
                conn.rollback(); // Có lỗi -> Hoàn tác tất cả
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
        } finally {
            try {
                conn.setAutoCommit(true); // Bật lại chế độ mặc định
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return -1; // Trả về -1 nếu thất bại
    }

    // 5. UPDATE: Cập nhật trạng thái đơn hàng (Admin/Shipper)
    // Các trạng thái: PENDING -> CONFIRMED -> SHIPPING -> COMPLETED / CANCELLED
    public boolean updateOrderStatus(int orderId, String status) {
        String sql = "UPDATE orders SET status = ? WHERE order_id = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, status);
            ps.setInt(2, orderId);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // 6. UPDATE: Cập nhật trạng thái thanh toán (is_paid)
    public boolean updatePaymentStatus(int orderId, boolean isPaid) {
        String sql = "UPDATE orders SET is_paid = ? WHERE order_id = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setByte(1, (byte) (isPaid ? 1 : 0)); // Chuyển boolean sang byte
            ps.setInt(2, orderId);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // Helper: Map ResultSet sang Object (Code gọn hơn và dùng chung được)
    private Order mapRowToOrder(ResultSet rs) throws SQLException {
        int orderId = rs.getInt("order_id");
        int userId = rs.getInt("user_id");
        int voucherId = rs.getInt("voucher_id");
        int paymentMethodId = rs.getInt("payment_method_id");
        double totalAmount = rs.getDouble("total_amount"); // Sửa getInt thành getDouble cho tiền
        String shippingAddress = rs.getString("shipping_address");
        String status = rs.getString("status");
        byte is_paid = rs.getByte("is_paid");
        
        Timestamp ts = rs.getTimestamp("created_at");
        LocalDateTime created_at = (ts != null) ? ts.toLocalDateTime() : LocalDateTime.now();

        // Lấy object con
        user user = userDAO.getUserById(userId);
        Voucher voucher = (voucherId == 0) ? null : voucherDAO.getVoucherById(voucherId);
        PaymentMethod payment = paymentMethodDAO.getPaymentMethodById(paymentMethodId);

        return new Order(orderId, user, voucher, payment, totalAmount, shippingAddress, status, is_paid, created_at);
    }
    
    // ================= MAIN TEST CASE =================
public static void main(String[] args) {
    // 1. Khởi tạo các DAO
    OrderDAO orderDAO = new OrderDAO();
    ProductVariantDAO productDAO = new ProductVariantDAO();
    userDAO uDao = new userDAO();
    OrderItemDAO orderItemDAO = new OrderItemDAO();
    PaymentMethodDAO pDao = new PaymentMethodDAO();

    System.out.println("========= BẮT ĐẦU TEST CRUD ORDER =========");

    // ==========================================
    // [C] CREATE: TẠO ĐƠN HÀNG MỚI
    // ==========================================
    System.out.println("\n--- 1. [CREATE] TAO DON HANG MOI ---");

    int newOrderId = -1; // Biến để lưu ID đơn hàng sau khi tạo

    try {
        // A. Chuẩn bị dữ liệu mẫu
        user customer = uDao.getUserById(2); // Giả sử User ID 2 tồn tại
        PaymentMethod payment = pDao.getPaymentMethodById(1); // Giả sử Payment ID 1 tồn tại (COD)
        model.ProductVariants product1 = productDAO.getVariantById(1); // iPhone
        model.ProductVariants product2 = productDAO.getVariantById(3); // Samsung

        // B. Tạo đối tượng Order (Chưa có ID)
        Order newOrder = new Order();
        newOrder.setUser(customer);
        newOrder.setPaymentMethod(payment);
        newOrder.setShippingAddress("123 Duong Test, Can Tho"); // Hardcode địa chỉ test
        newOrder.setStatus("PENDING");
        newOrder.setIsPaid((byte) 0); // 0: Chưa thanh toán

        // C. Tạo danh sách OrderItem (Chi tiết đơn hàng)
        List<OrderItem> listItems = new ArrayList<>();

        // Item 1: Mua 1 cái iPhone
        OrderItem item1 = new OrderItem();
        item1.setProductVariants(product1);
        item1.setQuantity(1);
        item1.setPrice(product1.getPrice());
        listItems.add(item1);

        // Item 2: Mua 2 cái Samsung
        OrderItem item2 = new OrderItem();
        item2.setProductVariants(product2);
        item2.setQuantity(2);
        item2.setPrice(product2.getPrice());
        listItems.add(item2);

        // Tính tổng tiền (để set vào Order)
        double totalAmount = (item1.getQuantity() * item1.getPrice()) 
                           + (item2.getQuantity() * item2.getPrice());
        newOrder.setTotalAmount(totalAmount);

        // D. Gọi DAO để lưu vào DB
        // Giả sử hàm createOrder trả về ID của đơn hàng mới tạo (int)
        newOrderId = orderDAO.createOrder(newOrder, listItems);

        if (newOrderId > 0) {
            System.out.println("✅ Tạo đơn hàng thành công! ID mới là: " + newOrderId);
        } else {
            System.out.println("❌ Tạo đơn hàng thất bại.");
            return; // Dừng chương trình nếu tạo thất bại
        }

    } catch (Exception e) {
        System.out.println("❌ Lỗi khi tạo dữ liệu test: " + e.getMessage());
        e.printStackTrace();
        return;
    }

    // ==========================================
    // [R] READ: GET BY ID (Xem chi tiết đơn vừa tạo)
    // ==========================================
    System.out.println("\n--- 2. [READ] GET BY ID (" + newOrderId + ") ---");
    Order createdOrder = orderDAO.getOrderById(newOrderId);
    if (createdOrder != null) {
        System.out.println("Thông tin đơn hàng:");
        System.out.println("- Khách hàng: " + createdOrder.getUser().getFullName());
        System.out.println("- Tổng tiền: " + String.format("%,.0f VNĐ", createdOrder.getTotalAmount()));
        System.out.println("- Trạng thái: " + createdOrder.getStatus());
        System.out.println("- Thanh toán: " + (createdOrder.getIsPaid() == 1 ? "Rồi" : "Chưa"));
        
        // In ra danh sách món hàng (Nếu DAO getOrderById đã join lấy luôn items)
        // System.out.println("- Số lượng mặt hàng: " + createdOrder.getItems().size());
    } else {
        System.out.println("❌ Không tìm thấy đơn hàng ID " + newOrderId);
    }

    // ==========================================
    // [U] UPDATE: CẬP NHẬT TRẠNG THÁI & THANH TOÁN
    // ==========================================
    System.out.println("\n--- 3. [UPDATE] CAP NHAT DON HANG (" + newOrderId + ") ---");

    // Giả sử Admin duyệt đơn -> CONFIRMED
    boolean statusUpdate = orderDAO.updateOrderStatus(newOrderId, "CONFIRMED");
    System.out.println("-> Cập nhật trạng thái CONFIRMED: " + statusUpdate);

    // Giả sử Khách đã trả tiền -> isPaid = true
    boolean paymentUpdate = orderDAO.updatePaymentStatus(newOrderId, true);
    System.out.println("-> Cập nhật thanh toán ĐÃ TRẢ: " + paymentUpdate);

    // [R] Kiểm tra lại xem đã thay đổi chưa
    Order updatedOrder = orderDAO.getOrderById(newOrderId);
    if (updatedOrder != null) {
        System.out.println(">> Check lại DB: Status=" + updatedOrder.getStatus() + ", Paid=" + updatedOrder.getIsPaid());
    }

    // ==========================================
    // [D] DELETE / CANCEL
    // ==========================================
    System.out.println("\n--- 4. [DELETE/CANCEL] XU LY DON HANG (" + newOrderId + ") ---");
    
    // Cách 1: Soft Delete (Hủy đơn) - Dữ liệu VẪN CÒN trong DB
    boolean isCancelled = orderDAO.updateOrderStatus(newOrderId, "CANCELLED");

    if (isCancelled) {
        System.out.println("✅ Đã hủy đơn hàng thành công (Soft Delete).");

        // Verify: Lấy lại xem trạng thái đã đổi chưa
        Order cancelledOrder = orderDAO.getOrderById(newOrderId);
        if (cancelledOrder != null && "CANCELLED".equals(cancelledOrder.getStatus())) {
            System.out.println(">> Kiểm chứng: Đơn hàng vẫn còn, trạng thái là CANCELLED (Đúng logic thực tế).");
        } else {
            System.out.println(">> Kiểm chứng: Lỗi cập nhật trạng thái.");
        }
    } else {
        System.out.println("❌ Hủy đơn thất bại.");
    }
    
    /* // Cách 2: Hard Delete (Xóa hẳn khỏi DB) - Nếu bạn muốn test xóa thật
    // boolean isDeleted = orderDAO.deleteOrder(newOrderId); // Cần viết hàm này trong DAO
    // if (isDeleted) {
    //      Order deletedOrder = orderDAO.getOrderById(newOrderId);
    //      if (deletedOrder == null) System.out.println(">> Đã xóa bay màu khỏi DB.");
    // }
    */

    // ==========================================
    // CÁC CHỨC NĂNG READ KHÁC
    // ==========================================
    System.out.println("\n--- 5. LIST ORDERS BY USER ID (2) ---");
    List<Order> history = orderDAO.getOrdersByUserId(2);
    System.out.println("Tìm thấy " + history.size() + " đơn hàng của User 2.");

    System.out.println("\n--- 6. LIST ALL ORDERS ---");
    List<Order> all = orderDAO.getAllOrder();
    System.out.println("Tổng cộng trong DB có: " + all.size() + " đơn hàng.");
}
}