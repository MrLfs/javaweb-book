<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>图书管理</title>
	<%--	静态包含 base标签 css样式  JQuery文件--%>
	<%@include file="/pages/common/head.jsp"%>
	<script type="text/javascript">
		$(function () {
			$(".deleteClass").click(function () {

				return confirm( "确定要删除【" + $(this).parent().parent().find("td:first").text() + "】吗?" );
			})

			$("#searchPageBtn").click(function () {
				var pageNo = $("#pn_input").val();
				var pageTotal = ${requestScope.page.pageTotal};
				if(pageNo < pageTotal && pageNo > 1){

					//javaScript语言中提供了一个location地址栏对象
					//它有一个属性交href，它可以获取浏览器地址栏中的地址
					//href属性可读可写，赋值就是跳转
					location.href="${pageScope.basePath}/${requestScope.page.url}&pageNo=" + pageNo;
				}

			})
		})
	</script>
</head>
<body>
	
	<div id="header">
			<img class="logo_img" alt="" src="static/img/logo.gif" >
			<span class="wel_word">图书管理系统</span>
		<%--			静态包含 manager管理模块的菜单--%>
		<%@include file="/pages/common/manager_menu.jsp"%>
	</div>
	
	<div id="main">
		<table>
			<tr>
				<th>名称</th>
				<th>价格</th>
				<th>作者</th>
				<th>销量</th>
				<th>库存</th>
				<th colspan="2">操作</th>
			</tr>		
			<c:forEach items="${requestScope.page.items}" var="book">
				<tr>
					<td>${book.name}</td>
					<td>${book.price}</td>
					<td>${book.author}</td>
					<td>${book.sales}</td>
					<td>${book.stock}</td>
					<td><a href="manager/bookServlet?action=getBook&method=update&id=${book.id}&pageNo=${requestScope.page.pageNo}">修改</a></td>
					<td><a class="deleteClass" href="manager/bookServlet?action=delete&id=${book.id}&pageNo=${requestScope.page.pageNo}">删除</a></td>
				</tr>
			</c:forEach>

			
			<tr>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td><a href="pages/manager/book_edit.jsp?method=add&pageNo=${requestScope.page.pageTotal}">添加图书</a></td>
			</tr>	
		</table>
		<%@include file="/pages/common/page_nav.jsp"%>
	</div>

	<%--	静态包含--%>
	<%@include file="/pages/common/footer.jsp"%>
</body>
</html>