<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

	<jsp:include page="header.jsp"></jsp:include>
	<br>
	
	<div class="container">	
		<h1>ПОРЪЧКИ</h1>
		
			<h4>ПОРЪЧКАТА Е ИЗПРАТЕНА УСПЕШНО</h4>
			<h3>Благодарим Ви за доверието! </h3>
			<br>			
			<form action="${pageContext.request.contextPath}/cart/thanks" method="post" style="display:inline;">		
				<h2 >	ПРЕДИШНИ ПОРЪЧКИ 		
					<input type="image" name="submit"	src="<c:url value='/img/buttons/arrow.png'/>" alt="ЦЪК" title="drop down" width="50" height="auto" >	
				</h2>
			</form>		
		
			<c:if test="${not empty sessionScope.orders}">
					<!-- <form action="deliveryInfo" method="get" >	 -->	
					
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
											<ul>  <!-- style="list-style-type: none" -->
												<c:forEach items="${ order.products }" var="productEntry">
													<li>	
															${productEntry.key.name } - ${productEntry.value } бр.
													</li>
												</c:forEach>														
											</ul>			
										</td>
										<td>
											<ul >
												<li>
													<span style="font-weight:lighter"> Име </span>
													${order.deliveryInfo.recieverFirstName} ${order.deliveryInfo.recieverLastName}
												</li>
												<li>
													Телефон 
													<span style="font-weight:bold"> Телефон </span>
													 ${order.deliveryInfo.recieverPhone}
												</li>                                           
												<li>                                            
													Град 
													<span style="font-weight: bold;"> Град </span> ${order.deliveryInfo.city}  
													 <span style="font-weight: normal"> Пощенски код </span> ${order.deliveryInfo.zipCode}
												</li>                                           
												<li>                                            
													Адрес
													<span style="font-weight: bold;"> Адрес </span>
													  ${order.deliveryInfo.address}
												</li>                                           
												<li>                                            
													Бележка
													<span style="font-weight: bold;"> Бележка </span>
													  ${order.deliveryInfo.notes}
												</li>  												
											</ul>
										
										</td>
									</tr>
								</c:forEach>	
							</tbody>
						</table>
					<br>				
					<!-- </form> -->
			</c:if>
		</div>	<!-- container -->
</body>
</html>