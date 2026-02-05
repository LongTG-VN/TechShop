package controller.admin;

import dao.BrandDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.List;
import model.Brand;

/**
 *
 * @author CaoTram
 */
@WebServlet(name = "brandServlet", urlPatterns = {"/brandServlet"})
public class brandServlet extends HttpServlet {

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
        String page = "/pages/BrandManagementPage/brandManagement.jsp";
        List<?> listData = null;
        BrandDAO bdao = new BrandDAO();

        if (action != null) {
            switch (action) {
                case "add":
                    page = "/pages/BrandManagementPage/addBrand.jsp";
                    break;
                case "delete":
                    int idDel = Integer.parseInt(request.getParameter("id"));
                    Brand brand = bdao.getBrandById(idDel);
                    int pCount = bdao.countProductsByBrandId(idDel);
                    request.setAttribute("brand", brand);
                    request.setAttribute("productCount", pCount);
                    page = "/pages/BrandManagementPage/deleteBrand.jsp";
                    break;
                case "edit":
                    int idEdit = Integer.parseInt(request.getParameter("id"));
                    Brand b = bdao.getBrandById(idEdit);
                    request.setAttribute("brand", b);
                    page = "/pages/BrandManagementPage/editBrand.jsp";
                    break;
                case "detail":
                    int idDetail = Integer.parseInt(request.getParameter("id"));
                    Brand brandDetail = bdao.getBrandById(idDetail);
                    int productCount = bdao.countProductsByBrandId(idDetail);
                    request.setAttribute("brand", brandDetail);
                    request.setAttribute("productCount", productCount);
                    page = "/pages/BrandManagementPage/detailBrand.jsp";
                    break;
                case "all":
                    page = "/pages/BrandManagementPage/brandManagement.jsp";
                    listData = bdao.getAllBrand();
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
        BrandDAO bdao = new BrandDAO();
        HttpSession session = request.getSession();

        if ("add".equals(action)) {
            String name = request.getParameter("brandName").trim();
            boolean status = "1".equals(request.getParameter("isActive"));

            if (bdao.isBrandNameExists(name)) {
                session.setAttribute("msg", "Error: Brand name '" + name + "' already exists!");
                session.setAttribute("msgType", "danger");
                response.sendRedirect("brandServlet?action=add");
                return;
            }

            bdao.insertBrand(name, status);
            session.setAttribute("msg", "Brand '" + name + "' added successfully!");
            session.setAttribute("msgType", "success");
            response.sendRedirect("brandServlet?action=all");
            return;

        } else if ("update".equals(action)) {
            int id = Integer.parseInt(request.getParameter("brandId"));
            String name = request.getParameter("brandName").trim();
            boolean status = "1".equals(request.getParameter("isActive"));

            if (bdao.isBrandNameExists(name, id)) {
                session.setAttribute("msg", "Error: Name '" + name + "' is already used by another brand!");
                session.setAttribute("msgType", "danger");
                response.sendRedirect("brandServlet?action=edit&id=" + id);
                return;
            }

            Brand currentBrand = bdao.getBrandById(id);
            if (currentBrand.getBrandName().equals(name) && currentBrand.isIsActive() == status) {
                session.setAttribute("msg", "No changes detected.");
                session.setAttribute("msgType", "danger");
                response.sendRedirect("brandServlet?action=edit&id=" + id);
                return;
            }

            Brand b = new Brand(id, name, status);
            bdao.updateBrand(b);
            session.setAttribute("msg", "Brand '" + name + "' updated successfully!");
            session.setAttribute("msgType", "success");
            response.sendRedirect("brandServlet?action=all");
            return;

        } else if ("delete".equals(action)) {
            int id = Integer.parseInt(request.getParameter("brandId"));
            int count = bdao.countProductsByBrandId(id);

            if (count > 0) {
                bdao.deactivateBrand(id);
                session.setAttribute("msg", "Brand contains products. Switched to INACTIVE.");
            } else {
                bdao.deleteBrand(id);
                session.setAttribute("msg", "Brand deleted successfully!");
            }
            session.setAttribute("msgType", "success");
        }

        response.sendRedirect("brandServlet?action=all");
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
