<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

	<jsp:include page="header.jsp"></jsp:include>
	<br>
	
	<div class="container">	
		<div class="col-xs-12 col-md-4">		
			<h4 style="color:#c4463e;">ПОРЪЧКАТА Е ИЗПРАТЕНА УСПЕШНО</h4>
			<h4>Очаквайте доставка </h4>
			<h4>Благодарим Ви за доверието! </h4>	
					
			<form action="${pageContext.request.contextPath}/user/profile/showOrders" method="post" style="display:inline;">		
				<h3 >	
					ПРЕДИШНИ ПОРЪЧКИ 		
					<input type="image" name="submit"	src="<c:url value='/img/buttons/arrow.png'/>" alt="ПОКАЖИ" title="show" width="40px" height="auto" >	
				</h3>
			</form>		
			<h3>
				<a href="<c:url value='/user/contactForm'/>" title="Контакти">
					ИЗПРАТИ НИ СЪОБЩЕНИЕ
					<img src="<c:url value='/img/buttons/send.png'/>" alt="ПИСМО В БУТИЛКА" title="send mail" width="100px" height="auto" >
				</a> 
			</h3>
		</div>
		
		<div class="col-xs-12 col-md-6">
			<img src="<c:url value='/img/delivery_cat2.jpg'/>" alt=""  width="auto" height="600px" >
		</div>	
			
	</div>	<!-- container -->
</body>
</html>