<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:if test="${not empty sessionScope.msg}">
    <div class="mb-4">
        <div class="px-4 py-3 rounded border
             ${sessionScope.msgType == 'danger' ? 'bg-red-50 text-red-800 border-red-200' : 'bg-green-50 text-green-800 border-green-200'}">
            ${sessionScope.msg}
        </div>
    </div>
    <c:remove var="msg" scope="session"/>
    <c:remove var="msgType" scope="session"/>
</c:if>

<c:if test="${empty supplier}">
    <div class="p-4 bg-white rounded border">
        <div class="text-red-700 font-medium mb-2">Supplier not found.</div>
        <a class="text-blue-600 hover:underline" href="staffservlet?action=supplierManagement">Back to list</a>
    </div>
</c:if>

<c:if test="${not empty supplier}">
    <div class="bg-white p-5 rounded-xl border shadow-sm max-w-xl">
        <h2 class="text-xl font-bold mb-4">Edit supplier (#${supplier.supplier_id})</h2>

        <form action="supplier" method="POST" id="editSupplierForm"
              onsubmit="return validateSupplierNameField(document.getElementById('editSupplierName'));">
            <input type="hidden" name="action" value="update"/>
            <input type="hidden" name="supplier_id" value="${supplier.supplier_id}"/>

            <div class="mb-3">
                <label class="block mb-1 font-medium" for="editSupplierName">Supplier name *</label>
                <input id="editSupplierName" type="text" name="supplier_name" required maxlength="100"
                       value="<c:out value='${supplier.supplier_name}'/>"
                       class="w-full px-3 py-2 border rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-200" placeholder="Nhập tên..."
                       title="Letters and spaces only (Unicode / Vietnamese OK)"
                       autocomplete="organization"
                       oninput="filterSupplierNameInput(this)">
                <p class="text-xs text-gray-500 mt-1">Chỉ chữ cái và khoảng trắng; không số, không ký tự đặc biệt.</p>
            </div>

            <div class="mb-3">
                <label class="block mb-1 font-medium">Phone *</label>
                <input type="tel" name="phone" required maxlength="10" pattern="[0-9]{10}"
                       inputmode="numeric" title="Enter exactly 10 digits"
                       value="${supplier.phone}"
                       class="w-full px-3 py-2 border rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-200" placeholder="10 chữ số"
                       oninput="this.value=this.value.replace(/[^0-9]/g,'')">
            </div>

            <div class="mb-4">
                <label class="inline-flex items-center gap-2">
                    <input type="checkbox" name="is_active" value="1" <c:if test="${supplier.is_active}">checked</c:if> class="w-4 h-4">
                        <span>Active</span>
                    </label>
                </div>

                <div class="flex gap-2">
                    <button type="submit" class="px-4 py-2 rounded-lg bg-blue-600 text-white hover:bg-blue-700 shadow-sm">
                        Save
                    </button>
                    <a href="staffservlet?action=supplierManagement" class="px-4 py-2 rounded-lg border bg-gray-50 hover:bg-gray-100">Cancel</a>
                </div>
            </form>
        </div>
<script>
    function filterSupplierNameInput(el) {
        if (!el) return;
        try {
            el.value = el.value.replace(/[^\p{L} ]/gu, '');
        } catch (e) {
            el.value = el.value.replace(/[^a-zA-ZÀ-ỹà-ỹĂăÂâĐđÊêÔôƠơƯư\s]/g, '');
        }
    }
    function validateSupplierNameField(el) {
        if (!el) return true;
        var v = el.value.replace(/^\s+|\s+$/g, '');
        el.value = v;
        if (!v.length) {
            el.setCustomValidity('Vui lòng nhập tên nhà cung cấp.');
            el.reportValidity();
            return false;
        }
        var ok;
        try {
            ok = /^[\p{L} ]+$/u.test(v);
        } catch (e) {
            ok = /^[a-zA-ZÀ-ỹà-ỹĂăÂâĐđÊêÔôƠơƯư ]+$/.test(v);
        }
        if (!ok) {
            el.setCustomValidity('Chỉ được chữ cái và khoảng trắng.');
            el.reportValidity();
            return false;
        }
        el.setCustomValidity('');
        return true;
    }
</script>
</c:if>