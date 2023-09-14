package com.avanade.adnd.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/health")
@Tag(name = "Health Check", description = "Health Check API")
public class HealthCheckController {

    @GetMapping()
    public String healthCheck() {
        return "OK";
    }

}
