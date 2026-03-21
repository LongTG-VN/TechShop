<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<div class="max-w-[1200px] mx-auto px-4 py-8 font-sans text-gray-800">
    <div class="flex flex-col lg:flex-row gap-8">

        <%-- SIDEBAR: BỘ LỌC --%>
        <aside class="w-full lg:w-1/4 flex flex-col gap-6">
            <form action="productpageservlet" method="GET" id="filterForm">
                <input type="hidden" name="keyword" value="${keyword}" />

                <div class="border border-gray-200 rounded-xl p-5 bg-white shadow-sm mb-6">
                    <h3 class="font-bold text-lg mb-4 text-gray-900 border-b pb-2">Categories</h3>
                    <ul class="space-y-3 text-sm">
                        <li>
                            <label class="flex items-center gap-2 cursor-pointer hover:text-red-600 transition-colors">
                                <input type="radio" name="categoryId" value=""
                                       class="w-4 h-4 text-red-600 focus:ring-red-500" 
                                       ${empty categoryId ? 'checked' : ''} onchange="this.form.submit()">
                                <span>All Categories</span>
                            </label>
                        </li>
                        <c:forEach var="c" items="${categoryList}">
                            <li>
                                <label class="flex items-center gap-2 cursor-pointer hover:text-red-600 transition-colors">
                                    <input type="radio" name="categoryId" value="${c.categoryId}"
                                           class="w-4 h-4 text-red-600 focus:ring-red-500"
                                           ${categoryId==c.categoryId ? 'checked' : ''}
                                           onchange="this.form.submit()">
                                    <span>${c.categoryName}</span>
                                </label>
                            </li>
                        </c:forEach>
                    </ul>
                </div>

                <div class="border border-gray-200 rounded-xl p-5 bg-white shadow-sm mb-6">
                    <h3 class="font-bold text-lg mb-4 text-gray-900 border-b pb-2">Brands</h3>
                    <div class="space-y-3 text-sm">
                        <label class="flex items-center gap-2 cursor-pointer">
                            <input type="radio" name="brandId" value=""
                                   class="w-4 h-4 text-red-600 focus:ring-red-500" 
                                   ${empty brandId ? 'checked' : ''} onchange="this.form.submit()">
                            <span>All Brands</span>
                        </label>
                        <c:forEach var="b" items="${brandList}">
                            <label class="flex items-center gap-2 cursor-pointer">
                                <input type="radio" name="brandId" value="${b.brandId}"
                                       class="w-4 h-4 text-red-600 focus:ring-red-500" 
                                       ${brandId==b.brandId ? 'checked' : ''} onchange="this.form.submit()">
                                <span>${b.brandName}</span>
                            </label>
                        </c:forEach>
                    </div>
                </div>

                <div class="border border-gray-200 rounded-xl p-5 bg-white shadow-sm mb-6">
                    <h3 class="font-bold text-lg mb-4 text-gray-900 border-b pb-2">Price Range</h3>
                    <div class="space-y-3 text-sm">
                        <label class="flex items-center gap-2 cursor-pointer">
                            <input type="radio" name="priceRange" value=""
                                   class="w-4 h-4 text-red-600 focus:ring-red-500" 
                                   ${empty priceRange ? 'checked' : ''} onchange="this.form.submit()">
                            <span>All Prices</span>
                        </label>
                        <label class="flex items-center gap-2 cursor-pointer">
                            <input type="radio" name="priceRange" value="under5"
                                   class="w-4 h-4 text-red-600 focus:ring-red-500" 
                                   ${priceRange=='under5' ? 'checked' : ''} onchange="this.form.submit()">
                            <span>Under 5 Million</span>
                        </label>
                        <label class="flex items-center gap-2 cursor-pointer">
                            <input type="radio" name="priceRange" value="5to10"
                                   class="w-4 h-4 text-red-600 focus:ring-red-500" 
                                   ${priceRange=='5to10' ? 'checked' : ''} onchange="this.form.submit()">
                            <span>5 - 10 Million</span>
                        </label>
                        <label class="flex items-center gap-2 cursor-pointer">
                            <input type="radio" name="priceRange" value="15to25"
                                   class="w-4 h-4 text-red-600 focus:ring-red-500" 
                                   ${priceRange=='15to25' ? 'checked' : ''} onchange="this.form.submit()">
                            <span>15 - 25 Million</span>
                        </label>
                        <label class="flex items-center gap-2 cursor-pointer">
                            <input type="radio" name="priceRange" value="over25"
                                   class="w-4 h-4 text-red-600 focus:ring-red-500" 
                                   ${priceRange=='over25' ? 'checked' : ''} onchange="this.form.submit()">
                            <span>Over 25 Million</span>
                        </label>
                    </div>
                </div>
            </form>
        </aside>

        <%-- NỘI DUNG CHÍNH --%>
        <main class="w-full lg:w-3/4">

            <%-- Thanh sắp xếp --%>
            <div class="flex justify-between items-center mb-6 bg-white p-3 rounded-xl border border-gray-200 shadow-sm">
                <span class="text-sm font-medium">Sort by:</span>
                <select name="sortOrder" form="filterForm" onchange="this.form.submit()"
                        class="border border-gray-300 rounded-md text-sm px-3 py-1.5 focus:outline-none focus:border-red-500 focus:ring-1 focus:ring-red-500">
                    <option value="priceAsc" ${sortOrder == 'priceAsc' ? 'selected' : ''}>Price: Low to High</option>
                    <option value="priceDesc" ${sortOrder == 'priceDesc' ? 'selected' : ''}>Price: High to Low</option>
                </select>
            </div>

            <%-- Lưới sản phẩm --%>
            <div class="grid grid-cols-2 lg:grid-cols-3 gap-4 md:gap-6">
                <c:forEach var="p" items="${productList}">
                    <div class="group bg-white border border-gray-100 rounded-2xl p-4 shadow-sm hover:shadow-xl hover:-translate-y-1 transition-all duration-300 flex flex-col relative">
                        <div class="aspect-square mb-4 overflow-hidden rounded-xl bg-gray-50 flex items-center justify-center p-4">
                            <img src="${empty p.thumbnailUrl ? pageContext.request.contextPath.concat('/assets/img/product/default.png') : pageContext.request.contextPath.concat('/').concat(p.thumbnailUrl)}"
                                 alt="${p.name}"
                                 class="object-contain w-full h-full group-hover:scale-105 transition-transform duration-500">
                        </div>

                        <div class="flex flex-col flex-1">
                            <h3 class="font-bold text-gray-800 text-sm md:text-base line-clamp-2 hover:text-red-600 cursor-pointer">
                                ${p.name}
                            </h3>
                            <div class="mt-2 flex items-center gap-1">
                                <div class="flex text-yellow-400 text-sm">
                                    <c:set var="fullStars" value="${p.averageRating != null ? p.averageRating : 0}" />
                                    <c:forEach var="i" begin="1" end="5">
                                        <c:choose>
                                            <c:when test="${i <= fullStars + 0.5}">
                                                <span>★</span>
                                            </c:when>
                                            <c:otherwise>
                                                <span class="text-gray-300">★</span>
                                            </c:otherwise>
                                        </c:choose>
                                    </c:forEach>
                                </div>
                                <span class="text-xs text-gray-500">(${p.reviewCount != null ? p.reviewCount : 0})</span>
                            </div>
                            <div class="mt-auto pt-3">
                                <p class="text-red-600 font-bold text-lg">
                                    <fmt:formatNumber value="${p.minPrice != null ? p.minPrice : 0}"
                                                      type="number" groupingUsed="true" />₫
                                </p>
                            </div>
                            <a href="detailservlet?productId=${p.productId}"
                               class="w-full mt-4 text-center bg-red-50 border border-red-500 text-red-600 font-semibold py-2 rounded-lg hover:bg-red-500 hover:text-white transition-colors duration-300 text-sm block">
                                View Details
                            </a>
                        </div>
                    </div>
                </c:forEach>

                <c:if test="${empty productList}">
                    <div class="col-span-full py-12 text-center text-gray-500">
                        No products found matching your current filters.
                    </div>
                </c:if>
            </div> <%-- Kết thúc grid sản phẩm --%>

            <%-- PHÂN TRANG --%>
            <c:if test="${endP > 1}">
                <div class="flex justify-center items-center gap-2 mt-12 pb-8">
                    <%-- Nút Previous --%>
                    <c:if test="${tag > 1}">
                        <a href="productpageservlet?page=${tag - 1}&keyword=${keyword}&categoryId=${categoryId}&brandId=${brandId}&priceRange=${priceRange}&sortOrder=${sortOrder}" 
                           class="p-2 rounded-lg border border-gray-200 hover:bg-gray-100 transition-colors">
                            <svg class="w-5 h-5" fill="none" stroke="currentColor" viewBox="0 0 24 24"><path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M15 19l-7-7 7-7"/></svg>
                        </a>
                    </c:if>

                    <%-- Các số trang --%>
                    <c:forEach begin="1" end="${endP}" var="i">
                        <a href="productpageservlet?page=${i}&keyword=${keyword}&categoryId=${categoryId}&brandId=${brandId}&priceRange=${priceRange}&sortOrder=${sortOrder}" 
                           class="w-10 h-10 flex items-center justify-center rounded-lg border transition-all duration-300
                           ${tag == i ? 'bg-red-600 text-white border-red-600 font-bold shadow-md' : 'bg-white text-gray-600 border-gray-200 hover:border-red-500 hover:text-red-500'}">
                            ${i}
                        </a>
                    </c:forEach>

                    <%-- Nút Next --%>
                    <c:if test="${tag < endP}">
                        <a href="productpageservlet?page=${tag + 1}&keyword=${keyword}&categoryId=${categoryId}&brandId=${brandId}&priceRange=${priceRange}&sortOrder=${sortOrder}" 
                           class="p-2 rounded-lg border border-gray-200 hover:bg-gray-100 transition-colors">
                            <svg class="w-5 h-5" fill="none" stroke="currentColor" viewBox="0 0 24 24"><path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M9 5l7 7-7 7"/></svg>
                        </a>
                    </c:if>
                </div>
            </c:if>

        </main>
    </div>
</div>