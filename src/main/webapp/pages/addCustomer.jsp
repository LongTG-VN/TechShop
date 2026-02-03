<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="flex justify-center items-start pt-10 min-h-screen bg-gray-50">
    <div class="w-full max-w-2xl bg-white rounded-2xl shadow-2xl p-10 border border-gray-100">

        <div class="text-center mb-10">
            <h2 class="text-3xl font-extrabold text-gray-900 tracking-tight">New Customer</h2>
            <p class="text-gray-500 mt-2">Please fill in the information below.</p>
        </div>

        <form action="customerservlet" method="POST" class="space-y-8">
            <input type="hidden" name="action" value="add">

            <div class="grid md:grid-cols-2 md:gap-8">
                <div class="relative z-0 w-full group">
                    <input type="text" name="username" id="username" class="block py-2.5 px-0 w-full text-sm text-gray-900 bg-transparent border-0 border-b-2 border-gray-300 appearance-none focus:outline-none focus:ring-0 focus:border-blue-600 peer" placeholder=" " required />
                    <label for="username" class="absolute text-sm text-gray-500 duration-300 transform -translate-y-6 scale-75 top-3 -z-10 origin-[0] peer-focus:left-0 peer-focus:text-blue-600 peer-placeholder-shown:scale-100 peer-placeholder-shown:translate-y-0 peer-focus:scale-75 peer-focus:-translate-y-6">Username</label>
                </div>
                <div class="relative z-0 w-full group">
                    <input type="text" name="full_name" id="full_name" class="block py-2.5 px-0 w-full text-sm text-gray-900 bg-transparent border-0 border-b-2 border-gray-300 appearance-none focus:outline-none focus:ring-0 focus:border-blue-600 peer" placeholder=" " required />
                    <label for="full_name" class="absolute text-sm text-gray-500 duration-300 transform -translate-y-6 scale-75 top-3 -z-10 origin-[0] peer-focus:left-0 peer-focus:text-blue-600 peer-placeholder-shown:scale-100 peer-placeholder-shown:translate-y-0 peer-focus:scale-75 peer-focus:-translate-y-6">Fullname</label>
                </div>
            </div>

            <div class="relative z-0 w-full group">
                <input type="email" name="email" id="email" class="block py-2.5 px-0 w-full text-sm text-gray-900 bg-transparent border-0 border-b-2 border-gray-300 appearance-none focus:outline-none focus:ring-0 focus:border-blue-600 peer" placeholder=" " required />
                <label for="email" class="absolute text-sm text-gray-500 duration-300 transform -translate-y-6 scale-75 top-3 -z-10 origin-[0] peer-focus:left-0 peer-focus:text-blue-600 peer-placeholder-shown:scale-100 peer-placeholder-shown:translate-y-0 peer-focus:scale-75 peer-focus:-translate-y-6">Email</label>
            </div>


            <div class="relative z-0 w-full group">
                <input type="password" name="password" id="password" class="block py-2.5 px-0 w-full text-sm text-gray-900 bg-transparent border-0 border-b-2 border-gray-300 appearance-none focus:outline-none focus:ring-0 focus:border-blue-600 peer" placeholder=" " required />
                <label for="password" class="absolute text-sm text-gray-500 duration-300 transform -translate-y-6 scale-75 top-3 -z-10 origin-[0] peer-focus:left-0 peer-focus:text-blue-600 peer-placeholder-shown:scale-100 peer-placeholder-shown:translate-y-0 peer-focus:scale-75 peer-focus:-translate-y-6">password</label>
            </div>


            <div class="grid md:grid-cols-2 md:gap-8">
                <div class="relative z-0 w-full group">
                    <input type="tel" name="phone_number" id="phone_number" class="block py-2.5 px-0 w-full text-sm text-gray-900 bg-transparent border-0 border-b-2 border-gray-300 appearance-none focus:outline-none focus:ring-0 focus:border-blue-600 peer" placeholder=" " required />
                    <label for="phone_number" class="absolute text-sm text-gray-500 duration-300 transform -translate-y-6 scale-75 top-3 -z-10 origin-[0] peer-focus:left-0 peer-focus:text-blue-600 peer-placeholder-shown:scale-100 peer-placeholder-shown:translate-y-0 peer-focus:scale-75 peer-focus:-translate-y-6">Phone</label>
                </div>
                <div class="relative z-0 w-full group">
                    <select name="status" id="status" class="block py-2.5 px-0 w-full text-sm text-gray-900 bg-transparent border-0 border-b-2 border-gray-300 focus:outline-none focus:ring-0 focus:border-blue-600 peer">
                        <option value="Active" selected>Active</option>
                        <option value="Locked">Locked</option>
                    </select>
                    <label for="status" class="absolute text-xs text-blue-600 duration-300 transform -translate-y-6 scale-75 top-3 -z-10 origin-[0]">Status</label>
                </div>
            </div>

            <div class="flex items-center justify-end space-x-4 pt-6">
                <a href="customerservlet?action=all" 
                   class="inline-flex items-center px-5 py-2.5 text-sm font-medium text-gray-700 bg-white border border-gray-300 rounded-lg hover:bg-gray-50 hover:text-red-600 focus:ring-4 focus:outline-none focus:ring-gray-100 transition-all duration-200">
                    <svg class="w-4 h-4 mr-2" fill="none" stroke="currentColor" viewBox="0 0 24 24"><path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M6 18L18 6M6 6l12 12"></path></svg>
                    Cancel
                </a>
                <button type="submit" class="px-10 py-2.5 text-sm font-medium text-white bg-blue-600 rounded-lg hover:bg-blue-700 focus:ring-4 focus:ring-blue-300 shadow-lg shadow-blue-500/40 transition-all duration-200 transform active:scale-95">
                    Create customer
                </button>
            </div>
        </form>
    </div>
</div>