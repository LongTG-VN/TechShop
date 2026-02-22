<%@page contentType="text/html" pageEncoding="UTF-8"%>
<div class="max-w-2xl mx-auto bg-white overflow-hidden shadow-xl sm:rounded-lg">
    <div class="px-6 py-5 border-b border-gray-200 bg-gray-50">
        <div class="flex items-center gap-2">
            <a href="userservlet?action=addressBook" class="text-gray-400 hover:text-blue-600 transition-colors">
                <svg class="h-5 w-5" fill="none" stroke="currentColor" viewBox="0 0 24 24"><path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M10 19l-7-7m0 0l7-7m-7 7h18"></path></svg>
            </a>
            <h3 class="text-lg leading-6 font-medium text-gray-900">Thêm Địa Chỉ Mới</h3>
        </div>
    </div>

    <div class="px-6 py-6 bg-white">
        <form action="addresssuserservlet" method="POST" class="space-y-6">
            <input type="hidden" name="provinceName" id="provinceName">
            <input type="hidden" name="wardName" id="wardName">
            <input type="hidden" name="action" id="action" value="add">
  
            <div class="grid grid-cols-1 md:grid-cols-2 gap-6">
                <div>
                    <label for="nameReceiver" class="block text-sm font-medium text-gray-700 mb-1">Tên người nhận <span class="text-red-500">*</span></label>
                    <input type="text" name="nameReceiver" id="nameReceiver" required
                           class="w-full px-4 py-2 text-sm border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-500 outline-none transition-colors"
                           placeholder="VD: Nguyễn Văn A">
                </div>

                <div>
                    <label for="phoneReceiver" class="block text-sm font-medium text-gray-700 mb-1">Số điện thoại <span class="text-red-500">*</span></label>
                    <input type="tel" name="phoneReceiver" id="phoneReceiver" required pattern="[0-9]{10,11}"
                           class="w-full px-4 py-2 text-sm border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-500 outline-none transition-colors"
                           placeholder="VD: 0912345678">
                </div>
            </div>

            <div class="border-t border-gray-100 pt-5 mt-2">
                <h4 class="text-sm font-medium text-gray-900 mb-4">Chi tiết địa chỉ <span class="text-red-500">*</span></h4>

                <div class="grid grid-cols-1 md:grid-cols-2 gap-4 mb-4">
                    <div>
                        <label for="province" class="block text-sm text-gray-700 mb-1">Tỉnh/Thành phố</label>
                        <select name="province" id="province" required
                                class="w-full px-4 py-2 text-sm border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-500 outline-none bg-white">
                            <option value="">Chọn Tỉnh/Thành</option>
                        </select>
                    </div>

                    <div>
                        <label for="ward" class="block text-sm text-gray-700 mb-1">Phường/Xã/Thị trấn</label>
                        <select name="ward" id="ward" required disabled
                                class="w-full px-4 py-2 text-sm border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-500 outline-none bg-gray-50 disabled:opacity-60 transition-all">
                            <option value="">Chọn Phường/Xã</option>
                        </select>
                    </div>
                </div>

                <div>
                    <label for="street" class="block text-sm text-gray-700 mb-1">Số nhà, Tên đường</label>
                    <input type="text" name="street" id="street" required
                           class="w-full px-4 py-2 text-sm border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-500 outline-none"
                           placeholder="VD: Số 123, Đường 3/2">
                </div>
            </div>

            <div class="flex items-center mt-4">
                <input type="checkbox" name="isDefault" id="isDefault" value="true"
                       class="h-4 w-4 text-blue-600 focus:ring-blue-500 border-gray-300 rounded cursor-pointer">
                <label for="isDefault" class="ml-2 block text-sm text-gray-900 cursor-pointer">
                    Đặt làm địa chỉ mặc định
                </label>
            </div>

            <div class="flex items-center justify-end gap-3 pt-4 border-t border-gray-100">
                <a href="userservlet?action=addressBook" 
                   class="px-4 py-2 text-sm font-medium text-gray-700 bg-white border border-gray-300 rounded-lg hover:bg-gray-50 transition">
                    Hủy
                </a>
                <button type="submit" 
                        class="px-4 py-2 text-sm font-medium text-white bg-blue-600 rounded-lg hover:bg-blue-700 transition shadow-sm">
                    Lưu Địa Chỉ
                </button>
            </div>
        </form>
    </div>
</div>

<script>
const contextPath = "${pageContext.request.contextPath}";
const provinceUrl = contextPath + "/assest/js/province.json";
const wardUrl     = contextPath + "/assest/js/ward.json";

let provincesData = [];
let wardsData     = [];

document.addEventListener("DOMContentLoaded", function () {
    const provinceSelect = document.getElementById('province');
    // Vì dữ liệu VietMap chỉ có 2 cấp, ta sẽ ẩn hoặc không dùng districtSelect nếu cần
    const wardSelect     = document.getElementById('ward');

    // 1. Nạp dữ liệu
    Promise.all([
        fetch(provinceUrl).then(res => res.json()),
        fetch(wardUrl).then(res => res.json())
    ])
    .then(([provinces, wards]) => {
        // Chuyển Object thành mảng để xử lý lặp
        provincesData = Object.values(provinces);
        wardsData     = Object.values(wards);

        // Đổ danh sách Tỉnh/Thành
        provinceSelect.innerHTML = '<option value="">Chọn Tỉnh/Thành</option>';
        provincesData.sort((a, b) => a.name.localeCompare(b.name)).forEach(p => {
            let opt = document.createElement('option');
            opt.value = p.code; // Mã tỉnh (ví dụ: 12)
            opt.textContent = p.name_with_type; // Tên hiển thị
            provinceSelect.appendChild(opt);
        });
    })
    .catch(err => {
        console.error("Lỗi nạp file:", err);
        provinceSelect.innerHTML = '<option value="">Lỗi nạp dữ liệu!</option>';
    });

    // 2. Khi chọn Tỉnh -> Lọc trực tiếp Phường/Xã (Bỏ qua Quận/Huyện)
    provinceSelect.onchange = function() {
        const pCode = this.value; // Lấy mã tỉnh
        const pName = this.options[this.selectedIndex].text;
        
        // Gán tên tỉnh vào hidden input để gửi về Servlet
        document.getElementById('provinceName').value = (pCode === "") ? "" : pName;

        // Reset ô Phường/Xã
        wardSelect.innerHTML = '<option value="">Chọn Phường/Xã</option>';
        wardSelect.disabled = true;

        if (pCode) {
            // Lọc các xã trong wardsData có parent_code trùng với pCode của tỉnh
            // Đây là cấu trúc đặc thù của file VietMap bạn gửi
            const filteredWards = wardsData.filter(w => w.parent_code == pCode);
            
            if (filteredWards.length > 0) {
                filteredWards.sort((a, b) => a.name.localeCompare(b.name)).forEach(w => {
                    let opt = document.createElement('option');
                    opt.value = w.code;
                    opt.textContent = w.name_with_type;
                    wardSelect.appendChild(opt);
                });
                wardSelect.disabled = false;
            }
        }
        console.log("Đã chọn Tỉnh:", document.getElementById('provinceName').value);
    };

    // 3. Khi chọn Phường/Xã
    wardSelect.onchange = function() {
        const wName = this.options[this.selectedIndex].text;
        document.getElementById('wardName').value = (this.value === "") ? "" : wName;
        console.log("Đã chọn Xã:", document.getElementById('wardName').value);
    };
});
</script>