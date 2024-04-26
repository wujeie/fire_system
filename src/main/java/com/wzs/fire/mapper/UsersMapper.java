package com.wzs.fire.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wzs.fire.pojo.entity.Users;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UsersMapper extends BaseMapper {
    int deleteByPrimaryKey(Integer userId);

    int insert(Users record);

    int insertSelective(Users record);

    Users selectByPrimaryKey(Integer userId);

    int updateByPrimaryKeySelective(Users record);

    int updateByPrimaryKey(Users record);

    int getRoleIdByUserId(Integer userId);
}