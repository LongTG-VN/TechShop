<%@page contentType="text/html" pageEncoding="UTF-8"%>


<div class="container mx-auto max-w-screen-xl px-4"> 


    <div class="w-full my-4"> 
        <figure class="w-full"> 
            <img class="w-full h-auto rounded-base object-cover shadow-lg" 
                 src="../assest/img/banner/banner_TGDD_tet.png" 
                 alt="">
        </figure>
    </div>

    <div class="w-full sp-1">
        <h3 class="text-3xl font-bold mb-4">⭐ Sản phẩm giá không rẽ</h3>
        <div class="ant-tabs ant-tabs-top">
            <div class="ant-tabs-nav" role="tablist">
                <div class="ant-tabs-nav-wrap">
                    <div class="ant-tabs-nav-list py-3 flex gap-4">
                        <div class="ant-tabs-tab tab-item active cursor-pointer p-2 border-b-2 border-blue-600" onclick="switchTab(event, 'tab-1')">
                            <div class="ant-tabs-tab-btn">Điện thoại</div>
                        </div>
                        <div class="ant-tabs-tab tab-item cursor-pointer p-2 border-b-2 border-transparent" onclick="switchTab(event, 'tab-2')">
                            <div class="ant-tabs-tab-btn">Máy tính</div>
                        </div>
                        <div class="ant-tabs-tab tab-item cursor-pointer p-2 border-b-2 border-transparent" onclick="switchTab(event, 'tab-3')">
                            <div class="ant-tabs-tab-btn">Phụ kiện</div>
                        </div>
                        <div class="ant-tabs-tab tab-item cursor-pointer p-2 border-b-2 border-transparent" onclick="switchTab(event, 'tab-4')">
                            <div class="ant-tabs-tab-btn">Laptop</div>
                        </div>
                    </div>
                </div>
            </div>

            <div class="ant-tabs-content-holder mt-4">
                <div id="tab-1" class="tab-pane block">

                    <div class="grid grid-cols-2 md:grid-cols-3 lg:grid-cols-5 gap-4 py-4">
                        <div class="bg-white p-4 border border-gray-100 rounded-xl shadow-sm hover:shadow-md transition-all duration-300 group cursor-pointer flex flex-col h-full">

                            <div class="relative overflow-hidden mb-4 flex-shrink-0">
                                <img class="w-full h-48 object-contain group-hover:scale-105 transition-transform duration-300" 
                                     src="../assest/img/product/samsung-galaxy-s25-green-thumbai-600x600.jpg" 
                                     alt="Samsung Galaxy S25">
                            </div>

                            <h3 class="text-[14px] font-semibold leading-snug text-gray-800 mb-2 hover:text-blue-600 line-clamp-2 h-10">
                                Samsung Galaxy S25 5G 12GB/256GB
                            </h3>

                            <div class="flex gap-1 mb-3">
                                <span class="bg-gray-100 text-[10px] text-gray-500 px-1.5 py-0.5 rounded">Full HD+</span>
                                <span class="bg-gray-100 text-[10px] text-gray-500 px-1.5 py-0.5 rounded">6.2"</span>
                            </div>

                            <div class="mt-auto mb-2">
                                <strong class="text-red-600 text-lg block">17.590.000₫</strong>
                                <div class="flex items-center gap-2">
                                    <span class="text-xs text-gray-400 line-through">22.580.000₫</span>
                                    <span class="text-red-600 text-xs font-bold">-22%</span>
                                </div>
                            </div>

                            <div class="flex items-center text-xs text-gray-500">
                                <span class="text-yellow-400">⭐</span>
                                <span class="ml-1 font-bold text-gray-700">4.9</span>
                                <span class="mx-1 text-gray-300">•</span>
                                <span>Đã bán 7,2k</span>
                            </div>
                        </div>
                        <div class="bg-white p-4 shadow rounded-lg border text-center">Sản phẩm 02</div>
                        <div class="bg-white p-4 shadow rounded-lg border text-center">Sản phẩm 03</div>
                        <div class="bg-white p-4 shadow rounded-lg border text-center">Sản phẩm 04</div>
                        <div class="bg-white p-4 shadow rounded-lg border text-center">Sản phẩm 05</div>

                        <div class="bg-white p-4 border border-gray-100 rounded-xl shadow-sm hover:shadow-md transition-all duration-300 group cursor-pointer flex flex-col h-full">

                            <div class="relative overflow-hidden mb-4 flex-shrink-0">
                                <img class="w-full h-48 object-contain group-hover:scale-105 transition-transform duration-300" 
                                     src="../assest/img/product/samsung-galaxy-s25-green-thumbai-600x600.jpg" 
                                     alt="Samsung Galaxy S25">
                            </div>

                            <h3 class="text-[14px] font-semibold leading-snug text-gray-800 mb-2 hover:text-blue-600 line-clamp-2 h-10">
                                Samsung Galaxy S25 5G 12GB/256GB
                            </h3>

                            <div class="flex gap-1 mb-3">
                                <span class="bg-gray-100 text-[10px] text-gray-500 px-1.5 py-0.5 rounded">Full HD+</span>
                                <span class="bg-gray-100 text-[10px] text-gray-500 px-1.5 py-0.5 rounded">6.2"</span>
                            </div>

                            <div class="mt-auto mb-2">
                                <strong class="text-red-600 text-lg block">17.590.000₫</strong>
                                <div class="flex items-center gap-2">
                                    <span class="text-xs text-gray-400 line-through">22.580.000₫</span>
                                    <span class="text-red-600 text-xs font-bold">-22%</span>
                                </div>
                            </div>

                            <div class="flex items-center text-xs text-gray-500">
                                <span class="text-yellow-400">⭐</span>
                                <span class="ml-1 font-bold text-gray-700">4.9</span>
                                <span class="mx-1 text-gray-300">•</span>
                                <span>Đã bán 7,2k</span>
                            </div>
                        </div>
                        <div class="bg-white p-4 shadow rounded-lg border text-center">Sản phẩm 07</div>
                        <div class="bg-white p-4 shadow rounded-lg border text-center">Sản phẩm 08</div>
                        <div class="bg-white p-4 shadow rounded-lg border text-center">Sản phẩm 09</div>
                        <div class="bg-white p-4 shadow rounded-lg border text-center">Sản phẩm 10</div>
                    </div>



                </div>
                <div id="tab-2" class="tab-pane hidden">Nội dung Tab 2: Máy tính cấu hình cao</div>
                <div id="tab-3" class="tab-pane hidden">Nội dung Tab 3: Phụ kiện chính hãng</div>
                <div id="tab-4" class="tab-pane hidden">
                    <p>Danh sách Laptop Gaming cực khủng...</p>
                </div>
            </div>
        </div>
    </div>



    <div id="animation-carousel" class="relative w-full" data-carousel="slide">

        <!-- Carousel wrapper -->
        <div class="relative h-[220px] md:h-[450px] overflow-hidden rounded-base bg-white">

            <!-- Item 1 -->
            <div class="hidden duration-300 ease-in-out flex items-center justify-center" data-carousel-item="active">
                <img
                    src="../assest/img/carousel/colorOS.jpg"
                    class="block max-w-full max-h-full object-contain"
                    alt="ColorOS"
                    >
            </div>

            <!-- Item 2 -->
            <div class="hidden duration-300 ease-in-out flex items-center justify-center" data-carousel-item>
                <img
                    src="../assest/img/carousel/kynguyenmoi.png"
                    class="block max-w-full max-h-full object-contain"
                    alt="Kỷ nguyên mới"
                    >
            </div>

            <!-- Item 3 -->
            <div class="hidden duration-300 ease-in-out flex items-center justify-center" data-carousel-item>
                <img
                    src="../assest/img/carousel/tetsumvay.jpg"
                    class="block max-w-full max-h-full object-contain"
                    alt="Tết sum vầy"
                    >
            </div>

        </div>

        <!-- Prev -->
        <button
            type="button"
            class="absolute top-1/2 -translate-y-1/2 start-4 z-30
            flex items-center justify-center
            w-10 h-10 rounded-full
            bg-white/30 hover:bg-white/50
            backdrop-blur-md shadow-md
            transition"
            data-carousel-prev
            >
            <svg class="w-5 h-5" fill="none" stroke="currentColor" viewBox="0 0 24 24">
            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                  d="M15 19l-7-7 7-7"/>
            </svg>
            <span class="sr-only">Previous</span>
        </button>

        <!-- Next -->
        <button
            type="button"
            class="absolute top-1/2 -translate-y-1/2 end-4 z-30
            flex items-center justify-center
            w-10 h-10 rounded-full
            bg-white/30 hover:bg-white/50
            backdrop-blur-md shadow-md
            transition"
            data-carousel-next
            >
            <svg class="w-5 h-5" fill="none" stroke="currentColor" viewBox="0 0 24 24">
            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                  d="M9 5l7 7-7 7"/>
            </svg>
            <span class="sr-only">Next</span>
        </button>

    </div>


</div>

<script>
    // --- LOGIC TABS ---
    function switchTab(event, tabId) {
        document.querySelectorAll('.tab-pane').forEach(p => p.classList.replace('block', 'hidden'));
        document.getElementById(tabId).classList.replace('hidden', 'block');

        document.querySelectorAll('.tab-item').forEach(item => {
            item.classList.remove('active', 'border-blue-600', 'text-blue-600', 'font-bold');
            item.classList.add('border-transparent', 'text-gray-500');
        });

        event.currentTarget.classList.add('active', 'border-blue-600', 'text-blue-600', 'font-bold');
        event.currentTarget.classList.remove('border-transparent', 'text-gray-500');
    }

</script>