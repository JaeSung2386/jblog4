<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!doctype html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>JBlog</title>
<Link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/jblog.css?after">
<script type="text/javascript" src="${pageContext.request.contextPath}/assets/js/jquery/jquery-1.9.0.js"></script>
</head>
<body>
	<div class="center-content">
		<c:import url="/WEB-INF/views/include/menu.jsp"/>
		<form class="login-form" method="post" action="${pageContext.servletContext.contextPath }/user/auth">
      		<div class="center-summary">
      			<h3>로그인</h3>
      		</div>
      		<input type="text" name="id" placeholder="id를 입력해주세요.">
      		<input type="password" name="password" placeholder="password를 입력해주세요.">
      		<input type="submit" value="로그인">
		</form>
		<c:import url="/WEB-INF/views/include/find_info.jsp"/>
	</div>
</body>
</html>
