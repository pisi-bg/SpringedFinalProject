<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

	<jsp:include page="header.jsp"></jsp:include>
	
	<h2>ФОРМА ЗА РЕГИСТРАЦИЯ</h2>
	<h3>МОЛЯ, ПОПЪЛНЕТЕ ВСИЧКИ ПОЛЕТА:</h3>
	<br>
		
	<c:if test="${sessionScope.regError }">
		<h3 class="has-error" >Грешка при регистрацията, моля прабвайте с друг мейл.</h3>
	</c:if>

	<h5 class="center" style="text-align: center;">
		<f:form commandName="user">
			
			Име:<f:input path="firstName" /><br>
			<f:errors path="firstName" cssClass="error" style = "color:red"></f:errors><br><br>
			Фамилия:<f:input path="lastName" /><br>
			<f:errors path="lastName" cssClass="error" style = "color:red"></f:errors><br><br>
			Имейл:<f:input path="email" /><br>
			<f:errors path="email" cssClass="error" style = "color:red"></f:errors><br><br>
			Парола:<f:password path="password" /><br>
			<f:errors path="password" cssClass="error" style = "color:red"></f:errors><br><br>
			Пол: <f:radiobutton path="isMale" value="true" label="мъж" />
			<f:radiobutton path="isMale" value="false" label="жена"/>	<br><br>
			<input type="image"  src="<c:url value='/img/buttons/register.png'/>" alt="РЕГИСТРИРАЙ СЕ" title="РЕГИСТРИРАЙ СЕ" width="6%" height="auto" >
			<!-- <input type="submit" value="РЕГИСТРИРАЙ МЕ"> -->			
		</f:form>		
	</h5>
</body>
</html>