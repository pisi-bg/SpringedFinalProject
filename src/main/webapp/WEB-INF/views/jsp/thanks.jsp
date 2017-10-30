<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!--!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd"-->
<!DOCTYPE html SYSTEM "about:legacy-compat">
<html>
<head>
<meta charset="UTF-8">
<title>Thanks</title>
</head>	
	<jsp:include page="header.jsp"></jsp:include>
	<br>
	
	<h1>ПОРЪЧКИ</h1>
	
		<h4>ПОРЪЧКАТА Е ИЗПРАТЕНА УСПЕШНО</h4>
		<h3>Благодарим Ви за доверието! </h3>
		<br>
		
		
			<form action="${pageContext.request.contextPath}/cart/thanks" method="post" style="display:inline;">		
				<h2 >	ПРЕДИШНИ ПОРЪЧКИ 		
					<input type="image" name="submit"	src="<c:url value='/img/buttons/arrow.png'/>" alt="ЦЪК" title="drop down" width="50" height="auto" >	
				</h2>
			</form>			
	<%-- 	<c:if test="${empty sessionScope.orders}">
			<h4 >	НЯМА ПРЕДИШНИ ПОРЪЧКИ 	</h4>
		</c:if> --%>
		
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
</body>
</html>