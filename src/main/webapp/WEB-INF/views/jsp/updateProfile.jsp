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
		
		
		
		<f:form commandName="user">				
			Име:<f:input path="firstName"/>
			<f:errors path="firstName" cssClass="error" style = "color:red"></f:errors><br>
			Фамилия:<f:input path="lastName"/>
			<f:errors path="lastName" cssClass="error" style = "color:red"></f:errors><br>
			Имейл:<f:input path="email"/>
			<f:errors path="email" cssClass="error" style = "color:red"></f:errors><br>
			Парола:<f:password path="password" placeholder="Минимум 6 символа..."/>
			<f:errors path="password" cssClass="error" style = "color:red" ></f:errors><br>
			Пол: <f:radiobutton path="isMale" value="true" label="мъж"/>
			<f:radiobutton path="isMale" value="false" label="жена"/>	<br>
			<input type="submit" value="Промени">			
		</f:form>
		
<%-- 	<a href= "<c:url value='/index'/>"><button>Home</button></a> --%>
	
</body>
</html>