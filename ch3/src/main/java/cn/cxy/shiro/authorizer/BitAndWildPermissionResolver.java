package cn.cxy.shiro.authorizer;

import cn.cxy.shiro.permission.BitPermission;
import org.apache.shiro.authz.Permission;
import org.apache.shiro.authz.permission.PermissionResolver;
import org.apache.shiro.authz.permission.WildcardPermission;

/**
 * Function: 自定义实现权限解析器
 * Reason: TODO
 * Date: 2017/6/15 10:38 </br>
 *
 * @author: cx.yang
 * @since: Thinkingbar Web Project 1.0
 */
public class BitAndWildPermissionResolver implements PermissionResolver {

    public Permission resolvePermission(String permissionString) {
        if (permissionString.startsWith("+")) {
            return new BitPermission(permissionString);
        }
        return new WildcardPermission(permissionString);
    }
}
