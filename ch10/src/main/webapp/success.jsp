<%--
  Created by IntelliJ IDEA.
  User: charl
  Date: 2017/6/29
  Time: 22:16
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>success</title>
</head>
<body>
<shiro:authenticated>
    欢迎[<shiro:principal/>]登录，<<a href="${pageContext.request.contextPath}/logout">点击退出</a>
</shiro:authenticated>
</body>
</html>
