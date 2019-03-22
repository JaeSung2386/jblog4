<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<h1 class="logo">
	<a href="${pageContext.servletContext.contextPath }">
		<h1>JBlog</h1>
	</a>
</h1>

<ul class="menu">
	<li><a href="${pageContext.servletContext.contextPath }">메인</a></li>
	<c:if test="${authuser.no eq null }">
		<li><a href="${pageContext.servletContext.contextPath }/user/login">로그인</a></li>
		<li><a href="${pageContext.servletContext.contextPath }/user/join">회원가입</a></li>
	</c:if>
	<c:if test="${authuser.no ne null }">
		<li><a href="${pageContext.servletContext.contextPath }/${authuser.id}">내 블로그</a></li>
		<li><a href="${pageContext.servletContext.contextPath }/user/logout">로그아웃</a></li>			
	</c:if>
</ul>