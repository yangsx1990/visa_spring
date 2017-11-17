package com.aoji.mapper;

import com.aoji.model.SysRolePermission;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface SysRolePermissionMapper extends Mapper<SysRolePermission> {
    /**
     * 根据角色ids查询权限id集合
     * @param roleIds 角色ids
     * @return
     */
    List<Integer> getPermissionIds(List<Integer> roleIds);
}