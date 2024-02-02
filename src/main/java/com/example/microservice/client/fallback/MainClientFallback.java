package com.example.microservice.client.fallback;

import com.example.microservice.client.MainClient;
import org.springframework.stereotype.Component;

@Component
public class MainClientFallback implements MainClient {
    @Override
    public boolean checkBearer(String bearerToken) {
        return false;
    }
}
