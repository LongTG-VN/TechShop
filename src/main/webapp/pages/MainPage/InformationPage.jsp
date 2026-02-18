
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<body class="bg-[#f4f6f8] font-sans text-gray-800">

   

    <div class="max-w-[1200px] mx-auto px-4 pb-12">
        <div class="bg-white rounded-3xl overflow-hidden shadow-sm border border-gray-100 flex flex-col md:flex-row">
            <div class="w-full md:w-1/2 p-8 md:p-12 flex flex-col justify-center">
                <span class="text-blue-600 font-bold tracking-wider uppercase text-sm mb-2">Câu chuyện của chúng tôi</span>
                <h1 class="text-3xl md:text-5xl font-extrabold text-gray-900 mb-6 leading-tight">
                    Chào mừng bạn đến với <span class="text-red-600">TechShop</span>
                </h1>
                <p class="text-gray-600 leading-relaxed mb-6 text-lg">
                    Được thành lập với niềm đam mê công nghệ mãnh liệt, TechShop không chỉ là một cửa hàng bán lẻ thiết bị điện tử, mà còn là điểm đến tin cậy cho những người yêu công nghệ. Chúng tôi chuyên cung cấp các dòng điện thoại, laptop và phụ kiện chính hãng với mức giá cạnh tranh nhất.
                </p>
                <div class="mt-4">
                    <a href="#core-values" class="inline-block bg-blue-600 text-white font-semibold px-8 py-3 rounded-full hover:bg-blue-700 transition-colors shadow-md hover:shadow-lg">
                        Tìm hiểu thêm
                    </a>
                </div>
            </div>
            <div class="w-full md:w-1/2 relative min-h-[300px] md:min-h-full bg-gray-100">
                <img src="https://images.unsplash.com/photo-1556740749-887f6717d7e4?q=80&w=2070&auto=format&fit=crop" 
                     alt="Cửa hàng TechShop" 
                     class="absolute inset-0 w-full h-full object-cover">
            </div>
        </div>
    </div>

    <div class="bg-blue-600 text-white py-12 mb-16">
        <div class="max-w-[1200px] mx-auto px-4">
            <div class="grid grid-cols-2 md:grid-cols-4 gap-8 text-center">
                <div>
                    <div class="text-4xl md:text-5xl font-extrabold mb-2">5+</div>
                    <div class="text-blue-200 font-medium text-sm uppercase tracking-wider">Năm kinh nghiệm</div>
                </div>
                <div>
                    <div class="text-4xl md:text-5xl font-extrabold mb-2">10K+</div>
                    <div class="text-blue-200 font-medium text-sm uppercase tracking-wider">Khách hàng tin dùng</div>
                </div>
                <div>
                    <div class="text-4xl md:text-5xl font-extrabold mb-2">500+</div>
                    <div class="text-blue-200 font-medium text-sm uppercase tracking-wider">Sản phẩm đa dạng</div>
                </div>
                <div>
                    <div class="text-4xl md:text-5xl font-extrabold mb-2">100%</div>
                    <div class="text-blue-200 font-medium text-sm uppercase tracking-wider">Cam kết chính hãng</div>
                </div>
            </div>
        </div>
    </div>

    <div id="core-values" class="max-w-[1200px] mx-auto px-4 pb-20">
        <div class="text-center mb-12">
            <h2 class="text-3xl font-bold text-gray-900 mb-4">Giá Trị Cốt Lõi</h2>
            <p class="text-gray-500 max-w-2xl mx-auto">Chúng tôi luôn đặt quyền lợi của khách hàng lên hàng đầu, cam kết mang lại những trải nghiệm mua sắm vượt trội nhất.</p>
        </div>

        <div class="grid grid-cols-1 md:grid-cols-3 gap-8">
            <div class="bg-white p-8 rounded-2xl shadow-sm border border-gray-100 hover:shadow-xl hover:-translate-y-1 transition-all duration-300 text-center group">
                <div class="w-20 h-20 mx-auto bg-blue-50 rounded-full flex items-center justify-center mb-6 group-hover:bg-blue-600 transition-colors duration-300">
                    <svg class="w-10 h-10 text-blue-600 group-hover:text-white transition-colors duration-300" fill="none" stroke="currentColor" viewBox="0 0 24 24"><path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M9 12l2 2 4-4m6 2a9 9 0 11-18 0 9 9 0 0118 0z"></path></svg>
                </div>
                <h3 class="text-xl font-bold text-gray-900 mb-3">Chất Lượng Hàng Đầu</h3>
                <p class="text-gray-600 leading-relaxed">Mọi sản phẩm tại TechShop đều trải qua quy trình kiểm tra nghiêm ngặt, đảm bảo nguyên seal, chính hãng 100% khi đến tay người dùng.</p>
            </div>

            <div class="bg-white p-8 rounded-2xl shadow-sm border border-gray-100 hover:shadow-xl hover:-translate-y-1 transition-all duration-300 text-center group">
                <div class="w-20 h-20 mx-auto bg-red-50 rounded-full flex items-center justify-center mb-6 group-hover:bg-red-600 transition-colors duration-300">
                    <svg class="w-10 h-10 text-red-600 group-hover:text-white transition-colors duration-300" fill="none" stroke="currentColor" viewBox="0 0 24 24"><path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M4.318 6.318a4.5 4.5 0 000 6.364L12 20.364l7.682-7.682a4.5 4.5 0 00-6.364-6.364L12 7.636l-1.318-1.318a4.5 4.5 0 00-6.364 0z"></path></svg>
                </div>
                <h3 class="text-xl font-bold text-gray-900 mb-3">Tận Tâm Phục Vụ</h3>
                <p class="text-gray-600 leading-relaxed">Đội ngũ nhân viên luôn sẵn sàng tư vấn, hỗ trợ kỹ thuật nhiệt tình để giúp bạn tìm được sản phẩm công nghệ phù hợp nhất với nhu cầu.</p>
            </div>

            <div class="bg-white p-8 rounded-2xl shadow-sm border border-gray-100 hover:shadow-xl hover:-translate-y-1 transition-all duration-300 text-center group">
                <div class="w-20 h-20 mx-auto bg-green-50 rounded-full flex items-center justify-center mb-6 group-hover:bg-green-600 transition-colors duration-300">
                    <svg class="w-10 h-10 text-green-600 group-hover:text-white transition-colors duration-300" fill="none" stroke="currentColor" viewBox="0 0 24 24"><path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M12 8v4l3 3m6-3a9 9 0 11-18 0 9 9 0 0118 0z"></path></svg>
                </div>
                <h3 class="text-xl font-bold text-gray-900 mb-3">Bảo Hành Tốc Độ</h3>
                <p class="text-gray-600 leading-relaxed">Chính sách đổi trả linh hoạt, bảo hành nhanh chóng. Chúng tôi luôn đồng hành cùng thiết bị của bạn trong suốt quá trình sử dụng.</p>
            </div>
        </div>
    </div>

</body>