<%--
  Created by IntelliJ IDEA.
  User: charl
  Date: 2017/7/1
  Time: 11:18
  To change this template use File | Settings | File Templates.
--%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>OAuth2集成</title>
</head>
<body>
<shiro:guest>
    游客你好，请<a href="${pageContext.request.contextPath}/login">登录</a>
</shiro:guest>

<shiro:user>
    欢迎[<shiro:principal/>]，点击<a href="${pageContext.request.contextPath}/logout">退出</a>
    <a href="${pageContext.request.contextPath}/client" target="_blank">客户端管理</a>
    <a href="${pageContext.request.contextPath}/user" target="_blank">用户管理</a>
</shiro:user>
</body>
</html>
