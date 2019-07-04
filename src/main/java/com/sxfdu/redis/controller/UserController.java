package com.sxfdu.redis.controller;

import com.sxfdu.redis.domain.User;
import com.sxfdu.redis.mapper.UserMapper;
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
    @Autowired
    private UserMapper userMapper;

    /**
     * 测试mybatis是否整合成功
     * @param id
     * @return
     */
    @RequestMapping("/getuser")
    @ResponseBody
    public User getUser(@Param("id")String id) {

        User user = userMapper.find(id);
        return user;
    }
}
