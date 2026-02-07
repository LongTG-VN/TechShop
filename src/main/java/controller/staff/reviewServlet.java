package controller.staff;

import dao.ReviewDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.List;
import model.Review;

/**
 *
 * @author Hong Kieu
 */
@WebServlet(name = "reviewServlet", urlPatterns = {"/reviewServlet"})
public class reviewServlet extends HttpServlet {

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
            out.println("<title>Servlet reviewServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet reviewServlet at " + request.getContextPath() + "</h1>");
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
        String page = "/pages/ReviewManagementPage/reviewManagement.jsp";
        List<Review> listData = null;

        ReviewDAO rdao = new ReviewDAO();

        if (action != null) {
            switch (action) {
                case "detail":
                    int idDetail = Integer.parseInt(request.getParameter("id"));
                    request.setAttribute("review", rdao.getReviewById(idDetail));
                    page = "/pages/ReviewManagementPage/detailReview.jsp";
                    break;
                case "delete":
                    int idDel = Integer.parseInt(request.getParameter("id"));
                    request.setAttribute("review", rdao.getReviewById(idDel));
                    page = "/pages/ReviewManagementPage/deleteReview.jsp";
                    break;
                case "all":
                default:
                    listData = rdao.getAllReviews();
                    page = "/pages/ReviewManagementPage/reviewManagement.jsp";
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
        ReviewDAO rdao = new ReviewDAO();
        HttpSession session = request.getSession();

        if ("delete".equals(action)) {
            try {
                int id = Integer.parseInt(request.getParameter("reviewId"));
                rdao.deleteReview(id);
                session.setAttribute("msg", "Review deleted successfully!");
                session.setAttribute("msgType", "success");
            } catch (Exception e) {
                session.setAttribute("msg", "Error: Could not delete review.");
                session.setAttribute("msgType", "danger");
            }
        }

        response.sendRedirect("reviewServlet?action=all");
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
