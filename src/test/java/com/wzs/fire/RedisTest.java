package com.wzs.fire;

import com.wzs.fire.pojo.entity.Users;
import com.wzs.fire.util.RedisUtil;
import org.apache.catalina.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class RedisTest {
@Autowired
    RedisUtil redisUtil;
    @Test
    public void testAdduserDetail(){
        Users users = new Users();
        users.setUsername("uuuu");
        users.setEmail("123456");
        redisUtil.set("user:1",users);
    }
}