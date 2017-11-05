<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
	
	<jsp:include page="header.jsp"></jsp:include><br><br><br>

	<div class="col-xs-12 col-md-4 no-padding">
		<img src="<c:url value='/img/error_kotka.png'/>" alt="error" title="Error"  width="100%"/>	
	</div>
	
	<div class="col-xs-12 col-md-6 " style="float:center">
		<h1 class="has-error">
		 	<c:out value="${requestScope.error }"></c:out>
		</h1>	
	</div>	
</body>
</html>