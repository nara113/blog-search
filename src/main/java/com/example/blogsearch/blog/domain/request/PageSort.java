package com.example.blogapi.blog.domain.request;

import lombok.Getter;

import java.util.Arrays;
import java.util.Map;

import static java.util.stream.Collectors.toMap;

@Getter
public enum PageSort {
    ACCURACY("accuracy", "sim"),
    RECENCY("recency", "date");

    private final String kakaoSort;
    private final String naverSort;

    private static final Map<String, PageSort> pageSortMap =
            Arrays.stream(values())
                    .collect(toMap(PageSort::getKakaoSort, pageSort -> pageSort));

    PageSort(String kakaoSort, String naverSort) {
        this.kakaoSort = kakaoSort;
        this.naverSort = naverSort;
    }

    public static PageSort of(String kakaoSort) {
        if (kakaoSort == null) {
            return ACCURACY;
        }

        return pageSortMap.getOrDefault(kakaoSort, ACCURACY);
    }
}
