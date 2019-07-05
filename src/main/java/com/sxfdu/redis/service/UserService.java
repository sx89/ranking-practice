package com.sxfdu.redis.service;

import com.sxfdu.redis.domain.User;
import com.sxfdu.redis.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

/**
 * @author sunxu93@163.com
 * @date 19/7/5/005 10:54
 */
@Service

@CacheConfig(cacheNames = "userInfoCache") // 本类内方法指定使用缓存时，默认的名称就是userInfoCache
@Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
public class UserService {
    @Autowired
    private UserMapper userMapper;


    // 因为必须要有返回值，才能保存到数据库中，如果保存的对象的某些字段是需要数据库生成的，
    //那保存对象进数据库的时候，就没必要放到缓存了
    @CachePut(key = "#p0.id")  //#p0表示第一个参数
    //必须要有返回值，否则没数据放到缓存中
    public User insertUser(User u) {
        this.userMapper.insert(u);
        //u对象中可能只有只几个有效字段，其他字段值靠数据库生成，比如id
        return this.userMapper.find(u.getId());
    }


    @CachePut(key = "#p0.id")
    public User updateUser(User u) {
        this.userMapper.update(u);
        //可能只是更新某几个字段而已，所以查次数据库把数据全部拿出来全部
        return this.userMapper.find(u.getId());
    }

    @Nullable
    @Cacheable(key = "#p0") // @Cacheable 会先查询缓存，如果缓存中存在，则不执行方法
    public User findById(String id) {
        System.err.println("根据id=" + id + "获取用户对象，从数据库中获取");
        Assert.notNull(id, "id不用为空");
        return this.userMapper.find(id);
    }


    @CacheEvict(key = "#p0")  //删除缓存名称为userInfoCache,key等于指定的id对应的缓存
    public void deleteById(String id) {
        this.userMapper.delete(id);
    }

    //清空缓存名称为userInfoCache（看类名上的注解)下的所有缓存
    //如果数据失败了，缓存时不会清除的
    @CacheEvict(allEntries = true)
    public void deleteAll() {
        this.userMapper.deleteAll();
    }

    /**
     * 最终key的形式是:
     * value :: keyGenerator  组成的key:
     * UserInfoList::UserService.findByIdTtl[2]
     *
     * @param id
     * @return
     */
    @Nullable
    @Cacheable(value = "UserInfoList", keyGenerator = "simpleKeyGenerator") // @Cacheable 会先查询缓存，如果缓存中存在，则不执行方法
    public User findByIdTtl(String id) {
        System.err.println("根据id=" + id + "获取用户对象，从数据库中获取");
        Assert.notNull(id, "id不用为空");
        return this.userMapper.find(id);
    }

}
