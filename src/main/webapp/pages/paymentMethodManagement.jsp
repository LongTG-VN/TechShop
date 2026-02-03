<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="bg-white rounded-xl shadow-lg p-5">
    <div class="flex flex-col md:flex-row md:items-center md:justify-between gap-4 mb-5">
        <div class="flex flex-1 gap-4">
            <div class="relative w-full md:w-2/3">
                <span class="absolute inset-y-0 left-0 flex items-center pl-3 text-gray-400">
                    <svg class="w-5 h-5" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                        <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M21 21l-6-6m2-5a7 7 0 11-14 0 7 7 0 0114 0z"/>
                    </svg>
                </span>
                <input type="text" id="searchInput"
                       class="w-full pl-10 pr-4 py-2 text-sm border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-500"
                       placeholder="Search by name...">
            </div>

            <select id="statusFilter" class="border border-gray-300 rounded-lg px-3 py-2 text-sm focus:ring-2 focus:ring-blue-500">
                <option value="all">All Status</option>
                <option value="active">Active Only</option>
                <option value="inactive">Inactive Only</option>
            </select>
        </div>

        <a href="paymentMethodServlet?action=add"
           class="inline-flex items-center justify-center px-4 py-2 text-sm font-medium text-white bg-blue-600 rounded-lg hover:bg-blue-700 transition-colors">
            Add New Method
        </a>
    </div>

    <div class="overflow-x-auto rounded-lg border border-gray-200">
        <table class="w-full text-sm text-left text-gray-600">
            <thead class="text-xs uppercase bg-gray-50 text-gray-700 font-semibold">
                <tr>
                    <th class="px-6 py-3 w-20 text-center">ID</th>
                    <th class="px-6 py-3">Method Name</th>
                    <th class="px-6 py-3 text-center">Status</th>
                    <th class="px-6 py-3 text-center w-40">Actions</th>
                </tr>
            </thead>
            <tbody id="paymentTableBody">
                <c:forEach items="${listdata}" var="pm">
                    <tr class="payment-row bg-white border-b hover:bg-gray-50 transition-colors" 
                        data-status="${pm.is_active ? 'active' : 'inactive'}">
                        <td class="px-6 py-4 text-center">#${pm.method_id}</td>
                        <td class="px-6 py-4 font-semibold text-gray-700 method-name">${pm.method_name}</td>
                        <td class="px-6 py-4 text-center status-label">
                            <c:choose>
                                <c:when test="${pm.is_active}">
                                    <span class="px-3 py-1 text-xs font-medium text-green-700 bg-green-100 rounded-full">Active</span>
                                </c:when>
                                <c:otherwise>
                                    <span class="px-3 py-1 text-xs font-medium text-red-700 bg-red-100 rounded-full">Inactive</span>
                                </c:otherwise>
                            </c:choose>
                        </td>
                        <td class="px-6 py-4 text-center space-x-3">
                            <a href="paymentMethodServlet?action=edit&id=${pm.method_id}" class="text-blue-600 hover:underline">Edit</a>
                            <a href="paymentMethodServlet?action=delete&id=${pm.method_id}" class="text-red-600 hover:underline" onclick="return confirm('Are you sure you want to permantly delete this method?')">Delete</a>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>

    <script>
    document.addEventListener("DOMContentLoaded", function() {
        const searchInput = document.getElementById('searchInput');
        const statusFilter = document.getElementById('statusFilter');
        const tableBody = document.getElementById('paymentTableBody');
        const rows = tableBody.getElementsByClassName('payment-row');

        function filterTable() {
            const searchText = searchInput.value.toLowerCase();
            const filterStatus = statusFilter.value; // 'all', 'active', hoặc 'inactive'
            let hasResult = false;

            for (let i = 0; i < rows.length; i++) {
                const nameText = rows[i].querySelector('.method-name').innerText.toLowerCase();
                const rowStatus = rows[i].getAttribute('data-status');

                // Kiểm tra 2 điều kiện cùng lúc
                const matchesSearch = nameText.includes(searchText);
                const matchesStatus = (filterStatus === 'all' || rowStatus === filterStatus);

                if (matchesSearch && matchesStatus) {
                    rows[i].style.display = "";
                    hasResult = true;
                } else {
                    rows[i].style.display = "none";
                }
            }

            // Hiển thị thông báo nếu không tìm thấy
            let noResultRow = document.getElementById('noResultRow');
            if (!hasResult) {
                if (!noResultRow) {
                    noResultRow = document.createElement('tr');
                    noResultRow.id = 'noResultRow';
                    noResultRow.innerHTML = '<td colspan="4" class="px-6 py-10 text-center text-gray-400 italic">No matching results found.</td>';
                    tableBody.appendChild(noResultRow);
                }
            } else if (noResultRow) {
                noResultRow.remove();
            }
        }

        searchInput.addEventListener('keyup', filterTable);
        statusFilter.addEventListener('change', filterTable);
    });
    </script>
</div>