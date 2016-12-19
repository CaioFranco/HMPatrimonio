<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Espaço Gerenciamento</title>
<link href="<c:url value="/resources/css/estilo_geral.css" />"
	rel="stylesheet">
</head>
<body background='<c:url value="/resources/imgs/bg.jpg"/>'>

<c:import url="/WEB-INF/views/template/cabecalho_gerenciamento.jsp"/>

<div class="geral">
 <div class="wrapper">

			<div class="wrapperNavlist">
	  			<ul class="navlist">
	  				<li><a href="solicitar_movimentacao">Solicitar Movimentação</a></li> 
		  			<li><a href="registro_solicitacao">Visualizar Solicitações</a></li>		  			
		  			<li><a href="responder_solicitacao">Responder Solicitação</a></li>
		  			<li><a href="registro_movimentacao">Visualizar de Movimentação</a></li>
		  			<li><a href="registro_conferencia">Visuzalizar de Conferências</a></li>
		  			
		  			<li><a href="registro_patrimonio">Visuzalizar de Patrimônios</a></li>
		  			
		  		</ul>
	  		</div>
	  		<div class="wrapperNavlist">
	  			<ul class="navlist">
	  		
				</ul>
			</div>
			<div class="wrapperNavlist">
	  			<ul class="navlist">
	  			<li><a href=<c:url value="/home.jsp"/>>Início</a></li>
			</ul>
			</div>
    
    </div>

<div class="conteudo">

<h1>Seja bem vindo ${usuario.nome} ${usuario.sobrenome}</h1>
<h1>H+Desk </h1>
<p>
Painel de Administrador<br/><br/><br/>
</p>
<img src='<c:url value="/resources/imgs/admin.png"/>'>
</div>


<c:import url="/WEB-INF/views/template/rodape_geral.jsp" />


</div>
</body>
</html>