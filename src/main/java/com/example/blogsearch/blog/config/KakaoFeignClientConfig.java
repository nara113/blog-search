package com.example.blogsearch.blog.config;

import feign.RequestInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.openfeign.FeignFormatterRegistrar;
import org.springframework.context.annotation.Bean;
import org.springframework.format.datetime.standard.DateTimeFormatterRegistrar;
import org.springframework.http.HttpHeaders;

public class KakaoFeignClientConfig {

    private final String restApiKey;

    public KakaoFeignClientConfig(@Value("${client.kakao.rest-api-key}") String restApiKey) {
        this.restApiKey = restApiKey;
    }

    @Bean
    public RequestInterceptor requestInterceptor() {
        return requestTemplate ->
                requestTemplate.header(HttpHeaders.AUTHORIZATION, restApiKey);
    }
}
