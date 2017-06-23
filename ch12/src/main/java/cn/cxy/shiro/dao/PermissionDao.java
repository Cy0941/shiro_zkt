package cn.cxy.shiro.dao;

import cn.cxy.shiro.entity.Permission;

/**
 * Function: TODO
 * Reason: TODO ADD REASON(可选).</br>
 * Date: 2017/6/16 13:56 </br>
 *
 * @author: cx.yang
 * @since: Thinkingbar Web Project 1.0
 */
public interface PermissionDao {

    Permission createPermission(Permission permission);

    void deletePermission(Long permissionId);

}
