/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.admin;

import dao.CustomerDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.util.List;
import model.Customer;

/**
 *
 * @author ASUS
 */
@WebServlet(name = "customerServlet", urlPatterns = {"/customerservlet"})
public class customerServlet extends HttpServlet {

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
            out.println("<title>Servlet customerServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet customerServlet at " + request.getContextPath() + "</h1>");
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
        String page = "/pages/customerManagement.jsp"; // Mặc định là bảng danh sách
        List<?> listData = null; // Dấu <?> cho phép gán bất kỳ List nào (Customer, Employee...)
        CustomerDAO cdao = new CustomerDAO();

        if (action != null) {
            switch (action) {
                case "add":
                    page = "/pages/addCustomer.jsp";
                    break;
                case "edit":
                    int idC = Integer.parseInt(request.getParameter("id"));
                    Customer customer = cdao.getCustomerById(idC);
                    request.setAttribute("customer", customer);
                    page = "/pages/editCustomer.jsp";
                    break;
                case "delete":
                    int idD = Integer.parseInt(request.getParameter("id"));
                    Customer customerD = cdao.getCustomerById(idD);
                    cdao.deleteCustomer(idD);
                    page = "/pages/customerManagement.jsp";
                    listData = new CustomerDAO().getAllCustomer();
                    break;
                case "all":
                    // Code lấy list dữ liệu từ DB ở đây
                    page = "/pages/customerManagement.jsp";
                    listData = new CustomerDAO().getAllCustomer();
                    break;
            }
        }

        // Đẩy đường dẫn trang con vào attribute
        request.setAttribute("contentPage", page);
        request.setAttribute("listdata", listData);

        // Forward về template chính để giữ Sidebar/Header
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
        CustomerDAO cdao = new CustomerDAO();
        if (action != null) {
            switch (action) {
                case "add":
                    // 2. Lấy dữ liệu từ các thẻ <input> (theo đúng 'name' bạn đặt ở Form)
                    String username = request.getParameter("username");
                    String fullName = request.getParameter("full_name");
                    String email = request.getParameter("email");
                    String password = request.getParameter("password");
                    String phone = request.getParameter("phone_number");
                    String status = request.getParameter("status");
                    cdao.addCustomer(new Customer(0, username, password, fullName, email, phone, status, LocalDateTime.now()));
                    // 3. Thực hiện lưu vào Database qua lớp DAO
                    // Customer newCustomer = new Customer(username, password, fullName, email, phone, status);
                    // customerDAO.insert(newCustomer);
                    // 4. QUAN TRỌNG: Điều hướng ngược lại trang danh sách khách hàng
                    // Sử dụng sendRedirect để trình duyệt gọi lại doGet của Servlet
                    break;
                case "edit":
                    // 1. Lấy ID (Cực kỳ quan trọng để Update)
                    int id = Integer.parseInt(request.getParameter("customerID"));

                    // 2. Lấy các thông tin mới từ Form
                    String usernameE = request.getParameter("username");
                    String fullNameE = request.getParameter("full_name");
                    String emailE = request.getParameter("email");
                    String phoneE = request.getParameter("phone_number");
                    String statusE = request.getParameter("status");

                    // 3. Thực hiện Update
                    // Lưu ý: Thường không cập nhật Password và CreatedAt ở đây để bảo mật
                    Customer updatedCus = cdao.getCustomerById(id);
                    updatedCus.setCustomerID(id);
                    updatedCus.setUserName(usernameE);
                    updatedCus.setFullname(fullNameE);
                    updatedCus.setEmail(emailE);
                    updatedCus.setPhoneNumber(phoneE);
                    updatedCus.setStatus(statusE);

                    cdao.updateCustomer(updatedCus); // Gọi hàm update trong DAO
                    // 4. Quay lại trang danh sách
                    break;
            }
        }
        response.sendRedirect("customerservlet?action=all");
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
