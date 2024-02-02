package com.example.microservice.component;

import com.example.microservice.client.MainClient;
import com.example.microservice.exception.IncorrectBearerException;
import com.example.microservice.exception.enums.IncorrectBearerType;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@Component
public class RequestResponseFilter extends OncePerRequestFilter {

    private final MainClient mainClient;

    public RequestResponseFilter(MainClient mainClient) {
        this.mainClient = mainClient;
    }

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain) throws ServletException, IOException {
        logTransactionInfo(request);
        if (request.getHeader("Authorization") == null) {
            throw new IncorrectBearerException(IncorrectBearerType.NULL);
        }

        if (mainClient.checkBearer(request.getHeader("Authorization"))) {
            filterChain.doFilter(request, response);
        } else {
            throw new IncorrectBearerException(IncorrectBearerType.INCORRECT);
        }
        logTransactionCompletionInfo(response);
    }

    private void logTransactionInfo(HttpServletRequest request) {
        log.info("Send {} request for endpoint {}",request.getMethod(), request.getRequestURI());
    }

    private void logTransactionCompletionInfo(HttpServletResponse response) {
        log.info("Request completed with status {}",response.getStatus());

    }
}
