<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="max-w-2xl mx-auto bg-white p-10 rounded-3xl shadow-2xl border-4 border-red-50 mt-20 text-center">
    <div class="w-24 h-24 bg-red-100 text-red-600 rounded-full flex items-center justify-center mx-auto mb-6 animate-pulse">
        <svg class="w-12 h-12" fill="none" stroke="currentColor" viewBox="0 0 24 24"><path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M12 9v2m0 4h.01m-6.938 4h13.856c1.54 0 2.502-1.667 1.732-3L13.732 4c-.77-1.333-2.694-1.333-3.464 0L3.34 16c-.77 1.333.192 3 1.732 3z"/></svg>
    </div>
    
    <h2 class="text-3xl font-black text-gray-900 uppercase mb-4 tracking-tighter">Confirm Deletion</h2>
    <p class="text-gray-600 font-bold mb-8 italic">Are you sure you want to proceed with variant <span class="text-red-600 font-black">${variant.sku}</span>?</p>

    <div class="grid grid-cols-2 gap-4 p-5 bg-gray-50 rounded-2xl border-2 border-gray-100 text-left mb-8">
        <div>
            <p class="text-xs font-black text-gray-400 uppercase tracking-widest">Variant ID</p>
            <p class="text-lg font-black text-blue-600">#${variant.variantId}</p>
        </div>
        <div>
            <p class="text-xs font-black text-gray-400 uppercase tracking-widest">SKU Identifier</p>
            <p class="text-lg font-black text-gray-800 uppercase">${variant.sku}</p>
        </div>
    </div>

    <c:choose>
        <c:when test="${inventoryCount > 0}">
            <div class="p-5 bg-orange-50 border-l-8 border-orange-500 text-orange-800 text-sm text-left mb-8 rounded-xl font-bold">
                <p class="mb-1 text-base uppercase font-black">⚠️ System Warning:</p>
                This variant has <strong>${inventoryCount} items in stock</strong>. 
                To maintain database integrity, it will be <strong>hidden (Set to INACTIVE)</strong> instead of being deleted.
            </div>
        </c:when>
        <c:otherwise>
            <div class="p-5 bg-red-50 border-l-8 border-red-500 text-red-700 text-sm text-left mb-8 rounded-xl font-bold">
                <p class="mb-1 text-base uppercase font-black">🗑️ Safe to Remove:</p>
                No stock detected. This variant will be <strong>permanently deleted</strong>.
            </div>
        </c:otherwise>
    </c:choose>

    <form action="variantServlet" method="POST" class="flex justify-center gap-6">
        <input type="hidden" name="action" value="delete">
        <input type="hidden" name="variantId" value="${variant.variantId}">
        
        <a href="variantServlet?action=all" class="px-8 py-3 bg-gray-100 text-gray-600 font-black rounded-xl hover:bg-gray-200 transition-all uppercase text-sm">Cancel</a>
        <button type="submit" class="px-10 py-3 bg-red-600 text-white font-black rounded-xl hover:bg-red-700 shadow-xl shadow-red-200 transition-all uppercase text-sm">Confirm Action</button>
    </form>
</div>