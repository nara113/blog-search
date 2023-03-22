package com.example.blogsearch.blog.client;

import com.example.blogsearch.blog.config.KakaoFeignClientConfig;
import com.example.blogsearch.blog.domain.client.response.kakao.KakaoApiResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(
        name = "kakaoBlogClient",
        url = "${blog.api.kakao.base-url}",
        configuration = KakaoFeignClientConfig.class
)
public interface KakaoBlogClient {

    @GetMapping(path = "${blog.api.kakao.path}")
    KakaoApiResponse search(@RequestParam String query,
                            @RequestParam Integer page,
                            @RequestParam Integer size,
                            @RequestParam String sort
    );
}
