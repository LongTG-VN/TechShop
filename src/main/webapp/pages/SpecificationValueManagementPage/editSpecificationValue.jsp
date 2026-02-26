<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="max-w-2xl mx-auto bg-white p-8 rounded-2xl shadow-xl border mt-10">
    <h2 class="text-2xl font-bold text-gray-900 mb-8 uppercase text-center border-b pb-4 tracking-tight">Edit Spec Value</h2>
    <form action="specificationValueServlet" method="POST" class="space-y-6">
        <input type="hidden" name="action" value="update">
        <input type="hidden" name="productId" value="${val.productId}">
        <input type="hidden" name="specId" value="${val.specId}">

        <div class="grid grid-cols-1 md:grid-cols-2 gap-6">
            <div class="space-y-2">
                <label class="text-xs font-bold text-gray-400 uppercase tracking-widest">Product</label>
                <div class="p-3 bg-gray-100 border border-gray-200 rounded-lg text-gray-600 font-bold">${val.productName}</div>
            </div>
            <div class="space-y-2">
                <label class="text-xs font-bold text-gray-400 uppercase tracking-widest">Specification</label>
                <div class="p-3 bg-gray-100 border border-gray-200 rounded-lg text-gray-600 font-bold">${val.specName}</div>
            </div>
            <div class="space-y-2 md:col-span-2">
                <label class="text-xs font-bold text-gray-400 uppercase tracking-widest text-blue-600">Specification Value</label>
                <input type="text" name="specValue" value="${val.specValue}" required 
                       class="w-full p-3 bg-gray-50 border border-gray-200 rounded-lg outline-none focus:ring-2 focus:ring-blue-500 shadow-inner">
            </div>
        </div>
        <div class="flex justify-end gap-4 pt-6 border-t">
            <a href="specificationValueServlet?action=all" class="px-6 py-2.5 text-gray-500 font-bold hover:bg-gray-100 rounded-lg transition-colors">Cancel</a>
            <button type="submit" class="px-10 py-2.5 bg-blue-600 text-white font-bold rounded-lg hover:bg-blue-700 shadow-lg transform hover:-translate-y-0.5 transition-all">Update Value</button>
        </div>
    </form>
</div>