package com.example.blogapi.blog.client;

import com.example.blogapi.blog.config.NaverFeignClientConfig;
import com.example.blogapi.blog.domain.client.response.naver.NaverApiResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(
        name = "NaverBlogClient",
        url = "${blog.api.naver.base-url}",
        configuration = NaverFeignClientConfig.class)
public interface NaverBlogClient {

    @GetMapping(path = "${blog.api.naver.path}")
    NaverApiResponse search(
            @RequestParam String query,
            @RequestParam Integer start,
            @RequestParam Integer display,
            @RequestParam String sort
    );
}
