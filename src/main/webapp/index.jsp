<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath }"></c:set>
<!DOCTYPE html>
<html>
<head>
<base href="${ctx }/">
<meta charset="UTF-8">
<title>首页</title>
<link rel="stylesheet" type="text/css" href="assets/hello/hello.css">
</head>
<body style="background: url(assets/img/009.jpg) no-repeat 0 0/cover ">
	<h1 align="center" style="color: #FFFFFF;">欢迎来到云仓系统</h1>

<!-- 用户名 密码区 -->
	<form action="ware/list" method="post">
		<span >用户名:</span> 
		<input id="username"  type="text" name="username" value="xiaoli" placeholder="请输入用户名"> 
		<span >密  码:</span>
		<input id="password"  type="password" name="password" value="123456" placeholder="请输入密码">
		<input type="submit" value="登录">
	</form>
</body>
</html>