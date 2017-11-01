<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>


		<jsp:include page="header.jsp"></jsp:include><br>
		<br>		
		<jsp:include page="categories.jsp"></jsp:include>
				
		
		<c:if test="${ sessionScope.products != null }">
			

			<div class="pisi-sort_buttons">
				<c:set var="asc" value="asc"></c:set>
				<c:set var="desc" value="desc"></c:set>
				
				<%-- <a href="${pageContext.request.contextPath}/products/sort/name/${asc}" >А-Я</a>
				<a href="${pageContext.request.contextPath}/products/sort/name/${desc}"">Я-А</a>
				<a href="${pageContext.request.contextPath}/products/sort/price/${asc}" ">Въз.</a>
				<a href="${pageContext.request.contextPath}/products/sort/price/${desc}" ">Низ.</a>	 --%>
				
				<!-- Show info about your favorites products  -->
				<c:if test="${sessionScope.favorite }"><h1>Вашите любими продукти:</h1></c:if>
				<c:if test="${sessionScope.favorite != null && !sessionScope.favorite }"><h1>Нямате любими продукти!</h1></c:if>
				
				<p>подреди по</p>
				
				
				<a class="pisi-button_yellow" href="${pageContext.request.contextPath}/products/sort/name/${asc}">име(а-я)</a>
				<a class="pisi-button_yellow" href="${pageContext.request.contextPath}/products/sort/name/${desc}">име(я-а)</a>
				<a class="pisi-button_yellow" href="${pageContext.request.contextPath}/products/sort/price/${asc}">цена(възх.)</a>
				<a class="pisi-button_yellow" href="${pageContext.request.contextPath}/products/sort/price/${desc}">цена(низх.)</a>
				
			</div>		
	
			<div class="pisi-grid_products">
				<ul>
					<c:forEach items="${ productPage.getPageList() }" var="pro">
							<li class="col-xs-12 col-md-3">
								<div class="wrap">
								
									<a href="${pageContext.request.contextPath}/products/productdetail/productId/${pro.id}" class="">
										<img src="<c:url value="/products/image/${ pro.id }"/>"  alt="${ pro.description }" />
									</a>
									<a href="${pageContext.request.contextPath}/products/productdetail/productId/${pro.id}" class="pisi-grid_products_title">
										<h3>
											${pro.name}
										</h3>
									</a>
									<div class="more-info">
										<span class="price">
											
											<c:if test="${ pro.discount == 0 }">
												<ul>
													<li class="price regular-price">												
														<fmt:formatNumber type="number" pattern="#####.##" value="${ pro.price }" />лв.												
													</li>
												</ul>
											</c:if>
											<c:if test="${ pro.discount != 0 }">				
												<ul >												
													<li class="price old-price">
														<span> 														
															<fmt:formatNumber type="number" pattern="#####.##" value="${ pro.price }" /> лв.														
														</span>
													</li>										
													<li class="price special-price" style="color: maroon">												
															<fmt:formatNumber type="number" pattern="#####.##" value="${pro.calcDiscountedPrice() }" /> лв.													
													</li>
												</ul>								
											</c:if>
											
										</span>
										<a href="${pageContext.request.contextPath}/products/productdetail/productId/${pro.id}" class="pisi-button_dark">Виж повече</a>
										
										
									</div>
								</div>
							</li>
			
					</c:forEach>
				</ul>
				
				
				
				
				
				
		  		 
		  		 
		  		 
		  		 
				
				
			</div>	
			
			
				<div>
				    <span style="float:left;">
					    <c:choose>
					        <c:when test="${productPage.firstPage}"></c:when>
					        <c:otherwise><a href="${pageContext.request.contextPath}/${sessionScope.url }/-2">Prev</a></c:otherwise>
					    </c:choose>
				    </span>
				    <span>
					    <c:forEach begin="0" end="${productPage.pageCount-1}" varStatus="loop">
						    &nbsp;
						    <c:choose>
						        <c:when test="${loop.index == productPage.page}">${loop.index+1}</c:when>
						        <c:otherwise><a href="${pageContext.request.contextPath}/${sessionScope.url }/${loop.index}">${loop.index+1}</a></c:otherwise>
						    </c:choose>
					    &nbsp;
					    </c:forEach>
				    </span>
				    <span>
					    <c:choose>
					        <c:when test="${productPage.lastPage}"></c:when>
					        <c:otherwise><a href="${pageContext.request.contextPath}/${sessionScope.url }/-1">Next</a></c:otherwise>
					    </c:choose>
				    </span>
		  		 </div>		
		</c:if>	
<!-- pisi-wrap -->
	

</body>
</html>