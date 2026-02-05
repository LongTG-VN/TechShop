<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%-- 1. THÔNG BÁO TOAST --%>
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

<div class="max-w-2xl mx-auto bg-white p-8 rounded-xl shadow-md border border-gray-100 mt-10">
    <h2 class="text-2xl font-bold mb-6 text-gray-800 uppercase tracking-wide">EDIT BRAND</h2>

    <form action="brandServlet" method="POST">
        <input type="hidden" name="action" value="update">
        <input type="hidden" name="brandId" value="${brand.brandId}">

        <div class="space-y-6">
            <%-- Cột ID và Tên Brand --%>
            <div class="grid grid-cols-12 gap-4 mb-4">
                <div class="col-span-3">
                    <label class="block mb-2 text-sm font-medium text-gray-700">Brand ID</label>
                    <input type="text" value="#${brand.brandId}" 
                           class="w-full p-2 border border-gray-300 rounded-lg bg-gray-50 cursor-not-allowed font-bold text-blue-600" 
                           readonly>
                </div>
                <div class="col-span-9">
                    <label class="block mb-2 text-sm font-medium text-gray-700">Brand Name</label>
                    <input type="text" name="brandName" value="${brand.brandName}" 
                           class="w-full p-2 border border-gray-300 rounded-lg bg-gray-50 focus:ring-2 focus:ring-blue-500 outline-none font-semibold text-blue-600" 
                           required>
                </div>
            </div>

            <%-- Trạng thái Hiển thị --%>
            <div class="mb-6">
                <label class="block mb-2 text-sm font-medium text-gray-700">Status</label>
                <div class="flex gap-4 p-2 bg-gray-50 rounded-lg w-fit border border-gray-100">
                    <label class="flex items-center cursor-pointer">
                        <input type="radio" name="isActive" value="1" 
                               ${brand.isActive ? 'checked' : ''} 
                               class="w-4 h-4 text-blue-600 border-gray-300 focus:ring-blue-500"> 
                        <span class="ml-2 text-sm font-medium text-gray-700">Active</span>
                    </label>
                    <label class="flex items-center cursor-pointer">
                        <input type="radio" name="isActive" value="0" 
                               ${!brand.isActive ? 'checked' : ''} 
                               class="w-4 h-4 text-blue-600 border-gray-300 focus:ring-blue-500"> 
                        <span class="ml-2 text-sm font-medium text-gray-700">Inactive</span>
                    </label>
                </div>
            </div>

            <hr class="border-gray-100">

            <%-- Nút điều hướng --%>
            <div class="flex justify-end gap-3 pt-2">
                <a href="brandServlet?action=all" 
                   class="inline-flex items-center justify-center gap-2 px-8 py-2 bg-gray-100 text-gray-600 rounded-lg hover:bg-gray-200 transition-all font-medium border border-gray-200 text-sm">
                    <svg class="w-4 h-4" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                        <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M10 19l-7-7m0 0l7-7m-7 7h18"/>
                    </svg>
                    Cancel
                </a>

                <button type="submit" 
                        class="px-6 py-2 bg-blue-600 text-white rounded-lg hover:bg-blue-700 shadow-lg shadow-blue-100 font-medium transition-all flex items-center gap-2 text-sm">
                    <svg class="w-4 h-4" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                        <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M4 16v1a3 3 0 003 3h10a3 3 0 003-3v-1m-4-8l-4-4m0 0L8 8m4-4v12" />
                    </svg>
                    Update Brand
                </button>
            </div>
        </div>
    </form>
</div>