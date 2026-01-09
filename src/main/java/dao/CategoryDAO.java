/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import model.Category;
import utils.DBContext;

/**
 *
 * @author LE HOANG NHAN
 */
public class CategoryDAO extends DBContext {

    // READ ALL
    public List<Category> getAllCategories() {
        List<Category> list = new ArrayList<>();
        String sql = "SELECT * FROM categories";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Category c = new Category(
                        rs.getInt("category_id"),
                        rs.getString("category_name"),
                        rs.getString("slug")
                );
                list.add(c);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    // GET BY ID
    public Category getCategoryById(int id) {
        String sql = "SELECT * FROM categories WHERE category_id = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Category(
                        rs.getInt("category_id"),
                        rs.getString("category_name"),
                        rs.getString("slug")
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // CREATE
    public void insertCategory(Category c) {
        String sql = "INSERT INTO categories(category_name, slug) VALUES (?, ?)";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, c.getCategory_Name());
            ps.setString(2, c.getSlug());
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // UPDATE
    public void updateCategory(Category c) {
        String sql = "UPDATE categories SET category_name = ?, slug = ? WHERE category_id = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, c.getCategory_Name());
            ps.setString(2, c.getSlug());
            ps.setInt(3, c.getCategory_id());
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // DELETE
    public void deleteCategory(int id) {
        String sql = "DELETE FROM categories WHERE category_id = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // TEST
    public static void main(String[] args) {
        CategoryDAO dao = new CategoryDAO();

        // INSERT
//        dao.insertCategory(new Category(0, "Phone", "phone"));
        // UPDATE
//        dao.updateCategory(new Category(1, "Smart Phone", "smart-phone"));
        // GET BY ID
//        System.out.println(dao.getCategoryById(1));
        // DELETE
//        dao.deleteCategory(3);
        // GET ALL
        List<Category> list = dao.getAllCategories();
        for (Category c : list) {
            System.out.println(c);
        }
    }
}
