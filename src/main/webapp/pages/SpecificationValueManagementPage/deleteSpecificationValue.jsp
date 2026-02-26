<%@page contentType="text/html" pageEncoding="UTF-8"%>

<div class="max-w-lg mx-auto bg-white p-10 rounded-2xl shadow-2xl border border-red-100 mt-20 text-center">
    <div class="w-24 h-24 bg-red-50 text-red-500 rounded-full flex items-center justify-center mx-auto mb-6 shadow-inner">
        <svg class="w-12 h-12" fill="none" stroke="currentColor" viewBox="0 0 24 24"><path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M19 7l-.867 12.142A2 2 0 0116.138 21H7.862a2 2 0 01-1.995-1.858L5 7m5 4v6m4-6v6m1-10V4a1 1 0 00-1-1h-4a1 1 0 00-1 1v3M4 7h16" /></svg>
    </div>
    <h2 class="text-2xl font-black text-gray-900 mb-2 uppercase">Confirm Removal</h2>
    <p class="text-gray-500 leading-relaxed mb-8">Are you sure you want to remove <span class="text-red-600 font-bold">${val.specName}</span> from <span class="text-gray-900 font-bold">${val.productName}</span>?</p>
    
    <form action="specificationValueServlet" method="POST" class="flex justify-center gap-4">
        <input type="hidden" name="action" value="delete">
        <input type="hidden" name="productId" value="${val.productId}">
        <input type="hidden" name="specId" value="${val.specId}">
        <a href="specificationValueServlet?action=all" class="px-6 py-2.5 text-gray-500 font-bold border-2 border-gray-100 rounded-xl hover:bg-gray-50 transition-all">Go Back</a>
        <button type="submit" class="px-10 py-2.5 bg-red-600 text-white font-bold rounded-xl hover:bg-red-700 shadow-lg hover:shadow-red-200 transition-all">Remove Now</button>
    </form>
</div>