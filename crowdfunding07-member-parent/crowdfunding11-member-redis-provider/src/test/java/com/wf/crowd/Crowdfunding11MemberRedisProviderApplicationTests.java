package com.wf.crowd;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

@SpringBootTest
class Crowdfunding11MemberRedisProviderApplicationTests {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Test
    void contextLoads() {
    }

    @Test
    public void testRedis(){
        ValueOperations valueOperations = stringRedisTemplate.opsForValue();
        valueOperations.set("apple", "5");
    }

}
