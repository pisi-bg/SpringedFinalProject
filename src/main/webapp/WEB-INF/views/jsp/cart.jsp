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
	
	<%-- //	var productNoQuantity = '<%= session.getAttribute("productNotEnoughQuantity") %>'; --%>
	//	 var persons = ${personsJson};
	//	 ${sessionScope.list}
		var productNoQuantity =  ${sessionScope.productNotEnoughQuantity} ;
	//	 application.getAttribute("name")
		
		if(productNoQuantity != null ){
	 	   alert("Няма налично количество от " + productNoQuantity.name );
		}
	}
	</script>
</head>
<body onload="checkQuantities()">


	<jsp:include page="header.jsp"></jsp:include><br>
	<br>

	<h1 id="cart_title" class="page-heading">Количка за пазаруване</h1>

	<c:if test="${ sessionScope.cart == null || empty sessionScope.cart }">
		<h4>Няма добавени продукти в количката</h4>
	</c:if>



	<c:if
		test="${ sessionScope.cart != null &&  not empty sessionScope.cart}">
		<table id="cart_summary"
			class="table table-bordered stock-management-on">
			<thead class="">
				<tr>
					<th class="cart_product first_item">Снимка</th>
					<th class="cart_description item">Продукт</th>
					<th class="cart_unit item text-right">Цена</th>
					<th class="cart_quantity item text-center">Количество</th>
					<th class="cart_delete last_item"></th>
					<th width="5%"></th>
					<th class="cart_total item text-right">Общо</th>
				</tr>
			</thead>

			<tbody>
				<c:forEach items="${ sessionScope.cart }" var="productEntry">
					<c:set var="product" value="${productEntry.key}" />
					<tr>    
						<td class="cart_product"><a
							href="<c:url value='/products/productdetail/productId/${product.id}'/>"> <img
								src="${product.image }" alt="${product.description }" width="98"
								height="98">
						</a></td>
						<td class="cart_description">
							<p class="product-name" align="center">
								<a href="<c:url value='/products/productdetail/productId/${product.id}'/>">${product.description }</a>
							</p>
						</td>
						<td class="cart_unit">
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
									<img src="<c:url value='/img/buttons/sale_piggy.png'/>" alt="SALE" title="SALE" width="auto" height="40" align="left">																	
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
						</td>
						<td>						
							<form action="<c:url value='/cart/updateCart'/>" method="get">
								<input type="hidden" value="${ product.id }" name=productId> 								
								<input type="text" style="width: 35px; height: 35px; font-size: 14px; border: 1px solid #C0C0C0;" name="count" size="2" value="${productEntry.value}" maxlength="2">
								<input type="image" name="submit"
									src="<c:url value='/img/buttons/update.png'/>" alt="UPDATE" title="Update">									
							</form>
							
						</td>	
						<td>						
							<form action="<c:url value='/cart/removeFromCart/${ product.id }'/>" method="post">
								<input type="image" name="submit" width="25" height="auto"
									src="<c:url value='/img/buttons/icon_trash.png'/>" alt="REMOVE" title="Remove"> 
									<%-- <input type="hidden" value="${ product.id }" name="productId"> --%>
							</form>
						</td>
						<td width="10%"></td>
						<td align="right"><c:if test="${ product.discount == 0 }">
								<fmt:formatNumber type="number" pattern="#####.00"
									value="${ product.price * productEntry.value }" />лв.									
							</c:if> <c:if test="${ product.discount != 0 }">
								<fmt:formatNumber type="number" pattern="#####.00"
									value="${ product.calcDiscountedPrice()  *  productEntry.value }" />лв.									 
							</c:if></td>
					</tr>
				</c:forEach>
			</tbody>
			<tfoot class="">
				<tr class="cart_total_price">
					<td rowspan="2" colspan="2" style="border: 1px solid #ccc; color: #000; background-color: #FFF">
						<h3 style="text-transform: uppercase">Заплащане</h3> 
						<br> Заплащането се извършва с наложен платеж при получаване на доставката <br>
					</td>
				</tr>


				<td rowspan="3" colspan="3" class="total_price_container text-right">
					<span>Обща цена: </span>
				</td>
				<td width="5%"></td>
				<td class="cart_total item text-right" rowspan="3" colspan="2"
					class="price" id="total_price_container"><span
					id="total_price" style="font-size: 24px;"> <fmt:formatNumber
							type="number" pattern="#####.00"
							value="${ sessionScope.priceForCart }" />лв.
				</span></td>

			</tfoot>
		</table>
		<hr>
		
		
			<c:if test="${ empty sessionScope.user }">
				<h3 style=" color: #000; background-color: #FFF">ПОРЪЧКА</h3> 
				<br> Поръчки се приемта само от регистрирани потребители <br>
			</c:if>
			
		<p class="cart_navigation  clearfix inner-top" align="center"  >
			<c:if test="${not empty sessionScope.user}">
				<a href="<c:url value='/cart/deliveryInfo'/>">
					<img src="<c:url value='/img/buttons/submit_order.png'/>" alt="ПОТВЪРДИ" title="submit order" width="auto" height="40">
				</a>
			</c:if>
			<c:if test="${ empty sessionScope.user }">
				<a href="<c:url value='/user/login'/>" title="LogIn" class="nav_user"
					style="text-decoration: none">ВХОД</a> &nbsp;
				<a href="<c:url value='/user/register'/>" title="Register" class="nav_user"
					style="text-decoration: none">РЕГИСТРАЦИЯ</a> &nbsp;														
			</c:if>
		</p>
		
	
	</c:if>
</body>
</html>