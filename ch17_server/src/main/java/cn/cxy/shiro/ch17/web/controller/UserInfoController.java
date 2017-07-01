package cn.cxy.shiro.ch17.web.controller;

import cn.cxy.shiro.ch17.Constants;
import cn.cxy.shiro.ch17.service.OAuthService;
import org.apache.oltu.oauth2.as.response.OAuthASResponse;
import org.apache.oltu.oauth2.common.OAuth;
import org.apache.oltu.oauth2.common.error.OAuthError;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.message.OAuthResponse;
import org.apache.oltu.oauth2.common.message.types.ParameterStyle;
import org.apache.oltu.oauth2.common.utils.OAuthUtils;
import org.apache.oltu.oauth2.rs.request.OAuthAccessResourceRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Function: TODO 资源控制器
 * Reason: TODO ADD REASON(可选).</br>
 * Date: 2017/7/1 11:43 </br>
 *
 * @author: cx.yang
 * @since: Thinkingbar Web Project 1.0
 */
@Controller
public class UserInfoController {

    @Autowired
    private OAuthService oAuthService;

    @RequestMapping("/userInfo")
    public HttpEntity useInfo(HttpServletRequest request) throws OAuthSystemException {
        //cxy 4、根据AccessToken访问受保护的资源
        try {
            //构建OAuth资源请求
            OAuthAccessResourceRequest oAuthAccessResourceRequest = new OAuthAccessResourceRequest(request, ParameterStyle.QUERY);

            //获取AccessToken
            String accessToken = oAuthAccessResourceRequest.getAccessToken();

            //验证AccessToken
            if (!oAuthService.checkAccessToken(accessToken)) {
                //如果不存在/过期了，返回未验证错误，需重新验证
                OAuthResponse oAuthResponse = OAuthASResponse.errorResponse(HttpServletResponse.SC_UNAUTHORIZED)
                        .setRealm(Constants.RESOURCE_SERVER_NAME)
                        .setError(OAuthError.ResourceResponse.INVALID_TOKEN)
                        .buildHeaderMessage();
                HttpHeaders httpHeaders = new HttpHeaders();
                httpHeaders.add(OAuth.HeaderType.WWW_AUTHENTICATE, oAuthResponse.getHeader(OAuth.HeaderType.WWW_AUTHENTICATE));
                return new ResponseEntity(httpHeaders, HttpStatus.UNAUTHORIZED);
            }

            //返回用户名
            String username = oAuthService.getUsernameByAccessToken(accessToken);
            return new ResponseEntity(username, HttpStatus.OK);
        } catch (OAuthProblemException e) {
            //检查是否设置了错误码
            String errorCode = e.getError();
            if (OAuthUtils.isEmpty(errorCode)) {
                OAuthResponse oAuthResponse = OAuthASResponse.errorResponse(HttpServletResponse.SC_UNAUTHORIZED)
                        .setRealm(Constants.RESOURCE_SERVER_NAME)
                        .buildHeaderMessage();

                HttpHeaders httpHeaders = new HttpHeaders();
                httpHeaders.add(OAuth.HeaderType.WWW_AUTHENTICATE, oAuthResponse.getHeader(OAuth.HeaderType.WWW_AUTHENTICATE));
                return new ResponseEntity(httpHeaders, HttpStatus.UNAUTHORIZED);
            }

            OAuthResponse oAuthResponse = OAuthASResponse.errorResponse(HttpServletResponse.SC_UNAUTHORIZED)
                    .setRealm(Constants.RESOURCE_SERVER_NAME)
                    .setError(e.getError())
                    .setErrorDescription(e.getDescription())
                    .setErrorUri(e.getUri())
                    .buildHeaderMessage();
            HttpHeaders headers = new HttpHeaders();
            headers.add(OAuth.HeaderType.WWW_AUTHENTICATE, oAuthResponse.getHeader(OAuth.HeaderType.WWW_AUTHENTICATE));
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }

}
