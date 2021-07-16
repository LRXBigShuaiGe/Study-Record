<%--
  Created by IntelliJ IDEA.
  User: lenovo
  Date: 2021/6/15
  Time: 21:44
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>success</title>
</head>
<body>

    <h1>登录成功！！！</h1>
    pageContext: ${pageScope.username}<br>
    request: ${requestScope.username}<br>
    session: ${sessionScope.username}<br>
    application: ${applicationScope.username}<br>
<%System.out.println("来到页面了"); %>
</body>
</html>
