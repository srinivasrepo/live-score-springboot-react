package com.konasl.livescore.service.impl;

import com.konasl.livescore.configuration.mapper.MapperRegistry;
import com.konasl.livescore.dto.LiveScoreQuery;
import com.konasl.livescore.dto.LiveScoreRequest;
import com.konasl.livescore.dto.LiveScoreResponse;
import com.konasl.livescore.entity.LiveScore;
import com.konasl.livescore.repository.LiveScoreRepository;
import com.konasl.livescore.service.LiveScoreService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class LiveScoreServiceImpl implements LiveScoreService {

    private final LiveScoreRepository liveScoreRepository;
    private final MapperRegistry mapperRegistry;

    @Override
    public void saveLiveScore(final LiveScore liveScore) {
        log.info("Updated score :: {}", liveScore.getTitle());
        liveScoreRepository.findLiveScoreByLinkAndUri(liveScore.getLink(), liveScore.getUri()).ifPresent(existingLiveScore -> liveScore.setId(existingLiveScore.getId()));
        liveScoreRepository.save(liveScore);
    }

    @Override
    public Page<LiveScoreResponse> findLiveScores(final LiveScoreRequest liveScoreRequest) {
        return liveScoreRepository.findLiveScores(mapperRegistry.getMapper(LiveScoreRequest.class, LiveScoreQuery.class).map(liveScoreRequest), PageRequest.of(liveScoreRequest.getPageNumber(), liveScoreRequest.getPageSize())).map(mapperRegistry.getMapper(LiveScore.class, LiveScoreResponse.class)::map);
    }
}