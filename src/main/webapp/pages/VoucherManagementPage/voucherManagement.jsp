<%@page contentType="text/html" pageEncoding="UTF-8"%>

<div class="bg-white rounded-xl shadow-lg p-5">

    <!-- Header -->
    <div class="flex flex-col md:flex-row md:items-center md:justify-between gap-4 mb-5">
        <!-- Search -->
        <form class="w-full md:w-1/2">
            <div class="relative">
                <span class="absolute inset-y-0 left-0 flex items-center pl-3 text-gray-400">
                    <svg class="w-5 h-5" fill="currentColor" viewBox="0 0 20 20">
                        <path fill-rule="evenodd"
                              d="M8 4a4 4 0 100 8 4 4 0 000-8zM2 8a6 6 0 1110.89 3.476l4.817 4.817a1 1 0 01-1.414 1.414l-4.816-4.816A6 6 0 012 8z"
                              clip-rule="evenodd"/>
                    </svg>
                </span>
                <input type="text"
                       class="w-full pl-10 pr-4 py-2 text-sm border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-500 focus:border-blue-500"
                       placeholder="Tìm voucher theo mã hoặc tên chương trình...">
            </div>
        </form>

        <!-- Add voucher -->
        <a href="voucher?action=add"
           class="inline-flex items-center justify-center px-4 py-2 text-sm font-medium text-white bg-blue-600 rounded-lg hover:bg-blue-700 focus:ring-4 focus:ring-blue-300 transition-colors">
            <svg class="w-4 h-4 mr-2" fill="currentColor" viewBox="0 0 20 20">
                <path fill-rule="evenodd"
                      d="M10 3a1 1 0 011 1v5h5a1 1 0 110 2h-5v5a1 1 0 11-2 0v-5H4a1 1 0 110-2h5V4a1 1 0 011-1z"
                      clip-rule="evenodd"/>
            </svg>
            Thêm voucher
        </a>
    </div>

    <!-- Table -->
    <div class="overflow-x-auto rounded-lg border border-gray-200">
        <table class="w-full text-sm text-left text-gray-600">
            <thead class="text-xs uppercase bg-gray-50 text-gray-700 font-semibold">
                <tr>
                    <th class="px-4 py-3">Mã voucher</th>
                    <th class="px-4 py-3">Giảm giá</th>
                    <th class="px-4 py-3 text-center">Số lượng</th>
                    <th class="px-4 py-3">Thời hạn</th>
                    <th class="px-4 py-3 text-center">Trạng thái</th>
                    <th class="px-4 py-3 text-center">Hành động</th>
                </tr>
            </thead>

            <tbody class="divide-y divide-gray-200">
                <tr class="hover:bg-gray-50 transition-colors">
                    <td class="px-4 py-3 font-medium text-gray-900">
                        SALE10
                        <p class="text-xs text-gray-500">Giảm 10% toàn shop</p>
                    </td>
                    <td class="px-4 py-3">
                        <span class="bg-green-100 text-green-800 text-xs font-semibold px-2.5 py-0.5 rounded border border-green-200">
                            10%
                        </span>
                    </td>
                    <td class="px-4 py-3 text-center">
                        100
                    </td>
                    <td class="px-4 py-3">
                        01/01/2026 – 31/01/2026
                    </td>
                    <td class="px-4 py-3 text-center">
                        <span class="px-2 py-1 text-xs font-semibold text-green-700 bg-green-100 rounded-full">
                            Đang hoạt động
                        </span>
                    </td>
                    <td class="px-4 py-3 text-center space-x-2">
                        <a href="#" class="text-blue-600 hover:text-blue-800 font-medium hover:underline">Sửa</a>
                        <a href="#" class="text-red-600 hover:text-red-800 font-medium hover:underline">Xóa</a>
                        <a href="#" class="text-gray-600 hover:text-gray-800 font-medium hover:underline">Chi tiết</a>
                    </td>
                </tr>

                <tr class="hover:bg-gray-50 transition-colors">
                    <td class="px-4 py-3 font-medium text-gray-900">
                        FREESHIP
                        <p class="text-xs text-gray-500">Miễn phí vận chuyển</p>
                    </td>
                    <td class="px-4 py-3">
                        <span class="bg-blue-100 text-blue-800 text-xs font-semibold px-2.5 py-0.5 rounded border border-blue-200">
                            50.000 ₫
                        </span>
                    </td>
                    <td class="px-4 py-3 text-center">
                        30
                    </td>
                    <td class="px-4 py-3">
                        15/01/2026 – 31/01/2026
                    </td>
                    <td class="px-4 py-3 text-center">
                        <span class="px-2 py-1 text-xs font-semibold text-yellow-700 bg-yellow-100 rounded-full">
                            Sắp hết
                        </span>
                    </td>
                    <td class="px-4 py-3 text-center space-x-2">
                        <a href="#" class="text-blue-600 hover:text-blue-800 font-medium hover:underline">Sửa</a>
                        <a href="#" class="text-red-600 hover:text-red-800 font-medium hover:underline">Xóa</a>
                        <a href="#" class="text-gray-600 hover:text-gray-800 font-medium hover:underline">Chi tiết</a>
                    </td>
                </tr>

                <tr class="hover:bg-gray-50 transition-colors">
                    <td class="px-4 py-3 font-medium text-gray-900">
                        OLD2024
                        <p class="text-xs text-gray-500">Voucher cũ</p>
                    </td>
                    <td class="px-4 py-3">
                        <span class="bg-gray-100 text-gray-800 text-xs font-semibold px-2.5 py-0.5 rounded border border-gray-200">
                            5%
                        </span>
                    </td>
                    <td class="px-4 py-3 text-center">
                        0
                    </td>
                    <td class="px-4 py-3">
                        01/10/2024 – 31/12/2024
                    </td>
                    <td class="px-4 py-3 text-center">
                        <span class="px-2 py-1 text-xs font-semibold text-red-700 bg-red-100 rounded-full">
                            Hết hạn
                        </span>
                    </td>
                    <td class="px-4 py-3 text-center space-x-2">
                        <a href="#" class="text-blue-600 hover:text-blue-800 font-medium hover:underline">Sửa</a>
                        <a href="#" class="text-red-600 hover:text-red-800 font-medium hover:underline">Xóa</a>
                        <a href="#" class="text-gray-600 hover:text-gray-800 font-medium hover:underline">Chi tiết</a>
                    </td>
                </tr>
            </tbody>
        </table>
    </div>

    <!-- Pagination -->
    <div class="flex flex-col md:flex-row justify-between items-center gap-3 mt-5">
        <p class="text-sm text-gray-500">
            Hiển thị <span class="font-semibold text-gray-900">1–10</span> /
            <span class="font-semibold text-gray-900">20</span> voucher
        </p>

        <nav class="inline-flex rounded-lg shadow-sm isolate">
            <a href="#" class="px-3 py-2 text-sm font-medium text-gray-500 bg-white border border-gray-300 rounded-l-lg hover:bg-gray-50">
                Trước
            </a>
            <a href="#" class="px-3 py-2 text-sm font-medium text-blue-600 bg-blue-50 border border-gray-300">
                1
            </a>
            <a href="#" class="px-3 py-2 text-sm font-medium text-gray-500 bg-white border border-gray-300 rounded-r-lg hover:bg-gray-50">
                Sau
            </a>
        </nav>
    </div>
</div>
