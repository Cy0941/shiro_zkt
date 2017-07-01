package cn.cxy.shiro.ch17.web.controller;

import cn.cxy.shiro.ch17.Constants;
import cn.cxy.shiro.ch17.service.ClientService;
import cn.cxy.shiro.ch17.service.OAuthService;
import org.apache.commons.lang3.StringUtils;
import org.apache.oltu.oauth2.as.issuer.MD5Generator;
import org.apache.oltu.oauth2.as.issuer.OAuthIssuerImpl;
import org.apache.oltu.oauth2.as.request.OAuthAuthzRequest;
import org.apache.oltu.oauth2.as.response.OAuthASResponse;
import org.apache.oltu.oauth2.common.OAuth;
import org.apache.oltu.oauth2.common.error.OAuthError;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.message.OAuthResponse;
import org.apache.oltu.oauth2.common.message.types.ResponseType;
import org.apache.oltu.oauth2.common.utils.OAuthUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * cxy 授权控制器 -- 请求授权，拿到授权许可（授权码）  --  authCode
 * <p>User: Zhang Kaitao
 * <p>Date: 14-2-16
 * <p>Version: 1.0
 */
@Controller
public class AuthorizeController {

    @Autowired
    private OAuthService oAuthService;
    @Autowired
    private ClientService clientService;

    @RequestMapping("/authorize")
    public Object authorize(
            Model model,
            HttpServletRequest request)
            throws URISyntaxException, OAuthSystemException {
        try {
            //构建OAuth 授权请求
            OAuthAuthzRequest oAuthAuthzRequest = new OAuthAuthzRequest(request);
            //检查传入的客户端IP是否正确
            if (!oAuthService.checkClientId(oAuthAuthzRequest.getClientId())) {
                OAuthResponse oAuthResponse = OAuthResponse
                        .errorResponse(HttpServletResponse.SC_BAD_REQUEST)
                        .setError(OAuthError.TokenResponse.INVALID_CLIENT)
                        .setErrorDescription(Constants.INVALID_CLIENT_DESCRIPTION)
                        .buildJSONMessage();
                return new ResponseEntity(oAuthResponse.getBody(), HttpStatus.valueOf(oAuthResponse.getResponseStatus()));
            }
            Subject subject = SecurityUtils.getSubject();
            //cxy 1、请求授权
            //如果没有用户登录，跳转到登录页面
            if (!subject.isAuthenticated()) {
                if (!login(subject, request)) {//登录失败跳转到登录页面
                    model.addAttribute("client", clientService.findByClientId(oAuthAuthzRequest.getClientId()));
                    return "oauth2login";
                }
            }

            //cxy 2、生成并返回授权许可
            String username = (String) subject.getPrincipal();
            //生成授权码
            String authorizationCode = null;
            //responseType 目前仅支持 CODE 及 TOKEN  cxy 校验何物
            String responseType = oAuthAuthzRequest.getParam(OAuth.OAUTH_RESPONSE_TYPE);
            if (responseType.equals(ResponseType.CODE.toString())) {
                OAuthIssuerImpl oAuthIssuer = new OAuthIssuerImpl(new MD5Generator());
                authorizationCode = oAuthIssuer.authorizationCode();
                //授权码放入缓存
                oAuthService.addAuthCode(authorizationCode, username);
            }

            //进行OAuth响应构建
            OAuthASResponse.OAuthAuthorizationResponseBuilder builder = OAuthASResponse.authorizationResponse(request, HttpServletResponse.SC_FOUND);
            //设置授权码
            builder.setCode(authorizationCode);
            //得到客户端重定向地址
            String redirectURL = oAuthAuthzRequest.getParam(OAuth.OAUTH_REDIRECT_URI);
            //构建响应
            final OAuthResponse response = builder.location(redirectURL).buildQueryMessage();
            //根据OAuthResponse 返回 ResponseEntity 响应
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.setLocation(new URI(response.getLocationUri()));
            return new ResponseEntity(httpHeaders, HttpStatus.valueOf(response.getResponseStatus()));
        } catch (OAuthProblemException e) {
            //出错处理
            String redirectUri = e.getRedirectUri();
            if (OAuthUtils.isEmpty(redirectUri)) {
                //没有传入redirectURI - 直接报错
                return new ResponseEntity("OAuth callback url needs to be provided by client!!!", HttpStatus.NOT_FOUND);
            }

            //返回错误消息
            final OAuthResponse response = OAuthResponse.errorResponse(HttpServletResponse.SC_FOUND)
                    .error(e).location(redirectUri).buildQueryMessage();
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.setLocation(new URI(response.getLocationUri()));
            return new ResponseEntity(httpHeaders, HttpStatus.valueOf(response.getResponseStatus()));
        }
    }

    private boolean login(Subject subject, HttpServletRequest request) {
        if ("get".equalsIgnoreCase(request.getMethod())) {
            return false;
        }
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password)) {
            return false;
        }
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        try {
            subject.login(token);
            return true;
        } catch (AuthenticationException e) {
            request.setAttribute("error", "登录失败： " + e.getClass().getName());
            return false;
        }
    }

}