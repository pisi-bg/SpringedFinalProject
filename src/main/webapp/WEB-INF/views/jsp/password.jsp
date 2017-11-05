<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>	

	
	<jsp:include page="header.jsp"></jsp:include>
	
	<h3>Моля, въведете имейла си,за да получите съобщение на вашата поща.</h3><br>
	<h4>Ако не получите такова, моля пишете на pisi.bg.shop@gmail.com и екипът на pisi.bg ще се свърже с вас</h4><br>
	
	<h5 class="has-error"><c:out value="${pass }"></c:out></h5>
	
	<h4>
		<f:form>
			<input type="email" name="email" required> &nbsp;&nbsp;
			<input type="submit" value="Изпрати">
		</f:form>
	</h4>
</body>
</html>