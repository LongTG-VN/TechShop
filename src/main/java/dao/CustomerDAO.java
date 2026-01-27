/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import model.Customer;
import utils.DBContext;

/**
 *
 * @author ASUS
 */
public class CustomerDAO extends DBContext {

    public List<Customer> getAllCustomer() {
        List<Customer> list = new ArrayList<>();
        String sql = "SELECT customers.*\n"
                + "FROM     customers";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int role_id = rs.getInt("customer_id");
                String username = rs.getString("username");
                String passwordHash = rs.getString("password_hash");
                String fullname = rs.getString("full_name");
                String email = rs.getString("email");
                String phoneNumber = rs.getString("phone_number");
                String status = rs.getString("status");
                LocalDateTime createdAt = rs.getTimestamp("created_at").toLocalDateTime();
                Customer customer = new Customer(role_id, username, passwordHash, fullname, email, phoneNumber, status, createdAt);
                list.add(customer);
            }
        } catch (Exception e) {
        }
        return list;
    }
    
    
    public Customer getCustomerById(int id) {
        String sql = "SELECT * FROM customers WHERE customer_id = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return mapResultSetToCustomer(rs);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    private Customer mapResultSetToCustomer(ResultSet rs) throws Exception {
        return new Customer(
            rs.getInt("customer_id"),
            rs.getString("username"),
            rs.getString("password_hash"),
            rs.getString("full_name"),
            rs.getString("email"),
            rs.getString("phone_number"),
            rs.getString("status"),
            rs.getTimestamp("created_at").toLocalDateTime()
        );
    }
    
    public boolean deleteCustomer(int id) {
        String sql = "DELETE FROM customers WHERE customer_id = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    public boolean updateCustomer(Customer c) {
        String sql = "UPDATE customers SET full_name = ?, email = ?, phone_number = ?, status = ? WHERE customer_id = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setNString(1, c.getFullname());
            ps.setString(2, c.getEmail());
            ps.setString(3, c.getPhoneNumber());
            ps.setString(4, c.getStatus());
            ps.setInt(5, c.getCustomerID());
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public boolean addCustomer(Customer c) {
        String sql = "INSERT INTO customers (username, password_hash, full_name, email, phone_number, status, created_at) "
                   + "VALUES (?, ?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, c.getUserName());
            ps.setString(2, c.getPassword());
            ps.setNString(3, c.getFullname()); // Dùng setNString cho dữ liệu có dấu (NVARCHAR)
            ps.setString(4, c.getEmail());
            ps.setString(5, c.getPhoneNumber());
            ps.setString(6, c.getStatus());
            ps.setTimestamp(7, Timestamp.valueOf(LocalDateTime.now()));
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public static void main(String[] args) {
        CustomerDAO a = new CustomerDAO();
                List<Customer> list = a.getAllCustomer();
//        for (Customer customer : list) {
//            System.out.println(customer);
//        }
a.addCustomer(new Customer(0, "L", "L", "L", "L", "L", "L", LocalDateTime.MAX));
    }
}
