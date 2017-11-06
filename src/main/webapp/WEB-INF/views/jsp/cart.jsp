<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html SYSTEM "about:legacy-compat">
<html>
<head>
	<title>Cart</title>
	<script type="text/javascript">
		function checkQuantities() {
			 alert("Няма налично количество от " + productNoQuantity.name );
			var productNoQuantity =  ${sessionScope.productNotEnoughQuantity} ;
			if(productNoQuantity != null ){
		 	   alert("Няма налично количество от " + productNoQuantity.name );
			}
		}
	</script>
</head>
<body onload="checkQuantities()">

	<jsp:include page="header.jsp"></jsp:include><br>
	<br>
	<div class="container">
		<h1 id="cart_title" class="page-heading">Количка за пазаруване</h1>
	
		<c:if test="${ sessionScope.cart == null || empty sessionScope.cart }">
			<h4>Няма добавени продукти в количката</h4>
		</c:if>
	
		<c:if test="${ sessionScope.cart != null &&  not empty sessionScope.cart}">
			<table id="cart_summary"
				class="table table-bordered stock-management-on">
				
					<thead class="left">
						<tr>
							<th class="cart_product first_item">Снимка</th>
							<th class="cart_description item">Продукт</th>
							<th class="cart_unit item text-left">Цена</th>
							<th class="cart_quantity item text-center">Количество</th>
							<!-- <th class="cart_delete last_item"></th> -->
							<!-- <th width="5%"></th> -->
							<th class="cart_total item text-right">Общо</th>
						</tr>
					</thead>
				<tbody>
					<c:forEach items="${ sessionScope.cart }" var="productEntry">
						<c:set var="product" value="${productEntry.key}" />
						<tr>    
							<td class="cart_product">
								<a href="<c:url value='/products/productdetail/productId/${product.id}'/>">
									 <img src="<c:url value="/products/image/${product.id }"/>" alt="${product.description }" width=400px" height="auto">
								</a></td>
							<td class="cart_description" style="text-transform:capitalize; color: #440008;">
								<h4 style="text-transform:capitalize; color: #440008;">
									<a href="<c:url value='/products/productdetail/productId/${product.id}'/>" style="text-transform:capitalize; color: #440008;">${product.description }</a>									
								</h4>
							</td>
							<td class="cart_unit" width="120px" align="right">
								<h5 align="center">
									<c:if test="${ product.discount == 0 }">
										<ul style="list-style-type: none">
											<li class="price regular-price">
												<span> 
													<fmt:formatNumber type="number" pattern="#####.##" value="${ product.price }" />лв.
												</span>
											</li>
										</ul>
									</c:if>
									<c:if test="${  product.discount != 0 }">								
											<img src="<c:url value='/img/buttons/sale_piggy.png'/>" alt="SALE" title="SALE" width="50px" height="auto" align="left">	<br><br>	<br>																	
											<ul style="list-style-type: none">
												<%-- <li style="color: maroon">	<img src="<c:url value='/img/buttons/sale.png'/>" alt="SALE" title="SALE" width="auto" height="40"></li> --%>
												<li class="price regular-price">
													<span> 
														<del>
															<fmt:formatNumber type="number" pattern="#####.##" value="${ product.price }" /> лв.
														</del>
													</span>
												</li>										
												<li class="price special-price" style="color: maroon">
													<span> 
														<fmt:formatNumber type="number" pattern="#####.##" value="${product.calcDiscountedPrice() }" /> лв.
													</span>
												</li>
											</ul>								
									</c:if>
								</h5>
							</td>
							<td style="font-size: 22px;     vertical-align: middle;">
								<form action="<c:url value='/cart/updateCart'/>" method="get">
									<input type="hidden" value="${ product.id }" name=productId> 								
									<input type="number" style="width: 35px; height: 35px; font-size: 14px; border: 1px solid #C0C0C0;" name="count" size="2" min="1" max="99" value="${productEntry.value}" maxlength="2" required>
									<input type="image" name="submit"
										src="<c:url value='/img/buttons/update.png'/>" alt="UPDATE" title="Update">									
								</form>
								<br>					
								<form action="<c:url value='/cart/removeFromCart/${ product.id }'/>" method="post">
									<input type="image" name="submit" width="35" height="auto" 
										src="<c:url value='/img/buttons/icon_trash.png'/>" alt="REMOVE" title="Remove" align="center"> 
										<%-- <input type="hidden" value="${ product.id }" name="productId"> --%>
								</form>				
							</td>
							<td align="right">
								<c:if test="${ product.discount == 0 }">
									<fmt:formatNumber type="number" pattern="#####.00" value="${ product.price * productEntry.value }" />лв.									
								</c:if> 
								<c:if test="${ product.discount != 0 }">
									<fmt:formatNumber type="number" pattern="#####.00" value="${ product.calcDiscountedPrice()  *  productEntry.value }" />лв.									 
								</c:if>
							</td>
						</tr>
					</c:forEach>
				</tbody>
				<tfoot class="">
					
					<tr class="cart_total_price" align="left">
						<td rowspan="1" colspan="1" ></td>
						<td rowspan="1" colspan="1" style="border: 1px solid #ccc; color: #000; background-color: #FFF">
							<h3 style="text-transform: uppercase; text-align: left">Заплащане</h3> 
							<h5> Заплащането се извършва с наложен платеж при получаване на доставката </h5>
						</td>
						<td  rowspan="1" colspan="2" class="total_price_container text-right">
							<h3 style="text-transform: uppercase; text-align: left; vertical-align: middle;">Обща цена: </h3>
						</td>
						<h2 class="left">
							<td class="cart_total item text-right" rowspan="2" colspan="1" class="price" id="total_price_container">
								<span id="total_price" style="font-size: 24px; vertical-align: middle;">
									 <fmt:formatNumber type="number" pattern="#####.00" value="${ sessionScope.priceForCart }" />лв.
								</span>
							</td>
						</h2>
					</tr>
				</tfoot>
			</table>
			<hr>
			<c:if test="${ empty sessionScope.user }">
				<h3 style=" color: #000; background-color: #FFF">ПОРЪЧКА</h3> 
				<h5 align="center"> Поръчки се приемат само от регистрирани потребители </h5>
			</c:if> <br>
			<h3>
				<c:if test="${not empty sessionScope.user}">
					<a href="<c:url value='/cart/deliveryInfo'/>">
						<img src="<c:url value='/img/buttons/submit_order.png'/>" alt="ПОТВЪРДИ" title="submit order" width="15%" height="auto" >
					</a>
				</c:if>
				<c:if test="${ empty sessionScope.user }">
					<a href="<c:url value='/user/login'/>" title="LogIn" class="pisi-button_yellow"
						style="text-decoration: none">ВХОД</a> &nbsp;
					<a href="<c:url value='/user/register'/>" title="Register" class="pisi-button_yellow"
						style="text-decoration: none">РЕГИСТРАЦИЯ</a> &nbsp;														
				</c:if>
			</h3> <br>
		</c:if>
	</div>
</body>
</html>