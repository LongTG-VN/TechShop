<%@page contentType="text/html" pageEncoding="UTF-8"%>

<header class=" w-full z-20 top-0 start-0 bg-white shadow-sm">
  
  <nav class="bg-white border-b border-gray-200">
    <div class="flex flex-wrap justify-between items-center mx-auto max-w-screen-xl p-4 gap-4">
      
      <a href="https://flowbite.com" class="flex items-center space-x-3 shrink-0">
        <img src="https://flowbite.com/docs/images/logo.svg" class="h-8" alt="Flowbite Logo" />
        <span class="self-center text-xl font-bold text-gray-900 whitespace-nowrap">Flowbite</span>
      </a>

      <div class="flex-1 max-w-lg mx-auto hidden md:block">
        <div class="relative">
          <div class="absolute inset-y-0 start-0 flex items-center ps-3 pointer-events-none">
            <svg class="w-4 h-4 text-gray-500" aria-hidden="true" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 20 20">
              <path stroke="currentColor" stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="m19 19-4-4m0-7A7 7 0 1 1 1 8a7 7 0 0 1 14 0Z"/>
            </svg>
          </div>
          <input type="search" class="block w-full p-2.5 ps-10 text-sm text-gray-900 border border-gray-300 rounded-lg bg-gray-50 focus:ring-blue-500 focus:border-blue-500" placeholder="Tìm kiếm sản phẩm...">
        </div>
      </div>

      <div class="flex items-center gap-4 shrink-0">
    <a href="userservlet?action=loginPage" class="text-sm font-medium text-gray-700 hover:text-blue-600 transition-colors">
        Đăng nhập
    </a>

    <a href="userservlet?action=registerPage" class="text-white bg-blue-600 hover:bg-blue-700 focus:ring-4 focus:ring-blue-300 font-medium rounded-lg text-sm px-4 py-2 transition-colors focus:outline-none">
        Đăng kí
    </a>
</div>
    </div>
  </nav>

  <nav class="bg-gray-50 border-b border-gray-200">
    <div class="max-w-screen-xl px-4 py-3 mx-auto flex justify-center">
        
        <ul class="flex flex-row font-medium space-x-12 text-sm">
          
          <li>
            <a href="userservlet?action=homePage" class="text-gray-900 hover:text-blue-600 transition-colors" aria-current="page">Trang chủ</a>
          </li>
          
          <li class="relative group">
            <button class="flex items-center text-gray-900 hover:text-blue-600 focus:outline-none transition-colors">
              Sản phẩm 
              <svg class="w-2.5 h-2.5 ms-2 transition-transform duration-200 group-hover:rotate-180" aria-hidden="true" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 10 6">
                <path stroke="currentColor" stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="m1 1 4 4 4-4"/>
              </svg>
            </button>
            
            <div class="absolute left-1/2 -translate-x-1/2 top-full mt-3 z-50 hidden group-hover:block w-48 bg-white divide-y divide-gray-100 rounded-lg shadow-xl border border-gray-100">
                
                <div class="absolute -top-1.5 left-1/2 -translate-x-1/2 w-3 h-3 bg-white border-t border-l border-gray-100 transform rotate-45"></div>

                <ul class="relative py-2 text-sm text-gray-700 bg-white rounded-lg">
                  <li><a href="#" class="block px-4 py-2 hover:bg-blue-50 hover:text-blue-600">Điện thoại</a></li>
                  <li><a href="#" class="block px-4 py-2 hover:bg-blue-50 hover:text-blue-600">Laptop</a></li>
                  <li><a href="#" class="block px-4 py-2 hover:bg-blue-50 hover:text-blue-600">Máy tính bảng</a></li>
                  <li><a href="#" class="block px-4 py-2 hover:bg-blue-50 hover:text-blue-600">Phụ kiện</a></li>
                </ul>
            </div>
          </li>

          <li><a href="#" class="text-gray-900 hover:text-blue-600 transition-colors">Công ty</a></li>
          <li><a href="#" class="text-gray-900 hover:text-blue-600 transition-colors">Tính năng</a></li>
        </ul>

    </div>
  </nav>
</header>