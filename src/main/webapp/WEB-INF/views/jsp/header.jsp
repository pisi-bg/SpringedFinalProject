<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!--!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd"-->
<!DOCTYPE html SYSTEM "about:legacy-compat">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<style type="text/css">
	
		/* 	* {
		      font-family: sans-serif;
		    } */
		   
		@font-face {
		    font-family: "Comfortaa";
		    src: url('../fonts/Comfortaa-Bold.ttf') format('truetype') ;
		    src: url('/fonts/Comfortaa-Bold.ttf') format('truetype') ;
		}
		p.customfont { 
		    font-family: "Comfortaa", Verdana, Tahoma;
		}
		body {
		 	 font-family: 'Comfortaa';
		 	  src: url('/fonts/Comfortaa-Bold.ttf') format('truetype') ;
		}

</style>
</head>
<body>



	<div class="nav">
		<nav>		
		<h4 style="display: inline-block; text-transform:">
			<a href="<c:url value='/user/contactForm'/>" title="Контакти"
				class="nav_user" style="text-decoration: none">КОНТАКТИ</a> &nbsp;
			<c:if test="${sessionScope.user == null}">

				<a href="<c:url value='/user/login'/>" title="LogIn" class="nav_user"
					style="text-decoration: none">ВХОД</a> &nbsp;
				<a href="<c:url value='/user/register'/>" title="Register" class="nav_user" style="text-decoration: none">РЕГИСТРАЦИЯ</a> &nbsp;
				<a href="<c:url value='/user/password'/>" title="LostPass" class="nav_user"
					style="text-decoration: none">ЗАБРАВЕНА ПАРОЛА</a> &nbsp;
			</c:if>
			<c:if test="${sessionScope.user != null}">
				<a href="<c:url value='/user/logout'/>" title="LogOut" class="nav_user"
					style="text-decoration: none">ИЗХОД</a> &nbsp;
				<a href="<c:url value='/user/profile'/>" title="Profile" class="nav_user" style="text-decoration: none">ПРОФИЛ</a> &nbsp;
				<a href="<c:url value='/user/favorites'/>" title="Favorites" class="nav_user"
					style="text-decoration: none">ЛЮБИМИ</a> &nbsp;
			</c:if>
			
			<c:if test="${ sessionScope.user.isAdmin() }">
				<a href="${pageContext.request.contextPath}/user/admin/addProduct" title="Add Product" class="nav_user"
					style="text-decoration: none">ДОБАВИ ПРОДУКТ</a> &nbsp;
			</c:if>
		</h4>
		</nav>
	</div>
	
	<div class="img">
		<nav>
		<h3 style="display: inline-block; text-transform:">
			<a href="<c:url value='/index'/>" style="text-decoration: none">
				<img src="<c:url value='/img/buttons/pisi.png'/>" alt="HOME" title="Logo" width="15%" height="auto">
			</a>		
			
						
			&nbsp;&nbsp;&nbsp;&nbsp;
			<form action="${pageContext.request.contextPath}/products/search/0" method="post" style="display:inline;">
				<input type="text" name="word">
				<!-- <input type="submit" value="Търси"> -->
				<input type="image" name="submit"	src="<c:url value='/img/buttons/search2.png'/>" alt="ТЪРСИ" title="търси" width="35" height="auto">	
				
			</form>	
			&nbsp;				
			
			<a href="<c:url value='/cart/view'/>" style="text-decoration: none">
				<img src="<c:url value='/img/buttons/catCart.png'/>" alt="КОЛИЧКА" title="cart" width="12%" height="auto" align="right">
			</a>			
		</h3>
		</nav>
	</div>

	<div class="products">
		<nav>
		<h3 style="display: inline-block;">
					    
				&nbsp; <a href="<c:url value='/products/animal/2/0'/>" title="Cats" class="products" style="text-decoration: none">КОТКИ</a>
				&nbsp; <a href="<c:url value='/products/animal/3/0'/>" title="Dogs" class="products" style="text-decoration: none">КУЧЕТА</a>
				&nbsp; <a href="<c:url value='/products/animal/4/0'/>" title="LittleFellows" class="products" style="text-decoration: none">МАЛКИ ЖИВОТНИ</a>
				&nbsp; <a href="<c:url value='/products/animal/1/0'/>" title="Aquaristics" class="products" style="text-decoration: none">АКВАРИСТИКА</a> 
				&nbsp; <a href="<c:url value='/products/animal/5/0'/>" title="Birds" class="products" style="text-decoration: none">ПТИЦИ</a> 
				&nbsp; <a href="<c:url value='/products/animal/6/0'/>" title="Reptiles" class="products" style="text-decoration: none">ТЕРАРИСТИКА</a> &nbsp;
		</h3>
		</nav>
	</div>
</body>
</html>