<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
<div class="space-y-6 font-sans text-gray-900">

    <%-- ADD NEW --%>
    <c:if test="${showAddForm}">
        <div class="bg-white p-6 rounded-xl shadow-md border-t-4 border-blue-600 animate-fade-in">
            <h3 class="text-lg font-bold text-gray-800 mb-4 flex items-center">
                <svg class="w-5 h-5 mr-2 text-blue-600" fill="currentColor" viewBox="0 0 20 20">
                <path fill-rule="evenodd" d="M10 3a1 1 0 011 1v5h5a1 1 0 110 2h-5v5a1 1 0 11-2 0v-5H4a1 1 0 110-2h5V4a1 1 0 011-1z" clip-rule="evenodd"/>
                </svg>
                Add new brand
            </h3>
            <form action="brand" method="POST" class="grid grid-cols-1 md:grid-cols-3 gap-4 items-end">
                <input type="hidden" name="action" value="addProcess">
                <div>
                    <label class="block text-sm font-medium text-gray-700 mb-1">Brand Name</label>
                    <input type="text" name="brandName" required class="w-full px-3 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-500 outline-none">
                </div>
                <div class="flex items-center h-10">
                    <input type="checkbox" name="isActive" id="isActiveAdd" checked class="w-4 h-4 text-blue-600 rounded">
                    <label for="isActiveAdd" class="ml-2 text-sm text-gray-600">Active now</label>
                </div>
                <div class="flex gap-2">
                    <button type="submit" class="bg-blue-600 text-white px-6 py-2 rounded-lg hover:bg-blue-700 font-medium transition">Save</button>
                    <a href="brand?action=brandManagement" class="bg-gray-100 text-gray-600 px-6 py-2 rounded-lg hover:bg-gray-200 transition">Cancel</a>
                </div>
            </form>
        </div>
    </c:if>

    <%-- 2. EDIT --%>
    <c:if test="${not empty brandToEdit}">
        <div class="bg-white p-6 rounded-xl shadow-md border-t-4 border-yellow-500 animate-fade-in mb-6">
            <h3 class="text-lg font-bold text-gray-800 mb-4 flex items-center">
                <svg class="w-5 h-5 mr-2 text-yellow-500" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M11 5H6a2 2 0 00-2 2v11a2 2 0 002 2h11a2 2 0 002-2v-5m-1.414-9.414a2 2 0 112.828 2.828L11.828 15H9v-2.828l8.586-8.586z"></path>
                </svg>
                Edit brand #${brandToEdit.brandId}
            </h3>
            <form action="brand" method="POST" class="grid grid-cols-1 md:grid-cols-3 gap-4 items-end">
                <input type="hidden" name="action" value="updateProcess">
                <input type="hidden" name="brandId" value="${brandToEdit.brandId}">
                <div>
                    <label class="block text-sm font-medium text-gray-700 mb-1">Brand Name</label>
                    <input type="text" name="brandName" value="${brandToEdit.brandName}" required 
                           class="w-full px-3 py-2 border border-yellow-300 rounded-lg focus:ring-2 focus:ring-yellow-500 outline-none">
                </div> 
                <div class="flex flex-col">
                    <label class="block text-sm font-medium text-gray-700 mb-1">Status</label>
                    <div class="flex items-center h-[42px]">
                        <input type="checkbox" name="isActive" id="isActiveEdit" 
                               ${brandToEdit.isIsActive() ? 'checked' : ''} 
                               class="w-6 h-6 text-yellow-500 border-2 border-yellow-300 rounded focus:ring-yellow-500 focus:ring-2 transition cursor-pointer">
                        <label for="isActiveEdit" class="ml-3 text-sm font-medium text-gray-600 cursor-pointer">
                            Active
                        </label>
                    </div>
                </div>
                <div class="flex gap-2">
                    <button type="submit" class="bg-yellow-500 text-white px-6 py-2 rounded-lg hover:bg-yellow-600 font-medium transition">Update</button>
                    <a href="brand?action=brandManagement" class="bg-gray-100 text-gray-600 px-6 py-2 rounded-lg hover:bg-gray-200 transition">Cancel</a>
                </div>
            </form>
        </div>
    </c:if>

    <%-- 3. DETAIL --%>
    <c:if test="${not empty brandDetail}">
        <div class="p-6 bg-blue-50 border border-blue-200 rounded-xl shadow-sm relative animate-fade-in mb-6">
            <div class="flex justify-between items-start mb-4">
                <h3 class="text-blue-800 font-bold uppercase text-sm tracking-wider">Detailed Information</h3>
                <div class="flex items-center space-x-2">
                    <a href="brand?action=edit&id=${brandDetail.brandId}" 
                       class="bg-yellow-500 hover:bg-yellow-600 text-white px-3 py-1.5 rounded-lg text-xs font-bold transition flex items-center shadow-sm">
                        Edit information
                    </a>
                    <a href="brand?action=brandManagement" class="text-gray-400 hover:text-red-500 transition">
                        <svg class="w-6 h-6" fill="none" stroke="currentColor" viewBox="0 0 24 24"><path d="M6 18L18 6M6 6l12 12"></path></svg>
                    </a>
                </div>
            </div>
            <div class="grid grid-cols-2 md:grid-cols-4 gap-6">
                <div>
                    <p class="text-xs text-gray-500 uppercase font-semibold">ID</p>
                    <p class="font-bold text-lg text-gray-900">#${brandDetail.brandId}</p>
                </div>
                <div>
                    <p class="text-xs text-gray-500 uppercase font-semibold">Brand Name</p>
                    <p class="font-bold text-lg text-gray-900">${brandDetail.brandName}</p>
                </div>
                <div class="flex flex-col items-center">
                    <p class="text-xs text-gray-500 uppercase font-semibold mb-1">Status</p>
                    <span class="inline-flex items-center px-3 py-1 rounded-full text-xs font-bold ${brandDetail.isIsActive() ? 'bg-green-100 text-green-700' : 'bg-red-100 text-red-700'}">
                        ${brandDetail.isIsActive() ? 'Active' : 'Inactive'}
                    </span>
                </div>
            </div>
        </div>
    </c:if>

    <%-- LIST --%>
    <div class="bg-white rounded-xl shadow-lg p-5">
        <div class="flex flex-col md:flex-row md:items-center md:justify-between gap-4 mb-5">
            <div class="w-full md:w-2/3 flex gap-3">
                <div class="relative flex-1">
                    <span class="absolute inset-y-0 left-0 flex items-center pl-3 text-gray-400">
                        <svg class="w-5 h-5" fill="currentColor" viewBox="0 0 20 20"><path fill-rule="evenodd" d="M8 4a4 4 0 100 8 4 4 0 000-8zM2 8a6 6 0 1110.89 3.476l4.817 4.817a1 1 0 01-1.414 1.414l-4.816-4.816A6 6 0 012 8z" clip-rule="evenodd"/></svg>
                    </span>
                    <input type="text" id="liveSearchInput" onkeyup="applyFilters()" 
                           class="w-full pl-10 pr-4 py-2 text-sm border border-gray-300 rounded-lg outline-none focus:ring-2 focus:ring-blue-500" 
                           placeholder="Search...">
                </div>
                <select id="statusFilter" onchange="applyFilters()" 
                        class="px-4 py-2 text-sm border border-gray-300 rounded-lg outline-none bg-white cursor-pointer shadow-sm focus:ring-2 focus:ring-blue-500 font-medium">
                    <option value="all">All Status</option>
                    <option value="active">Only Active</option>
                    <option value="inactive">Only Inactive</option>
                </select>
            </div>
            <a href="brand?action=add" class="inline-flex items-center px-4 py-2 text-sm font-bold text-white bg-blue-600 rounded-lg hover:bg-blue-700 transition shadow-sm">+ Add New Brand</a>
        </div>

        <div class="overflow-x-auto rounded-lg border border-gray-200">
            <table class="w-full text-sm text-left text-gray-600">
                <thead class="text-xs uppercase bg-gray-50 text-gray-700 font-semibold">
                    <tr>
                        <th class="px-6 py-3 w-20 text-center">ID</th>
                        <th class="px-6 py-3">Brand Name</th>
                        <th class="px-6 py-3 text-center">Status</th>
                        <th class="px-6 py-3 text-center w-60">Action</th>
                    </tr>
                </thead>
                <tbody id="brandTableBody" class="divide-y divide-gray-200 bg-white">
                    <c:forEach items="${brandList}" var="b">
                        <tr class="brand-row hover:bg-gray-50 transition-colors" 
                            data-status="${b.isIsActive() ? 'active' : 'inactive'}">
                            <td class="px-6 py-4 text-center text-gray-500 font-medium">#${b.brandId}</td>
                            <td class="brand-name px-6 py-4 font-bold text-gray-900">${b.brandName}</td>
                            <td class="px-6 py-4 text-center">
                                <span class="px-3 py-1 text-xs font-medium rounded-full ${b.isIsActive() ? 'bg-green-100 text-green-700' : 'bg-red-100 text-red-700'}">
                                    ${b.isIsActive() ? 'Active' : 'Inactive'}
                                </span>
                            </td>
                            <td class="px-6 py-4">
                                <div class="flex items-center justify-center space-x-3 whitespace-nowrap">
                                    <a href="brand?action=detail&id=${b.brandId}" class="text-gray-500 hover:text-blue-600 font-medium transition hover:underline">Detail</a>
                                    <a href="brand?action=edit&id=${b.brandId}" class="text-blue-600 hover:text-blue-800 font-medium transition hover:underline">Edit</a>
                                    <button onclick="showDeleteOptions('${b.brandId}', '${b.brandName}')" class="text-red-600 hover:text-red-800 font-medium transition hover:underline focus:outline-none">Delete</button>
                                </div>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</div>

<script>
    function applyFilters() {
        let search = document.getElementById('liveSearchInput').value.toLowerCase();
        let status = document.getElementById('statusFilter').value;
        let rows = document.getElementsByClassName('brand-row');
        for (let row of rows) {
            let name = row.getElementsByClassName('brand-name')[0].textContent.toLowerCase();
            let state = row.getAttribute('data-status');
            let matchSearch = name.includes(search);
            let matchStatus = (status === 'all' || status === state);
            row.style.display = (matchSearch && matchStatus) ? "" : "none";
        }
    }

    function showDeleteOptions(id, name) {
        Swal.fire({
            title: 'How would you like to delete "' + name + '"?',
            icon: 'warning',
            showDenyButton: true,
            showCancelButton: true,
            confirmButtonText: 'Deactivate', // Ngừng hoạt động (Soft Delete)
            denyButtonText: 'Delete Permanently', // Xóa vĩnh viễn (Hard Delete)
            cancelButtonText: 'Cancel'
        }).then((result) => {
            if (result.isConfirmed)
                window.location.href = 'brand?action=softDelete&id=' + id;
            else if (result.isDenied)
                window.location.href = 'brand?action=delete&id=' + id;
        });
    }
</script>