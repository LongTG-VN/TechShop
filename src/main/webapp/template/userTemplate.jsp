<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

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
        
         <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/flowbite@2.5.2/dist/flowbite.min.js"></script>
        
    </body>
</html>