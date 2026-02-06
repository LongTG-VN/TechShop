<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:if test="${empty inventoryItem}">
    <div class="p-6 text-center text-red-600">Không tìm thấy item kho.</div>
    <a href="staffservlet?action=inventoryManagement" class="text-blue-600 underline">Quay lại danh sách</a>
</c:if>

<c:if test="${not empty inventoryItem}">
    <div class="max-w-2xl mx-auto bg-white p-8 rounded-2xl shadow-xl border border-gray-100 mt-6">
        <h2 class="text-2xl font-bold mb-6 text-gray-800 uppercase tracking-wide">Inventory Item Detail</h2>

        <div class="space-y-6">
            <div class="grid grid-cols-12 gap-4">
                <div class="col-span-4">
                    <label class="block mb-2 text-sm font-medium text-gray-700">Inventory ID</label>
                    <input type="text" value="#${inventoryItem.inventory_id}"
                           class="w-full p-2 border border-gray-300 rounded-lg bg-gray-50 cursor-not-allowed font-bold text-blue-600" readonly>
                </div>
                <div class="col-span-4">
                    <label class="block mb-2 text-sm font-medium text-gray-700">Variant ID</label>
                    <input type="text" value="${inventoryItem.variant_id}"
                           class="w-full p-2 border border-gray-300 rounded-lg bg-gray-50 cursor-not-allowed font-medium" readonly>
                </div>
                <div class="col-span-4">
                    <label class="block mb-2 text-sm font-medium text-gray-700">Receipt item ID</label>
                    <input type="text" value="${inventoryItem.receipt_item_id}"
                           class="w-full p-2 border border-gray-300 rounded-lg bg-gray-50 cursor-not-allowed font-medium" readonly>
                </div>
            </div>

            <div>
                <label class="block mb-2 text-sm font-medium text-gray-700">IMEI</label>
                <input type="text" value="${inventoryItem.imei}"
                       class="w-full p-2 border border-gray-300 rounded-lg bg-gray-50 cursor-not-allowed font-mono" readonly>
            </div>

            <div class="grid grid-cols-1 sm:grid-cols-2 gap-4">
                <div>
                    <label class="block mb-2 text-sm font-medium text-gray-700">Import price</label>
                    <fmt:formatNumber value="${inventoryItem.import_price}" type="number" groupingUsed="true" maxFractionDigits="0" var="importPriceFmt" />
                    <input type="text" value="${importPriceFmt} ₫"
                           class="w-full p-2 border border-gray-300 rounded-lg bg-gray-50 cursor-not-allowed font-semibold" readonly>
                </div>
                <div>
                    <label class="block mb-2 text-sm font-medium text-gray-700">Status</label>
                    <input type="text" value="${empty inventoryItem.status ? 'N/A' : inventoryItem.status}"
                           class="w-full p-2 border border-gray-300 rounded-lg bg-gray-50 cursor-not-allowed font-medium" readonly>
                </div>
            </div>

            <hr class="border-gray-100">

            <div class="flex justify-end gap-3 pt-2">
                <a href="staffservlet?action=inventoryManagement"
                   class="inline-flex items-center gap-2 px-4 py-2 bg-gray-100 text-gray-700 rounded-full hover:bg-gray-200 transition-all font-medium border border-gray-200 text-sm no-underline">
                    <span class="inline-flex items-center justify-center w-7 h-7 bg-white rounded-full text-gray-700">
                        <svg class="w-4 h-4" fill="none" stroke="currentColor" viewBox="0 0 24 24"><path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M10 19l-7-7m0 0l7-7m-7 7h18"/></svg>
                    </span>
                    <span class="hidden sm:inline">Quay lại</span>
                </a>

                <a href="inventory?action=edit&id=${inventoryItem.inventory_id}"
                   class="inline-flex items-center gap-2 px-4 py-2 bg-blue-600 text-white rounded-full hover:bg-blue-700 transition-all font-medium text-sm no-underline shadow-sm">
                    <span class="inline-flex items-center justify-center w-7 h-7 bg-white rounded-full text-blue-600">
                        <svg xmlns="http://www.w3.org/2000/svg" class="w-4 h-4" viewBox="0 0 20 20" fill="currentColor">
                        <path d="M17.414 2.586a2 2 0 010 2.828l-9.9 9.9a1 1 0 01-.464.263l-4 1a1 1 0 01-1.213-1.213l1-4a1 1 0 01.263-.464l9.9-9.9a2 2 0 012.828 0z" />
                        </svg>
                    </span>
                    <span class="hidden sm:inline">Sửa</span>
                </a>
            </div>
        </div>
    </div>
</c:if>

