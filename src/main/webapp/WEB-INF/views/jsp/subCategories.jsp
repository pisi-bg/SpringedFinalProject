<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<!--!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd"-->
<!DOCTYPE html SYSTEM "about:legacy-compat">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>

				<!-- AQUARISTICS -->
	<c:if test="${ sessionScope.animalId == 1 }">
		<c:if test="${ requestScope.catId == 1 }">		
			<a href="<c:url value='/products/subcategory/animal/${sessionScope.animalId}/catId/1/subcatId/1'/>" style="text-decoration: none">ХРАНА</a> &nbsp;
		</c:if>
		
		<!-- here we place other sub categories like above when we fill DB with testing info and subId="sub category id in the db" -->		
		
	</c:if>
	
			<!-- CATS -->
	<c:if test="${ sessionScope.animalId == 2 }">
					<!-- FOOD  -->
		<c:if test="${ requestScope.catId == 1 }">		
			<a href="<c:url value='/products/subcategory/animal/${sessionScope.animalId }/catId/1/subcatId/6/0'/>" style="text-decoration: none">КОНСЕРВИРАНА ХРАНА</a> &nbsp;
		</c:if>
		<c:if test="${ requestScope.catId == 1 }">
			<a href="<c:url value='/products/subcategory/animal/${sessionScope.animalId}/catId/1/subcatId/15/0'/>" style="text-decoration: none">ХРАНА НА ГРАНУЛИ</a> &nbsp;
		</c:if>
				<!-- ACCESSOARIES -->
		<c:if test="${ requestScope.catId == 2 }">
			<a href="<c:url value='/products/subcategory/animal/${sessionScope.animalId}/catId/2/subcatId/8/0'/>" style="text-decoration: none">ТРАНСПОРТ</a> &nbsp;
		</c:if>
		<c:if test="${ requestScope.catId == 2 }">
			<a href="<c:url value='/products/subcategory/animal/${sessionScope.animalId}/catId/2/subcatId/11/0'/>" style="text-decoration: none"> ПОВОДИ И КАИШКИ</a> &nbsp;
		</c:if>
		<c:if test="${ requestScope.catId == 2 }">
			<a href="<c:url value='/products/subcategory/animal/${sessionScope.animalId}/catId/2/subcatId/12/0'/>" style="text-decoration: none">ДРЕХИ</a> &nbsp;
		</c:if>
					<!-- COSMETICS -->
		<c:if test="${ requestScope.catId == 3 }">
			<a href="<c:url value='/products/subcategory/animal/${sessionScope.animalId}/catId/3/subcatId/7/0'/>" style="text-decoration: none">ЧЕТКИ И ГРЕБЕНИ</a> &nbsp;
		</c:if>			<!-- To be listed more -->
				
				<!-- HYGIENE -->
		<c:if test="${ requestScope.catId == 4 }">
			<a href="<c:url value='/products/subcategory/animal/${sessionScope.animalId}/catId/4/subcatId/56/0'/>" style="text-decoration: none">КОЗМЕТИКА</a> &nbsp;
		</c:if>						<!-- Toilets is just an example we don't have it in the database -->
		
	</c:if>
	
			<!-- DOGS -->
	<c:if test="${ sessionScope.animalId== 3 }">
					<!-- FOOD  -->
		<c:if test="${ requestScope.catId == 1 }">
			<a href="<c:url value='/products/subcategory/animal/${sessionScope.animalId}/catId/1/subcatId/6/0'/>" style="text-decoration: none">КОНСЕРВИРАНА ХРАНА</a> &nbsp;
			
		</c:if>
		<c:if test="${ requestScope.catId == 1 }">
			<a href="<c:url value='/products/subcategory/animal/${sessionScope.animalId}/catId/1/subcatId/15/0'/>" style="text-decoration: none">ХРАНА НА ГРАНУЛИ</a> &nbsp;
		</c:if>
				<!-- ACCESSOARIES -->
		<c:if test="${ requestScope.catId == 2 }">
			<a href="<c:url value='/products/subcategory/animal/${sessionScope.animalId}/catId/2/subcatId/8/0'/>" style="text-decoration: none">ТРАНСПОРТ</a> &nbsp;
		</c:if>
		<c:if test="${ requestScope.catId == 2 }">
			<a href="<c:url value='/products/subcategory/animal/${sessionScope.animalId}/catId/2/subcatId/11/0'/>" style="text-decoration: none">ПОВОДИ И КАИШКИ</a> &nbsp;
		</c:if>
		<c:if test="${ requestScope.catId == 2 }">
			<a href="<c:url value='/products/subcategory/animal/${sessionScope.animalId}/catId/2/subcatId/12/0'/>" style="text-decoration: none">ДРЕХИ</a> &nbsp;
		</c:if>
					<!-- COSMETICS -->
		<c:if test="${ requestScope.catId == 3 }">
			<a href="<c:url value='/products/subcategory/animal/${sessionScope.animalId}/catId/3/subcatId/7/0'/>" style="text-decoration: none">ЧЕТКИ И ГРЕБЕНИ</a> &nbsp;
		</c:if>			<!-- To be listed more -->
				
				<!-- HYGIENE -->
		<c:if test="${ sessionScope.catId == 4 }">
			<a href="<c:url value='/products/subcategory/animal/${sessionScope.animalId}/catId/4/subcatId/56/0'/>" >КОЗМЕТИКА</a> &nbsp;
		</c:if>						<!-- Toilets is just an example we don't have it in the database -->
		
	</c:if>
	
</body>
</html>