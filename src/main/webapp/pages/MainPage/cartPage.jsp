<%@page contentType="text/html" pageEncoding="UTF-8"%>

<div class="bg-gray-50 min-h-screen pb-12">
    <div class="max-w-[1200px] mx-auto px-4 py-8 md:py-12 font-sans text-gray-800">

        <h1 class="text-2xl md:text-3xl font-extrabold text-gray-900 mb-8 tracking-tight">
            Giỏ hàng của bạn <span class="text-gray-400 font-medium text-lg ml-2" id="cartCount">(2 sản phẩm)</span>
        </h1>

        <div class="grid grid-cols-1 lg:grid-cols-12 gap-8 lg:gap-10">

            <div class="lg:col-span-8 flex flex-col gap-5" id="cartItemsContainer">

                <div class="cart-item flex flex-col sm:flex-row items-start sm:items-center gap-4 md:gap-6 bg-white p-5 rounded-3xl border border-gray-100 shadow-sm relative transition-all hover:shadow-md">
                    <button onclick="removeItem(this)" class="absolute top-4 right-4 sm:static sm:order-last text-gray-400 hover:text-red-500 transition-colors p-2 rounded-full hover:bg-red-50">
                        <svg class="w-5 h-5" fill="none" stroke="currentColor" viewBox="0 0 24 24" xmlns="http://www.w3.org/2000/svg"><path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M19 7l-.867 12.142A2 2 0 0116.138 21H7.862a2 2 0 01-1.995-1.858L5 7m5 4v6m4-6v6m1-10V4a1 1 0 00-1-1h-4a1 1 0 00-1 1v3M4 7h16"></path></svg>
                    </button>

                    <div class="w-24 h-24 md:w-32 md:h-32 flex-shrink-0 bg-gray-50 rounded-2xl border border-gray-100 p-2 flex items-center justify-center">
                        <img src="https://placehold.co/400x400/ffffff/555555?text=iPhone+15" alt="Product" class="w-full h-full object-contain mix-blend-multiply">
                    </div>

                    <div class="flex-1 w-full">
                        <h3 class="font-bold text-gray-900 text-lg lg:text-xl mb-1 line-clamp-2 pr-8 sm:pr-0 hover:text-red-600 cursor-pointer transition-colors">iPhone 15 Pro Max</h3>
                        <p class="text-sm text-gray-500 mb-3 font-medium">Phân loại: 256GB, Titan Tự Nhiên</p>
                        
                        <div class="flex flex-wrap items-center justify-between gap-4 mt-2">
                            <div class="flex flex-col">
                                <span class="font-black text-red-600 text-lg">29.490.000đ</span>
                                <span class="text-sm text-gray-400 line-through">34.990.000đ</span>
                            </div>

                            <div class="flex items-center border border-gray-200 rounded-xl h-10 bg-gray-50 p-1">
                                <button onclick="updateQty(this, -1)" class="w-8 h-full flex items-center justify-center text-gray-500 hover:bg-white hover:shadow-sm rounded-lg transition-all text-lg font-medium">-</button>
                                <input type="text" value="1" class="item-qty w-10 h-full text-center border-none bg-transparent focus:ring-0 text-gray-900 font-bold text-sm pointer-events-none" readonly>
                                <button onclick="updateQty(this, 1)" class="w-8 h-full flex items-center justify-center text-gray-500 hover:bg-white hover:shadow-sm rounded-lg transition-all text-lg font-medium">+</button>
                            </div>
                        </div>
                    </div>
                </div>

                <div class="cart-item flex flex-col sm:flex-row items-start sm:items-center gap-4 md:gap-6 bg-white p-5 rounded-3xl border border-gray-100 shadow-sm relative transition-all hover:shadow-md">
                    <button onclick="removeItem(this)" class="absolute top-4 right-4 sm:static sm:order-last text-gray-400 hover:text-red-500 transition-colors p-2 rounded-full hover:bg-red-50">
                        <svg class="w-5 h-5" fill="none" stroke="currentColor" viewBox="0 0 24 24" xmlns="http://www.w3.org/2000/svg"><path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M19 7l-.867 12.142A2 2 0 0116.138 21H7.862a2 2 0 01-1.995-1.858L5 7m5 4v6m4-6v6m1-10V4a1 1 0 00-1-1h-4a1 1 0 00-1 1v3M4 7h16"></path></svg>
                    </button>

                    <div class="w-24 h-24 md:w-32 md:h-32 flex-shrink-0 bg-gray-50 rounded-2xl border border-gray-100 p-2 flex items-center justify-center">
                        <img src="https://placehold.co/400x400/ffffff/555555?text=AirPods" alt="Product" class="w-full h-full object-contain mix-blend-multiply">
                    </div>

                    <div class="flex-1 w-full">
                        <h3 class="font-bold text-gray-900 text-lg lg:text-xl mb-1 line-clamp-2 pr-8 sm:pr-0 hover:text-red-600 cursor-pointer transition-colors">Tai nghe Bluetooth AirPods Pro 2</h3>
                        <p class="text-sm text-gray-500 mb-3 font-medium">Phân loại: Trắng, Hộp sạc MagSafe</p>
                        
                        <div class="flex flex-wrap items-center justify-between gap-4 mt-2">
                            <div class="flex flex-col">
                                <span class="font-black text-red-600 text-lg">5.990.000đ</span>
                            </div>

                            <div class="flex items-center border border-gray-200 rounded-xl h-10 bg-gray-50 p-1">
                                <button onclick="updateQty(this, -1)" class="w-8 h-full flex items-center justify-center text-gray-500 hover:bg-white hover:shadow-sm rounded-lg transition-all text-lg font-medium">-</button>
                                <input type="text" value="2" class="item-qty w-10 h-full text-center border-none bg-transparent focus:ring-0 text-gray-900 font-bold text-sm pointer-events-none" readonly>
                                <button onclick="updateQty(this, 1)" class="w-8 h-full flex items-center justify-center text-gray-500 hover:bg-white hover:shadow-sm rounded-lg transition-all text-lg font-medium">+</button>
                            </div>
                        </div>
                    </div>
                </div>

                <div class="mt-4">
                    <a href="${pageContext.request.contextPath}/productpageservlet" class="inline-flex items-center gap-2 text-blue-600 font-medium hover:text-blue-800 transition-colors">
                        <svg class="w-4 h-4" fill="none" stroke="currentColor" viewBox="0 0 24 24" xmlns="http://www.w3.org/2000/svg"><path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M10 19l-7-7m0 0l7-7m-7 7h18"></path></svg>
                        Tiếp tục mua sắm
                    </a>
                </div>

            </div>

            <div class="lg:col-span-4">
                <div class="bg-white p-6 md:p-8 rounded-3xl border border-gray-100 shadow-sm sticky top-6">
                    <h2 class="text-xl font-bold text-gray-900 mb-6 border-b border-gray-100 pb-4">Tóm tắt đơn hàng</h2>
                    
                    <div class="mb-6 flex gap-2">
                        <input type="text" placeholder="Nhập mã giảm giá..." class="flex-1 border border-gray-200 rounded-xl px-4 py-3 text-sm focus:outline-none focus:border-red-500 focus:ring-1 focus:ring-red-500 bg-gray-50">
                        <button class="bg-gray-900 text-white px-5 py-3 rounded-xl text-sm font-bold hover:bg-gray-800 transition-colors">Áp dụng</button>
                    </div>

                    <div class="space-y-4 text-sm text-gray-600 mb-6 border-b border-gray-100 pb-6">
                        <div class="flex justify-between items-center">
                            <span>Tạm tính:</span>
                            <span class="font-semibold text-gray-900 text-base">41.470.000đ</span>
                        </div>
                        <div class="flex justify-between items-center text-green-600">
                            <span>Khuyến mãi:</span>
                            <span class="font-semibold">-0đ</span>
                        </div>
                        <div class="flex justify-between items-center">
                            <span>Phí vận chuyển:</span>
                            <span class="font-semibold text-gray-900 text-base">Miễn phí</span>
                        </div>
                    </div>

                    <div class="flex justify-between items-end mb-8">
                        <span class="font-bold text-gray-900 text-lg">Tổng cộng:</span>
                        <div class="text-right">
                            <span class="block font-black text-red-600 text-3xl tracking-tight">41.470.000đ</span>
                            <span class="text-xs text-gray-400 font-medium">(Đã bao gồm VAT nếu có)</span>
                        </div>
                    </div>

                    <a href="orderpageservlet" class="w-full flex justify-center items-center gap-2 bg-red-600 text-white h-14 rounded-xl font-bold text-lg hover:bg-red-700 shadow-md hover:shadow-lg transition-all">
                        Tiến hành thanh toán
                        <svg class="w-5 h-5" fill="none" stroke="currentColor" viewBox="0 0 24 24" xmlns="http://www.w3.org/2000/svg"><path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M14 5l7 7m0 0l-7 7m7-7H3"></path></svg>
                    </a>
                </div>
            </div>

        </div>
    </div>
