package cn.cxy.shiro.service;


import cn.cxy.shiro.entity.Permission;

/**
 * <p>User: Zhang Kaitao
 * <p>Date: 14-1-28
 * <p>Version: 1.0
 */
public interface PermissionService {
    Permission createPermission(Permission permission);

    void deletePermission(Long permissionId);
}
