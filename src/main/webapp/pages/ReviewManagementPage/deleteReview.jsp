<%@page contentType="text/html" pageEncoding="UTF-8"%>
<div class="max-w-2xl mx-auto bg-white p-8 rounded-2xl shadow-xl border border-gray-100 mt-20">
    <div class="flex justify-center mb-6">
        <div class="w-20 h-20 rounded-full flex items-center justify-center bg-red-50 text-red-500 animate-pulse">
            <svg class="w-12 h-12" fill="none" stroke="currentColor" viewBox="0 0 24 24"><path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M12 9v2m0 4h.01m-6.938 4h13.856c1.54 0 2.502-1.667 1.732-3L13.732 4c-.77-1.333-2.694-1.333-3.464 0L3.34 16c-.77 1.333.192 3 1.732 3z"/></svg>
        </div>
    </div>

    <div class="text-center space-y-4">
        <h2 class="text-2xl font-extrabold text-gray-900 uppercase">Confirm Delete Review</h2>
        <p class="text-gray-600">Are you sure you want to remove this review from <strong>${review.customerName}</strong>?</p>

        <div class="p-5 bg-gray-50 rounded-xl border border-gray-100 text-left">
            <p class="text-xs font-bold text-gray-400 uppercase">Product: <span class="text-gray-800">${review.productName}</span></p>
            <p class="text-xs font-bold text-gray-400 uppercase mt-2">Rating: <span class="text-yellow-600">${review.rating} Stars</span></p>
            <p class="text-xs font-bold text-gray-400 uppercase mt-2">Comment:</p>
            <p class="text-sm italic text-gray-700">"${review.comment}"</p>
        </div>
    </div>

    <form action="reviewServlet" method="POST" class="mt-10 flex justify-center gap-4">
        <input type="hidden" name="action" value="delete">
        <input type="hidden" name="reviewId" value="${review.reviewId}">
        <a href="reviewServlet?action=all" class="px-6 py-2.5 text-gray-500 font-bold border rounded-lg">Cancel</a>
        <button type="submit" class="px-10 py-2.5 bg-red-600 text-white font-bold rounded-lg hover:bg-red-700 shadow-lg">
            Delete Permanently
        </button>
    </form>
</div>