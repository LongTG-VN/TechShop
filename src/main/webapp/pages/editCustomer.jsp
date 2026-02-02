<%@page contentType="text/html" pageEncoding="UTF-8"%>
<div class="flex justify-center items-start pt-10 px-4">
    <div class="w-full max-w-2xl bg-white rounded-2xl shadow-2xl border border-gray-100 p-8 transition-all duration-300 hover:shadow-blue-100">
        
        <div class="text-center mb-10">
            <div class="inline-flex items-center justify-center w-16 h-16 bg-blue-50 text-blue-600 rounded-full mb-4">
                <svg class="w-8 h-8" fill="none" stroke="currentColor" viewBox="0 0 24 24" xmlns="http://www.w3.org/2000/svg">
                    <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M11 5H6a2 2 0 00-2 2v11a2 2 0 002 2h11a2 2 0 002-2v-5m-1.414-9.414a2 2 0 112.828 2.828L11.828 15H9v-2.828l8.586-8.586z"></path>
                </svg>
            </div>
            <h2 class="text-3xl font-extrabold text-gray-900 tracking-tight">Cập nhật Khách Hàng</h2>
            <p class="text-gray-500 mt-2">Đang chỉnh sửa tài khoản: <span class="px-2 py-0.5 bg-blue-100 text-blue-700 rounded font-mono text-sm">@${customer.userName}</span></p>
        </div>

        <form action="customerservlet" method="POST" class="space-y-6">
            <input type="hidden" name="action" value="edit">
            <input type="hidden" name="customerID" value="${customer.customerID}">

            <div class="grid md:grid-cols-2 md:gap-8">
                <div class="relative z-0 w-full mb-6 group">
                    <input type="text" name="username" value="${customer.userName}" readonly 
                           class="block py-2.5 px-0 w-full text-sm text-gray-400 bg-transparent border-0 border-b-2 border-gray-200 appearance-none focus:outline-none cursor-not-allowed" />
                    <label class="absolute text-sm text-gray-400 duration-300 transform -translate-y-6 scale-75 top-3 -z-10 origin-[0]">Tên đăng nhập (Cố định)</label>
                </div>
                
                <div class="relative z-0 w-full mb-6 group">
                    <input type="text" name="full_name" id="full_name" value="${customer.fullname}" 
                           class="block py-2.5 px-0 w-full text-sm text-gray-900 bg-transparent border-0 border-b-2 border-gray-300 appearance-none focus:outline-none focus:ring-0 focus:border-blue-600 peer" placeholder=" " required />
                    <label for="full_name" class="peer-focus:font-medium absolute text-sm text-gray-500 duration-300 transform -translate-y-6 scale-75 top-3 -z-10 origin-[0] peer-focus:start-0 peer-focus:text-blue-600 peer-placeholder-shown:scale-100 peer-placeholder-shown:translate-y-0 peer-focus:scale-75 peer-focus:-translate-y-6">Họ và Tên</label>
                </div>
            </div>

            <div class="relative z-0 w-full mb-6 group">
                <input type="email" name="email" id="email" value="${customer.email}" 
                       class="block py-2.5 px-0 w-full text-sm text-gray-900 bg-transparent border-0 border-b-2 border-gray-300 appearance-none focus:outline-none focus:ring-0 focus:border-blue-600 peer" placeholder=" " required />
                <label for="email" class="peer-focus:font-medium absolute text-sm text-gray-500 duration-300 transform -translate-y-6 scale-75 top-3 -z-10 origin-[0] peer-focus:start-0 peer-focus:text-blue-600 peer-placeholder-shown:scale-100 peer-placeholder-shown:translate-y-0 peer-focus:scale-75 peer-focus:-translate-y-6">Địa chỉ Email</label>
            </div>

            <div class="grid md:grid-cols-2 md:gap-8">
                <div class="relative z-0 w-full mb-6 group">
                    <input type="tel" name="phone_number" id="phone_number" value="${customer.phoneNumber}" 
                           class="block py-2.5 px-0 w-full text-sm text-gray-900 bg-transparent border-0 border-b-2 border-gray-300 appearance-none focus:outline-none focus:ring-0 focus:border-blue-600 peer" placeholder=" " required />
                    <label for="phone_number" class="peer-focus:font-medium absolute text-sm text-gray-500 duration-300 transform -translate-y-6 scale-75 top-3 -z-10 origin-[0] peer-focus:start-0 peer-focus:text-blue-600 peer-placeholder-shown:scale-100 peer-placeholder-shown:translate-y-0 peer-focus:scale-75 peer-focus:-translate-y-6">Số điện thoại</label>
                </div>
                
                <div class="relative z-0 w-full mb-6 group">
                    <select name="status" id="status" class="block py-2.5 px-0 w-full text-sm text-gray-500 bg-transparent border-0 border-b-2 border-gray-300 focus:outline-none focus:ring-0 focus:border-blue-600 peer">
                        <option value="Active" ${customer.status == 'Active' ? 'selected' : ''}>Hoạt động</option>
                        <option value="Locked" ${customer.status == 'Locked' ? 'selected' : ''}>Bị khóa</option>
                    </select>
                    <label for="status" class="peer-focus:font-medium absolute text-sm text-gray-500 duration-300 transform -translate-y-6 scale-75 top-3 -z-10 origin-[0] peer-focus:start-0 peer-focus:text-blue-600">Trạng thái hệ thống</label>
                </div>
            </div>

            <div class="flex items-center justify-end space-x-3 pt-6 border-t border-gray-50">
                <a href="customerservlet?action=all" 
                   class="inline-flex items-center px-5 py-2.5 text-sm font-medium text-gray-700 bg-white border border-gray-300 rounded-lg hover:bg-gray-50 hover:text-blue-600 focus:ring-4 focus:outline-none focus:ring-gray-100 transition-all duration-200">
                    <svg class="w-4 h-4 mr-2" fill="none" stroke="currentColor" viewBox="0 0 24 24"><path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M10 19l-7-7m0 0l7-7m-7 7h18"></path></svg>
                    Quay lại
                </a>
                <button type="submit" 
                        class="inline-flex items-center px-8 py-2.5 text-sm font-bold text-white bg-gradient-to-r from-green-500 to-green-600 rounded-lg hover:from-green-600 hover:to-green-700 focus:ring-4 focus:outline-none focus:ring-green-300 shadow-lg shadow-green-500/30 active:scale-95 transition-all duration-200">
                    <svg class="w-4 h-4 mr-2" fill="none" stroke="currentColor" viewBox="0 0 24 24"><path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M5 13l4 4L19 7"></path></svg>
                    Lưu thay đổi
                </button>
            </div>
        </form>
    </div>
</div>