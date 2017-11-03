<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%> 
 <%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>												

	
	
	<c:if test="${ sessionScope.animalId != null }">
	
		<div class="pisi-nav_main-cat">
			<div class="container">
				<nav>	
					<c:forEach items="${ sessionScope.categoriesD }" var="parentCategory">
			   					
						 <a href="<c:url value='/products/animal/${sessionScope.animalId}/category/${parentCategory.value}/0'/>" > ${parentCategory.key} </a>
								
					</c:forEach>	                        
				</nav>
			</div>
		</div> 
	
		<div class="pisi-nav_sub-cat" style="font-size: 12px;">
			<div class="container">
				<nav>	
					<c:forEach items="${ sessionScope.subCategories }" var="subCategory">	
						 <a href="<c:url value='/products/subcategory/animal/${sessionScope.animalId}/catId/${sessionScope.catId}/subcatId/${subCategory.value}/0'/>" > ${subCategory.key} </a>
					</c:forEach>	                        
				</nav>
			</div>
		</div>	
	
	</c:if>
	
		
		
	
