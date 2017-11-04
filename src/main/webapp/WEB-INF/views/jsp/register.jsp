<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>

	<jsp:include page="header.jsp"></jsp:include>
	
	<h2>ФОРМА ЗА РЕГИСТРАЦИЯ</h2>
	<h3>МОЛЯ, ПОПЪЛНЕТЕ ВСИЧКИ ПОЛЕТА:</h3>
	
		
		<c:if test="${sessionScope.regError }">
			<h3 class="has-error" >Грешка при регистрацията, моля прабвайте с друг мейл.</h3>
		</c:if>
	
		<h5 class="center">
			<f:form commandName="user">
				
				Име:<f:input path="firstName" />
				<f:errors path="firstName" cssClass="error" style = "color:red"></f:errors><br>
				Фамилия:<f:input path="lastName" />
				<f:errors path="lastName" cssClass="error" style = "color:red"></f:errors><br>
				Имейл:<f:input path="email" />
				<f:errors path="email" cssClass="error" style = "color:red"></f:errors><br>
				Парола:<f:password path="password" />
				<f:errors path="password" cssClass="error" style = "color:red"></f:errors><br>
				Пол: <f:radiobutton path="isMale" value="true" label="мъж" />
				<f:radiobutton path="isMale" value="false" label="жена"/>	<br>
				<input type="submit" value="РЕГИСТРИРАЙ МЕ">			
			</f:form>		
			<a href= "<c:url value='/index'/>"><button>Home</button></a>
		</h5>
</body>
</html>