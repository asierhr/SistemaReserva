package com.asier.SistemaReservas.system.RateLimiting;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class RateLimitInterceptor implements HandlerInterceptor{
    private final RateLimitingService rateLimitingService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler){
        String uri = request.getRequestURI();
        String userId = request.getHeader("X-User-Id");
        String ip = request.getRemoteAddr();

        String keyBase = (userId != null)? userId : ip;

        if (uri.matches(".*/hotels/.*/rooms/reservation")) {
            rateLimitingService.checkRateLimit(
                "rate:hotel:" + keyBase,
                3,
                60
            );
        }

        if (uri.matches(".*/flights-reservation/.*/seats")) {
            rateLimitingService.checkRateLimit(
                "rate:flight:" + keyBase,
                3,
                60
            );
        }

        return true;
    }
}
