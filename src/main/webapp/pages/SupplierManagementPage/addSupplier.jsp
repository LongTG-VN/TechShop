<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%-- Thêm nhà cung cấp: POST supplier?action=add; thông báo flash qua session --%>
<div class="w-full flex flex-col items-center">
    <c:if test="${not empty sessionScope.msg}">
        <div class="mb-4 w-full max-w-xl">
            <div class="px-4 py-3 rounded-lg
                 ${sessionScope.msgType == 'danger' ? 'bg-red-50 text-red-800 ring-1 ring-red-200/60' : 'bg-green-50 text-green-800 ring-1 ring-emerald-200/60'}">
                ${sessionScope.msg}
            </div>
        </div>
        <c:remove var="msg" scope="session"/>
        <c:remove var="msgType" scope="session"/>
    </c:if>

    <div class="w-full max-w-xl rounded-xl bg-white p-6 sm:p-8 shadow-lg ring-1 ring-gray-100">
        <h2 class="text-xl font-bold mb-4">Add supplier</h2>

        <form action="supplier" method="POST" id="addSupplierForm"
              onsubmit="return validateSupplierNameField(document.getElementById('addSupplierName'));">
            <input type="hidden" name="action" value="add"/>

            <div class="mb-3">
                <label class="block mb-1 font-medium" for="addSupplierName">Supplier name *</label>
                <input id="addSupplierName" type="text" name="supplier_name" required maxlength="100"
                       class="w-full px-3 py-2 border rounded" placeholder="Supplier name..."
                       title="Letters, digits, Vietnamese, spaces, &, . and -"
                       autocomplete="organization"
                       oninput="filterSupplierNameInput(this)">
                <p class="text-xs text-gray-500 mt-1">Allowed: letters, digits, Vietnamese, spaces, &amp;, . and -</p>
            </div>

            <div class="mb-3">
                <label class="block mb-1 font-medium">Phone *</label>
                <input type="tel" name="phone" required maxlength="10" pattern="[0-9]{10}"
                       inputmode="numeric" title="Exactly 10 digits"
                       class="w-full px-3 py-2 border rounded" placeholder="10 digits"
                       oninput="this.value=this.value.replace(/[^0-9]/g,'')">
                <p class="text-xs text-gray-500 mt-1">Must be 10 digits and unique — not used by another supplier.</p>
            </div>

            <div class="mb-3">
                <label class="block mb-1 font-medium" for="addSupplierEmail">Email</label>
                <input id="addSupplierEmail" type="email" name="email" maxlength="255"
                       class="w-full px-3 py-2 border rounded" placeholder="email@example.com (optional)"
                       autocomplete="email"
                       onblur="if (this.value)
                                   this.value = this.value.trim().toLowerCase()">
            </div>

            <div class="mb-3">
                <label class="block mb-1 font-medium" for="addSupplierAddress">Address</label>
                <textarea id="addSupplierAddress" name="address" rows="1" maxlength="500"
                          class="supplier-address-textarea block w-full px-3 py-2 border rounded text-gray-900 placeholder:text-gray-400 focus:outline-none focus:ring-2 focus:ring-blue-200 resize-none break-words"
                          placeholder="Warehouse / office address (optional)"
                          oninput="autoResizeAddress(this)"></textarea>
            </div>

            <div class="mb-4">
                <label class="inline-flex items-center gap-2">
                    <input type="checkbox" name="is_active" value="1" checked class="w-4 h-4">
                    <span>Active</span>
                </label>
            </div>

            <div class="flex flex-wrap gap-3 pt-1">
                <button type="submit" class="px-5 py-2.5 rounded-lg bg-blue-600 text-white hover:bg-blue-700 border-0 shadow-sm focus:outline-none focus:ring-2 focus:ring-blue-400 focus:ring-offset-1">Save</button>
                <a href="staffservlet?action=supplierManagement" class="inline-flex items-center px-5 py-2.5 rounded-lg bg-gray-100 text-gray-800 hover:bg-gray-200 border-0 transition-colors">Cancel</a>
            </div>
        </form>
    </div>
</div>
<style>
    textarea.supplier-address-textarea {
        resize: none !important;
        min-height: 2.5rem;
        max-height: 12rem;
        overflow-y: hidden;
        line-height: 1.5;
    }
    textarea.supplier-address-textarea::-webkit-resizer {
        display: none;
    }
</style>
<script>
    /*
     * Form thêm nhà cung cấp:
     * - Ô địa chỉ tự cao theo nội dung (có trần để không chiếm cả màn hình)
     * - Lọc ký tự tên realtime; validate tên trước submit (khớp servlet)
     */
    /** Điều chỉnh chiều cao textarea địa chỉ theo nội dung; vượt ngưỡng thì bật cuộn trong khung. */
    function autoResizeAddress(el) {
        if (!el)
            return;
        var cap = 192;
        el.style.height = 'auto';
        el.style.overflowY = 'hidden';
        var sh = el.scrollHeight;
        if (sh > cap) {
            el.style.height = cap + 'px';
            el.style.overflowY = 'auto';
        } else {
            var h = sh < 40 ? 40 : sh;
            el.style.height = h + 'px';
        }
    }

    /** Sau khi trang load: căn chiều cao textarea địa chỉ theo nội dung ban đầu. */
    function initAddSupplierAddressOnLoad() {
        var ta = document.getElementById('addSupplierAddress');
        if (ta)
            autoResizeAddress(ta);
    }
    document.addEventListener('DOMContentLoaded', initAddSupplierAddressOnLoad);

    /** Xóa ký tự không nằm trong bộ cho phép của tên nhà cung cấp (đồng bộ với servlet). */
    function filterSupplierNameInput(el) {
        if (!el)
            return;
        try {
            el.value = el.value.replace(/[^a-zA-Z0-9À-ỹ\s&.-]/g, '');
        } catch (e) {
            el.value = el.value.replace(/[^a-zA-Z0-9À-ỹ\s&.-]/g, '');
        }
    }

    /** Trước khi gửi form: trim tên, bắt buộc có nội dung, regex giống máy chủ; dùng setCustomValidity của trình duyệt. */
    function validateSupplierNameField(el) {
        if (!el)
            return true;
        var v = el.value.replace(/^\s+|\s+$/g, '');
        el.value = v;
        if (!v.length) {
            el.setCustomValidity('Please enter supplier name.');
            el.reportValidity();
            return false;
        }
        var ok;
        try {
            ok = /^[a-zA-Z0-9À-ỹ\s&.-]+$/.test(v);
        } catch (e) {
            ok = /^[a-zA-Z0-9À-ỹ\s&.-]+$/.test(v);
        }
        if (!ok) {
            el.setCustomValidity('Invalid name. Letters, digits, Vietnamese, spaces, &, . and - only.');
            el.reportValidity();
            return false;
        }
        el.setCustomValidity('');
        return true;
    }
</script>