<%@ page isELIgnored="false"%>
<%@ page language="Java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Login</title>
</head>
<body>
<h1>Login</h1>
<form action="<%=basePath%>add.html" method="post">
    <p>
        <label>投保城市</label>
        <input id="city" name="city_name" type="text" />
    </p>
    <p>
        <label>车牌号</label>
        <input id="plateNumber" name="plate_number" type="text" />
    </p>
    <input type="submit" value="Login" />
</form>
</body>
</html>