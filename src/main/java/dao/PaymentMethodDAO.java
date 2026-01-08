/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import model.PaymentMethod;
import utils.DBContext;

/**
 *
 * @author WIN11
 */
public class PaymentMethodDAO extends DBContext {

    public List<PaymentMethod> getAllPaymentMethod() {
        List<PaymentMethod> list = new ArrayList<>();
        String sql = "SELECT * FROM payment_methods";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new PaymentMethod(
                        rs.getInt("method_id"),
                        rs.getString("method_name")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public PaymentMethod getPaymentMethodById(int id) {
        String sql = "SELECT * FROM payment_methods WHERE method_id = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new PaymentMethod(
                        rs.getInt("method_id"),
                        rs.getString("method_name")
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void insertPaymentMethod(PaymentMethod pm) {
        String sql = "INSERT INTO payment_methods(method_name) VALUES (?)";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, pm.getMethod_name());
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updatePaymentMethod(PaymentMethod pm) {
        String sql = "UPDATE payment_methods SET method_name=? WHERE method_id=?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, pm.getMethod_name());
            ps.setInt(2, pm.getMethod_id());
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deletePaymentMethod(int id) {
        String sql = "DELETE FROM payment_methods WHERE method_id=?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        PaymentMethodDAO a = new PaymentMethodDAO();
// Delete
//        a.deletePaymentMethod(9); 
// Insert
//        a.insertPaymentMethod(new PaymentMethod(6, "QR Pay"));
// Update
//        a.updatePaymentMethod(new PaymentMethod(2, "Bank Transfer"));
// Get by ID
//        System.out.println(a.getPaymentMethodById(1));        

// Get all
        List<PaymentMethod> list = a.getAllPaymentMethod();
        for (PaymentMethod object : list) {
            System.out.println(object);
        }

    }
}
