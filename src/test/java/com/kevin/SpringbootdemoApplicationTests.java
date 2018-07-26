//package com.kevin;
//
//import com.kevin.model.SysUser;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.test.context.junit4.SpringRunner;
//
//@RunWith(SpringRunner.class)
//@SpringBootTest
//public class SpringbootdemoApplicationTests {
//
//    @Autowired
//    private RedisTemplate<String, Object> redisTemplate;
//
//    @Test
//    public void contextLoads() {
//
//        // 存取字符串
//        redisTemplate.opsForValue().set("a", "1");
//        Object a = redisTemplate.opsForValue().get("a");
//        System.out.println(a);
//
//        // 存取user对象
//        SysUser user = new SysUser();
//        user.setUserName("张三");
//        user.setUserPhone("1234567890111");
//        redisTemplate.opsForValue().set("userTest", user);
//        SysUser user1 = (SysUser) redisTemplate.opsForValue().get("userTest");
//        System.out.println(user1);
//    }
//
//
//}
