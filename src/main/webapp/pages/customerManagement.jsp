<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
                       class="w-full pl-10 pr-4 py-2 text-sm border rounded-lg focus:ring-2 focus:ring-blue-500 focus:border-blue-500"
                       placeholder="Tìm kiếm theo tên, email, số điện thoại...">
            </div>
        </form>

        <!-- Add button -->
        <a href="customerservlet?action=add"
           class="inline-flex items-center justify-center px-4 py-2 text-sm font-medium text-white bg-blue-600 rounded-lg hover:bg-blue-700 focus:ring-4 focus:ring-blue-300">
            <svg class="w-4 h-4 mr-2" fill="currentColor" viewBox="0 0 20 20">
            <path fill-rule="evenodd"
                  d="M10 3a1 1 0 011 1v5h5a1 1 0 110 2h-5v5a1 1 0 11-2 0v-5H4a1 1 0 110-2h5V4a1 1 0 011-1z"
                  clip-rule="evenodd"/>
            </svg>
            Thêm khách hàng
        </a>



    </div>



    <!-- Table -->
    <div class="overflow-x-auto">
        <table class="w-full text-sm text-left text-gray-600">
            <thead class="text-xs uppercase bg-gray-100 text-gray-700">
                <tr>
                    <th class="px-4 py-3">Khách hàng</th>
                    <th class="px-4 py-3">Email</th>
                    <th class="px-4 py-3">SĐT</th>
                    <th class="px-4 py-3">Ngày tạo</th>
                    <th class="px-4 py-3">Trạng thái</th>
                    <th class="px-4 py-3 text-center">Hành động</th>
                </tr>
            </thead>
            <tbody class="divide-y">
                <%-- 'listdata' được gửi từ Servlet thông qua request.setAttribute --%>
                <c:forEach items="${listdata}" var="cus">
                    <tr class="hover:bg-gray-50">
                        <td class="px-4 py-3 font-medium text-gray-900">
                            ${cus.fullname} <br/>
                            <span class="text-xs text-gray-400">@${cus.userName}</span>
                        </td>
                        <td class="px-4 py-3">${cus.email}</td>
                        <td class="px-4 py-3">${cus.phoneNumber}</td>
                        <td class="px-4 py-3 text-xs">${cus.createdAt}</td>
                        <td class="px-4 py-3">
                            <c:choose>
                                <c:when test="${cus.status eq 'Active' || cus.status eq 'Hoạt động'}">
                                    <span class="px-2 py-1 text-xs font-semibold text-green-700 bg-green-100 rounded-full">
                                        Hoạt động
                                    </span>
                                </c:when>
                                <c:otherwise>
                                    <span class="px-2 py-1 text-xs font-semibold text-red-700 bg-red-100 rounded-full">
                                        ${cus.status}
                                    </span>
                                </c:otherwise>
                            </c:choose>
                        </td>
                        <td class="px-4 py-3 text-center space-x-3">
                            <a href="customerservlet?action=edit&id=${cus.customerID}" 
                               class="text-blue-600 hover:text-blue-800 font-medium">Sửa</a>

                            <a href="customerservlet?action=delete&id=${cus.customerID}" 
                               onclick="return confirm('Bạn có chắc muốn xóa khách hàng này?')"
                               class="text-red-600 hover:text-red-800 font-medium">Xóa</a>

                            <a href="adminservlet?action=detailCustomer&id=${cus.customerID}" 
                               class="text-gray-600 hover:text-gray-800 font-medium">Chi tiết</a>
                        </td>
                    </tr>
                </c:forEach>

                <%-- Hiển thị thông báo nếu danh sách trống --%>
                <c:if test="${empty listdata}">
                    <tr>
                        <td colspan="6" class="px-4 py-10 text-center text-gray-500">
                            Không tìm thấy dữ liệu khách hàng nào.
                        </td>
                    </tr>
                </c:if>
            </tbody>
        </table>
    </div>

    <!-- Pagination -->
    <div class="flex flex-col md:flex-row justify-between items-center gap-3 mt-5">
        <p class="text-sm text-gray-500">
            Hiển thị <span class="font-semibold text-gray-900">1–10</span> / 
            <span class="font-semibold text-gray-900">100</span> kết quả
        </p>

        <div class="inline-flex rounded-lg shadow-sm">
            <a href="#" class="px-3 py-2 text-sm border rounded-l-lg hover:bg-gray-100">Trước</a>
            <a href="#" class="px-3 py-2 text-sm border bg-blue-50 text-blue-600 font-semibold">1</a>
            <a href="#" class="px-3 py-2 text-sm border hover:bg-gray-100">2</a>
            <a href="#" class="px-3 py-2 text-sm border rounded-r-lg hover:bg-gray-100">Sau</a>
        </div>
    </div>
</div>


