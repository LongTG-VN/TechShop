<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="p-6 bg-white rounded-xl shadow-sm">
    <div class="flex justify-between items-center mb-6">
        <h2 class="text-2xl font-bold text-gray-800">Order Status Categories</h2>
        <a href="orderStatusServlet?action=add" class="bg-blue-600 text-white px-4 py-2 rounded-lg hover:bg-blue-700 transition">
            + Add New Status
        </a>
    </div>

    <table class="w-full text-left border-collapse">
        <thead>
            <tr class="bg-gray-50 border-b">
                <th class="p-4 font-semibold text-gray-600">Step</th>
                <th class="p-4 font-semibold text-gray-600">Code</th>
                <th class="p-4 font-semibold text-gray-600">Name</th>
                <th class="p-4 font-semibold text-gray-600">Type</th>
                <th class="p-4 font-semibold text-gray-600">Actions</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach items="${listdata}" var="s">
                <tr class="border-b hover:bg-gray-50 transition">
                    <td class="p-4 font-bold text-blue-600">#${s.stepOrder}</td>
                    <td class="p-4 font-mono text-sm">${s.statusCode}</td>
                    <td class="p-4">${s.statusName}</td>
                    <td class="p-4">
                        <c:choose>
                            <c:when test="${s.isFinal}">
                                <span class="bg-green-100 text-green-700 px-2 py-1 rounded text-xs font-bold">FINAL STAGE</span>
                            </c:when>
                            <c:otherwise>
                                <span class="bg-gray-100 text-gray-600 px-2 py-1 rounded text-xs">Processing</span>
                            </c:otherwise>
                        </c:choose>
                    </td>
                    <td class="p-4 text-blue-600 font-medium">
                        <a href="orderStatusServlet?action=edit&id=${s.statusId}" class="hover:underline">Edit</a>
                        <a href="orderStatusServlet?action=delete&id=${s.statusId}" 
                           onclick="return confirm('Are you sure you want to permanently delete this status? ')"
                           class="text-red-600 hover:text-red-800 font-medium">
                            Delete
                        </a>
                    </td>

                </tr>
            </c:forEach>
        </tbody>
    </table>
</div>