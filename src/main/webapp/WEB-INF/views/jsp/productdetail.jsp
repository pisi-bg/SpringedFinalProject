<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>


	<jsp:include page="header.jsp"></jsp:include>

	<c:if test="${ sessionScope.products != null } ">
		<jsp:include page="categories.jsp"></jsp:include>
	</c:if>
	
	<div class="container">
	
		<div class="pisi-item">
				<h1>${ productCurrent.name }</h1>
	
				<div class="col-xs-12 col-md-6 image" >
					<img src="<c:url value="/products/image/${ productCurrent.id }"/>" alt="${ productCurrent.description }" class="img-big" /><br />
				</div>
				
				<div class="col-xs-12 col-md-6 more-info">
					<div class="description">
						<h2 class="left">Описание:</h2>
						<h3 class="left">${productCurrent.description }</h3>
						<br><br><br>
					</div>
					<c:if test="${ productCurrent.discount == 0 }">
						<span class="price">			
							<fmt:formatNumber type="number" pattern="#####.##" value="${ productCurrent.price }" />лв.
						</span>								
					</c:if>
					<c:if test="${ productCurrent.discount != 0 }">
						<span class="old-price"> 														
							<fmt:formatNumber type="number" pattern="#####.##" value="${ productCurrent.price }" />лв.														
						</span>								
						<span class="special-price">												
								<fmt:formatNumber type="number" pattern="#####.##" value="${productCurrent.calcDiscountedPrice() }" /> лв.													
						</span>						
					</c:if>
					<br><br><br>
					<div class="rating">
						<p>
							<img src="<c:url value='/img/buttons/has_rating.png'/>"
									alt="rating" title="rating" width="5%" height="auto">
									<fmt:formatNumber	type="number" pattern="#####.##" value="${ productCurrent.rating }" />				
							<img src="<c:url value='/img/buttons/people.png'/>"
									alt="Voted:" title="voted" width="5%" height="auto">
									<fmt:formatNumber type="number" pattern="#####.##" value="${ productCurrent.countRating }" /> 
							<c:if test="${( (sessionScope.user == null) || (sessionScope.user != null && (!sessionScope.isFavorite)))}">
								<a href="<c:url value='/products/addFavorite'/>">
									<img src="<c:url value='/img/buttons/addFav.png'/>"
									alt="ADD IN FAVORITES" title="addInFavorits" width="5%"
									height="auto">
								</a>	
							</c:if>
							<c:if
								test="${ sessionScope.user != null &&  sessionScope.isFavorite}">
								<a href="<c:url value='/products/removeFavorite'/>">
									<img src="<c:url value='/img/buttons/removeFav.png'/>"
									alt="REMOVE FROM FAVORITES" title="removeFromFavorits" width="5%"
									height="auto">
								</a>					
							</c:if>				 
						</p>
						<br>		
					</div>
						<c:if test="${sessionScope.user != null && sessionScope.ratingFromUser == -1}">
							<h5>
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
									<input type="image" src="<c:url value='/img/buttons/add_rating.png'/>" title="ADD REVIEW" alt="ADD REVIEW" width="5%" height="auto" />
								</f:form>
							</h5>
						</c:if>				
						
					<c:if test="${sessionScope.user != null &&( sessionScope.ratingFromUser != null && sessionScope.ratingFromUser !=-1)}">
						<h5>Your rate: ${ sessionScope.ratingFromUser}</h5>	
							<img src="<c:url value='/img/buttons/has_rating.png'/>"
							alt="your rating" title="RatingAdded" width="5%" height="auto">
					</c:if>
					<br>
				</div> <!-- more-info -->
				<br><br><br>
				<div class="addincart">
					<br>
					<a href="<c:url value='/products/addInCart/${productCurrent.id}'/>">
						<img src="<c:url value='/img/buttons/shopping_cart - color.png'/>"
						alt="ADD IN CART" title="addInCart" width="15%" height="auto">
					</a>
				</div>  <!-- addincart -->
			</div><!-- pisi-item -->
			<br><br><br>
			<%-- </c:if> --%>
			
			<div class="col-xs-12 col-md-4 admin">
				<c:if test="${sessionScope.user.isAdmin() }">
					<h3 class ="left">
						АДМИНИСТРАТОРСКИ ПАНЕЛ <br>
							<h5>
								<a href='<c:url value='/user/admin/removeProduct'></c:url>'>
								<button>Изтрий артикул</button></a>
								<br>
								<form action="${pageContext.request.contextPath}/user/admin/quantity" method="post">
									<input type="number" name="quantity" placeholder="Количество" min="1" style="width: 150px;"> 
									<input type="submit" value="Добави">
								</form>
								
								<form action="${pageContext.request.contextPath}/user/admin/discount" method="post">
									<input type="number" name="discount" placeholder="Отстъпка %" min="0" max="99" style="width: 150px;">
									<input type="submit" value="Добави">
								</form>
							</h5>	
					</h3>
				</c:if>
			</div> <!-- admin	 -->
			
			<div class="col-xs-12 col-md-6  comments">
				<c:if test="${sessionScope.comments.isEmpty() }">
					<p>Няма коментари. Бъди първият.</p>
				</c:if>
			<!-- 	<br><br><br><br><br><br> -->
		
				<c:if test="${sessionScope.comments  != null}">
					<c:if test="${!sessionScope.comments.isEmpty() }">
						<table border="1">
							<thead class="">
								<tr>
									<th class="cart_product first_item">Потребител</th>
									<th class="cart_description item">Рейтинг</th>											
									<th class="cart_quantity item text-center">Коментар</th>							
									<th class="cart_quantity item text-center">Дата</th>
								</tr>
							</thead>						
							<tbody>			
								<c:forEach items="${ sessionScope.comments }" var="com">
									<tr>
										<td>${ com.userEmail }</td>
										<td>${com.rating }</td>
										<td>${com.comment }</td>
										<td>${com.dateTime }</td>																									
									</tr>
								</c:forEach>	
							</tbody>
						</table>
					</c:if>
				</c:if>
		</div> <!-- "comments"	 -->
		
	</div> <!-- container" -->
</body>
</html>