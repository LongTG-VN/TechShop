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
                request.setAttribute("hasDefaultAddress", utils.IO.checkDefaultAddress(currentUserId));
                break;

            case "edit":
                page = "/pages/AddressManagementPage/editAddress.jsp";
                String editIdRaw = request.getParameter("id"); // Lấy ID của địa chỉ cần sửa trên URL

                if (editIdRaw != null && !editIdRaw.isEmpty()) {
                    int editId = Integer.parseInt(editIdRaw);

                    // 1. Lấy thông tin địa chỉ từ Database
                    CustomerAddress addressToEdit = cadd.getAddressById(editId);

                    if (addressToEdit != null) {
                        // 2. Đẩy các thông tin cơ bản lên request
                        request.setAttribute("addressId", addressToEdit.getAddressId());
                        request.setAttribute("nameReceiver", addressToEdit.getNameReceiver());
                        request.setAttribute("phoneReceiver", addressToEdit.getPhoneReceiver());

                        // 3. Xử lý tách chuỗi địa chỉ
                        // Giả sử lúc thêm mới (Add) bạn lưu gộp chuỗi theo format: "Số nhà, Phường/Xã, Tỉnh/Thành phố"
                        String fullAddress = addressToEdit.getAddress();
                        String street = fullAddress; // Mặc định gán cả chuỗi vào Số nhà/Đường
                        String ward = "";
                        String province = "";

                        if (fullAddress != null && fullAddress.contains(",")) {
                            String[] parts = fullAddress.split(",");
                            if (parts.length >= 3) {
                                // Lấy từ dưới lên: Tỉnh là phần tử cuối, Xã là phần tử áp chót
                                province = parts[parts.length - 1].trim();
                                ward = parts[parts.length - 2].trim();

                                // Gộp các phần còn lại (nếu có nhiều dấu phẩy ở số nhà) làm street
                                street = "";
                                for (int i = 0; i < parts.length - 2; i++) {
                                    street += parts[i].trim() + (i < parts.length - 3 ? ", " : "");
                                }
                            }
                        }

                        // Đẩy lên request để file JS tự động chọn đúng dropdown
                        request.setAttribute("street", street);
                        request.setAttribute("wardName", ward);
                        request.setAttribute("provinceName", province);

                        // 4. Truyền trạng thái xem địa chỉ này có đang là mặc định hay không
                        request.setAttribute("isCurrentDefault", addressToEdit.isIsDefault());
                    }
                }

                // 5. Kiểm tra user đã có địa chỉ mặc định nào khác chưa (phục vụ logic khóa checkbox)
                request.setAttribute("hasDefaultAddress", utils.IO.checkDefaultAddress(currentUserId));
                break;

            case "delete":
                String idRaw = request.getParameter("id");
                if (idRaw != null && !idRaw.isEmpty()) {
                    int idD = Integer.parseInt(idRaw);

                    // 1. Lấy thông tin địa chỉ sắp xóa
                    CustomerAddress addressToDelete = cadd.getAddressById(idD);

                    // 2. Kiểm tra xem nó có phải mặc định không (dùng isIsDefault cho khớp DAO của bạn)
                    if (addressToDelete != null && addressToDelete.isIsDefault()) {
                        // Gọi hàm set địa chỉ khác lên làm mặc định thay thế
                        utils.IO.deleteDefaultAddress(currentUserId, idD);
                    }

                    // 3. Tiến hành xóa
                    cadd.deleteAddress(idD);

                    // 4. Load lại trang
                    response.sendRedirect("addresssuserservlet");
                    return;
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
                String errorPhone = utils.IO.CheckNumber(street) ? "" : "Phone must be 10 number";

                if (!errorPhone.isEmpty()) {
                    aO.insertAddress(new CustomerAddress(0, currentUserId, fullAddress, phoneReceiver, nameReceiver, isDefault));
                    response.sendRedirect("addresssuserservlet");
                } else {

                    request.setAttribute("errorPhone", errorPhone);
                    request.setAttribute("phoneReceiver", phoneReceiver);
                    request.setAttribute("street", street);
                    request.setAttribute("wardName", wardName);
                    request.setAttribute("provinceName", provinceName);
                    request.setAttribute("fullAddress", fullAddress);
                    request.setAttribute("isDefault", isDefault);

                    request.setAttribute("HeaderComponent", "/components/navbar.jsp");
                    request.setAttribute("FooterComponent", "/components/footer.jsp");
                    request.setAttribute("ContentPage", "/pages/AddressManagementPage/addAddress.jsp");
                    request.getRequestDispatcher("/template/userTemplate.jsp").forward(request, response);
                }

                return;

            case "update":
                // 1. Lấy thông tin từ form
                int addressId = Integer.parseInt(request.getParameter("addressId"));
                String editName = request.getParameter("nameReceiver");
                String editPhone = request.getParameter("phoneReceiver");
                String editStreet = request.getParameter("street");
                String editWard = request.getParameter("wardName");
                String editProvince = request.getParameter("provinceName");

                // 2. Gộp chuỗi địa chỉ hoàn chỉnh theo đúng format
                String editFullAddress = editStreet + ", " + editWard + ", " + editProvince;

                // 3. Kiểm tra xem người dùng có tick chọn mặc định không
                boolean editIsDefault = "true".equals(request.getParameter("isDefault"));

                // 4. Validate số điện thoại (Truyền đúng biến editPhone)
                String editErrorPhone = utils.IO.CheckNumber(editPhone) ? "" : "Số điện thoại phải gồm 10 chữ số bắt đầu từ 0";

                // 5. Xử lý lưu hoặc báo lỗi
                if (editErrorPhone.isEmpty()) {

                    // --- ĐOẠN CODE MỚI XỬ LÝ LOGIC ĐỔI MẶC ĐỊNH ---
                    // Lấy thông tin địa chỉ cũ từ Database lên để so sánh
                    CustomerAddress oldAddress = aO.getAddressById(addressId);

                    // Nếu ngày xưa nó LÀ mặc định, mà bây giờ user lại BỎ TICK (!editIsDefault)
                    if (oldAddress != null && oldAddress.isIsDefault() && !editIsDefault) {
                        // Thì mới gọi hàm đổi "vương miện" sang cho địa chỉ khác
                        utils.IO.deleteDefaultAddress(currentUserId, addressId);
                    }
                    // ----------------------------------------------

                    // Cập nhật vào Database
                    CustomerAddress updatedAddress = new CustomerAddress(addressId, currentUserId, editFullAddress, editPhone, editName, editIsDefault);
                    aO.updateAddress(updatedAddress);

                    // Chuyển hướng về trang danh sách
                    response.sendRedirect("addresssuserservlet");

                } else {
                    // Thất bại: Báo lỗi và giữ nguyên dữ liệu trên form
                    request.setAttribute("addressId", addressId);
                    request.setAttribute("errorPhone", editErrorPhone);
                    request.setAttribute("nameReceiver", editName);
                    request.setAttribute("phoneReceiver", editPhone);
                    request.setAttribute("street", editStreet);
                    request.setAttribute("wardName", editWard);
                    request.setAttribute("provinceName", editProvince);

                    // Đẩy lại biến isCurrentDefault để checkbox không bị mất dấu tick
                    request.setAttribute("isCurrentDefault", editIsDefault);

                    // Forward lại trang edit
                    request.setAttribute("HeaderComponent", "/components/navbar.jsp");
                    request.setAttribute("FooterComponent", "/components/footer.jsp");
                    request.setAttribute("ContentPage", "/pages/AddressManagementPage/editAddress.jsp");
                    request.getRequestDispatcher("/template/userTemplate.jsp").forward(request, response);
                }
                return;
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
