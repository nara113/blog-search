package com.example.blogapi.keyword.service;

import com.example.blogapi.keyword.domain.PopularKeyword;
import com.example.blogapi.keyword.entity.KeywordCount;
import com.example.blogapi.keyword.repository.KeywordCountRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class PopularKeywordService {
    private final KeywordCountRepository keywordCountRepository;

    @Cacheable(cacheNames = "popularKeywords", key = "#root.method.name")
    public List<PopularKeyword> getTop10PopularKeywords() {
        return keywordCountRepository.findTop10ByOrderByCountDesc()
                .stream()
                .map(PopularKeyword::of)
                .toList();
    }

    @Retryable(
            retryFor = {
                    ObjectOptimisticLockingFailureException.class,
                    DataIntegrityViolationException.class
            },
            backoff = @Backoff(delay = 200))
    @Transactional
    public void upsertKeywordCounts(Map<String, Long> keywordCountMap) {
        List<KeywordCount> keywordCounts = keywordCountMap.keySet()
                .stream()
                .map(keyword -> keywordCountRepository.findKeywordCountByKeyword(keyword)
                        .orElseGet(() -> KeywordCount.of(keyword)))
                .toList();

        keywordCounts.forEach(keywordCount ->
                keywordCount.increaseCount(keywordCountMap.get(keywordCount.getKeyword())));

        keywordCountRepository.saveAll(keywordCounts);
    }

    @Recover
    private void recover(Exception exception,
                         Map<String, Long> keywordCountMap) {
        log.error("키워드 카운트 업데이트에 실패했습니다. keywordCountMap: {}", keywordCountMap, exception);
    }
}
