<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:if test="${empty supplier}">
    <div class="p-4 bg-white rounded border">
        <div class="text-red-700 font-medium mb-2">Supplier not found.</div>
        <a class="text-blue-600 hover:underline" href="staffservlet?action=supplierManagement">Back to list</a>
    </div>
</c:if>

<c:if test="${not empty supplier}">
    <div class="bg-white p-5 rounded-xl border shadow-sm max-w-xl">
        <h2 class="text-xl font-bold mb-4">Supplier details</h2>

        <table class="w-full border-collapse overflow-hidden rounded-xl border">
            <tr>
                <td class="p-2 border bg-gray-50 w-40">ID</td>
                <td class="p-2 border">#${supplier.supplier_id}</td>
            </tr>
            <tr>
                <td class="p-2 border bg-gray-50">Name</td>
                <td class="p-2 border">${supplier.supplier_name}</td>
            </tr>
            <tr>
                <td class="p-2 border bg-gray-50">Phone</td>
                <td class="p-2 border">${supplier.phone}</td>
            </tr>
            <tr>
                <td class="p-2 border bg-gray-50">Status</td>
                <td class="p-2 border">
                    <c:choose>
                        <c:when test="${supplier.is_active}">Active</c:when>
                        <c:otherwise>Inactive</c:otherwise>
                    </c:choose>
                </td>
            </tr>
        </table>

        <div class="flex gap-2 mt-4">
            <a href="staffservlet?action=supplierManagement" class="px-4 py-2 rounded-lg border bg-gray-50 hover:bg-gray-100">Back to list</a>
            <a href="supplier?action=edit&id=${supplier.supplier_id}" class="px-4 py-2 rounded-lg bg-blue-600 text-white hover:bg-blue-700 shadow-sm">Edit</a>
        </div>
    </div>

</c:if>