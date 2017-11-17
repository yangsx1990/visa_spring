package com.aoji.mapper;

import com.aoji.model.SysUserRole;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface SysUserRoleMapper extends Mapper<SysUserRole> {
    /**
     * 根据用户id查询用户角色ids
     * @param userId 用户id
     * @return
     */
    List<Integer> getRoleIdsByUserId(Long userId);
}