package com.sxfdu.redis.service;

import com.sxfdu.redis.domain.ScoreFlow;
import com.sxfdu.redis.domain.User;
import com.sxfdu.redis.domain.UserScore;
import com.sxfdu.redis.domain.UserScoreExample;
import com.sxfdu.redis.mapper.ScoreFlowMapper;
import com.sxfdu.redis.mapper.UserMapper;
import com.sxfdu.redis.mapper.UserScoreMapper;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author sunxu93@163.com
 * @date 19/7/7/007 17:22
 */
@Service
public class RankingService implements InitializingBean {

    public static final String RANKING_NAME = "user_score";
    public static final String SALE_SCORE = "sale_score_rank";
    @Autowired
    private RedisService redisService;

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserScoreMapper userScoreMapper;
    @Autowired
    private ScoreFlowMapper scoreFlowMapper;
    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 只是往redis中存放数据,没有与数据库关联起来,
     */
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

    /**
     * 与数据库关联
     * 增删改数据,数据库和redis都进行数据改变
     * 查数据从redis中取出来(排行榜本来也是查要远远多于增删改
     * <p>
     * 关于查数据,其实健全的逻辑是,如果redis查不到,将从DB中查找,但是可能会导致缓存雪崩问题(sql承受不住大量跳过redis的请求),所以暂时
     * 不使用sql
     */

    /**
     * 从数据库获取用户积分数据
     * 写到redis的zset里面
     */
    public void rankSaleAdd() {
        UserScoreExample userScoreExample = new UserScoreExample();
        userScoreExample.setOrderByClause("id desc");
        List<UserScore> userScores = userScoreMapper.selectByExample(userScoreExample);
        userScores.forEach(userScore -> {
            String key = userScore.getUserId() + ":" + userScore.getName();
            Long userScore1 = userScore.getUserScore();
            redisService.zAdd(SALE_SCORE, key, userScore1);
        });
    }

    /**
     * 把uid对应的score 增加一些值
     * 写入流水线表和用户积分表
     * 同时在redis里给对应用户增加积分
     */
    public void increSaleScore(String uid, Integer score) {
        User user = userMapper.find(uid);
        if (user == null) {
            return;
        }
        int uidInt = Integer.parseInt(uid);
        long scoreLong = Long.parseLong(score + "");
        String name = user.getUserName();
        String key = uid + ":" + name;
        scoreFlowMapper.insertSelective(new ScoreFlow(scoreLong, name, uidInt));
        userScoreMapper.insertSelective(new UserScore(uidInt, scoreLong, name));
        redisService.incrementScore(SALE_SCORE, key, score);
    }

    public Map<String, Object> userRank(String uid, String name) {

        //存储在linkedmap里面,既可以迅速读取,又可以按照插入顺序读取
        LinkedHashMap<String, Object> objectObjectLinkedHashMap = new LinkedHashMap<>();
        String key = uid + ":" + name;
        ZSetOperations zSetOperations = redisTemplate.opsForZSet();
        Long rank = zSetOperations.rank(SALE_SCORE, key);
        Double score = zSetOperations.score(SALE_SCORE, key);
        objectObjectLinkedHashMap.put("userId", uid);
        objectObjectLinkedHashMap.put("score", score);
        objectObjectLinkedHashMap.put("rank", rank);

//          //遍历linkedmap
//
//        for (Map.Entry entry : objectObjectLinkedHashMap.entrySet()) {
//            System.out.println(entry);
//        }
        return objectObjectLinkedHashMap;
    }

    public List<Map<String, Object>> reverseZRankWithRank(long start, long end) {
        Set<ZSetOperations.TypedTuple<Object>> setObj = redisService.reverseZRankWithRank(SALE_SCORE, start, end);
        List<Map<String, Object>> mapList = setObj.stream().map(objectTypedTuple -> {
            Map<String, Object> map = new LinkedHashMap<>();
            map.put("userId", objectTypedTuple.getValue().toString().split(":")[0]);
            map.put("userName", objectTypedTuple.getValue().toString().split(":")[1]);
            map.put("score", objectTypedTuple.getScore());
            return map;
        }).collect(Collectors.toList());
        return mapList;
    }

    public List<Map<String, Object>> saleRankWithScore(Integer start, Integer end) {
        Set<ZSetOperations.TypedTuple<Object>> setObj = redisService.reverseZRankWithScore(SALE_SCORE, start, end);
        List<Map<String, Object>> mapList = setObj.stream().map(objectTypedTuple -> {
            Map<String, Object> map = new LinkedHashMap<>();
            map.put("userId", objectTypedTuple.getValue().toString().split(":")[0]);
            map.put("userName", objectTypedTuple.getValue().toString().split(":")[1]);
            map.put("score", objectTypedTuple.getScore());
            return map;
        }).collect(Collectors.toList());
        return mapList;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        /**
         * "实现预热加载,initialBean接口的实现方法afterPropertiesSet()  会在初始化bean的所有属性之后运行,
         * 这个时候,springapplication.run还没有运行,就可以保证  在接受请求之前(springapplication运行之前)
         * 就通过this.rankSaleAdd()把数据加载到redis,"
         */
        this.rankSaleAdd();
    }
}
