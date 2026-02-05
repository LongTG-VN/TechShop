<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%-- 
    1. XỬ LÝ DỮ LIỆU TRƯỚC KHI HIỂN THỊ 
    Để code HTML bên dưới sạch đẹp, ta xử lý format ở đây.
--%>

<c:set var="fmtValidFrom" value="${fn:substring(voucher.validFrom.toString(), 0, 16)}" />
<c:set var="fmtValidTo" value="${fn:substring(voucher.validTo.toString(), 0, 16)}" />

<fmt:formatNumber var="fmtMinOrder" value="${voucher.minOrderValue}" groupingUsed="false" maxFractionDigits="0" />
<fmt:formatNumber var="fmtMaxDiscount" value="${voucher.maxDiscountAmount}" groupingUsed="false" maxFractionDigits="0" />


<div class="flex justify-center items-start pt-10 min-h-screen bg-gray-50">
    <div class="w-full max-w-2xl bg-white rounded-2xl shadow-2xl p-10 border border-gray-100">

        <div class="text-center mb-10">
            <div class="inline-flex items-center justify-center w-16 h-16 bg-blue-50 text-blue-600 rounded-full mb-4">
                <svg class="w-8 h-8" fill="none" stroke="currentColor" viewBox="0 0 24 24"><path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M11 5H6a2 2 0 00-2 2v11a2 2 0 002 2h11a2 2 0 002-2v-5m-1.414-9.414a2 2 0 112.828 2.828L11.828 15H9v-2.828l8.586-8.586z"></path></svg>
            </div>
            <h2 class="text-3xl font-extrabold text-gray-900 tracking-tight">Update Voucher</h2>
            <p class="text-gray-500 mt-2">Editing campaign: <span class="font-mono font-bold text-blue-600">${voucher.code}</span></p>
        </div>

        <form action="voucherservlet" method="POST" class="space-y-8">
            <input type="hidden" name="action" value="edit">
            <input type="hidden" name="voucher_id" value="${voucher.voucherId}">

            <div class="grid md:grid-cols-2 md:gap-8">
                <div class="relative z-0 w-full group">
                    <input type="text" name="code" id="code" value="${voucher.code}" readonly
                           class="block py-2.5 px-0 w-full text-sm text-gray-500 bg-gray-50 border-0 border-b-2 border-gray-300 appearance-none cursor-not-allowed focus:outline-none" />
                    <label class="absolute text-sm text-gray-400 duration-300 transform -translate-y-6 scale-75 top-3 -z-10 origin-[0]">
                        Voucher Code (Locked)
                    </label>
                </div>
                <div class="relative z-0 w-full group">
                    <input type="number" name="discount_percent" id="discount_percent" min="1" max="100" value="${voucher.discountPercent}"
                           class="block py-2.5 px-0 w-full text-sm text-gray-900 bg-transparent border-0 border-b-2 border-gray-300 appearance-none focus:outline-none focus:ring-0 focus:border-blue-600 peer" 
                           placeholder=" " required />
                    <label for="discount_percent" class="absolute text-sm text-gray-500 duration-300 transform -translate-y-6 scale-75 top-3 -z-10 origin-[0] peer-focus:text-blue-600 peer-placeholder-shown:scale-100 peer-placeholder-shown:translate-y-0 peer-focus:scale-75 peer-focus:-translate-y-6">
                        Discount Percentage (%)
                    </label>
                </div>
            </div>

            <div class="grid md:grid-cols-2 md:gap-8">
                <div class="relative z-0 w-full group">
                    <input type="datetime-local" name="valid_from" id="valid_from" value="${fmtValidFrom}"
                           class="block py-2.5 px-0 w-full text-sm text-gray-900 bg-transparent border-0 border-b-2 border-gray-300 appearance-none focus:outline-none focus:ring-0 focus:border-blue-600 peer" 
                           required />
                    <label for="valid_from" class="absolute text-sm text-gray-500 duration-300 transform -translate-y-6 scale-75 top-3 -z-10 origin-[0] peer-focus:text-blue-600 peer-placeholder-shown:scale-100 peer-placeholder-shown:translate-y-0 peer-focus:scale-75 peer-focus:-translate-y-6">
                        Valid From
                    </label>
                </div>
                <div class="relative z-0 w-full group">
                    <input type="datetime-local" name="valid_to" id="valid_to" value="${fmtValidTo}"
                           class="block py-2.5 px-0 w-full text-sm text-gray-900 bg-transparent border-0 border-b-2 border-gray-300 appearance-none focus:outline-none focus:ring-0 focus:border-blue-600 peer" 
                           required />
                    <label for="valid_to" class="absolute text-sm text-gray-500 duration-300 transform -translate-y-6 scale-75 top-3 -z-10 origin-[0] peer-focus:text-blue-600 peer-placeholder-shown:scale-100 peer-placeholder-shown:translate-y-0 peer-focus:scale-75 peer-focus:-translate-y-6">
                        Valid To
                    </label>
                </div>
            </div>

            <div class="grid md:grid-cols-2 md:gap-8">
                <div class="relative z-0 w-full group">
                    <input type="number" name="min_order_value" id="min_order_value" min="0" step="1000" value="${fmtMinOrder}"
                           class="block py-2.5 px-0 w-full text-sm text-gray-900 bg-transparent border-0 border-b-2 border-gray-300 appearance-none focus:outline-none focus:ring-0 focus:border-blue-600 peer" 
                           placeholder=" " required />
                    <label for="min_order_value" class="absolute text-sm text-gray-500 duration-300 transform -translate-y-6 scale-75 top-3 -z-10 origin-[0] peer-focus:text-blue-600 peer-placeholder-shown:scale-100 peer-placeholder-shown:translate-y-0 peer-focus:scale-75 peer-focus:-translate-y-6">
                        Min Order Value (VND)
                    </label>
                </div>
                <div class="relative z-0 w-full group">
                    <input type="number" name="max_discount_amount" id="max_discount_amount" min="0" step="1000" value="${fmtMaxDiscount}"
                           class="block py-2.5 px-0 w-full text-sm text-gray-900 bg-transparent border-0 border-b-2 border-gray-300 appearance-none focus:outline-none focus:ring-0 focus:border-blue-600 peer" 
                           placeholder=" " />
                    <label for="max_discount_amount" class="absolute text-sm text-gray-500 duration-300 transform -translate-y-6 scale-75 top-3 -z-10 origin-[0] peer-focus:text-blue-600 peer-placeholder-shown:scale-100 peer-placeholder-shown:translate-y-0 peer-focus:scale-75 peer-focus:-translate-y-6">
                        Max Discount Amount (VND)
                    </label>
                </div>
            </div>

            <div class="grid md:grid-cols-2 md:gap-8">
                <div class="relative z-0 w-full group">
                    <input type="number" name="total_quantity" id="total_quantity" min="1" value="${voucher.totalQuantity}"
                           class="block py-2.5 px-0 w-full text-sm text-gray-900 bg-transparent border-0 border-b-2 border-gray-300 appearance-none focus:outline-none focus:ring-0 focus:border-blue-600 peer" 
                           placeholder=" " required />
                    <label for="total_quantity" class="absolute text-sm text-gray-500 duration-300 transform -translate-y-6 scale-75 top-3 -z-10 origin-[0] peer-focus:text-blue-600 peer-placeholder-shown:scale-100 peer-placeholder-shown:translate-y-0 peer-focus:scale-75 peer-focus:-translate-y-6">
                        Total Quantity
                    </label>
                    <p class="absolute right-0 top-3 text-xs text-gray-400">
                        Used: <span class="font-semibold text-gray-600">${voucher.usedQuantity}</span>
                    </p>
                </div>
                
                <div class="relative z-0 w-full group">
                    <select name="status" id="status" class="block py-2.5 px-0 w-full text-sm text-gray-900 bg-transparent border-0 border-b-2 border-gray-300 focus:outline-none focus:ring-0 focus:border-blue-600 peer">
                        <option value="ACTIVE" ${voucher.status == 'ACTIVE' ? 'selected' : ''}>Active</option>
                        <option value="LOCKED" ${voucher.status == 'LOCKED' ? 'selected' : ''}>Locked</option>
                        <option value="EXPIRED" ${voucher.status == 'EXPIRED' ? 'selected' : ''}>Expired</option>
                    </select>
                    <label for="status" class="absolute text-xs text-blue-600 duration-300 transform -translate-y-6 scale-75 top-3 -z-10 origin-[0]">
                        Current Status
                    </label>
                </div>
            </div>

            <div class="flex items-center justify-end space-x-4 pt-6">
                <a href="voucherservlet?action=all" 
                   class="inline-flex items-center px-5 py-2.5 text-sm font-medium text-gray-700 bg-white border border-gray-300 rounded-lg hover:bg-gray-50 hover:text-red-600 focus:ring-4 transition-all duration-200">
                    <svg class="w-4 h-4 mr-2" fill="none" stroke="currentColor" viewBox="0 0 24 24"><path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M6 18L18 6M6 6l12 12"></path></svg>
                    Back
                </a>
                <button type="submit" class="px-10 py-2.5 text-sm font-medium text-white bg-gradient-to-r from-blue-600 to-indigo-600 rounded-lg hover:from-blue-700 hover:to-indigo-700 focus:ring-4 focus:ring-blue-300 shadow-lg shadow-blue-500/30 transition-all duration-200 transform active:scale-95">
                    Save Updates
                </button>
            </div>
        </form>
    </div>
</div>