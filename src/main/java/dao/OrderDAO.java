/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

    public List<Order> getAllOrdersWithFullInfo() {
        List<Order> list = new ArrayList<>();
        String sql = "SELECT o.*, c.full_name, "
                + "(SELECT TOP 1 p.name FROM order_items oi "
                + " JOIN inventory_items ii ON oi.inventory_id = ii.inventory_id "
                + " JOIN product_variants pv ON ii.variant_id = pv.variant_id "
                + " JOIN products p ON pv.product_id = p.product_id "
                + " WHERE oi.order_id = o.order_id) as representative_product, "
                + "(SELECT COUNT(*) FROM order_items WHERE order_id = o.order_id) as total_items "
                + "FROM orders o "
                + "JOIN customers c ON o.customer_id = c.customer_id "
                + "WHERE EXISTS (SELECT 1 FROM order_items WHERE order_id = o.order_id)"
                + "ORDER BY o.created_at DESC";
        try (PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Order o = new Order(
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
                o.setCustomerName(rs.getString("full_name"));

                String pName = rs.getString("representative_product");
                int count = rs.getInt("total_items");

                String finalName = (pName != null) ? pName : "No products";
                if (count > 1) {
                    finalName += " (+" + (count - 1) + " items)";
                }

                o.setOrderName(finalName);
                list.add(o);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    // ===== READ BY ID =====
    public Order getOrderById(int id) {
        String sql = "SELECT o.*, c.full_name, c.email, c.phone_number "
                + "FROM orders o "
                + "JOIN customers c ON o.customer_id = c.customer_id "
                + "WHERE o.order_id = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Order order = new Order();
                order.setOrderId(rs.getInt("order_id"));
                order.setShippingAddress(rs.getString("shipping_address"));
                order.setTotalAmount(rs.getBigDecimal("total_amount"));
                order.setStatus(rs.getString("status"));
                order.setCreatedAt(rs.getTimestamp("created_at"));
                order.setPaymentStatus(rs.getString("payment_status"));

                order.setCustomerName(rs.getNString("full_name"));
                order.setEmail(rs.getString("email"));
                order.setPhone(rs.getString("phone_number"));

                return order;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    //Get order detail with IMEI
    public List<Map<String, Object>> getOrderDetailsWithIMEI(int orderId) {
        List<Map<String, Object>> details = new ArrayList<>();
        String sql = "SELECT p.name, pv.sku, ii.imei, oi.selling_price "
                + "FROM order_items oi "
                + "JOIN inventory_items ii ON oi.inventory_id = ii.inventory_id "
                + "JOIN product_variants pv ON ii.variant_id = pv.variant_id "
                + "JOIN products p ON pv.product_id = p.product_id "
                + "WHERE oi.order_id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, orderId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Map<String, Object> item = new HashMap<>();
                item.put("productName", rs.getString("name"));
                item.put("sku", rs.getString("sku"));
                item.put("imei", rs.getString("imei"));
                item.put("price", rs.getBigDecimal("selling_price"));
                details.add(item);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return details;
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
