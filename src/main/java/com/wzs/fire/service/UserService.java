package com.wzs.fire.service;

import com.wzs.fire.common.Result;
import com.wzs.fire.pojo.dto.UserDto;
import com.wzs.fire.pojo.dto.UserLoginDto;
import com.wzs.fire.pojo.entity.Users;
import com.wzs.fire.pojo.vo.UserVo;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface UserService {
     Result logout();

    Result login(Users user);

    Result getUserInfoByUsername(String username);

    Result<List<UserDto>> getAllUsers();

    Result<UserDto> getUserById(Integer userId);

    Result updateUser(UserVo user);

    Result<List<UserVo>> getUserAuthority();

    Result updateUserAuthority(UserVo userVo);

    void addUser(Users users);

    void deleteUserByUserId(Integer userId);
}
