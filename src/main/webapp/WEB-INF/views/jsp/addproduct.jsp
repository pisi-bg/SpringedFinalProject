<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


	 <c:if test="${ !sessionScope.user.isAdmin()}">	 
		<c:redirect url="index.jsp"></c:redirect>
	</c:if> 
	<jsp:include page="header.jsp"></jsp:include>
	
	<div class="container">
		<div class="col-xs-12 col-md-6" >		
			<h3 style="text-align: left;">ДОБАВИ НОВА МАРКА</h3><br>
			
			<h5 class="has-error"><c:out value="${requestScope.brandError }"></c:out></h5>
			<h4 style="text-align: left; text-transform:capitalize;">
				<form action="addBrand" method="post" enctype="multipart/form-data"   accept-charset="UTF-8 ">
					Име на марка: 
						<input type= "text" name= "newBrandname"  required><br>				
					Снимка на марката:	 
						<input type="file" name="newBrandImage" accept=".jpg, .png, .jpeg"  required/><br>	
						<input type="submit">
				</form>
			</h4>
			<h3 style="text-align: left;">ДОБАВИ НОВ ПРОДУКТ</h3><br>
			<h5 class="has-error"><c:out value="${requestScope.productError }"></c:out></h5>
			
			<!-- <h6 style="text-align:center; font-size: 16px; "> -->
			<h4 style="text-align: left; text-transform:capitalize;">
				<form action="addProduct" method="post" enctype="multipart/form-data" accept-charset="UTF-8 ">
					Име на продукт: <br>
						<input type= "text" name= "name"  required style="width: 250px;"><br>		
						
					Тип животно: <br>
						<c:forEach items="${sessionScope.animals}" var="animal"  >							
							<input type="radio" name="animal" value="${animal}" required />${animal}									
						</c:forEach><br>	
									
					Категория:<br>
						<select name="category"  required>		
							<c:forEach items="${sessionScope.subCategories}" var="subCategory" >
								<option value="${subCategory}" >${subCategory}</option>												
							</c:forEach>					
						</select><br>	
							
					Цена: <br>
						<input type="number" min="0.00" max="10000.00" step="0.01" name="price" style="width: 150px;" required /><br>
					
					Описание:<br>
						<input type="text" name="description" style="width: 150px;" required><br>
					
					Марка:<br>
						<select name="brand"  required>
							<c:forEach items="${sessionScope.brands}" var="brand" >
								<option value="${brand}" >${brand}</option>												
							</c:forEach>
						</select><br>
					
					Брой в наличност: <br>
						<input type="number" min="0" max="200000" step="1" name="instock_count" style="width: 150px;" required/><br>
					Намаление в процент: <br>
						<input type="number" min="0" max="99" step="1" name="discount" required /><br>		
					Снимка:	 <br>
							<input type="file" name="image" accept=".jpg, .png, .jpeg"  required style="text-align: center;"/><br>				
					<input type="submit">
				</form>
			</h4>
		</div>
	</div>
	
</body>
</html>