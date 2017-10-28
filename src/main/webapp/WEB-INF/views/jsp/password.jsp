<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Insert title here</title>
</head>
<body>
	
	<jsp:include page="header.jsp"></jsp:include>
	
	<h3>Моля, въведете имейла си и ще получите съобщение на вашата поща.</h3>
	<h4>Ако не получите такова, моля пише те на pisi.bg.shop@gmail.com и екипът на pisi.bg ще се свърже с вас</h4>
	<f:form>
		<input type="email" name="email" required> &nbsp;&nbsp;
		<input type="submit" value="Изпрати">
	</f:form>

</body>
</html>