package com.example.blogapi.blog.client;

import com.example.blogapi.blog.config.KakaoFeignClientConfig;
import com.example.blogapi.blog.domain.client.response.kakao.KakaoApiResponse;
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
    KakaoApiResponse search(String query,
                            @RequestParam Integer page,
                            @RequestParam Integer size,
                            @RequestParam String sort
    );

//    @Slf4j
//    @Component
//    class TestFallbackFactory implements FallbackFactory<FallbackWithFactory> {
//
//        @Override
//        public FallbackWithFactory create(Throwable cause) {
//            log.info("[Azure] error occurred, {}", cause.getMessage());
//            log.debug("★ Fallback reason was: " + cause.getMessage());
//            return new FallbackWithFactory();
//        }
//
//    }
//
//    class FallbackWithFactory implements KakaoBlogClient {
//
////        @Override
////        public Hello getHello() {
////            throw new NoFallbackAvailableException("Boom!", new RuntimeException());
////        }
////
////        @Override
////        public String getException() {
////            return "Fixed response";
////        }
//
//        @Override
//        public KakaoApiResponse search(String query, String sort, Integer page, Integer size) {
//            return null;
//        }
//    }
}
