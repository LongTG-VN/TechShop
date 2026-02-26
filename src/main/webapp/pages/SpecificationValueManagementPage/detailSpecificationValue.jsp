<%@page contentType="text/html" pageEncoding="UTF-8"%>

<div class="max-w-2xl mx-auto bg-white p-8 rounded-2xl shadow-xl border mt-10">
    <div class="flex justify-between items-center border-b pb-6 mb-8">
        <h2 class="text-2xl font-bold text-gray-900 uppercase tracking-tight">Value Detail</h2>
        <a href="specificationValueServlet?action=all" class="text-blue-600 font-bold text-sm hover:underline">← Back to List</a>
    </div>
    <div class="space-y-8">
        <div class="flex items-center gap-4 bg-blue-50 p-4 rounded-xl border border-blue-100">
            <div class="p-3 bg-blue-600 text-white rounded-lg"><svg class="w-6 h-6" fill="currentColor" viewBox="0 0 20 20"><path d="M7 3a1 1 0 000 2h6a1 1 0 100-2H7zM4 7a1 1 0 011-1h10a1 1 0 110 2H5a1 1 0 01-1-1zM2 11a2 2 0 012-2h12a2 2 0 012 2v4a2 2 0 01-2 2H4a2 2 0 01-2-2v-4z"/></svg></div>
            <div>
                <p class="text-xs font-bold text-blue-400 uppercase">Product Name</p>
                <p class="text-lg font-black text-blue-900">${val.productName}</p>
            </div>
        </div>
        <div class="grid grid-cols-2 gap-4">
            <div class="p-4 bg-gray-50 rounded-xl border">
                <p class="text-xs font-bold text-gray-400 uppercase">Specification</p>
                <p class="text-md font-bold text-gray-800">${val.specName}</p>
            </div>
            <div class="p-4 bg-gray-50 rounded-xl border">
                <p class="text-xs font-bold text-gray-400 uppercase">Current Value</p>
                <p class="text-md font-bold text-gray-800">${val.specValue}</p>
            </div>
        </div>
    </div>
    <div class="mt-10 flex justify-center gap-4">
        <a href="specificationValueServlet?action=edit&pid=${val.productId}&sid=${val.specId}" class="px-8 py-2 bg-orange-500 text-white font-bold rounded-lg hover:bg-orange-600 transition-all shadow-md">Edit Value</a>
    </div>
</div>