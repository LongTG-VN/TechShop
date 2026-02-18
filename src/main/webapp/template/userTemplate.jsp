<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>${pageTitle}</title>

    <!-- Tailwind -->
    <script src="https://cdn.tailwindcss.com"></script>

    <!-- Flowbite CSS -->
    <link href="https://cdn.jsdelivr.net/npm/flowbite@2.5.2/dist/flowbite.min.css" rel="stylesheet"/>

    <style>
        body {
            font-family: 'Inter', sans-serif;
        }

        .section-container {
            @apply bg-gradient-to-b from-red-50 to-white rounded-2xl p-6 shadow-sm;
        }

        .section-title {
            @apply text-2xl md:text-3xl font-extrabold text-gray-800 mb-6 relative inline-block;
        }

        .section-title::after {
            content: "";
            position: absolute;
            left: 0;
            bottom: -6px;
            width: 60%;
            height: 4px;
            background: #ef4444;
            border-radius: 9999px;
        }
    </style>
</head>

<body class="bg-gray-50 min-h-screen flex flex-col">

    <!-- HEADER -->
    <header>
        <jsp:include page="${HeaderComponent}" />
    </header>

    <!-- MAIN CONTENT -->
    <main class="flex-grow container mx-auto px-4 py-6">
        <jsp:include page="${ContentPage}" />
    </main>

    <!-- FOOTER -->
    <footer>
        <jsp:include page="${FooterComponent}" />
    </footer>

    <!-- Scripts -->
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/flowbite@2.5.2/dist/flowbite.min.js"></script>

</body>
</html>