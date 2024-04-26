package com.wzs.fire;

import com.mysql.cj.util.StringUtils;
import com.wzs.fire.mapper.TestpaperquestionsMapper;
import com.wzs.fire.mapper.UserMapper;
import com.wzs.fire.pojo.entity.Users;
import com.wzs.fire.util.JWTUtil;
import io.jsonwebtoken.Claims;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Date;
import java.util.List;

@SpringBootTest
public class MapperTest {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    TestpaperquestionsMapper testpaperquestionsMapper;
    @Test
    public void testGetAllUser(){
        List<Users> users = userMapper.selectList(null);
        System.out.println(users
        );
    }
    @Test
    public void BCryptPasswordTest(){
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        String encode = bCryptPasswordEncoder.encode("123456");
        System.out.println(encode);
//        $2a$10$meixulgCtIJ/KDhziIahUu5KtBxhhi53e5r0QIsglcdtNfrMbZ3EK
        boolean matches = bCryptPasswordEncoder.matches(
                "123456",
                "$2a$10$meixulgCtIJ/KDhziIahUu5KtBxhhi53e5r0QIsglcdtNfrMbZ3EK"
        );
        System.out.println(matches);
    }
    @Test
    public void JWTUtiltest(){
//        String token = "eyJhbGciOiJIUzUxMiIsInppcCI6IkdaSVAifQ.H4sIAAAAAAAA_y2LXQqAIBCE77LPCa6lS91GaQWDQliDIrp7K_Qww_zwPbC1AgtQQKYcgsmZ2EyzRZOcWuZkE_nVIVkYoMQGCxI6Gmfr_QByJqXllsZ7_0W0HiUeXTrEc9Uh1qqZr_rDkwsdLvrh-wGUhvxEgwAAAA.pLAnw1cpNbrqGhulAwwfx1EZ0jGbO0mkwatOuVcjyqe7Ok8NjhC9vIlrT1_bteP8wjp-eoZ70R-U66sRiJJO_Q";
//        Claims claimsBody = JWTUtil.getClaimsBody(token);
//        System.out.println(claimsBody);
//        Integer id = (Integer) claimsBody.get("userId");
//        System.out.println(id);
//        String token = "1111111111";
//        if (!StringUtils.isNullOrEmpty(token)){
//            //解析token
//            System.out.println("11111111111111111111111");
//        }
        String token  = "eyJhbGciOiJIUzUxMiIsInppcCI6IkdaSVAifQ.H4sIAAAAAAAA_y2LWwrDIBAA77LfEdTEiLlBf3qHNa5gqW3IRvqid-8K_RuGmQ9cjgILWJM1RkwqW1zVZLRXcXWk5slpF8aQfZpggIIHLMYbG6zXbh6AW5Q7l50Uv_ig2iNmcY83C2NLwrhtwvTc_vOo5z7v9yudsVIvUi03aRrTfpLFfH9Ov_7umgAAAA.vA1W0U0JU5zfRU9bfNbp8NpyVrWfxB4arsa4_kgnMx_L1yKOb_4mlPUuDKsD1sfb4X4wsPmzaMSJwEqSJuqREw";
        Claims claimsBody = JWTUtil.getClaimsBody(token);
        Object roleName = claimsBody.get("roleName");
        System.out.println(roleName);
    }
    @Test
    public void paperquestionTest(){
        List<Integer> paperIdByQuestion = testpaperquestionsMapper.getPaperIdByQuestion(1);
        System.out.println(paperIdByQuestion);
    }
}
