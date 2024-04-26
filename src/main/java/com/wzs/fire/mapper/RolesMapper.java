package com.wzs.fire.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wzs.fire.pojo.entity.Roles;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface RolesMapper extends BaseMapper {
    int deleteByPrimaryKey(Integer roleId);

    int insert(Roles record);

    int insertSelective(Roles record);

    Roles selectByPrimaryKey(Integer roleId);

    int updateByPrimaryKeySelective(Roles record);

    int updateByPrimaryKey(Roles record);

    @Select("select role_name from roles where role_id = #{roleId}")
    String getRoleNameByRoleId(int roleId);
}