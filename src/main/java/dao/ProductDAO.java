package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import model.Product;
import utils.DBContext;

/**
 * @author CT
 */
public class ProductDAO extends DBContext {

    private BrandDAO brandDAO = new BrandDAO();
    private CategoryDAO categoryDAO = new CategoryDAO();

    // 1. Lấy tất cả sản phẩm
    public List<Product> getAllproduct() {
        List<Product> list = new ArrayList<>();
        String sql = "SELECT * FROM products";
        try (PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(mapResultSetToProduct(rs));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    // 2. Lấy sản phẩm theo ID
    public Product getProductById(int id) {
        String sql = "SELECT * FROM products WHERE product_id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToProduct(rs);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // 3. Thêm mới sản phẩm
    public boolean insertProduct(Product p) {
        String sql = "INSERT INTO products (category_id, brand_id, name, description, "
                + "warranty_months, is_active, created_at) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, p.getCategory().getCategoryId());
            ps.setInt(2, p.getBrand().getBrandId());
            ps.setString(3, p.getProductName());
            ps.setString(4, p.getDescription());
            ps.setInt(5, p.getWarranty_months());
            ps.setByte(6, p.getIs_active());
            ps.setTimestamp(7, Timestamp.valueOf(LocalDateTime.now()));

            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // 4. Cập nhật sản phẩm
    public boolean updateProduct(Product p) {
        String sql = "UPDATE products SET category_id = ?, brand_id = ?, name = ?, "
                + "description = ?, warranty_months = ?, is_active = ? WHERE product_id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, p.getCategory().getCategoryId());
            ps.setInt(2, p.getBrand().getBrandId());
            ps.setString(3, p.getProductName());
            ps.setString(4, p.getDescription());
            ps.setInt(5, p.getWarranty_months());
            ps.setByte(6, p.getIs_active());
            ps.setInt(7, p.getProductId());

            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // 5. Xóa sản phẩm
    public boolean deleteProduct(int id) {
        String sql = "UPDATE products SET is_active = 0 WHERE product_id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // Hàm hỗ trợ map dữ liệu từ DB sang Object
    private Product mapResultSetToProduct(ResultSet rs) throws Exception {
        int productID = rs.getInt("product_id");
        int categoryID = rs.getInt("category_id");
        int brandID = rs.getInt("brand_id");
        String productName = rs.getString("name");
        String description = rs.getString("description");
        int warrantyMonths = rs.getInt("warranty_months");
        byte isActive = rs.getByte("is_active");

        Timestamp ts = rs.getTimestamp("created_at");
        LocalDateTime createdAt = (ts != null) ? ts.toLocalDateTime() : LocalDateTime.now();

        return new Product(
                productID,
                categoryDAO.getCategoryById(categoryID),
                brandDAO.getBrandById(brandID),
                productName,
                description,
                warrantyMonths,
                isActive,
                createdAt
        );
    }

    public static void main(String[] args) {
        ProductDAO dao = new ProductDAO();
        model.Category cat = new model.Category();
        cat.setCategoryId(1);
        model.Brand brand = new model.Brand();
        brand.setBrandId(1);


        // 1. TEST INSERT
//        Product pNew = new Product(0, cat, brand, "Sản phẩm Test", "Mô tả test", 12, (byte) 1, LocalDateTime.now());
//        boolean isIns = dao.insertProduct(pNew);
//        System.out.println("1. Insert: " + (isIns ? "OK" : "FAIL"));

        // Lấy ID vừa tạo (Giả sử là ID lớn nhất hoặc lấy từ danh sách)
        List<Product> list = dao.getAllproduct();
        int lastId = list.get(list.size() - 1).getProductId();

        // 2. TEST GET BY ID
        Product pGet = dao.getProductById(lastId);
//        System.out.println("2. GetByID (" + lastId + "): " + (pGet != null ? "Tìm thấy: " + pGet.getProductName() : "FAIL"));

        // 3. TEST UPDATE
//        pGet.setProductName(
//                "Sản phẩm đã Update");
//        boolean isUpd = dao.updateProduct(new Product(11, cat, brand, "Sản phẩm Test", "Mô tả test 2", 12, (byte) 1, LocalDateTime.now()));
//        System.out.println("3. Update: " + (isUpd ? "OK" : "FAIL"));

        // 4. TEST DELETE (Hoặc Soft Delete tùy bạn cài đặt)
//        boolean isDel = dao.deleteProduct(lastId);
//        System.out.println("4. Delete: " + (isDel ? "OK" : "FAIL"));

        // 5. TEST GET ALL
        List<Product> listFinal = dao.getAllproduct();
        for (Product object : listFinal) {
            System.out.println(object);
        }

//        System.out.println("========== FINISH TESTING ==========");
    }
}
