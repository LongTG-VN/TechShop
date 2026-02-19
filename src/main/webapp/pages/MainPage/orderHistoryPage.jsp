<%@page contentType="text/html" pageEncoding="UTF-8"%>

<div class="bg-gray-50 min-h-screen pb-12 font-sans text-gray-800">
    <div class="max-w-[1000px] mx-auto px-4 py-8 md:py-12">

        <div class="mb-8">
            <h1 class="text-2xl md:text-3xl font-extrabold text-gray-900 tracking-tight mb-2">Lịch sử đơn hàng</h1>
            <p class="text-sm text-gray-500">Quản lý và theo dõi trạng thái các đơn hàng của bạn.</p>
        </div>

        <div class="bg-white rounded-2xl p-2 mb-6 flex overflow-x-auto shadow-sm border border-gray-100 snap-x hide-scrollbar">
            <button class="snap-center flex-shrink-0 px-6 py-2.5 rounded-xl font-bold text-red-600 bg-red-50 text-sm transition-colors">
                Tất cả đơn
            </button>
            <button class="snap-center flex-shrink-0 px-6 py-2.5 rounded-xl font-semibold text-gray-600 hover:bg-gray-50 hover:text-gray-900 text-sm transition-colors">
                Chờ xác nhận
            </button>
            <button class="snap-center flex-shrink-0 px-6 py-2.5 rounded-xl font-semibold text-gray-600 hover:bg-gray-50 hover:text-gray-900 text-sm transition-colors">
                Đang vận chuyển
            </button>
            <button class="snap-center flex-shrink-0 px-6 py-2.5 rounded-xl font-semibold text-gray-600 hover:bg-gray-50 hover:text-gray-900 text-sm transition-colors">
                Đã hoàn thành
            </button>
            <button class="snap-center flex-shrink-0 px-6 py-2.5 rounded-xl font-semibold text-gray-600 hover:bg-gray-50 hover:text-gray-900 text-sm transition-colors">
                Đã hủy
            </button>
        </div>

        <div class="space-y-6">

            <div class="bg-white border border-gray-100 rounded-3xl p-6 shadow-sm hover:shadow-md transition-shadow">
                <div class="flex flex-wrap items-center justify-between border-b border-gray-100 pb-4 mb-4 gap-4">
                    <div class="flex items-center gap-3">
                        <span class="font-bold text-gray-900">Mã đơn: #ORD-2026VN</span>
                        <span class="w-1.5 h-1.5 rounded-full bg-gray-300"></span>
                        <span class="text-sm text-gray-500">19/02/2026</span>
                    </div>
                    <span class="px-3 py-1 bg-yellow-100 text-yellow-700 text-xs font-bold rounded-lg flex items-center gap-1.5 uppercase tracking-wide">
                        <span class="w-2 h-2 rounded-full bg-yellow-500 animate-pulse"></span>
                        Đang xử lý
                    </span>
                </div>

                <div class="space-y-4 mb-6">
                    <div class="flex gap-4 items-center">
                        <div class="w-20 h-20 flex-shrink-0 bg-gray-50 rounded-xl border border-gray-100 p-2">
                            <img src="https://placehold.co/150x150/ffffff/555555?text=iPhone" alt="Product" class="w-full h-full object-contain mix-blend-multiply">
                        </div>
                        <div class="flex-1">
                            <h3 class="font-bold text-gray-900 text-base line-clamp-1 hover:text-red-600 cursor-pointer transition-colors">iPhone 15 Pro Max 256GB</h3>
                            <p class="text-sm text-gray-500 mt-1">Phân loại: Titan Tự Nhiên</p>
                            <p class="text-sm font-semibold text-gray-900 mt-1">x1</p>
                        </div>
                        <div class="text-right">
                            <p class="font-bold text-red-600">29.490.000đ</p>
                            <p class="text-xs text-gray-400 line-through">34.990.000đ</p>
                        </div>
                    </div>
                </div>

                <div class="flex flex-wrap items-center justify-between pt-4 border-t border-gray-100 gap-4">
                    <div class="flex items-baseline gap-2">
                        <span class="text-gray-600 text-sm font-medium">Thành tiền:</span>
                        <span class="text-2xl font-black text-red-600">29.490.000đ</span>
                    </div>
                    <div class="flex gap-3">
                        <button class="px-5 py-2.5 rounded-xl border border-gray-200 text-gray-700 font-bold text-sm hover:bg-gray-50 transition-colors">
                            Hủy đơn hàng
                        </button>
                        <button class="px-5 py-2.5 rounded-xl bg-red-50 text-red-600 font-bold text-sm hover:bg-red-100 transition-colors">
                            Xem chi tiết
                        </button>
                    </div>
                </div>
            </div>

            <div class="bg-white border border-gray-100 rounded-3xl p-6 shadow-sm hover:shadow-md transition-shadow">
                <div class="flex flex-wrap items-center justify-between border-b border-gray-100 pb-4 mb-4 gap-4">
                    <div class="flex items-center gap-3">
                        <span class="font-bold text-gray-900">Mã đơn: #ORD-9988AB</span>
                        <span class="w-1.5 h-1.5 rounded-full bg-gray-300"></span>
                        <span class="text-sm text-gray-500">10/02/2026</span>
                    </div>
                    <span class="px-3 py-1 bg-green-100 text-green-700 text-xs font-bold rounded-lg flex items-center gap-1.5 uppercase tracking-wide">
                        <svg class="w-3.5 h-3.5" fill="none" stroke="currentColor" viewBox="0 0 24 24"><path stroke-linecap="round" stroke-linejoin="round" stroke-width="3" d="M5 13l4 4L19 7"></path></svg>
                        Giao hàng thành công
                    </span>
                </div>

                <div class="space-y-4 mb-6">
                    <div class="flex gap-4 items-center">
                        <div class="w-20 h-20 flex-shrink-0 bg-gray-50 rounded-xl border border-gray-100 p-2">
                            <img src="https://placehold.co/150x150/ffffff/555555?text=AirPods" alt="Product" class="w-full h-full object-contain mix-blend-multiply">
                        </div>
                        <div class="flex-1">
                            <h3 class="font-bold text-gray-900 text-base line-clamp-1 hover:text-red-600 cursor-pointer transition-colors">Tai nghe Bluetooth AirPods Pro 2</h3>
                            <p class="text-sm text-gray-500 mt-1">Phân loại: Trắng</p>
                            <p class="text-sm font-semibold text-gray-900 mt-1">x2</p>
                        </div>
                        <div class="text-right">
                            <p class="font-bold text-red-600">5.990.000đ</p>
                        </div>
                    </div>
                </div>

                <div class="flex flex-wrap items-center justify-between pt-4 border-t border-gray-100 gap-4">
                    <div class="flex items-baseline gap-2">
                        <span class="text-gray-600 text-sm font-medium">Thành tiền:</span>
                        <span class="text-2xl font-black text-red-600">11.980.000đ</span>
                    </div>
                    <div class="flex gap-3">
                        <button class="px-5 py-2.5 rounded-xl border border-gray-200 text-gray-700 font-bold text-sm hover:bg-gray-50 transition-colors">
                            Đánh giá sản phẩm
                        </button>
                        <button class="px-5 py-2.5 rounded-xl bg-red-600 text-white font-bold text-sm hover:bg-red-700 shadow-sm transition-colors">
                            Mua lại
                        </button>
                    </div>
                </div>
            </div>

            <div class="bg-white border border-gray-100 rounded-3xl p-6 shadow-sm hover:shadow-md transition-shadow opacity-70">
                <div class="flex flex-wrap items-center justify-between border-b border-gray-100 pb-4 mb-4 gap-4">
                    <div class="flex items-center gap-3">
                        <span class="font-bold text-gray-900">Mã đơn: #ORD-5544CD</span>
                        <span class="w-1.5 h-1.5 rounded-full bg-gray-300"></span>
                        <span class="text-sm text-gray-500">01/02/2026</span>
                    </div>
                    <span class="px-3 py-1 bg-gray-200 text-gray-600 text-xs font-bold rounded-lg flex items-center gap-1.5 uppercase tracking-wide">
                        Đã hủy
                    </span>
                </div>

                <div class="space-y-4 mb-6">
                    <div class="flex gap-4 items-center">
                        <div class="w-20 h-20 flex-shrink-0 bg-gray-50 rounded-xl border border-gray-100 p-2">
                            <img src="https://placehold.co/150x150/ffffff/555555?text=Ốp+Lưng" alt="Product" class="w-full h-full object-contain mix-blend-multiply grayscale">
                        </div>
                        <div class="flex-1">
                            <h3 class="font-bold text-gray-900 text-base line-clamp-1">Ốp lưng trong suốt iPhone 15 Pro Max</h3>
                            <p class="text-sm text-gray-500 mt-1">Phân loại: Trong suốt (Magsafe)</p>
                            <p class="text-sm font-semibold text-gray-900 mt-1">x1</p>
                        </div>
                        <div class="text-right">
                            <p class="font-bold text-gray-600">350.000đ</p>
                        </div>
                    </div>
                </div>

                <div class="flex flex-wrap items-center justify-between pt-4 border-t border-gray-100 gap-4">
                    <div class="flex items-baseline gap-2">
                        <span class="text-gray-600 text-sm font-medium">Thành tiền:</span>
                        <span class="text-2xl font-black text-gray-600">350.000đ</span>
                    </div>
                    <div class="flex gap-3">
                        <button class="px-5 py-2.5 rounded-xl border border-gray-200 text-gray-700 font-bold text-sm hover:bg-gray-50 transition-colors">
                            Xem chi tiết
                        </button>
                        <button class="px-5 py-2.5 rounded-xl bg-red-50 text-red-600 font-bold text-sm hover:bg-red-100 transition-colors">
                            Mua lại
                        </button>
                    </div>
                </div>
            </div>

        </div>

        <div class="mt-10 flex justify-center items-center gap-2">
            <button class="w-10 h-10 rounded-xl border border-gray-200 flex items-center justify-center hover:bg-gray-50 text-gray-500 transition-colors">&laquo;</button>
            <button class="w-10 h-10 rounded-xl bg-red-600 text-white font-bold shadow-sm">1</button>
            <button class="w-10 h-10 rounded-xl border border-gray-200 hover:border-red-500 hover:text-red-600 font-medium transition-colors">2</button>
            <button class="w-10 h-10 rounded-xl border border-gray-200 flex items-center justify-center hover:bg-gray-50 text-gray-500 transition-colors">&raquo;</button>
        </div>

    </div>
</div>

<style>
    .hide-scrollbar::-webkit-scrollbar {
        display: none;
    }
    .hide-scrollbar {
        -ms-overflow-style: none;
        scrollbar-width: none;
    }
</style>