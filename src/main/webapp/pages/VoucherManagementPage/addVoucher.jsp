<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="flex justify-center items-start pt-10 min-h-screen bg-gray-50">
    <div class="w-full max-w-2xl bg-white rounded-2xl shadow-2xl p-10 border border-gray-100">

        <div class="text-center mb-10">
            <h2 class="text-3xl font-extrabold text-gray-900 tracking-tight">Create New Voucher</h2>
            <p class="text-gray-500 mt-2">Setup a new discount campaign for your store.</p>
        </div>

        <form action="voucherservlet" method="POST" class="space-y-8">
            <input type="hidden" name="action" value="add">

            <div class="grid md:grid-cols-2 md:gap-8">
                <div class="relative z-0 w-full group">
                    <input type="text" name="code" id="code" value="${oldCode}"
                           class="block py-2.5 px-0 w-full text-sm text-gray-900 bg-transparent border-0 border-b-2 border-gray-300 appearance-none focus:outline-none focus:ring-0 focus:border-blue-600 peer" 
                           placeholder=" " required style="text-transform: uppercase;" />
                    <label for="code" class="absolute text-sm text-gray-500 duration-300 transform -translate-y-6 scale-75 top-3 -z-10 origin-[0] peer-focus:text-blue-600 peer-placeholder-shown:scale-100 peer-placeholder-shown:translate-y-0 peer-focus:scale-75 peer-focus:-translate-y-6">
                        Voucher Code (e.g. SALE2026)
                    </label>
                    <c:if test="${not empty errorCode}">
                        <p class="mt-1 text-xs text-red-600 font-medium">${errorCode}</p>
                    </c:if>
                </div>
                <div class="relative z-0 w-full group">
                    <input type="number" name="discount_percent" id="discount_percent" value="${oldDiscountPercent}" min="1" max="100"
                           class="block py-2.5 px-0 w-full text-sm text-gray-900 bg-transparent border-0 border-b-2 border-gray-300 appearance-none focus:outline-none focus:ring-0 focus:border-blue-600 peer" 
                           placeholder=" " required />
                    <label for="discount_percent" class="absolute text-sm text-gray-500 duration-300 transform -translate-y-6 scale-75 top-3 -z-10 origin-[0] peer-focus:text-blue-600 peer-placeholder-shown:scale-100 peer-placeholder-shown:translate-y-0 peer-focus:scale-75 peer-focus:-translate-y-6">
                        Discount Percentage (%)
                    </label>
                </div>
            </div>

            <div class="grid md:grid-cols-2 md:gap-8">
                <div class="relative z-0 w-full group">
                    <input type="datetime-local" name="valid_from" id="valid_from" 
                           value="${oldValidFromStr}" class="block py-2.5 px-0 w-full text-sm text-gray-900 bg-transparent border-0 border-b-2 border-gray-300 appearance-none focus:outline-none focus:ring-0 focus:border-blue-600 peer" 
                           placeholder=" " required />
                    <label for="valid_from" class="absolute text-sm text-gray-500 duration-300 transform -translate-y-6 scale-75 top-3 -z-10 origin-[0] peer-focus:text-blue-600 peer-placeholder-shown:scale-100 peer-placeholder-shown:translate-y-0 peer-focus:scale-75 peer-focus:-translate-y-6">
                        Valid From
                    </label>

                </div>
                <div class="relative z-0 w-full group">
                    <input type="datetime-local" name="valid_to" id="valid_to"  value="${oldValidToStr}"
                           class="block py-2.5 px-0 w-full text-sm text-gray-900 bg-transparent border-0 border-b-2 border-gray-300 appearance-none focus:outline-none focus:ring-0 focus:border-blue-600 peer" 
                           placeholder=" " required />
                    <label for="valid_to" class="absolute text-sm text-gray-500 duration-300 transform -translate-y-6 scale-75 top-3 -z-10 origin-[0] peer-focus:text-blue-600 peer-placeholder-shown:scale-100 peer-placeholder-shown:translate-y-0 peer-focus:scale-75 peer-focus:-translate-y-6">
                        Valid To (Expiration Date)
                    </label>
                    <c:if test="${not empty errorValidTo}">
                        <p class="mt-1 text-xs text-red-600 font-medium">${errorValidTo}</p>
                    </c:if>
                </div>
            </div>

            <div class="grid md:grid-cols-2 md:gap-8">
                <div class="relative z-0 w-full group">
                    <input type="number" name="min_order_value" id="min_order_value" min="0" step="1000" value="${oldMinOrderValueStr}"
                           class="block py-2.5 px-0 w-full text-sm text-gray-900 bg-transparent border-0 border-b-2 border-gray-300 appearance-none focus:outline-none focus:ring-0 focus:border-blue-600 peer" 
                           placeholder=" " required />
                    <label for="min_order_value" class="absolute text-sm text-gray-500 duration-300 transform -translate-y-6 scale-75 top-3 -z-10 origin-[0] peer-focus:text-blue-600 peer-placeholder-shown:scale-100 peer-placeholder-shown:translate-y-0 peer-focus:scale-75 peer-focus:-translate-y-6">
                        Min Order Value (VND)
                    </label>

                </div>
                <div class="relative z-0 w-full group">
                    <input type="number" name="max_discount_amount" id="max_discount_amount" min="0" step="1000" value="${oldMaxDiscountStr}"
                           class="block py-2.5 px-0 w-full text-sm text-gray-900 bg-transparent border-0 border-b-2 border-gray-300 appearance-none focus:outline-none focus:ring-0 focus:border-blue-600 peer" 
                           placeholder=" " />
                    <label for="max_discount_amount" class="absolute text-sm text-gray-500 duration-300 transform -translate-y-6 scale-75 top-3 -z-10 origin-[0] peer-focus:text-blue-600 peer-placeholder-shown:scale-100 peer-placeholder-shown:translate-y-0 peer-focus:scale-75 peer-focus:-translate-y-6">
                        Max Discount Amount (VND) - Optional
                    </label>
                    <c:if test="${not empty errorMaxDiscountAmount}">
                        <p class="mt-1 text-xs text-red-600 font-medium">${errorMaxDiscountAmount}</p>
                    </c:if>
                </div>
            </div>

            <div class="grid md:grid-cols-2 md:gap-8">
                <div class="relative z-0 w-full group">
                    <input type="number" name="total_quantity" id="total_quantity" min="1" value="${oldTotalQuantity}"
                           class="block py-2.5 px-0 w-full text-sm text-gray-900 bg-transparent border-0 border-b-2 border-gray-300 appearance-none focus:outline-none focus:ring-0 focus:border-blue-600 peer" 
                           placeholder=" " required />
                    <label for="total_quantity" class="absolute text-sm text-gray-500 duration-300 transform -translate-y-6 scale-75 top-3 -z-10 origin-[0] peer-focus:text-blue-600 peer-placeholder-shown:scale-100 peer-placeholder-shown:translate-y-0 peer-focus:scale-75 peer-focus:-translate-y-6">
                        Total Quantity
                    </label>
                </div>

              
            </div>

            <div class="flex items-center justify-end space-x-4 pt-6">
                <a href="voucherservlet?action=all" 
                   class="inline-flex items-center px-5 py-2.5 text-sm font-medium text-gray-700 bg-white border border-gray-300 rounded-lg hover:bg-gray-50 hover:text-red-600 focus:ring-4 transition-all duration-200">
                    <svg class="w-4 h-4 mr-2" fill="none" stroke="currentColor" viewBox="0 0 24 24"><path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M6 18L18 6M6 6l12 12"></path></svg>
                    Cancel
                </a>
                <button type="submit" class="px-10 py-2.5 text-sm font-medium text-white bg-blue-600 rounded-lg hover:bg-blue-700 focus:ring-4 focus:ring-blue-300 shadow-lg shadow-blue-500/40 transition-all duration-200 transform active:scale-95">
                    Create Voucher
                </button>
            </div>
        </form>
    </div>
