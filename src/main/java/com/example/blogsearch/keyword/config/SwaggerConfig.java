package com.example.blogapi.keyword.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(
        info = @Info(title = "블로그 검색 서비스 API"))
@RequiredArgsConstructor
@Configuration
public class SwaggerConfig {
}
