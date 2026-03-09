<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>${pageTitle}</title>

        <script src="https://cdn.tailwindcss.com"></script>
        <link href="https://cdn.jsdelivr.net/npm/flowbite@2.5.2/dist/flowbite.min.css" rel="stylesheet"/>

        <style>
            body {
                font-family: 'Inter', sans-serif;
            }

            /* CÁC CLASS CSS CHUẨN ĐỂ FIX GIAO DIỆN LỎ */
            .chat-msg {
                padding: 10px 14px;
                border-radius: 18px;
                max-width: 80%;
                font-size: 14.5px;
                line-height: 1.5;
                /* Hai dòng này cực kỳ quan trọng để chữ không dính lẹo và tự xuống dòng */
                word-wrap: break-word;
                white-space: pre-wrap;
                animation: fadeIn 0.3s ease;
            }

            @keyframes fadeIn {
                from {
                    opacity: 0;
                    transform: translateY(10px);
                }
                to {
                    opacity: 1;
                    transform: translateY(0);
                }
            }

            /* Tin nhắn AI: Nằm bên trái, màu trắng */
            .ai-msg {
                align-self: flex-start;
                background: #ffffff;
                border: 1px solid #e5e7eb;
                color: #1f2937;
                border-bottom-left-radius: 2px;
            }

            /* Tin nhắn User: Nằm bên phải, màu đỏ cho tiệp với theme TechShop */
            .user-msg {
                align-self: flex-end;
                background: #dc2626;
                color: white;
                border-bottom-right-radius: 2px;
            }

            .typing {
                font-size: 0.8rem;
                color: #888;
                display: none;
                margin-top: -5px;
                padding-left: 10px;
            }
        </style>
    </head>

    <body class="bg-gray-50 min-h-screen flex flex-col">

        <header>
            <jsp:include page="${HeaderComponent}" />
        </header>

        <main class="flex-grow container mx-auto px-4 py-6">
            <jsp:include page="${ContentPage}" />
        </main>

        <main class="flex-grow container mx-auto px-4 py-6">
            <jsp:include page="${FooterComponent}" />
        </main>
        <button id="chat-toggle-btn" class="fixed bottom-6 right-6 p-4 bg-red-600 text-white rounded-full shadow-2xl hover:bg-red-700 transition-all z-[60] focus:outline-none">
            <svg id="chat-icon" class="w-8 h-8" fill="none" stroke="currentColor" viewBox="0 0 24 24">
            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M8 10h.01M12 10h.01M16 10h.01M9 16H5a2 2 0 01-2-2V6a2 2 0 012-2h14a2 2 0 012 2v8a2 2 0 01-2 2h-5l-5 5v-5z"></path>
            </svg>
            <svg id="close-icon" class="hidden w-8 h-8" fill="none" stroke="currentColor" viewBox="0 0 24 24">
            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M6 18L18 6M6 6l12 12"></path>
            </svg>
        </button>

        <div id="ai-chat-box" class="hidden fixed bottom-24 right-6 w-80 md:w-96 h-[500px] bg-white rounded-2xl shadow-2xl flex flex-col z-[60] border border-gray-100 overflow-hidden">
            <div class="bg-red-600 p-4 text-white flex justify-between items-center">
                <div class="flex items-center gap-2">
                    <div class="w-2 h-2 bg-green-400 rounded-full animate-pulse"></div>
                    <span class="font-bold">TechShop Assistant</span>
                </div>
            </div>

           <div id="output" class="flex-grow p-4 overflow-y-auto flex flex-col bg-gray-50">
    <div class="self-start bg-white border border-gray-200 p-3 rounded-2xl rounded-bl-none shadow-sm max-w-[80%] chat-bubble mb-3">
        Chào Long! Mình là trợ lý Gemini 3. Hôm nay TechShop có thể giúp gì cho bạn?
    </div>
