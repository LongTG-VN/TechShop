/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.admin;

import dao.VoucherDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import model.Voucher;

/**
 *
 * @author ASUS
 */
@WebServlet(name = "voucherServlet", urlPatterns = {"/voucherservlet"})
public class voucherServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response, Voucher voucher)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet voucherServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h2>DEBUG VOUCHER</h2>");
            out.println("<ul>");
            out.println("<li>ID: " + voucher.getVoucherId() + "</li>");
            out.println("<li>Code: " + voucher.getCode() + "</li>");
            out.println("<li>Discount %: " + voucher.getDiscountPercent() + "</li>");
            out.println("<li>Max discount: " + voucher.getMaxDiscountAmount() + "</li>");
            out.println("<li>Min order: " + voucher.getMinOrderValue() + "</li>");
            out.println("<li>Valid from: " + voucher.getValidFrom() + "</li>");
            out.println("<li>Valid to: " + voucher.getValidTo() + "</li>");
            out.println("<li>Total quantity: " + voucher.getTotalQuantity() + "</li>");
            out.println("<li>Used quantity: " + voucher.getUsedQuantity() + "</li>");
            out.println("<li>Status: " + voucher.getStatus() + "</li>");
            out.println("</ul>");

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
        VoucherDAO vdao = new VoucherDAO();
        if (action != null) {
            switch (action) {
                case "add":
                    page = "/pages/VoucherManagementPage/addVoucher.jsp";
                    break;
                case "edit":
                    int idE = Integer.parseInt(request.getParameter("id"));
                    Voucher voucher = vdao.getVoucherById(idE);
                    request.setAttribute("voucher", voucher);
                    page = "/pages/VoucherManagementPage/editVoucher.jsp";
                    break;
                case "detail":
                    int idD = Integer.parseInt(request.getParameter("id"));
                    Voucher voucherD = vdao.getVoucherById(idD);
                    request.setAttribute("voucher", voucherD);
                    page = "/pages/VoucherManagementPage/detailVoucher.jsp";
                    break;

                case "delete":
                    int idToDelete = Integer.parseInt(request.getParameter("id"));
                    boolean isDelete = vdao.deleteVoucher(idToDelete);
                    page = "/pages/VoucherManagementPage/voucherManagement.jsp";
                    listData = new VoucherDAO().getAllVoucher();
                    break;
//                case "detail":
//                    int idDE = Integer.parseInt(request.getParameter("id"));
//                    Customer customerDE = cdao.getCustomerById(idDE);
//                    List<CustomerAddress> listCustomerAddress = addressDAO.getAddressesByCustomerId(idDE);
//                    request.setAttribute("customer", customerDE);
//                    request.setAttribute("customerAddress", listCustomerAddress);
//                    page = "/pages/CustomerManagementPage/detailCustomer.jsp";
//                    break;
                case "all":
                    page = "/pages/VoucherManagementPage/voucherManagement.jsp";
                    listData = new VoucherDAO().getAllVoucher();
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
        VoucherDAO dao = new VoucherDAO();

        if (action != null) {
            switch (action) {
                case "add":
                    String code = request.getParameter("code").toUpperCase(); // Code nên viết hoa
                    int discountPercent = Integer.parseInt(request.getParameter("discount_percent"));

                    // Xử lý ngày tháng: Input 'datetime-local' trả về dạng "yyyy-MM-ddTHH:mm"
                    // LocalDateTime.parse mặc định hiểu định dạng này, không cần Formatter
                    String validFromStr = request.getParameter("valid_from");
                    String validToStr = request.getParameter("valid_to");

                    LocalDateTime validFrom = LocalDateTime.parse(validFromStr);
                    LocalDateTime validTo = LocalDateTime.parse(validToStr);

                    double minOrderValue = Double.parseDouble(request.getParameter("min_order_value"));

                    // Max discount amount có thể để trống (Optional)
                    String maxDiscountStr = request.getParameter("max_discount_amount");
                    double maxDiscountAmount = (maxDiscountStr != null && !maxDiscountStr.isEmpty())
                            ? Double.parseDouble(maxDiscountStr) : 0;

                    int totalQuantity = Integer.parseInt(request.getParameter("total_quantity"));
                    String status = request.getParameter("status");

                    // B. Validate logic cơ bản
                    // 1. Ngày kết thúc phải sau ngày bắt đầu
                    // C. Tạo đối tượng Voucher
                    Voucher v = new Voucher();
                    v.setCode(code);
                    v.setDiscountPercent(discountPercent);
                    v.setValidFrom(validFrom);
                    v.setValidTo(validTo);
                    v.setMinOrderValue(minOrderValue);
                    v.setMaxDiscountAmount(maxDiscountAmount);
                    v.setTotalQuantity(totalQuantity);
                    v.setUsedQuantity(0); // Mới tạo thì chưa ai dùng
                    v.setStatus(status);

                    boolean isSuccess = dao.insertVoucher(v);
                    break;
                case "edit":
                    // 1. Lấy ID (Bắt buộc phải có để biết sửa dòng nào)
                    String codeE = request.getParameter("code").toUpperCase(); // Code nên viết hoa
                    int id = Integer.parseInt(request.getParameter("voucher_id"));
                    // 2. Lấy dữ liệu từ form (Y hệt phần Add)
                    int discountPercentE = Integer.parseInt(request.getParameter("discount_percent"));
                    // Parse ngày tháng
                    DateTimeFormatter formatter
                            = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");

                    LocalDateTime validFromE
                            = LocalDateTime.parse(request.getParameter("valid_from"), formatter);
                    LocalDateTime validToE
                            = LocalDateTime.parse(request.getParameter("valid_to"), formatter);
                    double minOrderValueE = Double.parseDouble(request.getParameter("min_order_value"));
                    String maxDiscountStrE = request.getParameter("max_discount_amount");
                    double maxDiscountAmountE = (maxDiscountStrE != null && !maxDiscountStrE.isEmpty())
                            ? Double.parseDouble(maxDiscountStrE) : 0;
                    int totalQuantityE = Integer.parseInt(request.getParameter("total_quantity"));
                    String statusE = request.getParameter("status");
                    // 3. Đóng gói vào object Voucher
                    Voucher vEdit = new Voucher();
                    vEdit.setCode(codeE);
                    vEdit.setVoucherId(id); // Quan trọng nhất
                    vEdit.setDiscountPercent(discountPercentE);
                    vEdit.setValidFrom(validFromE);
                    vEdit.setValidTo(validToE);
                    vEdit.setMinOrderValue(minOrderValueE);
                    vEdit.setMaxDiscountAmount(maxDiscountAmountE);
                    vEdit.setTotalQuantity(totalQuantityE);
                    vEdit.setStatus(statusE);

                    dao.updateVoucher(vEdit);

                    break;
            }
        }
        response.sendRedirect("voucherservlet?action=all");
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
