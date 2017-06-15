package cn.cxy.shiro.authorizer;

import org.apache.shiro.authz.Permission;
import org.apache.shiro.authz.permission.PermissionResolver;

/**
 * Function: 自定义实现权限解析器
 * Reason: TODO ADD REASON(可选).</br>
 * Date: 2017/6/15 10:38 </br>
 *
 * @author: cx.yang
 * @since: Thinkingbar Web Project 1.0
 */
public class BitAndWildPermissionResolver implements PermissionResolver {
    public Permission resolvePermission(String permissionString) {
        return null;
    }
}
