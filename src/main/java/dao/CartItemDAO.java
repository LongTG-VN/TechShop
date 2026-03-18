/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.CartItem;
import model.CartItemDisplay;
import utils.DBContext;

/**
 *
 * @author LE HOANG NHAN
 */
public class CartItemDAO extends DBContext {

    private CartItem mapCartItem(ResultSet rs) throws SQLException {
        return new CartItem(
                rs.getInt("cart_item_id"),
                rs.getInt("customer_id"),
                rs.getInt("variant_id"),
                rs.getInt("quantity"),
                rs.getTimestamp("created_at")
        );
    }

    // 1) Lấy toàn bộ cart_items (ít dùng, chủ yếu debug/admin)
    public List<CartItem> getAllCartItems() {
        List<CartItem> list = new ArrayList<>();
        if (conn == null) {
            return list;
        }
        String sql = "SELECT cart_item_id, customer_id, variant_id, quantity, created_at FROM cart_items";
        try (PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(mapCartItem(rs));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    // 2) Lấy giỏ hàng (raw) của 1 khách
    public List<CartItem> getCartByCustomerId(int customerId) {
        List<CartItem> list = new ArrayList<>();
        if (conn == null) {
            return list;
        }
        String sql = "SELECT cart_item_id, customer_id, variant_id, quantity, created_at FROM cart_items WHERE customer_id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, customerId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(mapCartItem(rs));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    // 3) Lấy 1 dòng cart_item theo id
    public CartItem getCartItemById(int id) {
        if (conn == null) {
            return null;
        }
        String sql = "SELECT cart_item_id, customer_id, variant_id, quantity, created_at FROM cart_items WHERE cart_item_id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapCartItem(rs);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // 4) Thêm item vào giỏ
    public boolean insertCartItem(CartItem item) {
        if (conn == null || item == null) {
            return false;
        }
        String sql = "INSERT INTO cart_items (customer_id, variant_id, quantity) VALUES (?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, item.getCustomer_id());
            ps.setInt(2, item.getVariant_id());
            ps.setInt(3, item.getQuantity());
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // 5) Update số lượng theo cart_item_id
    public boolean updateCartItem(CartItem item) {
        if (conn == null || item == null) {
            return false;
        }
        String sql = "UPDATE cart_items SET quantity = ? WHERE cart_item_id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, item.getQuantity());
            ps.setInt(2, item.getCart_item_id());
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // 6) Xóa 1 dòng khỏi giỏ
    public void deleteCartItem(int id) {
        if (conn == null) {
            return;
        }
        String sql = "DELETE FROM cart_items WHERE cart_item_id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Xóa toàn bộ giỏ hàng của một khách (sau khi đặt hàng thành công).
     */
    public void deleteCartByCustomerId(int customerId) {
        if (conn == null) {
            return;
        }
        String sql = "DELETE FROM cart_items WHERE customer_id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, customerId);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 7) Lấy 1 dòng theo (customer_id, variant_id) để check tồn tại trước khi add
    public CartItem getByCustomerAndVariant(int customerId, int variantId) {
        if (conn == null) {
            return null;
        }
        String sql = "SELECT cart_item_id, customer_id, variant_id, quantity, created_at FROM cart_items WHERE customer_id = ? AND variant_id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, customerId);
            ps.setInt(2, variantId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapCartItem(rs);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // 8) Lấy giỏ để hiển thị trang cart (kèm tên sp, sku, giá, thumbnail)
    public List<CartItemDisplay> getCartDisplayByCustomerId(int customerId) {
        List<CartItemDisplay> list = new ArrayList<>();
        if (conn == null) {
            return list;
        }
        // Lấy thumbnail bằng subquery TOP 1 để khỏi phải GROUP BY/MAX.
        String sql = "SELECT c.cart_item_id, c.variant_id, c.quantity, "
                + "p.name AS product_name, pv.sku, pv.selling_price, "
                + "(SELECT TOP 1 pi.image_url "
                + " FROM product_images pi "
                + " WHERE pi.product_id = p.product_id AND pi.is_thumbnail = 1) AS thumbnail_url "
                + "FROM cart_items c "
                + "JOIN product_variants pv ON c.variant_id = pv.variant_id "
                + "JOIN products p ON pv.product_id = p.product_id "
                + "WHERE c.customer_id = ? "
                + "ORDER BY c.created_at DESC";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, customerId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(new CartItemDisplay(
                            rs.getInt("cart_item_id"),
                            rs.getInt("variant_id"),
                            rs.getString("product_name"),
                            rs.getString("sku"),
                            rs.getLong("selling_price"),
                            rs.getInt("quantity"),
                            rs.getString("thumbnail_url")
                    ));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * Tổng số lượng sản phẩm trong giỏ (tổng quantity các dòng)
     */
    public int getCartTotalQuantityByCustomerId(int customerId) {
        if (conn == null) {
            return 0;
        }
        String sql = "SELECT COALESCE(SUM(quantity), 0) FROM cart_items WHERE customer_id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, customerId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * Số dòng sản phẩm trong giỏ (số sản phẩm khác nhau)
     */
    public int getCartItemCountByCustomerId(int customerId) {
        if (conn == null) {
            return 0;
        }
        String sql = "SELECT COUNT(*) FROM cart_items WHERE customer_id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, customerId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
}
