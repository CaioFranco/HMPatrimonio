<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link href="<c:url value="/resources/css/estilo_menu.css" />"
	rel="stylesheet">
<title>HOME</title>
</head>
<c:import url="/WEB-INF/views/template/cabecalho_home.jsp" />
<body>
	<h1>Seja bem vindo ${usuario.nome}</h1>
	<div class="principal">
		<ul class="menu">
			<li><a href="bem_vindo">Home</a></li>
			<li><a href="solicitar_movimentacao">Solicitar Movimentação</a></li>
			<li><a href="registro_solicitacao">Visualizar Solicitações</a></li>
			<li><a href="responder_solicitacao">Responder Solicitação</a></li>
			<li><a href="registro_movimentacao">Visualizar Movimentação</a></li>
			<li><a href="registro_conferencia">Visualizar Conferências</a></li>

			<li><a href="registro_patrimonio">Visualizar Patrimônios</a></li>
		</ul>
		<br /> <br /> <br /> <br /> <br />

		<form action="sair" method="post">
			<input id="sair" type="submit" value="Sair">
		</form>
	</div>
	<br />
	<br />
	<c:import url="/WEB-INF/views/template/rodape_home.jsp" />
</body>