package com.wzs.fire.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.wzs.fire.common.Result;
import com.wzs.fire.mapper.RolesMapper;
import com.wzs.fire.mapper.UserMapper;
import com.wzs.fire.pojo.dto.UserDto;
import com.wzs.fire.pojo.dto.UserLoginDto;
import com.wzs.fire.pojo.entity.Users;
import com.wzs.fire.pojo.vo.UserVo;
import com.wzs.fire.util.JWTUtil;
import com.wzs.fire.util.PasswordEncoderUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class UserServiceImpl implements com.wzs.fire.service.UserService{
@Autowired
private AuthenticationManager authenticationManager;

@Autowired
private UserMapper userMapper;
@Autowired
private RolesMapper rolesMapper;
    @Override
    public Result logout() {
        return Result.success();
    }

    @Override
    public Result login(Users user) {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword());
        Authentication authenticate = authenticationManager.authenticate(usernamePasswordAuthenticationToken);
        //如果认证没有通过
        if (authenticate == null) {
            throw new RuntimeException("认证失败");
        }
        //如果认证通过
        Object principal = authenticate.getPrincipal();
        UserLoginDto userLoginDto = (UserLoginDto) principal;
        String id = userLoginDto.getUser().getUserId().toString();
        int roleId = userLoginDto.getUser().getRoleId();
        String roleName = rolesMapper.getRoleNameByRoleId(roleId);
        String token = JWTUtil.getToken(Long.valueOf(id),roleName);
        String username = userLoginDto.getUser().getUsername();
        String avatarUrl = userLoginDto.getUser().getAvatar();
        HashMap<Object, Object> objectObjectHashMap = new HashMap<>();
        objectObjectHashMap.put("token", token);
        objectObjectHashMap.put("roleName", roleName);
        objectObjectHashMap.put("username",username);
        objectObjectHashMap.put("avatarUrl","http://localhost:83"+avatarUrl);
        return Result.success(200,"登录成功",objectObjectHashMap);
    }

    @Override
    public Result getUserInfoByUsername(String username) {
        Users user = userMapper.getUserInfoByUsername(username);
        return Result.success(200,"success",user);
    }

    @Override
    public Result<List<UserDto>> getAllUsers() {
        List<Users> users = userMapper.selectList(null);
        List<UserDto> userDtos = new ArrayList<>();
        for (Users user : users) {
            UserDto userDto = new UserDto();
            userDto.setUserId(user.getUserId());
            userDto.setEmail(user.getEmail());
            userDto.setUsername(user.getUsername());
            userDto.setCreatedAt(user.getCreatedAt().toString().substring(0, 10));
            userDto.setRoleName(rolesMapper.getRoleNameByRoleId(user.getRoleId()));
            userDtos.add(userDto);
        }
        return Result.success(200,"success",userDtos);
    }

    @Override
    public Result<UserDto> getUserById(Integer userId) {
        Users users = userMapper.getUserByUserId(userId);
        UserDto userDto = new UserDto();
        userDto.setUserId(users.getUserId());
        userDto.setEmail(users.getEmail());
        userDto.setUsername(users.getUsername());
        userDto.setCreatedAt(users.getCreatedAt().toString().substring(0, 10));
        userDto.setRoleName(rolesMapper.getRoleNameByRoleId(users.getRoleId()));
        userDto.setRoleId(users.getRoleId());
        return Result.success(200,"success",userDto);
    }

    @Override
    public Result updateUser(UserVo user) {
        Users users = new Users();
        users.setEmail(user.getEmail());
        users.setUsername(user.getUsername());
        Integer userId = userMapper.getUserIdByUsername(user.getUsername());
        users.setUserId(userId);
        String roleName = user.getRoleName();
        Integer roleId = "user".equals(roleName) ? 2 : ("admin".equals(roleName) ? 1 : null);
        users.setRoleId(roleId);
        userMapper.updateByUserId(users);
        return Result.success();
    }

    @Override
    public Result<List<UserVo>> getUserAuthority() {
        List<Users> users = userMapper.selectList(null);
        List<UserVo> userVos = new ArrayList<>();
        for (Users user : users) {
            Integer roleId = user.getRoleId();
            String roleNameByRoleId = rolesMapper.getRoleNameByRoleId(roleId);
            UserVo userVo = new UserVo();
            userVo.setUserId(user.getUserId());
            userVo.setUsername(user.getUsername());
            userVo.setRoleName(roleNameByRoleId);
            userVos.add(userVo);
        }
        return Result.success(200,"success",userVos);
    }

    @Override
    public Result updateUserAuthority(UserVo userVo) {
        Integer userId = userVo.getUserId();
        String roleName = userVo.getRoleName();
        Integer roleId = "user".equals(roleName) ? 2 : ("admin".equals(roleName) ? 1 : null);
        userMapper.updateUserAuthority(userId,roleId);
        return Result.success();
    }

    @Override
    public void addUser(Users users) {
        String username = users.getUsername();
        String email = users.getEmail();
        Integer roleId = users.getRoleId();
        String password = users.getPassword();
        PasswordEncoderUtil passwordEncoderUtil = new PasswordEncoderUtil();
        String encodedPassword = passwordEncoderUtil.encode(password);
        userMapper.userAdd(username,email,roleId,encodedPassword);
    }

    @Override
    public void deleteUserByUserId(Integer userId) {
        userMapper.deleteById(userId);
    }

}