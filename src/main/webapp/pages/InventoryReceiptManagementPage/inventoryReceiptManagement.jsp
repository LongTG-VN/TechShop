<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:if test="${not empty sessionScope.msg}">
    <c:set var="isDangerToast" value="${sessionScope.msgType == 'danger'}"/>
    <div id="toast-receipt"
         class="fixed top-6 left-1/2 z-[9999] w-[calc(100%-2rem)] max-w-md -translate-x-1/2 transition-all duration-300 ease-out">
        <div class="relative flex items-start gap-3 rounded-2xl border px-4 py-3 shadow-xl backdrop-blur-sm
             ${isDangerToast
               ? 'bg-white/95 border-red-100 text-red-700'
               : 'bg-white/95 border-emerald-100 text-emerald-700'}">
            <div class="flex h-10 w-10 shrink-0 items-center justify-center rounded-full
                 ${isDangerToast ? 'bg-red-100 text-red-600' : 'bg-emerald-100 text-emerald-600'}">
                <c:choose>
                    <c:when test="${isDangerToast}">
                        <svg class="h-5 w-5" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                        <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                              d="M12 9v3m0 4h.01M10.29 3.86l-7.5 13A1 1 0 003.66 18h16.68a1 1 0 00.87-1.5l-7.5-13a1 1 0 00-1.74 0z"/>
                        </svg>
                    </c:when>
                    <c:otherwise>
                        <svg class="h-5 w-5" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                        <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                              d="M9 12l2 2 4-4m5 2a9 9 0 11-18 0 9 9 0 0118 0z"/>
                        </svg>
                    </c:otherwise>
                </c:choose>
            </div>
            <div class="min-w-0 flex-1 pr-2">
                <p class="text-sm font-semibold text-gray-900">
                    ${isDangerToast ? 'Action failed' : 'Action completed'}
                </p>
                <p class="mt-1 text-sm leading-5 text-gray-600 break-words">
                    ${sessionScope.msg}
                </p>
            </div>
            <button type="button"
                    onclick="closeToastReceipt()"
                    class="flex h-8 w-8 shrink-0 items-center justify-center rounded-full text-gray-400 transition hover:bg-gray-100 hover:text-gray-600">
                <svg class="h-4 w-4" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                      d="M6 18L18 6M6 6l12 12"/>
                </svg>
            </button>
        </div>
    </div>
    <c:remove var="msg" scope="session"/>
    <c:remove var="msgType" scope="session"/>
    <script>
        function closeToastReceipt() {
            var t = document.getElementById('toast-receipt');
            if (!t)
                return;
            t.classList.add('opacity-0', '-translate-y-3');
            setTimeout(function () {
                t.remove();
            }, 300);
        }

        setTimeout(closeToastReceipt, 3200);
    </script>
</c:if>

<div class="bg-white rounded-xl shadow-lg p-5">
    <div class="flex flex-col md:flex-row md:items-center md:justify-between gap-4 mb-6">
        <h2 class="text-xl font-bold text-gray-800 uppercase tracking-tight">Inventory Receipt</h2>
        <a href="${pageContext.request.contextPath}/staffservlet?action=inventoryReceiptAdd"
           class="inline-flex items-center justify-center px-4 py-2 text-sm font-medium text-white bg-blue-600 rounded-lg hover:bg-blue-700">
            <svg class="w-4 h-4 mr-2" fill="currentColor" viewBox="0 0 20 20"><path fill-rule="evenodd" d="M10 3a1 1 0 011 1v5h5a1 1 0 110 2h-5v5a1 1 0 11-2 0v-5H4a1 1 0 110-2h5V4a1 1 0 011-1z" clip-rule="evenodd"/></svg>
            New Receipt
        </a>
    </div>

    <div class="overflow-x-auto rounded-lg border border-gray-200">
        <table class="w-full text-sm text-left text-gray-600">
            <thead class="text-xs uppercase bg-gray-100 text-gray-700">
                <tr>
                    <th class="px-4 py-3 w-16">ID</th>
                    <th class="px-4 py-3">Supplier</th>
                    <th class="px-4 py-3">Status</th>
                    <th class="px-4 py-3">Import Date</th>
                    <th class="px-4 py-3 text-center">Action</th>
                </tr>
            </thead>
            <tbody class="divide-y divide-gray-200">
                <c:forEach items="${listdata}" var="r">
                    <c:set var="supplierName" value="—"/>
                    <c:forEach items="${listSuppliers}" var="s">
                        <c:if test="${s.supplier_id == r.supplier_id}">
                            <c:set var="supplierName" value="${s.supplier_name}"/>
                        </c:if>
                    </c:forEach>

                    <c:set var="empName" value="—"/>
                    <c:forEach items="${listEmployees}" var="e">
                        <c:if test="${e.employeeId == r.employee_id}">
                            <c:set var="empName" value="${e.fullName}"/>
                        </c:if>
                    </c:forEach>
                    <tr class="hover:bg-gray-50">
                        <td class="px-4 py-3 font-medium text-gray-500">#${r.receipt_id}</td>
                        <td class="px-4 py-3 font-medium text-gray-900">${supplierName}</td>
                        <td class="px-4 py-3">
                            <span class="inline-flex items-center px-2 py-1 rounded-md text-xs font-semibold
                                  ${r.status == 'CONFIRMED' ? 'bg-emerald-100 text-emerald-700'
                                    : (r.status == 'CANCELLED' ? 'bg-red-100 text-red-700' : 'bg-amber-100 text-amber-700')}">
                                ${empty r.status ? 'DRAFT' : r.status}
                            </span>
                        </td>
                        <td class="px-4 py-3 text-xs">
                            <c:choose>
                                <c:when test="${r.import_date != null}">
                                    <fmt:formatDate value="${r.import_date}" pattern="yyyy-MM-dd HH:mm:ss"/>
                                </c:when>
                                <c:otherwise>—</c:otherwise>
                            </c:choose>
                        </td>
                        <td class="px-4 py-3 whitespace-nowrap">
                            <div class="flex items-center justify-center gap-4">
                                <a href="${pageContext.request.contextPath}/staffservlet?action=inventoryReceiptSupplierHistory&supplier_id=${r.supplier_id}"
                                   class="text-blue-500 hover:text-blue-700 transition-transform hover:scale-110"
                                   title="Supplier History">
                                    <svg xmlns="http://www.w3.org/2000/svg" class="w-6 h-6" fill="none" viewBox="0 0 24 24" stroke="currentColor" stroke-width="2">
                                        <path stroke-linecap="round" stroke-linejoin="round" d="M7 3h7l5 5v13a1 1 0 01-1 1H7a2 2 0 01-2-2V5a2 2 0 012-2z"/>
                                        <path stroke-linecap="round" stroke-linejoin="round" d="M14 3v6h6M9 13h6M9 17h6"/>
                                    </svg>
                                </a>
                            </div>
                        </td>
                    </tr>
                </c:forEach>
                <c:if test="${empty listdata}">
                    <tr><td colspan="5" class="px-4 py-10 text-center text-gray-500">No inventory receipts found.</td></tr>
                </c:if>
            </tbody>
        </table>
    </div>
    <div class="mt-4 text-sm text-gray-500">Showing <span class="font-bold text-gray-900">${listdata != null ? listdata.size() : 0}</span> receipt(s).</div>
</div>