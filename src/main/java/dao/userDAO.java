/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import model.role;
import model.user;
import utils.DBContext;

/**
 *
 * @author ASUS
 */
public class userDAO extends DBContext {

    private roleDAO roleDAO = new roleDAO();

    public List<user> getAllUser() {
        List<user> list = new ArrayList<>();
        String sql = "SELECT users.*\n"
                + "FROM     users";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int userId = rs.getInt("user_id");
                String fullName = rs.getString("full_name");
                String email = rs.getString("email");
                String password_hash = rs.getString("password_hash");
                String Phone = rs.getString("phone");
                role role = roleDAO.getRoleById(rs.getInt("role_id"));
                String status = rs.getString("status");
                LocalDateTime created_at = rs.getTimestamp("created_at").toLocalDateTime();
                list.add(new user(userId, fullName, email, password_hash, Phone, role, status, created_at));
            }
        } catch (Exception e) {
        }
        return list;
    }

    public user getUserById(int id) {
        String sql = "SELECT * FROM users WHERE user_id = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                role role = roleDAO.getRoleById(rs.getInt("role_id"));
                return new user(
                        rs.getInt("user_id"),
                        rs.getString("full_name"),
                        rs.getString("email"),
                        rs.getString("password_hash"),
                        rs.getString("phone"),
                        role,
                        rs.getString("status"),
                        rs.getTimestamp("created_at").toLocalDateTime()
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean addUser(user u) {
        String sql = "INSERT INTO users (full_name, email, password_hash, phone, role_id, status) VALUES (?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, u.getFullName());
            ps.setString(2, u.getEmail());
            ps.setString(3, u.getPassword());
            ps.setString(4, u.getPhone());
            ps.setInt(5, u.getRole().getRole_id());
            ps.setString(6, u.getStatus());
            System.out.println("Thêm thành công");
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean deleteUser(int id) {
        String sql = "DELETE FROM users WHERE user_id = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean updateUser(user u) {
        String sql = "UPDATE users SET full_name = ?, phone = ?,  role_id = ?, status = ?,email= ? WHERE user_id = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, u.getFullName());
            ps.setString(2, u.getPhone());
            ps.setInt(3, u.getRole().getRole_id());
            ps.setString(4, u.getStatus());
            ps.setInt(5, u.getUserId());
            ps.setString(6, u.getEmail());
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static void main(String[] args) {
        userDAO a = new userDAO();
        List<user> list = a.getAllUser();
//        for (user object : list) {
//            System.out.println(object);
//        }

//        System.out.println(a.getUserById(1));
//        roleDAO roleDAO = new roleDAO();
//        a.updateUser(new user(1005, "Long", "aaaaaa@gmi", "1234", "1234", roleDAO.getRoleById(1), "ACTIVE", LocalDateTime.now()));
//  a.deleteUser(3);
//        a.addUser(new user(0, "NGộ độc 3", "aa242@gma", "123", "123", roleDAO.getRoleById(1), "ACTIVE", LocalDateTime.now()));
    }
}
