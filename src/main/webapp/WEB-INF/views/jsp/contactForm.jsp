<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>

	<jsp:include page="header.jsp"></jsp:include>

	<h2>ФОРМА ЗА КОНТАКТИ</h2>
	<h3>МОЛЯ, ПОПЪЛНЕТЕ ВСИЧКИ ПОЛЕТА:</h3>

	<form method="POST"  action="contactForm" id="login_form" class="box">

		<!-- <h3 class="page-subheading">ФОРМА ЗА КОНТАКТИ</h3> -->


		<div style="clear: both"></div>

		<div class="form_content clearfix">
			<div class="form-group">
				<label for="name">Име</label> <input type="text" name="name"
					value="" class="account_input form-control">
			</div>
			<div class="form-group">
				<label for="phone">Телефон</label> <input type="number" name="phone"
					value="" class="account_input form-control">
			</div>
			<div class="form-group">
				<label for="email">Email</label> <input type="email" name="email"
					value="" class="account_input form-control">
			</div>
			<div class="form-group">
				<label for="subject">Относно</label> <input type="text"
					name="subject" value="" class="account_input form-control">
			</div>
			<div class="form-group">
				<label for="descr">Описание</label>

				<textarea name="descr" class="account_input form-control"
					style="height: 120px;"></textarea>
			</div>

			<p class="submit">
				
				<input type="image" name="submit" width="8%" height="auto" src="<c:url value='/img/buttons/send.png'/>" alt="ИЗПРАТИ" title="ИЗПРАТИ" width="6%" height="auto"> 
				
				<%-- <button type="submit" name="submit">
					<img src="<c:url value='/img/buttons/send.png'/>" alt="ИЗПРАТИ" title="ИЗПРАТИ" width="6%" height="auto" >
				</button> --%>
				

			</p>
		</div>
	</form>

</body>
</html>