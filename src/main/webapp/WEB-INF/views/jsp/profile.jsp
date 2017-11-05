<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>	
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form" %>	
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>


	<c:if test="${ sessionScope.user == null }">
		<c:redirect url="login">
		</c:redirect>
	</c:if>

	<jsp:include page="header.jsp"></jsp:include>
	
	<div class="container">
		<h3 class="has-error"><c:out value="${error }"></c:out></h3>
		<h3 style="color:#3CB371;font-size: 16px "><c:out value="${requestScope.success }"></c:out></h3>
		<h5 style="text-align: center">
			<c:if test="${sessionScope.user.getId() == 1 || sessionScope.user.getId() == 2 }">
				<form action="${pageContext.request.contextPath}/user/admin/changeStatus" method="post" style="display:inline">
					Имейл на потребител, чийто статут искате да смените: <br>
					<input type="email" name="email" required><br><br>
					<input type="submit" value="Промени">
				</form>
			</c:if>
		</h5>
		<%--  <c:if test="${empty sessionScope.orders}">
			<h4 >	НЯМА ПРЕДИШНИ ПОРЪЧКИ 	</h4>
		</c:if>   --%>
		
			<form action="${pageContext.request.contextPath}/user/profile/showOrders" method="post" style="display:inline;">		
				<h2 >	ПРЕДИШНИ 10 ПОРЪЧКИ 		
					<input type="image" name="submit"	src="<c:url value='/img/buttons/arrow.png'/>" alt="ЦЪК" title="drop down" width="50" height="auto" >	
				</h2>
			</form>			
		 <c:if test="${not empty sessionScope.orders}">		
			<table border="1" >
				<thead class="">
					<tr>
						<th class="col-xs-12 col-md-1">Дата</th>
						<th class="col-xs-12 col-md-1">Цена</th>											
						<th class="col-xs-12 col-md-8">Продукти</th>
						<th class="col-xs-12 col-md-2">Доставка</th>
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
							<td style="vertical-align: top; ">
								<!-- <ul>  style="list-style-type: none" -->
								
									<table  style="border-color:#FFF8DC; border:0; width:100%; table-layout: fixed; ">
										<thead class="light">
											<tr>
												<th class="col-xs-12 col-md-3" style="font-size: 14px; text-transform: lowercase; width:100%; text-transform: uppercase;font-weight: bold;" >име</th>
												<th class="col-xs-12 col-md-1" style="font-size: 14px; text-transform: lowercase; width:100%; text-transform: uppercase;font-weight: bold;" >к-во</th>																				
												<th class="col-xs-12 col-md-2" style="font-size: 14px; text-transform: lowercase; width:100%; text-transform: uppercase;font-weight: bold;" >марка</th>	
												<th class="col-xs-12 col-md-2" style="font-size: 14px; text-transform: lowercase; width:100%; text-transform: uppercase;font-weight: bold;" >категория</th>
											</tr>
										</thead>						
										<tbody>			
											<c:forEach items="${ order.products }" var="productEntry">
												<tr class="light">
													<td class="col-xs-12 col-md-3" style="font-weight: lighter; width:100%">${productEntry.key.name }</td>
													<td class="col-xs-12 col-md-1" style="font-weight: lighter; width:100%">${productEntry.value }</td>
													<td class="col-xs-12 col-md-2" style="font-weight: lighter; width:100%">${productEntry.key.brand }</td>
													<td class="col-xs-12 col-md-2" style="font-weight: lighter; width:100%">${productEntry.key.category }</td>														
												</tr>                                    
											</c:forEach>	
										</tbody>
									</table>
							</td>
							
							<td>
								<ul >
									<li>
										<p  style="font-size: 14px; text-transform: lowercase; width:100%; text-transform: uppercase;font-weight: bold; text-align:left"> Име 
											<span style="font-weight:lighter;text-transform: capitalize;">${order.deliveryInfo.recieverFirstName} ${order.deliveryInfo.recieverLastName}</span>
										 </p>
									</li>
									<li>
										<p  style="font-size: 14px; text-transform: lowercase; width:100%; text-transform: uppercase;font-weight: bold; text-align:left">Телефон
											<span style="font-weight:lighter; text-transform: capitalize;">${order.deliveryInfo.recieverPhone}</span>
										 </p>	 
									</li>                                           
									<li>                                            
										<p  style="font-size: 14px; text-transform: lowercase; width:100%; text-transform: uppercase;font-weight: bold; text-align:left">Град 
										<span style="font-weight:lighter; text-transform: capitalize;">${order.deliveryInfo.city}</span>   
										<!-- <p  style="font-size: 14px; text-transform: lowercase; width:100%; text-transform: uppercase;font-weight: bold; align:left"> --> ПK
										<span style="font-weight:lighter; text-transform: capitalize;">${order.deliveryInfo.zipCode}</span> 
										</p>
									</li>                                           
									<li>                                            
										<p  style="font-size: 14px; text-transform: lowercase; width:100%; text-transform: uppercase;font-weight: bold;text-align:left">Адрес											
											<span style="font-weight:lighter; text-transform: capitalize;">${order.deliveryInfo.address}</span>
										 </p>
									</li>                                           
									<li>                                            
										<p  style="font-size: 14px; text-transform: lowercase; width:100%; text-transform: uppercase;font-weight: bold; text-align:left">Бележка
											<span style="font-weight:lighter; text-transform: capitalize;">${order.deliveryInfo.notes}</span> 
										 </p>	
							  
									</li>  												
								</ul>
							
							</td>
						</tr>
					</c:forEach>	
				</tbody>
			</table>
			<br>
					
			</c:if>	
	
			
		 		<jsp:include page="${request.contextPath}/user/update"></jsp:include>
		 </div> <!-- contanier -->	
		

</body>
</html>