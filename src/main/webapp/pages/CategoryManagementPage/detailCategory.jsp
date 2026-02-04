<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="max-w-2xl mx-auto bg-white p-8 rounded-xl shadow-md border border-gray-100 mt-10">
    <h2 class="text-2xl font-bold mb-6">Detail Category #${category.categoryId}</h2>
    <div class="space-y-6">
        <div class="mb-4">
            <label class="block mb-2 text-sm font-medium">Category Name</label>
            <input type="text" name="categoryName" value="${category.categoryName}" 
                   class="w-full p-2 border border-gray-300 rounded-lg bg-gray-50 cursor-not-allowed" 
                   readonly>
        </div>

        <div class="mb-6">
            <label class="block mb-2 text-sm font-medium">Status</label>
            <div class="flex gap-4">
                <label class="flex items-center">
                    <input type="radio" name="isActive" value="true" 
                           ${category.isActive ? 'checked' : ''} disabled> 
                    <span class="ml-2 text-sm ${category.isActive ? 'text-gray-900' : 'text-gray-400'}">Active</span>
                </label>
                <label class="flex items-center">
                    <input type="radio" name="isActive" value="false" 
                           ${!category.isActive ? 'checked' : ''} disabled> 
                    <span class="ml-2 text-sm ${!category.isActive ? 'text-gray-900' : 'text-gray-400'}">Inactive</span>
                </label>
            </div>
        </div>

        <%-- NÚT THAO TÁC --%>
        <div class="flex justify-end gap-3 pt-4">
            <a href="categoryServlet?action=all" 
               class="px-6 py-2 bg-gray-200 text-gray-700 rounded-lg hover:bg-gray-300 transition-colors font-medium">
               Cancel
            </a>
            
            <%-- Nút Update chuyển sang trang Edit --%>
            <a href="categoryServlet?action=edit&id=${category.categoryId}" 
               class="px-6 py-2 bg-blue-600 text-white rounded-lg hover:bg-blue-700 shadow-md font-medium transition-colors">
               Edit To Category
            </a>
        </div>
    </div>
</div>