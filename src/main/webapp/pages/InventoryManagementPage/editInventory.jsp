<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:if test="${not empty sessionScope.msg}">
    <div id="toast-notification" class="fixed top-10 left-1/2 -translate-x-1/2 z-[9999] min-w-[320px] transition-all duration-500">
        <div class="flex items-center justify-center p-4 rounded-xl shadow-2xl border-2
             ${sessionScope.msgType == 'danger' ? 'bg-red-50 text-red-800 border-red-200' : 'bg-green-50 text-green-800 border-green-200'}">
            <span class="font-bold uppercase tracking-wider text-sm">${sessionScope.msg}</span>
        </div>
    </div>
    <c:remove var="msg" scope="session" />
    <c:remove var="msgType" scope="session" />
    <script>setTimeout(() => document.getElementById('toast-notification')?.remove(), 3000);</script>
</c:if>

<c:if test="${empty inventoryItem}">
    <div class="p-6 text-center text-red-600">Không tìm thấy item kho.</div>
    <a href="staffservlet?action=inventoryManagement" class="text-blue-600 underline">Quay lại danh sách</a>
</c:if>

<c:if test="${not empty inventoryItem}">
    <div class="max-w-2xl mx-auto bg-white p-8 rounded-2xl shadow-xl border border-gray-100 mt-6">
        <div class="flex justify-between items-start border-b pb-6 mb-8">
            <div>
                <h2 class="text-2xl font-extrabold text-gray-900 uppercase tracking-tight">Edit Inventory Item</h2>
                <p class="text-gray-500 mt-1">ID: <span class="font-mono font-bold text-blue-600">#${inventoryItem.inventory_id}</span></p>
            </div>
            <a href="staffservlet?action=inventoryManagement" class="text-gray-500 hover:text-gray-700 flex items-center gap-2 text-sm font-medium">
                <svg class="w-5 h-5" fill="none" stroke="currentColor" viewBox="0 0 24 24"><path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M10 19l-7-7m0 0l7-7m-7 7h18"/></svg>
                Back to List
            </a>
        </div>

        <form action="inventory" method="POST" class="space-y-6">
            <input type="hidden" name="action" value="update">
            <input type="hidden" name="inventory_id" value="${inventoryItem.inventory_id}">

            <div class="grid grid-cols-1 sm:grid-cols-2 gap-4">
                <div>
                    <label class="block text-sm font-bold text-gray-700 mb-2">Variant ID <span class="text-red-500">*</span></label>
                    <input type="number" name="variant_id" required min="1" value="${inventoryItem.variant_id}"
                           class="w-full px-4 py-2 border border-gray-200 rounded-lg focus:ring-2 focus:ring-blue-500 outline-none">
                </div>
                <div>
                    <label class="block text-sm font-bold text-gray-700 mb-2">Receipt Item ID <span class="text-red-500">*</span></label>
                    <input type="number" name="receipt_item_id" required min="1" value="${inventoryItem.receipt_item_id}"
                           class="w-full px-4 py-2 border border-gray-200 rounded-lg focus:ring-2 focus:ring-blue-500 outline-none">
                </div>
            </div>

            <div>
                <label class="block text-sm font-bold text-gray-700 mb-2">IMEI <span class="text-red-500">*</span></label>
                <input type="text" name="imei" required maxlength="100" value="${inventoryItem.imei}"
                       class="w-full px-4 py-2 border border-gray-200 rounded-lg focus:ring-2 focus:ring-blue-500 outline-none font-mono">
            </div>

            <div class="grid grid-cols-1 sm:grid-cols-2 gap-4">
                <div>
                    <label class="block text-sm font-bold text-gray-700 mb-2">Import price <span class="text-red-500">*</span></label>
                    <input type="number" name="import_price" required min="0" step="0.01" value="${inventoryItem.import_price}"
                           class="w-full px-4 py-2 border border-gray-200 rounded-lg focus:ring-2 focus:ring-blue-500 outline-none">
                </div>
                <div>
                    <label class="block text-sm font-bold text-gray-700 mb-2">Status</label>
                    <input type="text" name="status" list="statusList" value="${inventoryItem.status}"
                           class="w-full px-4 py-2 border border-gray-200 rounded-lg focus:ring-2 focus:ring-blue-500 outline-none">
                    <datalist id="statusList">
                        <option value="IN_STOCK" />
                        <option value="SOLD" />
                        <option value="RESERVED" />
                        <option value="DAMAGED" />
                        <option value="RETURNED" />
                    </datalist>
                </div>
            </div>

            <div class="flex gap-3 pt-4">
                <button type="submit" class="inline-flex items-center gap-2 h-11 px-5 bg-blue-600 hover:bg-blue-700 text-white rounded-full font-semibold text-sm shadow-md hover:shadow-lg transition-all focus:outline-none focus:ring-4 focus:ring-blue-200">
                    <svg xmlns="http://www.w3.org/2000/svg" class="w-5 h-5" fill="none" viewBox="0 0 24 24" stroke="currentColor" stroke-width="2">
                    <path stroke-linecap="round" stroke-linejoin="round" d="M4 4v5h.582m15.356 2A8.001 8.001 0 004.582 9m0 0H9m11 11v-5h-.581m0 0a8.003 8.003 0 01-15.357-2m15.357 2H15" />
                    </svg>
                    <span>Cập nhật</span>
                </button>

                <a href="staffservlet?action=inventoryManagement" class="inline-flex items-center gap-2 h-11 px-5 bg-gray-200 hover:bg-gray-300 text-gray-800 rounded-full font-semibold text-sm shadow-sm hover:shadow-md transition-all">
                    <svg xmlns="http://www.w3.org/2000/svg" class="w-5 h-5" fill="none" viewBox="0 0 24 24" stroke="currentColor" stroke-width="2">
                    <path stroke-linecap="round" stroke-linejoin="round" d="M10 19l-7-7m0 0l7-7m-7 7h18" />
                    </svg>
                    <span>Hủy</span>
                </a>
            </div>
        </form>
    </div>
</c:if>

