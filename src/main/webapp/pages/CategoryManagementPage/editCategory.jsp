<%@page contentType="text/html" pageEncoding="UTF-8"%>
<div class="max-w-2xl mx-auto bg-white p-8 rounded-xl shadow-md border border-gray-100 mt-10">
    <h2 class="text-2xl font-bold mb-6">Edit Category</h2>

    <form action="categoryServlet" method="POST">
        <input type="hidden" name="action" value="update">
        <input type="hidden" name="categoryId" value="${category.categoryId}">

        <div class="mb-4">
            <label class="block mb-2 text-sm font-medium">Category Name</label>
            <input type="text" name="categoryName" value="${category.categoryName}" 
                   class="w-full p-2 border rounded-lg focus:ring-blue-500" required>
        </div>

        <div class="mb-6">
            <label class="block mb-2 text-sm font-medium">Status</label>
            <div class="flex gap-4">
                <label class="flex items-center">
                    <input type="radio" name="isActive" value="1" ${category.isActive ? 'checked' : ''}> 
                    <span class="ml-2">Active</span>
                </label>
                <label class="flex items-center">
                    <input type="radio" name="isActive" value="0" ${!category.isActive ? 'checked' : ''}> 
                    <span class="ml-2">Inactive</span>
                </label>
            </div>
        </div>

        <div class="flex justify-end gap-3">
            <a href="categoryServlet?action=all" class="px-4 py-2 bg-gray-200 rounded-lg">Cancel</a>
            <button type="submit" class="px-4 py-2 bg-blue-600 text-white rounded-lg">Update Category</button>
        </div>
    </form>
</div>