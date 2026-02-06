<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:if test="${empty supplier}">
    <div class="flex flex-col items-center justify-center p-12 text-center">
        <div class="p-4 bg-red-50 rounded-full mb-4">
            <svg class="w-8 h-8 text-red-500" fill="none" stroke="currentColor" viewBox="0 0 24 24"><path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M6 18L18 6M6 6l12 12"/></svg>
        </div>
        <h3 class="text-lg font-bold text-gray-900">Không tìm thấy nhà cung cấp</h3>
        <p class="text-gray-500 mb-6">Dữ liệu không tồn tại hoặc đã bị xóa.</p>
        <a href="staffservlet?action=supplierManagement" class="text-blue-600 hover:text-blue-800 font-medium hover:underline">Quay lại danh sách</a>
    </div>
</c:if>

<c:if test="${not empty supplier}">
    <div class="max-w-3xl mx-auto mt-8 bg-white rounded-2xl shadow-lg border border-gray-100 overflow-hidden">

        <div class="px-8 py-6 border-b border-gray-100 bg-gray-50/50 flex flex-col md:flex-row md:items-center justify-between gap-4">
            <div>
                <div class="flex items-center gap-3 mb-1">
                    <h2 class="text-2xl font-extrabold text-gray-900 tracking-tight">${supplier.supplier_name}</h2>
                    <span class="px-2.5 py-0.5 rounded-md text-xs font-bold font-mono bg-blue-100 text-blue-700 border border-blue-200">
                        #${supplier.supplier_id}
                    </span>
                </div>
                <p class="text-sm text-gray-500">Thông tin chi tiết đối tác cung ứng</p>
            </div>

            <div class="flex-shrink-0">
                <span class="inline-flex items-center gap-1.5 px-4 py-2 rounded-full text-sm font-semibold shadow-sm border
                      ${supplier.is_active ? 'bg-green-50 text-green-700 border-green-200' : 'bg-red-50 text-red-700 border-red-200'}">
                    <span class="relative flex h-2.5 w-2.5">
                        <span class="animate-ping absolute inline-flex h-full w-full rounded-full opacity-75 ${supplier.is_active ? 'bg-green-400' : 'bg-red-400'}"></span>
                        <span class="relative inline-flex rounded-full h-2.5 w-2.5 ${supplier.is_active ? 'bg-green-500' : 'bg-red-500'}"></span>
                    </span>
                    ${supplier.is_active ? 'Đang hợp tác' : 'Ngừng hợp tác'}
                </span>
            </div>
        </div>

        <div class="p-8">
            <div class="grid grid-cols-1 md:grid-cols-2 gap-8">

                <div class="flex items-start gap-4 p-4 rounded-xl border border-gray-100 bg-white hover:shadow-md transition-shadow">
                    <div class="p-3 bg-blue-50 text-blue-600 rounded-lg">
                        <svg class="w-6 h-6" fill="none" stroke="currentColor" viewBox="0 0 24 24"><path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M3 5a2 2 0 012-2h3.28a1 1 0 01.948.684l1.498 4.493a1 1 0 01-.502 1.21l-2.257 1.13a11.042 11.042 0 005.516 5.516l1.13-2.257a1 1 0 011.21-.502l4.493 1.498a1 1 0 01.684.949V19a2 2 0 01-2 2h-1C9.716 21 3 14.284 3 6V5z"/></svg>
                    </div>
                    <div>
                        <p class="text-sm font-medium text-gray-500 mb-1">Số điện thoại</p>
                        <p class="text-lg font-bold text-gray-900 font-mono tracking-wide">${supplier.phone}</p>
                    </div>
                </div>

                <div class="flex items-start gap-4 p-4 rounded-xl border border-dashed border-gray-200 bg-gray-50/50">
                    <div class="p-3 bg-gray-100 text-gray-400 rounded-lg">
                        <svg class="w-6 h-6" fill="none" stroke="currentColor" viewBox="0 0 24 24"><path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M17.657 16.657L13.414 20.9a1.998 1.998 0 01-2.827 0l-4.244-4.243a8 8 0 1111.314 0z"/><path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M15 11a3 3 0 11-6 0 3 3 0 016 0z"/></svg>
                    </div>
                    <div>
                        <p class="text-sm font-medium text-gray-500 mb-1">Địa chỉ</p>
                        <p class="text-base text-gray-400 italic">Chưa cập nhật thông tin</p>
                    </div>
                </div>
            </div>
        </div>

        <div class="bg-gray-50 px-8 py-5 flex items-center justify-end gap-3 border-t border-gray-100">
            <a href="staffservlet?action=supplierManagement" 
               class="inline-flex items-center gap-2 px-5 py-2.5 bg-white border border-gray-300 text-gray-700 rounded-lg hover:bg-gray-50 hover:text-gray-900 transition-all font-medium text-sm shadow-sm">
                <svg class="w-4 h-4" fill="none" stroke="currentColor" viewBox="0 0 24 24"><path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M10 19l-7-7m0 0l7-7m-7 7h18"/></svg>
                Quay lại
            </a>

            <a href="supplier?action=edit&id=${supplier.supplier_id}" 
               class="inline-flex items-center gap-2 px-5 py-2.5 bg-blue-600 text-white rounded-lg hover:bg-blue-700 hover:shadow-md transition-all font-medium text-sm shadow-sm">
                <svg class="w-4 h-4" viewBox="0 0 20 20" fill="currentColor"><path d="M17.414 2.586a2 2 0 010 2.828l-9.9 9.9a1 1 0 01-.464.263l-4 1a1 1 0 01-1.213-1.213l1-4a1 1 0 01.263-.464l9.9-9.9a2 2 0 012.828 0z" /></svg>
                Chỉnh sửa thông tin
            </a>
        </div>
    </div>
</c:if>