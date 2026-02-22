/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.admin;

import dao.EmployeesDAO;
import dao.RoleDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import model.Employees;
import model.Role;

/**
 *
 * @author ASUS
 */
@WebServlet(name = "employeeServlet", urlPatterns = {"/employeeservlet"})
public class employeeServlet extends HttpServlet {

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
            out.println("<title>Servlet employeeServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet employeeServlet at " + request.getContextPath() + "</h1>");
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
        String page = "/pages/EmployeeManagementPage/employeeManagement.jsp";
        List<?> listData = null; // Dấu <?> cho phép gán bất kỳ List nào (Customer, Employee...)
        EmployeesDAO edao = new EmployeesDAO();
        RoleDAO rdao = new RoleDAO();
        if (action != null) {
            switch (action) {
                case "add":
                    page = "/pages/EmployeeManagementPage/addEmployee.jsp";
                    break;
                case "edit":
                    int id = Integer.parseInt(request.getParameter("id"));
                    Employees emp = edao.getEmployeeById(id);
                    List<Role> roles = rdao.getAllRole(); // Fetch all roles for the dropdown
                    request.setAttribute("employee", emp);
                    request.setAttribute("roleList", roles);
                    page = "/pages/EmployeeManagementPage/editEmployee.jsp";
                    break;
                case "delete":
                    int idD = Integer.parseInt(request.getParameter("id"));

                    // Hứng kết quả true/false từ DAO
                    boolean isDeleted = edao.deleteEmployee(idD);

                    // Xét điều kiện để gửi thông báo
                    if (isDeleted) {
                        request.setAttribute("successMessage", "Đã xóa nhân viên thành công!");
                    } else {
                        request.setAttribute("errorMessage", "Xóa thất bại! Nhân viên này đang dính líu đến dữ liệu khác trong hệ thống nên không thể xóa.");
                    }

                    page = "/pages/EmployeeManagementPage/employeeManagement.jsp";
                    listData = edao.getAllEmployeeses(); // Nhớ giữ dòng load lại list này nhé
                    break;
                case "detail":
                    int idDE = Integer.parseInt(request.getParameter("id"));
                    Employees employees = edao.getEmployeeById(idDE);
                    request.setAttribute("employee", employees);
                    page = "/pages/EmployeeManagementPage/detailEmployee.jsp";
                    break;
                case "all":
                    page = "/pages/EmployeeManagementPage/employeeManagement.jsp";
                    listData = edao.getAllEmployeeses();
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
        EmployeesDAO edao = new EmployeesDAO();
        RoleDAO rdao = new RoleDAO();
        if (action != null) {
            switch (action) {
                case "add":
                    // 1. Lấy dữ liệu từ Form Add Employee
                    String username = request.getParameter("username");
                    String fullName = request.getParameter("full_name");
                    String email = request.getParameter("email");
                    String password = request.getParameter("password");
                    String phone = request.getParameter("phone_number");
                    String status = request.getParameter("status");

                    // Lấy role_id từ <select> và ép kiểu sang int
                    int roleId = Integer.parseInt(request.getParameter("role_id"));

                    // 2. Khởi tạo đối tượng Employees (Model mới của bạn)
                    Employees newEmp = new Employees();
                    newEmp.setUsername(username);
                    newEmp.setFullName(fullName);
                    newEmp.setEmail(email);
                    newEmp.setPasswordHash(password); // Trong thực tế nên hash mật khẩu ở đây
                    newEmp.setPhoneNumber(phone);
                    newEmp.setRole(rdao.getRoleById(roleId));
                    newEmp.setStatus(status);

                    // 3. Gọi hàm insert từ EmployeesDAO
                    edao.insertEmployee(newEmp);
                    break;
                case "edit":
// 1. Lấy dữ liệu từ các input name trong Form Edit
                    int employeeId = Integer.parseInt(request.getParameter("employeeId"));
                    String fullNameE = request.getParameter("full_nameE"); // Thêm E
                    String emailE = request.getParameter("emailE");       // Thêm E
                    String phoneE = request.getParameter("phoneE");       // Đổi thành phoneE
                    String statusE = request.getParameter("statusE");     // Thêm E

                    // Lấy role_id từ select name="role_idE"
                    int roleIdE = Integer.parseInt(request.getParameter("role_idE")); // Thêm E

                    // Đóng gói vào object
                    Employees empUpdate = new Employees();
                    empUpdate.setEmployeeId(employeeId);
                    empUpdate.setFullName(fullNameE);
                    empUpdate.setEmail(emailE);
                    empUpdate.setPhoneNumber(phoneE);
                    empUpdate.setStatus(statusE);

                    Role r = new Role();
                    r.setRole_id(roleIdE);
                    empUpdate.setRole(r);

                    // Gọi DAO cập nhật
                    edao.updateEmployee(empUpdate);

                    break;
            }
        }
        response.sendRedirect("employeeservlet?action=all");
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
