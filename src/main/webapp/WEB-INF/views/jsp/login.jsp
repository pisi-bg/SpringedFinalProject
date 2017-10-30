<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!--!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd"-->
<!DOCTYPE html SYSTEM "about:legacy-compat">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>LogIn</title>


<style type="text/css">	
		h1 {
			    display: block;
			    font-size: 1.8em;
			 	/*  margin-top: 0.83em;
			    margin-bottom: 0.83em;
			    margin-left: 0;
			    margin-right: 0; */
			    font-weight: bold;
			    line-height:3;
			}
		p {
	    
	    font-size: 0.8em;
	     margin-top: 0;
	 	/*  margin-top: 0.83em;
	    margin-bottom: 0.83em;
	    margin-left: 0;
	    margin-right: 0; */
	    font-weight: bold;	    
	}
		
</style>


</head>
<body>
<jsp:include page="header.jsp"></jsp:include>
	<h1 style="background-color: #d99232"   align="center">
		 pisi.bg 
		<!--  <br>  your pet's favorite mall -->
		<p> your pal's favorite mall </p>
	</h1>	

	<c:if test="${ requestScope.wrongUser }"><h1 style ="color: red " align="center"> ГРЕШЕН ПТРЕБИТЕЛ</h1></c:if>
	
	
	<form action="login" method= "POST" style="text-align: center;">
		<h2  align="center">
			Здравей, влез с твоя майл и парола!
		</h2>
		Email<input type="email" name = "email" value="pesho@abv.bg" required><br>
		Password<input type="password" name ="password" value="pesho" required><br>
		<input type="image" name="submit" width="8%" height="auto" src="<c:url value='/img/buttons/profile.png'/>" alt="ВЛЕЗ" title="ВЛЕЗ"> 
		<br>ВЛЕЗ
		<!-- <input type="submit" value="Login">  -->
	</form>
		
	<div align="center">	
		<a href="<c:url value='/user/register'/>" title="Register" class="nav_user" style="text-decoration: none" >
			<img src="<c:url value='/img/buttons/register.png'/>" alt="РЕГИСТРИРАЙ СЕ" title="РЕГИСТРИРАЙ СЕ" width="6%" height="auto" >
			<br>РЕГИСТРИРАЙ СЕ
		</a> &nbsp;
	</div>
		
 <%-- 	<form action="<c:url value='/user/register'/>" method="POST"  style="text-align: center;">
		<input type="image" name="submit" width="8%" height="auto" src="<c:url value='/img/buttons/register.png'/>" alt="РЕГИСТРИРАЙ СЕ" title="РЕГИСТРИРАЙ СЕ"> 
		<br>РЕГИСТРИРАЙ СЕ
	</form> --%>
		
<%--	<a href= "<c:url value='/index'/>" ><button>Home</button></a> --%>
</body>
</html>