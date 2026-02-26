<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="max-w-2xl mx-auto bg-white p-8 rounded-2xl shadow-xl border mt-10">
    <h2 class="text-2xl font-bold text-gray-900 mb-8 uppercase text-center border-b pb-4">Assign Spec to Product</h2>

    <form action="specificationValueServlet" method="POST" class="space-y-6">
        <input type="hidden" name="action" value="add">

        <div class="space-y-2">
            <label class="text-xs font-bold text-gray-400 uppercase tracking-widest">Select Product</label>
            <select name="productId" required class="w-full p-3 bg-gray-50 border border-gray-200 rounded-lg outline-none focus:ring-2 focus:ring-blue-500">
                <c:forEach items="${products}" var="p">
                    <option value="${p.productId}">${p.name}</option>
                </c:forEach>
            </select>
        </div>

        <div class="space-y-2">
            <label class="text-xs font-bold text-gray-400 uppercase tracking-widest">Select Specification</label>
            <select name="specId" required class="w-full p-3 bg-gray-50 border border-gray-200 rounded-lg outline-none focus:ring-2 focus:ring-blue-500">
                <c:forEach items="${specs}" var="s">
                    <option value="${s.specId}">${s.specName} (${s.categoryName})</option>
                </c:forEach>
            </select>
        </div>

        <div class="space-y-2">
            <label class="text-xs font-bold text-gray-400 uppercase tracking-widest">Value</label>
            <input type="text" name="specValue" required placeholder="e.g. 16GB, OLED, Black"
                   class="w-full p-3 bg-gray-50 border border-gray-200 rounded-lg outline-none focus:ring-2 focus:ring-blue-500">
        </div>

        <div class="flex justify-end gap-4 pt-4">
            <a href="specificationValueServlet?action=all" class="px-6 py-2 text-gray-500 font-bold hover:bg-gray-100 rounded-lg">Cancel</a>
            <button type="submit" class="px-8 py-2 bg-blue-600 text-white font-bold rounded-lg hover:bg-blue-700 shadow-lg transition-all transform hover:-translate-y-0.5">
                Save Assignment
            </button>
        </div>
    </form>
</div>