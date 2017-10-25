<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
  
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>

	<a href="?language=bg">Български</a>
	<a href="?language=en">English</a>
	<a href="?language=fr">Francais</a>
	<a href="?language=es">Espaniol</a>

	<f:form commandName="diunerche" >
	<s:message code="bread"></s:message>:<f:input path="hlebche" /><br>
	<s:message code="sos"></s:message>:<f:input path="sosche" /><br>
	<s:message code="gramaj"></s:message>:<f:input path="gramaj" /><br>
	<s:message code="kartofi"></s:message>:<f:input path="kartofi" /><br>
	<s:message code="meso"></s:message>:<f:input path="meso" /><br>
	<input type="submit" value="Save diuner!">
	</f:form>
</body>
</html>