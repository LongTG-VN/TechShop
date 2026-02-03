package controller.admin;

import dao.BrandDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Brand;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "brandServlet", urlPatterns = {"/brand"})
public class brandServlet extends HttpServlet {

    private final BrandDAO brandDAO = new BrandDAO();

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
            out.println("<title>Servlet adminServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet adminServlet at " + request.getContextPath() + "</h1>");
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
        String txtSearch = request.getParameter("txtSearch");
        String page = "/pages/BrandManagementPage/brandManagement.jsp";

        // Xử lý tìm kiếm
        if (txtSearch != null && !txtSearch.trim().isEmpty()) {
            txtSearch = txtSearch.trim();
            request.setAttribute("brandList", brandDAO.searchBrandsByName(txtSearch));
            request.setAttribute("searchValue", txtSearch);
        } else if ("detail".equals(action)) {
            int id = Integer.parseInt(request.getParameter("id"));
            request.setAttribute("brandDetail", brandDAO.getBrandById(id));
            request.setAttribute("brandList", brandDAO.getAllBrand());
        } else if ("edit".equals(action)) {
            int id = Integer.parseInt(request.getParameter("id"));
            request.setAttribute("brandToEdit", brandDAO.getBrandById(id));
            request.setAttribute("brandList", brandDAO.getAllBrand());
        } else if ("add".equals(action)) {
            request.setAttribute("showAddForm", true);
            request.setAttribute("brandList", brandDAO.getAllBrand());
        } else if ("softDelete".equals(action)) {
            int id = Integer.parseInt(request.getParameter("id"));
            brandDAO.softDeleteBrand(id);
            response.sendRedirect("brand");
            return;
        } else if ("delete".equals(action)) {
            int id = Integer.parseInt(request.getParameter("id"));
            brandDAO.hardDeleteBrand(id);
            response.sendRedirect("brand");
            return;
        } else {
            // Mặc định load tất cả
            request.setAttribute("brandList", brandDAO.getAllBrand());
        }

        request.setAttribute("contentPage", page);
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
        request.setCharacterEncoding("UTF-8");
        String action = request.getParameter("action");

        if ("addProcess".equals(action)) {
            brandDAO.insertBrand(new Brand(request.getParameter("brandName"), request.getParameter("isActive") != null));
        } else if ("updateProcess".equals(action)) {
            int id = Integer.parseInt(request.getParameter("brandId"));
            brandDAO.updateBrand(new Brand(id, request.getParameter("brandName"), request.getParameter("isActive") != null));
        }
        response.sendRedirect("adminservlet?action=brandManagement");
    }
}
