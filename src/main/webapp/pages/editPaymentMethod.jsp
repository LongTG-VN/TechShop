<%@page contentType="text/html" pageEncoding="UTF-8"%>
<div class="max-w-2xl mx-auto bg-white p-8 rounded-xl shadow-md">
    <h2 class="text-2xl font-bold mb-6">Edit Payment Method</h2>
    
    <form action="paymentMethodServlet" method="POST">
        <input type="hidden" name="action" value="update">
        <input type="hidden" name="method_id" value="${payment.method_id}">

        <div class="mb-4">
            <label class="block mb-2">Method Name</label>
            <input type="text" name="method_name" value="${payment.method_name}" 
                   class="w-full p-2 border rounded" required>
        </div>

        <div class="mb-6">
            <label class="block mb-2">Status</label>
            <div class="flex gap-4">
                <label>
                    <input type="radio" name="is_active" value="1" ${payment.is_active ? 'checked' : ''}> Active
                </label>
                <label>
                    <input type="radio" name="is_active" value="0" ${!payment.is_active ? 'checked' : ''}> Inactive
                </label>
            </div>
        </div>

        <div class="flex justify-end gap-3">
            <a href="paymentMethodServlet?action=all" class="px-4 py-2 bg-gray-200 rounded">Cancel</a>
            <button type="submit" class="px-4 py-2 bg-blue-600 text-white rounded">Update Method</button>
        </div>
    </form>
</div>