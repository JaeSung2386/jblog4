<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!doctype html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>JBlog</title>
<Link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/jblog.css">
<link rel="stylesheet" href="https://code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
<script type="text/javascript" src="${pageContext.request.contextPath }/assets/js/jquery/jquery-1.9.0.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<script>
/* 댓글 추가, 삭제 ajax 구현 예정 */
var messageBox = function(title, message){
	$("#dialog-message").attr("title", title);
	$("#dialog-message p").text(message);
	$("#dialog-message").dialog({
		modal: true,
		buttons: {
			"확인": function(){
				$(this).dialog("close");
			}
		}
	});
}

var render = function(vo, mode){
	var htmls = 
			"<table class='tbl-ex'>" +
				"<tr data-no='" + vo.no + "'>" +
					"<td>" + vo.reg_date + "</td>" +
					"<td>" + 
						"<a href=''>" + "삭제" + "</a>" + 
					"</td>" +
				"</tr>" +
				"<tr>" +
					"<td>" +
						"<div class='view-content'>" + vo.content + "</div>" +
					"</td>" +
				"</tr>" +
			"</table>";
	if( mode == true ){
		$("#list-comment").append(htmls);	// 맨 뒤
	} else {
		$("#list-comment").prepend(htmls); // 맨 처음
	}
}

var fetchList = function(){
	console.log("fetchList 실행");
	$.ajax({
		url: "${pageContext.request.contextPath }/${id}/${categoryNo}/${postVo.no}/commentlist",
		type: "get",
		dataType: "json",
		data: "",
		success: function(response) {
			if(response.result == "fail") {
				console.warn(response.message);
				return;
			}
			// rendering
			$.each(response.data, function(index, vo) {
				render(vo, true);
			})
		},
		error: function(xhr, status, e) {
			console.error(status + ":" + e);
		}
	});		
}
$(function(){
	// 최초 리스트 가져오기
	fetchList();
});
</script>
</head>
<body>
	<div id="container">
		<c:import url="/WEB-INF/views/include/header.jsp"/>
		<div id="wrapper">
			<div id="content">
			<c:if test="${!empty postVo}">
				<div class="blog-content">
					<h4>${postVo.title } </h4>
					<p>
						${fn:replace(postVo.content, newline, "<br>") }
					<p>
				</div>
			</c:if>
			<c:if test ="${empty postVo }">
				<h1>게시글이 존재하지 않습니다. </h1>
			</c:if>
			
			<div id="list-comment"></div>
		
			<c:if test="${!empty postVo }">
			<div id="board">
				<form class="board-form" method="post" action="${pageContext.servletContext.contextPath }/${id }/comment/write">
					<input type="hidden" name="post_no" value="${postVo.no }">
					<table class="tbl-ex">
						<tr>
							<td>
								<textarea id="content" name="content" style="width:460px;"></textarea>
							</td>
							<td class ="bottom">
								<input type="submit" value="등록">
							</td>
						</tr>
					</table>
				</form>				
			</div>
			</c:if>
				<ul class="blog-list">
				<c:forEach items="${postlist }" var="vo" >
					<li>
						<a href="${pageContext.servletContext.contextPath }/${id }/${categoryNo }/${vo.no}?page=${pageVo.pageNo}"> ${vo.title } </a> 
						<span>${vo.reg_date }</span>	
					</li>
				</c:forEach>
				</ul>
				<!-- pager 추가 -->
				<div id="pager">
					<ul>
						<c:if test ="${pageVo.pageNo > 5 }">	
							<li><a href="${pageContext.servletContext.contextPath }/${id }/${categoryNo }?page=${pageVo.firstPageNo}">◀◀</a></li>
							<li><a href="${pageContext.servletContext.contextPath }/${id }/${categoryNo }?page=${pageVo.prevPageNo}">◀</a></li>
						</c:if>
							<c:forEach var="i" begin="${pageVo.startPageNo }" end="${pageVo.endPageNo }" step="1">
								<c:if test="${i ne 0 }">
									<c:choose>
										<c:when test="${i eq pageVo.pageNo }">
											<li class="selected"><a href="${pageContext.servletContext.contextPath }/${id }/${categoryNo }?page=${i}">${i}</a></li>
										</c:when>
										<c:otherwise>
											<li><a href="${pageContext.servletContext.contextPath }/${id }/${categoryNo }?page=${i}">${i}</a></li>
										</c:otherwise>
									</c:choose>
								</c:if>
							</c:forEach>
						<c:if test ="${pageVo.pageNo > 5 }">		
							<li><a href="${pageContext.servletContext.contextPath }/${id }/${categoryNo }?page=${pageVo.nextPageNo}">▶</a></li>
							<li><a href="${pageContext.servletContext.contextPath }/${id }/${categoryNo }?page=${pageVo.finalPageNo}">▶▶</a></li>
						</c:if>
					</ul>
				</div>
			</div>
		</div>
		
		<div id="extra">
			<div class="blog-logo">
				<img onerror="this.src='${pageContext.request.contextPath}/assets/images/spring-logo.jpg'" src="${pageContext.request.contextPath }${blogVo.logo }">
			</div>
		</div>

		<div id="navigation">
			<h2>카테고리</h2>
			<ul>
			<c:forEach items="${categorylist }" var="categoryVo">
				<li><a href="${pageContext.servletContext.contextPath }/${id }/${categoryVo.no}">${categoryVo.name }</a></li>
			</c:forEach>
			</ul>
		</div>
		<c:import url="/WEB-INF/views/include/footer.jsp"/>
	</div>
</body>
</html>