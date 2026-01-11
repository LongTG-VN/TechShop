/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import model.Role;
import utils.DBContext;

/**
 *
 * @author ASU
 */
public class RoleDAO extends DBContext {

    public List<Role> getAllRole() {
        List<Role> list = new ArrayList<>();
        String sql = "SELECT roles.*\n"
                + "FROM     roles";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {

                int role_id = rs.getInt("role_id");
                String role_name = rs.getString("role_name");
                String description = rs.getString("description");
                byte is_active = rs.getByte("is_active");
                Role role = new Role(role_id, role_name, description, is_active);

                list.add(role);
            }
        } catch (Exception e) {
        }
        return list;
    }

    public void insertRole(Role r) {
        String sql = "INSERT INTO roles (role_name, description, is_active) VALUES (?, ?, ?)";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, r.getRole_name());
            ps.setString(2, r.getDescription());
            ps.setByte(3, r.getIs_active());
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Role getRoleById(int id) {
        String sql = "SELECT * FROM roles WHERE role_id = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Role(rs.getInt("role_id"),
                        rs.getString("role_name"),
                        rs.getString("description"),
                        rs.getByte("is_active"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void updateRole(Role r) {
        String sql = "UPDATE roles SET role_name = ?, description = ? WHERE role_id = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, r.getRole_name());
            ps.setString(2, r.getDescription());
            ps.setInt(3, r.getRole_id());
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
//

    public void deleteRole(int id) {
        String sql = "UPDATE roles SET is_active = 0 WHERE role_id = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        RoleDAO a = new RoleDAO();
        List<Role> list = a.getAllRole();
        // hiển thị 
//                for (Role object : list) {
//            System.out.println(object);
//        }
        // Xóa 
//         a.deleteRole(3);
        //Chèn
//        a.insertRole(new Role(10,"hhi", "Nhân viên bán hàng tai gia",(byte) 1));
        // Update
//        a.updateRole(new Role(4, "WAREHOUSE", "Nhân viên kho", (byte) 0));
        // Get by ID
//        System.out.println(a.getRoleById(1));

    }
}
