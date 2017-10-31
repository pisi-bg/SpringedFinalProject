<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>	
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form" %>	
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!--!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd"-->
<!DOCTYPE html SYSTEM "about:legacy-compat">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>

	<c:if test="${ sessionScope.user == null }">
		<c:redirect url="login">
		</c:redirect>
	</c:if>

	<jsp:include page="header.jsp"></jsp:include>

	<!-- <h2>Your previous orders:</h2>

	<a href="sortOrders?sort=desc"><button>Newest to oldest</button></a>
	<a href="sortOrders?sort=asc"><button>Oldest to newest</button></a>
	<hr> -->
		<form action="${pageContext.request.contextPath}/user/profile/showOrders" method="post" style="display:inline;">		
				<h2 >	ПРЕДИШНИ 10 ПОРЪЧКИ 		
					<input type="image" name="submit"	src="<c:url value='/img/buttons/arrow.png'/>" alt="ЦЪК" title="drop down" width="50" height="auto" >	
				</h2>
		</form>			
	 	<%-- <c:if test="${empty sessionScope.orders}">
			<h4 >	НЯМА ПРЕДИШНИ ПОРЪЧКИ 	</h4>
		</c:if>  --%>
		
		<c:if test="${not empty sessionScope.orders}">
				<form action="deliveryInfo" method="get" >				
								<table border="1">
									<thead class="">
										<tr>
											<th class="cart_product first_item">Дата</th>
											<th class="cart_description item">Цена</th>											
											<th class="cart_quantity item text-center">Продукти</th>
										</tr>
									</thead>						
									<tbody>			
										<c:forEach items="${ sessionScope.orders }" var="order">
											<tr>
												<td>${ order.dateTime }</td>
												<td>${order.finalPrice }</td>
												<td>
													<ul style="list-style-type: none">
														<c:forEach items="${ order.products }" var="productEntry">
															<li>	
																	${productEntry.key.name } - ${productEntry.value }
															</li>
														</c:forEach>														
													</ul>			
												</td>
											</tr>
										</c:forEach>	
									</tbody>
								</table>
							<br>
										
				</form>
		</c:if>	
<!-- 
	<h1>Please update all fields:</h1>
	<form action="register" method="post">
		First name<input type="text" name="first_name" required><br>
		Last name<input type="text" name="last_name" required><br>
		Email<input type="email" name="email" required><br>
		Password<input type="password" name="password" required><br>
		Gender: <input type="radio" name="gender" value="true" checked>
		Male <input type="radio" name="gender" value="false"> Female<br>
		<input type="submit" value="Update">
	</form> -->
	
		<%-- <form action="${request.contextPath}/user/update" method="get" style="display:inline;">		
				<h2 >	ПРОМЕНИ ИНФОРМАЦИЯ В ПРОФИЛА 		
					<input type="image" name="submit"	src="<c:url value='/img/buttons/arrow.png'/>" alt="ЦЪК" title="drop down" width="50" height="auto" >	
				</h2>
		</form>	 --%>
		<%-- <c:if test="${requestScope.update ==1}"> --%>
		
	 		<jsp:include page="${request.contextPath}/user/update"></jsp:include>
	 		
	<%-- 	</c:if> --%>

</body>
</html>