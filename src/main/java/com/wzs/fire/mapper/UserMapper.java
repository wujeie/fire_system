package com.wzs.fire.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wzs.fire.pojo.dto.UserDto;
import com.wzs.fire.pojo.entity.Users;
import org.apache.ibatis.annotations.*;

@Mapper
public interface UserMapper extends BaseMapper<Users>{
    @Select("select role_id from users where user_id = #{userId}")
    int getRoleIdByUserId(Integer userId);

    @Select("select * from users where username = #{username}")
    Users getUserInfoByUsername(String username);

    @Select("select * from users where user_id = #{userId}")
    Users getUserByUserId(Integer userId);

    @Select("update users set username = #{username}, email = #{email} where user_id = #{userId}")
    void updateByUserId(Users users);

    @Update("update users set role_id = #{roleId} where user_id = #{userId}")
    void updateUserAuthority(@Param("userId") Integer userId, @Param("roleId") Integer roleId);

    @Insert("insert into users (username,role_id,email,password) values (#{username}, #{roleId}, #{email},#{password})")
    void userAdd(@Param("username") String username, @Param("email") String email,@Param("roleId") Integer roleId,@Param("password") String password);

    @Select("select user_id from users where username = #{username}")
    Integer getUserIdByUsername(String username);
}