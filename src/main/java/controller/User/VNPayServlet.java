package controller.User;

import dao.CartItemDAO;
import dao.InventoryItemDAO;
import dao.OrderDAO;
import dao.OrderItemDAO;
import dao.VoucherDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;
import model.CartItemDisplay;
import model.OrderItem;
import utils.VNPayConfig;
import utils.VNPayUtils;

@WebServlet(name = "vnpayservlet", urlPatterns = {"/vnpayservlet"})
public class VNPayServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");

        if ("pay".equals(action)) {
            handleCreatePayment(request, response);
        } else if ("return".equals(action)) {
            handleReturn(request, response);
        }
    }

    private void handleCreatePayment(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        String amountParam = request.getParameter("amount");
        long amount = Long.parseLong(amountParam) * 100;

        // Dùng timestamp làm txnRef tạm (chưa có orderId thật)
        String txnRef = String.valueOf(System.currentTimeMillis());

        // Lưu txnRef vào session để verify sau
        request.getSession().setAttribute("vnpay_txnRef", txnRef);

        String createDate = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        String expireDate = new SimpleDateFormat("yyyyMMddHHmmss")
                .format(new Date(System.currentTimeMillis() + 15 * 60 * 1000));
        String ipAddr = request.getRemoteAddr();

        Map<String, String> vnpParams = new HashMap<>();
        vnpParams.put("vnp_Version", VNPayConfig.vnp_Version);
        vnpParams.put("vnp_Command", VNPayConfig.vnp_Command);
        vnpParams.put("vnp_TmnCode", VNPayConfig.vnp_TmnCode);
        vnpParams.put("vnp_Amount", String.valueOf(amount));
        vnpParams.put("vnp_CurrCode", VNPayConfig.vnp_CurrCode);
        vnpParams.put("vnp_TxnRef", txnRef);
        vnpParams.put("vnp_OrderInfo", "Thanh toan don hang");
        vnpParams.put("vnp_OrderType", VNPayConfig.vnp_OrderType);
        vnpParams.put("vnp_Locale", VNPayConfig.vnp_Locale);
        vnpParams.put("vnp_ReturnUrl", VNPayConfig.vnp_ReturnUrl);
        vnpParams.put("vnp_IpAddr", ipAddr);
        vnpParams.put("vnp_CreateDate", createDate);
        vnpParams.put("vnp_ExpireDate", expireDate);

        String paymentUrl = VNPayUtils.buildPaymentUrl(vnpParams);
        response.sendRedirect(paymentUrl);
    }

    private void handleReturn(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        Map<String, String> params = new HashMap<>();
        request.getParameterMap().forEach((k, v) -> params.put(k, v[0]));

        String responseCode = params.get("vnp_ResponseCode");
        boolean success = "00".equals(responseCode);

        if (success) {
            // Lấy thông tin từ session để tạo order
            HttpSession sess = request.getSession();
            try {
                int customerId = (int) sess.getAttribute("vnpay_customerId");
                String address = (String) sess.getAttribute("vnpay_shippingAddress");
                int paymentMethodId = (int) sess.getAttribute("vnpay_paymentMethodId");
                long totalAmountL = (long) sess.getAttribute("vnpay_totalAmount");
                int voucherId = (int) sess.getAttribute("vnpay_voucherId");
                BigDecimal totalAmount = BigDecimal.valueOf(totalAmountL);

                // Lấy voucher nếu có
                model.Voucher appliedVoucher = null;
                if (voucherId > 0) {
                    appliedVoucher = new VoucherDAO().getVoucherById(voucherId);
                }

                // Tạo order
                OrderDAO orderDAO = new OrderDAO();
                model.Order newOrder = new model.Order(
                        customerId, appliedVoucher, paymentMethodId, address, totalAmount);
                int orderId = orderDAO.insertOrder(newOrder);

                if (orderId > 0) {
                    // Thêm order_items + update inventory
                    CartItemDAO cartDao = new CartItemDAO();
                    InventoryItemDAO inventoryDAO = new InventoryItemDAO();
                    OrderItemDAO orderItemDAO = new OrderItemDAO();
                    List<CartItemDisplay> listCart = cartDao.getCartDisplayByCustomerId(customerId);

                    for (CartItemDisplay item : listCart) {
                        int variantId = item.getVariantId();
                        int qty = item.getQuantity();
                        BigDecimal sellingPrice = BigDecimal.valueOf(item.getSellingPrice());

                        for (int i = 0; i < qty; i++) {
                            OrderItem oi = new OrderItem();
                            oi.setOrderId(orderId);
                            oi.setVariantId(variantId);
                            oi.setInventoryId(0); 
                            oi.setSellingPrice(sellingPrice);
                            orderItemDAO.insertOrderItem(oi);
                        }
                    }

                    // Cập nhật payment status PAID
                    orderDAO.updateOrderPaymentStatus(orderId, "PAID");

                    // Cập nhật voucher
                    if (appliedVoucher != null) {
                        new VoucherDAO().incrementUsedQuantity(appliedVoucher.getVoucherId());
                    }

                    // Xóa giỏ hàng
                    cartDao.deleteCartByCustomerId(customerId);
                }

                // Xóa session vnpay
                sess.removeAttribute("vnpay_customerId");
                sess.removeAttribute("vnpay_shippingAddress");
                sess.removeAttribute("vnpay_paymentMethodId");
                sess.removeAttribute("vnpay_totalAmount");
                sess.removeAttribute("vnpay_voucherId");
                sess.removeAttribute("vnpay_txnRef");

                response.sendRedirect("orderpageservlet?orderSuccess=1&orderId=" + orderId);

            } catch (Exception e) {
                e.printStackTrace();
                response.sendRedirect("orderpageservlet");
            }
            return;
        }

        // Hủy hoặc thất bại → quay về trang đặt hàng, giỏ hàng vẫn còn
        request.getSession().setAttribute("vnpayError", "24".equals(responseCode)
                ? "You have canceled your VNPAY payment or an error has occurred. Please try again. We apologize for the inconvenience."
                : "VNPay payment failed (Error code: " + responseCode + "). ");
        response.sendRedirect("orderpageservlet");
    }
}
