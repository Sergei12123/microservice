package com.example.microservice.client;

import com.example.microservice.client.fallback.MainClientFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "main-service", fallback = MainClientFallback.class)
public interface MainClient {

    @GetMapping("/bearer/check")
    boolean checkBearer(@RequestHeader("Authorization") String bearerToken);

}
