<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="max-w-2xl mx-auto bg-white p-8 rounded-xl shadow-md border border-gray-100 mt-10">
    <%-- TIÊU ĐỀ --%>
    <h2 class="text-2xl font-bold mb-6 text-gray-800 uppercase tracking-wide">Brand Information</h2>

    <div class="space-y-6">
        <%-- GRID THÔNG TIN: BRAND ID & BRAND NAME --%>
        <div class="grid grid-cols-12 gap-4 mb-4">
            <div class="col-span-3">
                <label class="block mb-2 text-sm font-medium text-gray-700">Brand ID</label>
                <input type="text" value="#${brand.brandId}" 
                       class="w-full p-2 border border-gray-300 rounded-lg bg-gray-50 cursor-not-allowed font-bold text-blue-600" 
                       readonly>
            </div>
            <div class="col-span-9">
                <label class="block mb-2 text-sm font-medium text-gray-700">Brand Name</label>
                <input type="text" value="${brand.brandName}" 
                       class="w-full p-2 border border-gray-300 rounded-lg bg-gray-50 cursor-not-allowed font-semibold text-blue-600" 
                       readonly>
            </div>
        </div>

        <%-- TRẠNG THÁI (STATUS) --%>
        <div class="mb-4">
            <label class="block mb-2 text-sm font-medium text-gray-700">Status</label>
            <div class="flex gap-4 p-2 bg-gray-50 rounded-lg w-fit border border-gray-100">
                <label class="flex items-center cursor-not-allowed">
                    <input type="radio" name="isActive" value="true" 
                           ${brand.isActive ? 'checked' : ''} disabled 
                           class="w-4 h-4 text-blue-600 border-gray-300"> 
                    <span class="ml-2 text-sm font-medium ${brand.isActive ? 'text-green-600' : 'text-gray-400'}">
                        Active
                    </span>
                </label>
                <label class="flex items-center cursor-not-allowed">
                    <input type="radio" name="isActive" value="false" 
                           ${!brand.isActive ? 'checked' : ''} disabled
                           class="w-4 h-4 text-blue-600 border-gray-300"> 
                    <span class="ml-2 text-sm font-medium ${!brand.isActive ? 'text-red-600' : 'text-gray-400'}">
                        Inactive
                    </span>
                </label>
            </div>
        </div>

        <%-- SẢN PHẨM LIÊN KẾT (LINKED PRODUCTS) --%>
        <div class="mb-6 p-4 bg-purple-50 rounded-xl border border-purple-100 flex items-center justify-between shadow-sm">
            <div class="flex items-center gap-3">
                <div class="p-2.5 bg-purple-600 rounded-lg text-white shadow-md">
                    <svg class="w-5 h-5" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                        <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M20 7l-8-4-8 4m16 0l-8 4m8-4v10l-8 4m0-10L4 7m8 4v10M4 7v10l8 4" />
                    </svg>
                </div>
                <div>
                    <p class="text-[10px] font-bold text-purple-600 uppercase tracking-widest">Linked Products</p>
                    <p class="text-xl font-extrabold text-purple-900 leading-tight">${productCount} <span class="text-xs font-medium uppercase">Items</span></p>
                </div>
            </div>
            <div class="text-[10px] text-purple-400 italic font-medium">
                Real-time Database
            </div>
        </div>

        <hr class="border-gray-100">

        <%-- NÚT ĐIỀU HƯỚNG --%>
        <div class="flex justify-end gap-3 pt-2">
            <%-- Nút quay lại danh sách Brand --%>
            <a href="brandServlet?action=all" 
               class="inline-flex items-center justify-center gap-2 px-8 py-2 bg-gray-100 text-gray-600 rounded-lg hover:bg-gray-200 transition-all font-medium border border-gray-200 text-sm">
                <svg class="w-4 h-4" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                    <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M10 19l-7-7m0 0l7-7m-7 7h18"/>
                </svg>
                Cancel
            </a>

            <%-- Nút chuyển sang trang chỉnh sửa Brand --%>
            <a href="brandServlet?action=edit&id=${brand.brandId}" 
               class="px-6 py-2 bg-blue-600 text-white rounded-lg hover:bg-blue-700 shadow-lg shadow-blue-100 font-medium transition-all flex items-center gap-2 text-sm">
                <svg class="w-4 h-4" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                    <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M11 5H6a2 2 0 00-2 2v11a2 2 0 002 2h11a2 2 0 002-2v-5m-1.414-9.414a2 2 0 112.828 2.828L11.828 15H9v-2.828l8.586-8.586z" />
                </svg>
                Edit Brand
            </a>
        </div>
    </div>
</div>