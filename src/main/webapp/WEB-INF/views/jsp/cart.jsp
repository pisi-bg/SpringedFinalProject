<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!--!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd"-->
<!DOCTYPE html SYSTEM "about:legacy-compat">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Cart</title>
</head>
<body>


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
						<td class="cart_unit"><c:if test="${ product.discount == 0 }">
								<ul style="list-style-type: none">
									<li class="price regular-price">
										<span> 
											<fmt:formatNumber type="number" pattern="#####.##" value="${ product.price }" />лв.
										</span>
									</li>
								</ul>
							</c:if>
							<c:if test="${  product.discount != 0 }">
								<ul style="list-style-type: none">
									<li class="price regular-price">
										<span> 
											<del>
												<fmt:formatNumber type="number" pattern="#####.##" value="${ product.price }" /> лв.
											</del>
										</span>
									</li>
									<li style="color: red">нова цена</li>
									<li class="price special-price">
										<span> 
											<fmt:formatNumber type="number" pattern="#####.##" value="${product.calcDiscountedPrice() }" /> лв.
										</span>
									</li>
								</ul>
							</c:if></td>
						<td>
						
						<%-- 	<a href="<c:url value='/cart/updateCart'/>">
								<img src="<c:url value='/img/buttons/update.png'/>" alt="UPDATE" title="Update" width="5%" height="auto">
							</a> --%>
						
						
						
							<form action="<c:url value='/cart/updateCart'/>" method="get">
								<input type="hidden" value="${ product.id }" name=productId> 
								<input type="text" style="width: 35px; height: 35px; font-size: 14px; border: 1px solid #C0C0C0;"
									name="count" size="2" value="${productEntry.value}" maxlength="2">
								 <input type="image" name="submit"
									src="<c:url value='/img/buttons/update.png'/>" alt="UPDATE" title="Update">									
							</form>
							
						</td>	
						<td>
						
							<%-- <a href="<c:url value='/cart/removeFromCart'/>">
								<img src="<c:url value='/img/buttons/icon_trash.png'/>" alt="REMOVE" title="Remove" width="5%" height="auto">
							</a> --%>
						
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
					<td rowspan="2" colspan="2"
						style="border: 1px solid #ccc; color: #000; background-color: #FFF">
						<h3 style="text-transform: uppercase">Заплащане</h3> <br>
						Заплащането се извършва с наложен платеж при получаване на
						доставката <br>
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
		
		<h3 style=" color: #000; background-color: #FFF">ПОРЪЧКА</h3> 
			<c:if test="${ empty sessionScope.user }">
				<br> Поръчки се приемта само от регистрирани потребители <br>
			</c:if>
			
		<p class="cart_navigation  clearfix inner-top" style=float:right;  >
			<c:if test="${not empty sessionScope.user}">
				<form action="<c:url value='/cart/deliveryInfo'/>" method="get" >
					<button type="submit" name="submit_fast_registration" >
						Потвърди поръчката
					</button>
				</form>
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