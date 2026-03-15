package utils;

public class VNPayConfig {

    public static final String vnp_TmnCode = "ND6CO1C8";       
    public static final String vnp_HashSecret = "9JC4WPE6FBEITQXQKKTS0P68EHZKUTAS"; 
    public static final String vnp_Url = "https://sandbox.vnpayment.vn/paymentv2/vpcpay.html";
    public static final String vnp_Version = "2.1.0";
    public static final String vnp_Command = "pay";
    public static final String vnp_CurrCode = "VND";
    public static final String vnp_Locale = "vn";
    public static final String vnp_OrderType = "other";
    // ReturnUrl: URL trên server bạn nhận kết quả từ VNPay
    // localhost không nhận được callback từ VNPay — dùng để hiển thị kết quả cho user thôi
    public static final String vnp_ReturnUrl = "http://localhost:8080/vnpayservlet?action=return";
}
