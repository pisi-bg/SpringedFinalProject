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
				
				<p>подреди по</p>
				<a class="pisi-button_yellow" href="${pageContext.request.contextPath}/products/sort/name/${asc}">име(а-я)</a>
				<a class="pisi-button_yellow" href="${pageContext.request.contextPath}/products/sort/name/${asc}">име(я-а)</a>
				<a class="pisi-button_yellow" href="${pageContext.request.contextPath}/products/sort/name/${asc}">цена(възх.)</a>
				<a class="pisi-button_yellow" href="${pageContext.request.contextPath}/products/sort/name/${asc}">цена(низх.)</a>
				
			</div>		
	
			<div class="pisi-grid_products">
				<ul>
					<c:forEach items="${ sessionScope.products }" var="pro">
							<li class="col-xs-12 col-md-3">
								<div class="wrap">
								
									<a href="#" class="">
										<img src="<c:url value='${ pro.image }'/>"  alt="${ pro.description }" />
									</a>
									<a href="#" class="pisi-grid_products_title">
										<h3>
											${pro.name}
										</h3>
									</a>
									<div class="more-info">
										<span class="price">
											
											<c:if test="${ productCurrent.discount == 0 }">
												<ul>
													<li class="price regular-price">												
														<fmt:formatNumber type="number" pattern="#####.##" value="${ productCurrent.price }" />лв.												
													</li>
												</ul>
											</c:if>
											<c:if test="${ productCurrent.discount != 0 }">				
												<ul >												
													<li class="price old-price">
														<span> 														
															<fmt:formatNumber type="number" pattern="#####.##" value="${ productCurrent.price }" /> лв.														
														</span>
													</li>										
													<li class="price special-price" style="color: maroon">												
															<fmt:formatNumber type="number" pattern="#####.##" value="${productCurrent.calcDiscountedPrice() }" /> лв.													
													</li>
												</ul>								
											</c:if>
											
										</span>
										<a href="#" class="pisi-button_dark">Виж повече</a>
										
										
									</div>
								</div>
							</li>
			
					</c:forEach>
				</ul>
			</div>			
		</c:if>	
<!-- pisi-wrap -->
	
</body>
</html>