package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import model.Employees; 
import utils.DBContext;

public class EmployeesDAO extends DBContext {

    // Helper: Map ResultSet sang Object Employee
    private Employees mapResultSetToEmployee(ResultSet rs) throws Exception {
        Employees emp = new Employees();
        emp.setEmployeeId(rs.getInt("employee_id"));
        emp.setUsername(rs.getString("username"));
        emp.setFullName(rs.getNString("full_name"));
        emp.setEmail(rs.getString("email"));
        emp.setPhoneNumber(rs.getString("phone_number"));
        emp.setRoleId(rs.getInt("role_id"));
        emp.setRoleName(rs.getString("role_name")); 
        emp.setStatus(rs.getString("status"));
        emp.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
        return emp;
    }

    // 1. Hàm Đăng nhập (Login)
    public Employees login(String username, String password) {
        String sql = """
                     SELECT e.*, r.role_name 
                     FROM employees e 
                     JOIN roles r ON e.role_id = r.role_id 
                     WHERE e.username = ? AND e.password_hash = ? AND e.status = 'ACTIVE'
                     """;
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, username);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return mapResultSetToEmployee(rs);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // 2. Lấy danh sách tất cả nhân viên
    public List<Employees> getAllEmployees() {
        List<Employees> list = new ArrayList<>();
        String sql = "SELECT e.*, r.role_name FROM employees e JOIN roles r ON e.role_id = r.role_id";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(mapResultSetToEmployee(rs));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    // 3. Thêm nhân viên mới
    public boolean insertEmployee(Employees emp) {
        String sql = """
                     INSERT INTO employees (username, password_hash, full_name, email, phone_number, role_id, status, created_at)
                     VALUES (?, ?, ?, ?, ?, ?, ?, GETDATE())
                     """;
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, emp.getUsername());
            ps.setString(2, emp.getPasswordHash());
            ps.setNString(3, emp.getFullName());
            ps.setString(4, emp.getEmail());
            ps.setString(5, emp.getPhoneNumber());
            ps.setInt(6, emp.getRoleId());
            ps.setString(7, "ACTIVE");
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // 4. Cập nhật thông tin nhân viên
    public boolean updateEmployee(Employees e) {
        String sql = "UPDATE employees SET full_name = ?, email = ?, phone_number = ?, role_id = ? WHERE employee_id = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setNString(1, e.getFullName());
            ps.setString(2, e.getEmail());
            ps.setString(3, e.getPhoneNumber());
            ps.setInt(4, e.getRoleId());
            ps.setInt(5, e.getEmployeeId());
            return ps.executeUpdate() > 0;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return false;
    }

    // 5. Đổi mật khẩu
    public boolean changePassword(int employeeId, String newPasswordHash) {
        String sql = "UPDATE employees SET password_hash = ? WHERE employee_id = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, newPasswordHash);
            ps.setInt(2, employeeId);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // 6. Vô hiệu hóa nhân viên (Xóa mềm)
    public boolean deactivateEmployee(int id) {
        String sql = "UPDATE employees SET status = 'INACTIVE' WHERE employee_id = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return false;
    }

    // Test Main
    public static void main(String[] args) {
        EmployeesDAO dao = new EmployeesDAO();

        System.out.println("--- 1. TEST READ ---");
        dao.getAllEmployees().forEach(System.out::println);

        System.out.println("\n--- 2. TEST CREATE ---");
        Employees newEmp = new Employees();
        String uniqueUser = "staff_" + (System.currentTimeMillis() % 10000);
        newEmp.setUsername(uniqueUser);
        newEmp.setPasswordHash("hash123");
        newEmp.setFullName("Nhân Viên Mới");
        newEmp.setEmail(uniqueUser + "@techshop.com");
        newEmp.setPhoneNumber("0987654321");
        newEmp.setRoleId(2); 
        
        if (dao.insertEmployee(newEmp)) {
            System.out.println("Thêm thành công: " + uniqueUser);
        }

        System.out.println("\n--- 3. TEST UPDATE & CHANGE PASS ---");
        List<Employees> list = dao.getAllEmployees();
        if (!list.isEmpty()) {
            Employees last = list.get(list.size() - 1);
            last.setFullName("Tên Đã Sửa");
            if(dao.updateEmployee(last)) System.out.println("Update thông tin OK");
            if(dao.changePassword(last.getEmployeeId(), "new_hash_456")) System.out.println("Đổi pass OK");
        }

        System.out.println("\n--- 4. TEST DEACTIVATE ---");
        if (!list.isEmpty()) {
            int id = list.get(list.size() - 1).getEmployeeId();
            if (dao.deactivateEmployee(id)) System.out.println("Vô hiệu hóa ID " + id + " OK");
        }

        System.out.println("\n--- 5. KIỂM TRA LẠI ---");
        dao.getAllEmployees().forEach(System.out::println);
    }
}