</div>

<script>
    // Hàm Tăng/Giảm số lượng
    function updateQty(btnElement, change) {
        // Tìm thẻ input nằm cùng hàng với nút được bấm
        let container = btnElement.parentElement;
        let inputField = container.querySelector('.item-qty');
        
        let currentVal = parseInt(inputField.value);
        let newVal = currentVal + change;
        
        // Không cho phép số lượng nhỏ hơn 1
        if (newVal >= 1) {
            inputField.value = newVal;
            // *Tại đây khi tích hợp thực tế: bạn sẽ gọi Ajax/Fetch API gửi số lượng mới lên Servlet*
        }
    }

    // Hàm Xóa sản phẩm khỏi giỏ
    function removeItem(btnElement) {
        // Hỏi xác nhận trước khi xóa
        if(confirm("Bạn có chắc chắn muốn bỏ sản phẩm này khỏi giỏ hàng?")) {
            // Tìm thẻ <div> chứa toàn bộ thông tin sản phẩm đó (có class 'cart-item')
            let itemCard = btnElement.closest('.cart-item');
            
            // Tạo hiệu ứng mờ dần rồi biến mất cho đẹp
            itemCard.style.transition = "opacity 0.3s ease";
            itemCard.style.opacity = "0";
            
            setTimeout(() => {
                itemCard.remove();
                
                // Cập nhật lại số lượng trên tiêu đề
                updateCartCount();
                
                // *Tại đây khi tích hợp thực tế: bạn sẽ gọi Ajax/Fetch API báo cho Servlet xóa Session/DB*
            }, 300);
        }
    }

    // Cập nhật con số đếm (2 sản phẩm)
    function updateCartCount() {
        let itemsCount = document.querySelectorAll('.cart-item').length;
        let countText = document.getElementById('cartCount');
        
        if(itemsCount > 0) {
            countText.innerText = "(" + itemsCount + " sản phẩm)";
        } else {
            countText.innerText = "(Trống)";
            // Hiển thị giao diện "Giỏ hàng trống" nếu thích
            document.getElementById('cartItemsContainer').innerHTML = `
                <div class="text-center py-12 bg-white rounded-3xl border border-gray-100">
                    <p class="text-gray-500 mb-4">Giỏ hàng của bạn đang trống.</p>
                    <a href="${pageContext.request.contextPath}/productpageservlet" class="text-red-600 font-bold hover:underline">Về trang sản phẩm</a>
                </div>
            `;
        }
    }
</script>