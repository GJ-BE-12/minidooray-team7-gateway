package com.nhnacademy.spring_boot_jpa.config;

import org.springframework.web.servlet.HandlerInterceptor;



import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

//선택 사항이지만, Gateway에서 세션 로그인 확인을 깔끔하게 할 수 있음.

public class SessionCheckInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {

        HttpSession session = request.getSession(false);

        // 로그인 안 된 경우
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect("/login");
            return false;
        }

        // 로그인 되어 있으면 통과
        return true;
    }
}
