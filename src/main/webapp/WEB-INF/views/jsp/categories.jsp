<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%> 
 <%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>												

	
	
	<c:if test="${ sessionScope.animalId != null }">
	
	
	
		<a href="<c:url value='/products/animal/${sessionScope.animalId }/category/1/0'/>" style="text-decoration: none">ХРАНА</a>  &nbsp;
		<a href="<c:url value='/products/animal/${sessionScope.animalId }/category/2/0'/>" style="text-decoration: none">АКСЕСОАРИ</a>  &nbsp;
		
		<c:if test="${ sessionScope.animalId == 2 || sessionScope.animalId == 3 }">
			<a href="<c:url value='/products/animal/${sessionScope.animalId }/category/3/0'/>" style="text-decoration: none">КОЗМЕТИКА</a>  &nbsp;
		</c:if>
		
		<c:if test="${ sessionScope.animalId == 1 || sessionScope.animalId == 2 || sessionScope.animalId == 3 }">
			<a href="<c:url value='/products/animal/${sessionScope.animalId }/category/4/0'/>" style="text-decoration: none">ХИГИЕНА</a>  &nbsp;
		</c:if>
		
		<c:if test="${ sessionScope.animalId == 1 || sessionScope.animalId == 5 || sessionScope.animalId == 6 }">
			<a href="<c:url value='/products/animal/${sessionScope.animalId }/category/5/0'/>" style="text-decoration: none">АКВАРИУМИ И КЛЕТКИ</a>  &nbsp;
		</c:if><br><br>
		
		<jsp:include page="subCategories.jsp"></jsp:include>
		
	</c:if>
