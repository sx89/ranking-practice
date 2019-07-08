package com.sxfdu.redis.service;

import com.sxfdu.redis.mapper.ScoreFlowMapper;
import com.sxfdu.redis.mapper.UserMapper;
import com.sxfdu.redis.mapper.UserScoreMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;

import java.util.Set;

/**
 * @author sunxu93@163.com
 * @date 19/7/7/007 17:22
 */
@Service
public class RankingService {

    public static final String RANKING_NAME = "user_score";
    public static final String SALESCORE = "sale_score_rank:";
    @Autowired
    private RedisService redisService;

    public void rankAdd(String uid, Integer score) {
        redisService.zAdd(RANKING_NAME,uid, score);
    }

    public void increScore(String uid, Integer score) {
        redisService.incrementScore(RANKING_NAME, uid, score);
    }

    public Long rankNum(String uid) {
        Long num = redisService.zRank(RANKING_NAME, uid);
        return num;
    }

    public Long getScore(String uid) {
        Double score = redisService.zGetScore(RANKING_NAME, uid);
        return score.longValue();
    }

    public Set<ZSetOperations.TypedTuple<Object>> rankWithScore(Integer start, Integer end) {
        Set<ZSetOperations.TypedTuple<Object>> typedTuples = redisService.zRankWithScore(RANKING_NAME, start, end);
        return typedTuples;
    }
}
