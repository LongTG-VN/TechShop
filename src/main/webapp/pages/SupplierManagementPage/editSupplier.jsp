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

<c:if test="${empty supplier}">
    <div class="p-6 text-center text-red-600">Không tìm thấy nhà cung cấp.</div>
    <a href="staffservlet?action=supplierManagement" class="text-blue-600 underline">Quay lại danh sách</a>
</c:if>

<c:if test="${not empty supplier}">
    <div class="max-w-2xl mx-auto bg-white p-8 rounded-2xl shadow-xl border border-gray-100 mt-6">
        <div class="flex justify-between items-start border-b pb-6 mb-8">
            <div>
                <h2 class="text-2xl font-extrabold text-gray-900 uppercase tracking-tight">Edit Supplier</h2>
                <p class="text-gray-500 mt-1">ID: <span class="font-mono font-bold text-blue-600">#${supplier.supplier_id}</span></p>
            </div>
            <a href="staffservlet?action=supplierManagement" class="text-gray-500 hover:text-gray-700 flex items-center gap-2 text-sm font-medium">
                <svg class="w-5 h-5" fill="none" stroke="currentColor" viewBox="0 0 24 24"><path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M10 19l-7-7m0 0l7-7m-7 7h18"/></svg>
                Back to List
            </a>
        </div>

        <form action="supplier" method="POST" class="space-y-6">
            <input type="hidden" name="action" value="update">
            <input type="hidden" name="supplier_id" value="${supplier.supplier_id}">
            <div>
                <label class="block text-sm font-bold text-gray-700 mb-2">Supplier Name <span class="text-red-500">*</span></label>
                <input type="text" name="supplier_name" required maxlength="100" value="${supplier.supplier_name}"
                       class="w-full px-4 py-2 border border-gray-200 rounded-lg focus:ring-2 focus:ring-blue-500 outline-none"
                       placeholder="Tên nhà cung cấp">
            </div>
            <div>
                <label class="block text-sm font-bold text-gray-700 mb-2">Phone <span class="text-red-500">*</span></label>
                <input type="tel" name="phone" required maxlength="10" pattern="[0-9]{10}" inputmode="numeric" title="Nhập đúng 10 chữ số" value="${supplier.phone}"
                       class="w-full px-4 py-2 border border-gray-200 rounded-lg focus:ring-2 focus:ring-blue-500 outline-none"
                       placeholder="10 chữ số" oninput="this.value=this.value.replace(/[^0-9]/g,'')">
            </div>
            <div>
                <label class="flex items-center gap-2 cursor-pointer">
                    <input type="checkbox" name="is_active" value="1" ${supplier.is_active ? 'checked' : ''}
                           class="w-4 h-4 text-blue-600 rounded focus:ring-blue-500">
                    <span class="text-sm font-medium text-gray-700">Đang hợp tác (Active)</span>
                </label>
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
