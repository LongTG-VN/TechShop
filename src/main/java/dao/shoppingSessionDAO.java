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
import model.ShoppingSession;
import model.User;
import utils.DBContext;

/**
 *
 * @author ASUS
 */
public class ShoppingSessionDAO extends DBContext {

    private UserDAO userDAO = new UserDAO();

    public List<ShoppingSession> getAllShoppingSession() {
        List<ShoppingSession> list = new ArrayList<>();
        String sql = "SELECT shopping_sessions.*\n"
                + "FROM     shopping_sessions";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {

                int shoppingSessionID = rs.getInt("session_id");
                int userID = rs.getInt("user_id");

                double totalPrice = rs.getDouble("total_price");

                LocalDateTime updated_at = rs.getTimestamp("updated_at").toLocalDateTime();

                list.add(new ShoppingSession(userID, userDAO.getUserById(userID), totalPrice, updated_at));
            }
        } catch (Exception e) {
        }
        return list;
    }

    public ShoppingSession getSessionById(int sessionId) {
        String sql = "SELECT * FROM shopping_sessions WHERE session_id = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, sessionId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                int userId = rs.getInt("user_id");
                double totalPrice = rs.getDouble("total_price");
                LocalDateTime updatedAt = rs.getTimestamp("updated_at").toLocalDateTime();

                return new ShoppingSession(
                        sessionId,
                        userDAO.getUserById(userId),
                        totalPrice,
                        updatedAt
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
        ShoppingSessionDAO a = new ShoppingSessionDAO();
        List<ShoppingSession> list = a.getAllShoppingSession();
//        for (ShoppingSession session : list) {
//            System.out.println(session);
//        }
        System.out.println(a.getSessionById(1));

    }
}
