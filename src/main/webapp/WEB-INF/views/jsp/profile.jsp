<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>	
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form" %>	
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
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
				<%-- <form action="deliveryInfo" method="get" >	 --%>	
						
			<table border="1">
							<thead class="">
								<tr>
									<th>Дата</th>
									<th>Цена</th>											
									<th>Продукти</th>
									<th>Доставка</th>
								</tr>
							</thead>						
							<tbody>			
								<c:forEach items="${ sessionScope.orders }" var="order">
									<tr>
									
										<td>
											<fmt:parseDate value="${ order.dateTime }" pattern="yyyy-MM-dd'T'HH:mm" var="parsedDateTime" type="both" />
											<fmt:formatDate pattern="dd.MM.yyyy HH:mm" value="${ parsedDateTime }" />
										</td>
										<td>${order.finalPrice }</td>
										<td>
											<!-- <ul>  style="list-style-type: none" -->
											
												<table  style="border-color:#FFF8DC; border:0;">
													<thead class="light">
														<tr>
															<th style="font-size: 12px;">име</th>
															<th style="font-size: 12px;">к-во</th>																				
															<th style="font-size: 12px;">марка</th>	
															<th style="font-size: 12px;">категория</th>
														</tr>
													</thead>						
													<tbody>			
														<c:forEach items="${ order.products }" var="productEntry">
															<tr class="light">
																<td width = "200px">${productEntry.key.name }</td>
																<td  width = "40px">${productEntry.value }</td>
																<td>${productEntry.key.brand }</td>
																<td>${productEntry.key.category }</td>														
															</tr> 
														</c:forEach>	
													</tbody>
												</table>
										</td>
										
										<td>
											<ul >
												<li>
													 Име 
													<span style="font-weight:lighter">${order.deliveryInfo.recieverFirstName} ${order.deliveryInfo.recieverLastName}</span>
												</li>
												<li>
													Телефон 
													<span style="font-weight:lighter">${order.deliveryInfo.recieverPhone}</span> 
												</li>                                           
												<li>                                            
													Град 
													<span style="font-weight:lighter">${order.deliveryInfo.city}</span>   
													 ПK
													<span style="font-weight:lighter">${order.deliveryInfo.zipCode}</span> 
												</li>                                           
												<li>                                            
													Адрес													
													<span style="font-weight:lighter">${order.deliveryInfo.address}</span>
												</li>                                           
												<li>                                            
													Бележка
													<span style="font-weight:lighter">${order.deliveryInfo.notes}</span>   
												</li>  												
											</ul>
										
										</td>
									</tr>
								</c:forEach>	
							</tbody>
						</table>
						
						
								<%-- <table border="1">
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
												<td>
													<fmt:parseDate value="${ order.dateTime }" pattern="yyyy-MM-dd'T'HH:mm" var="parsedDateTime" type="both" />
													<fmt:formatDate pattern="dd.MM.yyyy HH:mm" value="${ parsedDateTime }" />	
												</td>
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
								</table> --%>
							<br>
										
				<%-- </form> --%>
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