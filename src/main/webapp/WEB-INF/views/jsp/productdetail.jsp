<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>


	<jsp:include page="header.jsp"></jsp:include>

	<c:if test="${ sessionScope.products != null } ">
		<jsp:include page="categories.jsp"></jsp:include>
	</c:if>
	
	<div id="item">
		<h1>${ productCurrent.name }</h1>

		<img src="<c:url value='D:/images/products/${ productCurrent.image }'/>" alt="${ productCurrent.description }" /><br />
				
			<c:if test="${ productCurrent.discount == 0 }">
				<ul>
					<li class="price regular-price">
						<span> 
							<fmt:formatNumber type="number" pattern="#####.##" value="${ productCurrent.price }" />лв.
						</span>
					</li>
				</ul>
			</c:if>
			<c:if test="${  productCurrent.discount != 0 }">								
					<img src="<c:url value='/img/buttons/sale_piggy.png'/>" alt="SALE" title="SALE" width="auto" height="40" align="left">																	
					<ul>
						
						<li class="price regular-price">
							
							<del>
								<fmt:formatNumber type="number" pattern="#####.##" value="${ productCurrent.price }" /> лв.
							</del>
							
						</li>										
						<li class="price special-price" style="color: maroon">
							
							<fmt:formatNumber type="number" pattern="#####.##" value="${productCurrent.calcDiscountedPrice() }" /> лв.
						
						</li>
					</ul>								
			</c:if>
		
	</div>
	<div class="description">
		<label>Описание:</label>
		<p>${ productCurrent.description }</p>
		
	
	</div>
	<c:if test="${!sessionScope.user.isAdmin() }">
		<div class="rating">
			<p>
				<img src="<c:url value='/img/buttons/has_rating.png'/>"
						alt="rating" title="rating" width="2%" height="auto">
						<fmt:formatNumber	type="number" pattern="#####.##" value="${ productCurrent.rating }" />				
				<img src="<c:url value='/img/buttons/people.png'/>"
						alt="Voted:" title="voted" width="2%" height="auto">
						
						<fmt:formatNumber type="number" pattern="#####.##" value="${ productCurrent.countRating }" /> 
								 
				 	
				<c:if test="${( (sessionScope.user == null) || (sessionScope.user != null && (!sessionScope.isFavorite)))}">
					<a href="<c:url value='/products/addFavorite'/>">
						<img src="<c:url value='/img/buttons/addFav.png'/>"
						alt="ADD IN FAVORITES" title="addInFavorits" width="2%"
						height="auto">
					</a>	
				</c:if>
	
				<c:if
					test="${ sessionScope.user != null &&  sessionScope.isFavorite}">
					<a href="<c:url value='/products/removeFavorite'/>">
						<img src="<c:url value='/img/buttons/removeFav.png'/>"
						alt="REMOVE FROM FAVORITES" title="removeFromFavorits" width="2%"
						height="auto">
					</a>					
				</c:if>				 
			</p>
			<br>		
		</div>
			
			<c:if test="${sessionScope.user != null && sessionScope.ratingFromUser == -1}">
				<f:form commandName="newrating" action="${pageContext.request.contextPath}/products/addRating"> 
					Коментар:<f:input path="comment" />				
					<br>				
					
					Рейтинг<f:select path='rating'>
						<f:option value="1">1</f:option>
						<f:option value="2">2</f:option>
						<f:option value="3">3</f:option>
						<f:option value="4">4</f:option>
						<f:option value="5">5</f:option>
					</f:select>					
					<input type="image" src="<c:url value='/img/buttons/add_rating.png'/>" title="ADD REVIEW" alt="ADD REVIEW" width="2%" height="auto" />
				</f:form>

			</c:if>
			
				
			<c:if test="${sessionScope.user != null &&( sessionScope.ratingFromUser != null && sessionScope.ratingFromUser !=-1)}">
				Your rate: ${ sessionScope.ratingFromUser}	   
				
				<img src="<c:url value='/img/buttons/has_rating.png'/>"
				alt="your rating" title="RatingAdded" width="2%" height="auto">
			</c:if>
			<br>
			<br>
			<a href="<c:url value='/products/addInCart/${productCurrent.id}'/>">
				<img src="<c:url value='/img/buttons/shopping_cart - color.png'/>"
				alt="ADD IN CART" title="addInCart" width="auto" height="40">
			</a>
		
		</c:if>

		<c:if test="${sessionScope.user.isAdmin() }">
			<a href='<c:url value='/user/admin/removeProduct'></c:url>'><button>Изтрий
					артикул</button></a>
			<br>
			

			<form action="${pageContext.request.contextPath}/user/admin/quantity"
				method="post">
				<input type="number" name="quantity" placeholder="Количество"
					min="1"> <input type="submit" value="Добави">
			</form>
			<form action="${pageContext.request.contextPath}/user/admin/discount"
				method="post">
				<input type="number" name="discount" placeholder="Отстъпка %"
					min="0" max="99"> <input type="submit" value="Добави">
			</form>

		</c:if>

	

</body>
</html>