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

	<h3 align="center">Информация за доставка</h3>
	<br>
	 <!-- will be made with AJAX -->
			<c:if test="${sessionScope.deliveries != null &&  not empty sessionScope.deliveries }">
				<form action="deliveryInfo" method="post">
					Предишни доставки <select name='idxDeliveryInfo'>											
											<c:forEach items="${sessionScope.deliveries}" var="delivery" varStatus="loop">
												<option value="${loop.index}" >${delivery.address}</option>												
											</c:forEach>
									 </select> <br> 
									 <!--  input type="submit" name="Потвърди"-->
									
				</form>
			</c:if>
			
	
<%-- 	<div align="center">

		<form action="/deliveryInfo/new" method="post">
			<h3 align="center">Получател</h3>
			<!--  form action="getNamesFromUser" method="POST"
				style="text-align: center;">
				<br> Вземи от профила<input type="radio">
			</form-->
			Име <input type="text" name="firstName" value="${ sessionScope.user.firstName }" required><br>
			Фамилия <input type="text" name="lastName" value="${ sessionScope.user.lastName  }" required><br>
			Телефон <input type="text" name="phone" value="${ delivery.recieverPhone }" required><br>

			<h3 align="center">Адрес</h3>
				Град <select name='city'>
						<c:forEach items="${requestScope.cities}" var="city">
							<option value="${city}">${city}</option>
						</c:forEach>
					</select> <br> 
				Пощенски код <input type ="number" name="zip" min="1000" max ="9999" value="${ delivery.zipCode }" required> <br> 				
				Адрес <input type ="text" name="address" value="${ delivery.address }" required> <br>
				Бележка <input type="text" name="note" value="${ delivery.notes }"> <br> 
			<input type="submit" value="Потвърди">
		</form>
	</div> --%>
	
	<div align="center">		
		<f:form commandName="deliveryInfo" action="<c:url value='/cart/newDeliveryInfo'/>"> 
			
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

	</div>





</body>
</html>