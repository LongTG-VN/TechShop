<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!-- Show message if exists -->
<c:if test="${not empty sessionScope.msg}">

    <div id="toast-notification"
         class="fixed top-10 left-1/2 -translate-x-1/2 z-[9999] min-w-[320px] transition-all duration-500">

        <!-- Check message type -->
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

        <!-- remove message after showing -->
        <c:remove var="msg" scope="session"/>
        <c:remove var="msgType" scope="session"/>

        <!-- auto hide toast after 3 seconds -->
        <script>
            setTimeout(function () {
                var toast = document.getElementById("toast-notification");
                if (toast) {
                    toast.remove();
                }
            }, 3000);
        </script>

    </c:if>



    <!-- If supplier not found -->
    <c:if test="${empty supplier}">
        <div class="p-6 text-center text-red-600">
            Supplier not found.
        </div>

        <a href="staffservlet?action=supplierManagement"
           class="text-blue-600 underline">
            Back to list
        </a>
    </c:if>



    <!-- If supplier exists -->
    <c:if test="${not empty supplier}">

        <div class="max-w-2xl mx-auto bg-white p-8 rounded-2xl shadow-xl border border-gray-100 mt-6">

            <div class="flex justify-between items-start border-b pb-6 mb-8">

                <div>
                    <h2 class="text-2xl font-extrabold text-gray-900 uppercase tracking-tight">
                        Edit Supplier
                    </h2>

                    <p class="text-gray-500 mt-1">
                        ID:
                        <span class="font-mono font-bold text-blue-600">
                            #${supplier.supplier_id}
                        </span>
                    </p>
                </div>

                <a href="staffservlet?action=supplierManagement"
                   class="text-gray-500 hover:text-gray-700 flex items-center gap-2 text-sm font-medium">

                    <svg class="w-5 h-5" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                    <path stroke-linecap="round"
                          stroke-linejoin="round"
                          stroke-width="2"
                          d="M10 19l-7-7m0 0l7-7m-7 7h18"/>
                    </svg>

                    Back to List
                </a>
            </div>



            <form action="supplier" method="POST" class="space-y-6">

                <input type="hidden" name="action" value="update"/>
                <input type="hidden" name="supplier_id"
                       value="${supplier.supplier_id}"/>

                <!-- Supplier Name -->
                <div>
                    <label class="block text-sm font-bold text-gray-700 mb-2">
                        Supplier Name <span class="text-red-500">*</span>
                    </label>

                    <input type="text"
                           name="supplier_name"
                           required
                           maxlength="100"
                           value="${supplier.supplier_name}"
                           placeholder="Supplier name"
                           class="w-full px-4 py-2 border border-gray-200 rounded-lg focus:ring-2 focus:ring-blue-500 outline-none"/>
                </div>


                <!-- Phone -->
                <div>
                    <label class="block text-sm font-bold text-gray-700 mb-2">
                        Phone <span class="text-red-500">*</span>
                    </label>

                    <input type="tel"
                           name="phone"
                           required
                           maxlength="10"
                           pattern="[0-9]{10}"
                           title="Enter exactly 10 digits"
                           inputmode="numeric"
                           value="${supplier.phone}"
                           placeholder="10 digits"
                           class="w-full px-4 py-2 border border-gray-200 rounded-lg focus:ring-2 focus:ring-blue-500 outline-none"
                           oninput="this.value=this.value.replace(/[^0-9]/g,'')"/>
                </div>


                <!-- Active -->
                <div>
                    <label class="flex items-center gap-2 cursor-pointer">
                        <input type="checkbox"
                               name="is_active"
                               value="1"
                               <c:if test="${supplier.is_active}">checked</c:if>
                                   class="w-4 h-4 text-blue-600 rounded focus:ring-blue-500"/>

                               <span class="text-sm font-medium text-gray-700">
                                   Active
                               </span>
                        </label>
                    </div>



                    <div class="flex gap-3 pt-4">

                        <button type="submit"
                                class="inline-flex items-center gap-2 px-5 py-2.5 bg-blue-600 hover:bg-blue-700 text-white rounded-full font-semibold text-sm">
                            Update
                        </button>

                        <a href="staffservlet?action=supplierManagement"
                           class="inline-flex items-center gap-2 px-5 py-2.5 bg-gray-200 hover:bg-gray-300 text-gray-800 rounded-full font-semibold text-sm">
                            Cancel
                        </a>

                    </div>

                </form>

            </div>

    </c:if>