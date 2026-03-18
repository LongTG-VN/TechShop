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

<div class="bg-white p-4 rounded border max-w-xl">
    <h2 class="text-xl font-bold mb-4">Add supplier</h2>

    <form action="supplier" method="POST">
        <input type="hidden" name="action" value="add"/>

        <div class="mb-3">
            <label class="block mb-1 font-medium">Supplier name *</label>
            <input type="text" name="supplier_name" required maxlength="100"
                   class="w-full px-3 py-2 border rounded" placeholder="Enter supplier name...">
        </div>

        <div class="mb-3">
            <label class="block mb-1 font-medium">Phone *</label>
            <input type="tel" name="phone" required maxlength="10" pattern="[0-9]{10}"
                   inputmode="numeric" title="Enter exactly 10 digits"
                   class="w-full px-3 py-2 border rounded" placeholder="10 digits"
                   oninput="this.value=this.value.replace(/[^0-9]/g,'')">
        </div>

        <div class="mb-4">
            <label class="inline-flex items-center gap-2">
                <input type="checkbox" name="is_active" value="1" checked class="w-4 h-4">
                <span>Active</span>
            </label>
        </div>

        <div class="flex gap-2">
            <button type="submit" class="px-4 py-2 rounded bg-blue-600 text-white hover:bg-blue-700">Add</button>
            <a href="staffservlet?action=supplierManagement" class="px-4 py-2 rounded border bg-gray-50 hover:bg-gray-100">Cancel</a>
        </div>
    </form>
</div>