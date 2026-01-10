/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import model.ImportReceipts;
import model.Supplier;
import model.user;
import utils.DBContext;

/**
 *
 * @author ASUS
 */
public class ImportReceiptsDAO extends DBContext {

    private userDAO userDao = new userDAO();
    private SupplierDAO supplierDAO = new SupplierDAO();

    // 1. READ: Lấy tất cả phiếu nhập
    public List<ImportReceipts> getAllImportReceipt() {
        List<ImportReceipts> list = new ArrayList<>();
        String sql = "SELECT * FROM import_receipts ORDER BY import_date DESC"; // Nên sắp xếp mới nhất lên đầu
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(mapRowToReceipt(rs));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    // 2. READ: Lấy phiếu nhập theo ID (Xem chi tiết)
    public ImportReceipts getImportReceiptById(int id) {
        String sql = "SELECT * FROM import_receipts WHERE receipt_id = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return mapRowToReceipt(rs);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // 3. CREATE: Tạo phiếu nhập mới
    // QUAN TRỌNG: Hàm này trả về int (ID của phiếu vừa tạo) thay vì boolean.
    // Lý do: Sau khi tạo phiếu, bạn cần ID này để insert vào bảng ImportDetails.
    public int createImportReceipt(ImportReceipts ir) {
        String sql = "INSERT INTO [import_receipts] ([supplier_id], [staff_id], [total_cost], [import_date]) VALUES (?, ?, ?, ?)";
        try {
            // Thêm Statement.RETURN_GENERATED_KEYS để lấy ID tự tăng
            PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            
            ps.setInt(1, ir.getSupplier().getSupplier_id());
            ps.setInt(2, ir.getUser().getUserId()); // staff_id lấy từ User object
            ps.setDouble(3, ir.getTotalCost()); // Thường lúc đầu là 0
            
            // Xử lý ngày tháng
            if (ir.getImportDate() != null) {
                ps.setTimestamp(4, Timestamp.valueOf(ir.getImportDate()));
            } else {
                ps.setTimestamp(4, Timestamp.valueOf(LocalDateTime.now()));
            }

            int rowsAffected = ps.executeUpdate();
            
            if (rowsAffected > 0) {
                // Lấy ID vừa sinh ra
                ResultSet rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    return rs.getInt(1); // Trả về receipt_id mới
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1; // Trả về -1 nếu lỗi
    }

    // 4. UPDATE: Cập nhật tổng tiền (Chạy sau khi đã thêm các Import Details)
    public boolean updateTotalCost(int receiptId, double newTotalCost) {
        String sql = "UPDATE import_receipts SET total_cost = ? WHERE receipt_id = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setDouble(1, newTotalCost);
            ps.setInt(2, receiptId);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    
    // 5. UPDATE: Sửa thông tin phiếu (Ví dụ chọn sai nhà cung cấp)
    public boolean updateImportReceipt(ImportReceipts ir) {
        String sql = "UPDATE import_receipts SET supplier_id = ?, staff_id = ?, total_cost = ? WHERE receipt_id = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, ir.getSupplier().getSupplier_id());
            ps.setInt(2, ir.getUser().getUserId());
            ps.setDouble(3, ir.getTotalCost());
            ps.setInt(4, ir.getReceiptId());
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // 6. DELETE: Xóa phiếu nhập
    // Lưu ý: Cần xóa ImportDetails trước (hoặc thiết lập Cascade DB) nếu không sẽ lỗi khóa ngoại
    public boolean deleteImportReceipt(int id) {
        String sql = "DELETE FROM import_receipts WHERE receipt_id = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // Helper: Map ResultSet sang Object
    private ImportReceipts mapRowToReceipt(ResultSet rs) throws Exception {
        int receiptId = rs.getInt("receipt_id");
        int supplierId = rs.getInt("supplier_id");
        int staffId = rs.getInt("staff_id");
        double totalCost = rs.getDouble("total_cost");
        
        Timestamp ts = rs.getTimestamp("import_date");
        LocalDateTime importDate = (ts != null) ? ts.toLocalDateTime() : LocalDateTime.now();

        // Lấy object Supplier và User (Staff)
        Supplier supplier = supplierDAO.getSupplierById(supplierId);
        user staff = userDao.getUserById(staffId);

        return new ImportReceipts(receiptId, supplier, staff, totalCost, importDate);
    }

    // ================= MAIN TEST CASE =================
    public static void main(String[] args) {
        ImportReceiptsDAO dao = new ImportReceiptsDAO();
        SupplierDAO supDao = new SupplierDAO();
        userDAO uDao = new userDAO();

        System.out.println("--- 1. LIST ALL RECEIPTS ---");
        List<ImportReceipts> list = dao.getAllImportReceipt();
        for (ImportReceipts item : list) {
            System.out.println("ID: " + item.getReceiptId() + " - Supplier: " + item.getSupplier().getSupplier_name()+ " - Total: " + item.getTotalCost());
        }

        System.out.println("\n--- 2. CREATE NEW RECEIPT ---");
        // Giả lập dữ liệu: Supplier ID 1, Staff ID 1 (Admin)
        Supplier sup = supDao.getSupplierById(1);
        user staff = uDao.getUserById(1);
        
        if (sup != null && staff != null) {
            ImportReceipts newReceipt = new ImportReceipts(0, sup, staff, 0, LocalDateTime.now());
            
            // Hàm này trả về ID mới tạo
            int newId = dao.createImportReceipt(newReceipt);
            System.out.println("Created Receipt ID: " + newId);

            if (newId != -1) {
                System.out.println("\n--- 3. GET BY ID (" + newId + ") ---");
                System.out.println(dao.getImportReceiptById(newId));

                System.out.println("\n--- 4. UPDATE TOTAL COST ---");
                // Giả sử sau khi thêm chi tiết, tổng tiền là 50 triệu
                boolean updated = dao.updateTotalCost(newId, 50000000);
                System.out.println("Update Total Cost Result: " + updated);
                
                // Check lại
                ImportReceipts check = dao.getImportReceiptById(newId);
                System.out.println("New Total: " + check.getTotalCost());

                 System.out.println("\n--- 5. DELETE RECEIPT ---");
                 boolean deleted = dao.deleteImportReceipt(newId);
                 System.out.println("Delete Result: " + deleted);
            }
        } else {
            System.out.println("Cần có Supplier ID 1 và User ID 1 trong DB để test chức năng này.");
        }
    }
}