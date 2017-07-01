package cn.cxy.shiro.ch17.web.controller;

import cn.cxy.shiro.ch17.Constants;
import cn.cxy.shiro.ch17.service.OAuthService;
import org.apache.oltu.oauth2.as.issuer.MD5Generator;
import org.apache.oltu.oauth2.as.issuer.OAuthIssuer;
import org.apache.oltu.oauth2.as.issuer.OAuthIssuerImpl;
import org.apache.oltu.oauth2.as.request.OAuthTokenRequest;
import org.apache.oltu.oauth2.as.response.OAuthASResponse;
import org.apache.oltu.oauth2.common.OAuth;
import org.apache.oltu.oauth2.common.error.OAuthError;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.message.OAuthResponse;
import org.apache.oltu.oauth2.common.message.types.GrantType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Function: cxy 访问令牌控制器 - 根据授权码拿到AccessToken
 * Reason: TODO ADD REASON(可选).</br>
 * Date: 2017/7/1 11:16 </br>
 *
 * @author: cx.yang
 * @since: Thinkingbar Web Project 1.0
 */

@Controller
public class AccessTokenController {

    @Autowired
    private OAuthService oAuthService;

    @RequestMapping("/accessToken")
    public HttpEntity token(HttpServletRequest request) throws OAuthSystemException {
        try {
            //构建OAuth请求
            OAuthTokenRequest oAuthTokenRequest = new OAuthTokenRequest(request);
            //检查体提交的客户端IP是否正确
            if (!oAuthService.checkClientId(oAuthTokenRequest.getClientId())) {
                OAuthResponse oAuthResponse = OAuthResponse.errorResponse(HttpServletResponse.SC_BAD_REQUEST)
                        .setError(OAuthError.TokenResponse.INVALID_CLIENT)
                        .setErrorDescription(Constants.INVALID_CLIENT_DESCRIPTION)
                        .buildJSONMessage();
                return new ResponseEntity(oAuthResponse.getBody(), HttpStatus.valueOf(oAuthResponse.getResponseStatus()));
            }

            //检查客户端安全KEY是否正确  cxy 何时保存的安全KEY
            if (!oAuthService.checkClientSecret(oAuthTokenRequest.getClientSecret())) {
                OAuthResponse oAuthResponse = OAuthResponse.errorResponse(HttpServletResponse.SC_UNAUTHORIZED)
                        .setError(OAuthError.TokenResponse.UNAUTHORIZED_CLIENT)
                        .setErrorDescription(Constants.INVALID_CLIENT_DESCRIPTION)
                        .buildJSONMessage();
                return new ResponseEntity(oAuthResponse.getBody(), HttpStatus.valueOf(oAuthResponse.getResponseStatus()));
            }

            String authCode = oAuthTokenRequest.getParam(OAuth.OAUTH_CODE);
            //检查验证类型，此处只检查AUTHORIZATION_CODE类型，其他的还有PASSWORD和REFRESH_TOKEN  cxy 校验何物
            if (oAuthTokenRequest.getParam(OAuth.OAUTH_GRANT_TYPE).equals(GrantType.AUTHORIZATION_CODE.toString())) {
                if (!oAuthService.checkAuthCode(authCode)) {
                    OAuthResponse response = OAuthASResponse
                            .errorResponse(HttpServletResponse.SC_BAD_REQUEST)
                            .setError(OAuthError.TokenResponse.INVALID_GRANT)
                            .setErrorDescription("错误的授权码")
                            .buildJSONMessage();
                    return new ResponseEntity(response.getBody(), HttpStatus.valueOf(response.getResponseStatus()));
                }
            }

            //cxy 3、生成 AccessToken 并返回
            //生成Access'Token
            OAuthIssuer oAuthIssuerImpl = new OAuthIssuerImpl(new MD5Generator());
            final String accessToke = oAuthIssuerImpl.accessToken();
            oAuthService.addAccessToken(accessToke, oAuthService.getUsernameByAuthCode(authCode));

            //生成OAuth响应
            OAuthResponse response = OAuthASResponse.tokenResponse(HttpServletResponse.SC_OK)
                    .setAccessToken(accessToke)
                    .setExpiresIn(String.valueOf(oAuthService.getExpireIn()))
                    .buildJSONMessage();

            //根据OAuthResponse生成ResponseEntity
            return new ResponseEntity(response.getBody(), HttpStatus.valueOf(response.getResponseStatus()));

        } catch (OAuthProblemException e) {
            //构建错误响应
            OAuthResponse response = OAuthASResponse.errorResponse(HttpServletResponse.SC_BAD_REQUEST)
                    .error(e)
                    .buildJSONMessage();
            return new ResponseEntity(response.getBody(), HttpStatus.valueOf(response.getResponseStatus()));
        }
    }

}
