<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<div class="min-h-screen bg-gray-50 flex items-center justify-center p-4">
    <div class="bg-white rounded-2xl shadow-lg p-8 max-w-md w-full text-center">

        <c:choose>
            <%-- THÀNH CÔNG --%>
            <c:when test="${success}">
                <div class="w-16 h-16 bg-green-100 rounded-full flex items-center justify-center mx-auto mb-4">
                    <svg class="w-8 h-8 text-green-600" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                        <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M5 13l4 4L19 7"/>
                    </svg>
                </div>
                <h2 class="text-2xl font-extrabold text-green-600 mb-2">Payment successful!</h2>
                <p class="text-gray-500 mb-6">Your order has been paid.</p>
            </c:when>

            <%-- HỦY THANH TOÁN --%>
            <c:when test="${responseCode == '24'}">
                <div class="w-16 h-16 bg-orange-100 rounded-full flex items-center justify-center mx-auto mb-4">
                    <svg class="w-8 h-8 text-orange-500" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                        <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M12 9v2m0 4h.01M10.29 3.86L1.82 18a2 2 0 001.71 3h16.94a2 2 0 001.71-3L13.71 3.86a2 2 0 00-3.42 0z"/>
                    </svg>
                </div>
                <h2 class="text-2xl font-extrabold text-orange-500 mb-2">Payment cancelled</h2>
                <p class="text-gray-500 mb-6">You have canceled the transaction. The order has not been created.</p>
            </c:when>

            <%-- THẤT BẠI --%>
            <c:otherwise>
                <div class="w-16 h-16 bg-red-100 rounded-full flex items-center justify-center mx-auto mb-4">
                    <svg class="w-8 h-8 text-red-600" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                        <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M6 18L18 6M6 6l12 12"/>
                    </svg>
                </div>
                <h2 class="text-2xl font-extrabold text-red-600 mb-2">Payment failed!</h2>
                <p class="text-gray-500 mb-6">
                    Transaction failed. Error code:
                    <span class="font-mono font-bold">${responseCode}</span>
                </p>
            </c:otherwise>
        </c:choose>

        <%-- CHI TIẾT GIAO DỊCH — chỉ hiện khi có orderId --%>
        <c:if test="${not empty orderId && responseCode != '24'}">
            <div class="bg-gray-50 rounded-xl p-4 text-left space-y-2 mb-6 text-sm">
                <c:if test="${not empty orderId}">
                    <div class="flex justify-between">
                        <span class="text-gray-500">Order number:</span>
                        <span class="font-bold">#${orderId}</span>
                    </div>
                </c:if>
                <c:if test="${not empty transactionNo}">
                    <div class="flex justify-between">
                        <span class="text-gray-500">VNPay transaction code:</span>
                        <span class="font-bold">${transactionNo}</span>
                    </div>
                </c:if>
                <c:if test="${amount > 0}">
                    <div class="flex justify-between">
                        <span class="text-gray-500">Amount:</span>
                        <span class="font-bold text-red-600">
                            <fmt:formatNumber value="${amount}" type="number" pattern="#,###"/>đ
                        </span>
                    </div>
                </c:if>
            </div>
        </c:if>

        <%-- BUTTONS --%>
        <div class="flex flex-col gap-3">
            <c:choose>
                <c:when test="${success}">
                    <a href="orderhistorypageservlet"
                       class="block w-full py-3 bg-red-600 text-white font-bold rounded-xl hover:bg-red-700 transition">
                       View order history
                    </a>
                    <a href="userservlet?action=homePage"
                       class="block w-full py-3 bg-gray-100 text-gray-700 font-bold rounded-xl hover:bg-gray-200 transition">
                        Continue shopping
                    </a>
                </c:when>
                <c:otherwise>
                    <%-- Hủy hoặc thất bại → cho đặt lại --%>
                    <a href="userservlet?action=homePage"
                       class="block w-full py-3 bg-red-600 text-white font-bold rounded-xl hover:bg-red-700 transition">
                        Continue shopping
                    </a>
                    <a href="orderhistorypageservlet"
                       class="block w-full py-3 bg-gray-100 text-gray-700 font-bold rounded-xl hover:bg-gray-200 transition">
                        View order history
                    </a>
                </c:otherwise>
            </c:choose>
        </div>

    </div>
</div>
