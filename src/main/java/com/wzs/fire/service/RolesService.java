package com.wzs.fire.service;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import com.wzs.fire.mapper.RolesMapper;
import com.wzs.fire.pojo.entity.Roles;
@Service
public class RolesService{

    @Resource
    private RolesMapper rolesMapper;

    
    public int deleteByPrimaryKey(Integer roleId) {
        return rolesMapper.deleteByPrimaryKey(roleId);
    }

    
    public int insert(Roles record) {
        return rolesMapper.insert(record);
    }

    
    public int insertSelective(Roles record) {
        return rolesMapper.insertSelective(record);
    }

    
    public Roles selectByPrimaryKey(Integer roleId) {
        return rolesMapper.selectByPrimaryKey(roleId);
    }

    
    public int updateByPrimaryKeySelective(Roles record) {
        return rolesMapper.updateByPrimaryKeySelective(record);
    }

    
    public int updateByPrimaryKey(Roles record) {
        return rolesMapper.updateByPrimaryKey(record);
    }

}
