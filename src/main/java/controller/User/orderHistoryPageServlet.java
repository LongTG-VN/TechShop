/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package controller.User;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Cookie;
import java.util.ArrayList;
import java.util.List;
import dao.OrderDAO;
import model.Order;

/**
 *
 * @author ASUS
 */
@WebServlet(name="orderHistoryPageServlet", urlPatterns={"/orderhistorypageservlet"})
public class orderHistoryPageServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
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
            out.println("<title>Servlet orderHistoryPageServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet orderHistoryPageServlet at " + request.getContextPath () + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        // Lấy customer hiện tại từ cookie
        int currentUserId = -1;
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("cookieID".equals(cookie.getName())) {
                    try {
                        currentUserId = Integer.parseInt(cookie.getValue());
                    } catch (NumberFormatException e) {
                        currentUserId = -1;
                    }
                    break;
                }
            }
        }

        // Nếu chưa đăng nhập thì điều hướng về trang đăng nhập
        if (currentUserId == -1) {
            response.sendRedirect("userservlet?action=loginPage");
            return;
        }

        String statusFilter = request.getParameter("status");

        OrderDAO odao = new OrderDAO();
        List<Order> allOrders = odao.getOrdersByCustomerWithSummary(currentUserId);
        List<Order> ordersToShow = new ArrayList<>();

        if (statusFilter == null || statusFilter.trim().isEmpty() || "ALL".equalsIgnoreCase(statusFilter)) {
            ordersToShow = allOrders;
        } else {
            for (Order o : allOrders) {
                if (statusFilter.equalsIgnoreCase(o.getStatus())) {
                    ordersToShow.add(o);
                }
            }
        }

        request.setAttribute("orders", ordersToShow);
        request.setAttribute("currentStatus", (statusFilter == null || statusFilter.trim().isEmpty()) ? "ALL" : statusFilter);

        String headerComponent = "/components/navbar.jsp"; // Trang mặc định khi mới vào
        String footerComponent = "/components/footer.jsp"; // Trang mặc định khi mới vào
        String page = "/pages/MainPage/orderHistoryPage.jsp"; // Trang mặc định khi mới vào
        request.setAttribute("HeaderComponent", headerComponent);
        request.setAttribute("FooterComponent", footerComponent);
        request.setAttribute("ContentPage", page);

        // 5. Forward đến Template duy nhất
        request.getRequestDispatcher("/template/userTemplate.jsp").forward(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
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
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
