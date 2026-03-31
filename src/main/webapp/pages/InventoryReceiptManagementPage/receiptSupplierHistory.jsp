<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<div class="bg-white rounded-xl shadow-lg p-5">
    <div class="flex flex-col md:flex-row md:items-center md:justify-between gap-4 mb-6">
        <div>
            <h2 class="text-xl font-bold text-gray-800 uppercase tracking-tight">Supplier Receipt History</h2>
            <c:set var="historySupplierName" value="—"/>
            <c:forEach items="${listSuppliers}" var="s">
                <c:if test="${s.supplier_id == supplierIdHistory}">
                    <c:set var="historySupplierName" value="${s.supplier_name}"/>
                </c:if>
            </c:forEach>
            <p class="text-sm text-gray-500 mt-1">Supplier: <span class="font-semibold text-gray-700">${historySupplierName}</span></p>
        </div>
        <div class="flex items-center gap-2">
            <a href="${pageContext.request.contextPath}/staffservlet?action=inventoryReceiptManagement"
               class="inline-flex items-center justify-center px-4 py-2 text-sm font-medium text-gray-700 bg-gray-100 rounded-lg hover:bg-gray-200">
                Back to Receipt List
            </a>
        </div>
    </div>

    <div class="overflow-x-auto rounded-lg border border-gray-200">
        <table class="w-full text-sm text-left text-gray-600">
            <thead class="text-xs uppercase bg-gray-100 text-gray-700">
                <tr>
                    <th class="px-4 py-3 w-16">ID</th>
                    <th class="px-4 py-3">Receipt Code</th>
                    <th class="px-4 py-3">Employee</th>
                    <th class="px-4 py-3">Import Date</th>
                    <th class="px-4 py-3 text-center">Status</th>
                    <th class="px-4 py-3 text-right">Total Cost</th>
                    <th class="px-4 py-3 text-center">Action</th>
                </tr>
            </thead>
            <tbody class="divide-y divide-gray-200">
                <c:forEach items="${supplierHistoryReceipts}" var="r">
                    <c:set var="empName" value="—"/>
                    <c:forEach items="${listEmployees}" var="e">
                        <c:if test="${e.employeeId == r.employee_id}">
                            <c:set var="empName" value="${e.fullName}"/>
                        </c:if>
                    </c:forEach>
                    <tr class="hover:bg-gray-50">
                        <td class="px-4 py-3 font-medium text-gray-500">#${r.receipt_id}</td>
                        <td class="px-4 py-3 font-medium text-gray-900">${empty r.receipt_code ? '—' : r.receipt_code}</td>
                        <td class="px-4 py-3">${empName}</td>
                        <td class="px-4 py-3 text-xs">
                            <c:choose>
                                <c:when test="${r.import_date != null}">
                                    <fmt:formatDate value="${r.import_date}" pattern="yyyy-MM-dd HH:mm:ss"/>
                                </c:when>
                                <c:otherwise>—</c:otherwise>
                            </c:choose>
                        </td>
                        <td class="px-4 py-3 text-center">
                            <span class="inline-flex items-center px-2 py-1 rounded-md text-xs font-semibold
                                  ${r.status == 'CONFIRMED' ? 'bg-emerald-100 text-emerald-700'
                                    : (r.status == 'CANCELLED' ? 'bg-red-100 text-red-700' : 'bg-amber-100 text-amber-700')}">
                                ${empty r.status ? 'DRAFT' : r.status}
                            </span>
                        </td>
                        <td class="px-4 py-3 text-right font-semibold text-blue-600"><fmt:formatNumber value="${r.total_cost}" groupingUsed="true"/>₫</td>
                        <td class="px-4 py-3 text-center">
                            <div class="inline-flex items-center justify-center gap-3">
                                <a href="${pageContext.request.contextPath}/staffservlet?action=inventoryReceiptDetail&id=${r.receipt_id}&mode=view"
                                   class="inline-flex items-center justify-center text-blue-500 hover:text-blue-700 transition-transform hover:scale-110"
                                   title="View items">
                                    <svg xmlns="http://www.w3.org/2000/svg" class="w-5 h-5" fill="none" viewBox="0 0 24 24" stroke="currentColor" stroke-width="2">
                                        <path stroke-linecap="round" stroke-linejoin="round" d="M15 12a3 3 0 11-6 0 3 3 0 016 0z"/>
                                        <path stroke-linecap="round" stroke-linejoin="round" d="M2.458 12C3.732 7.943 7.523 5 12 5c4.478 0 8.268 2.943 9.542 7-1.274 4.057-5.064 7-9.542 7-4.477 0-8.268-2.943-9.542-7z"/>
                                    </svg>
                                </a>
                                <c:choose>
                                    <c:when test="${r.status == 'CONFIRMED'}">
                                        <span class="inline-flex items-center justify-center text-gray-300 cursor-not-allowed"
                                              title="This receipt is confirmed and locked">
                                            <svg xmlns="http://www.w3.org/2000/svg" class="w-5 h-5" fill="none" viewBox="0 0 24 24" stroke="currentColor" stroke-width="2">
                                                <path stroke-linecap="round" stroke-linejoin="round" d="M12 15v2m-6 4h12a2 2 0 002-2v-7a2 2 0 00-2-2h-1V8a5 5 0 10-10 0v2H6a2 2 0 00-2 2v7a2 2 0 002 2z"/>
                                            </svg>
                                        </span>
                                    </c:when>
                                    <c:otherwise>
                                        <a href="${pageContext.request.contextPath}/staffservlet?action=inventoryReceiptDetail&id=${r.receipt_id}&mode=add#add-item-form"
                                           class="inline-flex items-center justify-center text-orange-500 hover:text-orange-700 transition-transform hover:scale-110"
                                           title="Edit draft receipt">
                                            <svg xmlns="http://www.w3.org/2000/svg" class="w-5 h-5" fill="none" viewBox="0 0 24 24" stroke="currentColor" stroke-width="2">
                                                <path stroke-linecap="round" stroke-linejoin="round" d="M16.862 3.487a1.875 1.875 0 112.652 2.652L10.5 15.153l-3.75 1.098 1.098-3.75 9.014-9.014z"/>
                                                <path stroke-linecap="round" stroke-linejoin="round" d="M19.5 10.5V19.125A1.875 1.875 0 0117.625 21H4.875A1.875 1.875 0 013 19.125V6.375A1.875 1.875 0 014.875 4.5H13.5"/>
                                            </svg>
                                        </a>
                                    </c:otherwise>
                                </c:choose>
                            </div>
                        </td>
                    </tr>
                </c:forEach>
                <c:if test="${empty supplierHistoryReceipts}">
                    <tr>
                        <td colspan="7" class="px-4 py-10 text-center text-gray-500">
                            No receipts for this supplier yet.
                        </td>
                    </tr>
                </c:if>
            </tbody>
        </table>
    </div>
</div>
