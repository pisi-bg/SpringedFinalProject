<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


	 <c:if test="${ !sessionScope.user.isAdmin()}">	 
		<c:redirect url="index.jsp"></c:redirect>
	</c:if> 
	
	<jsp:include page="header.jsp"></jsp:include>
	
	<h3>ДОБАВИ НОВА МАРКА</h3><br>
	
	<form action="addBrand" method="post" enctype="multipart/form-data"   accept-charset="UTF-8 ">
		Име на марка: 
			<input type= "text" name= "newBrandname"  required><br>				
		Снимка на марката:	 
			<input type="file" name="newBrandImage" accept=".jpg, .png, .jpeg"  required/><br>	
			<input type="submit">
			<!-- <a class="pisi-button_yellow" href="#">ДОБАВИ</a><br> -->
	</form>
	
	<h3>ДОБАВИ НОВ ПРОДУКТ</h3><br>
	<form action="addProduct" method="post" enctype="multipart/form-data" accept-charset="UTF-8 ">
		Име на продукт: 
			<input type= "text" name= "name"  required style="width: 250px;"><br>		
			
		Тип животно: 
			<c:forEach items="${sessionScope.animals}" var="animal"  >							
				<input type="radio" name="animal" value="${animal}" required />${animal}									
			</c:forEach><br>	
						
		Категория:
			<select name="category"  required>		
				<c:forEach items="${sessionScope.subCategories}" var="subCategory" >
					<option value="${subCategory}" >${subCategory}</option>												
				</c:forEach>					
			</select><br>	
				
		Цена: 
			<input type="number" min="0.00" max="10000.00" step="0.01" name="price" style="width: 150px;" required /><br>
		
		Описание:
			<input type="text" name="description" style="width: 150px;" required><br>
		
		Марка:
			<select name="brand"  required>
				<c:forEach items="${sessionScope.brands}" var="brand" >
					<option value="${brand}" >${brand}</option>												
				</c:forEach>
			</select><br>
		
		Брой в наличност: 
			<input type="number" min="0" step="1" name="instock_count" style="width: 150px;" required/><br>
		Намаление в процент: 
			<input type="number" min="0" max="99" step="1" name="discount" required /><br>		
		Снимка:	 
			<input type="file" name="image" accept=".jpg, .png, .jpeg"  required/><br>	
			
		<input type="submit">
	</form>
	
	
	
</body>
</html>