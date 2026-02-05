<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%-- 1. HỆ THỐNG THÔNG BÁO TOAST --%>
<c:if test="${not empty sessionScope.msg}">
    <div id="toast-notification" class="fixed top-10 left-1/2 -translate-x-1/2 z-[9999] min-w-[320px] transition-all duration-500">
        <div class="flex items-center justify-center p-4 rounded-xl shadow-2xl border-2 animate-bounce
             ${sessionScope.msgType == 'danger' ? 'bg-red-50 text-red-800 border-red-200' : 'bg-green-50 text-green-800 border-green-200'}">

            <div class="flex-shrink-0 mr-3">
                <c:choose>
                    <c:when test="${sessionScope.msgType == 'danger'}">
                        <svg class="w-6 h-6" fill="currentColor" viewBox="0 0 20 20">
                            <path fill-rule="evenodd" d="M10 18a8 8 0 100-16 8 8 0 000 16zM8.707 7.293a1 1 0 00-1.414 1.414L8.586 10l-1.293 1.293a1 1 0 101.414 1.414L10 11.414l1.293 1.293a1 1 0 001.414-1.414L11.414 10l1.293-1.293a1 1 0 00-1.414-1.414L10 8.586 8.707 7.293z" clip-rule="evenodd"></path>
                        </svg>
                    </c:when>
                    <c:otherwise>
                        <svg class="w-6 h-6" fill="currentColor" viewBox="0 0 20 20">
                            <path fill-rule="evenodd" d="M10 18a8 8 0 100-16 8 8 0 000 16zm3.707-9.293a1 1 0 00-1.414-1.414L9 10.586 7.707 9.293a1 1 0 00-1.414 1.414l2 2a1 1 0 001.414 0l4-4z" clip-rule="evenodd"></path>
                        </svg>
                    </c:otherwise>
                </c:choose>
            </div>
            <span class="font-bold uppercase tracking-wider text-sm">${sessionScope.msg}</span>
        </div>
    </div>
    <c:remove var="msg" scope="session" />
    <c:remove var="msgType" scope="session" />
    <script>
        setTimeout(() => {
            const toast = document.getElementById('toast-notification');
            if (toast) {
                toast.style.opacity = '0';
                toast.style.transform = 'translate(-50%, -20px)';
                setTimeout(() => toast.remove(), 500);
            }
        }, 3000);
    </script>
</c:if>

<%-- 2. LAYOUT XÁC NHẬN XÓA BRAND --%>
<div class="max-w-2xl mx-auto bg-white p-8 rounded-2xl shadow-xl border border-gray-100 mt-20 ${productCount > 0 ? 'border-orange-100' : 'border-red-100'}">
    
    <%-- Header & Icon --%>
    <div class="flex items-center gap-4 mb-6">
        <div class="p-3 ${productCount > 0 ? 'bg-orange-100' : 'bg-red-100'} rounded-full">
            <c:choose>
                <c:when test="${productCount > 0}">
                    <svg class="w-6 h-6 text-orange-600" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                        <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M12 9v2m0 4h.01m-6.938 4h13.856c1.54 0 2.502-1.667 1.732-3L13.732 4c-.77-1.333-2.694-1.333-3.464 0L3.34 16c-.77 1.333.192 3 1.732 3z"/>
                    </svg>
                </c:when>
                <c:otherwise>
                    <svg class="w-6 h-6 text-red-600" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                        <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M19 7l-.867 12.142A2 2 0 0116.138 21H7.862a2 2 0 01-1.995-1.858L5 7m5 4v6m4-6v6m1-10V4a1 1 0 00-1-1h-4a1 1 0 00-1 1v3M4 7h16"/>
                    </svg>
                </c:otherwise>
            </c:choose>
        </div>
        <h2 class="text-2xl font-bold text-gray-800 uppercase tracking-tight">Confirm Action</h2>
    </div>

    <%-- Thông báo Logic --%>
    <div class="mb-6">
        <c:choose>
            <c:when test="${productCount > 0}">
                <div class="p-4 bg-orange-50 border-l-4 border-orange-500 text-orange-700">
                    <p class="font-bold text-lg">Soft Delete Recommended</p>
                    <p>This brand is linked to <strong>${productCount} products</strong>. To prevent data errors, it will be set to <span class="font-bold underline">Inactive</span> and hidden from the store.</p>
                </div>
            </c:when>
            <c:otherwise>
                <div class="p-4 bg-red-50 border-l-4 border-red-500 text-red-700">
                    <p class="font-bold text-lg">Permanent Delete</p>
                    <p>This brand is empty. Confirming this will <span class="font-bold underline">permanently remove</span> it from the database.</p>
                </div>
            </c:otherwise>
        </c:choose>
    </div>

    <%-- Grid Thông tin --%>
    <div class="bg-gray-50 p-6 rounded-lg mb-6 border border-gray-200">
        <div class="grid grid-cols-12 gap-6">
            <div class="col-span-3 text-left">
                <label class="block mb-1 text-xs font-bold text-gray-400 uppercase tracking-widest">Brand ID</label>
                <p class="text-lg font-bold text-blue-500">#${brand.brandId}</p>
            </div>
            <div class="col-span-5 text-left">
                <label class="block mb-1 text-xs font-bold text-gray-400 uppercase tracking-widest">Brand Name</label>
                <p class="text-lg font-bold text-gray-800">${brand.brandName}</p>
            </div>
            <div class="col-span-4 border-l border-gray-200 pl-6 text-right">
                <label class="block mb-1 text-xs font-bold text-gray-400 uppercase tracking-widest">Linked Products</label>
                <p class="text-lg font-extrabold text-purple-700">${productCount} Items</p>
            </div>
        </div>
    </div>

    <%-- Nút bấm --%>
    <form action="brandServlet" method="POST">
        <input type="hidden" name="action" value="delete">
        <input type="hidden" name="brandId" value="${brand.brandId}">

        <div class="flex justify-end gap-3 pt-4 border-t border-gray-100">
            <a href="brandServlet?action=all" 
               class="inline-flex items-center justify-center gap-2 px-8 py-2 bg-white border border-gray-300 text-gray-700 rounded-lg hover:bg-gray-50 transition-colors font-medium">
                <svg class="w-4 h-4" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                    <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M10 19l-7-7m0 0l7-7m-7 7h18"/>
                </svg>
                Cancel
            </a>

            <button type="submit" 
                    class="inline-flex items-center gap-2 px-6 py-2 text-white rounded-lg shadow-md font-medium transition-all focus:ring-4 
                    ${productCount > 0 ? 'bg-orange-600 hover:bg-orange-700 focus:ring-orange-200' : 'bg-red-600 hover:bg-red-700 focus:ring-red-200'}">
                <c:choose>
                    <c:when test="${productCount > 0}">
                        <svg class="w-4 h-4" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M10 9v6m4-6v6m7-3a9 9 0 11-18 0 9 9 0 0118 0z" />
                        </svg>
                        Deactivate Brand
                    </c:when>
                    <c:otherwise>
                        <svg class="w-4 h-4" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M19 7l-.867 12.142A2 2 0 0116.138 21H7.862a2 2 0 01-1.995-1.858L5 7m5 4v6m4-6v6m1-10V4a1 1 0 00-1-1h-4a1 1 0 00-1 1v3M4 7h16" />
                        </svg>
                        Delete Permanently
                    </c:otherwise>
                </c:choose>
            </button>
        </div>
    </form>
</div>