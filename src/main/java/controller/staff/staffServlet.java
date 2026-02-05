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
import java.util.List;

/**
 *
 * @author ASUS
 */
@WebServlet(name = "staffServlet", urlPatterns = {"/staffservlet"})
public class staffServlet extends HttpServlet {

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
            out.println("<title>Servlet staffServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet staffServlet at " + request.getContextPath() + "</h1>");
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
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");

        // 2. Lấy tham số 'action' từ URL (ví dụ: adminservlet?action=dashboard)
        String action = request.getParameter("action");
        String page = "/pages/dashboardPage.jsp"; // Trang mặc định khi mới vào

        List<?> listData = null; // Dấu <?> cho phép gán bất kỳ List nào (Customer, Employee...)
        // 3. Logic điều hướng (Switch-case sẽ sạch sẽ hơn if-else)
        if (action != null) {
            switch (action) {
                case "dashboard":
                    page = "/pages/dashboardPage.jsp";
                    break;
                case "processOrderManagement":
                    page = "/pages/processOrderManagement.jsp"; // Bạn cần tạo file này
//                    listData = new CustomerDAO().getAllCustomer();
                    break;
                case "supplierManagement":
                    page = "/pages/SupplierManagementPage/supplierManagement.jsp"; // Bạn cần tạo file này
//                    listData = new CustomerDAO().getAllCustomer();
                    break;
                case "inventoryManagement":
                    page = "/pages/InventoryManagementPage/inventoryManagement.jsp"; // Bạn cần tạo file này
//                    listData = new CustomerDAO().getAllCustomer();
                    break;
                case "productManagement":
                    page = "/pages/ProductManagementPage/productManagement.jsp";
                    ProductDAO productdao = new ProductDAO();
                    CategoryDAO ccdao = new CategoryDAO(); 
                    BrandDAO bdao = new BrandDAO();      

                    request.setAttribute("categories", ccdao.getAllCategory()); 
                    request.setAttribute("brands", bdao.getAllBrand());     

                    listData = productdao.getAllProduct();
                    break;
                case "reviewManagement":
                    page = "/pages/ReviewManagementPage/reviewManagement.jsp"; // Bạn cần tạo file này
//                    listData = new CustomerDAO().getAllCustomer();
                    break;
                default:
                    page = "/pages/dashboardPage.jsp";
            }
        }

        // 4. Đẩy đường dẫn trang con vào Attribute để Template include
        request.setAttribute("contentPage", page);
        request.setAttribute("listdata", listData);

        // 5. Forward đến Template duy nhất
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
        processRequest(request, response);
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
