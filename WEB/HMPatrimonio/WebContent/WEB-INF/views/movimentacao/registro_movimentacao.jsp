<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<jsp:useBean id="data" class="java.util.Date" />
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<script type="text/javascript" src="resources/js/jquery.js"></script>
<script type="text/javascript" src="resources/js/jquery.maskedinput.js"></script>
<script type="text/javascript"
	src="http://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
<script
	src="http://ajax.googleapis.com/ajax/libs/jqueryui/1.11.2/jquery-ui.min.js"></script>
<script type="text/javascript" src="resources/js/paging.js"></script>
<script type="text/javascript">
	$(document).ready(function() {
		$('#tableData').paging({
			limit : 10
		});
	});
</script>
<script type="text/javascript">
	var _gaq = _gaq || [];
	_gaq.push([ '_setAccount', 'UA-36251023-1' ]);
	_gaq.push([ '_setDomainName', 'jqueryscript.net' ]);
	_gaq.push([ '_trackPageview' ]);

	(function() {
		var ga = document.createElement('script');
		ga.type = 'text/javascript';
		ga.async = true;
		ga.src = ('https:' == document.location.protocol ? 'https://ssl'
				: 'http://www')
				+ '.google-analytics.com/ga.js';
		var s = document.getElementsByTagName('script')[0];
		s.parentNode.insertBefore(ga, s);
	})();
</script>
<title>Espaço Gerenciamento</title>
<link href="<c:url value="/resources/css/estilo_lista2.css" />"
	rel="stylesheet">
</head>
<body background='<c:url value="/resources/imgs/bg.jpg"/>'>

	<c:import url="/WEB-INF/views/template/cabecalho_gerenciamento.jsp" />



		<div class="formulario">

			<form action="buscar_destino" method="post"
				enctype="multipart/form-data">
				<fieldset>
					<legend>Pesquise</legend>
					<br /> <label>Destino:</label> <select id="ambiente"
						name="idAmbiente">
						<option>selecione um ambiente destino</option>
						<c:forEach items="${ambiente }" var="a">
							<option value="${a.id }">
								<c:if test="${a.id.equals(pr.id) }">selected</c:if>${a}</option>
						</c:forEach>
					</select> <input id="pesq" type="submit" value="Pesquisar"> <br /> <br /> <br />
				</fieldset>
				<br />
			</form>

			<table class="tbHeader">
				<tr>
					<td id="dest">Destino</td>
					<td id="sol">Solicitante</td>					
					<td id="dtm">Data de Movimentação</td>
					<td id="det"> Detalhar</td>
				</tr>



			</table>



			<div id="tableData">
				<table class="tabela">

					<c:forEach items="${movimentacao }" var="mv">
						<tr>
							<td id="dest">${mv.ambiente}</td>
							<td id="dest">${mv.ambiente.usuario}</td>

							<td id="dtm"><fmt:formatDate value="${mv.data.time}" type="both"
									pattern="dd/MM/yyyy" /></td>

							<td id="det"><a href="detalhar_movimentacao?id=${mv.id }"><img
									src='<c:url value="/resources/imgs/page.png"/>'></a></td>
						</tr>

					</c:forEach>



				</table>

			</div>
		</div>
		
		<form action="home" method="post">
	<input id="hm" type="submit" value="Home">
</form>
		<c:import url="/WEB-INF/views/template/rodape_geral.jsp" />


	
</body>
</html>
