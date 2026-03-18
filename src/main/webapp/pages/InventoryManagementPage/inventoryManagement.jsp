<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!-- Toast message -->
<c:if test="${not empty sessionScope.msg}">
    <div id="toast-notification"
         class="fixed top-10 left-1/2 -translate-x-1/2 z-[9999] min-w-[320px] transition-all duration-500">

        <c:choose>
            <c:when test="${sessionScope.msgType == 'danger'}">
                <div class="flex items-center justify-center p-4 rounded-xl shadow-2xl border-2 bg-red-50 text-red-800 border-red-200">
                    <span class="font-bold uppercase tracking-wider text-sm">
                        ${sessionScope.msg}
                    </span>
                </div>
            </c:when>
            <c:otherwise>
                <div class="flex items-center justify-center p-4 rounded-xl shadow-2xl border-2 bg-green-50 text-green-800 border-green-200">
                    <span class="font-bold uppercase tracking-wider text-sm">
                        ${sessionScope.msg}
                    </span>
                </div>
            </c:otherwise>
        </c:choose>

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

<div class="bg-white rounded-xl shadow-lg p-5">

    <!-- Search -->
    <div class="flex flex-col md:flex-row md:items-center md:justify-between gap-4 mb-5">

        <form class="w-full md:w-1/2" action="staffservlet" method="GET">
            <input type="hidden" name="action" value="inventoryManagement">

            <div class="relative">
                <span class="absolute inset-y-0 left-0 flex items-center pl-3 text-gray-400">
                    <svg class="w-5 h-5" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                    <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M21 21l-6-6m2-5a7 7 0 11-14 0 7 7 0 0114 0z"/>
                    </svg>
                </span>
                <input type="text"
                       name="keyword"
                       class="w-full pl-10 pr-10 py-2 text-sm border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-500 focus:border-blue-500"
                       placeholder="Search by product name, IMEI, status or ID..."
                       value="${param.keyword}">
                <c:if test="${not empty param.keyword}">
                    <a href="staffservlet?action=inventoryManagement"
                       class="absolute inset-y-0 right-0 flex items-center pr-3 text-gray-400 hover:text-gray-600">
                        <svg class="w-5 h-5" fill="none" stroke="currentColor" viewBox="0 0 24 24"><path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M6 18L18 6M6 6l12 12"/></svg>
                    </a>
                </c:if>
            </div>
        </form>

    </div>

    <!-- Totals: Nhập - Xuất - Tồn (chỉ hiển thị khi không search) -->
    <c:if test="${empty param.keyword}">
        <div class="grid grid-cols-1 sm:grid-cols-3 gap-4 mb-5">
            <!-- Nhập -->
            <div class="bg-gray-50 border border-gray-200 rounded-xl px-4 py-3">
                <p class="text-xs uppercase text-gray-500 font-semibold">Nhập</p>
                <p class="mt-1 text-xl font-bold text-gray-900">
                    ${totalImported != null ? totalImported : 0}
                </p>
            </div>
            <!-- Xuất (đã bán) -->
            <div class="bg-blue-50 border border-blue-200 rounded-xl px-4 py-3">
                <p class="text-xs uppercase text-blue-700 font-semibold">Xuất</p>
                <p class="mt-1 text-xl font-bold text-blue-700">
                    ${totalSold != null ? totalSold : 0}
                </p>
            </div>
            <!-- Tồn (trong kho) -->
            <div class="bg-green-50 border border-green-200 rounded-xl px-4 py-3">
                <p class="text-xs uppercase text-green-700 font-semibold">Tồn</p>
                <p class="mt-1 text-xl font-bold text-green-700">
                    ${totalInStock != null ? totalInStock : 0}
                </p>
            </div>
        </div>
    </c:if>

    <!-- Summary table: per variant (imported / sold / in stock) -->
    <c:if test="${empty param.keyword}">
    <div id="inventory-summary-view" class="overflow-x-auto rounded-lg border border-gray-200">

        <table class="w-full text-sm text-left text-gray-600">

            <thead class="text-xs uppercase bg-gray-50 text-gray-700 font-semibold">
                <tr>
                    <th class="px-4 py-3">#</th>
                    <th class="px-4 py-3">Tên sản phẩm</th>
                    <th class="px-4 py-3">SKU / Mẫu mã</th>
                    <th class="px-4 py-3 text-right">Nhập</th>
                    <th class="px-4 py-3 text-right">Xuất</th>
                    <th class="px-4 py-3 text-right">Tồn</th>
                    <th class="px-4 py-3 text-center">Hành động</th>
                </tr>
            </thead>

            <tbody class="divide-y divide-gray-200">

                <c:forEach items="${inventorySummary}" var="s" varStatus="stt">
                    <tr class="hover:bg-gray-50 transition-colors">
                        <td class="px-4 py-3 font-mono text-gray-700">
                            #${stt.count}
                        </td>
                        <td class="px-4 py-3 font-medium text-gray-900"
                            data-col="product">
                            ${s.productName}
                        </td>
                        <td class="px-4 py-3 font-mono text-gray-700">
                            ${s.sku}
                        </td>
                        <td class="px-4 py-3 text-right font-semibold">
                            ${s.imported}
                        </td>
                        <td class="px-4 py-3 text-right font-semibold text-blue-700">
                            ${s.sold}
                        </td>
                        <td class="px-4 py-3 text-right font-semibold text-green-700">
                            ${s.inStock}
                        </td>
                        <td class="px-4 py-3 text-center">
                            <button type="button"
                                    class="text-blue-600 hover:text-blue-800 font-medium hover:underline btn-summary-view-detail"
                                    data-variant-id="${s.variantId}">
                                View detail
                            </button>
                        </td>
                    </tr>
                </c:forEach>

                <c:if test="${empty inventorySummary}">
                    <tr>
                        <td colspan="6" class="px-4 py-8 text-center text-gray-500 italic">
                            No summary data.
                        </td>
                    </tr>
                </c:if>

            </tbody>
        </table>
    </div>
    </c:if>

    <!-- Table: per inventory item (IMEI-level) -->
    <div id="inventory-detail-view"
         class="overflow-x-auto rounded-lg border border-gray-200 ${empty param.keyword ? 'hidden' : ''}">

        <div class="flex items-center px-4 pt-4 pb-2">
            <button type="button"
                    id="btn-back-to-summary"
                    class="inline-flex items-center px-3 py-1.5 text-xs font-semibold rounded-full bg-gray-100 text-gray-700 border border-gray-300 hover:bg-gray-200">
                ← Back to summary
            </button>
        </div>

        <table class="w-full text-sm text-left text-gray-600">

            <thead class="text-xs uppercase bg-gray-50 text-gray-700 font-semibold">
                <tr>
                    <th class="px-4 py-3">ID</th>
                    <th class="px-4 py-3">Product Name</th>
                    <th class="px-4 py-3">IMEI</th>
                    <th class="px-4 py-3 text-right">Import Price</th>
                    <th class="px-4 py-3 text-center">Status</th>
                    <th class="px-4 py-3 text-center">Actions</th>
                </tr>
            </thead>

            <tbody class="divide-y divide-gray-200">

                <c:forEach items="${listInventory}" var="it" varStatus="stt">
                    <tr class="hover:bg-gray-50 transition-colors"
                        data-variant-id="${it.variant_id}">

                        <td class="px-4 py-3 font-mono text-gray-700">
                            #${stt.count}
                        </td>

                        <td class="px-4 py-3 font-medium text-gray-900">
                            <c:choose>
                                <c:when test="${not empty it.productName}">
                                    ${it.productName}
                                </c:when>
                                <c:otherwise>
                                    —
                                </c:otherwise>
                            </c:choose>
                        </td>

                        <td class="px-4 py-3 font-mono">
                            ${it.imei}
                        </td>

                        <td class="px-4 py-3 text-right font-semibold"
                            data-import-price="${it.import_price}">
                            <fmt:formatNumber value="${it.import_price}"
                                              type="number"
                                              groupingUsed="true"
                                              maxFractionDigits="0"/> ₫
                        </td>

                        <td class="px-4 py-3 text-center">
                            <c:choose>
                                <c:when test="${it.status == 'IN_STOCK'}">
                                    <span class="px-2 py-1 text-xs font-semibold rounded-full text-green-700 bg-green-100">
                                        IN_STOCK
                                    </span>
                                </c:when>
                                <c:when test="${it.status == 'SOLD'}">
                                    <span class="px-2 py-1 text-xs font-semibold rounded-full text-blue-700 bg-blue-100">
                                        SOLD
                                    </span>
                                </c:when>
                                <c:otherwise>
                                    <span class="px-2 py-1 text-xs font-semibold rounded-full text-gray-700 bg-gray-100">
                                        ${it.status}
                                    </span>
                                </c:otherwise>
                            </c:choose>
                        </td>

                        <td class="px-4 py-3 text-center space-x-2">
                            <a href="inventory?action=edit&id=${it.inventory_id}"
                               class="text-blue-600 hover:text-blue-800 font-medium hover:underline">
                                Edit
                            </a>
                            <a href="inventory?action=delete&id=${it.inventory_id}"
                               onclick="return confirm('Delete inventory item #${it.inventory_id}?')"
                               class="text-red-600 hover:text-red-800 font-medium hover:underline">
                                Delete
                            </a>
                            <a href="inventory?action=view&id=${it.inventory_id}"
                               class="text-gray-600 hover:text-gray-800 font-medium hover:underline">
                                Details
                            </a>
                        </td>

                    </tr>
                </c:forEach>

                <c:if test="${empty listInventory}">
                    <tr>
                        <td colspan="6" class="px-4 py-12 text-center text-gray-500 italic">
                            <c:choose>
                                <c:when test="${not empty param.keyword}">No results found for "${param.keyword}".</c:when>
                                <c:otherwise>No inventory data.</c:otherwise>
                            </c:choose>
                        </td>
                    </tr>
                </c:if>

            </tbody>
        </table>
    </div>
