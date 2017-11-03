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
					<h4 align="center">Избери от предишни доставки <br><br><br>
						 <select name='idxDeliveryInfo'>											
								<c:forEach items="${sessionScope.deliveries}" var="delivery" varStatus="loop">
									<option value="${loop.index}" >${delivery.address}</option>												
								</c:forEach>
						 </select> 									
						 <input type="image" name="submit" src="<c:url value='/img/buttons/update.png'/>" alt="UPDATE" title="Update">		
					 </h4> 								
				</form>
			</c:if>
		<br><br>
	
	</div> 
	
	<div align="center">		<%-- action="${pageContext.request.contextPath}/cart/newOrder" --%>
		<f:form commandName="deliveryInfo" > 			
			<h3 align="left">Получател</h3>
					Име:<f:input path="recieverFirstName" />
					<f:errors path="recieverFirstName" cssClass="error" style = "color:red"></f:errors><br>	
					Фамилия:<f:input path="recieverLastName" />
					<f:errors path="recieverLastName" cssClass="error" style = "color:red"></f:errors><br>	
					Телефон: +359<f:input path="recieverPhone" type="number"/>
					<f:errors path="recieverPhone" cssClass="error" style = "color:red"></f:errors><br>				
			<h3 align="left">Адрес</h3>	
					Град<f:select path='city' >
							<f:options items="${sessionScope.cities}"/>													
						</f:select>	
						<f:errors path="city" cssClass="error" style = "color:red"></f:errors><br>					
					Пощенски код:<f:input path="zipCode" min="1000" max ="9999"  type="number"/>
					<f:errors path="zipCode" cssClass="error" style = "color:red"></f:errors><br>	
					Адрес:<f:input path="address" />
					<f:errors path="address" cssClass="error" style = "color:red"></f:errors><br>		
					Бележка:<f:input path="notes" /><br>	
					<input type="submit">
		</f:form>
	</div>
</body>
</html>