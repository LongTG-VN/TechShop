<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:if test="${empty inventoryItem}">
    <div class="p-6 text-center text-red-600">
        Inventory item not found.
    </div>
    <div class="text-center">
        <a href="staffservlet?action=inventoryManagement"
           class="inline-flex items-center justify-center px-4 py-2 text-sm font-semibold rounded-lg border border-blue-200 bg-blue-50 text-blue-700 shadow-sm hover:bg-blue-100 focus:outline-none focus:ring-2 focus:ring-blue-300 no-underline">
            Back to list
        </a>
    </div>
</c:if>

<c:if test="${not empty inventoryItem}">
    <div class="max-w-2xl mx-auto bg-white p-8 rounded-2xl shadow-xl border border-gray-100 mt-6">

        <h2 class="text-2xl font-bold mb-6 text-gray-800 uppercase tracking-wide">
            Inventory Item Detail
        </h2>
        <p class="text-sm font-semibold text-gray-600 uppercase tracking-wide mb-4">Basic Information</p>

        <div class="space-y-6">

            <div class="grid grid-cols-1 sm:grid-cols-2 gap-4">

                <div>
                    <label class="block mb-2 text-sm font-medium text-gray-700">
                        Serial ID
                    </label>
                    <input type="text"
                           value="${empty inventoryItem.imei ? 'N/A' : inventoryItem.imei}"
                           class="w-full p-2 border border-gray-300 rounded-lg bg-gray-50 cursor-not-allowed font-bold text-blue-600"
                           readonly>
                </div>

                <div>
                    <label class="block mb-2 text-sm font-medium text-gray-700">
                        Product
                    </label>

                    <c:if test="${not empty inventoryItem.productName}">
                        <input type="text"
                               value="${inventoryItem.productName}"
                               class="w-full p-2 border border-gray-300 rounded-lg bg-gray-50 cursor-not-allowed font-medium"
                               readonly>
                    </c:if>

                    <c:if test="${empty inventoryItem.productName}">
                        <input type="text"
                               value="${inventoryItem.variant_id}"
                               class="w-full p-2 border border-gray-300 rounded-lg bg-gray-50 cursor-not-allowed font-medium"
                               readonly>
                    </c:if>
                </div>

            </div>

            <div class="grid grid-cols-1 sm:grid-cols-2 gap-4">

                <div>
                    <label class="block mb-2 text-sm font-medium text-gray-700">
                        Import price
                    </label>

                    <input type="text"
                           value="<fmt:formatNumber value='${inventoryItem.import_price}' type='number' groupingUsed='true' maxFractionDigits='0' /> ₫"
                           class="w-full p-2 border border-gray-300 rounded-lg bg-gray-50 cursor-not-allowed font-semibold"
                           readonly>
                </div>

                <div>
                    <label class="block mb-2 text-sm font-medium text-gray-700">
                        Status
                    </label>

                    <c:if test="${not empty inventoryItem.status}">
                        <input type="text"
                               value="${inventoryItem.status}"
                               class="w-full p-2 border border-gray-300 rounded-lg bg-gray-50 cursor-not-allowed font-medium"
                               readonly>
                    </c:if>

                    <c:if test="${empty inventoryItem.status}">
                        <input type="text"
                               value="N/A"
                               class="w-full p-2 border border-gray-300 rounded-lg bg-gray-50 cursor-not-allowed font-medium"
                               readonly>
                    </c:if>
                </div>

            </div>

            <hr class="border-gray-100">

            <div class="flex justify-end gap-3 pt-2">

                <a href="javascript:history.back()"
                   class="inline-flex items-center justify-center gap-2 px-4 py-2 text-sm font-semibold rounded-lg border border-gray-300 bg-white text-gray-700 shadow-sm hover:bg-gray-50 focus:outline-none focus:ring-2 focus:ring-gray-300 no-underline">
                    Back
                </a>

            </div>

        </div>
    </div>
</c:if>