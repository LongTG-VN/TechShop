<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="flex justify-center items-start pt-10 px-4 pb-20 bg-gray-50 min-h-screen">
    <div class="w-full max-w-2xl bg-white rounded-2xl shadow-2xl border border-gray-100 p-8">

        <div class="flex flex-col items-center mb-10 border-b border-gray-50 pb-8">
            <div class="relative">
                <div class="w-24 h-24 bg-gradient-to-tr from-indigo-600 to-blue-400 text-white rounded-full flex items-center justify-center text-3xl font-bold shadow-lg mb-4">
                    ${employee.fullName.substring(0,1).toUpperCase()}
                </div>
                <span class="absolute bottom-5 right-1 w-5 h-5 ${employee.status == 'ACTIVE' ? 'bg-green-500' : 'bg-red-500'} border-4 border-white rounded-full"></span>
            </div>
            <h2 class="text-3xl font-extrabold text-gray-900 tracking-tight">${employee.fullName}</h2>
            <p class="text-indigo-600 font-bold uppercase text-sm tracking-widest mt-1">
                ${not empty employee.role.role_name ? employee.role.role_name : 'Staff Member'}
            </p>
        </div>

        <div class="grid grid-cols-1 md:grid-cols-2 gap-8 mb-10">
            <div class="flex items-start space-x-4">
                <div class="p-3 bg-gray-50 rounded-lg text-gray-400">
                    <svg class="w-6 h-6" fill="none" stroke="currentColor" viewBox="0 0 24 24"><path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M16 7a4 4 0 11-8 0 4 4 0 018 0zM12 14a7 7 0 00-7 7h14a7 7 0 00-7-7z"></path></svg>
                </div>
                <div>
                    <p class="text-xs font-semibold text-gray-400 uppercase tracking-wider">System ID</p>
                    <p class="text-gray-900 font-medium">@${employee.username}</p>
                </div>
            </div>

            <div class="flex items-start space-x-4">
                <div class="p-3 bg-gray-50 rounded-lg text-gray-400">
                    <svg class="w-6 h-6" fill="none" stroke="currentColor" viewBox="0 0 24 24"><path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M3 8l7.89 5.26a2 2 0 002.22 0L21 8M5 19h14a2 2 0 002-2V7a2 2 0 00-2-2H5a2 2 0 00-2-2v10a2 2 0 002 2z"></path></svg>
                </div>
                <div>
                    <p class="text-xs font-semibold text-gray-400 uppercase tracking-wider">Work Email</p>
                    <p class="text-gray-900 font-medium">${employee.email}</p>
                </div>
            </div>

            <div class="flex items-start space-x-4">
                <div class="p-3 bg-gray-50 rounded-lg text-gray-400">
                    <svg class="w-6 h-6" fill="none" stroke="currentColor" viewBox="0 0 24 24"><path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M3 5a2 2 0 012-2h3.28a1 1 0 01.948.684l1.498 4.493a1 1 0 01-.502 1.21l-2.257 1.13a11.042 11.042 0 005.516 5.516l1.13-2.257a1 1 0 011.21-.502l4.493 1.498a1 1 0 01.684.949V19a2 2 0 01-2 2h-1C9.716 21 3 14.284 3 6V5z"></path></svg>
                </div>
                <div>
                    <p class="text-xs font-semibold text-gray-400 uppercase tracking-wider">Contact Number</p>
                    <p class="text-gray-900 font-medium">${employee.phoneNumber}</p>
                </div>
            </div>

            <div class="flex items-start space-x-4">
                <div class="p-3 bg-gray-50 rounded-lg text-gray-400">
                    <svg class="w-6 h-6" fill="none" stroke="currentColor" viewBox="0 0 24 24"><path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M8 7V3m8 4V3m-9 8h10M5 21h14a2 2 0 002-2V7a2 2 0 00-2-2H5a2 2 0 00-2 2v10a2 2 0 002 2z"></path></svg>
                </div>
                <div>
                    <p class="text-xs font-semibold text-gray-400 uppercase tracking-wider">Hired On</p>
                    <p class="text-gray-900 font-medium">${employee.createdAt}</p>
                </div>
            </div>
        </div>

        <div class="mt-10 pt-8 border-t border-gray-100">
            <h3 class="text-lg font-bold text-gray-900 mb-6 flex items-center">
                <svg class="w-5 h-5 mr-2 text-indigo-600" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                    <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M9 12l2 2 4-4m5.618-4.016A11.955 11.955 0 0112 2.944a11.955 11.955 0 01-8.618 3.04A12.02 12.02 0 003 9c0 5.591 3.824 10.29 9 11.622 5.176-1.332 9-6.03 9-11.622 0-1.042-.133-2.052-.382-3.016z"></path>
                </svg>
                Account Security & Status
            </h3>
            
            <div class="bg-gray-50 rounded-xl p-6 border border-gray-100">
                <div class="flex items-center justify-between">
                    <div>
                        <p class="text-sm font-bold text-gray-800">Account Standing</p>
                        <p class="text-xs text-gray-500 mt-1">Status determines system access permissions.</p>
                    </div>
                    <span class="px-4 py-1.5 text-xs font-bold rounded-full ${employee.status == 'ACTIVE' ? 'bg-green-100 text-green-700' : 'bg-red-100 text-red-700'}">
                        ${employee.status}
                    </span>
                </div>
            </div>
        </div>

        <div class="flex items-center justify-between pt-10 mt-10 border-t border-gray-50">
            <a href="employeeservlet?action=all" 
               class="text-sm font-medium text-gray-500 hover:text-indigo-600 transition-colors flex items-center gap-1 group">
                <svg class="w-4 h-4 transition-transform group-hover:-translate-x-1" fill="none" stroke="currentColor" viewBox="0 0 24 24"><path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M15 19l-7-7 7-7"></path></svg>
                Back to Staff List
            </a> 
        </div>
    </div>
</div>