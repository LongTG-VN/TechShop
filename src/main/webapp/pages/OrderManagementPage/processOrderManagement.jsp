<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<div class="bg-white rounded-xl shadow-lg p-5">
    <div class="flex justify-between items-center mb-6">
        <h1 class="text-2xl font-bold text-gray-800">Order Management</h1>
        <div class="text-sm text-gray-500">Total: ${orderList.size()} orders</div>
    </div>

    <div class="overflow-x-auto rounded-lg border border-gray-200">
        <table class="w-full text-sm text-left text-gray-600">
            <thead class="text-xs uppercase bg-gray-100 text-gray-700 font-bold">
                <tr>
                    <th class="px-6 py-4 text-center">Order ID</th>
                    <th class="px-6 py-4">Customer</th>
                    <th class="px-6 py-4">Total Amount</th>
                    <th class="px-6 py-4 text-center">Payment</th>
                    <th class="px-6 py-4 text-center">Status</th>
                    <th class="px-6 py-4 text-center">Actions</th>
                </tr>
            </thead>
            <tbody id="orderTableBody">
                <c:forEach items="${orderList}" var="o">
                    <tr class="order-row border-b hover:bg-gray-50 transition-colors">
                        <td class="px-6 py-4 text-center font-medium text-blue-600">#${o.orderId}</td>

                        <td class="px-6 py-4">
                            <div class="font-bold text-blue-700 text-sm uppercase tracking-wide">
                                ${o.orderName}
                            </div>
                            <div class="font-semibold text-gray-800">${o.customerName}</div>
                            <div class="text-xs text-gray-400">${o.createdAt}</div>
                        </td>

                        <td class="px-6 py-4 font-bold text-red-500">
                            ${o.totalAmount}
                        </td>

                        <td class="px-6 py-4 text-center">
                            <span class="px-3 py-1 rounded-full text-xs font-medium border
                                  ${o.paymentStatus == 'PAID' ? 'bg-green-100 text-green-700 border-green-200' : 'bg-red-100 text-red-700 border-red-200'}">
                                ${o.paymentStatus == 'PAID' ? '●' : '○'} ${o.paymentStatus}
                            </span>
                        </td>

                        <td class="px-6 py-4 text-center">
                            <span class="px-3 py-1 rounded-full text-xs font-bold uppercase tracking-wider
                                  ${o.status == 'PENDING' ? 'bg-yellow-100 text-yellow-700' : ''}
                                  ${o.status == 'APPROVED' ? 'bg-blue-100 text-blue-700' : ''}
                                  ${o.status == 'SHIPPING' ? 'bg-purple-100 text-purple-700' : ''}
                                  ${o.status == 'DELIVERED' ? 'bg-green-100 text-green-700' : ''}
                                  ${o.status == 'CANCELED' ? 'bg-gray-100 text-gray-600' : ''}">
                                ${o.status}
                            </span>
                        </td>

                        <td class="px-6 py-4 text-center">
                            <a href="orderStaffServlet?action=orderDetail&id=${o.orderId}" 
                               class="inline-block text-white bg-gray-800 hover:bg-black px-4 py-2 rounded-lg text-xs font-semibold transition shadow-md">
                                View Detail
                            </a>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>
</div>