<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="max-w-4xl mx-auto bg-white p-8 rounded-2xl shadow-xl border border-gray-100 mt-10">
    <div class="flex justify-between items-start border-b pb-6 mb-8">
        <div>
            <h2 class="text-3xl font-extrabold text-gray-900 uppercase tracking-tight">Review Detail</h2>
            <p class="text-gray-500 mt-1">Review ID: <span class="font-mono font-bold text-blue-600">#${review.reviewId}</span></p>
        </div>
        <a href="reviewServlet?action=all" class="text-gray-500 flex items-center gap-2 text-sm font-medium hover:text-blue-600">
            <svg class="w-5 h-5" fill="none" stroke="currentColor" viewBox="0 0 24 24"><path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M10 19l-7-7m0 0l7-7m-7 7h18"/></svg>
            Back to List
        </a>
    </div>

    <div class="grid grid-cols-1 md:grid-cols-2 gap-10">
        <div class="space-y-6">
            <h3 class="text-lg font-bold text-gray-800 border-l-4 border-blue-600 pl-3">Customer & Product</h3>
            <div class="space-y-4">
                <div>
                    <label class="text-xs font-bold text-gray-400 uppercase">Customer Name</label>
                    <p class="text-lg font-semibold text-gray-900">${review.customerName}</p>
                </div>
                <div>
                    <label class="text-xs font-bold text-gray-400 uppercase">Product Name</label>
                    <p class="text-lg font-semibold text-gray-700">${review.productName}</p>
                </div>
            </div>
        </div>

        <div class="bg-gray-50 p-6 rounded-xl space-y-6 border border-gray-100 shadow-inner">
            <h3 class="text-lg font-bold text-gray-800">Review Info</h3>
            <div class="space-y-4">
                <div>
                    <p class="text-xs font-bold text-gray-400 uppercase">Rating</p>
                    <p class="text-xl font-bold text-yellow-500">${review.rating} / 5 Stars</p>
                </div>
                <%-- Thêm Status hiển thị tại đây --%>
                <div>
                    <p class="text-xs font-bold text-gray-400 uppercase">Status</p>
                    <p class="text-sm font-bold ${review.status.toLowerCase() == 'visible' ? 'text-green-600' : 'text-red-600'}">
                        ${review.status.toUpperCase()}
                    </p>
                </div>
                <div>
                    <p class="text-xs font-bold text-gray-400 uppercase">Created At</p>
                    <p class="text-sm font-bold text-gray-700">${review.getFormattedDate()}</p>
                </div>
            </div>
        </div>
    </div>

    <div class="mt-10 pt-8 border-t">
        <label class="text-xs font-bold text-gray-400 uppercase">Content</label>
        <div class="mt-4 p-4 bg-gray-50 rounded-lg text-gray-600 leading-relaxed italic border-l-4 border-gray-200">
            "${review.comment}"
        </div>
    </div>
</div>