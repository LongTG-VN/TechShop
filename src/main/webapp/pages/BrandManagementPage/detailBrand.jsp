<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="max-w-2xl mx-auto bg-white p-8 rounded-2xl shadow-xl border border-gray-100 mt-10">
    <%-- TIÊU ĐỀ --%>
    <div class="flex items-center gap-3 mb-8 border-b pb-4">
        <div class="p-2 bg-blue-50 rounded-lg">
            <svg class="w-6 h-6 text-blue-600" fill="none" stroke="currentColor" viewBox="0 0 24 24">
            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M13 16h-1v-4h-1m1-4h.01M21 12a9 9 0 11-18 0 9 9 0 0118 0z" />
            </svg>
        </div>
        <h2 class="text-2xl font-bold text-gray-800 uppercase tracking-wide">Brand Details</h2>
    </div>

    <div class="space-y-8">
        <%-- HÀNG 1: ID VÀ BRAND NAME --%>
        <div class="grid grid-cols-12 gap-6">
            <div class="col-span-4">
                <label class="block mb-2 text-xs font-bold text-gray-500 uppercase">ID</label>
                <div class="p-3 bg-gray-50 border border-gray-200 rounded-xl font-mono text-blue-600 font-bold">
                    #${brand.brandId}
                </div>
            </div>
            <div class="col-span-8">
                <label class="block mb-2 text-xs font-bold text-gray-500 uppercase">Brand Name</label>
                <div class="p-3 bg-gray-50 border border-gray-200 rounded-xl font-bold text-gray-800">
                    ${brand.brandName}
                </div>
            </div>
        </div>

        <%-- HÀNG 2: STATUS VÀ INVENTORY --%>
        <div class="grid grid-cols-12 gap-6 items-start">
            <div class="col-span-4">
                <label class="block mb-2 text-xs font-bold text-gray-500 uppercase">Status</label>
                <c:choose>
                    <c:when test="${brand.isActive}">
                        <span class="inline-flex items-center px-4 py-2 bg-green-50 text-green-700 text-xs font-bold rounded-full border border-green-200 uppercase">
                            Active
                        </span>
                    </c:when>
                    <c:otherwise>
                        <span class="inline-flex items-center px-4 py-2 bg-red-50 text-red-700 text-xs font-bold rounded-full border border-red-200 uppercase">
                            Inactive
                        </span>
                    </c:otherwise>
                </c:choose>
            </div>

            <div class="col-span-8">
                <label class="block mb-2 text-xs font-bold text-gray-500 uppercase">Inventory</label>
                <div class="flex items-baseline gap-1 pt-1">
                    <span class="text-xl font-black text-blue-600">${productCount}</span>
                    <span class="text-[11px] font-bold text-blue-900/40 uppercase tracking-tight">Products linked</span>
                </div>
            </div>
        </div>

        <%-- HÀNG 3: LOGO --%>
        <div>
            <label class="block mb-2 text-xs font-bold text-gray-500 uppercase">Brand Logo [cite: 81]</label>
            <div class="flex flex-col items-center justify-center p-8 bg-gray-50 rounded-3xl border border-gray-100 shadow-inner ">
                <div class="relative [cite: 82]">
                    <c:choose>
                        <c:when test="${not empty brand.imageUrl}">
                            <img src="${pageContext.request.contextPath}/${brand.imageUrl}" 
                                 class="w-40 h-40 object-contain rounded-2xl border-4 border-white shadow-xl bg-white p-3 ">
                        </c:when>
                        <c:otherwise>
                            <div class="w-40 h-40 bg-white rounded-2xl flex items-center justify-center border-2 border-dashed border-gray-200 text-gray-300 [cite: 84]">
                                <svg class="w-12 h-12" fill="none" stroke="currentColor" viewBox="0 0 24 24"><path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M4 16l4.586-4.586a2 2 0 012.828 0L16 16m-2-2l1.586-1.586a2 2 0 012.828 0L20 14m-6-6h.01M6 20h12a2 2 0 002-2V6a2 2 0 00-2-2H6a2 2 0 00-2 2v12a2 2 0 002 2z"/></svg>
                            </div>
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>
        </div>

        <%-- NÚT ĐIỀU HƯỚNG --%>
        <div class="flex justify-end gap-3 pt-6 border-t border-gray-100">
            <a href="brandServlet?action=all" 
               class="inline-flex items-center gap-2 px-6 py-2.5 bg-white text-gray-600 rounded-xl hover:bg-gray-50 transition-all font-bold border border-gray-200 text-sm">
                <svg class="w-4 h-4" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M10 19l-7-7m0 0l7-7m-7 7h18" />
                </svg>
                Back to List
            </a>

            <a href="brandServlet?action=edit&id=${brand.brandId}" 
               class="inline-flex items-center gap-2 px-8 py-2.5 bg-blue-600 text-white rounded-xl hover:bg-blue-700 shadow-lg shadow-blue-200 transition-all font-bold text-sm">
                <svg class="w-4 h-4" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M11 5H6a2 2 0 00-2 2v11a2 2 0 002 2h11a2 2 0 002-2v-5m-1.414-9.414a2 2 0 112.828 2.828L11.828 15H9v-2.828l8.586-8.586z" />
                </svg>
                Edit Brand
            </a>
        </div>
    </div>
</div>