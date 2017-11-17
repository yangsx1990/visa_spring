package com.aoji.service.impl;

import com.aoji.mapper.SysPermissionMapper;
import com.aoji.mapper.SysRolePermissionMapper;
import com.aoji.mapper.SysUserMapper;
import com.aoji.mapper.SysUserRoleMapper;
import com.aoji.model.*;
import com.aoji.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.List;

/**
 * @author yangsaixing
 * @description 系统用户服务实现类
 * @date Created in 上午9:28 2017/11/17
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    SysUserMapper sysUserMapper;

    @Autowired
    SysUserRoleMapper sysUserRoleMapper;

    @Autowired
    SysRolePermissionMapper sysRolePermissionMapper;

    @Autowired
    SysPermissionMapper sysPermissionMapper;

    @Override
    public SysUser getUserByName(String username) {
        SysUser user=new SysUser();
        user.setUsername(username);
        List<SysUser> userList=sysUserMapper.select(user);
        if(userList.size()>0){
            return userList.get(0);
        }
        return new SysUser();
    }

    @Override
    public List<SysPermission> getFunctionByName(String username) {
        //1、根据用户名查询用户信息
        SysUser user=getUserByName(username);
        if(user==null|| StringUtils.isEmpty(user.getId())){
            throw new RuntimeException("用户信息异常");
        }
        //2、根据用户信息查询角色
        List<Integer> roleIds=getRoleIdsByUserId(user.getId());
        //3、根据角色查询权限
        return getFunctionByRoleIds(roleIds);
    }

    /**
     * 根据角色ids查询权限信息
     * @param roleIds 角色ids
     * @return
     */
    private List<SysPermission> getFunctionByRoleIds(List<Integer> roleIds) {
       List<Integer> permission= sysRolePermissionMapper.getPermissionIds(roleIds);
       Example sysPermissionExample=new Example(SysPermission.class);
       sysPermissionExample.createCriteria().andEqualTo("deleteStatus",0).andIn("id",permission)
       .andEqualTo("level",0);
       sysPermissionExample.orderBy("sort").desc();
       List<SysPermission> permissionParent= sysPermissionMapper.selectByExample(sysPermissionExample);
       for(SysPermission sysPermission:permissionParent){
           Example example=new Example(SysPermission.class);
           example.createCriteria().andEqualTo("deleteStatus",0).andEqualTo("parentId",sysPermission.getId())
                   .andEqualTo("level",1);
           example.orderBy("sort").desc();
           sysPermission.setChildList(sysPermissionMapper.selectByExample(example));
       }
        return  permissionParent;
    }

    /**
     * 根据用户id查询角色信息
     * @param userId 用户id
     * @return
     */
    private List<Integer> getRoleIdsByUserId(Long userId) {
        return sysUserRoleMapper.getRoleIdsByUserId(userId);
    }
}
