/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.staff;

import dao.BrandDAO;
import dao.CategoryDAO;
import dao.ProductDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.List;
import model.Product;

/**
 *
 * @author CaoTram
 */
@WebServlet(name = "productServlet", urlPatterns = {"/productServlet"})
public class productServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet productServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet productServlet at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        String page = "/pages/ProductManagementPage/productManagement.jsp";
        List<Product> listData = null;

        ProductDAO pdao = new ProductDAO();
        CategoryDAO cdao = new CategoryDAO();
        BrandDAO bdao = new BrandDAO();

        if (action != null) {
            switch (action) {
                case "add":
                    request.setAttribute("categories", cdao.getAllCategory());
                    request.setAttribute("brands", bdao.getAllBrand());
                    page = "/pages/ProductManagementPage/addProduct.jsp";
                    break;
                case "edit":
                    int idEdit = Integer.parseInt(request.getParameter("id"));
                    request.setAttribute("product", pdao.getProductById(idEdit));
                    request.setAttribute("categories", cdao.getAllCategory());
                    request.setAttribute("brands", bdao.getAllBrand());
                    page = "/pages/ProductManagementPage/editProduct.jsp";
                    break;
                case "detail":
                    int idDetail = Integer.parseInt(request.getParameter("id"));
                    request.setAttribute("product", pdao.getProductById(idDetail));
                    page = "/pages/ProductManagementPage/detailProduct.jsp";
                    break;
                case "delete":
                    int idDel = Integer.parseInt(request.getParameter("id"));
                    request.setAttribute("product", pdao.getProductById(idDel));
                    request.setAttribute("orderCount", pdao.countProductInOrderDetails(idDel));
                    page = "/pages/ProductManagementPage/deleteProduct.jsp";
                    break;
                case "all":
                    request.setAttribute("categories", cdao.getAllCategory());
                    request.setAttribute("brands", bdao.getAllBrand());
                    listData = pdao.getAllProduct();
                    break;
            }
        }
        request.setAttribute("contentPage", page);
        request.setAttribute("listdata", listData);
        request.getRequestDispatcher("/template/staffTemplate.jsp").forward(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        ProductDAO pdao = new ProductDAO();
        HttpSession session = request.getSession();

        if ("add".equals(action)) {
            String name = request.getParameter("name").trim();
            int categoryId = Integer.parseInt(request.getParameter("categoryId"));
            int brandId = Integer.parseInt(request.getParameter("brandId"));
            String description = request.getParameter("description");
            String status = request.getParameter("status");

            if (pdao.isProductDuplicate(name, categoryId, brandId, 0)) {
                session.setAttribute("msg", "Error: This combination of Name, Category and Brand already exists!");
                session.setAttribute("msgType", "danger");
                response.sendRedirect("productServlet?action=add");
                return;
            }

            Product p = new Product();
            p.setName(name);
            p.setCategoryId(categoryId);
            p.setBrandId(brandId);
            p.setDescription(description);
            p.setStatus(status);
            p.setCreatedBy((Integer) session.getAttribute("userId"));

            pdao.insertProduct(p);
            session.setAttribute("msg", "Product added successfully!");
            session.setAttribute("msgType", "success");

        } else if ("update".equals(action)) {
            int id = Integer.parseInt(request.getParameter("productId"));
            String name = request.getParameter("name").trim();
            int categoryId = Integer.parseInt(request.getParameter("categoryId"));
            int brandId = Integer.parseInt(request.getParameter("brandId"));
            String description = request.getParameter("description");
            String status = request.getParameter("status");

            if (pdao.isProductDuplicate(name, categoryId, brandId, id)) {
                session.setAttribute("msg", "Error: This combination is already used by another product!");
                session.setAttribute("msgType", "danger");
                response.sendRedirect("productServlet?action=edit&id=" + id);
                return;
            }

            // KIỂM TRA THAY ĐỔI
            Product cur = pdao.getProductById(id);
            if (cur.getName().equals(name) && cur.getCategoryId() == categoryId
                    && cur.getBrandId() == brandId && cur.getStatus().equals(status)
                    && cur.getDescription().equals(description)) {

                session.setAttribute("msg", "NO CHANGES DETECTED.");
                session.setAttribute("msgType", "danger");
                response.sendRedirect("productServlet?action=edit&id=" + id);
                return;
            }

            Product p = new Product();
            p.setProductId(id);
            p.setName(name);
            p.setCategoryId(categoryId);
            p.setBrandId(brandId);
            p.setDescription(description);
            p.setStatus(status);
            p.setUpdatedBy((Integer) session.getAttribute("userId"));

            pdao.updateProduct(p);
            session.setAttribute("msg", "Product updated successfully!");
            session.setAttribute("msgType", "success");

        } else if ("delete".equals(action)) {
            int id = Integer.parseInt(request.getParameter("productId"));
            if (pdao.countProductInOrderDetails(id) > 0) {
                pdao.softDeleteProduct(id);
                session.setAttribute("msg", "Product has orders. Set to INACTIVE.");
            } else {
                pdao.deleteProduct(id);
                session.setAttribute("msg", "Product deleted successfully!");
            }
            session.setAttribute("msgType", "success");
        }

        response.sendRedirect("productServlet?action=all");
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
