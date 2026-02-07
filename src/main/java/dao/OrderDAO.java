/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import model.Order;
import utils.DBContext;

/**
 *
 * @author WIN11
 */
public class OrderDAO extends DBContext {

    // ===== CREATE =====
    public void insertOrder(Order o) {
        String sql = """
            INSERT INTO orders
            (customer_id, voucher_id, payment_method_id, shipping_address, total_amount)
            VALUES (?, ?, ?, ?, ?)
        """;
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, o.getCustomerId());

            if (o.getVoucherId() == null) {
                ps.setNull(2, Types.INTEGER);
            } else {
                ps.setInt(2, o.getVoucherId());
            }

            ps.setInt(3, o.getPaymentMethodId());
            ps.setString(4, o.getShippingAddress());
            ps.setBigDecimal(5, o.getTotalAmount());

            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ===== READ ALL =====
    public List<Order> getAllOrders() {
        List<Order> list = new ArrayList<>();
        String sql = "SELECT * FROM orders ORDER BY created_at DESC";
        try (PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(new Order(
                        rs.getInt("order_id"),
                        rs.getInt("customer_id"),
                        (Integer) rs.getObject("voucher_id"),
                        rs.getInt("payment_method_id"),
                        rs.getString("shipping_address"),
                        rs.getBigDecimal("total_amount"),
                        rs.getString("payment_status"),
                        rs.getString("status"),
                        rs.getTimestamp("created_at")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    // ===== READ BY ID =====
    public Order getOrderById(int orderId) {
        String sql = "SELECT * FROM orders WHERE order_id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, orderId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Order(
                        rs.getInt("order_id"),
                        rs.getInt("customer_id"),
                        (Integer) rs.getObject("voucher_id"),
                        rs.getInt("payment_method_id"),
                        rs.getString("shipping_address"),
                        rs.getBigDecimal("total_amount"),
                        rs.getString("payment_status"),
                        rs.getString("status"),
                        rs.getTimestamp("created_at")
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // ===== Update Order =====
    public void updateOrderCurrentStatus(int orderId, String status) {
        String sql = "UPDATE orders SET status = ? WHERE order_id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, status);
            ps.setInt(2, orderId);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //==== Update order's payment status ====

    public void updateOrderPaymentStatus(int orderId, String paymentStatus) {
        String sql = "UPDATE orders SET payment_status = ? WHERE order_id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, paymentStatus);
            ps.setInt(2, orderId);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //Test
    public static void main(String[] args) {
        // OrderDAO dao = new OrderDAO();

        // ===== 1. INSERT ORDER =====
//        Order newOrder = new Order(
//                1, // customer_id (PHẢI TỒN TẠI)
//                null, // voucher_id (có thể null)
//                1, // payment_method_id (PHẢI TỒN TẠI)
//                "123 Nguyễn Văn A, Q1, HCM",
//                new BigDecimal("15000000")
//        );
//        dao.insertOrder(newOrder);
//        System.out.println(">>> Insert order OK");
        // ===== 2. GET ALL ORDERS =====
//        System.out.println(">>> Get all orders:");
//        List<Order> allOrders = dao.getAllOrders();
//        for (Order o : allOrders) {
//            System.out.println(o);
//        }
        // ===== 3. GET ORDER BY ID =====
//        System.out.println(">>> Get order by ID = 1");
//        Order order = dao.getOrderById(1);
//        System.out.println(order);
        // ===== 4. UPDATE ORDER STATUS =====
//        dao.updateOrderCurrentStatus(1, "APPROVED");
//        System.out.println(">>> Update order current status OK");
        // ===== 5. UPDATE PAYMENT STATUS =====
//        dao.updateOrderPaymentStatus(1, "PAID");
//        System.out.println(">>> Update payment status OK");
    }

}