</div>

<script>
    (function () {
        const summaryView = document.getElementById('inventory-summary-view');
        const detailView = document.getElementById('inventory-detail-view');
        const detailRows = detailView ? detailView.querySelectorAll('tbody tr[data-variant-id]') : null;
        const btnRowDetails = document.querySelectorAll('.btn-summary-view-detail');
        const btnBack = document.getElementById('btn-back-to-summary');

        if (!summaryView || !detailView) {
            return;
        }

        function parsePrice(text) {
            if (!text) return 0;
            const digits = text.replace(/[^0-9]/g, '');
            if (!digits) return 0;
            return parseInt(digits, 10);
        }

        function formatPrice(value) {
            try {
                return value.toLocaleString('vi-VN');
            } catch (e) {
                return value;
            }
        }

        function updateDetailSummary() {
            if (!detailRows) {
                return;
            }
            // Hiện tại chỉ giữ nguyên logic chọn variant,
            // không hiển thị thêm tổng số lượng / tổng tiền ở header chi tiết.
            // Nếu cần dùng lại sau có thể tính lại tại đây.
        }

        // From summary row: view detail for a specific variant
        if (btnRowDetails && detailRows) {
            btnRowDetails.forEach(function (btn) {
                btn.addEventListener('click', function () {
                    const variantId = btn.getAttribute('data-variant-id');
                    if (!variantId) {
                        return;
                    }

                    // Show detail view
                    summaryView.classList.add('hidden');
                    detailView.classList.remove('hidden');

                    // Filter rows by variant id
                    detailRows.forEach(function (row) {
                        const rowVariantId = row.getAttribute('data-variant-id');
                        if (rowVariantId === variantId) {
                            row.classList.remove('hidden');
                        } else {
                            row.classList.add('hidden');
                        }
                    });

                    updateDetailSummary();

                    // Scroll to detail table
                    detailView.scrollIntoView({behavior: 'smooth', block: 'start'});
                });
            });
        }

        // Back from detail to summary: show all summary + all detail rows
        if (btnBack && detailRows) {
            btnBack.addEventListener('click', function () {
                // Show summary, hide detail
                summaryView.classList.remove('hidden');
                detailView.classList.add('hidden');

                // Reset all detail rows to visible for next time
                detailRows.forEach(function (row) {
                    row.classList.remove('hidden');
                });

                updateDetailSummary();

                summaryView.scrollIntoView({behavior: 'smooth', block: 'start'});
            });
        }

        // Khi load trang lần đầu, nếu đang ở chế độ chi tiết (ví dụ sau redirect),
        // vẫn tính tổng số máy và tổng tiền đang hiển thị.
        updateDetailSummary();
    })();
</script>