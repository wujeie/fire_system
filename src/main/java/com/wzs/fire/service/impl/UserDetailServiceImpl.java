package com.wzs.fire.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wzs.fire.mapper.RolesMapper;
import com.wzs.fire.mapper.UserMapper;
import com.wzs.fire.pojo.dto.UserLoginDto;
import com.wzs.fire.pojo.entity.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class UserDetailServiceImpl implements UserDetailsService {
    @Autowired
    UserMapper userMapper;
    @Autowired
    RolesMapper rolesMapper;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //查询用户信息
        QueryWrapper<Users> usersQueryWrapper = new QueryWrapper<>();
        usersQueryWrapper.eq("username",username);
        Users user = userMapper.selectOne(usersQueryWrapper);
        //如果没有查询到，抛出异常
        if(user == null){
            throw new UsernameNotFoundException("用户不存在");
        }
        //查询用户权限
        String roleNameByRoleId = rolesMapper.getRoleNameByRoleId(user.getUserId());
        List<String> authorities = Arrays.asList("roleNameByRoleId");
        //封装成UserDetails返回
        return new UserLoginDto(user,authorities);
    }
}
