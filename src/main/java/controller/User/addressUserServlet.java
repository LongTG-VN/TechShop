/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.User;

import dao.CustomerAddressDAO;
import dao.CustomerDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import model.Customer;
import model.CustomerAddress;

/**
 *
 * @author ASUS
 */
@WebServlet(name = "addressUserServlet", urlPatterns = {"/addresssuserservlet"})
public class addressUserServlet extends HttpServlet {

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
            out.println("<title>Servlet addressUserServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet addressUserServlet at " + request.getContextPath() + "</h1>");
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

        // 1. Lấy action và xử lý null/empty ngay từ đầu
        String action = request.getParameter("action");
        if (action == null) {
            action = "default";
        }

        // 2. Lấy currentUserId từ cookie
        int currentUserId = -1;
        jakarta.servlet.http.Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (jakarta.servlet.http.Cookie cookie : cookies) {
                if (cookie.getName().equals("cookieID")) {
                    currentUserId = Integer.parseInt(cookie.getValue());
                    break;
                }
            }
        }

        CustomerDAO cdao = new CustomerDAO();
        CustomerAddressDAO cadd = new CustomerAddressDAO();
        String page = "/pages/DashboardPage/userDashboard.jsp";

        // 3. Xử lý logic theo action
        switch (action) {
            case "add":
                page = "/pages/AddressManagementPage/addAddress.jsp";
                break;

            case "delete":
                String idRaw = request.getParameter("id");
                if (idRaw != null && !idRaw.isEmpty()) {
                    int idD = Integer.parseInt(idRaw);
                    cadd.deleteAddress(idD);
                    // SAU KHI XÓA: Dùng redirect để trình duyệt tải lại trang với dữ liệu mới
                    response.sendRedirect("addresssuserservlet");
                    return; // Kết thúc hàm tại đây, không forward nữa
                }
                break;

            default:
                // Trang chính: Load dữ liệu để hiển thị
                List<CustomerAddress> listdata = cadd.getAddressesByCustomerId(currentUserId);
                Customer customer = cdao.getCustomerById(currentUserId);
                request.setAttribute("customer", customer);
                request.setAttribute("listAddress", listdata);
                page = "/pages/DashboardPage/userDashboard.jsp";
                break;
        }

        // 4. Setup giao diện và Forward
        request.setAttribute("HeaderComponent", "/components/navbar.jsp");
        request.setAttribute("FooterComponent", "/components/footer.jsp");
        request.setAttribute("ContentPage", page);
        request.getRequestDispatcher("/template/userTemplate.jsp").forward(request, response);
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
        CustomerAddressDAO aO = new CustomerAddressDAO();
        int currentUserId = -1;
        String action = request.getParameter("action");
        jakarta.servlet.http.Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (jakarta.servlet.http.Cookie cookie : cookies) {
                if (cookie.getName().equals("cookieID")) {
                    currentUserId = Integer.parseInt(cookie.getValue());
                    break;
                }
            }
        }
        switch (action) {
            case "add":
                // 1. Lấy thông tin cơ bản
                String nameReceiver = request.getParameter("nameReceiver");
                String phoneReceiver = request.getParameter("phoneReceiver");
                String street = request.getParameter("street"); // Số nhà, tên đường

// 2. Lấy Tên địa chỉ (Chữ tiếng Việt) từ thẻ Hidden
                String wardName = request.getParameter("wardName");
                String provinceName = request.getParameter("provinceName");

// 3. Gộp thành chuỗi địa chỉ hoàn chỉnh để lưu vào 1 cột duy nhất trong DB
// Ví dụ: "Số 123, Phường An Hội Tây, Thành phố Hồ Chí Minh"
                String fullAddress = street + ", " + wardName + ", " + provinceName;

// 4. Kiểm tra địa chỉ mặc định
                boolean isDefault = "true".equals(request.getParameter("isDefault"));

// 5. Gọi DAO để thực hiện câu lệnh INSERT
                aO.insertAddress(new CustomerAddress(0, currentUserId, nameReceiver, phoneReceiver, fullAddress, isDefault));
//                processRequest(request, response);

                response.sendRedirect("addresssuserservlet");
                break;
            default:
                throw new AssertionError();
        }

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
