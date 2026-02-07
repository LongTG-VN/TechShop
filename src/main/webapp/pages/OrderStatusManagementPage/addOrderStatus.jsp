<%@page contentType="text/html" pageEncoding="UTF-8"%>
<div class="max-w-2xl mx-auto bg-white p-8 rounded-xl shadow-md">
    <div class="flex items-center justify-between mb-6">
        <h2 class="text-2xl font-bold text-gray-800">Add New Order Status</h2>
        <a href="adminservlet?action=orderStatusManagement" class="text-blue-600 hover:underline text-sm">← Back to list</a>
    </div>

    <form action="orderStatusServlet" method="POST" class="space-y-5">
        <input type="hidden" name="action" value="add">

        <div>
            <label class="block text-sm font-semibold text-gray-700 mb-1">Status Code</label>
            <input type="text" name="status_code" placeholder="e.g., PENDING, SHIPPED..." 
                   required class="w-full px-4 py-2 border rounded-lg focus:ring-2 focus:ring-blue-500 outline-none">
            <p class="text-xs text-gray-500 mt-1">Unique identifier for logic processing (uppercase recommended).</p>
        </div>

        <div>
            <label class="block text-sm font-semibold text-gray-700 mb-1">Display Name</label>
            <input type="text" name="status_name" placeholder="e.g., Đang chờ xử lý" 
                   required class="w-full px-4 py-2 border rounded-lg focus:ring-2 focus:ring-blue-500 outline-none">
        </div>

        <div class="grid grid-cols-2 gap-4">
            <div>
                <label class="block text-sm font-semibold text-gray-700 mb-1">Step Order</label>
                <input type="number" name="step_order" value="1" min="1"
                       required class="w-full px-4 py-2 border rounded-lg focus:ring-2 focus:ring-blue-500 outline-none">
            </div>
            <div>
                <label class="block text-sm font-semibold text-gray-700 mb-1">Status Type</label>
                <div class="mt-2">
                    <label class="inline-flex items-center cursor-pointer">
                        <input type="checkbox" name="is_final" value="1" class="sr-only peer">
                        <div class="w-11 h-6 bg-gray-200 peer-focus:outline-none peer-focus:ring-4 peer-focus:ring-blue-300 rounded-full peer peer-checked:after:translate-x-full peer-checked:after:border-white after:content-[''] after:absolute after:top-[2px] after:left-[2px] after:bg-white after:border-gray-300 after:border after:rounded-full after:h-5 after:w-5 after:transition-all peer-checked:bg-blue-600"></div>
                        <span class="ml-3 text-sm font-medium text-gray-700">Is Final Stage?</span>
                    </label>
                </div>
            </div>
        </div>

        <div class="pt-4 border-t">
            <button type="submit" class="w-full bg-blue-600 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded-lg transition duration-200 shadow-lg">
                Save Status Category
            </button>
        </div>
    </form>
</div>