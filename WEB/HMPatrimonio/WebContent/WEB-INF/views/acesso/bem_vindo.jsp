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
<div class="principal">
<h1>Seja bem vindo ${usuario.nome} </h1>
  <ul class="menu">
		<li><a href="home">Home</a></li>
		<li><a href="gestor">Espaço Gestor</a></li>
	  		<li><a href="#">Espaço Empresa</a>
	         	<ul>
	                  <li><a href="empresa">Espaço Dono</a></li>
	                  <li><a href="cliente">Espaço Funcionário</a></li>
	                              
	       		</ul>
			</li>
		<li><a href="profissionais">Espaço Profissionais</a></li>
		<li><a href="contato">Contato</a></li>      
</ul>
<br/><br/><br/><br/><br/>
</div>	
<br/><br/><br/>
<c:import url="/WEB-INF/views/template/rodape_home.jsp" />
</body>
	
