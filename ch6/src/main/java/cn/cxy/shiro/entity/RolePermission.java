package cn.cxy.shiro.entity;

import lombok.*;

import java.io.Serializable;

/**
 * 用户角色关系
 * <p>User: Zhang Kaitao
 * <p>Date: 14-1-28
 * <p>Version: 1.0
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class RolePermission implements Serializable {

    private Long roleId;
    private Long permissionId;

}
