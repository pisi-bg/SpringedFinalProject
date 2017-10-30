<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!--!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd"-->
<!DOCTYPE html SYSTEM "about:legacy-compat">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Delivery info</title>
</head>
<body>

	<jsp:include page="header.jsp"></jsp:include><br>
	<br>
	<div align="left">
			<h1 align="center">Информация за доставка</h1>
			<br>
			 <!-- will be made with AJAX -->
			<c:if test="${sessionScope.deliveries != null &&  not empty sessionScope.deliveries }">
				<form action="<c:url value='/cart/deliveryInfo'/>" method="get">
					<h4 align="center">Избери от предишни доставки <br>
						 <select name='idxDeliveryInfo'>											
								<c:forEach items="${sessionScope.deliveries}" var="delivery" varStatus="loop">
									<option value="${loop.index}" >${delivery.address}</option>												
								</c:forEach>
						 </select> 									
						 <input type="image" name="submit" src="<c:url value='/img/buttons/update.png'/>" alt="UPDATE" title="Update">		
					 </h4> 								
				</form>
			</c:if>

		<form action="<c:url value='/cart/newOrder'/>" method="post">
			<h3 align="left">Получател</h3>
			<!--  form action="getNamesFromUser" method="POST"
				style="text-align: center;">
				<br> Вземи от профила<input type="radio">
			</form-->
			Име <input type="text" name="firstName" value="${ sessionScope.selectedDelInfo.recieverFirstName  }" required><br>
			Фамилия <input type="text" name="lastName" value="${ sessionScope.selectedDelInfo.recieverLastName  }" required><br>
			Телефон <input type="text" name="phone" value="${ sessionScope.selectedDelInfo.recieverPhone }" required><br>

			<h3 align="left">Адрес</h3>
				Град <select name='city'>
						<c:forEach items="${requestScope.cities}" var="city">
							<option value="${city}">${city}</option>
						</c:forEach>
					</select> <br> 
				Пощенски код <input type ="number" name="zip" min="1000" max ="9999" value="${sessionScope.selectedDelInfo.zipCode }" required> <br> 				
				Адрес <input type ="text" name="address" value="${ sessionScope.selectedDelInfo.address }" required> <br>
				Бележка <input type="text" name="note" value="${ sessionScope.selectedDelInfo.notes }"> <br> 
			<input type="submit" value="Потвърди">
		</form>
	</div> 
	
<%-- 	<div align="center">		
		<f:form commandName="deliveryInfo" > action="<c:url value='/cart/newDeliveryInfo'/>"  
			
			<h3 align="left">Получател</h3>
					Име:<f:input path="recieverFirstName" /><br>	
					Фамилия:<f:input path="recieverLastName" /><br>
					Телефон:<f:input path="recieverPhone" /><br>			
			<h3 align="left">Адрес</h3>	
					Град<f:select path='city' >
							<c:forEach items="${requestScope.cities}" var="city">
								<f:option value="${city}">${city}</f:option>
							</c:forEach>						
						</f:select>					
					Пощенски код:<f:input path="zipCode"  min="1000" max ="9999" /><br>
					Адрес:<f:input path="address" /><br>	
					Бележка:<f:input path="notes" /><br>	
		</f:form>
	</div> --%>
</body>
</html>