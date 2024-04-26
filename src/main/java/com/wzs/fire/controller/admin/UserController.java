package com.wzs.fire.controller.admin;

import com.wzs.fire.common.Result;
import com.wzs.fire.pojo.dto.UserDto;
import com.wzs.fire.pojo.dto.UserLoginDto;
import com.wzs.fire.pojo.entity.Users;
import com.wzs.fire.pojo.vo.UserVo;
import com.wzs.fire.service.UserService;
import org.apache.ibatis.annotations.Delete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;
    //登录
    @PostMapping("/login")
    public Result login(@RequestBody Users user) {
        userService.login(user);
        return userService.login(user);
    }
    //退出登录
    @RequestMapping("/logout")
    public Result loginOut(){
        return userService.logout();
    }
    //获取用户信息
    @GetMapping("/getUserInfo")
    public Result<Users> getUserInfo(String username){
        return userService.getUserInfoByUsername(username);
    }
    @GetMapping("/getAllUsers")
    public Result<List<UserDto>> getAllUsers(){
        return userService.getAllUsers();
    }

    @GetMapping("getUserById")
    public Result<UserDto> getUserById(Integer userId){
        return userService.getUserById(userId);
    }

    @PostMapping("/updateUser")
    public Result updateUser(@RequestBody UserVo user){
        return userService.updateUser(user);
    }
    @GetMapping("/getUserAuthority")
    public Result<List<UserVo>> getUserAuthority(){
        return userService.getUserAuthority();
    }
    @PostMapping("/updateUserAuthority")
    public Result updateUserAuthority (@RequestBody UserVo userVo){
        return userService.updateUserAuthority(userVo);
    }

    @PostMapping("/addUser")
    public Result addUser(@RequestBody Users users){
        userService.addUser(users);
        return Result.success();
    }
    @DeleteMapping("/deleteUser")
    public Result deleteUser(Integer userId){
        userService.deleteUserByUserId(userId);
        return Result.success();
    }

}