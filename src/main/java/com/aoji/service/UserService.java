package com.aoji.service;

import com.aoji.model.SysPermission;
import com.aoji.model.SysUser;

import java.util.List;

/**
 * @author yangsaixing
 * @description
 * @date Created in 上午9:27 2017/11/17
 */
public interface UserService {

    /**
     * 根据用户名查询用户信息
     * @param username 用户名
     * @return
     */
    SysUser getUserByName(String username);

    /**
     * 根据用户名查询用户权限
     * @param username
     * @return
     */
    List<SysPermission> getFunctionByName(String username);

}
