<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
	 <jsp:include page="header.jsp"></jsp:include>

	
	
	<div class="pisi-banner">
		<img src="<c:url value='/img/pisi_bg_image.jpg'/>" alt="BRAND"  />
	</div>
	
	<div style="text-align: center; color:#c4463e;">
		<h2 style="padding:10px;  background-color: #d99232;" >ТОП МАРКИ</h2>
	</div>
	
	<div class="pisi-line_products">
		<!--  <div class="container">  -->
			<ul>
				<c:forEach items="${ sessionScope.brands }" var="brand">
					<li class="col-xs-12 col-md-2">
						<div class="wrap-line">							
							<form action="${pageContext.request.contextPath}/products/search/0" method="post">
								<input type="hidden" name="keyword" value="${ brand.key }" required>						
								<input type="image" name="submit" src="<c:url value="/products/brand/${ brand.key }"/>" alt="${ brand.key}" class="products_img" style="height: 150px; max-height: 150px; min-height: 150px; width:150px; max-width: 150px; min-width: 150px; " >	
								<div class="products_title">
									<span class="small_product_name">
										${brand.key}
									</span>
								</div>
								<div class="more-info">	
									<input type="submit" value="Виж повече" class="pisi-button_dark-smaller"> <!-- Виж повече</input>	 -->					
								</div>															
							</form>	
						</div> <!-- "wrap" -->
					</li>
				</c:forEach>
			</ul>
		<!--  </div>  container -->
	</div>	<!-- pisi-line_products -->
	
	<!--  <div style="text-align: center; color:#c4463e;"> -->
	<!-- 	<h3 style="padding:10px;  background-color: #d99232; text-align: center; color:#c4463e;" >ТОП ПРОДУКТИ</h3> -->
	<!-- </div>  -->
	<div class="pic-buffer"></div> 
<!-- 	<div class="row">
		<h2 style="padding:10px;  background-color: #d99232;" >ТОП ПРОДУКТИ</h2>
	</div> -->
	<div class="row">
		<span style=" float:center; padding:10px;  background-color: #d99232; font-size: 24px; text-align: center; color:#40020a;font-weight: bold;" >ТОП ПРОДУКТИ</span>
	</div>
	<div class="pisi-line_products">
		<!--  <div class="container">  -->
			<ul>
				<c:forEach items="${ sessionScope.topProducts }" var="pro">
					<li class="col-xs-12 col-md-2">
						<div class="wrap-line">											
								<a href="<c:url value='/products/productdetail/productId/${pro.id}'/>" >
									<img src="<c:url value="/products/image/${ pro.id }"/>"  alt="${ pro.description }" class="products_img" style="height: 120px"/>
								</a>
								<div class="products_title">
									<a href="<c:url value='/products/productdetail/productId/${pro.id}'/>" >
										<span class="small_product_name">
											${pro.name}
										</span>
									</a>
								</div>
							<!-- 	<div class="pic-buffer"></div> -->
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
		<!--  </div>  class="container" -->
	</div>	<!-- pisi-line_products -->
	
	<!-- <div class="pic-buffer"></div> -->
	


</body>
</html>