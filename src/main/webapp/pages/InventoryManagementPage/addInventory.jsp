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

<div class="max-w-2xl mx-auto bg-white p-8 rounded-2xl shadow-xl border border-gray-100 mt-6">
    <div class="flex justify-between items-start border-b pb-6 mb-8">
        <div>
            <h2 class="text-2xl font-extrabold text-gray-900 uppercase tracking-tight">Add Inventory Item</h2>
            <p class="text-gray-500 mt-1">Thêm một bản ghi vào bảng <span class="font-mono">inventory_items</span></p>
        </div>
        <a href="staffservlet?action=inventoryManagement" class="text-gray-500 hover:text-gray-700 flex items-center gap-2 text-sm font-medium">
            <svg class="w-5 h-5" fill="none" stroke="currentColor" viewBox="0 0 24 24"><path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M10 19l-7-7m0 0l7-7m-7 7h18"/></svg>
            Back to List
        </a>
    </div>

    <form action="inventory" method="POST" class="space-y-6">
        <input type="hidden" name="action" value="add">

        <div class="grid grid-cols-1 sm:grid-cols-2 gap-4">
            <div>
                <label class="block text-sm font-bold text-gray-700 mb-2">Variant ID <span class="text-red-500">*</span></label>
                <input type="number" name="variant_id" required min="1"
                       class="w-full px-4 py-2 border border-gray-200 rounded-lg focus:ring-2 focus:ring-blue-500 outline-none"
                       placeholder="VD: 1">
            </div>
            <div>
                <label class="block text-sm font-bold text-gray-700 mb-2">Receipt Item ID <span class="text-red-500">*</span></label>
                <input type="number" name="receipt_item_id" required min="1"
                       class="w-full px-4 py-2 border border-gray-200 rounded-lg focus:ring-2 focus:ring-blue-500 outline-none"
                       placeholder="VD: 1">
            </div>
        </div>

        <div>
            <label class="block text-sm font-bold text-gray-700 mb-2">IMEI <span class="text-red-500">*</span></label>
            <input type="text" name="imei" required maxlength="100"
                   class="w-full px-4 py-2 border border-gray-200 rounded-lg focus:ring-2 focus:ring-blue-500 outline-none font-mono"
                   placeholder="VD: IMEI-TEST-001">
            <p class="text-xs text-gray-500 mt-1">IMEI không được trùng.</p>
        </div>

        <div class="grid grid-cols-1 sm:grid-cols-2 gap-4">
            <div>
                <label class="block text-sm font-bold text-gray-700 mb-2">Import price <span class="text-red-500">*</span></label>
                <input type="number" name="import_price" required min="0" step="0.01"
                       class="w-full px-4 py-2 border border-gray-200 rounded-lg focus:ring-2 focus:ring-blue-500 outline-none"
                       placeholder="VD: 15000000">
            </div>
            <div>
                <label class="block text-sm font-bold text-gray-700 mb-2">Status</label>
                <input type="text" name="status" list="statusList"
                       class="w-full px-4 py-2 border border-gray-200 rounded-lg focus:ring-2 focus:ring-blue-500 outline-none"
                       placeholder="Mặc định: IN_STOCK">
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
            <button type="submit" class="inline-flex items-center gap-2 px-4 py-2 bg-blue-600 hover:bg-blue-700 text-white rounded-full font-medium">
                <span class="inline-flex items-center justify-center w-7 h-7 bg-white rounded-full text-blue-600">
                    <svg xmlns="http://www.w3.org/2000/svg" class="w-4 h-4" viewBox="0 0 20 20" fill="currentColor">
                    <path fill-rule="evenodd" d="M10 5a1 1 0 011 1v3h3a1 1 0 110 2h-3v3a1 1 0 11-2 0v-3H6a1 1 0 110-2h3V6a1 1 0 011-1z" clip-rule="evenodd" />
                    </svg>
                </span>
                <span class="hidden sm:inline">Thêm</span>
            </button>

            <a href="staffservlet?action=inventoryManagement" class="inline-flex items-center gap-2 px-4 py-2 bg-gray-200 hover:bg-gray-300 text-gray-700 rounded-full font-medium">
                <span class="inline-flex items-center justify-center w-7 h-7 bg-white rounded-full text-gray-700">
                    <svg class="w-4 h-4" fill="none" stroke="currentColor" viewBox="0 0 24 24"><path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M10 19l-7-7m0 0l7-7m-7 7h18"/></svg>
                </span>
                <span class="hidden sm:inline">Hủy</span>
            </a>
        </div>
    </form>
</div>

