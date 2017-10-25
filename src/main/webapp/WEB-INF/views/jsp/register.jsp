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
	<h1>Please fill all fields:</h1>
	
	<f:form commandName="user">
		First name:<f:input path="firstName"/><br>
		Last name:<f:input path="lastName"/><br>
		Email:<f:input path="email"/><br>
		Password:<f:password path="password"/><br>
		Gender: <f:radiobutton path="isMale" value="true" label="Male"/>
		<f:radiobutton path="isMale" value="false" label="Female"/>	<br>
		<input type="submit" value="Register">			
	</f:form>		
	<a href= "<c:url value='/index'/>"><button>Home</button></a>
	
</body>
</html>