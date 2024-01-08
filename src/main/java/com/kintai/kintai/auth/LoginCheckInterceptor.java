package com.kintai.kintai.auth;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;

@Slf4j
public class LoginCheckInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String requestURI = request.getRequestURI();
        if(requestURI.equals("/profile")) {
            return true;
        }

        if (request.getQueryString() != null) {
            requestURI += "?" + request.getQueryString();
        }
        log.info("認証チェック Interceptor 実行 {}", requestURI);

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("Login") == null) {
            log.info("未認証 要請");
            //ログイン画面にリダイレクト
            response.sendRedirect("/login?redirectURL=" + requestURI);
            return false;
        }
        return true;
    }
}
