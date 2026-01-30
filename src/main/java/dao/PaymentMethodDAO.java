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

    public List<PaymentMethod> getAllActivePaymentMethods() {
        List<PaymentMethod> list = new ArrayList<>();
        String sql = "SELECT * FROM payment_methods WHERE is_active = 1";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new PaymentMethod(
                        rs.getInt("method_id"),
                        rs.getString("method_name"),
                        rs.getBoolean("is_active")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public PaymentMethod getActivePaymentMethodById(int id) {
        String sql = "SELECT * FROM payment_methods WHERE method_id = ? AND is_active = 1";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new PaymentMethod(
                        rs.getInt("method_id"),
                        rs.getString("method_name"),
                        rs.getBoolean("is_active")
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<PaymentMethod> getAllInactivePaymentMethods() {
        List<PaymentMethod> list = new ArrayList<>();
        String sql = "SELECT * FROM payment_methods WHERE is_active = 0";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new PaymentMethod(
                        rs.getInt("method_id"),
                        rs.getString("method_name"),
                        rs.getBoolean("is_active")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public void insertPaymentMethod(String name) {
        String sql = "INSERT INTO payment_methods(method_name) VALUES (?)";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, name);
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

    //Soft delete
    public void disablePaymentMethod(int id) {
        String sql = "UPDATE payment_methods SET is_active = 0 WHERE method_id=?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//    //TEST
//    public static void main(String[] args) {
//        PaymentMethodDAO dao = new PaymentMethodDAO();

////         ===== INSERT =====
////         dao.insertPaymentMethod("COD");
////         dao.insertPaymentMethod("VNPAY");
////         dao.insertPaymentMethod("MOMO");
//        // ===== UPDATE =====
////         dao.updatePaymentMethod(new PaymentMethod(2, "Bank Transfer", true));
//        // ===== DISABLE (Soft delete) =====
////         dao.disablePaymentMethod(3);
//        // ===== GET BY ID  =====
//        System.out.println(dao.getActivePaymentMethodById(2));
////          ===== GET ALL UN-ACTIVE =====
////          List<PaymentMethod> list = dao.getAllInactivePaymentMethods();
////          for (PaymentMethod pm : list) {
////          System.out.println(pm);
////          }
//
//        // ===== GET ALL =====
////        List<PaymentMethod> allList = dao.getAllActivePaymentMethods();
////        for (PaymentMethod pm : allList) {
////            System.out.println(pm);
////        }
//    }

}
