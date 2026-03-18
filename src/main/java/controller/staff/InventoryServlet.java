/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.staff;

import dao.ImportReceiptItemDAO;
import dao.InventoryItemDAO;
import dao.ProductVariantDAO;
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

    private void redirectToInventoryManagement(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.sendRedirect(request.getContextPath() + "/staffservlet?action=inventoryManagement");
    }

    private int parseIntSafe(String s, int defaultValue) {
        if (s == null) {
            return defaultValue;
        }
        try {
            return Integer.parseInt(s.trim());
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setCharacterEncoding("UTF-8");
        request.setCharacterEncoding("UTF-8");

        String action = request.getParameter("action");
        if (action == null) {
            redirectToInventoryManagement(request, response);
            return;
        }

        String page = "/pages/InventoryManagementPage/inventoryManagement.jsp";
        switch (action) {
            case "add": {
                int receiptItemId = parseIntSafe(request.getParameter("receipt_item_id"), 0);
                if (receiptItemId > 0) {
                    request.setAttribute("receiptItem", new ImportReceiptItemDAO().getItemById(receiptItemId));
                }
                request.setAttribute("listVariants", new ProductVariantDAO().getAllVariant());
                page = "/pages/InventoryManagementPage/addInventory.jsp";
                break;
            }
            case "view": {
                InventoryItem item = getByIdSafe(request);
                request.setAttribute("inventoryItem", item);
                page = "/pages/InventoryManagementPage/detailInventory.jsp";
                break;
            }
            case "edit": {
                InventoryItem item = getByIdSafe(request);
                request.setAttribute("inventoryItem", item);
                request.setAttribute("listVariants", new ProductVariantDAO().getAllVariant());
                page = "/pages/InventoryManagementPage/editInventory.jsp";
                break;
            }
            case "delete": {
                boolean ok = false;
                int id = parseIntSafe(request.getParameter("id"), 0);
                if (id > 0) {
                    ok = dao.deleteInventory(id);
                }
                if (ok) {
                    setMsg(request.getSession(), "Inventory item deleted.", "success");
                } else {
                    setMsg(request.getSession(), "Delete failed (invalid ID or item in use).", "danger");
                }
                redirectToInventoryManagement(request, response);
                return;
            }
            default: {
                redirectToInventoryManagement(request, response);
                return;
            }
        }

        request.setAttribute("contentPage", page);
        request.getRequestDispatcher("/template/staffTemplate.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setCharacterEncoding("UTF-8");
        request.setCharacterEncoding("UTF-8");

        String action = request.getParameter("action");
        if (action == null) {
            redirectToInventoryManagement(request, response);
            return;
        }

        switch (action) {
            case "add": {
                String receiptItemIdStr = request.getParameter("receipt_item_id");
                String imei = request.getParameter("imei");
                String importPriceStr = request.getParameter("import_price");
                String status = request.getParameter("status");

                try {
                    int variantId = parseIntSafe(request.getParameter("variant_id"), 0);
                    int receiptItemId;
                    if (receiptItemIdStr != null && !receiptItemIdStr.isBlank()) {
                        receiptItemId = Integer.parseInt(receiptItemIdStr);
                    } else {
                        receiptItemId = new ImportReceiptItemDAO().getFirstReceiptItemId();
                        if (receiptItemId == 0) {
                            setMsg(request.getSession(), "No receipt item available. Create an import receipt first.", "danger");
                            redirectToInventoryManagement(request, response);
                            return;
                        }
                    }
                    double importPrice = Double.parseDouble(importPriceStr != null ? importPriceStr : "0");
                    if (imei == null || imei.trim().isEmpty()) {
                        setMsg(request.getSession(), "Please enter IMEI.", "danger");
                    } else {
                        String normalizedStatus = (status == null || status.trim().isEmpty()) ? "IN_STOCK" : status.trim();
                        InventoryItem item = new InventoryItem(0, variantId, receiptItemId, imei.trim(), importPrice, normalizedStatus);
                        boolean ok = dao.insertInventory(item);
                        if (ok) {
                            setMsg(request.getSession(), "Item added.", "success");
                        } else {
                            setMsg(request.getSession(), "Add failed (duplicate IMEI or invalid data).", "danger");
                        }
                    }
                } catch (NumberFormatException e) {
                    setMsg(request.getSession(), "Invalid data (variant or import price).", "danger");
                }

                redirectToInventoryManagement(request, response);
                break;
            }
            case "update": {
                String imei = request.getParameter("imei");
                String importPriceStr = request.getParameter("import_price");
                String status = request.getParameter("status");

                try {
                    int inventoryId = parseIntSafe(request.getParameter("inventory_id"), 0);
                    InventoryItem existing = dao.getInventoryById(inventoryId);
                    if (existing == null) {
                        setMsg(request.getSession(), "Item not found.", "danger");
                        redirectToInventoryManagement(request, response);
                        return;
                    }
                    int variantId = parseIntSafe(request.getParameter("variant_id"), 0);
                    double importPrice = Double.parseDouble(importPriceStr != null ? importPriceStr : "0");

                    if (imei == null || imei.trim().isEmpty()) {
                        setMsg(request.getSession(), "Please enter IMEI.", "danger");
                    } else {
                        String normalizedStatus = (status == null || status.trim().isEmpty()) ? "IN_STOCK" : status.trim();
                        InventoryItem item = new InventoryItem(inventoryId, variantId, existing.getReceipt_item_id(), imei.trim(), importPrice, normalizedStatus);
                        boolean ok = dao.updateInventory(item);
                        if (ok) {
                            setMsg(request.getSession(), "Item updated.", "success");
                        } else {
                            setMsg(request.getSession(), "Update failed.", "danger");
                        }
                    }
                } catch (NumberFormatException e) {
                    setMsg(request.getSession(), "Invalid data.", "danger");
                }

                redirectToInventoryManagement(request, response);
                break;
            }
            default:
                redirectToInventoryManagement(request, response);
                break;
        }
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

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }
}
