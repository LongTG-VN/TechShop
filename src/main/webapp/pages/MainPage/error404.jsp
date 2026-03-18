
<body class="bg-slate-50 dark:bg-slate-900 flex items-center justify-center min-h-screen overflow-hidden">
    <div class="flex flex-col items-center justify-center w-full max-w-2xl px-4 text-center mx-auto">
        
        <div class="relative flex items-center justify-center mb-8">
            <h1 class="text-[10rem] md:text-[14rem] font-black text-slate-200 dark:text-slate-800 leading-none select-none">
                404
            </h1>
            <div class="absolute">
                <svg class="w-20 h-20 md:w-28 md:h-28 text-indigo-600 animate-bounce" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                    <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M12 19l9 2-9-18-9 18 9-2zm0 0v-8" />
                </svg>
            </div>
        </div>

        <div class="z-10">
            <h2 class="text-3xl font-bold text-slate-800  md:text-5xl mb-4 tracking-tight">
                Oops! Page not found
            </h2>
            <p class="text-base md:text-lg text-slate-600 dark:text-slate-400 mb-10 max-w-md mx-auto leading-relaxed">
                The page you are looking for might have been removed, had its name changed, or is temporarily unavailable.
            </p>

            <div class="flex flex-col sm:flex-row gap-4 justify-center items-center">
                <a href="${pageContext.request.contextPath}/userservlet?action=homePage" 
                   class="w-full sm:w-auto px-8 py-4 bg-indigo-600 text-white font-bold rounded-2xl shadow-xl shadow-indigo-200 dark:shadow-none hover:bg-indigo-700 transition-all duration-300 transform hover:-translate-y-1">
                    Back to Homepage
                </a>
                <a href="javascript:history.back()" 
                   class="w-full sm:w-auto px-8 py-4 bg-white dark:bg-slate-800 text-slate-700 dark:text-slate-200 font-bold rounded-2xl border border-slate-200 dark:border-slate-700 hover:bg-slate-50 dark:hover:bg-slate-700 transition-all duration-300">
                    Go Back
                </a>
            </div>
        </div>
    </div>
</body>