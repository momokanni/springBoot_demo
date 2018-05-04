package com.txhl.wxorder.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.simpleframework.xml.core.Commit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 * redis分布式锁
 *
 * @author Administrator
 * @create 2018-05-02 17:37
 */
@Component
@Slf4j
public class RedisLock {

    @Autowired
    private StringRedisTemplate redisTemplate;

    /**
     * @param: key,value(当前时间+超时时间)
     * describe: 加锁
     * creat_user: sl
     * creat_date: 2018/5/2
     * creat_time: 17:45
     **/
    public boolean lock(String key,String value){
        log.info("【redis分布式锁】 初始值：{},{}",key,value);
        boolean status = (redisTemplate.opsForValue().setIfAbsent(key, value));
        log.info("【redis分布式锁】 加锁初始判断：{}",status);
        if (status){
            log.info("【redis分布式锁】 进入IF判断");
            return true;
        }

        String currentValue = redisTemplate.opsForValue().get(key);
        log.info("【redis分布式锁】 加锁当前时间：{}",currentValue);
        //判断锁是否过期
        if(!StringUtils.isEmpty(currentValue) && Long.parseLong(currentValue) < System.currentTimeMillis()){
            //获取上一个锁的时间
            String oldValue = redisTemplate.opsForValue().getAndSet(key,value);
            log.info("【redis分布式锁】 加锁上个时间：{}",oldValue);
            if (!StringUtils.isEmpty(oldValue) && oldValue.equals(currentValue)){
                return true;
            }
        }
        return false;
    }

    /**
     * @param: key，value
     * describe: 解锁
     * creat_user: sl
     * creat_date: 2018/5/2
     * creat_time: 19:58
     **/
    public void unlock(String key,String value){
        try {
            String currentValue = redisTemplate.opsForValue().get(key);
            if (!StringUtils.isEmpty(currentValue) && currentValue.equals(value)){
                redisTemplate.opsForValue().getOperations().delete(key);
            }
        } catch (Exception e){
            log.error("【redis分布式锁】 解锁异常 {}",e);
        }
    }

}
