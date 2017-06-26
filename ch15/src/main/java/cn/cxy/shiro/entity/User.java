package cn.cxy.shiro.entity;

import lombok.*;

import java.io.Serializable;

/**
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
public class User implements Serializable {
    private Long id;
    private String username;
    private String password;
    private String salt;
    private Boolean locked = Boolean.FALSE;

    public String getCredentialsSalt() {
        return username + salt;
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