</div>
                    
                    <script>
    document.addEventListener('DOMContentLoaded', function() {
        // Lấy thời gian hiện tại và format chuẩn theo 'YYYY-MM-DDThh:mm'
        var now = new Date();
        now.setMinutes(now.getMinutes() - now.getTimezoneOffset());
        var currentDateTime = now.toISOString().slice(0, 16);
        
        // valid_from: Tối đa là hiện tại (chỉ chọn từ quá khứ đến hiện tại)
        var validFromInput = document.getElementById('valid_from');
        if (validFromInput) {
            validFromInput.max = currentDateTime;
        }
        
        // valid_to: Tối thiểu là hiện tại (chỉ chọn từ hiện tại đến tương lai)
        var validToInput = document.getElementById('valid_to');
        if (validToInput) {
            validToInput.min = currentDateTime;
        }
        
        // Mở rộng thêm: Đảm bảo valid_to luôn phải lớn hơn valid_from nếu người dùng đổi valid_from
        validFromInput.addEventListener('change', function() {
            if (this.value) {
                // Nếu valid_from được chọn, valid_to ít nhất phải bằng valid_from
                // Nhưng vì yêu cầu valid_to luôn từ hiện tại trở đi, ta lấy max giữa hiện tại và valid_from
                var fromValue = this.value;
                validToInput.min = fromValue > currentDateTime ? fromValue : currentDateTime;
            }
        });
    });
</script>