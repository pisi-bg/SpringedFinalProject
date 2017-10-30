<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>	
	<h2>ПРОМЕНИ ИНФОРМАЦИЯ В ПРОФИЛ:</h2>
	<h3>попълни всички полета:</h3>
	
	<f:form commandName="user" >
		Име :<f:input path="firstName"/><br>
		Фамилия:<f:input path="lastName"/><br>
		email:<f:input path="email"/><br>
		парола:<f:password path="password"/><br>
		пол: <f:radiobutton path="isMale" value="true" label="мъж"/>
		<f:radiobutton path="isMale" value="false" label="жена"/>	<br>
		<input type="submit" value="Update">			
	</f:form>	
		
<%-- 	<a href= "<c:url value='/index'/>"><button>Home</button></a> --%>
	
</body>
</html>