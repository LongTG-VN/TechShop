<%@page contentType="text/html" pageEncoding="UTF-8"%>
<div class="max-w-2xl mx-auto bg-white p-8 rounded-xl shadow-md">
    <div class="flex items-center justify-between mb-6">
        <h2 class="text-2xl font-bold text-gray-800">Edit Order Status</h2>
        <a href="adminservlet?action=orderStatusManagement" class="text-blue-600 hover:underline text-sm">← Back</a>
    </div>

    <form action="orderStatusServlet" method="POST" class="space-y-5">
        <input type="hidden" name="action" value="update">
        <input type="hidden" name="status_id" value="${status.statusId}">

        <div>
            <label class="block text-sm font-semibold text-gray-700 mb-1">Status Code (Read-only)</label>
            <input type="text" value="${status.statusCode}" readonly 
                   class="w-full px-4 py-2 border rounded-lg bg-gray-50 text-gray-500 cursor-not-allowed outline-none">
        </div>

        <div>
            <label class="block text-sm font-semibold text-gray-700 mb-1">Display Name</label>
            <input type="text" name="status_name" value="${status.statusName}" required 
                   class="w-full px-4 py-2 border rounded-lg focus:ring-2 focus:ring-blue-500 outline-none">
        </div>

        <div class="grid grid-cols-2 gap-4">
            <div>
                <label class="block text-sm font-semibold text-gray-700 mb-2">Step Order</label>
                <div class="flex items-center">
                    <button type="button" onclick="decrementStep()" 
                            class="bg-gray-200 text-gray-600 hover:bg-gray-300 h-10 w-12 rounded-l-lg border-r border-gray-300 transition duration-200 flex items-center justify-center">
                        <span class="text-xl font-bold">−</span>
                    </button>

                    <input type="number" id="step_order_input" name="step_order" 
                           value="${status != null ? status.stepOrder : 1}" 
                           min="1" required
                           class="h-10 w-20 border-y border-gray-300 text-center text-lg font-semibold focus:outline-none focus:ring-0 appearance-none m-0">

                    <button type="button" onclick="incrementStep()" 
                            class="bg-gray-200 text-gray-600 hover:bg-gray-300 h-10 w-12 rounded-r-lg border-l border-gray-300 transition duration-200 flex items-center justify-center">
                        <span class="text-xl font-bold">+</span>
                    </button>
                </div>
                <p class="text-xs text-gray-400 mt-2">Display order in the process (1 is first).</p>
            </div>

            
            <div>
                <label class="block text-sm font-semibold text-gray-700 mb-1">Status Type</label>
                <div class="mt-2">
                    <label class="inline-flex items-center cursor-pointer">
                        <input type="checkbox" name="is_final" value="1" ${status.isFinal ? 'checked' : ''} class="sr-only peer">
                        <div class="w-11 h-6 bg-gray-200 peer-focus:outline-none peer-focus:ring-4 peer-focus:ring-blue-300 rounded-full peer peer-checked:after:translate-x-full peer-checked:after:border-white after:content-[''] after:absolute after:top-[2px] after:left-[2px] after:bg-white after:border-gray-300 after:border after:rounded-full after:h-5 after:w-5 after:transition-all peer-checked:bg-blue-600"></div>
                        <span class="ml-3 text-sm font-medium text-gray-700">Is Final Stage?</span>
                    </label>
                </div>
            </div>
        </div>

        <div class="pt-4 border-t flex gap-3">
            <button type="submit" class="flex-1 bg-blue-600 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded-lg transition shadow-lg">
                Update Changes
            </button>
            <a href="adminservlet?action=orderStatusManagement" class="flex-1 text-center bg-gray-100 hover:bg-gray-200 text-gray-700 font-bold py-2 px-4 rounded-lg transition">
                Cancel
            </a>
        </div>
        <script>
            function incrementStep() {
                const input = document.getElementById('step_order_input');
                input.value = parseInt(input.value) + 1;
            }

            function decrementStep() {
                const input = document.getElementById('step_order_input');
                const currentValue = parseInt(input.value);
                if (currentValue > 1) {
                    input.value = currentValue - 1;
                }
            }
        </script>
    </form>
</div>