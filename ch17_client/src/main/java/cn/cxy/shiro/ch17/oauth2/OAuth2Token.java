package cn.cxy.shiro.ch17.oauth2;

import lombok.Getter;
import lombok.Setter;
import org.apache.shiro.authc.AuthenticationToken;

/**
 * Function: TODO
 * Reason: TODO ADD REASON(可选).</br>
 * Date: 2017/7/1 14:53 </br>
 *
 * @author: cx.yang
 * @since: Thinkingbar Web Project 1.0
 */

@Setter
@Getter
public class OAuth2Token implements AuthenticationToken {

    private String authCode;
    private String principal;

    public OAuth2Token(String authCode){
        this.authCode = authCode;
    }

    public Object getPrincipal() {
        return principal;
    }

    public Object getCredentials() {
        return authCode;
    }
}
