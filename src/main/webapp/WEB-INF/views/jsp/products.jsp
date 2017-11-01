<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>


		<jsp:include page="header.jsp"></jsp:include><br>
		<br>		
		<jsp:include page="categories.jsp"></jsp:include>
		
		
		<!-- <div class="pisi-wrap-container"> -->
			
			<c:if test="${ sessionScope.products != null }">
				<div  class="w3-container" >	
					<div class="pisi-sort_buttons">
						<c:set var="asc" value="asc"></c:set>
						<c:set var="desc" value="desc"></c:set>
						
						<%-- <a href="${pageContext.request.contextPath}/products/sort/name/${asc}" >А-Я</a>
						<a href="${pageContext.request.contextPath}/products/sort/name/${desc}"">Я-А</a>
						<a href="${pageContext.request.contextPath}/products/sort/price/${asc}" ">Въз.</a>
						<a href="${pageContext.request.contextPath}/products/sort/price/${desc}" ">Низ.</a>	 --%>
						
						<p>подреди по</p>
						
						<a class="pisi-button_yellow" href="${pageContext.request.contextPath}/products/sort/name/${asc}">име(а-я)</a>
						<a class="pisi-button_yellow" href="${pageContext.request.contextPath}/products/sort/name/${desc}">име(я-а)</a>
						<a class="pisi-button_yellow" href="${pageContext.request.contextPath}/products/sort/price/${asc}">цена(възх.)</a>
						<a class="pisi-button_yellow" href="${pageContext.request.contextPath}/products/sort/price/${desc}">цена(низх.)</a>
						
					</div>		
			
					<div class="pisi-grid_products">
						<ul>
							<c:forEach items="${ productPage.getPageList() }" var="pro">
									<li class="col-xs-12 col-md-4">
										<div class="wrap">
											
												<a href="<c:url value='/products/productdetail/productId/${pro.id}'/>" class="products_img">
													<img src="<c:url value="/products/image/${ pro.id }"/>"  alt="${ pro.description }" />
												</a>
												
												<div class="products_title">
													<a href="<c:url value='/products/productdetail/productId/${pro.id}'/>" >
														<h3>
															<p class="table-view">
																${pro.name}
															</p>
														</h3>
													</a>
												</div>
												
												<div class="more-info">
													<!-- <span class="price"> -->
														<c:if test="${ pro.discount == 0 }">
															<span class="price">			
																<fmt:formatNumber type="number" pattern="#####.##" value="${ pro.price }" />лв.
															</span>								
														</c:if>
														<c:if test="${ pro.discount != 0 }">
															<span class="old-price"> 														
																<fmt:formatNumber type="number" pattern="#####.##" value="${ pro.price }" />лв.														
															</span>								
															<span class="special-price">												
																	<fmt:formatNumber type="number" pattern="#####.##" value="${pro.calcDiscountedPrice() }" /> лв.													
															</span>						
														</c:if>
														
													<!-- </span> -->
													<a href="<c:url value='/products/productdetail/productId/${pro.id}'/>" class="pisi-button_dark">Виж повече</a>
												</div>
											
										</div> <!-- "wrap" -->
									</li>
					
							</c:forEach>
						</ul>
						
					</div>	<!-- pisi-grid_products -->
				</div> <!-- pisi-wrap -->
		
				<div class="pisi-paging">
				   <!--  <span > -->
					    <c:choose>
					        <c:when test="${productPage.firstPage}"></c:when>
					        <c:otherwise><a href="${pageContext.request.contextPath}/${sessionScope.url }/-2">Prev</a></c:otherwise>
					    </c:choose>
				  <!--   </span>
				    <span> -->
					    <c:forEach begin="0" end="${productPage.pageCount-1}" varStatus="loop">
						    &nbsp;
						    <c:choose>
						        <c:when test="${loop.index == productPage.page}">${loop.index+1}</c:when>
						        <c:otherwise><a href="${pageContext.request.contextPath}/${sessionScope.url }/${loop.index}">${loop.index+1}</a></c:otherwise>
						    </c:choose>
					    &nbsp;
					    </c:forEach>
				   <!--  </span>
				    <span> -->
					    <c:choose>
					        <c:when test="${productPage.lastPage}"></c:when>
					        <c:otherwise><a href="${pageContext.request.contextPath}/${sessionScope.url }/-1">Next</a></c:otherwise>
					    </c:choose>
				    <!-- </span> -->
		  		 </div>		<!-- pisi-paging -->					
			</c:if>			
	

</body>
</html>