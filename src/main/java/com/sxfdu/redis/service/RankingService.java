package com.sxfdu.redis.service;

import com.sxfdu.redis.mapper.ScoreFlowMapper;
import com.sxfdu.redis.mapper.UserMapper;
import com.sxfdu.redis.mapper.UserScoreMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ZSetOperations;

import java.util.Set;

/**
 * @author sunxu93@163.com
 * @date 19/7/7/007 17:22
 */
public class RankingService {


    private static final String RANKGNAME = "user_score";

    private static final String SALESCORE = "sale_score_rank:";

    @Autowired
    private RedisService redisService;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private ScoreFlowMapper scoreFlowMapper;

    @Autowired
    private UserScoreMapper userScoreMapper;


    public void rankAdd(String uid, Integer score) {
        redisService.zAdd(RANKGNAME, uid, score);
    }

    public void increSocre(String uid, Integer score) {

        redisService.incrementScore(RANKGNAME, uid, score);
    }

    public Long rankNum(String uid) {
        return redisService.zRank(RANKGNAME, uid);
    }

    public Long score(String uid) {
        Long score = redisService.zSetScore(RANKGNAME, uid).longValue();
        return score;
    }

    public Set<ZSetOperations.TypedTuple<Object>> rankWithScore(Integer start, Integer end) {
        return redisService.zRankWithScore(RANKGNAME, start, end);
    }
}
