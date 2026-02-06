package controller.staff;

import dao.InventoryItemDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import model.InventoryItem;

@WebServlet(name = "InventoryServlet", urlPatterns = {"/inventory"})
public class InventoryServlet extends HttpServlet {

    private InventoryItemDAO dao;

    @Override
    public void init() {
        dao = new InventoryItemDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setCharacterEncoding("UTF-8");
        request.setCharacterEncoding("UTF-8");

        String action = request.getParameter("action");
        if (action == null) {
            response.sendRedirect(request.getContextPath() + "/staffservlet?action=inventoryManagement");
            return;
        }

        switch (action) {
            case "add" -> {
                request.setAttribute("contentPage", "/pages/InventoryManagementPage/addInventory.jsp");
                request.getRequestDispatcher("/template/staffTemplate.jsp").forward(request, response);
            }
            case "view" -> {
                InventoryItem item = getByIdSafe(request);
                request.setAttribute("inventoryItem", item);
                request.setAttribute("contentPage", "/pages/InventoryManagementPage/detailInventory.jsp");
                request.getRequestDispatcher("/template/staffTemplate.jsp").forward(request, response);
            }
            case "edit" -> {
                InventoryItem item = getByIdSafe(request);
                request.setAttribute("inventoryItem", item);
                request.setAttribute("contentPage", "/pages/InventoryManagementPage/editInventory.jsp");
                request.getRequestDispatcher("/template/staffTemplate.jsp").forward(request, response);
            }
            case "delete" -> {
                String idStr = request.getParameter("id");
                boolean ok = false;
                if (idStr != null && !idStr.isBlank()) {
                    try {
                        int id = Integer.parseInt(idStr);
                        ok = dao.deleteInventory(id);
                    } catch (NumberFormatException ignored) {
                    }
                }
                setMsg(request.getSession(), ok ? "Đã xóa item kho." : "Xóa thất bại (ID không hợp lệ hoặc đang được ràng buộc).", ok ? "success" : "danger");
                response.sendRedirect(request.getContextPath() + "/staffservlet?action=inventoryManagement");
            }
            default -> response.sendRedirect(request.getContextPath() + "/staffservlet?action=inventoryManagement");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setCharacterEncoding("UTF-8");
        request.setCharacterEncoding("UTF-8");

        String action = request.getParameter("action");
        if (action == null) {
            response.sendRedirect(request.getContextPath() + "/staffservlet?action=inventoryManagement");
            return;
        }

        switch (action) {
            case "add" -> handleAdd(request, response);
            case "update" -> handleUpdate(request, response);
            default -> response.sendRedirect(request.getContextPath() + "/staffservlet?action=inventoryManagement");
        }
    }

    private void handleAdd(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String variantIdStr = request.getParameter("variant_id");
        String receiptItemIdStr = request.getParameter("receipt_item_id");
        String imei = request.getParameter("imei");
        String importPriceStr = request.getParameter("import_price");
        String status = request.getParameter("status");

        try {
            int variantId = Integer.parseInt(variantIdStr);
            int receiptItemId = Integer.parseInt(receiptItemIdStr);
            double importPrice = Double.parseDouble(importPriceStr);
            if (imei == null || imei.trim().isEmpty()) {
                setMsg(request.getSession(), "Vui lòng nhập IMEI.", "danger");
            } else {
                String normalizedStatus = (status == null || status.trim().isEmpty()) ? "IN_STOCK" : status.trim();
                InventoryItem item = new InventoryItem(0, variantId, receiptItemId, imei.trim(), importPrice, normalizedStatus);
                boolean ok = dao.insertInventory(item);
                setMsg(request.getSession(), ok ? "Đã thêm item kho." : "Thêm thất bại (IMEI trùng hoặc sai khóa ngoại).", ok ? "success" : "danger");
            }
        } catch (NumberFormatException e) {
            setMsg(request.getSession(), "Dữ liệu không hợp lệ (variant/receipt/import price).", "danger");
        }

        response.sendRedirect(request.getContextPath() + "/staffservlet?action=inventoryManagement");
    }

    private void handleUpdate(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String inventoryIdStr = request.getParameter("inventory_id");
        String variantIdStr = request.getParameter("variant_id");
        String receiptItemIdStr = request.getParameter("receipt_item_id");
        String imei = request.getParameter("imei");
        String importPriceStr = request.getParameter("import_price");
        String status = request.getParameter("status");

        try {
            int inventoryId = Integer.parseInt(inventoryIdStr);
            int variantId = Integer.parseInt(variantIdStr);
            int receiptItemId = Integer.parseInt(receiptItemIdStr);
            double importPrice = Double.parseDouble(importPriceStr);

            if (imei == null || imei.trim().isEmpty()) {
                setMsg(request.getSession(), "Vui lòng nhập IMEI.", "danger");
            } else {
                String normalizedStatus = (status == null || status.trim().isEmpty()) ? "IN_STOCK" : status.trim();
                InventoryItem item = new InventoryItem(inventoryId, variantId, receiptItemId, imei.trim(), importPrice, normalizedStatus);
                boolean ok = dao.updateInventory(item);
                setMsg(request.getSession(), ok ? "Đã cập nhật item kho." : "Cập nhật thất bại.", ok ? "success" : "danger");
            }
        } catch (NumberFormatException e) {
            setMsg(request.getSession(), "Dữ liệu không hợp lệ.", "danger");
        }

        response.sendRedirect(request.getContextPath() + "/staffservlet?action=inventoryManagement");
    }

    private InventoryItem getByIdSafe(HttpServletRequest request) {
        String idStr = request.getParameter("id");
        if (idStr == null || idStr.isBlank()) {
            return null;
        }
        try {
            int id = Integer.parseInt(idStr);
            return dao.getInventoryById(id);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    private void setMsg(HttpSession session, String msg, String type) {
        session.setAttribute("msg", msg);
        session.setAttribute("msgType", type);
    }
}

