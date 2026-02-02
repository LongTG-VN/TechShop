/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import model.OrderItem;
import utils.DBContext;

public class OrderItemDAO extends DBContext {

    // ===== CREATE =====
    public void insertOrderItem(OrderItem item) {
        String sql = """
            INSERT INTO order_items (order_id, inventory_id, selling_price)
            VALUES (?, ?, ?)
        """;
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, item.getOrderId());
            ps.setInt(2, item.getInventoryId());
            ps.setBigDecimal(3, item.getSellingPrice());
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ===== READ BY ORDER =====
    public List<OrderItem> getOrderItemsByOrderId(int orderId) {
        List<OrderItem> list = new ArrayList<>();
        String sql = "SELECT * FROM order_items WHERE order_id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, orderId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new OrderItem(
                        rs.getInt("order_item_id"),
                        rs.getInt("order_id"),
                        rs.getInt("inventory_id"),
                        rs.getBigDecimal("selling_price")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    //==== Test ====
    public static void main(String[] args) {
    OrderItemDAO dao = new OrderItemDAO();

    // ===== CREATE (INSERT) =====
    // tạo dữ liệu ảo để test trước
    //giả sử:
    // order_id = 1 (đã tồn tại)
    // inventory_item_id = 10 (IMEI chưa bán)
    // unit_price = 15000000
    // dao.insertOrderItem(new OrderItem(1, 10, 15000000));

    // ===== GET BY ORDER ID =====
    List<OrderItem> list = dao.getOrderItemsByOrderId(1);
    for (OrderItem item : list) {
        System.out.println(item);
    }

}

}
