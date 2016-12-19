<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<jsp:useBean id="data" class="java.util.Date"/>
<!DOCTYPE html >
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Detalhes de Movimentacao</title>
<link href="<c:url value="/resources/css/estilo_lista2.css" />"
	rel="stylesheet">
</head>
<body background='<c:url value="/resources/imgs/bg.jpg"/>'>

<c:import url="/WEB-INF/views/template/cabecalho_gerenciamento.jsp"/>



<table class="tbHeader" >
		<tr>
			<td id="mov">Movimentacao</td>	
			<td id="pat">Patrimonios</td>					
			<td >Motivo</td>			
			
		</tr>
		
</table>

	<div id="tableData">
	<table class="tabela">
	
			<tr>
				<td id="mov">${movimentacao.id }</td>
				<td id="pat">
				${movimentacao.patrimonio.modelo.componente}
				
					
				</td>
				
				<td >${movimentacao.solicitacao.motivo }</td>		
		
			</tr>
	</table>
	
		
	
	


</div>
<form action="home" method="post">
	<input id="hm" type="submit" value="Home">
</form>

<c:import url="/WEB-INF/views/template/rodape_geral.jsp" />



</body>
</html>