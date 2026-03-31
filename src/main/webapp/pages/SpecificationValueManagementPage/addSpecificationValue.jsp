<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%-- 1. THÔNG BÁO TOAST (Giữ nguyên logic của bạn) --%>
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

<div class="max-w-2xl mx-auto bg-white p-8 rounded-2xl shadow-xl border mt-10">
    <h2 class="text-2xl font-bold text-gray-900 mb-8 uppercase text-center border-b pb-4">Assign Spec to Product</h2>

    <form action="specificationValueServlet" method="POST" class="space-y-6">
        <input type="hidden" name="action" value="add">

        <%-- Chọn sản phẩm --%>
        <div class="space-y-2">
            <label class="text-xs font-bold text-gray-400 uppercase tracking-widest">Select Product</label>
            <select name="productId" id="productSelect" required class="w-full p-3 bg-gray-50 border border-gray-200 rounded-lg outline-none focus:ring-2 focus:ring-blue-500">
                <option value="">-- Select Product --</option>
                <c:forEach items="${products}" var="p">
                    <option value="${p.productId}">${p.name}</option>
                </c:forEach>
            </select>
        </div>

        <%-- Chọn thông số (Sẽ được nạp tự động qua AJAX) --%>
        <div class="space-y-2">
            <label class="text-xs font-bold text-gray-400 uppercase tracking-widest">Select Specification</label>
            <select name="specId" id="specSelect" required class="w-full p-3 bg-gray-50 border border-gray-200 rounded-lg outline-none focus:ring-2 focus:ring-blue-500">
                <option value="">-- Select Product First --</option>
            </select>
        </div>

        <%-- Nhập giá trị --%>
        <div class="space-y-2">
            <label class="text-xs font-bold text-gray-400 uppercase tracking-widest">Value</label>
            <input type="text" name="specValue" required placeholder="e.g. 16GB, OLED, Black"
                   pattern="^[a-zA-Z0-9\s-]+$" title="Please enter only letters, numbers, spaces and hyphens."
                   class="w-full p-3 bg-gray-50 border border-gray-200 rounded-lg outline-none focus:ring-2 focus:ring-blue-500 font-medium">
        </div>

        <div class="flex justify-end gap-4 pt-4">
            <a href="specificationValueServlet?action=all" class="px-6 py-2 text-gray-500 font-bold hover:bg-gray-100 rounded-lg">Cancel</a>
            <button type="submit" class="px-8 py-2 bg-blue-600 text-white font-bold rounded-lg hover:bg-blue-700 shadow-lg transition-all transform hover:-translate-y-0.5">
                Save Assignment
            </button>
        </div>
    </form>
</div>

<script>
    document.getElementById('productSelect').addEventListener('change', function () {
        const productId = this.value;
        const specSelect = document.getElementById('specSelect');

        // Reset dropdown thông số
        specSelect.innerHTML = '<option value="">Loading...</option>';

        if (!productId) {
            specSelect.innerHTML = '<option value="">-- Select Product First --</option>';
            return;
        }

        // Gọi AJAX đến Servlet
        fetch("specificationValueServlet?action=getSpecsByProductId&productId=" + productId)
                .then(response => {
                    if (!response.ok)
                        throw new Error('Network response was not ok');
                    return response.json();
                })
                .then(data => {
                    specSelect.innerHTML = '<option value="">-- Select Specification --</option>';
                    if (data.length === 0) {
                        specSelect.innerHTML = '<option value="">No specifications found for this category</option>';
                        return;
                    }
                    data.forEach(spec => {
                        const option = document.createElement('option');
                        option.value = spec.specId;
                        option.textContent = spec.specName + (spec.unit ? ' (' + spec.unit + ')' : '');
                        specSelect.appendChild(option);
                    });
                })
                .catch(error => {
                    console.error('Error:', error);
                    specSelect.innerHTML = '<option value="">Error loading specs</option>';
                });
    });
    </script