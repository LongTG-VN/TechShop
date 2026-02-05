/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import model.Brand;
import utils.DBContext;

/**
 *
 * @author CT
 */
public class BrandDAO extends DBContext {

    // Lấy tất cả danh mục
    public List<Brand> getAllBrand() {
        List<Brand> list = new ArrayList<>();
        String sql = "SELECT brand_id, brand_name, is_active FROM brands";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new Brand(
                        rs.getInt("brand_id"),
                        rs.getString("brand_name"),
                        rs.getBoolean("is_active")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    // Lấy danh mục theo ID
    public Brand getBrandById(int id) {
        String sql = "SELECT brand_id, brand_name, is_active FROM brands WHERE brand_id = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Brand(
                        rs.getInt("brand_id"),
                        rs.getString("brand_name"),
                        rs.getBoolean("is_active")
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // Thêm danh mục mới
    public void insertBrand(String name, boolean is_active) {
        String sql = "INSERT INTO brands (brand_name, is_active) VALUES (?, ?)";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, name);
            ps.setBoolean(2, is_active);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Cập nhật danh mục
    public void updateBrand(Brand b) {
        String sql = "UPDATE brands SET brand_name = ?, is_active = ? WHERE brand_id = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, b.getBrandName());
            ps.setBoolean(2, b.isIsActive());
            ps.setInt(3, b.getBrandId());
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Xóa danh mục
    public void deleteBrand(int id) {
        String sql = "DELETE FROM brands WHERE brand_id = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

// 1. Đếm số lượng sản phẩm thuộc danh mục
    public int countProductsByBrandId(int id) {
        String sql = "SELECT COUNT(*) FROM products WHERE brand_id = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

// 2. Chuyển trạng thái sang Inactive (Soft Delete)
    public void deactivateBrand(int id) {
        String sql = "UPDATE brands SET is_active = 0 WHERE brand_id = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Kiểm tra tên đã tồn tại chưa (dùng cho Add)
    public boolean isBrandNameExists(String name) {
        String sql = "SELECT COUNT(*) FROM brands WHERE brand_name = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, name);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

// Kiểm tra tên đã tồn tại cho các ID khác chưa (dùng cho Update)
    public boolean isBrandNameExists(String name, int excludeId) {
        String sql = "SELECT COUNT(*) FROM brands WHERE brand_name = ? AND brand_id <> ?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, name);
            ps.setInt(2, excludeId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static void main(String[] args) {
        BrandDAO dao = new BrandDAO();
        for (Brand b : dao.getAllBrand()) {
            System.out.println(b);
        }
    }
}
