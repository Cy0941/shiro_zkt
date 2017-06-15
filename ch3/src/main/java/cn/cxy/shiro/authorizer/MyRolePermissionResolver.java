package cn.cxy.shiro.authorizer;

import org.apache.shiro.authz.Permission;
import org.apache.shiro.authz.permission.RolePermissionResolver;
import org.apache.shiro.authz.permission.WildcardPermission;

import java.util.Arrays;
import java.util.Collection;

/**
 * Function: 自定义实现角色->权限解析
 * Reason: TODO ADD REASON(可选).</br>
 * Date: 2017/6/15 10:41 </br>
 *
 * @author: cx.yang
 * @since: Thinkingbar Web Project 1.0
 */
public class MyRolePermissionResolver implements RolePermissionResolver {
    public Collection<Permission> resolvePermissionsInRole(String roleString) {
        if ("role1".equals(roleString)) {
            return Arrays.asList((Permission) new WildcardPermission("menu:*"));
        }
        return null;
    }
}
