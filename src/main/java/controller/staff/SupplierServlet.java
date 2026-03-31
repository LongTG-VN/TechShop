/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.staff;

import dao.SupplierDAO;
import java.io.IOException;
import java.util.Locale;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Supplier;

@WebServlet(name = "SupplierServlet", urlPatterns = {"/supplier"})
public class SupplierServlet extends HttpServlet {

    private SupplierDAO dao;

    @Override
    public void init() {
        dao = new SupplierDAO(); // một DAO cho cả servlet
    }

    private void redirectToSupplierManagement(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.sendRedirect(request.getContextPath() + "/staffservlet?action=supplierManagement"); // về list NCC
    }

    private int parseIntSafe(String s, int defaultValue) { // id từ query, sai format → default
        if (s == null) {
            return defaultValue;
        }
        try {
            return Integer.parseInt(s.trim());
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    private String trimToNull(String s) { // sau trim → null
        if (s == null) {
            return null;
        }
        String t = s.trim();
        return t.isEmpty() ? null : t;
    }

    private String normalizeSupplierName(String raw) { // trim + gộp whitespace
        if (raw == null) {
            return "";
        }
        return raw.trim().replaceAll("\\s+", " ");
    }

    private String normalizePhoneDigits(String raw) { // chỉ giữ số
        if (raw == null) {
            return "";
        }
        return raw.replaceAll("\\D", "");
    }

    private String normalizeAddress(String raw) { // trim + gộp space, giống tên
        String t = trimToNull(raw);
        if (t == null) {
            return null;
        }
        return t.replaceAll("\\s+", " ");
    }

    private String normalizeEmail(String raw) { // trim + lowercase, rỗng → null
        if (raw == null) {
            return null;
        }
        String t = raw.trim();
        return t.isEmpty() ? null : t.toLowerCase(Locale.ROOT);
    }

    private boolean isValidEmailOrEmpty(String emailNormalized) { // null/"" ok; có thì regex
        if (emailNormalized == null || emailNormalized.isEmpty()) {
            return true;
        }
        return emailNormalized.matches("^[a-z0-9+_.-]+@[a-z0-9.-]+\\.[a-z]{2,}$");
    }

    private boolean isValidSupplierName(String nameNormalized) { // khớp filter JSP
        if (nameNormalized == null) {
            return false;
        }
        if (nameNormalized.isEmpty()) {
            return false;
        }
        return nameNormalized.matches("^[a-zA-Z0-9À-ỹ\\s&.-]+$");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setCharacterEncoding("UTF-8");
        request.setCharacterEncoding("UTF-8");

        String action = request.getParameter("action");

        if (action == null || action.isBlank()) {
            redirectToSupplierManagement(request, response);
            return;
        }

        // add/view/edit → forward JSP qua staffTemplate; deactivate/restore/delete → redirect + flash
        String page = "/pages/SupplierManagementPage/supplierManagement.jsp";
        switch (action) {
            case "add":
                page = "/pages/SupplierManagementPage/addSupplier.jsp";
                break;
            case "view": {
                int id = parseIntSafe(request.getParameter("id"), 0); // ?id=
                if (id > 0) {
                    Supplier s = (Supplier) dao.getSupplierById(id);
                    request.setAttribute("supplier", s);
                }
                page = "/pages/SupplierManagementPage/viewSupplier.jsp";
                break;
            }
            case "edit": {
                int id = parseIntSafe(request.getParameter("id"), 0);
                if (id > 0) {
                    Supplier s = (Supplier) dao.getSupplierById(id);
                    request.setAttribute("supplier", s);
                }
                page = "/pages/SupplierManagementPage/editSupplier.jsp";
                break;
            }
            case "deactivate": { // soft: is_active = 0
                int id = parseIntSafe(request.getParameter("id"), 0);
                if (id > 0) {
                    if (!dao.hasConnection()) {
                        setMsg(request.getSession(), "Cannot connect to the database. Please try again later.", "danger");
                    } else if (dao.deactivateSupplier(id)) {
                        setMsg(request.getSession(), "Supplier has been deactivated.", "success");
                    } else {
                        setMsg(request.getSession(), "Could not deactivate (supplier not found or system error).", "danger");
                    }
                } else {
                    setMsg(request.getSession(), "Invalid id. Please try again.", "danger");
                }
                redirectToSupplierManagement(request, response);
                return;
            }
            case "restore": { // is_active = 1
                int id = parseIntSafe(request.getParameter("id"), 0);
                if (id > 0) {
                    if (!dao.hasConnection()) {
                        setMsg(request.getSession(), "Cannot connect to the database. Please try again later.", "danger");
                    } else if (dao.restoreSupplier(id)) {
                        setMsg(request.getSession(), "Supplier has been reactivated.", "success");
                    } else {
                        setMsg(request.getSession(), "Could not reactivate (supplier not found or system error).", "danger");
                    }
                } else {
                    setMsg(request.getSession(), "Invalid id. Please try again.", "danger");
                }
                redirectToSupplierManagement(request, response);
                return;
            }
            case "delete": { // hard delete nếu không có phiếu nhập
                int id = parseIntSafe(request.getParameter("id"), 0);
                if (id > 0) {
                    if (!dao.hasConnection()) {
                        setMsg(request.getSession(), "Cannot connect to the database. Please try again later.", "danger");
                    } else if (dao.deleteSupplierIfNoReferences(id)) {
                        setMsg(request.getSession(), "Supplier deleted.", "success");
                    } else {
                        setMsg(request.getSession(), "Cannot delete: supplier is used on import receipts or does not exist.", "danger");
                    }
                } else {
                    setMsg(request.getSession(), "Invalid id. Please try again.", "danger");
                }
                redirectToSupplierManagement(request, response);
                return;
            }
            default: {
                redirectToSupplierManagement(request, response);
                return;
            }
        }

        request.setAttribute("contentPage", page); // staffTemplate include JSP này
        request.getRequestDispatcher("/template/staffTemplate.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setCharacterEncoding("UTF-8");
        request.setCharacterEncoding("UTF-8");

        String action = request.getParameter("action");

        if (action == null || action.isBlank()) {
            redirectToSupplierManagement(request, response);
            return;
        }

        switch (action) {
            case "add": { // form add → insert; thứ tự check: tên, SĐT, email, trùng mail, trùng tên+địa chỉ
                String name = normalizeSupplierName(request.getParameter("supplier_name"));
                String phone = normalizePhoneDigits(request.getParameter("phone"));
                String email = normalizeEmail(request.getParameter("email"));
                String address = normalizeAddress(request.getParameter("address"));
                String isActive = request.getParameter("is_active");

                if (!name.isEmpty() && phone.length() == 10) {
                    if (!dao.hasConnection()) {
                        setMsg(request.getSession(), "Cannot connect to the database. Please try again later.", "danger");
                    } else if (!isValidSupplierName(name)) {
                        setMsg(request.getSession(), "Invalid name. Letters, digits, Vietnamese, spaces, &, . and - only.", "danger");
                    } else if (!phone.matches("[0-9]{10}")) {
                        setMsg(request.getSession(), "Phone must be exactly 10 digits.", "danger");
                    } else if (!isValidEmailOrEmpty(email)) {
                        setMsg(request.getSession(), "Invalid email format.", "danger");
                    } else if (email != null && dao.existsSupplierEmail(email, 0)) {
                        setMsg(request.getSession(), "This email is already in use by another supplier.", "danger");
                    } else if (dao.existsSameNameAndAddress(name, address, 0)) {
                        setMsg(request.getSession(), "Another supplier already has the same name and address. Same name is allowed if the address differs.", "danger");
                    } else {
                        Supplier s = new Supplier(0, name, phone, email, address, "1".equals(isActive));
                        if (dao.insertSupplier(s)) {
                            setMsg(request.getSession(), "Supplier added.", "success");
                        } else {
                            setMsg(request.getSession(), "Could not add supplier. Please try again.", "danger");
                        }
                    }
                } else {
                    setMsg(request.getSession(), "Please enter name and phone.", "danger");
                }
                break;
            }
            case "update": { // giống add + supplier_id; existsEmail/NameAddress loại trừ id hiện tại
                String idStr = request.getParameter("supplier_id");
                String name = normalizeSupplierName(request.getParameter("supplier_name"));
                String phone = normalizePhoneDigits(request.getParameter("phone"));
                String email = normalizeEmail(request.getParameter("email"));
                String address = normalizeAddress(request.getParameter("address"));
                String isActive = request.getParameter("is_active");

                if (idStr != null && !idStr.isEmpty() && !name.isEmpty() && phone.length() == 10) {
                    int id = parseIntSafe(idStr, 0);
                    if (id <= 0) {
                        setMsg(request.getSession(), "Invalid supplier id.", "danger");
                    } else if (!dao.hasConnection()) {
                        setMsg(request.getSession(), "Cannot connect to the database. Please try again later.", "danger");
                    } else if (!isValidSupplierName(name)) {
                        setMsg(request.getSession(), "Invalid name. Letters, digits, Vietnamese, spaces, &, . and - only.", "danger");
                    } else if (!phone.matches("[0-9]{10}")) {
                        setMsg(request.getSession(), "Phone must be exactly 10 digits.", "danger");
                    } else if (!isValidEmailOrEmpty(email)) {
                        setMsg(request.getSession(), "Invalid email format.", "danger");
                    } else if (email != null && dao.existsSupplierEmail(email, id)) {
                        setMsg(request.getSession(), "This email is already in use by another supplier.", "danger");
                    } else if (dao.existsSameNameAndAddress(name, address, id)) {
                        setMsg(request.getSession(), "Another supplier already has the same name and address. Same name is allowed if the address differs.", "danger");
                    } else {
                        Supplier s = new Supplier(id, name, phone, email, address, "1".equals(isActive));
                        if (dao.updateSupplier(s)) {
                            setMsg(request.getSession(), "Supplier updated.", "success");
                        } else {
                            setMsg(request.getSession(), "Could not update (supplier not found or system error).", "danger");
                        }
                    }
                } else {
                    setMsg(request.getSession(), "Please fill in all required fields.", "danger");
                }
                break;
            }
            default: {
                break;
            }
        }

        redirectToSupplierManagement(request, response); // post xong luôn về list (flash msg session)
    }

    private void setMsg(HttpSession session, String msg, String type) { // danger / success cho toast
        session.setAttribute("msg", msg);
        session.setAttribute("msgType", type);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }
}
