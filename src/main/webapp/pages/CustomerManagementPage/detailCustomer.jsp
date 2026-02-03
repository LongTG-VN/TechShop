<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="flex justify-center items-start pt-10 px-4 pb-20 bg-gray-50 min-h-screen">
    <div class="w-full max-w-2xl bg-white rounded-2xl shadow-2xl border border-gray-100 p-8 transition-all duration-300">

        <div class="flex flex-col items-center mb-10 border-b border-gray-50 pb-8">
            <div class="relative">
                <div class="w-24 h-24 bg-gradient-to-tr from-blue-600 to-indigo-400 text-white rounded-full flex items-center justify-center text-3xl font-bold shadow-lg mb-4">
                    ${customer.fullname.substring(0,1).toUpperCase()}
                </div>
                <span class="absolute bottom-5 right-1 w-5 h-5 ${customer.status == 'Active' ? 'bg-green-500' : 'bg-red-500'} border-4 border-white rounded-full"></span>
            </div>
            <h2 class="text-3xl font-extrabold text-gray-900 tracking-tight">${customer.fullname}</h2>
            <p class="text-blue-600 font-medium">Customer Profile</p>
        </div>

        <div class="grid grid-cols-1 md:grid-cols-2 gap-8 mb-10">
            <div class="flex items-start space-x-4">
                <div class="p-3 bg-gray-50 rounded-lg text-gray-400">
                    <svg class="w-6 h-6" fill="none" stroke="currentColor" viewBox="0 0 24 24"><path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M16 7a4 4 0 11-8 0 4 4 0 018 0zM12 14a7 7 0 00-7 7h14a7 7 0 00-7-7z"></path></svg>
                </div>
                <div>
                    <p class="text-xs font-semibold text-gray-400 uppercase tracking-wider">Username</p>
                    <p class="text-gray-900 font-medium">@${customer.userName}</p>
                </div>
            </div>

            <div class="flex items-start space-x-4">
                <div class="p-3 bg-gray-50 rounded-lg text-gray-400">
                    <svg class="w-6 h-6" fill="none" stroke="currentColor" viewBox="0 0 24 24"><path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M3 8l7.89 5.26a2 2 0 002.22 0L21 8M5 19h14a2 2 0 002-2V7a2 2 0 00-2-2H5a2 2 0 00-2 2v10a2 2 0 002 2z"></path></svg>
                </div>
                <div>
                    <p class="text-xs font-semibold text-gray-400 uppercase tracking-wider">Email Address</p>
                    <p class="text-gray-900 font-medium">${customer.email}</p>
                </div>
            </div>

            <div class="flex items-start space-x-4">
                <div class="p-3 bg-gray-50 rounded-lg text-gray-400">
                    <svg class="w-6 h-6" fill="none" stroke="currentColor" viewBox="0 0 24 24"><path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M3 5a2 2 0 012-2h3.28a1 1 0 01.948.684l1.498 4.493a1 1 0 01-.502 1.21l-2.257 1.13a11.042 11.042 0 005.516 5.516l1.13-2.257a1 1 0 011.21-.502l4.493 1.498a1 1 0 01.684.949V19a2 2 0 01-2 2h-1C9.716 21 3 14.284 3 6V5z"></path></svg>
                </div>
                <div>
                    <p class="text-xs font-semibold text-gray-400 uppercase tracking-wider">Phone Number</p>
                    <p class="text-gray-900 font-medium">${customer.phoneNumber}</p>
                </div>
            </div>

            <div class="flex items-start space-x-4">
                <div class="p-3 bg-gray-50 rounded-lg text-gray-400">
                    <svg class="w-6 h-6" fill="none" stroke="currentColor" viewBox="0 0 24 24"><path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M8 7V3m8 4V3m-9 8h10M5 21h14a2 2 0 002-2V7a2 2 0 00-2-2H5a2 2 0 00-2 2v10a2 2 0 002 2z"></path></svg>
                </div>
                <div>
                    <p class="text-xs font-semibold text-gray-400 uppercase tracking-wider">Member Since</p>
                    <p class="text-gray-900 font-medium">${customer.createdAt}</p>
                </div>
            </div>
        </div>

        <div class="mt-10 pt-8 border-t border-gray-100">
            <div class="flex items-center justify-between mb-6">
                <h3 class="text-lg font-bold text-gray-900 flex items-center">
                    <svg class="w-5 h-5 mr-2 text-blue-600" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                    <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M17.657 16.657L13.414 20.9a1.998 1.998 0 01-2.827 0l-4.244-4.243a8 8 0 1111.314 0z"></path>
                    <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M15 11a3 3 0 11-6 0 3 3 0 016 0z"></path>
                    </svg>
                    Registered Addresses
                </h3>
                <span class="text-xs font-medium text-gray-500 bg-gray-100 px-2 py-1 rounded">
                    Total: ${not empty customerAddress ? customerAddress.size() : 0}
                </span>
            </div>

            <div class="space-y-4">
                <c:choose>
                    <c:when test="${empty customerAddress}">
                        <div class="text-center py-10 bg-gray-50 rounded-xl border-2 border-dashed border-gray-200">
                            <p class="text-sm text-gray-400 italic font-medium">No addresses found for this customer.</p>
                        </div>
                    </c:when>
                    <c:otherwise>
                        <c:forEach items="${customerAddress}" var="addr">
                            <div class="group p-5 bg-white rounded-xl border border-gray-100 hover:border-blue-200 hover:shadow-lg transition-all duration-300">
                                <div class="flex items-start gap-4">
                                    <div class="w-10 h-10 bg-blue-50 text-blue-600 rounded-lg flex items-center justify-center group-hover:bg-blue-600 group-hover:text-white transition-colors duration-300">
                                        <svg class="w-5 h-5" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                                        <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M3 12l2-2m0 0l7-7 7 7M5 10v10a1 1 0 001 1h3m10-11l2 2m-2-2v10a1 1 0 01-1 1h-3m-6 0a1 1 0 001-1v-4a1 1 0 011-1h2a1 1 0 011 1v4a1 1 0 001 1m-6 0h6"></path>
                                        </svg>
                                    </div>

                                    <div class="flex-1">
                                        <div class="flex items-center justify-between mb-1">
                                            <div class="flex items-center gap-2">
                                                <span class="text-sm font-bold text-gray-800">${addr.nameReceiver}</span>
                                                <c:if test="${addr.isIsDefault()}">
                                                    <span class="text-[9px] bg-blue-600 text-white px-2 py-0.5 rounded font-bold uppercase tracking-tighter shadow-sm">Primary</span>
                                                </c:if>
                                            </div>

                                            <a href="customerservlet?action=deleteAddress&addressId=${addr.addressId}&customerID=${customer.customerID}" 
                                               onclick="return confirm('Are you sure you want to delete this address?')"
                                               class="p-2 text-gray-400 hover:text-red-600 hover:bg-red-50 rounded-full transition-all duration-200"
                                               title="Delete Address">
                                                <svg class="w-5 h-5" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                                                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M19 7l-.867 12.142A2 2 0 0116.138 21H7.862a2 2 0 01-1.995-1.858L5 7m5 4v6m4-6v6m1-10V4a1 1 0 00-1-1h-4a1 1 0 00-1 1v3M4 7h16"></path>
                                                </svg>
                                            </a>
                                        </div>

                                        <p class="text-xs text-blue-500 font-semibold mb-2">${addr.phoneReceiver}</p>
                                        <p class="text-sm text-gray-600 leading-relaxed">${addr.address}</p>
                                    </div>
                                </div>
                            </div>
                        </c:forEach>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>

        <div class="flex items-center justify-between pt-10 mt-10 border-t border-gray-50">
            <a href="customerservlet?action=all" 
               class="text-sm font-medium text-gray-500 hover:text-blue-600 transition-colors flex items-center gap-1 group">
                <svg class="w-4 h-4 transition-transform group-hover:-translate-x-1" fill="none" stroke="currentColor" viewBox="0 0 24 24"><path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M15 19l-7-7 7-7"></path></svg>
                Back to Dashboard
            </a>         
        </div>
    </div>
</div>