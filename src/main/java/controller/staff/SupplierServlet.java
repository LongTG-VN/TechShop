/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.staff;

import dao.SupplierDAO;
import java.io.IOException;
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
        dao = new SupplierDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setCharacterEncoding("UTF-8");
        request.setCharacterEncoding("UTF-8");

        String action = request.getParameter("action");

        if ("add".equals(action)) {
            // Show add form (inside staff template)
            request.setAttribute("contentPage", "/pages/SupplierManagementPage/addSupplier.jsp");
            request.getRequestDispatcher("/template/staffTemplate.jsp").forward(request, response);
            return;
        }

        if ("view".equals(action)) {
            String idStr = request.getParameter("id");
            if (idStr != null && !idStr.isEmpty()) {
                try {
                    int id = Integer.parseInt(idStr);
                    Supplier s = (Supplier) dao.getSupplierById(id);
                    request.setAttribute("supplier", s);
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
            }
            request.setAttribute("contentPage", "/pages/SupplierManagementPage/viewSupplier.jsp");
            request.getRequestDispatcher("/template/staffTemplate.jsp").forward(request, response);
            return;
        }

        if ("edit".equals(action)) {
            String idStr = request.getParameter("id");
            if (idStr != null && !idStr.isEmpty()) {
                try {
                    int id = Integer.parseInt(idStr);
                    Supplier s = (Supplier) dao.getSupplierById(id);
                    request.setAttribute("supplier", s);
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
            }
            request.setAttribute("contentPage", "/pages/SupplierManagementPage/editSupplier.jsp");
            request.getRequestDispatcher("/template/staffTemplate.jsp").forward(request, response);
            return;
        }

        if ("deactivate".equals(action)) {
            String idStr = request.getParameter("id");
            if (idStr != null && !idStr.isEmpty()) {
                try {
                    int id = Integer.parseInt(idStr);
                    dao.deactivateSupplier(id);
                    setMsg(request.getSession(), "Đã dừng hợp tác nhà cung cấp.", "success");
                } catch (NumberFormatException e) {
                    setMsg(request.getSession(), "Lỗi: ID không hợp lệ.", "danger");
                }
            }
            response.sendRedirect(request.getContextPath() + "/staffservlet?action=supplierManagement");
            return;
        }

        if ("restore".equals(action)) {
            String idStr = request.getParameter("id");
            if (idStr != null && !idStr.isEmpty()) {
                try {
                    int id = Integer.parseInt(idStr);
                    dao.restoreSupplier(id);
                    setMsg(request.getSession(), "Đã mở lại hợp tác nhà cung cấp.", "success");
                } catch (NumberFormatException e) {
                    setMsg(request.getSession(), "Lỗi: ID không hợp lệ.", "danger");
                }
            }
            response.sendRedirect(request.getContextPath() + "/staffservlet?action=supplierManagement");
            return;
        }

        // Default: redirect to list
        response.sendRedirect(request.getContextPath() + "/staffservlet?action=supplierManagement");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setCharacterEncoding("UTF-8");
        request.setCharacterEncoding("UTF-8");

        String action = request.getParameter("action");

        if ("add".equals(action)) {
            String name = request.getParameter("supplier_name");
            String phone = request.getParameter("phone");
            String isActive = request.getParameter("is_active");

            if (name != null && !name.trim().isEmpty() && phone != null && !phone.trim().isEmpty()) {
                if (!phone.matches("[0-9]{10}")) {
                    setMsg(request.getSession(), "Số điện thoại phải đúng 10 chữ số.", "danger");
                } else {
                    Supplier s = new Supplier(0, name.trim(), phone.trim(), "1".equals(isActive));
                    dao.insertSupplier(s);
                    setMsg(request.getSession(), "Đã thêm nhà cung cấp thành công.", "success");
                }
            } else {
                setMsg(request.getSession(), "Vui lòng điền đầy đủ tên và số điện thoại.", "danger");
            }
            response.sendRedirect(request.getContextPath() + "/staffservlet?action=supplierManagement");
            return;
        }

        if ("update".equals(action)) {
            String idStr = request.getParameter("supplier_id");
            String name = request.getParameter("supplier_name");
            String phone = request.getParameter("phone");
            String isActive = request.getParameter("is_active");

            if (idStr != null && !idStr.isEmpty() && name != null && !name.trim().isEmpty() && phone != null && !phone.trim().isEmpty()) {
                if (!phone.matches("[0-9]{10}")) {
                    setMsg(request.getSession(), "Số điện thoại phải đúng 10 chữ số.", "danger");
                } else {
                    try {
                        int id = Integer.parseInt(idStr);
                        Supplier s = new Supplier(id, name.trim(), phone.trim(), "1".equals(isActive));
                        dao.updateSupplier(s);
                        setMsg(request.getSession(), "Đã cập nhật nhà cung cấp thành công.", "success");
                    } catch (NumberFormatException e) {
                        setMsg(request.getSession(), "Lỗi: ID không hợp lệ.", "danger");
                    }
                }
            } else {
                setMsg(request.getSession(), "Vui lòng điền đầy đủ thông tin.", "danger");
            }
            response.sendRedirect(request.getContextPath() + "/staffservlet?action=supplierManagement");
            return;
        }

        response.sendRedirect(request.getContextPath() + "/staffservlet?action=supplierManagement");
    }

    private void setMsg(HttpSession session, String msg, String type) {
        session.setAttribute("msg", msg);
        session.setAttribute("msgType", type);
    }
}
