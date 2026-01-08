/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import model.role;
import model.userAddresses;
import utils.DBContext;

/**
 *
 * @author ASUS
 */
public class userAddressesDAO extends DBContext {

    private userDAO userDAO = new userDAO();

    public List<userAddresses> getAllUserAddresses() {
        List<userAddresses> list = new ArrayList<>();
        String sql = "SELECT user_addresses.*\n"
                + "FROM     user_addresses	";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {

                int addressID = rs.getInt("address_id");
                int userID = rs.getInt("user_id");
                String recipientName = rs.getString("recipient_name");
                String phone = rs.getString("phone");
                String addressLine = rs.getString("address_line");
                byte is_default = rs.getByte("is_default");

                list.add(new userAddresses(addressID, userDAO.getUserById(userID), recipientName, phone, addressLine, is_default));
            }
        } catch (Exception e) {
        }
        return list;
    }

    public static void main(String[] args) {
        userAddressesDAO a = new userAddressesDAO();
        List<userAddresses> list = a.getAllUserAddresses();
        for (userAddresses addresses : list) {
            System.out.println(addresses);
        }

    }
}
