/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.admin;

import dao.CategoryDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import model.Category;

/**
 *
 * @author CaoTram
 */
@WebServlet(name = "categoryServlet", urlPatterns = {"/categoryServlet"})
public class categoryServlet extends HttpServlet {

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
            out.println("<title>Servlet categoryServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet categoryServlet at " + request.getContextPath() + "</h1>");
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
        String page = "/pages/CategoryManagementPage/categoryManagement.jsp";
        List<?> listData = null;
        CategoryDAO cdao = new CategoryDAO();

        if (action != null) {
            switch (action) {
                case "add":
                    page = "/pages/CategoryManagementPage/addCategory.jsp";
                    break;
                case "delete":
                    int idDelete = Integer.parseInt(request.getParameter("id"));
                    cdao.deleteCategory(idDelete);
                    response.sendRedirect("categoryServlet?action=all");
                    return;
                case "edit":
                    int idEdit = Integer.parseInt(request.getParameter("id"));
                    Category c = cdao.getCategoryById(idEdit);
                    request.setAttribute("category", c);
                    page = "/pages/CategoryManagementPage/editCategory.jsp";
                    break;
                case "detail":
                    int idDetail = Integer.parseInt(request.getParameter("id"));
                    Category cd = cdao.getCategoryById(idDetail);
                    request.setAttribute("category", cd);
                    page = "/pages/CategoryManagementPage/detailCategory.jsp";
                    break;
                case "all":
                    page = "/pages/CategoryManagementPage/categoryManagement.jsp";
                    listData = cdao.getAllCategory();
                    break;

            }
        }

        request.setAttribute("contentPage", page);
        request.setAttribute("listdata", listData);
        request.getRequestDispatcher("/template/adminTemplate.jsp").forward(request, response);
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
        CategoryDAO cdao = new CategoryDAO();

        if ("add".equals(action)) {
            String name = request.getParameter("categoryName");
            String statusRaw = request.getParameter("isActive");
            boolean status = "1".equals(statusRaw);
            cdao.insertCategory(name, status);
        }
        if ("update".equals(action)) {
            int id = Integer.parseInt(request.getParameter("categoryId"));
            String name = request.getParameter("categoryName");
            boolean status = "1".equals(request.getParameter("isActive"));

            Category c = new Category(id, name, status);
            cdao.updateCategory(c);
        }

        response.sendRedirect("categoryServlet?action=all");
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
