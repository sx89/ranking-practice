package com.sxfdu.redis.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * 可以把请求的sessionId存放进redis
 * 也可以把它取出来
 * @author sunxu93@163.com
 * @date 19/7/7/007 16:48
 */
@RestController
public class SessionController {

    @RequestMapping(value = "/setSession", method = RequestMethod.GET)
    public Map<String, Object> setSession (HttpServletRequest request){
        Map<String, Object> map = new HashMap<>();
        request.getSession().setAttribute("request Url", request.getRequestURL());
        map.put("request Url", request.getRequestURL());
        return map;
    }

    @RequestMapping(value = "/getSession", method = RequestMethod.GET)
    public Object getSession (HttpServletRequest request){
        Map<String, Object> map = new HashMap<>();
        map.put("sessionIdUrl",request.getSession().getAttribute("request Url"));
        map.put("sessionId", request.getSession().getId());
        return map;
    }
}
