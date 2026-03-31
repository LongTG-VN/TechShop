/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import model.ImportReceipts;
import utils.DBContext;

/**
 *
 * @author LE HOANG NHAN
 */
public class ImportReceiptsDAO extends DBContext {

    private String totalCostColumn;
    private String receiptsTable;
    private String receiptItemsTable;
    private Boolean createdAtIsIntType;

    private String resolveReceiptsTable() {
        if (receiptsTable != null) {
            return receiptsTable;
        }
        receiptsTable = "import_receipts";
        try {
            DatabaseMetaData meta = conn.getMetaData();
            String[] candidates = {"import_receipts", "import_receipt"};
            for (String candidate : candidates) {
                try (ResultSet rs = meta.getTables(null, null, candidate, new String[]{"TABLE"})) {
                    if (rs.next()) {
                        receiptsTable = rs.getString("TABLE_NAME");
                        return receiptsTable;
                    }
                }
                try (ResultSet rs = meta.getTables(null, null, candidate.toUpperCase(), new String[]{"TABLE"})) {
                    if (rs.next()) {
                        receiptsTable = rs.getString("TABLE_NAME");
                        return receiptsTable;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return receiptsTable;
    }

    private String resolveReceiptItemsTable() {
        if (receiptItemsTable != null) {
            return receiptItemsTable;
        }
        receiptItemsTable = "import_receipt_items";
        try {
            DatabaseMetaData meta = conn.getMetaData();
            String[] candidates = {"import_receipt_items", "import_receipt_item"};
            for (String candidate : candidates) {
                try (ResultSet rs = meta.getTables(null, null, candidate, new String[]{"TABLE"})) {
                    if (rs.next()) {
                        receiptItemsTable = rs.getString("TABLE_NAME");
                        return receiptItemsTable;
                    }
                }
                try (ResultSet rs = meta.getTables(null, null, candidate.toUpperCase(), new String[]{"TABLE"})) {
                    if (rs.next()) {
                        receiptItemsTable = rs.getString("TABLE_NAME");
                        return receiptItemsTable;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return receiptItemsTable;
    }

    private String resolveColumn(String... candidates) {
        String table = resolveReceiptsTable();
        try {
            DatabaseMetaData meta = conn.getMetaData();
            for (String candidate : candidates) {
                try (ResultSet rs = meta.getColumns(null, null, table, candidate)) {
                    if (rs.next()) {
                        return rs.getString("COLUMN_NAME");
                    }
                }
                try (ResultSet rs = meta.getColumns(null, null, table.toUpperCase(), candidate.toUpperCase())) {
                    if (rs.next()) {
                        return rs.getString("COLUMN_NAME");
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return candidates[0];
    }

    private String receiptIdCol() {
        return resolveColumn("receipt_id", "receiptId", "id");
    }

    private String supplierIdCol() {
        return resolveColumn("supplier_id", "supplierId");
    }

    private String employeeIdCol() {
        return resolveColumn("employee_id", "employeeId", "staff_id", "staffId");
    }

    private String importDateCol() {
        return resolveColumn("import_date", "importDate", "created_at", "createdAt");
    }

    private String receiptCodeCol() {
        return resolveColumn("receipt_code", "receiptCode");
    }

    private String statusCol() {
        return resolveColumn("status");
    }

    private String noteCol() {
        return resolveColumn("note");
    }

    private String createdByCol() {
        return resolveColumn("created_by", "createdBy");
    }

    private String createdAtCol() {
        return resolveColumn("created_at", "createdAt");
    }

    private boolean isCreatedAtIntType() {
        if (createdAtIsIntType != null) {
            return createdAtIsIntType;
        }
        createdAtIsIntType = false;
        String table = resolveReceiptsTable();
        String col = createdAtCol();
        try {
            DatabaseMetaData meta = conn.getMetaData();
            try (ResultSet rs = meta.getColumns(null, null, table, col)) {
                if (rs.next()) {
                    String type = rs.getString("TYPE_NAME");
                    createdAtIsIntType = type != null && type.toUpperCase().contains("INT");
                    return createdAtIsIntType;
                }
            }
            try (ResultSet rs = meta.getColumns(null, null, table.toUpperCase(), col.toUpperCase())) {
                if (rs.next()) {
                    String type = rs.getString("TYPE_NAME");
                    createdAtIsIntType = type != null && type.toUpperCase().contains("INT");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return createdAtIsIntType;
    }

    private Timestamp readCreatedAt(ResultSet rs) throws Exception {
        if (!isCreatedAtIntType()) {
            return rs.getTimestamp("created_at");
        }
        int raw = rs.getInt("created_at");
        if (raw <= 0) {
            return null;
        }
        // Support yyyymmdd integer format.
        String s = String.valueOf(raw);
        if (s.length() == 8) {
            int y = Integer.parseInt(s.substring(0, 4));
            int m = Integer.parseInt(s.substring(4, 6));
            int d = Integer.parseInt(s.substring(6, 8));
            return Timestamp.valueOf(String.format("%04d-%02d-%02d 00:00:00", y, m, d));
        }
        return null;
    }

    private String resolveTotalCostColumn() {
        if (totalCostColumn != null) {
            return totalCostColumn;
        }
        totalCostColumn = resolveColumn("total_cost", "totalCost");
        return totalCostColumn;
    }

    private boolean hasRealTotalCostColumn() {
        String col = resolveTotalCostColumn();
        return !"total_cost".equals(col) && !"totalCost".equals(col) ? true : hasColumn("total_cost", "totalCost");
    }

    private boolean hasColumn(String... candidates) {
        String table = resolveReceiptsTable();
        try {
            DatabaseMetaData meta = conn.getMetaData();
            for (String candidate : candidates) {
                try (ResultSet rs = meta.getColumns(null, null, table, candidate)) {
                    if (rs.next()) {
                        return true;
                    }
                }
                try (ResultSet rs = meta.getColumns(null, null, table.toUpperCase(), candidate.toUpperCase())) {
                    if (rs.next()) {
                        return true;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    private String buildSelectBase() {
        String totalExpr = hasRealTotalCostColumn()
                ? resolveTotalCostColumn()
                : "(SELECT COALESCE(SUM(i.import_price * i.quantity), 0) FROM " + resolveReceiptItemsTable()
                + " i WHERE i.receipt_id = " + resolveReceiptsTable() + "." + receiptIdCol() + ")";
        return "SELECT "
                + receiptIdCol() + " AS receipt_id, "
                + receiptCodeCol() + " AS receipt_code, "
                + supplierIdCol() + " AS supplier_id, "
                + employeeIdCol() + " AS employee_id, "
                + totalExpr + " AS total_cost, "
                + importDateCol() + " AS import_date, "
                + statusCol() + " AS status, "
                + noteCol() + " AS note, "
                + createdByCol() + " AS created_by, "
                + createdAtCol() + " AS created_at "
                + "FROM " + resolveReceiptsTable();
    }

    private ImportReceipts mapReceipt(ResultSet rs) throws Exception {
        ResultSetMetaData md = rs.getMetaData();
        if (md.getColumnCount() >= 5) {
            return new ImportReceipts(
                    rs.getInt("receipt_id"),
                    rs.getInt("supplier_id"),
                    rs.getInt("employee_id"),
                    rs.getDouble("total_cost"),
                    rs.getTimestamp("import_date"),
                    rs.getString("receipt_code"),
                    rs.getString("status"),
                    rs.getString("note"),
                    rs.getInt("created_by"),
                    readCreatedAt(rs)
            );
        }
        throw new IllegalArgumentException("ResultSet is missing expected columns for import receipt.");
    }

    public List<ImportReceipts> getAllReceipts() {
        List<ImportReceipts> list = new ArrayList<>();
        String sql = buildSelectBase();
        try (PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(mapReceipt(rs));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public ImportReceipts getReceiptById(int id) {
        String sql = buildSelectBase() + " WHERE " + receiptIdCol() + " = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapReceipt(rs);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public int insertReceiptReturnId(ImportReceipts p) {
        String sql = "INSERT INTO " + resolveReceiptsTable()
                + " (" + supplierIdCol() + ", " + employeeIdCol()
                + (hasRealTotalCostColumn() ? ", " + resolveTotalCostColumn() : "")
                + ", " + importDateCol()
                + ", " + receiptCodeCol()
                + ", " + statusCol()
                + ", " + createdByCol()
                + ", " + createdAtCol()
                + ") VALUES (?, ?"
                + (hasRealTotalCostColumn() ? ", ?" : "")
                + ", ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, p.getSupplier_id());
            ps.setInt(2, p.getEmployee_id());
            int idx = 3;
            if (hasRealTotalCostColumn()) {
                ps.setDouble(idx++, p.getTotal_cost());
            }
            java.sql.Timestamp now = new java.sql.Timestamp(System.currentTimeMillis());
            ps.setTimestamp(idx++, p.getImport_date() != null ? p.getImport_date() : now);
            String rc = generateNextReceiptCode();
            ps.setString(idx++, rc);
            ps.setString(idx++, (p.getStatus() == null || p.getStatus().isBlank()) ? "DRAFT" : p.getStatus());
            ps.setInt(idx++, p.getCreated_by() > 0 ? p.getCreated_by() : p.getEmployee_id());
            if (isCreatedAtIntType()) {
                Timestamp v = p.getCreated_at() != null ? p.getCreated_at() : now;
                int ymd = Integer.parseInt(new java.text.SimpleDateFormat("yyyyMMdd").format(v));
                ps.setInt(idx, ymd);
            } else {
                ps.setTimestamp(idx, p.getCreated_at() != null ? p.getCreated_at() : now);
            }
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    private String generateNextReceiptCode() {
        LocalDate now = LocalDate.now();
        String ym = now.format(DateTimeFormatter.ofPattern("yyyyMM"));
        String prefix = "PN-" + ym + "-";
        String sql = "SELECT COALESCE(MAX(TRY_CAST(RIGHT(" + receiptCodeCol() + ", 4) AS INT)), 0) AS max_no "
                + "FROM " + resolveReceiptsTable() + " WHERE " + receiptCodeCol() + " LIKE ?";
        int next = 1;
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, prefix + "%");
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    next = rs.getInt("max_no") + 1;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return prefix + String.format("%04d", next);
    }

    public void insertReceipt(ImportReceipts p) {
        insertReceiptReturnId(p);
    }

    public void updateReceipt(ImportReceipts p) {
        String sql = "UPDATE " + resolveReceiptsTable()
                + " SET " + supplierIdCol() + "=?, " + employeeIdCol() + "=?"
                + (hasRealTotalCostColumn() ? ", " + resolveTotalCostColumn() + "=?" : "")
                + ", " + receiptCodeCol() + "=?"
                + ", " + statusCol() + "=?"
                + ", " + noteCol() + "=?"
                + " WHERE " + receiptIdCol() + "=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, p.getSupplier_id());
            ps.setInt(2, p.getEmployee_id());
            int idx = 3;
            if (hasRealTotalCostColumn()) {
                ps.setDouble(idx++, p.getTotal_cost());
            }
            ps.setString(idx++, p.getReceipt_code());
            ps.setString(idx++, p.getStatus());
            ps.setString(idx++, p.getNote());
            ps.setInt(idx, p.getReceipt_id());
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void recalculateTotalCost(int receiptId) {
        if (!hasRealTotalCostColumn()) {
            return;
        }
        String sql = """
                UPDATE %s
                SET %s = (
                    SELECT COALESCE(SUM(import_price * quantity), 0)
                    FROM %s
                    WHERE receipt_id = ?
                )
                WHERE %s = ?
                """.formatted(resolveReceiptsTable(), resolveTotalCostColumn(), resolveReceiptItemsTable(), receiptIdCol());
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, receiptId);
            ps.setInt(2, receiptId);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean deleteReceipt(int id) {
        String sql = "DELETE FROM " + resolveReceiptsTable() + " WHERE " + receiptIdCol() + " = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public double getTotalImportCostByMonth(int month, int year) {
        String sql;
        if (hasRealTotalCostColumn()) {
            sql = """
                     SELECT COALESCE(SUM(%s), 0) AS total
                     FROM %s
                     WHERE (? = 0 OR MONTH(%s) = ?)
                       AND (? = 0 OR YEAR(%s) = ?)
                     """.formatted(resolveTotalCostColumn(), resolveReceiptsTable(), importDateCol(), importDateCol());
        } else {
            sql = """
                     SELECT COALESCE(SUM(i.import_price * i.quantity), 0) AS total
                     FROM %s r
                     JOIN %s i ON i.receipt_id = r.%s
                     WHERE (? = 0 OR MONTH(r.%s) = ?)
                       AND (? = 0 OR YEAR(r.%s) = ?)
                     """.formatted(resolveReceiptsTable(), resolveReceiptItemsTable(), receiptIdCol(), importDateCol(), importDateCol());
        }
        double total = 0;
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, month);
            ps.setInt(2, month);
            ps.setInt(3, year);
            ps.setInt(4, year);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    total = rs.getDouble("total");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return total;
    }

    // --- MAIN TEST ---
    public static void main(String[] args) {
        ImportReceiptsDAO dao = new ImportReceiptsDAO();

        // 1. Xem danh sach
        System.out.println("--- LIST ALL ---");
        List<ImportReceipts> list = dao.getAllReceipts();
        for (ImportReceipts item : list) {
            System.out.println(item);
        }

        // 2. Insert (Chèn)
        // LƯU Ý: Thay ID 1, 1 bang ID Supplier/Employee CO THAT cua ban
        dao.insertReceipt(new ImportReceipts(0, 1, 1, 550000, null));
        System.out.println("Da them phieu nhap.");

        // 3. Update (Sửa) - Thay ID = 1 bang ID phieu nhap CO THAT
        // dao.updateReceipt(new ImportReceipts(1, 1, 1, 888888, null));
        // System.out.println("Da update phieu ID = 1.");
        // 4. Get By ID
        // System.out.println(dao.getReceiptById(1));
    }
}
