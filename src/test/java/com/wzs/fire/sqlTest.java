package com.wzs.fire;

import com.wzs.fire.mapper.UsersMapper;
import com.wzs.fire.pojo.entity.Users;
import com.wzs.fire.service.FeedbackService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class sqlTest {
    @Autowired
    UsersMapper usersMapper;
    @Autowired
    FeedbackService feedbackService;
    @Test
    public void test() {
        Users users = usersMapper.selectByPrimaryKey(1);
        System.out.println(users);
    }
    @Test
    public void getCount(){
        System.out.println(feedbackService.getUnResponsesCount());
        System.out.println(feedbackService.getHasResponseSCount());
    }
}
