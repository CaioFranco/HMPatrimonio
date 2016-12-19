<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
	<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Página não Encontrada</title>
<link href="<c:url value="/resources/css/estilo_geral.css" />"
	rel="stylesheet">
</head>
<body background='<c:url value="/resources/imgs/bg.jpg"/>' >
	<br/>
	<figure>

		<img src='<c:url value="/resources/imgs/erro01.jpg"/>'>


	</figure>

	<h1>Erro ao acessar!</h1>
	<h5><a href=<c:url value="/"/>>clique aqui para tentar novamente</a></h5>
	
	
	
</body>
</html>