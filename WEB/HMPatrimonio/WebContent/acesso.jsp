<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
	<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
	<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link href="<c:url value="/resources/css/estilo_login.css" />"
	rel="stylesheet">
<title>Área de Acesso</title>
</head>
<c:import url="/WEB-INF/views/template/cabecalho_home.jsp" />
<body>
<div class="principal">
	<form action="acessar" method="post">

		<div id="borda">
			<fieldset>
					<legend>Dados para liberação</legend><br /><br />
				<label for="user">Usuário:</label> 
				<input id="user" type="text" name="login"><br /><br />
				
				<label for="senha">Senha:</label> 
				<input id="senha"	type="password" name="senha"><br />
				<div id="img">
					<img alt="acesso" src='<c:url value="/resources/imgs/acesso.png"/>'>	
				</div>
				  <input type="submit" value="Acessar">		
			</fieldset>
		</div>
	</form>
	
</div>
<c:import url="/WEB-INF/views/template/rodape_home.jsp" />
</body>
</html>