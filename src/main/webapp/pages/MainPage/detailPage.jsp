<%@page contentType="text/html" pageEncoding="UTF-8"%>

<div class="max-w-[1200px] mx-auto px-4 py-10 font-sans text-gray-800 bg-white">

    <div class="grid grid-cols-1 lg:grid-cols-12 gap-10 lg:gap-14">

        <div class="lg:col-span-5 flex flex-col gap-4">

            <a id="mainImageLink" href="https://placehold.co/1200x1200/ffffff/555555?text=Ảnh+1+HD" data-fancybox="gallery" class="cursor-zoom-in border border-gray-100 rounded-3xl overflow-hidden p-6 flex items-center justify-center bg-gray-50 aspect-square shadow-sm transition-shadow hover:shadow-md">
                <img id="mainImage" src="https://placehold.co/600x600/ffffff/555555?text=Ảnh+1" alt="Product Image" class="w-full h-full object-contain mix-blend-multiply transition-transform duration-300 hover:scale-105">
            </a>

            <div class="flex gap-3 justify-center overflow-x-auto pb-2 snap-x">
                <button onclick="changeImage(event, 'https://placehold.co/600x600/ffffff/555555?text=Ảnh+1', 'https://placehold.co/1200x1200/ffffff/555555?text=Ảnh+1+HD')" class="thumb-btn snap-center flex-shrink-0 w-20 h-20 border-2 border-red-500 rounded-2xl p-1 bg-white shadow-sm">
                    <img src="https://placehold.co/150x150/ffffff/555555?text=Ảnh+1" class="w-full h-full object-contain rounded-xl">
                </button>
                <button onclick="changeImage(event, 'https://placehold.co/600x600/ffffff/555555?text=Ảnh+2', 'https://placehold.co/1200x1200/ffffff/555555?text=Ảnh+2+HD')" class="thumb-btn snap-center flex-shrink-0 w-20 h-20 border-2 border-gray-100 hover:border-red-300 transition-all rounded-2xl p-1 bg-white hover:shadow-sm">
                    <img src="https://placehold.co/150x150/ffffff/555555?text=Ảnh+2" class="w-full h-full object-contain rounded-xl">
                </button>
                <button onclick="changeImage(event, 'https://placehold.co/600x600/ffffff/555555?text=Ảnh+3', 'https://placehold.co/1200x1200/ffffff/555555?text=Ảnh+3+HD')" class="thumb-btn snap-center flex-shrink-0 w-20 h-20 border-2 border-gray-100 hover:border-red-300 transition-all rounded-2xl p-1 bg-white hover:shadow-sm">
                    <img src="https://placehold.co/150x150/ffffff/555555?text=Ảnh+3" class="w-full h-full object-contain rounded-xl">
                </button>
                <button onclick="changeImage(event, 'https://placehold.co/600x600/ffffff/555555?text=Ảnh+4', 'https://placehold.co/1200x1200/ffffff/555555?text=Ảnh+4+HD')" class="thumb-btn snap-center flex-shrink-0 w-20 h-20 border-2 border-gray-100 hover:border-red-300 transition-all rounded-2xl p-1 bg-white hover:shadow-sm">
                    <img src="https://placehold.co/150x150/ffffff/555555?text=Ảnh+4" class="w-full h-full object-contain rounded-xl">
                </button>
            </div>
        </div>

        <div class="lg:col-span-7 flex flex-col">
            <h1 class="text-3xl lg:text-4xl font-extrabold text-gray-900 mb-3 tracking-tight">iPhone 15 Pro Max 256GB</h1>
            <div class="flex items-center gap-3 mb-6 text-sm">
                <div class="flex items-center text-yellow-400 text-base">
                    ★★★★★ <span class="text-gray-500 ml-2 text-sm font-medium">(128 đánh giá)</span>
                </div>
                <span class="w-1.5 h-1.5 rounded-full bg-gray-300"></span>
                <span class="text-green-600 font-semibold flex items-center gap-1">
                    <svg class="w-4 h-4" fill="currentColor" viewBox="0 0 20 20" xmlns="http://www.w3.org/2000/svg"><path fill-rule="evenodd" d="M10 18a8 8 0 100-16 8 8 0 000 16zm3.707-9.293a1 1 0 00-1.414-1.414L9 10.586 7.707 9.293a1 1 0 00-1.414 1.414l2 2a1 1 0 001.414 0l4-4z" clip-rule="evenodd"></path></svg>
                    Tình trạng: Còn hàng
                </span>
            </div>

            <div class="bg-gradient-to-r from-red-50 to-white border border-red-100 p-5 rounded-2xl mb-8 flex items-baseline gap-4">
                <span class="text-4xl font-black text-red-600 tracking-tight">29.490.000đ</span>
                <span class="text-xl text-gray-400 line-through font-medium">34.990.000đ</span>
                <span class="bg-red-600 text-white text-xs font-bold px-2.5 py-1 rounded-md uppercase tracking-wider">-15%</span>
            </div>

            <div class="mb-6">
                <h3 class="font-bold text-gray-900 mb-3 uppercase text-xs tracking-wider">Dung lượng:</h3>
                <div class="flex flex-wrap gap-3">
                    <button onclick="selectCapacity(event)" class="capacity-btn px-5 py-2.5 border-2 border-red-600 text-red-600 font-bold rounded-xl bg-red-50 transition-all">256GB</button>
                    <button onclick="selectCapacity(event)" class="capacity-btn px-5 py-2.5 border border-gray-200 text-gray-600 font-semibold rounded-xl hover:border-gray-400 transition-all">512GB</button>
                    <button onclick="selectCapacity(event)" class="capacity-btn px-5 py-2.5 border border-gray-200 text-gray-600 font-semibold rounded-xl hover:border-gray-400 transition-all">1TB</button>
                </div>
            </div>

            <div class="mb-8">
                <h3 class="font-bold text-gray-900 mb-3 uppercase text-xs tracking-wider flex items-center gap-2">
                    Màu sắc: <span id="colorNameLabel" class="text-gray-500 font-medium normal-case">Titan Tự Nhiên</span>
                </h3>
                <div class="flex gap-3">
                    <button onclick="selectColor(event, 'Titan Tự Nhiên')" class="color-btn w-11 h-11 rounded-full border-2 border-red-600 p-1 focus:outline-none transition-transform hover:scale-110">
                        <span class="block w-full h-full rounded-full shadow-inner" style="background-color: #C0C0C0;"></span>
                    </button>
                    <button onclick="selectColor(event, 'Titan Đen')" class="color-btn w-11 h-11 rounded-full border border-gray-200 p-1 focus:outline-none hover:border-gray-400 transition-transform hover:scale-110">
                        <span class="block w-full h-full rounded-full shadow-inner" style="background-color: #38414D;"></span>
                    </button>
                    <button onclick="selectColor(event, 'Titan Trắng')" class="color-btn w-11 h-11 rounded-full border border-gray-200 p-1 focus:outline-none hover:border-gray-400 transition-transform hover:scale-110">
                        <span class="block w-full h-full rounded-full shadow-inner border border-gray-200" style="background-color: #F3F2F8;"></span>
                    </button>
                </div>
            </div>

            <div class="flex flex-wrap sm:flex-nowrap gap-4 mt-auto">
                <div class="flex items-center border border-gray-200 rounded-xl h-14 bg-gray-50 p-1">
                    <button onclick="updateQuantity(-1)" class="w-10 h-full flex items-center justify-center text-gray-500 hover:bg-white hover:shadow-sm rounded-lg transition-all text-xl">-</button>
                    <input type="text" id="qtyInput" value="1" class="w-12 h-full text-center border-none bg-transparent focus:ring-0 text-gray-900 font-bold pointer-events-none" readonly>
                        <button onclick="updateQuantity(1)" class="w-10 h-full flex items-center justify-center text-gray-500 hover:bg-white hover:shadow-sm rounded-lg transition-all text-xl">+</button>
                </div>
                <button class="flex-1 h-14 border-2 border-red-600 text-red-600 font-bold text-lg rounded-xl hover:bg-red-600 hover:text-white transition-colors duration-300 flex items-center justify-center gap-2 shadow-sm">
                    🛒 Thêm vào giỏ
                </button>
            </div>
        </div>
    </div>

    <div class="mt-16">
        <h2 class="text-2xl font-bold text-gray-900 mb-6 flex items-center gap-2">
            Thông số kỹ thuật
        </h2>
        <div class="border border-gray-200 rounded-3xl overflow-hidden bg-white shadow-sm max-w-3xl">
            <ul class="text-sm md:text-base text-gray-700">
                <li class="px-6 py-4 flex flex-col sm:flex-row sm:items-center border-b border-gray-100">
                    <span class="font-semibold text-gray-500 w-full sm:w-1/3 mb-1 sm:mb-0">Màn hình:</span>
                    <span class="w-full sm:w-2/3 text-gray-900">OLED, 6.7", Super Retina XDR</span>
                </li>
                <li class="px-6 py-4 flex flex-col sm:flex-row sm:items-center bg-gray-50 border-b border-gray-100">
                    <span class="font-semibold text-gray-500 w-full sm:w-1/3 mb-1 sm:mb-0">Hệ điều hành:</span>
                    <span class="w-full sm:w-2/3 text-gray-900">iOS 17</span>
                </li>
                <li class="px-6 py-4 flex flex-col sm:flex-row sm:items-center border-b border-gray-100">
                    <span class="font-semibold text-gray-500 w-full sm:w-1/3 mb-1 sm:mb-0">Camera sau:</span>
                    <span class="w-full sm:w-2/3 text-gray-900">Chính 48 MP & Phụ 12 MP, 12 MP</span>
                </li>
                <li class="px-6 py-4 flex flex-col sm:flex-row sm:items-center bg-gray-50 border-b border-gray-100">
                    <span class="font-semibold text-gray-500 w-full sm:w-1/3 mb-1 sm:mb-0">Camera trước:</span>
                    <span class="w-full sm:w-2/3 text-gray-900">12 MP</span>
                </li>
                <li class="px-6 py-4 flex flex-col sm:flex-row sm:items-center border-b border-gray-100">
                    <span class="font-semibold text-gray-500 w-full sm:w-1/3 mb-1 sm:mb-0">Chip:</span>
                    <span class="w-full sm:w-2/3 text-gray-900">Apple A17 Pro 6 nhân</span>
                </li>
                <li class="px-6 py-4 flex flex-col sm:flex-row sm:items-center bg-gray-50 border-b border-gray-100">
                    <span class="font-semibold text-gray-500 w-full sm:w-1/3 mb-1 sm:mb-0">RAM/Dung lượng:</span>
                    <span class="w-full sm:w-2/3 text-gray-900">8 GB / 256 GB</span>
                </li>
                <li class="px-6 py-4 flex flex-col sm:flex-row sm:items-center">
                    <span class="font-semibold text-gray-500 w-full sm:w-1/3 mb-1 sm:mb-0">Pin, Sạc:</span>
                    <span class="w-full sm:w-2/3 text-gray-900">4422 mAh, 20 W</span>
                </li>
            </ul>
        </div>
    </div>

    <div class="mt-16 border border-gray-200 rounded-3xl p-6 md:p-10 bg-white shadow-sm mb-12">
        <h2 class="text-2xl font-bold text-gray-900 mb-8">Đánh giá sản phẩm</h2>

        <div class="grid grid-cols-1 md:grid-cols-3 gap-8 items-center bg-gray-50 rounded-2xl p-6 mb-10 border border-gray-100">
            <div class="flex flex-col items-center justify-center text-center">
                <span class="text-6xl font-black text-red-600">4.8</span>
                <div class="flex text-yellow-400 text-xl mt-3 mb-1">
                    ★★★★★
                </div>
                <span class="text-gray-500 text-sm font-medium">Dựa trên 128 đánh giá</span>
            </div>

            <div class="flex flex-col gap-2.5">
                <div class="flex items-center gap-3 text-sm">
                    <span class="text-gray-700 font-bold w-12">5 sao</span>
                    <div class="flex-1 h-2.5 bg-gray-200 rounded-full overflow-hidden">
                        <div class="h-full bg-red-500 rounded-full" style="width: 85%"></div>
                    </div>
                    <span class="text-gray-500 text-xs font-bold w-8 text-right">85%</span>
                </div>
                <div class="flex items-center gap-3 text-sm">
                    <span class="text-gray-700 font-bold w-12">4 sao</span>
                    <div class="flex-1 h-2.5 bg-gray-200 rounded-full overflow-hidden">
                        <div class="h-full bg-red-500 rounded-full" style="width: 10%"></div>
                    </div>
                    <span class="text-gray-500 text-xs font-bold w-8 text-right">10%</span>
                </div>
                <div class="flex items-center gap-3 text-sm">
                    <span class="text-gray-700 font-bold w-12">3 sao</span>
                    <div class="flex-1 h-2.5 bg-gray-200 rounded-full overflow-hidden">
                        <div class="h-full bg-red-500 rounded-full" style="width: 3%"></div>
                    </div>
                    <span class="text-gray-500 text-xs font-bold w-8 text-right">3%</span>
                </div>
                <div class="flex items-center gap-3 text-sm">
                    <span class="text-gray-700 font-bold w-12">2 sao</span>
                    <div class="flex-1 h-2.5 bg-gray-200 rounded-full overflow-hidden">
                        <div class="h-full bg-red-500 rounded-full" style="width: 1%"></div>
                    </div>
                    <span class="text-gray-500 text-xs font-bold w-8 text-right">1%</span>
                </div>
                <div class="flex items-center gap-3 text-sm">
                    <span class="text-gray-700 font-bold w-12">1 sao</span>
                    <div class="flex-1 h-2.5 bg-gray-200 rounded-full overflow-hidden">
                        <div class="h-full bg-red-500 rounded-full" style="width: 1%"></div>
                    </div>
                    <span class="text-gray-500 text-xs font-bold w-8 text-right">1%</span>
                </div>
            </div>

            <div class="flex justify-center md:justify-end">
                <button class="bg-white border-2 border-red-600 text-red-600 font-bold py-3 px-8 rounded-xl hover:bg-red-600 hover:text-white transition-all duration-300 shadow-sm">
                    ✍️ Viết đánh giá của bạn
                </button>
            </div>
        </div>

        <div class="space-y-8">
            <div class="flex gap-5 border-b border-gray-100 pb-8">
                <div class="w-14 h-14 bg-gray-200 rounded-full flex items-center justify-center font-bold text-gray-500 text-xl flex-shrink-0">
                    H
                </div>
                <div class="flex-1">
                    <div class="flex items-center justify-between mb-1">
                        <h4 class="font-bold text-gray-900 text-base">Hoàng Ngọc Anh</h4>
                        <span class="text-sm text-gray-400 font-medium">18/02/2026</span>
                    </div>
                    <div class="text-yellow-400 text-sm mb-3 tracking-widest">★★★★★</div>
                    <p class="text-gray-700 text-base leading-relaxed mb-4">
                        Máy dùng siêu mượt, thiết kế viền Titan cầm rất nhẹ và sướng tay. Camera chụp buổi tối cực kỳ nét. Shop giao hàng nhanh, bọc gói cẩn thận. Sẽ ủng hộ shop dài dài!
                    </p>
                    <button class="text-sm text-gray-500 font-medium hover:text-blue-600 flex items-center gap-1.5 transition-colors">
                        <svg class="w-4 h-4" fill="none" stroke="currentColor" viewBox="0 0 24 24" xmlns="http://www.w3.org/2000/svg"><path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M14 10h4.764a2 2 0 011.789 2.894l-3.5 7A2 2 0 0115.263 21h-4.017c-.163 0-.326-.02-.485-.06L7 20m7-10V5a2 2 0 00-2-2h-.095c-.5 0-.905.405-.905.905 0 .714-.211 1.412-.608 2.006L7 11v9m7-10h-2M7 20H5a2 2 0 01-2-2v-6a2 2 0 012-2h2.5"></path></svg>
                        Hữu ích (12)
                    </button>
                </div>
            </div>

            <div class="flex gap-5">
                <div class="w-14 h-14 bg-blue-100 text-blue-600 rounded-full flex items-center justify-center font-bold text-xl flex-shrink-0">
                    T
                </div>
                <div class="flex-1">
                    <div class="flex items-center justify-between mb-1">
                        <h4 class="font-bold text-gray-900 text-base">Trần Lê Minh</h4>
                        <span class="text-sm text-gray-400 font-medium">15/02/2026</span>
                    </div>
                    <div class="text-yellow-400 text-sm mb-3 tracking-widest">★★★★<span class="text-gray-300">★</span></div>
                    <p class="text-gray-700 text-base leading-relaxed mb-4">
                        Mọi thứ đều hoàn hảo ngoại trừ việc sạc pin đôi lúc máy hơi nóng lên một chút. Màn hình 120Hz lướt cực êm. Điểm trừ là không kèm củ sạc trong hộp.
                    </p>
                    <button class="text-sm text-gray-500 font-medium hover:text-blue-600 flex items-center gap-1.5 transition-colors">
                        <svg class="w-4 h-4" fill="none" stroke="currentColor" viewBox="0 0 24 24" xmlns="http://www.w3.org/2000/svg"><path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M14 10h4.764a2 2 0 011.789 2.894l-3.5 7A2 2 0 0115.263 21h-4.017c-.163 0-.326-.02-.485-.06L7 20m7-10V5a2 2 0 00-2-2h-.095c-.5 0-.905.405-.905.905 0 .714-.211 1.412-.608 2.006L7 11v9m7-10h-2M7 20H5a2 2 0 01-2-2v-6a2 2 0 012-2h2.5"></path></svg>
                        Hữu ích (5)
                    </button>
                </div>
            </div>
        </div>

        <div class="mt-10 flex justify-center">
            <button class="px-8 py-3 border-2 border-gray-200 rounded-xl text-base font-bold text-gray-700 hover:border-gray-400 hover:bg-gray-50 transition-all">
                Xem thêm đánh giá
            </button>
        </div>
    </div>

