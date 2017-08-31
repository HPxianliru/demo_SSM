<%@ page isELIgnored="false"%>
<%@ page language="Java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
</head>
<body>
    <p>carInfo</p>
    <h2>车辆信息录入</h2>
    <ul>
        <li>车主信息
            <ul>
                <li>
                    <label>车主姓名</label>
                    <input id="carOwenName" name="carOwenName" type="text" />
                </li>
                <li>
                    <label>手机号码</label>
                    <input id="telPhone" name="telPhone" type="text" />
                </li>
                <li>
                    <label>身份证号</label>
                    <input id="identityNum" name="identityNum" type="text" />
                </li>
            </ul>
        </li>
        <li>车辆信息
            <ul>
                <li>
                    <label>车辆型号</label>
                    <input id="modloName" name="modloName" type="text">
                </li>
                <li>
                    <label>车辆识别号</label>
                    <input id="vin" name="vin" type="text">
                </li>
                <li>
                    <label>发动机号</label>
                    <input id="engiNo" name="engiNo" type="text">
                </li>
                <li>
                    <label>注册登记日期</label>
                    <input id="firstDate" name="firstDate" type="text">
                </li>
            </ul>
        </li>
        <li>
            <label>是否过户车</label>

        </li>
    </ul>
</body>
</html>