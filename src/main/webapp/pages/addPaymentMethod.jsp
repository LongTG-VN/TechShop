<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="flex justify-center items-start pt-10">
    <div class="w-full max-w-2xl bg-white rounded-2xl shadow-xl border border-gray-100 p-8">
        <div class="text-center mb-10">
            <div class="inline-flex items-center justify-center w-16 h-16 bg-blue-100 rounded-full mb-4">
                <svg class="w-8 h-8 text-blue-600" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                    <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M3 10h18M7 15h1m4 0h1m-7 4h12a3 3 0 003-3V8a3 3 0 00-3-3H6a3 3 0 00-3 3v8a3 3 0 003 3z"/>
                </svg>
            </div>
            <h2 class="text-3xl font-extrabold text-gray-900">Add Payment Method</h2>
            <p class="text-gray-500 mt-2">Create a new billing option for the system.</p>
        </div>

        <form action="paymentMethodServlet" method="POST" class="space-y-6">
            <input type="hidden" name="action" value="add">

            <div class="relative z-0 w-full mb-6 group">
                <input type="text" name="method_name" id="method_name" 
                       class="block py-2.5 px-0 w-full text-sm text-gray-900 bg-transparent border-0 border-b-2 border-gray-300 appearance-none focus:outline-none focus:ring-0 focus:border-blue-600 peer" 
                       placeholder=" " required />
                <label for="method_name" class="peer-focus:font-medium absolute text-sm text-gray-500 duration-300 transform -translate-y-6 scale-75 top-3 -z-10 origin-[0] peer-focus:text-blue-600 peer-placeholder-shown:scale-100 peer-placeholder-shown:translate-y-0 peer-focus:scale-75 peer-focus:-translate-y-6">
                    Method Name (e.g., Apple Pay, Bank Transfer)
                </label>
            </div>

            <div class="relative z-0 w-full mb-6 group">
                <label class="block mb-2 text-sm font-medium text-gray-500">Initial Status</label>
                <div class="flex items-center space-x-6 pt-2">
                    <label class="flex items-center cursor-pointer">
                        <input type="radio" name="is_active" value="1" checked class="w-4 h-4 text-blue-600 focus:ring-blue-500">
                        <span class="ml-2 text-sm text-gray-700">Active</span>
                    </label>
                    <label class="flex items-center cursor-pointer">
                        <input type="radio" name="is_active" value="0" class="w-4 h-4 text-gray-400 focus:ring-blue-500">
                        <span class="ml-2 text-sm text-gray-700">Inactive</span>
                    </label>
                </div>
            </div>

            <div class="flex items-center justify-end space-x-4 pt-6 border-t border-gray-100">
                <a href="paymentMethodServlet?action=all" 
                   class="text-gray-500 bg-white hover:bg-gray-100 border border-gray-300 font-medium rounded-lg text-sm px-6 py-2.5 transition-all">
                    Cancel
                </a>
                <button type="submit" 
                        class="text-white bg-blue-600 hover:bg-blue-700 font-medium rounded-lg text-sm px-10 py-2.5 shadow-lg shadow-blue-500/30 transition-all transform hover:-translate-y-1">
                    Save Method
                </button>
            </div>
        </form>
    </div>
</div>