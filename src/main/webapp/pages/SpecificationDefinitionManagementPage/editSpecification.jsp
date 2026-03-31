<%@page contentType="text/html" pageEncoding="UTF-8" %>
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
<div class="max-w-3xl mx-auto bg-white p-8 rounded-2xl shadow-xl border border-gray-100 mt-10">
    <div class="flex justify-between items-center border-b pb-6 mb-8">
        <h2 class="text-2xl font-bold text-gray-900 uppercase">Edit Specification</h2>
        <p class="text-blue-600 font-mono font-bold">#${spec.specId}</p>
    </div>

    <form action="specificationServlet" method="POST" class="space-y-6">
        <input type="hidden" name="action" value="update">
        <input type="hidden" name="specId" value="${spec.specId}">

        <div class="grid grid-cols-1 md:grid-cols-2 gap-6">
            <div class="space-y-2">
                <label class="text-xs font-bold text-gray-400 uppercase tracking-widest">Spec Name</label>
                <input type="text" name="specName" value="${spec.specName}"
                       class="w-full p-3 bg-gray-50 border border-gray-200 rounded-lg focus:ring-2 focus:ring-blue-500 outline-none"
                       placeholder="e.g. RAM, Screen Size"
                       required 
                       pattern="^[a-zA-Z\s]+$" 
                       title="Please enter only alphabet characters and spaces."/>
            </div>

            <div class="space-y-2">
                <label class="text-xs font-bold text-gray-400 uppercase tracking-widest">Unit</label>
                <input type="text" name="unit" value="${spec.unit}"
                       class="w-full p-3 bg-gray-50 border border-gray-200 rounded-lg focus:ring-2 focus:ring-orange-500 outline-none">
            </div>

            <div class="space-y-2">
                <label class="text-xs font-bold text-gray-400 uppercase tracking-widest">Category</label>
                <select name="categoryId" required
                        class="w-full p-3 bg-gray-50 border border-gray-200 rounded-lg outline-none">
                    <c:forEach items="${categories}" var="cat">
                        <option value="${cat.categoryId}" ${cat.categoryId==spec.categoryId ? 'selected' : '' }>
                            ${cat.categoryName}
                        </option>
                    </c:forEach>
                </select>
            </div>

            <div class="space-y-2">
                <label class="text-xs font-bold text-gray-400 uppercase tracking-widest">Status</label>
                <div class="flex gap-4 mt-2">
                    <label class="flex items-center gap-2 cursor-pointer">
                        <input type="radio" name="isActive" value="1" ${spec.isActive ? 'checked' : '' }
                               class="w-4 h-4 text-orange-600">
                        <span class="text-sm font-medium text-gray-700">Active</span>
                    </label>
                    <label class="flex items-center gap-2 cursor-pointer">
                        <input type="radio" name="isActive" value="0" ${!spec.isActive ? 'checked' : '' }
                               class="w-4 h-4 text-orange-600">
                        <span class="text-sm font-medium text-gray-700">Inactive</span>
                    </label>
                </div>
            </div>

            <%-- Loại thông số: General hay Variant --%>
            <div class="space-y-2">
                <label class="text-xs font-bold text-gray-400 uppercase tracking-widest">Spec Type</label>
                <div class="flex gap-6 mt-2">
                    <label class="flex items-center gap-2 cursor-pointer group">
                        <input type="radio" name="isVariant" value="0" ${!spec.isVariant ? 'checked' : '' }
                               class="w-5 h-5 text-orange-600 focus:ring-orange-400">
                        <div class="flex flex-col">
                            <span
                                class="text-sm font-bold text-gray-700 group-hover:text-orange-600 transition-colors">General
                                Spec</span>
                        </div>
                    </label>
                    <label
                        class="flex items-center gap-2 cursor-pointer group border-l pl-6 border-gray-100">
                        <input type="radio" name="isVariant" value="1" ${spec.isVariant ? 'checked' : '' }
                               class="w-5 h-5 text-purple-600 focus:ring-purple-400">
                        <div class="flex flex-col">
                            <span
                                class="text-sm font-bold text-gray-700 group-hover:text-purple-600 transition-colors">Variant
                                Spec</span>
                        </div>
                    </label>
                </div>
            </div>
        </div>

        <div class="pt-8 border-t flex justify-end gap-4">
            <a href="specificationServlet?action=all"
               class="px-6 py-2.5 text-gray-500 font-bold hover:bg-gray-100 rounded-lg">Cancel</a>
            <button type="submit"
                    class="px-10 py-2.5 bg-orange-600 text-white font-bold rounded-lg hover:bg-orange-700 shadow-lg transform hover:-translate-y-0.5 transition-all">
                Update Changes
            </button>
        </div>
    </form>
</div>