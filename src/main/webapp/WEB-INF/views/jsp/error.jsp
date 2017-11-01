<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>ERROR</title>
</head>
<body>
	
	<jsp:include page="header.jsp"></jsp:include><br><br><br>


	<div class="col-xs-12 col-md-4 no-padding">
		<img src="<c:url value='/img/error_kotka.png'/>" alt="error" title="Error"  width="100%"/>	
	</div>
	
	 <dir class ="message">
	 	<c:out value="${requestScope.error }"></c:out>
	 </dir>
	

</body>
</html>