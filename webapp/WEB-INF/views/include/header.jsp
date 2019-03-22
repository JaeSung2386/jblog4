<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<div id="header">
	<a id="id-header" href="${pageContext.servletContext.contextPath }/${id }">
		<h1>${blogVo.title }</h1>
	</a>
	<ul>
		<c:if test="${authuser.no ne null }">
			<li>
				<a href="${pageContext.servletContext.contextPath }/user/logout">로그아웃</a>
			</li>
			<li>
				<span style="color:white">|</span>
				<a href="${pageContext.servletContext.contextPath }/${authuser.id }/admin/basic">블로그 관리</a>
				<span style="color:white">|</span>
			</li>
		</c:if>
		<li>
			<a href="${pageContext.servletContext.contextPath }">JBlog 메인</a>
		</li>
		<li>	
			<a href="${pageContext.servletContext.contextPath }/${id }">${id }의 블로그메인</a>
			<span style="color:white">|</span>
		</li>
		<c:if test="${authuser.no eq null }">
			<li>
				<a href="${pageContext.servletContext.contextPath }/user/login">로그인</a>
				<span style="color:white">|</span>
			</li>
		</c:if>
	</ul>
</div>