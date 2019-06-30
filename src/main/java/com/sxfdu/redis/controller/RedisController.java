package com.sxfdu.redis.controller;

import com.sxfdu.redis.service.RedisService;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author sunxu93@163.com
 * @date 19/6/30/030 17:34
 */
@RestController
public class RedisController {

    @Resource
    private RedisTemplate redisTemplate;

    @Resource
    private RedisService redisService;

    @RequestMapping("/redis")


    @ResponseBody
    public String setAndGetValue(String name, String value) {
        redisTemplate.opsForValue().set(name, value);
        return (String) redisTemplate.opsForValue().get(name);
    }

    @ResponseBody
    public String setAndGetValue2(String name, String value) {
        boolean status = redisService.set(name, value);
        return redisService.get(name).toString();
    }
}
