<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://sargue.net/jsptags/time" prefix="javatime" %>


	<jsp:include page="header.jsp"></jsp:include>

	<c:if test="${ sessionScope.products != null } ">
		<jsp:include page="categories.jsp"></jsp:include>
	</c:if>
	
	<div class="container">
	
		<div class="pisi-item">
				<h1>${ productCurrent.name }</h1>
	
				<div class="col-xs-12 col-md-4 image" >
					<img id="biggg" src="<c:url value="/products/image/${ productCurrent.id }"/>" alt="${ productCurrent.description }" class="big"
					 style="width: auto; max-width: 400px; height: 400px; min-height: 400px; background-size:cover; max-height: 400px; position:center;"/><br>
				</div>
				
				<div class="col-xs-12 col-md-5 more-info">
					<div class="description">
						<h2 class="left" style="color: #c4463e;">Описание:</h2>
						<h3 class="left">${productCurrent.description }</h3>						
						<h4 class="" style="color: #c4463e; text-align:left;">Цена:</h4>
						 <div  style="text-align: left;"> 
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
						 </div>
					</div>
					<br><br><br>
					<div class="rating">
						<h3>
							<img src="<c:url value='/img/buttons/has_rating.png'/>"
									alt="rating" title="rating" width="10%" height="auto">
									<fmt:formatNumber	type="number" pattern="#####.##" value="${ productCurrent.rating }" />				
							<img src="<c:url value='/img/buttons/people.png'/>"
									alt="Voted:" title="voted" width="10%" height="auto">
									<fmt:formatNumber type="number" pattern="#####.##" value="${ productCurrent.countRating }" /> 
							<c:if test="${( (sessionScope.user == null) || (sessionScope.user != null && (!sessionScope.isFavorite)))}">
								<a href="<c:url value='/products/addFavorite'/>">
									<img src="<c:url value='/img/buttons/addFav.png'/>"
									alt="ADD IN FAVORITES" title="addInFavorits" width="10%"
									height="auto">
								</a>	
							</c:if>
							<c:if
								test="${ sessionScope.user != null &&  sessionScope.isFavorite}">
								<a href="<c:url value='/products/removeFavorite'/>">
									<img src="<c:url value='/img/buttons/removeFav.png'/>"
									alt="REMOVE FROM FAVORITES" title="removeFromFavorits" width="10%"
									height="auto">
								</a>					
							</c:if>				 
						</h3>
						<br>		
					</div>
						<c:if test="${sessionScope.user != null && sessionScope.ratingFromUser == -1 && error == null}">
							<h4>
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
									<input type="image" src="<c:url value='/img/buttons/add_rating.png'/>" title="добави рейтинг" alt="ADD REVIEW" width="10%" height="auto" />
								</f:form>
							</h4>
						</c:if>				
						
					<c:if test="${sessionScope.user != null &&( sessionScope.ratingFromUser != null && sessionScope.ratingFromUser !=-1)}">
						<h4>Your rate: ${ sessionScope.ratingFromUser}
							<img src="<c:url value='/img/buttons/has_rating.png'/>"
							alt="your rating" title="RatingAdded" width="10%" height="auto">
						</h4>	
					</c:if>
					<br>
					<h5 style="text-align: center">
						<br>							
						<c:if test="${ productCurrent.inStock !=0 }">					
							<a href="<c:url value='/products/addInCart/${productCurrent.id}'/>">
								<img src="<c:url value='/img/buttons/shopping_cart - color.png'/>"
								alt="ADD IN CART" title="addInCart" width="35%" height="auto">
							</a>					
						</c:if>
						<c:if test="${ productCurrent.inStock ==0 }">	
							<p class="has-error">  ПРОДУКТЪТ Е ИЗЧЕРПАН В МОМЕНТА </p>
						</c:if>
					</h5>
					
				<c:if test="${sessionScope.user.isAdmin() }">
					<h3 class ="left">
						АДМИНИСТРАТОРСКИ ПАНЕЛ <br>
					</h3>
					<h5>
						<a href='<c:url value='/user/admin/removeProduct'></c:url>'>
							<button>Изтрий артикул</button>
						</a>
						<br>

						<form action="${pageContext.request.contextPath}/user/admin/quantity" method="post">
							<input type="number" name="quantity" placeholder="Количество" min="1" max ="200000"style="width: 150px;"> 
							<input type="submit" value="Добави">
						</form>
						<form action="${pageContext.request.contextPath}/user/admin/discount" method="post">
							<input type="number" name="discount" placeholder="Отстъпка %" min="0" max="99" style="width: 150px;">
							<input type="submit" value="Добави">
						</form>
					</h5>	
				</c:if>
				</div> <!-- more-info -->
			</div><!-- pisi-item -->
			
			<div class="col-xs-12 col-md-6 comments" style="float:left;" >
				<br><br><br>
				<c:if test="${sessionScope.comments.isEmpty() }">
					<p>Няма коментари. Бъди първият.</p>
				</c:if>
				<c:if test="${sessionScope.comments  != null}">
					<c:if test="${!sessionScope.comments.isEmpty() }">
						<table border="1"  >
							<thead class="">
									<tr>
										<th>Дата</th>
										<th>Потребител</th>																				
										<th>Коментар</th>	
									</tr>
							</thead>						
							<tbody>			
								<c:forEach items="${ sessionScope.comments }" var="comment">
									<tr>
										<td width = "130px">										
											<fmt:parseDate value="${comment.dateTime}" pattern="yyyy-MM-dd'T'HH:mm" var="parsedDateTime" type="both" />
											<fmt:formatDate pattern="dd.MM.yyyy HH:mm" value="${ parsedDateTime }" />									
										</td>
										<td>${ comment.userName }</td>
										<td align="left">
											<fmt:formatNumber	type="number" pattern="#####.##" value="${comment.rating }" />	
											<img src="<c:url value='/img/buttons/has_rating.png'/>" alt="rating" title="rating" width="10%" height="auto">
											<p align="center">
												${comment.comment }
											</p>
										</td>																							
									</tr>
								</c:forEach>	
							</tbody>
						</table> <br>
					</c:if>
				</c:if>
		</div> <!-- "comments"	 -->
		
	</div> <!-- container" -->
</body>
</html>