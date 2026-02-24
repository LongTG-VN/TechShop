package controller.admin;

import dao.BrandDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import java.io.File;
import java.util.List;
import model.Brand;

/**
 *
 * @author CaoTram
 */
@WebServlet(name = "brandServlet", urlPatterns = {"/brandServlet"})
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024 * 2, // 2MB
        maxFileSize = 1024 * 1024 * 10, // 10MB (tối đa 1 file)
        maxRequestSize = 1024 * 1024 * 50 // 50MB (tối đa cả request)
)
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
    private static final String UPLOAD_DIR = "assest/img/brands";

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

        if ("add".equals(action) || "update".equals(action)) {
            String name = request.getParameter("brandName").trim();
            boolean status = "1".equals(request.getParameter("isActive"));

            // 1. XỬ LÝ FILE UPLOAD (Sử dụng đường dẫn tương đối từ Server)
            Part filePart = request.getPart("brandLogo");
            String fileName = filePart.getSubmittedFileName();
            String imagePath = null;

            // Trong doPost của brandServlet.java
            if (fileName != null && !fileName.isEmpty()) {
                // 1. Tạo tên file duy nhất với Timestamp
                fileName = System.currentTimeMillis() + "_" + fileName;

                // 2. Lấy đường dẫn thư mục CHẠY TẠM (Deploy Path) để hiện ảnh ngay lập tức
                String deployPath = request.getServletContext().getRealPath("") + File.separator + UPLOAD_DIR;

                // 3. Lấy đường dẫn thư mục GỐC (Source Path) để lưu vĩnh viễn (không bị Clean làm mất)
                // Nó sẽ tìm thư mục project của bạn và trỏ vào src/main/webapp/assest/img/brands
                String realPath = request.getServletContext().getRealPath("");
                String sourcePath = realPath.substring(0, realPath.indexOf("target"))
                        + "src" + File.separator + "main" + File.separator + "webapp" + File.separator + UPLOAD_DIR;

                // 4. Tạo thư mục nếu chưa có
                File uploadDirDeploy = new File(deployPath);
                File uploadDirSource = new File(sourcePath);
                if (!uploadDirDeploy.exists()) {
                    uploadDirDeploy.mkdirs();
                }
                if (!uploadDirSource.exists()) {
                    uploadDirSource.mkdirs();
                }

                // 5. Ghi file vào thư mục CHẠY TẠM (để trình duyệt thấy ngay)
                filePart.write(deployPath + File.separator + fileName);

                // 6. Copy file sang thư mục GỐC (để lưu vĩnh viễn và gửi cho nhóm)
                try {
                    java.nio.file.Files.copy(
                            new File(deployPath + File.separator + fileName).toPath(),
                            new File(sourcePath + File.separator + fileName).toPath(),
                            java.nio.file.StandardCopyOption.REPLACE_EXISTING
                    );
                } catch (IOException e) {
                    System.out.println("Lưu vào source lỗi: " + e.getMessage());
                }

                // 7. Lưu đường dẫn vào DB (Chuẩn: assest/img/brands/tên_file)
                imagePath = UPLOAD_DIR + "/" + fileName;
            }
            // 2. XỬ LÝ ACTION: ADD
            if ("add".equals(action)) {
                if (bdao.isBrandNameExists(name)) {
                    session.setAttribute("msg", "Error: Brand name '" + name + "' already exists!");
                    session.setAttribute("msgType", "danger");
                    response.sendRedirect("brandServlet?action=add");
                    return;
                }
                bdao.insertBrand(name, imagePath, status);
                session.setAttribute("msg", "Brand '" + name + "' added successfully!");

                // 3. XỬ LÝ ACTION: UPDATE
            } else if ("update".equals(action)) {
                int id = Integer.parseInt(request.getParameter("brandId"));

                // Sử dụng hàm số 6 trong DAO để loại trừ chính mình khi check trùng tên
                if (bdao.isBrandNameExists(name, id)) {
                    session.setAttribute("msg", "Error: Name '" + name + "' is already used by another brand!");
                    session.setAttribute("msgType", "danger");
                    response.sendRedirect("brandServlet?action=edit&id=" + id);
                    return;
                }

                Brand current = bdao.getBrandById(id);
                // Nếu người dùng không chọn ảnh mới, giữ lại đường dẫn ảnh cũ
                if (imagePath == null) {
                    imagePath = current.getImageUrl();
                }

                Brand b = new Brand(id, name, imagePath, status);
                bdao.updateBrand(b);
                session.setAttribute("msg", "Brand '" + name + "' updated successfully!");
            }
            session.setAttribute("msgType", "success");

            // 4. XỬ LÝ ACTION: DELETE
        } else if ("delete".equals(action)) {
            int id = Integer.parseInt(request.getParameter("brandId"));
            if (bdao.countProductsByBrandId(id) > 0) {
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
