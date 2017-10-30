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
	
	<f:form commandName="user">
		Име:<f:input path="firstName"/><br>
		Фамилия:<f:input path="lastName"/><br>
		Майл:<f:input path="email"/><br>
		Парола:<f:password path="password"/><br>
		Пол: <f:radiobutton path="isMale" value="true" label="мъж"/>
		<f:radiobutton path="isMale" value="false" label="жена"/>	<br>
		<input type="submit" value="РЕГИСТРИРАЙ МЕ">			
	</f:form>		
	<a href= "<c:url value='/index'/>"><button>Home</button></a>
	
</body>
</html>