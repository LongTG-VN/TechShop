<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!-- If supplier not found -->
<c:if test="${empty supplier}">
    <div class="flex flex-col items-center justify-center p-12 text-center">

        <h3 class="text-lg font-bold text-gray-900">
            Supplier not found
        </h3>

        <p class="text-gray-500 mb-6">
            Data does not exist or has been deleted.
        </p>

        <a href="staffservlet?action=supplierManagement"
           class="text-blue-600 hover:text-blue-800 font-medium hover:underline">
            Back to list
        </a>

    </div>
</c:if>



<!-- If supplier exists -->
<c:if test="${not empty supplier}">

    <div class="max-w-3xl mx-auto mt-8 bg-white rounded-2xl shadow-lg border border-gray-100 overflow-hidden">

        <!-- Header -->
        <div class="px-8 py-6 border-b border-gray-100 bg-gray-50/50 flex flex-col md:flex-row md:items-center justify-between gap-4">

            <div>
                <div class="flex items-center gap-3 mb-1">

                    <h2 class="text-2xl font-extrabold text-gray-900 tracking-tight">
                        ${supplier.supplier_name}
                    </h2>

                    <span class="px-2.5 py-0.5 rounded-md text-xs font-bold font-mono bg-blue-100 text-blue-700 border border-blue-200">
                        #${supplier.supplier_id}
                    </span>

                </div>

                <p class="text-sm text-gray-500">
                    Supplier details
                </p>
            </div>

            <!-- Status -->
            <div class="flex-shrink-0">

                <c:choose>
                    <c:when test="${supplier.is_active}">
                        <span class="inline-flex items-center gap-1.5 px-4 py-2 rounded-full text-sm font-semibold shadow-sm border bg-green-50 text-green-700 border-green-200">
                            Active
                        </span>
                    </c:when>
                    <c:otherwise>
                        <span class="inline-flex items-center gap-1.5 px-4 py-2 rounded-full text-sm font-semibold shadow-sm border bg-red-50 text-red-700 border-red-200">
                            Inactive
                        </span>
                    </c:otherwise>
                </c:choose>

            </div>

        </div>



        <!-- Content -->
        <div class="p-8">

            <div class="grid grid-cols-1 md:grid-cols-2 gap-8">

                <!-- Phone -->
                <div class="flex items-start gap-4 p-4 rounded-xl border border-gray-100 bg-white">

                    <div class="p-3 bg-blue-50 text-blue-600 rounded-lg">
                        <svg class="w-6 h-6"
                             fill="none"
                             stroke="currentColor"
                             viewBox="0 0 24 24">
                        <path stroke-linecap="round"
                              stroke-linejoin="round"
                              stroke-width="2"
                              d="M3 5a2 2 0 012-2h3.28a1 1 0 01.948.684l1.498 4.493a1 1 0 01-.502 1.21l-2.257 1.13a11.042 11.042 0 005.516 5.516l1.13-2.257a1 1 0 011.21-.502l4.493 1.498a1 1 0 01.684.949V19a2 2 0 01-2 2h-1C9.716 21 3 14.284 3 6V5z"/>
                        </svg>
                    </div>

                    <div>
                        <p class="text-sm font-medium text-gray-500 mb-1">
                            Phone
                        </p>

                        <p class="text-lg font-bold text-gray-900 font-mono">
                            ${supplier.phone}
                        </p>
                    </div>

                </div>


                <!-- Address -->
                <div class="flex items-start gap-4 p-4 rounded-xl border border-dashed border-gray-200 bg-gray-50/50">

                    <div class="p-3 bg-gray-100 text-gray-400 rounded-lg">
                        <svg class="w-6 h-6"
                             fill="none"
                             stroke="currentColor"
                             viewBox="0 0 24 24">
                        <path stroke-linecap="round"
                              stroke-linejoin="round"
                              stroke-width="2"
                              d="M17.657 16.657L13.414 20.9a1.998 1.998 0 01-2.827 0l-4.244-4.243a8 8 0 1111.314 0z"/>
                        <path stroke-linecap="round"
                              stroke-linejoin="round"
                              stroke-width="2"
                              d="M15 11a3 3 0 11-6 0 3 3 0 016 0z"/>
                        </svg>
                    </div>

                    <div>
                        <p class="text-sm font-medium text-gray-500 mb-1">
                            Address
                        </p>

                        <p class="text-base text-gray-400 italic">
                            Not updated
                        </p>
                    </div>

                </div>

            </div>

        </div>



        <!-- Footer -->
        <div class="bg-gray-50 px-8 py-5 flex items-center justify-end gap-3 border-t border-gray-100">

            <a href="staffservlet?action=supplierManagement"
               class="inline-flex items-center gap-2 px-5 py-2.5 bg-white border border-gray-300 text-gray-700 rounded-lg hover:bg-gray-50 font-medium text-sm">

                <svg class="w-4 h-4"
                     fill="none"
                     stroke="currentColor"
                     viewBox="0 0 24 24">
                <path stroke-linecap="round"
                      stroke-linejoin="round"
                      stroke-width="2"
                      d="M10 19l-7-7m0 0l7-7m-7 7h18"/>
                </svg>

                Back
            </a>

            <a href="supplier?action=edit&id=${supplier.supplier_id}"
               class="inline-flex items-center gap-2 px-5 py-2.5 bg-blue-600 text-white rounded-lg hover:bg-blue-700 font-medium text-sm">

                <svg class="w-4 h-4"
                     viewBox="0 0 20 20"
                     fill="currentColor">
                <path d="M17.414 2.586a2 2 0 010 2.828l-9.9 9.9a1 1 0 01-.464.263l-4 1a1 1 0 01-1.213-1.213l1-4a1 1 0 01.263-.464l9.9-9.9a2 2 0 012.828 0z"/>
                </svg>

                Edit
            </a>

        </div>

    </div>

</c:if>