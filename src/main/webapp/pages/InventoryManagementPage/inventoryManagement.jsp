<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<c:if test="${not empty sessionScope.msg}">
    <div id="toast-notification" class="fixed top-10 left-1/2 -translate-x-1/2 z-[9999] min-w-[320px] transition-all duration-500">
        <div class="flex items-center justify-center p-4 rounded-xl shadow-2xl border-2
             ${sessionScope.msgType == 'danger' ? 'bg-red-50 text-red-800 border-red-200' : 'bg-green-50 text-green-800 border-green-200'}">
            <span class="font-bold uppercase tracking-wider text-sm">${sessionScope.msg}</span>
        </div>
    </div>
    <c:remove var="msg" scope="session" />
    <c:remove var="msgType" scope="session" />
    <script>setTimeout(() => document.getElementById('toast-notification')?.remove(), 3000);</script>
</c:if>

<div class="bg-white rounded-xl shadow-lg p-5">
    <div class="flex flex-col md:flex-row md:items-center md:justify-between gap-4 mb-5">
        <form class="w-full md:w-1/2" action="staffservlet" method="GET">
            <input type="hidden" name="action" value="inventoryManagement">
            <div class="relative">
                <span class="absolute inset-y-0 left-0 flex items-center pl-3 text-gray-400">
                    <svg class="w-5 h-5" fill="currentColor" viewBox="0 0 20 20">
                    <path fill-rule="evenodd"
                          d="M8 4a4 4 0 100 8 4 4 0 000-8zM2 8a6 6 0 1110.89 3.476l4.817 4.817a1 1 0 01-1.414 1.414l-4.816-4.816A6 6 0 012 8z"
                          clip-rule="evenodd"/>
                    </svg>
                </span>
                <input type="text" name="keyword"
                       class="w-full pl-10 pr-4 py-2 text-sm border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-500 focus:border-blue-500"
                       placeholder="Tìm theo IMEI / status / ID..." value="${param.keyword}">
            </div>
        </form>

        <a href="inventory?action=add"
           class="inline-flex items-center justify-center px-4 py-2 text-sm font-medium text-white bg-blue-600 rounded-lg hover:bg-blue-700 focus:ring-4 focus:ring-blue-300 transition-colors">
            <svg class="w-4 h-4 mr-2" fill="currentColor" viewBox="0 0 20 20">
            <path fill-rule="evenodd"
                  d="M10 3a1 1 0 011 1v5h5a1 1 0 110 2h-5v5a1 1 0 11-2 0v-5H4a1 1 0 110-2h5V4a1 1 0 011-1z"
                  clip-rule="evenodd"/>
            </svg>
            Thêm item kho
        </a>
    </div>

    <div class="overflow-x-auto rounded-lg border border-gray-200">
        <table class="w-full text-sm text-left text-gray-600">
            <thead class="text-xs uppercase bg-gray-50 text-gray-700 font-semibold">
                <tr>
                    <th class="px-4 py-3">ID</th>
                    <th class="px-4 py-3">Variant</th>
                    <th class="px-4 py-3">Receipt item</th>
                    <th class="px-4 py-3">IMEI</th>
                    <th class="px-4 py-3 text-right">Import price</th>
                    <th class="px-4 py-3 text-center">Status</th>
                    <th class="px-4 py-3 text-center">Hành động</th>
                </tr>
            </thead>

            <tbody class="divide-y divide-gray-200">
                <c:forEach items="${listInventory}" var="it">
                    <tr class="hover:bg-gray-50 transition-colors">
                        <td class="px-4 py-3 font-mono text-gray-700">#${it.inventory_id}</td>
                        <td class="px-4 py-3 font-medium text-gray-900">${it.variant_id}</td>
                        <td class="px-4 py-3">${it.receipt_item_id}</td>
                        <td class="px-4 py-3 font-mono">${it.imei}</td>
                        <td class="px-4 py-3 text-right font-semibold">
                            <fmt:formatNumber value="${it.import_price}" type="number" groupingUsed="true" maxFractionDigits="0" />
                            ₫
                        </td>
                        <td class="px-4 py-3 text-center">
                            <c:set var="st" value="${it.status}" />
                            <span class="px-2 py-1 text-xs font-semibold rounded-full
                                  ${st == 'IN_STOCK' ? 'text-green-700 bg-green-100' : (st == 'SOLD' ? 'text-blue-700 bg-blue-100' : 'text-gray-700 bg-gray-100')}">
                                ${empty st ? 'N/A' : st}
                            </span>
                        </td>
                        <td class="px-4 py-3 text-center space-x-2">
                            <a href="inventory?action=edit&id=${it.inventory_id}" class="text-blue-600 hover:text-blue-800 font-medium hover:underline">Sửa</a>
                            <a href="inventory?action=delete&id=${it.inventory_id}"
                               onclick="return confirm('Xóa item kho #${it.inventory_id}?')"
                               class="text-red-600 hover:text-red-800 font-medium hover:underline">Xóa</a>
                            <a href="inventory?action=view&id=${it.inventory_id}" class="text-gray-600 hover:text-gray-800 font-medium hover:underline">Chi tiết</a>
                        </td>
                    </tr>
                </c:forEach>

                <c:if test="${empty listInventory}">
                    <tr>
                        <td colspan="7" class="px-4 py-12 text-center text-gray-500 italic">
                            Không có dữ liệu inventory.
                        </td>
                    </tr>
                </c:if>
            </tbody>
        </table>
    </div>
</div>
