<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


	<jsp:include page="header.jsp"></jsp:include>

	<h2>ФОРМА ЗА КОНТАКТИ</h2>
	<h4>МОЛЯ, ПОПЪЛНЕТЕ ВСИЧКИ ПОЛЕТА:</h4>

	<form method="POST"  action="contactForm" id="login_form" class="box">

		<!-- <h3 class="page-subheading">ФОРМА ЗА КОНТАКТИ</h3> -->


			<div class="container" >
	
				<div class="form_content clearfix">
					<div class="form-group">
						<h5 class="has-error">
							<c:out value="${name }"></c:out>
						</h5>
						<label for="name">Име</label> 
						<input type="text" name="name" value="" class="account_input form-control" required>
					</div>
					<div class="form-group">
						<h5 class="has-error">
							<c:out value="${phone }"></c:out>
						</h5>
						<label for="phone">Телефон</label> 
						<input type="number" name="phone" value="" class="account_input form-control" required>
					</div>
					<div class="form-group">
						<h5 class="has-error">
							<c:out value="${email }"></c:out>
						</h5>
						<label for="email">Email</label>
						 <input type="email" name="email" value="" class="account_input form-control" required>
					</div>
					<div class="form-group">
						<h5 class="has-error">
							<c:out value="${subject }"></c:out>
						</h5>
						<label for="subject">Относно</label> 
						<input type="text" name="subject" value="" class="account_input form-control" required>
					</div>
					<div class="form-group">
						<h5 class="has-error">
							<c:out value="${describe }"></c:out>
						</h5>
						<label for="descr">Описание</label>
		
						<textarea name="descr" class="account_input form-control" style="height: 120px;" required>
						</textarea>
					</div>
		
					<p class="submit" align="center">
						<input type="image" height="auto" src="<c:url value='/img/buttons/send.png'/>" alt="ИЗПРАТИ" title="ИЗПРАТИ" width="8%" height="auto"> 
					</p>
				</div>
			</div>
	</form>

</body>
</html>