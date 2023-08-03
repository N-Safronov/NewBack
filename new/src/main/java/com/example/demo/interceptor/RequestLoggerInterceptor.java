package com.example.demo.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@Component
public class RequestLoggerInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        // Логирование перед обработкой запроса контроллером (preHandle)
        String requestURL = request.getRequestURL().toString();
        System.out.println("Received request: " + requestURL);
        return true; // Возвращаем true, чтобы продолжить выполнение запроса
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {
        // Логирование после обработки запроса контроллером (postHandle)
        // Можно добавить логирование тела ответа, если необходимо
    }
}