</div>

            <div id="typing-indicator" class="typing">AI đang suy nghĩ...</div>

            <div class="p-4 border-t bg-white">
                <div class="flex gap-2">
                    <input type="text" id="user-input" placeholder="Nhập câu hỏi..." class="flex-grow text-sm border border-gray-200 p-2 px-4 rounded-full focus:outline-none focus:border-red-500">
                    <button id="btn-send" class="bg-red-600 text-white p-2 rounded-full hover:bg-red-700 transition-colors w-10 h-10 flex justify-center items-center">
                        <svg class="w-5 h-5" fill="none" stroke="currentColor" viewBox="0 0 24 24"><path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M12 19l9 2-9-18-9 18 9-2zm0 0v-8"></path></svg>
                    </button>
                </div>
            </div>
        </div>

        <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/flowbite@2.5.2/dist/flowbite.min.js"></script>

        <script type="importmap">
            { "imports": { "@google/genai": "https://esm.run/@google/genai" } }
        </script>

        <script type="module">
            import { GoogleGenAI } from "@google/genai";

            // Logic Ẩn/Hiện Khung Chat
            const toggleBtn = document.getElementById('chat-toggle-btn');
            const chatBox = document.getElementById('ai-chat-box');
            const chatIcon = document.getElementById('chat-icon');
            const closeIcon = document.getElementById('close-icon');

            toggleBtn.addEventListener('click', () => {
                chatBox.classList.toggle('hidden');
                chatIcon.classList.toggle('hidden');
                closeIcon.classList.toggle('hidden');
            });

            // Cấu hình AI
            const API_KEY = "Co qq";
            const ai = new GoogleGenAI({apiKey: API_KEY});

            const btn = document.getElementById('btn-send');
            const input = document.getElementById('user-input');
            const output = document.getElementById('output');
            const typingIndicator = document.getElementById('typing-indicator');

            // Hàm gõ chữ chuẩn của bạn
           async function typewriterEffect(element, text) {
            element.textContent = ""; // Dùng textContent thay vì innerText
            let currentStr = "";
            for (let i = 0; i < text.length; i++) {
                currentStr += text[i];
                element.textContent = currentStr; // Cập nhật toàn bộ chuỗi để giữ nguyên space
                output.scrollTop = output.scrollHeight;
                await new Promise(resolve => setTimeout(resolve, 15)); 
            }
        }

        // 2. HÀM NỐI TIN NHẮN (Đã fix lỗi tràn khung và thêm ép lề)
        function appendMessage(text, isUser) {
            const msgDiv = document.createElement('div');
            
            // Thêm 2 class Tailwind: 'whitespace-pre-wrap' (giữ xuống dòng/khoảng trắng) và 'break-words' (ép rớt chữ khi quá dài)
            if (isUser) {
                // Tin nhắn User (Bên phải, màu đỏ)
                msgDiv.className = "self-end bg-red-600 text-white p-3 rounded-2xl rounded-br-none shadow-md max-w-[80%] mb-3 whitespace-pre-wrap break-words";
            } else {
                // Tin nhắn AI (Bên trái, màu trắng)
                msgDiv.className = "self-start bg-white border border-gray-200 p-3 rounded-2xl rounded-bl-none shadow-sm max-w-[80%] mb-3 whitespace-pre-wrap break-words";
            }
            
            msgDiv.textContent = text;
            output.appendChild(msgDiv);
            output.scrollTop = output.scrollHeight;
            return msgDiv;
        }

            async function askAI() {
                const text = input.value.trim();
                if (!text)
                    return;

                appendMessage(text, true); // Hiện lời User bên phải
                input.value = '';
                typingIndicator.style.display = 'block';

                try {
                    // Config AI chuẩn
                    const response = await ai.models.generateContent({
                        model: "gemini-3-flash-preview",
                        contents: text,
                        config: {
                            systemInstruction: `
                                Bạn là trợ lý bán hàng chuyên nghiệp của TechShop - cửa hàng đồ công nghệ do Thái Gia Long sáng lập. 
                                Quy tắc trả lời:
                                1. Chỉ tư vấn về các sản phẩm công nghệ (Laptop, chuột, bàn phím, linh kiện).
                                2. Nếu khách hỏi về lĩnh vực khác, hãy từ chối khéo léo và lái về đồ công nghệ.
                                3. Luôn gọi khách hàng là 'Quý khách' hoặc 'Bạn' và xưng là 'TechShop AI'.
                                4. Luôn nhắc đến ưu đãi 'Bảo hành 12 tháng' và 'Giao hàng hỏa tốc trong FPT University'.
                            `,
                            temperature: 0.7,
                            thinkingConfig: {
                                thinkingLevel: "LOW"
                            }
                        }
                    });

                    typingIndicator.style.display = 'none';
                    const aiMsgDiv = appendMessage("", false); // Lời AI bên trái
                    await typewriterEffect(aiMsgDiv, response.text);

                } catch (error) {
                    typingIndicator.style.display = 'none';
                    let errorMsg = "Lỗi kết nối: " + error.message;

                    if (error.message.includes("503") || error.message.includes("high demand")) {
                        errorMsg = "Hệ thống đang bận một chút. Long thử lại sau 30 giây nhé!";
                    }

                    const errDiv = appendMessage(errorMsg, false);
                    errDiv.style.backgroundColor = "#fee2e2";
                    errDiv.style.color = "#b91c1c";
                }
            }

            // Gán sự kiện cho Nút gửi và Phím Enter
            btn.addEventListener('click', askAI);
            input.addEventListener('keypress', (e) => {
                if (e.key === 'Enter')
                    askAI();
            });
        </script>
    </body>
</html>