<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<div class="flex justify-center items-center min-h-screen bg-gray-50 py-10 px-4">
    <div class="w-full max-w-3xl bg-white rounded-2xl shadow-xl overflow-hidden border border-gray-100">

        <div class="bg-gray-50 px-8 py-6 border-b border-gray-200 flex justify-between items-center">
            <div>
                <h2 class="text-2xl font-bold text-gray-800">Voucher Details</h2>
                <p class="text-sm text-gray-500 mt-1">ID Reference: <span class="font-mono text-gray-700">#${voucher.voucherId}</span></p>
            </div>

            <div>
                <c:choose>
                    <c:when test="${voucher.status == 'ACTIVE'}">
                        <span class="inline-flex items-center px-3 py-1 rounded-full text-sm font-medium bg-green-100 text-green-800 border border-green-200">
                            <span class="w-2 h-2 mr-2 bg-green-500 rounded-full"></span> Active
                        </span>
                    </c:when>
                    <c:when test="${voucher.status == 'LOCKED'}">
                        <span class="inline-flex items-center px-3 py-1 rounded-full text-sm font-medium bg-red-100 text-red-800 border border-red-200">
                            <span class="w-2 h-2 mr-2 bg-red-500 rounded-full"></span> Locked
                        </span>
                    </c:when>
                    <c:otherwise>
                        <span class="inline-flex items-center px-3 py-1 rounded-full text-sm font-medium bg-gray-100 text-gray-800 border border-gray-200">
                            Expired / Other
                        </span>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>

        <div class="p-8">
            <div class="grid grid-cols-1 md:grid-cols-2 gap-8 mb-8">

                <div class="bg-blue-50 p-6 rounded-xl border border-blue-100 text-center">
                    <p class="text-xs text-blue-500 font-semibold uppercase tracking-wider mb-2">Voucher Code</p>
                    <div class="text-3xl font-black text-blue-700 tracking-widest font-mono select-all">
                        ${voucher.code}
                    </div>
                </div>

                <div class="flex flex-col justify-center space-y-4">
                    <div>
                        <p class="text-sm text-gray-500">Discount Amount</p>
                        <p class="text-2xl font-bold text-gray-900">
                            ${voucher.discountPercent}% <span class="text-base font-normal text-gray-500">OFF</span>
                        </p>
                    </div>
                    <div class="grid grid-cols-2 gap-4">
                        <div>
                            <p class="text-xs text-gray-500">Min Order</p>
                            <p class="font-semibold text-gray-800">
                                <fmt:formatNumber value="${voucher.minOrderValue}" type="currency" currencySymbol="₫"/>
                            </p>
                        </div>
                        <div>
                            <p class="text-xs text-gray-500">Max Discount</p>
                            <p class="font-semibold text-gray-800">
                                <c:choose>
                                    <c:when test="${voucher.maxDiscountAmount > 0}">
                                        <fmt:formatNumber value="${voucher.maxDiscountAmount}" type="currency" currencySymbol="₫"/>
                                    </c:when>
                                    <c:otherwise>Unlimited</c:otherwise>
                                </c:choose>
                            </p>
                        </div>
                    </div>
                </div>
            </div>

            <hr class="border-gray-100 my-8">

            <div class="grid grid-cols-1 md:grid-cols-2 gap-8">

                <div>
                    <h3 class="text-sm font-semibold text-gray-900 uppercase tracking-wide mb-4 flex items-center">
                        <svg class="w-4 h-4 mr-2 text-gray-400" fill="none" stroke="currentColor" viewBox="0 0 24 24"><path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M12 8v4l3 3m6-3a9 9 0 11-18 0 9 9 0 0118 0z"></path></svg>
                        Validity Period
                    </h3>
                    <div class="relative pl-4 border-l-2 border-gray-200 space-y-4">
                        <div class="relative">
                            <span class="absolute -left-[21px] top-1 w-3 h-3 bg-green-500 rounded-full border-2 border-white"></span>
                            <p class="text-xs text-gray-500">Start Date</p>
                            <p class="text-sm font-medium text-gray-900">
                                ${voucher.validFrom.dayOfMonth}/${voucher.validFrom.monthValue}/${voucher.validFrom.year} 
                                <span class="text-gray-400 ml-1">${voucher.validFrom.hour}:${voucher.validFrom.minute}</span>
                            </p>
                        </div>
                        <div class="relative">
                            <span class="absolute -left-[21px] top-1 w-3 h-3 bg-red-400 rounded-full border-2 border-white"></span>
                            <p class="text-xs text-gray-500">Expiration Date</p>
                            <p class="text-sm font-medium text-gray-900">
                                ${voucher.validTo.dayOfMonth}/${voucher.validTo.monthValue}/${voucher.validTo.year} 
                                <span class="text-gray-400 ml-1">${voucher.validTo.hour}:${voucher.validTo.minute}</span>
                            </p>
                        </div>
                    </div>
                </div>

                <div>
                    <h3 class="text-sm font-semibold text-gray-900 uppercase tracking-wide mb-4 flex items-center">
                        <svg class="w-4 h-4 mr-2 text-gray-400" fill="none" stroke="currentColor" viewBox="0 0 24 24"><path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M9 19v-6a2 2 0 00-2-2H5a2 2 0 00-2 2v6a2 2 0 002 2h2a2 2 0 002-2zm0 0V9a2 2 0 012-2h2a2 2 0 012 2v10m-6 0a2 2 0 002 2h2a2 2 0 002-2m0 0V5a2 2 0 012-2h2a2 2 0 012 2v14a2 2 0 01-2 2h-2a2 2 0 01-2-2z"></path></svg>
                        Usage Statistics
                    </h3>
                    <div class="bg-gray-50 rounded-lg p-4">
                        <div class="flex justify-between items-end mb-2">
                            <span class="text-sm text-gray-600">Total Usage</span>
                            <span class="text-sm font-bold text-gray-900">
                                ${voucher.usedQuantity} <span class="text-gray-400 font-normal">/ ${voucher.totalQuantity}</span>
                            </span>
                        </div>

                        <c:set var="percent" value="${(voucher.totalQuantity > 0) ? (voucher.usedQuantity * 100 / voucher.totalQuantity) : 0}" />
                        <div class="w-full bg-gray-200 rounded-full h-2.5 mb-2">
                            <div class="bg-blue-600 h-2.5 rounded-full transition-all duration-500" style="width: ${percent}%"></div>
                        </div>
                        <p class="text-xs text-gray-500 text-right">${percent}% redeemed</p>
                    </div>
                </div>
            </div>
        </div>

        <div class="bg-gray-50 px-8 py-5 border-t border-gray-200 flex justify-end space-x-3">
            <a href="voucherservlet?action=all" 
               class="px-4 py-2 text-sm font-medium text-gray-700 bg-white border border-gray-300 rounded-lg hover:bg-gray-50 focus:ring-2 focus:ring-offset-1 focus:ring-gray-200">
                Back to List
            </a>

        </div>

    </div>
</div>