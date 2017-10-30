<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>

	<jsp:include page="header.jsp"></jsp:include>
	
	<h2>ЛЮБИМИ ПРОДУКТИ</h2>
	
		<c:if test="${ sessionScope.favorites != null }">
			
			<c:set var="asc" value="asc"></c:set>
			<c:set var="desc" value="desc"></c:set>
			<br>
		 	
			<table border="1">
				<c:forEach items="${ sessionScope.favorites }" var="pro">

					<tr>					
						<td><a href="<c:url value='/products/productdetail/productId/${pro.id}'/>">${pro.name }</a></td>
						<td>${pro.description }</td>
						<td>${pro.price }лв.</td>
						<c:if test="${pro.rating != 0 }">
							<td>${pro.rating }</td>
						</c:if>
						<c:if test="${pro.rating == 0 }">
							<td>No rating</td>
						</c:if>
						<td>${pro.rating }</td>
						<td>  <img src="${ pro.image }" alt="oops no image here" width="100" height="auto" /><br /> </td>
						
					</tr>
				</c:forEach>
			</table>
				
	</c:if>
	
</body>
</html>