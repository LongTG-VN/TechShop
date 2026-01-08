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
import model.loyaltyPoints;
import utils.DBContext;

/**
 *
 * @author ASUS
 */
public class loyaltyPointsDAO extends DBContext{
    
        private userDAO userDAO = new userDAO();

        public List<loyaltyPoints> getAllloyaltyPoints() {
       
        List<loyaltyPoints> list = new ArrayList<>();
        String sql = "SELECT loyalty_points.*\n"
                + "FROM     loyalty_points";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {

                int pointID = rs.getInt("point_id");
                int userID = rs.getInt("user_id");
                int points = rs.getInt("points");
                String reason = rs.getString("reason");
                LocalDateTime created_at = rs.getTimestamp("created_at").toLocalDateTime();

                list.add(new loyaltyPoints(pointID, userDAO.getUserById(userID), points, reason, created_at));
            }
        } catch (Exception e) {
        }
        return list;
    }
        
        public boolean addLoyaltyPoints(loyaltyPoints lp) {
        String sql = "INSERT INTO [loyalty_points] ([user_id], [points], [reason]) VALUES (?, ?, ?)";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            // Lấy ID từ đối tượng user bên trong loyaltyPoints
            ps.setInt(1, lp.getUser().getUserId()); 
            ps.setInt(2, lp.getPoints());
            ps.setString(3, lp.getReason());
            // Truyền thời gian hiện tại từ Java hoặc để null nếu muốn SQL tự dùng GETDATE()
           
            
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
        
        public static void main(String[] args) {
         loyaltyPointsDAO a = new loyaltyPointsDAO();
         userDAO uDao = new userDAO();
         model.user u = uDao.getUserById(1);
         loyaltyPoints newPoint = new loyaltyPoints(0, u, 100, "Thưởng mua hàng A", LocalDateTime.now());
         a.addLoyaltyPoints(newPoint);     
         
         List<loyaltyPoints> list = a.getAllloyaltyPoints();
                 for (loyaltyPoints points : list) {
                     System.out.println(points);
            }

         
            
    }

}
