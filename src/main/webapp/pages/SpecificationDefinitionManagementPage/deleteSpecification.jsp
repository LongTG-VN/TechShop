<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="max-w-2xl mx-auto bg-white p-8 rounded-2xl shadow-xl border mt-20">
    <div class="text-center">
        <div class="w-20 h-20 rounded-full bg-orange-50 text-orange-500 flex items-center justify-center mx-auto mb-6">
            <svg class="w-12 h-12" fill="none" stroke="currentColor" viewBox="0 0 24 24"><path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M12 9v2m0 4h.01m-6.938 4h13.856c1.54 0 2.502-1.667 1.732-3L13.732 4c-.77-1.333-2.694-1.333-3.464 0L3.34 16c-.77 1.333.192 3 1.732 3z"/></svg>
        </div>
        <h2 class="text-2xl font-bold text-gray-900 mb-2 uppercase">Confirm Spec Deletion</h2>
        <p class="text-gray-600 mb-8">Are you sure you want to proceed with <strong>${spec.specName}</strong>?</p>
        
        <div class="bg-gray-50 p-4 rounded-xl text-left border mb-8">
            <p class="text-sm font-bold text-gray-400 uppercase">Category: <span class="text-gray-800">${spec.categoryName}</span></p>
            <c:choose>
                <c:when test="${usageCount > 0}">
                    <div class="mt-4 p-3 bg-orange-100 text-orange-700 border-l-4 border-orange-500 text-sm">
                        ⚠️ This specification is used in <strong>${usageCount} product values</strong>. It will be <strong>DEACTIVATED</strong> to maintain historical data.
                    </div>
                </c:when>
                <c:otherwise>
                    <div class="mt-4 p-3 bg-red-100 text-red-700 border-l-4 border-red-500 text-sm">
                        🗑️ This spec is not in use. It will be <strong>PERMANENTLY DELETED</strong>.
                    </div>
                </c:otherwise>
            </c:choose>
        </div>

        <form action="specificationServlet" method="POST" class="flex justify-center gap-4">
            <input type="hidden" name="action" value="delete">
            <input type="hidden" name="specId" value="${spec.specId}">
            <a href="specificationServlet?action=all" class="px-6 py-2 border rounded-lg font-bold text-gray-500 hover:bg-gray-50">Cancel</a>
            <button type="submit" class="px-10 py-2 bg-red-600 text-white font-bold rounded-lg hover:bg-red-700 shadow-lg transition-transform hover:-translate-y-0.5">Confirm</button>
        </form>
    </div>
</div>