<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%-- 1. THÔNG BÁO TOAST --%>
<c:if test="${not empty sessionScope.msg}">
    <div id="toast-notification" class="fixed top-10 left-1/2 -translate-x-1/2 z-[9999] min-w-[320px] transition-all duration-500">
        <div class="flex items-center justify-center p-4 rounded-xl shadow-2xl border-2 animate-bounce
             ${sessionScope.msgType == 'danger' ? 'bg-red-50 text-red-800 border-red-200' : 'bg-green-50 text-green-800 border-green-200'}">
            <span class="font-bold uppercase tracking-wider text-sm">${sessionScope.msg}</span>
        </div>
    </div>
    <c:remove var="msg" scope="session" />
    <c:remove var="msgType" scope="session" />
    <script>
        setTimeout(() => {
            const toast = document.getElementById('toast-notification');
            if (toast) {
                toast.style.opacity = '0';
                toast.style.transform = 'translate(-50%, -20px)';
                setTimeout(() => toast.remove(), 500);
            }
        }, 3000);
    </script>
</c:if>

<div class="bg-white rounded-xl shadow-lg p-5">
    <div class="flex flex-col md:flex-row md:items-center md:justify-between gap-4 mb-6">
        <h2 class="text-xl font-bold text-gray-800 uppercase tracking-tight">Review Management</h2>
    </div>

    <%-- 2. BỘ LỌC ĐA NĂNG --%>
    <div class="flex flex-col md:flex-row gap-3 mb-6 items-center bg-gray-50 p-3 rounded-xl border border-gray-100">
        <div class="relative flex-1 w-full">
            <span class="absolute inset-y-0 left-0 flex items-center pl-3 text-gray-400">
                <svg class="w-5 h-5" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                    <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M21 21l-6-6m2-5a7 7 0 11-14 0 7 7 0 0114 0z"/>
                </svg>
            </span>
            <input type="text" id="nameSearch" oninput="filterReviews()" 
                   class="w-full pl-10 pr-4 py-2 text-sm border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-500 outline-none bg-white shadow-sm" 
                   placeholder="Search product name...">
        </div>

        <select id="productSelect" onchange="filterReviews()" 
                class="w-full md:w-56 px-3 py-2 text-sm border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-500 outline-none bg-white cursor-pointer shadow-sm">
            <option value="">All Products</option>
            <c:forEach items="${reviewedProducts}" var="pName">
                <option value="${pName}">${pName}</option>
            </c:forEach>
        </select>

        <select id="ratingFilter" onchange="filterReviews()" 
                class="w-full md:w-44 px-3 py-2 text-sm border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-500 outline-none bg-white cursor-pointer shadow-sm">
            <option value="">All Ratings</option>
            <option value="5">5 Stars</option>
            <option value="4">4 Stars</option>
            <option value="3">3 Stars</option>
            <option value="2">2 Stars</option>
            <option value="1">1 Star</option>
        </select>
    </div>

    <%-- 3. BẢNG DỮ LIỆU --%>
    <div class="overflow-x-auto rounded-lg border border-gray-200">
        <table class="w-full text-sm text-left text-gray-600">
            <thead class="text-xs uppercase bg-gray-50 text-gray-700 font-bold">
                <tr>
                    <th class="px-4 py-3 w-16 text-center">ID</th>
                    <th class="px-4 py-3">Product Name</th>
                    <th class="px-4 py-3">Content</th>
                    <th class="px-4 py-3 text-center">Rating</th>
                    <th class="px-4 py-3">Date</th>
                    <th class="px-4 py-3 text-center w-32">Actions</th>
                </tr>
            </thead>
            <tbody class="divide-y divide-gray-200" id="reviewTableBody">
                <c:forEach items="${listdata}" var="r">
                    <tr class="review-row hover:bg-gray-50 transition-colors">
                        <td class="px-4 py-3 text-center font-medium text-gray-500">#${r.reviewId}</td>
                        <td class="px-4 py-3 font-bold text-gray-900 product-name-cell">${r.productName}</td>
                        <%-- Cột Content thay thế cho Customer --%>
                        <td class="px-4 py-3 text-gray-600 italic truncate max-w-xs">"${r.comment}"</td>
                        <td class="px-4 py-3 text-center rating-cell">
                            <span class="inline-block px-2.5 py-0.5 text-xs font-bold text-yellow-700 bg-yellow-50 rounded border border-yellow-100 uppercase">
                                ${r.rating} ⭐
                            </span>
                        </td>
                        <td class="px-4 py-3 text-xs text-gray-500">${r.formattedDate}</td>
                        <td class="px-4 py-4 text-center">
                            <div class="flex items-center justify-center gap-4">
                                <a href="reviewServlet?action=detail&id=${r.reviewId}" class="text-blue-500 hover:text-blue-700 transition-transform hover:scale-110">
                                    <svg class="w-6 h-6" fill="none" stroke="currentColor" stroke-width="2" viewBox="0 0 24 24">
                                        <path d="M15 12a3 3 0 11-6 0 3 3 0 016 0z" /><path d="M2.458 12C3.732 7.943 7.523 5 12 5c4.478 0 8.268 2.943 9.542 7-1.274 4.057-5.064 7-9.542 7-4.477 0-8.268-2.943-9.542-7z" />
                                    </svg>
                                </a>
                                <a href="reviewServlet?action=delete&id=${r.reviewId}" class="text-red-500 hover:text-red-700 transition-transform hover:scale-110">
                                    <svg class="w-6 h-6" fill="none" stroke="currentColor" stroke-width="2" viewBox="0 0 24 24">
                                        <path d="M19 7l-.867 12.142A2 2 0 0116.138 21H7.862a2 2 0 01-1.995-1.858L5 7m5 4v6m4-6v6m1-10V4a1 1 0 00-1-1h-4a1 1 0 00-1 1v3M4 7h16" />
                                    </svg>
                                </a>
                            </div>
                        </td>
                    </tr>
                </c:forEach>

                <tr id="noResultsRow" style="display: none;">
                    <td colspan="6" class="px-4 py-12 text-center">
                        <div class="flex flex-col items-center justify-center text-gray-400">
                            <svg class="w-12 h-12 mb-3" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M9.172 9.172a4 4 0 015.656 0M9 10h.01M15 10h.01M21 12a9 9 0 11-18 0 9 9 0 0118 0z" />
                            </svg>
                            <span class="text-lg font-medium">No reviews match.</span>
                        </div>
                    </td>
                </tr>
            </tbody>
        </table>
    </div>
</div>

<script>
    function filterReviews() {
        const searchVal = document.getElementById('nameSearch').value.toLowerCase();
        const selectVal = document.getElementById('productSelect').value.toLowerCase();
        const ratingVal = document.getElementById('ratingFilter').value;
        
        const rows = document.querySelectorAll('#reviewTableBody .review-row');
        const noResultsRow = document.getElementById('noResultsRow');
        let visibleCount = 0;

        rows.forEach(row => {
            const productName = row.querySelector('.product-name-cell').innerText.toLowerCase();
            const ratingText = row.querySelector('.rating-cell').innerText.trim().charAt(0);

            const matchesSearch = productName.includes(searchVal);
            const matchesSelect = selectVal === "" || productName === selectVal;
            const matchesRating = ratingVal === "" || ratingText === ratingVal;

            const isVisible = matchesSearch && matchesSelect && matchesRating;

            row.style.display = isVisible ? "" : "none";
            if(isVisible) visibleCount++;
        });

        if (noResultsRow) {
            noResultsRow.style.display = (visibleCount === 0 && rows.length > 0) ? "" : "none";
        }
    }
</script>