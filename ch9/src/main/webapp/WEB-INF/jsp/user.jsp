<%--
  Created by IntelliJ IDEA.
  User: Charls
  Date: 2017/6/17
  Time: 10:36
  To change this template use File | Settings | File Templates.
--%>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>用户列表</title>
</head>
<body>
<shiro:hasAnyRoles name="admin,user">
    <div>
        <a>${username}，你好，欢迎访问！</a>
    </div>
</shiro:hasAnyRoles>
</body>
</html>
