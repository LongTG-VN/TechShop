<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="max-w-2xl mx-auto bg-white p-8 rounded-xl shadow-md border mt-10 ${productCount > 0 ? 'border-orange-100' : 'border-red-100'}">
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
        <h2 class="text-2xl font-bold text-gray-800">Confirm Action #${category.categoryId}</h2>
    </div>

    <div class="mb-6">
        <c:choose>
            <c:when test="${productCount > 0}">
                <div class="p-4 bg-orange-50 border-l-4 border-orange-500 text-orange-700">
                    <p class="font-bold text-lg">Soft Delete Recommended</p>
                    <p>This category is linked to <strong>${productCount} products</strong>. To prevent data errors, it will be set to <span class="font-bold underline">Inactive</span> and hidden from the store.</p>
                </div>
            </c:when>
            <c:otherwise>
                <div class="p-4 bg-red-50 border-l-4 border-red-500 text-red-700">
                    <p class="font-bold text-lg">Permanent Delete</p>
                    <p>This category is empty. Confirming this will <span class="font-bold underline">permanently remove</span> it from the database.</p>
                </div>
            </c:otherwise>
        </c:choose>
    </div>

    <div class="space-y-4 bg-gray-50 p-6 rounded-lg mb-6 border border-gray-200">
        <div class="grid grid-cols-2 gap-4">
            <div>
                <label class="block mb-1 text-xs font-semibold uppercase text-gray-500">Category Name</label>
                <p class="text-lg font-medium text-gray-900">${category.categoryName}</p>
            </div>
            <div>
                <label class="block mb-1 text-xs font-semibold uppercase text-gray-500">Linked Products</label>
                <p class="text-lg font-medium text-gray-900">${productCount} Items</p>
            </div>
        </div>
    </div>

    <form action="categoryServlet" method="POST">
        <input type="hidden" name="action" value="delete">
        <input type="hidden" name="categoryId" value="${category.categoryId}">

        <div class="flex justify-end gap-3 pt-4">
            <a href="categoryServlet?action=all" 
               class="px-6 py-2 bg-white border border-gray-300 text-gray-700 rounded-lg hover:bg-gray-50 transition-colors font-medium">
                Cancel
            </a>

            <button type="submit" 
                    class="px-6 py-2 text-white rounded-lg shadow-md font-medium transition-all focus:ring-4 
                    ${productCount > 0 ? 'bg-orange-600 hover:bg-orange-700 focus:ring-orange-200' : 'bg-red-600 hover:bg-red-700 focus:ring-red-200'}">
                <c:choose>
                    <c:when test="${productCount > 0}">Deactivate Category</c:when>
                    <c:otherwise>Delete Permanently</c:otherwise>
                </c:choose>
            </button>
        </div>
    </form>
</div>