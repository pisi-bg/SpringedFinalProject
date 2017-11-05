<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<jsp:include page="header.jsp"></jsp:include>
	<h1 style="background-color: #d99232;   align: center; padding-top: 10px;  padding-bottom: 10px; margin-top: 0px;" >
		 pisi.bg 
		<!--  <br>  your pet's favorite mall -->
		<span style="font-size: 28px;"> your pal's favorite mall </span>
	</h1>	

	<c:if test="${ requestScope.wrongUser }"><h3 class="has-error"> ГРЕШЕН ПOТРЕБИТЕЛ</h3></c:if>
	
	
	<form action="login" method= "POST" style="text-align: center;">
		<h3  align="center">
			Здравей, влез с твоя майл и парола!
		</h3>
		<h4>
			Email <input type="email" name = "email" value="dimdim@abv.bg" required><br><br>
			Password <input type="password" name ="password" value="dimanas" required><br>
			<input type="image" name="submit" width="8%" height="auto" src="<c:url value='/img/buttons/profile.png'/>" alt="ВЛЕЗ" title="ВЛЕЗ"> 
			<br>ВЛЕЗ
		</h4>
	</form>
		
	<div align="center">	
		<h4 style="    color: #440008;">
			<a href="<c:url value='/user/register'/>" title="Register" class="nav_user" style="text-decoration: none" >
				<img src="<c:url value='/img/buttons/register.png'/>" alt="РЕГИСТРИРАЙ СЕ" title="РЕГИСТРИРАЙ СЕ" width="6%" height="auto" >
				<br>РЕГИСТРИРАЙ СЕ
			</a> &nbsp;
		</h4>
	</div>
		
 <%-- 	<form action="<c:url value='/user/register'/>" method="POST"  style="text-align: center;">
		<input type="image" name="submit" width="8%" height="auto" src="<c:url value='/img/buttons/register.png'/>" alt="РЕГИСТРИРАЙ СЕ" title="РЕГИСТРИРАЙ СЕ"> 
		<br>РЕГИСТРИРАЙ СЕ
	</form> --%>
		
<%--	<a href= "<c:url value='/index'/>" ><button>Home</button></a> --%>
</body>
</html>