package model;

import java.time.LocalDateTime;

/**
 * Model đại diện cho bảng employees
 *
 * @author ASUS
 */
public class Employees {

    private int employeeId;
    private String username;
    private String passwordHash;
    private String fullName;
    private String email;
    private String phoneNumber;
    private int roleId;
    private String status;
    private LocalDateTime createdAt;

    // Thuộc tính bổ sung để hiển thị (Join từ bảng roles)
    private String roleName;

    public Employees() {
    }

    public Employees(int employeeId, String username, String passwordHash, String fullName, String email, String phoneNumber, int roleId, String status, LocalDateTime createdAt) {
        this.employeeId = employeeId;
        this.username = username;
        this.passwordHash = passwordHash;
        this.fullName = fullName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.roleId = roleId;
        this.status = status;
        this.createdAt = createdAt;
    }

    // Getter và Setter
    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    @Override
    public String toString() {
        return "Employee{" + "id=" + employeeId + ", user=" + username
                + ", name=" + fullName + ", role=" + roleName + ", status=" + status + '}';
    }
}
