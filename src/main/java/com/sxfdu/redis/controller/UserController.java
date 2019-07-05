package com.sxfdu.redis.controller;

import com.sxfdu.redis.domain.User;
import com.sxfdu.redis.mapper.UserMapper;
import com.sxfdu.redis.service.RedisService;
import com.sxfdu.redis.service.UserService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author sunxu93@163.com
 * @date 19/7/4/004 20:21
 */

@RestController
public class UserController {
    private static final String key = "userCache_";

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserService userService;

    @Autowired
    private RedisService redisService;

    /**
     * 测试mybatis是否整合成功
     *
     * @param id
     * @return
     */
    @RequestMapping("/getuser")
    @ResponseBody
    public User getUser(@Param("id") String id) {

        User user = userMapper.find(id);
        return user;
    }


    @RequestMapping("/getUserCache")
    @ResponseBody
    public User getUserCache(String id) {
        String cacheKey = key + id;
        User user = (User) redisService.get(cacheKey);
        if (null == user) {
            User userDB = userMapper.find(cacheKey);
            System.out.println("缓存中木有,从数据库中获取数据");
            if (null != userDB) {
                redisService.set(cacheKey, userDB);
            }
            return userDB;
        }
        return user;
    }


    /**
     * 使用了spring 缓存注解
     *
     * @param id
     * @return
     */
    @ResponseBody
    @RequestMapping("getByCache")
    public User getByCache(String id) {
        User user = userService.findById(id);
        return user;
    }

    /**
     * spring缓存注解+keygenerator生成
     *
     * @param id
     * @return
     */
    @RequestMapping("getByCacheUniqueKey")
    @ResponseBody
    public User getByCacheUniqueKey(String id) {
        User user = userService.findByIdTtl(id);
        return user;
    }

}
