<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%-- 1. THÔNG BÁO TOAST --%>
<c:if test="${not empty sessionScope.msg}">
    <div id="toast-notification" class="fixed top-10 left-1/2 -translate-x-1/2 z-[9999] min-w-[320px] transition-all duration-500">
        <div class="flex items-center justify-center p-4 rounded-xl shadow-2xl border-2 animate-bounce
             ${sessionScope.msgType == 'danger' ? 'bg-red-50 text-red-800 border-red-200' : 'bg-green-50 text-green-800 border-green-200'}">
            <span class="font-bold uppercase tracking-wider text-sm">${sessionScope.msg}</span>
        </div>
    </div>
    <c:remove var="msg" scope="session" />
    <c:remove var="msgType" scope="session" />
    <script>
        setTimeout(() => {
            const toast = document.getElementById('toast-notification');
            if (toast) {
                toast.style.opacity = '0';
                toast.style.transform = 'translate(-50%, -20px)';
                setTimeout(() => toast.remove(), 500);
            }
        }, 3000);
    </script>
</c:if>

<div class="max-w-2xl mx-auto bg-white p-8 rounded-2xl shadow-xl border border-gray-100 mt-10">
    <%-- 2. HEADER --%>
    <div class="flex items-center gap-3 mb-8 border-b pb-4">
        <div class="p-2 bg-blue-50 rounded-lg">
            <svg class="w-6 h-6 text-blue-600" fill="none" stroke="currentColor" viewBox="0 0 24 24">
            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M11 5H6a2 2 0 00-2 2v11a2 2 0 002 2h11a2 2 0 002-2v-5m-1.414-9.414a2 2 0 112.828 2.828L11.828 15H9v-2.828l8.586-8.586z" />
            </svg>
        </div>
        <h2 class="text-2xl font-bold text-gray-800 uppercase tracking-wide">Edit Brand</h2>
    </div>

    <%-- FORM CHỈNH SỬA --%>
    <form action="brandServlet" method="POST" enctype="multipart/form-data" class="space-y-8">
        <input type="hidden" name="action" value="update">
        <input type="hidden" name="brandId" value="${brand.brandId}">

        <%-- THÔNG TIN CƠ BẢN --%>
        <div class="grid grid-cols-12 gap-6">
            <div class="col-span-4">
                <label class="block mb-2 text-xs font-bold text-gray-500 uppercase">ID</label>
                <div class="p-3 bg-gray-50 border border-gray-200 rounded-xl font-mono text-blue-600 font-bold">
                    #${brand.brandId}
                </div>
            </div>
            <div class="col-span-8">
                <label class="block mb-2 text-xs font-bold text-gray-500 uppercase">Brand Name</label>
                <input type="text" name="brandName" value="${brand.brandName}" required
                       class="w-full p-3 bg-gray-50 border border-gray-200 rounded-xl font-bold text-blue-600 focus:ring-0 outline-none">
            </div>
        </div>

        <%-- TRẠNG THÁI (STATUS) --%>
        <div>
            <label class="block mb-2 text-xs font-bold text-gray-500 uppercase">Status</label>
            <div class="flex items-center gap-4 pt-1">
                <label class="inline-flex items-center cursor-pointer">
                    <input type="radio" name="isActive" value="1" ${brand.isActive ? 'checked' : ''} class="w-4 h-4 text-blue-600">
                    <span class="ml-2 text-xs font-bold text-green-700 uppercase">Active</span>
                </label>
                <label class="inline-flex items-center cursor-pointer">
                    <input type="radio" name="isActive" value="0" ${!brand.isActive ? 'checked' : ''} class="w-4 h-4 text-blue-600">
                    <span class="ml-2 text-xs font-bold text-red-700 uppercase">Inactive</span>
                </label>
            </div>
        </div>

        <%-- HIỂN THỊ VÀ CHỌN LOGO --%>
        <%-- Phần Logo trong editBrand.jsp --%>
        <div>
            <label class="block mb-2 text-xs font-bold text-gray-500 uppercase">Brand Logo</label>
            <div class="p-6 bg-blue-50/40 rounded-3xl border border-blue-100 shadow-sm [cite: 101]">
                <div class="flex items-center gap-8 [cite: 101]">
                    <div class="relative [cite: 102]">
                        <c:choose>
                            <c:when test="${not empty brand.imageUrl}">
                                <%-- SỬA: Thêm contextPath để hiện ảnh hiện tại [cite: 103] --%>
                                <img src="${pageContext.request.contextPath}/${brand.imageUrl}" id="preview" 
                                     class="w-24 h-24 object-contain rounded-2xl border-4 border-white shadow-xl bg-white p-2 [cite: 103]">
                            </c:when>
                            <c:otherwise>
                                <div class="w-24 h-24 bg-gray-100 rounded-2xl flex items-center justify-center border-2 border-dashed border-gray-200 [cite: 104]">
                                    <svg class="w-8 h-8 text-gray-300" fill="none" stroke="currentColor" viewBox="0 0 24 24"><path d="M4 16l4.586-4.586a2 2 0 012.828 0L16 16m-2-2l1.586-1.586a2 2 0 012.828 0L20 14m-6-6h.01M6 20h12a2 2 0 002-2V6a2 2 0 00-2-2H6a2 2 0 00-2 2v12a2 2 0 002 2z"/></svg>
                                </div>
                            </c:otherwise>
                        </c:choose>
                    </div>
                    <div class="flex-1 space-y-2 [cite: 107]">
                        <input type="file" name="brandLogo" accept="image/*" onchange="previewImage(this)"
                               class="block w-full text-xs text-gray-500 file:mr-4 file:py-2 file:px-4 file:rounded-full file:border-0 file:text-xs file:font-bold file:bg-blue-600 file:text-white hover:file:bg-blue-700 cursor-pointer [cite: 107]">
                        <p class="text-[10px] text-blue-300 italic">* Select a new image if you want to update the logo. [cite: 108]</p>
                    </div>
                </div>
            </div>
        </div>

        <%-- NÚT ĐIỀU HƯỚNG --%>
        <div class="flex justify-end gap-3 pt-6 border-t border-gray-100">
            <a href="brandServlet?action=all" 
               class="inline-flex items-center gap-2 px-6 py-2.5 bg-white text-gray-600 rounded-xl hover:bg-gray-50 transition-all font-bold border border-gray-200 text-sm">
                <svg class="w-4 h-4" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M10 19l-7-7m0 0l7-7m-7 7h18" />
                </svg>
                Back to List
            </a>

            <button type="submit" 
                    class="inline-flex items-center gap-2 px-8 py-2.5 bg-blue-600 text-white rounded-xl hover:bg-blue-700 shadow-lg shadow-blue-200 transition-all font-bold text-sm uppercase tracking-tight">
                Update Brand
            </button>
        </div>
    </form>
</div>

<script>
    function previewImage(input) {
        if (input.files && input.files[0]) {
            var reader = new FileReader();
            reader.onload = function (e) {
                const img = document.getElementById('preview');
                if (img)
                    img.src = e.target.result;
            };
            reader.readAsDataURL(input.files[0]);
        }
    }
</script>