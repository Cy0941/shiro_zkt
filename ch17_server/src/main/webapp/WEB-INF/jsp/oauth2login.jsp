<%--
  Created by IntelliJ IDEA.
  User: charl
  Date: 2017/7/1
  Time: 10:48
  To change this template use File | Settings | File Templates.
--%>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>登录并授权</title>
    <style>
        .error{color:red;}
    </style>
</head>
<body>

<div>使用你的Shiro示例Server帐号访问 [${client.clientName}] ，并同时登录Shiro示例Server</div>
<div class="error">${error}</div>

<form action="" method="post">
    用户名：<input type="text" name="username" value="<shiro:principal/>"><br/>
    密码：<input type="password" name="password"><br/>
    <input type="submit" value="登录并授权">
</form>

</body>
</html>
