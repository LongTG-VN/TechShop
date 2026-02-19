<%@page contentType="text/html" pageEncoding="UTF-8"%>


<div class="max-w-[1200px] mx-auto px-4 py-8 font-sans text-gray-800">
    <div class="flex flex-col lg:flex-row gap-8">

        <aside class="w-full lg:w-1/4 flex flex-col gap-6">
            
            <div class="border border-gray-200 rounded-xl p-5 bg-white shadow-sm">
                <h3 class="font-bold text-lg mb-4 text-gray-900 border-b pb-2">Danh mục</h3>
                <ul class="space-y-3 text-sm">
                    <li>
                        <label class="flex items-center gap-2 cursor-pointer hover:text-red-600 transition-colors">
                            <input type="radio" name="category" class="w-4 h-4 text-red-600 focus:ring-red-500" checked>
                            <span>Tất cả</span>
                        </label>
                    </li>
                    <li>
                        <label class="flex items-center gap-2 cursor-pointer hover:text-red-600 transition-colors">
                            <input type="radio" name="category" class="w-4 h-4 text-red-600 focus:ring-red-500">
                            <span>Điện thoại</span>
                        </label>
                    </li>
                    <li>
                        <label class="flex items-center gap-2 cursor-pointer hover:text-red-600 transition-colors">
                            <input type="radio" name="category" class="w-4 h-4 text-red-600 focus:ring-red-500">
                            <span>Laptop</span>
                        </label>
                    </li>
                    <li>
                        <label class="flex items-center gap-2 cursor-pointer hover:text-red-600 transition-colors">
                            <input type="radio" name="category" class="w-4 h-4 text-red-600 focus:ring-red-500">
                            <span>Máy tính bảng</span>
                        </label>
                    </li>
                    <li>
                        <label class="flex items-center gap-2 cursor-pointer hover:text-red-600 transition-colors">
                            <input type="radio" name="category" class="w-4 h-4 text-red-600 focus:ring-red-500">
                            <span>Phụ kiện khác</span>
                        </label>
                    </li>
                </ul>
            </div>

            <div class="border border-gray-200 rounded-xl p-5 bg-white shadow-sm">
                <h3 class="font-bold text-lg mb-4 text-gray-900 border-b pb-2">Hãng sản xuất</h3>
                <div class="space-y-3 text-sm">
                    <label class="flex items-center gap-2 cursor-pointer">
                        <input type="checkbox" class="w-4 h-4 rounded text-red-600 focus:ring-red-500">
                        <span>Apple</span>
                    </label>
                    <label class="flex items-center gap-2 cursor-pointer">
                        <input type="checkbox" class="w-4 h-4 rounded text-red-600 focus:ring-red-500">
                        <span>Samsung</span>
                    </label>
                    <label class="flex items-center gap-2 cursor-pointer">
                        <input type="checkbox" class="w-4 h-4 rounded text-red-600 focus:ring-red-500">
                        <span>Xiaomi</span>
                    </label>
                    <label class="flex items-center gap-2 cursor-pointer">
                        <input type="checkbox" class="w-4 h-4 rounded text-red-600 focus:ring-red-500">
                        <span>Dell / Asus</span>
                    </label>
                </div>
            </div>

            <div class="border border-gray-200 rounded-xl p-5 bg-white shadow-sm">
                <h3 class="font-bold text-lg mb-4 text-gray-900 border-b pb-2">Mức giá</h3>
                <div class="space-y-3 text-sm">
                    <label class="flex items-center gap-2 cursor-pointer">
                        <input type="radio" name="price" class="w-4 h-4 text-red-600 focus:ring-red-500">
                        <span>Dưới 5 triệu</span>
                    </label>
                    <label class="flex items-center gap-2 cursor-pointer">
                        <input type="radio" name="price" class="w-4 h-4 text-red-600 focus:ring-red-500">
                        <span>Từ 5 - 15 triệu</span>
                    </label>
                    <label class="flex items-center gap-2 cursor-pointer">
                        <input type="radio" name="price" class="w-4 h-4 text-red-600 focus:ring-red-500">
                        <span>Từ 15 - 25 triệu</span>
                    </label>
                    <label class="flex items-center gap-2 cursor-pointer">
                        <input type="radio" name="price" class="w-4 h-4 text-red-600 focus:ring-red-500">
                        <span>Trên 25 triệu</span>
                    </label>
                </div>
            </div>

        </aside>

        <main class="w-full lg:w-3/4">
            
            <div class="flex justify-between items-center mb-6 bg-white p-3 rounded-xl border border-gray-200 shadow-sm">
                <span class="text-sm font-medium">Sắp xếp theo:</span>
                <select class="border border-gray-300 rounded-md text-sm px-3 py-1.5 focus:outline-none focus:border-red-500 focus:ring-1 focus:ring-red-500">
                    <option>Giá: Thấp đến Cao</option>
                    <option>Giá: Cao đến Thấp</option>
                </select>
            </div>

            <div class="grid grid-cols-2 lg:grid-cols-3 gap-4 md:gap-6">

                <div class="group bg-white border border-gray-100 rounded-2xl p-4 shadow-sm hover:shadow-xl hover:-translate-y-1 transition-all duration-300 flex flex-col relative">
                    <span class="absolute top-4 left-4 bg-red-500 text-white text-xs font-bold px-2 py-1 rounded-md z-10">-15%</span>
                    
                    <div class="aspect-square mb-4 overflow-hidden rounded-xl bg-gray-50 flex items-center justify-center p-4">
                        <img src="https://placehold.co/400x400/ffffff/555555?text=Ảnh+Điện+Thoại" alt="Điện thoại Smartphone" class="object-contain w-full h-full group-hover:scale-105 transition-transform duration-500">
                    </div>
                    
                    <div class="flex flex-col flex-1">
                        <h3 class="font-semibold text-gray-800 text-sm md:text-base line-clamp-2 hover:text-red-600 cursor-pointer">iPhone 15 Pro Max 256GB</h3>
                        <div class="mt-2 flex items-center gap-1">
                            <span class="text-yellow-400 text-sm">★★★★★</span>
                            <span class="text-xs text-gray-500">(128)</span>
                        </div>
                        <div class="mt-auto pt-3">
                            <p class="text-red-600 font-bold text-lg">29.490.000đ</p>
                            <p class="text-gray-400 text-sm line-through">34.990.000đ</p>
                        </div>
                        <a href="detailservlet" class="w-full mt-4 text-center bg-red-50 border border-red-500 text-red-600 font-semibold py-2 rounded-lg hover:bg-red-500 hover:text-white transition-colors duration-300 text-sm">
                            Xem chi tiết
                        </a>
                    </div>
                </div>

                <div class="group bg-white border border-gray-100 rounded-2xl p-4 shadow-sm hover:shadow-xl hover:-translate-y-1 transition-all duration-300 flex flex-col relative">
                    <div class="aspect-square mb-4 overflow-hidden rounded-xl bg-gray-50 flex items-center justify-center p-4">
                        <img src="https://placehold.co/400x400/ffffff/555555?text=Ảnh+Laptop" alt="Laptop" class="object-contain w-full h-full group-hover:scale-105 transition-transform duration-500">
                    </div>
                    
                    <div class="flex flex-col flex-1">
                        <h3 class="font-semibold text-gray-800 text-sm md:text-base line-clamp-2 hover:text-red-600 cursor-pointer">MacBook Air 13 inch M2 2022</h3>
                        <div class="mt-2 flex items-center gap-1">
                            <span class="text-yellow-400 text-sm">★★★★☆</span>
                            <span class="text-xs text-gray-500">(56)</span>
                        </div>
                        <div class="mt-auto pt-3">
                            <p class="text-red-600 font-bold text-lg">23.990.000đ</p>
                        </div>
                        <button class="w-full mt-4 bg-red-50 border border-red-500 text-red-600 font-semibold py-2 rounded-lg hover:bg-red-500 hover:text-white transition-colors duration-300 text-sm">
                           Xem chi tiết
                        </button>
                    </div>
                </div>

                <div class="group bg-white border border-gray-100 rounded-2xl p-4 shadow-sm hover:shadow-xl hover:-translate-y-1 transition-all duration-300 flex flex-col relative">
                    <span class="absolute top-4 left-4 bg-red-500 text-white text-xs font-bold px-2 py-1 rounded-md z-10">-10%</span>
                    <div class="aspect-square mb-4 overflow-hidden rounded-xl bg-gray-50 flex items-center justify-center p-4">
                        <img src="https://placehold.co/400x400/ffffff/555555?text=Ảnh+Tablet" alt="Máy tính bảng" class="object-contain w-full h-full group-hover:scale-105 transition-transform duration-500">
                    </div>
                    
                    <div class="flex flex-col flex-1">
                        <h3 class="font-semibold text-gray-800 text-sm md:text-base line-clamp-2 hover:text-red-600 cursor-pointer">iPad Pro 11 inch M2 128GB WiFi</h3>
                        <div class="mt-2 flex items-center gap-1">
                            <span class="text-yellow-400 text-sm">★★★★★</span>
                            <span class="text-xs text-gray-500">(89)</span>
                        </div>
                        <div class="mt-auto pt-3">
                            <p class="text-red-600 font-bold text-lg">20.490.000đ</p>
                            <p class="text-gray-400 text-sm line-through">22.990.000đ</p>
                        </div>
                        <button class="w-full mt-4 bg-red-50 border border-red-500 text-red-600 font-semibold py-2 rounded-lg hover:bg-red-500 hover:text-white transition-colors duration-300 text-sm">
                            Xem chi tiết
                        </button>
                    </div>
                </div>

                <div class="group bg-white border border-gray-100 rounded-2xl p-4 shadow-sm hover:shadow-xl hover:-translate-y-1 transition-all duration-300 flex flex-col relative">
                    <div class="aspect-square mb-4 overflow-hidden rounded-xl bg-gray-50 flex items-center justify-center p-4">
                        <img src="https://placehold.co/400x400/ffffff/555555?text=Ảnh+Phụ+Kiện" alt="Tai nghe" class="object-contain w-full h-full group-hover:scale-105 transition-transform duration-500">
                    </div>
                    
                    <div class="flex flex-col flex-1">
                        <h3 class="font-semibold text-gray-800 text-sm md:text-base line-clamp-2 hover:text-red-600 cursor-pointer">Tai nghe Bluetooth AirPods Pro 2</h3>
                        <div class="mt-2 flex items-center gap-1">
                            <span class="text-yellow-400 text-sm">★★★★★</span>
                            <span class="text-xs text-gray-500">(210)</span>
                        </div>
                        <div class="mt-auto pt-3">
                            <p class="text-red-600 font-bold text-lg">5.990.000đ</p>
                        </div>
                        <button class="w-full mt-4 bg-red-50 border border-red-500 text-red-600 font-semibold py-2 rounded-lg hover:bg-red-500 hover:text-white transition-colors duration-300 text-sm">
                           Xem chi tiết
                        </button>
                    </div>
                </div>

                </div>

            <div class="mt-10 flex justify-center items-center gap-2">
                <button class="w-10 h-10 rounded-lg border border-gray-300 flex items-center justify-center hover:bg-gray-50 text-gray-500">&laquo;</button>
                <button class="w-10 h-10 rounded-lg bg-red-600 text-white font-bold shadow-md">1</button>
                <button class="w-10 h-10 rounded-lg border border-gray-300 hover:border-red-500 hover:text-red-600 font-medium">2</button>
                <button class="w-10 h-10 rounded-lg border border-gray-300 hover:border-red-500 hover:text-red-600 font-medium">3</button>
                <span class="text-gray-500">...</span>
                <button class="w-10 h-10 rounded-lg border border-gray-300 flex items-center justify-center hover:bg-gray-50 text-gray-500">&raquo;</button>
            </div>

        </main>
    </div>
</div>