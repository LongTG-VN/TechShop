package controller.User;

import dao.CartItemDAO;
import dao.CustomerDAO;
import dao.InventoryItemDAO;
import dao.ProductVariantDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.CartItem;
import model.CartItemDisplay;
import model.ProductVariant;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "cartServlet", urlPatterns = {"/cartservlet"})
public class cartServlet extends HttpServlet {

    private int getCustomerId(HttpServletRequest request) {
        Object sid = request.getSession(false) != null ? request.getSession().getAttribute("customerId") : null;
        if (sid instanceof Integer) {
            return (Integer) sid;
        }
        if (sid != null) {
            try {
                return Integer.parseInt(sid.toString());
            } catch (NumberFormatException ignored) {
            }
        }
        int fromCookie = getCustomerIdFromCookie(request);
        if (fromCookie > 0) {
            request.getSession(true).setAttribute("customerId", fromCookie);
        }
        return fromCookie;
    }

    private int getCustomerIdFromCookie(HttpServletRequest request) {
        jakarta.servlet.http.Cookie[] cookies = request.getCookies();
        if (cookies == null) {
            return -1;
        }
        boolean isCustomer = false;
        String idVal = null;
        for (jakarta.servlet.http.Cookie c : cookies) {
            if ("cookieID".equals(c.getName())) {
                idVal = c.getValue();
            }
            if ("cookieRole".equals(c.getName()) && "customer".equals(c.getValue())) {
                isCustomer = true;
            }
        }
        if (!isCustomer || idVal == null || idVal.isEmpty()) {
            return -1;
        }
        try {
            return Integer.parseInt(idVal);
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate, max-age=0");
        response.setHeader("Pragma", "no-cache");
        response.setDateHeader("Expires", 0);

        int customerId = getCustomerId(request);
        if (customerId > 0 && new CustomerDAO().getCustomerById(customerId) == null) {
            request.getSession().removeAttribute("customerId");
            customerId = -1;
        }
        List<CartItemDisplay> listCart = new java.util.ArrayList<>();
        if (customerId > 0) {
            CartItemDAO cartDao = new CartItemDAO();
            listCart = cartDao.getCartDisplayByCustomerId(customerId);
        }
        int cartCount = listCart.size();

        // API chỉ trả số giỏ (JSON) để client cập nhật badge không cần F5
        if ("count".equals(request.getParameter("action"))) {
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().print("{\"cartCount\":" + cartCount + "}");
            return;
        }

        String headerComponent = "/components/navbar.jsp";
        String footerComponent = "/components/footer.jsp";
        String page = "/pages/MainPage/cartPage.jsp";

        request.setAttribute("cartCount", cartCount);
        request.getSession().setAttribute("cartCount", cartCount);

        request.setAttribute("listCart", listCart);
        request.setAttribute("HeaderComponent", headerComponent);
        request.setAttribute("FooterComponent", footerComponent);
        request.setAttribute("ContentPage", page);
        request.getRequestDispatcher("/template/userTemplate.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String action = request.getParameter("action");
        boolean isAjax = "XMLHttpRequest".equals(request.getHeader("X-Requested-With"))
                || "1".equals(request.getParameter("ajax"));

        if ("add".equals(action)) {
            int customerId = getCustomerId(request);
            if (customerId <= 0) {
                if (isAjax) {
                    sendJson(response, false, 0, 0, "Please log in to add items to your cart.");
                } else {
                    response.sendRedirect(request.getContextPath() + "/userservlet?action=loginPage&from=cart");
                }
                return;
            }
            if (new CustomerDAO().getCustomerById(customerId) == null) {
                request.getSession().removeAttribute("customerId");
                String msg = "Your session is no longer valid. Please log in again.";
                if (isAjax) {
                    sendJson(response, false, 0, 0, msg);
                } else {
                    request.getSession().setAttribute("msg", msg);
                    request.getSession().setAttribute("msgType", "danger");
                    response.sendRedirect(request.getContextPath() + "/userservlet?action=loginPage");
                }
                return;
            }

            String vParam = request.getParameter("variant_id");
            String qParam = request.getParameter("quantity");
            if (vParam == null || vParam.isEmpty() || qParam == null || qParam.isEmpty()) {
                if (isAjax) {
                    sendJson(response, false, 0, 0, "Invalid product data.");
                } else {
                    response.sendRedirect(request.getContextPath() + "/cartservlet");
                }
                return;
            }

            int variantId;
            int quantity;
            try {
                variantId = Integer.parseInt(vParam);
                quantity = Integer.parseInt(qParam);
                if (quantity < 1) {
                    quantity = 1;
                }
            } catch (NumberFormatException e) {
                if (isAjax) {
                    sendJson(response, false, 0, 0, "Invalid quantity.");
                } else {
                    response.sendRedirect(request.getContextPath() + "/cartservlet");
                }
                return;
            }

            CartItemDAO cartDao = new CartItemDAO();
            CartItem existing = cartDao.getByCustomerAndVariant(customerId, variantId);

            // Kiểm tra tồn kho để không cho giỏ vượt quá số lượng còn trong kho
            InventoryItemDAO invDao = new InventoryItemDAO();
            int available = invDao.countAvailableByVariantId(variantId);
            if (available <= 0) {
                String msg = "This product is out of stock. Please choose another.";
                if (isAjax) {
                    sendJson(response, false, 0, 0, msg);
                } else {
                    request.getSession().setAttribute("msg", msg);
                    request.getSession().setAttribute("msgType", "danger");
                    response.sendRedirect(request.getContextPath() + "/cartservlet");
                }
                return;
            }

            int currentQty = (existing != null) ? existing.getQuantity() : 0;
            int targetQty = currentQty + quantity;
            if (targetQty > available) {
                targetQty = available; // không cho vượt quá tồn kho
            }

            boolean saved;
            if (existing != null) {
                existing.setQuantity(targetQty);
                saved = cartDao.updateCartItem(existing);
            } else {
                CartItem newItem = new CartItem(0, customerId, variantId, targetQty, null);
                saved = cartDao.insertCartItem(newItem);
            }

            if (!saved) {
                String msg = "Could not add item to cart. Please try again.";
                if (isAjax) {
                    sendJson(response, false, 0, 0, msg);
                } else {
                    request.getSession().setAttribute("msg", msg);
                    request.getSession().setAttribute("msgType", "danger");
                    response.sendRedirect(request.getContextPath() + "/cartservlet");
                }
                return;
            }

            if (isAjax) {
                long totalAmount = 0;
                List<CartItemDisplay> list = cartDao.getCartDisplayByCustomerId(customerId);
                int cartCountAjax = (list != null) ? list.size() : 0; // Số sản phẩm khác nhau (như trong giỏ)
                for (CartItemDisplay d : list) {
                    totalAmount += d.getSubtotal();
                }
                request.getSession().setAttribute("cartCount", cartCountAjax);

                String addMsg;
                if (quantity > available || currentQty + quantity > available) {
                    addMsg = "Added to cart with maximum available quantity (" + targetQty + " item(s)).";
                } else {
                    addMsg = "Added to cart.";
                }

                sendJson(response, true, totalAmount, cartCountAjax, addMsg);
            } else {
                String addMsg;
                if (quantity > available || currentQty + quantity > available) {
                    addMsg = "Added to cart with maximum available quantity (" + targetQty + " item(s)).";
                    request.getSession().setAttribute("msgType", "danger");
                } else {
                    addMsg = "Added to cart.";
                    request.getSession().setAttribute("msgType", "success");
                }
                request.getSession().setAttribute("msg", addMsg);
                response.sendRedirect(request.getContextPath() + "/cartservlet");
            }
            return;
        }

        if ("update".equals(action)) {
            int customerId = getCustomerId(request);
            if (customerId <= 0) {
                if (isAjax) {
                    sendJson(response, false, 0, 0, "Please log in again to update your cart.");
                } else {
                    response.sendRedirect(request.getContextPath() + "/cartservlet");
                }
                return;
            }
            if (new CustomerDAO().getCustomerById(customerId) == null) {
                request.getSession().removeAttribute("customerId");
                if (isAjax) {
                    sendJson(response, false, 0, 0, "Your session is no longer valid. Please log in again.");
                } else {
                    response.sendRedirect(request.getContextPath() + "/userservlet?action=loginPage");
                }
                return;
            }
            CartItemDAO cartDao = new CartItemDAO();
            String idParam = request.getParameter("cart_item_id");
            String qParam = request.getParameter("quantity");
            if (idParam != null && qParam != null) {
                try {
                    int cartItemId = Integer.parseInt(idParam);
                    int quantity = Integer.parseInt(qParam);
                    if (quantity >= 1) {
                        CartItem item = cartDao.getCartItemById(cartItemId);
                        if (item != null && item.getCustomer_id() == customerId) {

                            // Kiểm tra tồn kho trước khi cập nhật số lượng
                            InventoryItemDAO invDao = new InventoryItemDAO();
                            int available = invDao.countAvailableByVariantId(item.getVariant_id());

                            if (isAjax) {
                                // Với AJAX: nếu không đủ hàng thì giữ nguyên quantity cũ và trả lỗi để JS hiển thị thông báo + revert UI
                                if (available <= 0 || quantity > available) {
                                    ProductVariantDAO pvDao = new ProductVariantDAO();
                                    ProductVariant v = pvDao.getVariantById(item.getVariant_id());
                                    String productName = (v != null && v.getProductName() != null) ? v.getProductName() : "product";
                                    String errorMsg = "Product \"" + productName + "\" does not have enough stock. Please update your cart or contact the store.";
                                    sendJson(response, false, 0, 0, errorMsg);
                                    return;
                                }
                                // Đủ hàng -> cập nhật đúng quantity user chọn
                                item.setQuantity(quantity);
                                if (!cartDao.updateCartItem(item)) {
                                    sendJson(response, false, 0, 0, "Could not update cart. Please try again.");
                                    return;
                                }
                            } else {
                                // Không phải AJAX (submit thường): tự động chỉnh quantity để khớp tồn kho và dùng toast
                                if (available <= 0) {
                                    cartDao.deleteCartItem(cartItemId);
                                    request.getSession().setAttribute("msg", "Item is out of stock and has been removed from your cart.");
                                    request.getSession().setAttribute("msgType", "danger");
                                } else {
                                    int newQty = quantity;
                                    if (quantity > available) {
                                        newQty = available; // tự chỉnh về mức tối đa còn trong kho
                                        ProductVariantDAO pvDao = new ProductVariantDAO();
                                        ProductVariant v = pvDao.getVariantById(item.getVariant_id());
                                        String productName = (v != null && v.getProductName() != null) ? v.getProductName() : "product";
                                        String warnMsg = "Product \"" + productName + "\" only has " + available + " in stock. Cart quantity has been adjusted to " + available + ".";
                                        request.getSession().setAttribute("msg", warnMsg);
                                        request.getSession().setAttribute("msgType", "danger");
                                    }
                                    item.setQuantity(newQty);
                                    if (!cartDao.updateCartItem(item)) {
                                        request.getSession().setAttribute("msg", "Could not update cart. Please try again.");
                                        request.getSession().setAttribute("msgType", "danger");
                                    }
                                }
                            }
                        }
                    }
                } catch (NumberFormatException ignored) {
                }
            }
            if (isAjax) {
                long totalAmount = 0;
                List<CartItemDisplay> list = cartDao.getCartDisplayByCustomerId(customerId);
                int cartCountAjax = (list != null) ? list.size() : 0; // Số sản phẩm khác nhau (như badge)
                for (CartItemDisplay d : list) {
                    totalAmount += d.getSubtotal();
                }
                request.getSession().setAttribute("cartCount", cartCountAjax);
                sendJson(response, true, totalAmount, cartCountAjax, null);
            } else {
                response.sendRedirect(request.getContextPath() + "/cartservlet");
            }
            return;
        }

        if ("remove".equals(action)) {
            int customerId = getCustomerId(request);
            if (customerId <= 0) {
                response.sendRedirect(request.getContextPath() + "/cartservlet");
                return;
            }
            if (new CustomerDAO().getCustomerById(customerId) == null) {
                request.getSession().removeAttribute("customerId");
                response.sendRedirect(request.getContextPath() + "/userservlet?action=loginPage");
                return;
            }
            String idParam = request.getParameter("cart_item_id");
            if (idParam != null) {
                try {
                    int cartItemId = Integer.parseInt(idParam);
                    CartItemDAO cartDao = new CartItemDAO();
                    CartItem item = cartDao.getCartItemById(cartItemId);
                    if (item != null && item.getCustomer_id() == customerId) {
                        cartDao.deleteCartItem(cartItemId);
                    }
                } catch (NumberFormatException ignored) {
                }
            }
            response.sendRedirect(request.getContextPath() + "/cartservlet");
            return;
        }

        response.sendRedirect(request.getContextPath() + "/cartservlet");
    }

    /**
     * Trả JSON cho AJAX: { "success": true/false, "totalAmount": number,
     * "cartCount": number, "message": "..." }
     */
    private void sendJson(HttpServletResponse response, boolean success, long totalAmount, int cartCount) throws IOException {
        sendJson(response, success, totalAmount, cartCount, null);
    }

    private void sendJson(HttpServletResponse response, boolean success, long totalAmount, int cartCount, String message) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        String msg = message != null ? ",\"message\":\"" + message.replace("\\", "\\\\").replace("\"", "\\\"") + "\"" : "";
        response.getWriter().print("{\"success\":" + success + ",\"totalAmount\":" + totalAmount + ",\"cartCount\":" + cartCount + msg + "}");
    }

}
