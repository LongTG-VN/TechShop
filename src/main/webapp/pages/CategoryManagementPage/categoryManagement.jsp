<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="bg-white rounded-xl shadow-lg p-5">
    <%-- 1. SEARCH AND FILTER SECTION --%>
    <div class="flex flex-col md:flex-row md:items-center md:justify-between gap-4 mb-5">
        <div class="flex flex-1 gap-4">
            <div class="relative w-full md:w-2/3">
                <span class="absolute inset-y-0 left-0 flex items-center pl-3 text-gray-400">
                    <svg class="w-5 h-5" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                    <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M21 21l-6-6m2-5a7 7 0 11-14 0 7 7 0 0114 0z"/>
                    </svg>
                </span>
                <input type="text" id="searchInput"
                       class="w-full pl-10 pr-4 py-2 text-sm border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-500 outline-none"
                       placeholder="Search category by name...">
            </div>

            <select id="statusFilter" class="border border-gray-300 rounded-lg px-3 py-2 text-sm focus:ring-2 focus:ring-blue-500 outline-none bg-white cursor-pointer">
                <option value="all">All Status</option>
                <option value="active">Active Only</option>
                <option value="inactive">Inactive Only</option>
            </select>
        </div>
        <a href="categoryServlet?action=add"
           class="inline-flex items-center justify-center px-4 py-2 text-sm font-medium text-white bg-blue-600 rounded-lg hover:bg-blue-700 transition-colors shadow-sm">
            + Add New Category
        </a>
    </div>

    <%-- 2. DATA TABLE --%>
    <div class="overflow-x-auto rounded-lg border border-gray-200">
        <table class="w-full text-sm text-left text-gray-600">
            <thead class="text-xs uppercase bg-gray-50 text-gray-700 font-semibold">
                <tr>
                    <th class="px-6 py-3 w-20 text-center">ID</th>
                    <th class="px-6 py-3">Category Name</th>
                    <th class="px-6 py-3 text-center">Status</th>
                    <th class="px-6 py-3 text-center w-48">Actions</th>
                </tr>
            </thead>
            <tbody id="categoryTableBody">
                <c:forEach items="${listdata}" var="cat">
                    <tr class="category-row bg-white border-b hover:bg-gray-50 transition-colors" 
                        data-status="${cat.isActive ? 'active' : 'inactive'}">
                        <td class="px-6 py-4 text-center font-medium text-gray-500">#${cat.categoryId}</td>
                        <td class="px-6 py-4 font-bold text-gray-900 category-name">${cat.categoryName}</td>

                        <td class="px-6 py-4 text-center">
                            <c:choose>
                                <c:when test="${cat.isActive}">
                                    <span class="px-3 py-1 text-xs font-bold text-green-700 bg-green-100 rounded-full">Active</span>
                                </c:when>
                                <c:otherwise>
                                    <span class="px-3 py-1 text-xs font-bold text-red-700 bg-red-100 rounded-full">Inactive</span>
                                </c:otherwise>
                            </c:choose>
                        </td>
                        <td class="px-6 py-4 text-center space-x-3">
                            <a href="categoryServlet?action=detail&id=${cat.categoryId}" 
                               class="text-gray-500 hover:text-gray-600 font-medium transition hover:underline">Detail</a>
                            <a href="categoryServlet?action=edit&id=${cat.categoryId}" 
                               class="text-blue-600 hover:text-blue-800 font-medium transition hover:underline">Edit</a>

                            <a href="categoryServlet?action=delete&id=${cat.categoryId}" 
                               class="text-red-600 hover:text-red-800 font-medium transition hover:underline" 
                               onclick="return confirm('Are you sure you want to delete category: ${cat.categoryName}?')">Delete</a>
                        </td>
                    </tr>
                </c:forEach>

                <c:if test="${empty listdata}">
                    <tr>
                        <td colspan="4" class="px-6 py-10 text-center text-gray-400 italic">No categories found.</td>
                    </tr>
                </c:if>
            </tbody>
        </table>  
    </div>
    <%-- PHẦN PHÂN TRANG (PAGINATION) --%>
    <div class="mt-5 flex flex-col md:flex-row justify-between items-center gap-4 text-sm text-gray-500">
        <span>Showing <span class="font-bold text-gray-900">1-${listdata.size()}</span> of <span class="font-bold text-gray-900">${listdata.size()}</span> categories</span>
        <nav class="inline-flex rounded-md shadow-sm">
            <button class="px-3 py-2 border rounded-l-md bg-white hover:bg-gray-50 text-gray-400 cursor-not-allowed transition">Previous</button>
            <button class="px-3 py-2 border-t border-b bg-blue-50 text-blue-600 font-bold">1</button>
            <button class="px-3 py-2 border rounded-r-md bg-white hover:bg-gray-50 text-gray-400 cursor-not-allowed transition">Next</button>
        </nav>
    </div>

    <%-- 3. LIVE FILTER LOGIC --%>
    <script>
        document.addEventListener("DOMContentLoaded", function () {
            const searchInput = document.getElementById('searchInput');
            const statusFilter = document.getElementById('statusFilter');
            const tableBody = document.getElementById('categoryTableBody');
            const rows = tableBody.getElementsByClassName('category-row');

            function filterTable() {
                const searchText = searchInput.value.toLowerCase();
                const filterStatus = statusFilter.value;
                let hasResult = false;

                for (let i = 0; i < rows.length; i++) {
                    const nameText = rows[i].querySelector('.category-name').innerText.toLowerCase();
                    const rowStatus = rows[i].getAttribute('data-status');

                    const matchesSearch = nameText.includes(searchText);
                    const matchesStatus = (filterStatus === 'all' || rowStatus === filterStatus);

                    if (matchesSearch && matchesStatus) {
                        rows[i].style.display = "";
                        hasResult = true;
                    } else {
                        rows[i].style.display = "none";
                    }
                }

                let noResultRow = document.getElementById('noResultRow');
                if (!hasResult && rows.length > 0) {
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