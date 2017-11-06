<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
	 <jsp:include page="header.jsp"></jsp:include>

	
	
	<div class="pisi-banner">
		<img src="<c:url value='/img/pisi_bg_image.jpg'/>" alt="BRAND" title="BRAND" />
	</div>
	
	<!-- <div class="container"> -->
		<div class="pisi-line_products">
			<ul>
				<c:forEach items="${ sessionScope.topProducts }" var="pro">
					<li class="col-xs-12 col-md-2">
						<div class="wrap-line">											
								<a href="<c:url value='/products/productdetail/productId/${pro.id}'/>" >
									<img src="<c:url value="/products/image/${ pro.id }"/>"  alt="${ pro.description }" class="products_img" style="height: 100"/>
								</a>
								<div class="products_title">
									<a href="<c:url value='/products/productdetail/productId/${pro.id}'/>" >
										<span class="small_product_name">
											${pro.name}
										</span>
									</a>
								</div>
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
		</div>	<!-- pisi-grid_products -->
	<!-- </div> container -->

</body>
</html>