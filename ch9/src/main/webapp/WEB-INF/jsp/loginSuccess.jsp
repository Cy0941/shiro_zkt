<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<html>
<head>
    <title></title>
</head>
<body>
<%--
    /logout2 为直接使用 shiro 的登出拦截器
--%>
<shiro:authenticated>
    欢迎${subject.principal}登录成功！<a href="${pageContext.request.contextPath}/logout2">退出</a>
</shiro:authenticated>
</body>
</html>