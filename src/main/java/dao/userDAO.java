package dao;

import java.security.MessageDigest;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import model.role;
import model.user;
import utils.DBContext;

public class userDAO extends DBContext {

    private roleDAO roleDAO = new roleDAO();

    // 1. Lấy tất cả người dùng
    public List<user> getAllUser() {
        List<user> list = new ArrayList<>();
        String sql = "SELECT * FROM users";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {

                list.add(mapUser(rs));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    // 2. Lấy người dùng theo ID
    public user getUserById(int id) {
        String sql = "SELECT * FROM users WHERE user_id = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return mapUser(rs);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // 3. Thêm mới người dùng (Bổ sung user_name)
    public boolean addUser(user u) {
        String sql = "INSERT INTO users (user_name, full_name, email, password_hash, phone, role_id, status) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, u.getUserName()); // Cần đảm bảo model user có field userName
            ps.setString(2, u.getFullName());
            ps.setString(3, u.getEmail());
            ps.setString(4, hashMD5(u.getPassword())); // Mã hóa ngay khi add
            ps.setString(5, u.getPhone());
            ps.setInt(6, u.getRole().getRole_id());
            ps.setString(7, u.getStatus());
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // 4. Cập nhật thông tin (Fix lại thứ tự ? và cột)
    public boolean updateUser(user u) {
        String sql = "UPDATE users SET user_name = ?, full_name = ?, email = ?, phone = ?, role_id = ?, status = ? , password_hash = ? WHERE user_id = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, u.getUserName());
            ps.setString(2, u.getFullName());
            ps.setString(3, u.getEmail());
            ps.setString(4, u.getPhone());
            ps.setInt(5, u.getRole().getRole_id());
            ps.setString(6, u.getStatus());
            ps.setInt(7, u.getUserId());
            ps.setString(8, hashMD5(u.getPassword()));
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // 5. Xóa người dùng
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

    // 6. Đăng nhập (Sửa đúng tên cột trong DB của bạn là password_hash)
    public user login(String username, String pass) {
        String sql = "SELECT * FROM users WHERE user_name = ? AND password_hash = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, username);
            ps.setString(2, hashMD5(pass));
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return mapUser(rs);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // Hàm bổ trợ để tránh lặp code (Helper Method)
    private user mapUser(ResultSet rs) throws Exception {
        role r = roleDAO.getRoleById(rs.getInt("role_id"));
        return new user(
                rs.getInt("user_id"),
                rs.getString("user_name"), // Cột mới thêm
                rs.getString("full_name"),
                rs.getString("email"),
                rs.getString("password_hash"),
                rs.getString("phone"),
                r,
                rs.getString("status"),
                rs.getTimestamp("created_at").toLocalDateTime()
        );
    }

    // Hàm Hash MD5 chuẩn
    public String hashMD5(String str) {
        try {
            MessageDigest mes = MessageDigest.getInstance("MD5");
            byte[] mesMD5 = mes.digest(str.getBytes());
            StringBuilder result = new StringBuilder();
            for (byte b : mesMD5) {
                result.append(String.format("%02x", b));
            }
            return result.toString();
        } catch (Exception e) {
            return str; // Nếu lỗi trả về chuỗi gốc (tạm thời)
        }
    }

    public static void main(String[] args) {
        userDAO dao = new userDAO();
        // Test login
        List<user> list = dao.getAllUser();
//        for (user object : list) {
//            System.out.println(object);
//        }
         roleDAO b = new roleDAO();
//      dao.deleteUser(1008);
//        dao.updateUser(new user(1009, "long", "lo", "aa@gmail.com", "123", "12312", b.getRoleById(1), "ACTIVE", LocalDateTime.now()));
//        dao.addUser(new user(0, "long", "lol", "a111a@gmail.com", "12345", "12312", b.getRoleById(1), "ACTIVE", LocalDateTime.now()));
        user u = dao.login("lol", "12345");
        if(u != null) System.out.println("Chào mừng: " + u.getFullName());
    }
}
