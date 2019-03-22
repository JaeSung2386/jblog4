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

var num = 1;
var td_num = 0;
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
	var htmls;
	if(vo.total_post > 1) {
		htmls = 
			"<tr data-no='" + vo.no + "'>" +
			"<td id='num'>" + num++ + "</td>" +
			"<td>" + vo.name + "</td>" +
			"<td>" + vo.total_post + "</td>" +
			"<td>" + vo.description + "</td>" +
			"<td>" + 
				"<a href='' data-no='" + vo.no + "'>" +
				"</a>" +
			"</td>" +
			"</tr>";	
	} else {
		htmls = 
			"<tr data-no='" + vo.no + "'>" +
			"<td id='num'>" + num++ + "</td>" +
			"<td>" + vo.name + "</td>" +
			"<td>" + vo.total_post + "</td>" +
			"<td>" + vo.description + "</td>" +
			"<td>" + 
				"<a href='' data-no='" + vo.no + "'>" +
				"<img src='${pageContext.request.contextPath}/assets/images/delete.jpg'>" + 
				"</a>" +
			"</td>" +
		"</tr>";
	}
	if( mode == true ){
		$("#list-category").append(htmls);	// 맨 뒤
	} else {
		$("#list-category").prepend(htmls); // 맨 처음
	}
}

var fetchList = function(){
	$.ajax({
		url: "${pageContext.request.contextPath }/${authuser.id}/admin/category/list",
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
	// 카테고리 추가
	$("#add-form").submit(function(){
		event.preventDefault();
		
		var categoryVo = {};
		categoryVo.name = $("#input-name").val();
		categoryVo.description = $("#input-desc").val();
		
		if(categoryVo.name == "") {
			messageBox("카테고리 추가", "카테고리명은 필수 입력 항목입니다.");
			return;
		}
		if(categoryVo.description == "") {
			messageBox("카테고리 추가", "카테고리 설명은 필수 입력 항목입니다.");
			return;
		}
		
		$.ajax({
			url: "${pageContext.servletContext.contextPath }/${authuser.id}/admin/category/add",
			type: "post",
			dataType: "json",
			data: JSON.stringify(categoryVo),
			contentType: 'application/json',
			success: function(response) {
				if(response.result == "fail") {
					console.error(response.message);
					return;
				}
				// rendering
				render(response.data, true);
			},
			error: function(xhr, status, e) {
				console.error(status + ":" + e);
			}
		});
		//내용 비우기
		$("#input-name").val("");
		$("#input-desc").val("");
	});
	
	// 삭제
	$(document).on("click", "#list-category td a", function(){
		event.preventDefault();
		var no = $(this).data("no");
		var temp = $("#list-category td a").parent('td').eq(0).text();
		console.log(temp);
		console.log(this);
		$.ajax({
			url: "${pageContext.servletContext.contextPath }/${authuser.id}/admin/category/delete",
			type: "post",
			datType: "json",
			data:
				"no=" + no,
			success: function(response) {
				if(response.result == "fail") {
					console.error(response.message);
					return;
				}

				$( "#list-category tr[data-no='" + response.data + "']" ).remove();
				
			},
			error: function(xhr, status, e) {
				console.error(status + ":" + e);
			}
		})
	});
	
	// 최초 리스트 가져오기
	fetchList();
});
</script>
</head>
<body>
	<div id="container">
		<c:import url="/WEB-INF/views/include/header.jsp"/>
		<div id="wrapper">
			<div id="content" class="full-screen">
				<ul class="admin-menu">
					<li><a href="${pageContext.servletContext.contextPath }/${authuser.id }/admin/basic">기본설정</a></li>
					<li class="selected">카테고리</li>
					<li><a href="${pageContext.servletContext.contextPath }/${authuser.id }/admin/write">글작성</a></li>
				</ul>
		      	<table class="admin-cat">
		      		<tr>
		      			<th>번호</th>
		      			<th>카테고리명</th>
		      			<th>포스트 수</th>
		      			<th>설명</th>
		      			<th>삭제</th>      			
		      		</tr>
		      		<tbody id="list-category"></tbody>  					  
				</table>
      	
      			<h4 class="n-c">새로운 카테고리 추가</h4>
      			<form id="add-form" method="post">
			      	<table id="admin-cat-add">
			      		<tr>
			      			<td class="t">카테고리명</td>
			      			<td><input id="input-name" type="text" name="name"></td>
			      		</tr>
			      		<tr>
			      			<td class="t">설명</td>
			      			<td><input id="input-desc" type="text" name="description"></td>
			      		</tr>
			      		<tr>
			      			<td class="s">&nbsp;</td>
			      			<td><input type="submit" value="카테고리 추가"></td>
			      		</tr>      		      		
			      	</table>
		      	</form> 
			</div>
			<div id="dialog-message" title="" style="display:none">
  				<p style="padding:20px 0"></p>
			</div>	
		</div>
		<c:import url="/WEB-INF/views/include/footer.jsp"/>
	</div>
</body>
</html>