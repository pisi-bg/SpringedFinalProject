<!--%@page import="model.dao.AnimalDao"%-->
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!--!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd"-->
<!DOCTYPE html SYSTEM "about:legacy-compat">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>TESTING</title>
</head>
<body>


			<table border="1">
				<c:forEach items="${ productList.getPageList() }" var="pro">

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
			
		 <div>
		    <span style="float:left;">
			    <c:choose>
			        <c:when test="${productList.firstPage}"></c:when>
			        <c:otherwise><a href="${pageContext.request.contextPath}/page/-2">Prev</a></c:otherwise>
			    </c:choose>
		    </span>
		    <span>
			    <c:forEach begin="0" end="${productList.pageCount-1}" varStatus="loop">
				    &nbsp;
				    <c:choose>
				        <c:when test="${loop.index == productList.page}">${loop.index+1}</c:when>
				        <c:otherwise><a href="${pageContext.request.contextPath}/page/${loop.index}">${loop.index+1}</a></c:otherwise>
				    </c:choose>
			    &nbsp;
			    </c:forEach>
		    </span>
		    <span>
			    <c:choose>
			        <c:when test="${productList.lastPage}"></c:when>
			        <c:otherwise><a href="${pageContext.request.contextPath}/page/-1">Next</a></c:otherwise>
			    </c:choose>
		    </span>
		   </div>

</body>
</html>