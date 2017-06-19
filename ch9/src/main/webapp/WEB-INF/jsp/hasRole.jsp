<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<html>
<head>
    <title></title>
</head>
<body>
<shiro:hasRole name="admin">
    ${subject.principal}拥有角色。
</shiro:hasRole>
</body>
</html>