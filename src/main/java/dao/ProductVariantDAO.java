/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import model.product;
import model.ProductVariants;
import utils.DBContext;

/**
 *
 * @author ASUS
 */
public class ProductVariantDAO extends DBContext {

    private productDAO productDAO = new productDAO();

    // 1. Lấy tất cả biến thể (List All)
    public List<ProductVariants> getAllProductVariants() {
        List<ProductVariants> list = new ArrayList<>();
        String sql = "SELECT * FROM product_variants";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(mapRowToVariant(rs));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    // 2. Lấy biến thể theo ID (Get By ID)
    public ProductVariants getVariantById(int variantId) {
        String sql = "SELECT * FROM product_variants WHERE variant_id = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, variantId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return mapRowToVariant(rs);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // 3. Lấy tất cả biến thể của 1 Sản phẩm (QUAN TRỌNG: Dùng cho trang Product Detail)
    // Ví dụ: iPhone 15 có các màu: Titan Xanh, Titan Tự Nhiên...
    public List<ProductVariants> getVariantsByProductId(int productId) {
        List<ProductVariants> list = new ArrayList<>();
        String sql = "SELECT * FROM product_variants WHERE product_id = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, productId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(mapRowToVariant(rs));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    // 4. Thêm biến thể mới (Insert)
    public boolean insertVariant(ProductVariants pv) {
        String sql = "INSERT INTO [product_variants] "
                   + "([product_id], [color], [storage_capacity], [sku], [original_price], [price], [stock_quantity], [image_url], [is_active]) "
                   + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, pv.getProduct().getProductId());
            ps.setString(2, pv.getColor());
            ps.setString(3, pv.getStorage_capacity()); // Đã sửa tên biến cho đúng chuẩn
            ps.setString(4, pv.getSku());
            ps.setDouble(5, pv.getOriginal_price());
            ps.setDouble(6, pv.getPrice());
            ps.setInt(7, pv.getStock_quantity());
            ps.setString(8, pv.getImgURL());
            ps.setByte(9, pv.getIs_active());

            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // 5. Cập nhật biến thể (Update)
    public boolean updateVariant(ProductVariants pv) {
        String sql = "UPDATE [product_variants] SET "
                   + "[color] = ?, "
                   + "[storage_capacity] = ?, "
                   + "[sku] = ?, "
                   + "[original_price] = ?, "
                   + "[price] = ?, "
                   + "[stock_quantity] = ?, "
                   + "[image_url] = ?, "
                   + "[is_active] = ? "
                   + "WHERE [variant_id] = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, pv.getColor());
            ps.setString(2, pv.getStorage_capacity());
            ps.setString(3, pv.getSku());
            ps.setDouble(4, pv.getOriginal_price());
            ps.setDouble(5, pv.getPrice());
            ps.setInt(6, pv.getStock_quantity());
            ps.setString(7, pv.getImgURL());
            ps.setByte(8, pv.getIs_active());
            ps.setInt(9, pv.getVariantId());

            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // 6. Xóa biến thể (Delete)
    public boolean deleteVariant(int variantId) {
        String sql = "DELETE FROM product_variants WHERE variant_id = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, variantId);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // Helper: Map ResultSet sang Object (Đã sửa lỗi lấy sai cột của bạn)
    private ProductVariants mapRowToVariant(ResultSet rs) throws SQLException {
        int variantId = rs.getInt("variant_id");
        int productId = rs.getInt("product_id");
        String color = rs.getString("color");
        
        // Lỗi cũ: rs.getString("stock_quantity") -> Sai, phải là storage_capacity
        String storageCapacity = rs.getString("storage_capacity"); 
        
        String sku = rs.getString("sku");
        double originalPrice = rs.getDouble("original_price");
        double price = rs.getDouble("price");
        int stockQuantity = rs.getInt("stock_quantity");
        String imgURL = rs.getString("image_url");
        byte isActive = rs.getByte("is_active");
        
        Timestamp ts = rs.getTimestamp("created_at");
        LocalDateTime createdAt = (ts != null) ? ts.toLocalDateTime() : LocalDateTime.now();

        product product = productDAO.getProductById(productId);

        return new ProductVariants(variantId, product, color, storageCapacity, sku, originalPrice, price, imgURL, stockQuantity, isActive, createdAt);
    }

    // ================= TEST CASE NGẮN GỌN =================
    public static void main(String[] args) {
        ProductVariantDAO dao = new ProductVariantDAO();
        productDAO pDao = new productDAO();
        
        // Product ID để test (Ví dụ ID 1 = iPhone 15 Pro Max)
        int productId = 1; 
//        System.out.println("TEST in ra het");
//        for (ProductVariants allProductVariant : dao.getAllProductVariants()) {
//            System.out.println(allProductVariant);
//        }

        // 1. Chèn (Insert) - Dùng SKU ngẫu nhiên để tránh lỗi Duplicate Key
//        System.out.println("--- INSERT VARIANT ---");
        String uniqueSku = "TEST-IP15-" + System.currentTimeMillis();
        product p = pDao.getProductById(productId);
//        ProductVariants newVariant = new ProductVariants(0, p, "Pink Test", "128GB", uniqueSku, 30000000, 28000000, "img.jpg", 10, (byte)1, null);
//        
//        boolean isInserted = dao.insertVariant(newVariant);
//        System.out.println("Insert Result: " + isInserted);

        // 2. Get By Product ID (Xem danh sách biến thể của SP đó)
//        System.out.println("\n--- LIST BY PRODUCT ID " + productId + " ---");
//        List<ProductVariants> list = dao.getVariantsByProductId(productId);
//         list.forEach(System.out::println); // In hết cac bien the cua 1 sp
        

//             System.out.println("Delete Result: " + dao.deleteVariant(7));
           
             
//        
            System.out.println("Update Result: " + dao.updateVariant(new ProductVariants(8, p, "Pink Test", "128GB", uniqueSku, 30000000, 28000000, "img test test.jpg", 10, (byte)1, null)));


       
        }
    
}