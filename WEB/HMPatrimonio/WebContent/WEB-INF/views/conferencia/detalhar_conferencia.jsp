<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<jsp:useBean id="data" class="java.util.Date" />
<!DOCTYPE html >
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
<title>Detalhes de Conferencia</title>
<link href="<c:url value="/resources/css/estilo_lista3.css" />"
	rel="stylesheet">
</head>
<body background='<c:url value="/resources/imgs/bg.jpg"/>'>

	<c:import url="/WEB-INF/views/template/cabecalho_gerenciamento.jsp" />
	<c:set value="${conferencia.patrimonio_ausente}" var="pa" />

	<div class="conteudo">


		<form action="busca_status" method="post"
			enctype="multipart/form-data">
			<fieldset>
				<br />
				<legend>Pesquise</legend>
				<label>Status:</label> <select id="status" name="s">

					<option>Escolha o status</option>
					<option>Presente</option>
					<option>Ausente</option>


				</select> <input type="submit" value="Pesquisar"> <br /> <br /> <br />
			</fieldset>
			<br />
		</form>

		<h4>Nome do ambiente: ${conferencia.agendamento.ambiente}</h4>

		<h4>
			Data de agendamento:
			<fmt:formatDate value="${conferencia.agendamento.data}" type="date"
				pattern="dd/MM/yyyy" />
		</h4>

		<h4>
			Data de conferência:
			<fmt:formatDate value="${conferencia.data}" type="date"
				pattern="dd/MM/yyyy" />
		</h4>
		<br />


		<table class="tbHeader">
			<tr>
				<td id="num">Nº Identificador</td>
				<td id="desc">Descrição</td>
				<td id="mod">Modelo</td>
				<td id="marca">Marca</td>
				<td id="st">Status</td>
			</tr>
		</table>

		<div id="tableData">

			<table class="tabela">

				<c:forEach items="${patrimonios }" var="p">

					<c:if
						test="${conferencia.agendamento.ambiente.id.equals(p.ambiente.id)}">

						<tr>
							<td id="num">${p.id }</td>
							<td id="desc">${p.modelo.descricao }</td>
							<td id="mod">${p.modelo.componente }</td>
							<td id="marca">${p.modelo.marca }</td>
							<td id="st"><c:choose>
									<c:when test="${pa.indexOf(p)>= 0}">Ausente</c:when>
									<c:otherwise>Presente</c:otherwise>

								</c:choose></td>
						</tr>
					</c:if>



				</c:forEach>



			</table>

		</div>
		<form action="home" method="post">
			<input id="hm" type="submit" value="Home">
		</form>
	</div>
	<c:import url="/WEB-INF/views/template/rodape_geral.jsp" />



</body>
</html>
