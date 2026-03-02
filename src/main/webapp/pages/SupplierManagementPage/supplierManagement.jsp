<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!-- Show message -->
<c:if test="${not empty sessionScope.msg}">

    <div id="toast-notification"
         class="fixed top-10 left-1/2 -translate-x-1/2 z-[9999] min-w-[320px] transition-all duration-500">

        <c:choose>
            <c:when test="${sessionScope.msgType == 'danger'}">
                <div class="flex items-center justify-center p-4 rounded-xl shadow-2xl border-2 bg-red-50 text-red-800 border-red-200">
                </c:when>
                <c:otherwise>
                    <div class="flex items-center justify-center p-4 rounded-xl shadow-2xl border-2 bg-green-50 text-green-800 border-green-200">
                    </c:otherwise>
                </c:choose>

                <span class="font-bold uppercase tracking-wider text-sm">
                    ${sessionScope.msg}
                </span>
            </div>
        </div>

        <c:remove var="msg" scope="session"/>
        <c:remove var="msgType" scope="session"/>

        <script>
            setTimeout(function () {
                var toast = document.getElementById("toast-notification");
                if (toast) {
                    toast.remove();
                }
            }, 3000);
        </script>

    </c:if>



    <!-- Statistic section -->
    <c:set var="total" value="0"/>
    <c:set var="activeCount" value="0"/>

    <c:if test="${not empty listSuppliers}">
        <c:set var="total" value="${listSuppliers.size()}"/>

        <c:forEach var="s" items="${listSuppliers}">
            <c:if test="${s.is_active}">
                <c:set var="activeCount" value="${activeCount + 1}"/>
            </c:if>
        </c:forEach>
    </c:if>

    <div class="grid grid-cols-1 md:grid-cols-3 gap-4 mb-6">

        <div class="bg-white rounded-xl shadow-sm p-4 border-l-4 border-blue-500">
            <p class="text-gray-500 text-sm">Total Suppliers</p>
            <h3 class="text-2xl font-bold text-blue-600">${total}</h3>
        </div>

        <div class="bg-white rounded-xl shadow-sm p-4 border-l-4 border-green-500">
            <p class="text-gray-500 text-sm">Active</p>
            <h3 class="text-2xl font-bold text-green-600">${activeCount}</h3>
        </div>

        <div class="bg-white rounded-xl shadow-sm p-4 border-l-4 border-red-500">
            <p class="text-gray-500 text-sm">Inactive</p>
            <h3 class="text-2xl font-bold text-red-600">
                ${total - activeCount}
            </h3>
        </div>

    </div>



    <div class="bg-white rounded-xl shadow-lg p-5">

        <!-- Search + Add -->
        <div class="flex flex-col md:flex-row md:items-center md:justify-between gap-4 mb-5">

            <form class="w-full md:w-1/2" action="staffservlet" method="GET">

                <input type="hidden" name="action" value="supplierManagement"/>

                <div class="relative">
                    <span class="absolute inset-y-0 left-0 flex items-center pl-3 text-gray-400">
                        <svg class="w-5 h-5" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M21 21l-6-6m2-5a7 7 0 11-14 0 7 7 0 0114 0z"/>
                        </svg>
                    </span>
                    <input type="text"
                           name="keyword"
                           value="${param.keyword}"
                           placeholder="Search by supplier name or phone..."
                           class="w-full pl-10 pr-10 py-2 border rounded-lg"/>

                    <c:if test="${not empty param.keyword}">
                        <a href="staffservlet?action=supplierManagement"
                           class="absolute inset-y-0 right-0 flex items-center pr-3 text-gray-400 hover:text-gray-600">
                            <svg class="w-5 h-5"
                                 fill="none"
                                 stroke="currentColor"
                                 viewBox="0 0 24 24">
                            <path stroke-linecap="round"
                                  stroke-linejoin="round"
                                  stroke-width="2"
                                  d="M6 18L18 6M6 6l12 12"/>
                            </svg>
                        </a>
                    </c:if>
                </div>

            </form>


            <a href="supplier?action=add"
               class="inline-flex items-center justify-center px-4 py-2 text-sm font-medium text-white bg-blue-600 rounded-lg hover:bg-blue-700 focus:ring-4 focus:ring-blue-300 transition-colors whitespace-nowrap">

                <svg class="w-4 h-4 mr-2"
                     fill="currentColor"
                     viewBox="0 0 20 20">
                <path fill-rule="evenodd"
                      d="M10 3a1 1 0 011 1v5h5a1 1 0 110 2h-5v5a1 1 0 11-2 0v-5H4a1 1 0 110-2h5V4a1 1 0 011-1z"
                      clip-rule="evenodd"/>
                </svg>

                Add Supplier
            </a>

        </div>



        <!-- Table header -->
        <div class="grid grid-cols-[60px_1fr_140px_140px_260px] gap-4 py-3 px-4 bg-gray-100 rounded-t-lg border border-gray-200 border-b-0 text-sm font-semibold text-gray-700 items-center">
            <div>ID</div>
            <div>SUPPLIER</div>
            <div>PHONE</div>
            <div>STATUS</div>
            <div class="text-right">ACTION</div>
        </div>



        <div class="border border-gray-200 border-t-0 rounded-b-lg divide-y divide-gray-200">

            <c:if test="${not empty listSuppliers}">

                <c:forEach var="s" items="${listSuppliers}" varStatus="st">

                    <div class="grid grid-cols-[60px_1fr_140px_140px_260px] gap-4 py-4 px-4 items-center hover:bg-gray-50/50">

                        <div class="font-mono text-gray-600">
                            #${st.count}
                        </div>

                        <div class="text-sm text-gray-900 font-medium">
                            ${s.supplier_name}
                        </div>

                        <div class="text-gray-700 font-medium">
                            ${s.phone}
                        </div>

                        <div>
                            <c:choose>
                                <c:when test="${s.is_active}">
                                    <span class="inline-block px-3 py-1 rounded-full text-sm font-medium bg-green-100 text-green-800">
                                        Active
                                    </span>
                                </c:when>
                                <c:otherwise>
                                    <span class="inline-block px-3 py-1 rounded-full text-sm font-medium bg-red-100 text-red-800">
                                        Inactive
                                    </span>
                                </c:otherwise>
                            </c:choose>
                        </div>

                        <div class="text-right space-x-3">

                            <a href="supplier?action=edit&id=${s.supplier_id}"
                               class="text-blue-600 hover:text-blue-800 font-medium hover:underline">
                                Edit
                            </a>

                            <c:choose>
                                <c:when test="${s.is_active}">
                                    <a href="supplier?action=deactivate&id=${s.supplier_id}"
                                       onclick="return confirm('Deactivate supplier #${s.supplier_id}?');"
                                       class="text-red-600 hover:text-red-800 font-medium hover:underline">
                                        Deactivate
                                    </a>
                                </c:when>
                                <c:otherwise>
                                    <a href="supplier?action=restore&id=${s.supplier_id}"
                                       onclick="return confirm('Reactivate supplier #${s.supplier_id}?');"
                                       class="text-emerald-600 hover:text-emerald-800 font-medium hover:underline">
                                        Reactivate
                                    </a>
                                </c:otherwise>
                            </c:choose>

                            <a href="supplier?action=view&id=${s.supplier_id}"
                               class="text-gray-600 hover:text-gray-800 font-medium hover:underline">
                                Details
                            </a>

                            <a href="supplier?action=delete&id=${s.supplier_id}"
                               onclick="return confirm('Delete supplier #${s.supplier_id}? Cannot delete if used in import receipts.');"
                               class="text-red-600 hover:text-red-800 font-medium hover:underline">
                                Delete
                            </a>

                        </div>

                    </div>

                </c:forEach>

            </c:if>



            <c:if test="${empty listSuppliers}">
                <div class="py-12 text-center text-gray-500 italic">
                    <c:choose>
                        <c:when test="${not empty param.keyword}">No results found for "${param.keyword}".</c:when>
                        <c:otherwise>No suppliers found.</c:otherwise>
                    </c:choose>
                </div>
            </c:if>

        </div>

    </div>