</div>

<script src="https://cdn.jsdelivr.net/npm/@fancyapps/ui@5.0/dist/fancybox/fancybox.umd.js"></script>

<script>
                        // 1. Khởi tạo Fancybox
                        Fancybox.bind('[data-fancybox="gallery"]', {
                            hideScrollbar: false,
                        });

                        // 2. Hàm đổi ảnh khi click ảnh thu nhỏ
                        function changeImage(event, newImgSrc, newHighResSrc) {
                            document.getElementById('mainImage').src = newImgSrc;
                            document.getElementById('mainImageLink').href = newHighResSrc;

                            let buttons = document.querySelectorAll('.thumb-btn');
                            buttons.forEach(btn => {
                                btn.classList.remove('border-red-500');
                                btn.classList.add('border-gray-100');
                            });

                            event.currentTarget.classList.remove('border-gray-100');
                            event.currentTarget.classList.add('border-red-500');
                        }

                        // 3. Xử lý click chọn Dung lượng (Capacity)
                        function selectCapacity(event) {
                            // Lấy danh sách tất cả các nút dung lượng
                            let buttons = document.querySelectorAll('.capacity-btn');

                            // Reset tất cả các nút về trạng thái xám (không chọn)
                            buttons.forEach(btn => {
                                btn.className = "capacity-btn px-5 py-2.5 border border-gray-200 text-gray-600 font-semibold rounded-xl hover:border-gray-400 transition-all";
                            });

                            // Đổi màu nút vừa được click thành màu đỏ (đã chọn)
                            let clickedBtn = event.currentTarget;
                            clickedBtn.className = "capacity-btn px-5 py-2.5 border-2 border-red-600 text-red-600 font-bold rounded-xl bg-red-50 transition-all";
                        }

                        // 4. Xử lý click chọn Màu sắc (Color)
                        function selectColor(event, colorName) {
                            // Lấy danh sách tất cả các nút màu
                            let buttons = document.querySelectorAll('.color-btn');

                            // Reset viền tất cả các nút
                            buttons.forEach(btn => {
                                btn.className = "color-btn w-11 h-11 rounded-full border border-gray-200 p-1 focus:outline-none hover:border-gray-400 transition-transform hover:scale-110";
                            });

                            // Hiện viền đỏ cho nút màu được click
                            let clickedBtn = event.currentTarget;
                            clickedBtn.className = "color-btn w-11 h-11 rounded-full border-2 border-red-600 p-1 focus:outline-none transition-transform hover:scale-110";

                            // Cập nhật text hiển thị tên màu kế bên chữ "Màu sắc:"
                            document.getElementById('colorNameLabel').innerText = colorName;
                        }

                        // 5. Nút tăng giảm số lượng
                        function updateQuantity(change) {
                            let input = document.getElementById('qtyInput');
                            let currentVal = parseInt(input.value);
                            let newVal = currentVal + change;

                            // Đảm bảo số lượng không thể rớt xuống số 0 hoặc âm
                            if (newVal >= 1) {
                                input.value = newVal;
                            }
                        }
</script>