package aiss.videominer.controller;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
@Component
public class Filter extends OncePerRequestFilter {
    @Value("{videominer.api.key}")
    private  String apiKey;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        if (request.getRequestURI().startsWith("/h2-ui")) {
            filterChain.doFilter(request, response);
            return;
        }

        String requestKey = request.getHeader("X-API-KEY");
        if (apiKey.equals(requestKey)){
            filterChain.doFilter(request,response);
        }else{
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("aplication/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write("{\"error\": \"Acceso denegado. API Key inválida o ausente.\"}");
        }

    }
}
