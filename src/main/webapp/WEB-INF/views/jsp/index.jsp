<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
	 <jsp:include page="header.jsp"></jsp:include>

	
	
	<div class="pisi-banner">
		<img src="<c:url value='/img/pisi_bg_image.jpg'/>" alt="BRAND"  />
	</div>
	
	<div>
		<h2>ТОП ПРОДУКТИ</h2>
	</div>
	
	<div class="pisi-line_products">
		 <div class="container"> 
			<ul>
				<c:forEach items="${ sessionScope.topProducts }" var="pro">
					<li class="col-xs-12 col-md-2">
						<div class="wrap-line">											
								<a href="<c:url value='/products/productdetail/productId/${pro.id}'/>" >
									<img src="<c:url value="/products/image/${ pro.id }"/>"  alt="${ pro.description }" class="products_img" style="height: 100px"/>
								</a>
								<div class="products_title">
									<a href="<c:url value='/products/productdetail/productId/${pro.id}'/>" >
										<span class="small_product_name">
											${pro.name}
										</span>
									</a>
								</div>
								<div class="pic-buffer"></div>
								<div class="more-info">
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
									<a href="<c:url value='/products/productdetail/productId/${pro.id}'/>" class="pisi-button_dark-smaller">Виж повече</a>
								</div>	
						</div> <!-- "wrap" -->
					</li>
				</c:forEach>
			</ul>
		 </div>  <!-- class="container" -->
	</div>	<!-- pisi-line_products -->
	
	<!-- <div class="pic-buffer"></div> -->
	
	<div style="text-align: center;">
		<h2>ТОП МАРКИ</h2>
	</div>
	
	<div class="pisi-line_products">
		 <div class="container"> 
			<ul>
				<c:forEach items="${ sessionScope.brands }" var="brand">
					<li class="col-xs-12 col-md-2">
						<div class="wrap-line">	
						
							<form action="${pageContext.request.contextPath}/products/search/0" method="post">
								<input type="hidden" name="keyword" value="${ brand.value }" required>						
								<input type="image" name="submit" src="<c:url value="/products/image/${ brand.value }"/>" alt="${ brand.key}" class="products_img" style="height: 100px" >	
								<div class="pic-buffer"></div>
								<div class="products_title">
									<span class="small_product_name">
										${brand.key}
									</span>
								</div>
								<div class="more-info">	
									<input type="submit" value="Виж повече" class="pisi-button_dark-smaller"> <!-- Виж повече</input>	 -->					
									<%-- <a href="<c:url value='/products/productdetail/productId/${pro.id}'/>" class="pisi-button_dark-smaller">Виж повече</a> --%>
								</div>	
														
							</form>	
						
							
															
							<%-- 	<a href="<c:url value='/products/search/{page}${pro.id}'/>" >
									<img src="<c:url value="/products/image/${ brand.value }"/>"  alt="${ brand.key}" class="products_img" style="height: 100px"/>
								</a>
								<div class="products_title">
									<a href="<c:url value='/products/productdetail/productId/${pro.id}'/>" >
										<span class="small_product_name">
											${brand.key}
										</span>
									</a>
								</div>
								<div class="pic-buffer"></div>
								<div class="more-info">								
									<a href="<c:url value='/products/productdetail/productId/${pro.id}'/>" class="pisi-button_dark-smaller">Виж повече</a>
								</div>	 --%>
								
						</div> <!-- "wrap" -->
					</li>
				</c:forEach>
			</ul>
		 </div>  <!-- container -->
	</div>	<!-- pisi-line_products -->

</body>
</html>