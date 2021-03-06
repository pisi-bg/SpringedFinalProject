<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html SYSTEM "about:legacy-compat">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>pisi bg</title>
	<%-- <link rel="stylesheet" href='<c:url value="https://zeroattentionspan.net/pisi.css" />' /> --%>
	<link href="<c:url value="/css/bootstrap.css" />" rel="stylesheet" type="text/css">
	<link href="<c:url value="/css/pisi1.css" />" rel="stylesheet" type="text/css">

</head>
<body>
	
	<!-- <div class = " pisi-nav header"> -->
		<div class="pisi-nav pisi-nav_upper">
		
			<nav>
				<a href="<c:url value='/user/contactForm'/>" title="Контакти">КОНТАКТИ</a> 
				<c:if test="${sessionScope.user == null}">
	
					<a href="<c:url value='/user/login'/>" title="LogIn">ВХОД</a> 
					<a href="<c:url value='/user/register'/>" title="Register">РЕГИСТРАЦИЯ</a> 
					<a href="<c:url value='/user/password'/>" title="LostPass">ЗАБРАВЕНА ПАРОЛА</a> 
				</c:if>
				<c:if test="${sessionScope.user != null}">
					<a href="<c:url value='/user/logout'/>" title="LogOut">ИЗХОД</a> 
					<a href="<c:url value='/user/profile'/>" title="Profile">ПРОФИЛ</a> 
					<a href="<c:url value='/user/favorites/0'/>" title="Favorites">ЛЮБИМИ</a> 
				</c:if>				
				<c:if test="${ sessionScope.user.isAdmin() }">
					<a href="${pageContext.request.contextPath}/user/admin/addProduct" title="Add Product">ДОБАВИ ПРОДУКТ</a> 
				</c:if>
			</nav>
		</div>
		
		<div class="pisi-brand">
			<div class="container">
				<div class="col-xs-12 col-md-4 pisi-brand_logo no-padding">
					<a href="<c:url value='/index'/>" >
						<img src="<c:url value='/img/buttons/pisi.png'/>" alt="HOME" title="HOME" >
						<span class="logo-text">
							<span class="pisi-title">Pisi.bg</span><br>
							<span class="pisi-title-shop">онлайн зоомагазин</span>
						</span>
					</a>
				</div>
				<div class="col-xs-12 col-md-4 pisi-brand_search">					
					<form action="${pageContext.request.contextPath}/products/search/0" method="post">
						<input type="text" name="keyword" required>						
						<input type="image" name="submit" 	src="<c:url value='/img/buttons/search2.png'/>" alt="ТЪРСИ" title="ТЪРСИ" >							
					</form>					
				</div>
				<div class="col-xs-12 col-md-2 ">
				</div>
				<div class="col-xs-12 col-md-4 pisi-brand_cart">
					<a href="<c:url value='/cart/view'/>" style="color:#440008;" >
						<img src="<c:url value='/img/buttons/catCart.png'/>" alt="КОЛИЧКА" title="cart"  style="float: right;"><br><br><br>
						<span class="text" style="float: right;">КОЛИЧКА</span>
					</a>
				</div>
			</div>
		</div>

		<div class="pisi-nav_lower">
			<div class="container">
				<nav>	
						<!-- TEST FOR DROPDOWN -> NOT WORKING -->
							<%-- <div class="navbar">
								  <div class="dropdown">
									<!--   <a href="<c:url value='/products/animal/3/0'/>" title="Dogs">  -->
									  	<button class="dropbtn">КУЧЕТА</button>
									 <!--  </a>	 -->
					    			<c:forEach items="${ sessionScope.dogs }" var="parentCategory">
					   					 <div class="dropdown-content">
											 <a href="<c:url value='/products/animal/3/category/${parentCategory.value}/0'/>" title="Dogs"> ${parentCategory.key} </a>
										 </div>
									</c:forEach>
								  </div> 
							</div>	 --%><!-- class="navbar" -->
						<!-- END *** TEST FOR DROPDOWN -->
					
				
						
										    
						<a href="<c:url value='/products/animal/2/0'/>" title="Cats" class="animal-category">КОТКИ</a>
						
						<a href="<c:url value='/products/animal/3/0'/>" title="Dogs">КУЧЕТА</a>
						<a href="<c:url value='/products/animal/4/0'/>" title="LittleFellows">МАЛКИ ЖИВОТНИ</a>
						<a href="<c:url value='/products/animal/1/0'/>" title="Aquaristics">АКВАРИСТИКА</a> 
						<a href="<c:url value='/products/animal/5/0'/>" title="Birds">ПТИЦИ</a> 
						<a href="<c:url value='/products/animal/6/0'/>" title="Reptiles">ТЕРАРИСТИКА</a> 
					                                         
				</nav>
			</div>
		</div> 
	<!-- </div>	 -->

		<!-- categories -->
		
		

