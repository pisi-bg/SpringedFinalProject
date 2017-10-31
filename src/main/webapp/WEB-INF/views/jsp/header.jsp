<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html SYSTEM "about:legacy-compat">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>pisi bg</title>
<%-- <link rel="stylesheet" href='<c:url value="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" />' /> --%>
<%-- <link rel="stylesheet" href='<c:url value="https://zeroattentionspan.net/pisi.css" />' /> --%>
<link href="<c:url value="/css/bootstrap.css" />" rel="stylesheet" type="text/css">
<link href="<c:url value="/css/pisi.css" />" rel="stylesheet" type="text/css">


</head>
<body>
	
	<!-- <div class = " pisi-nav header"> -->
		<div class="pisi-nav pisi-nav_upper">
		
			<nav>
				<a href="<c:url value='/user/contactForm'/>" title="Контакти"
					class="nav_user" style="text-decoration: none">КОНТАКТИ</a> 
				<c:if test="${sessionScope.user == null}">
	
					<a href="<c:url value='/user/login'/>" title="LogIn">ВХОД</a> 
					<a href="<c:url value='/user/register'/>" title="Register">РЕГИСТРАЦИЯ</a> 
					<a href="<c:url value='/user/password'/>" title="LostPass">ЗАБРАВЕНА ПАРОЛА</a> 
				</c:if>
				<c:if test="${sessionScope.user != null}">
					<a href="<c:url value='/user/logout'/>" title="LogOut">ИЗХОД</a> 
					<a href="<c:url value='/user/profile'/>" title="Profile">ПРОФИЛ</a> 
					<a href="<c:url value='/user/favorites'/>" title="Favorites">ЛЮБИМИ</a> 
				</c:if>				
				<c:if test="${ sessionScope.user.isAdmin() }">
					<a href="${pageContext.request.contextPath}/user/admin/addProduct" title="Add Product">ДОБАВИ ПРОДУКТ</a> 
				</c:if>
			</nav>

		</div>
		
		<div class="pisi-brand">
			<div class="container">
				<div class="col-xs-12 col-md-4 pisi-brand_logo no-padding">
					<a href="<c:url value='/index'/>" style="text-decoration: none">
						<img src="<c:url value='/img/buttons/pisi.png'/>" alt="HOME" title="HOME" ><span class="pisi-title">Pisi.bg</span>
					</a>
				</div>
				<div class="col-xs-12 col-md-4 pisi-brand_search">					
					<form action="${pageContext.request.contextPath}/products/search" method="post">
						<input type="text" name="word">						
						<input type="image" name="submit"	src="<c:url value='/img/buttons/search2.png'/>" alt="ТЪРСИ" title="ТЪРСИ">							
					</form>					
				</div>
				<div class="col-xs-12 col-md-4 pisi-brand_cart">
					<a href="<c:url value='/cart/view'/>" style="text-decoration: none">
						<img src="<c:url value='/img/buttons/catCart.png'/>" alt="КОЛИЧКА" title="cart">
					</a>
				</div>
			</div>
		</div>
	
		<div class="pisi-nav_lower">
			<div class="container">
				<nav>	
										    
					<a href="<c:url value='/products/animal/2'/>" title="Cats" class="animal-category">КОТКИ</a>
					<a href="<c:url value='/products/animal/3'/>" title="Dogs">КУЧЕТА</a>
					<a href="<c:url value='/products/animal/4'/>" title="LittleFellows">МАЛКИ ЖИВОТНИ</a>
					<a href="<c:url value='/products/animal/1'/>" title="Aquaristics">АКВАРИСТИКА</a> 
					<a href="<c:url value='/products/animal/5'/>" title="Birds">ПТИЦИ</a> 
					<a href="<c:url value='/products/animal/6'/>" title="Reptiles">ТЕРАРИСТИКА</a> 
					
				</nav>
			</div>
		</div> 
	<!-- </div>	 -->

		<!-- categories -->
		
		
	
