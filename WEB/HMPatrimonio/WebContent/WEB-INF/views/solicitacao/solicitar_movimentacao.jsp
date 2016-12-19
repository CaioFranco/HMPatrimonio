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

<link href="<c:url value="/resources/css/estilo_lista.css" />"
	rel="stylesheet">

<title>Espaço Gerenciamento</title>
</head>



<body background='<c:url value="/resources/imgs/bg.jpg"/>'>


	<c:import url="/WEB-INF/views/template/cabecalho_gerenciamento.jsp" />


	<div class="conteudo">

		<h1>${usuario.nome}</h1>

		<p>
			Para solicitar movimentação de patrimônio preencha adequadamente o
			formulário abaixo <br /> <br /> <br /> <br />
		</p>


		<div class="formulario">


			<div class="formulario2">
			<form action="busca_patrimonio" method="post"
				enctype="multipart/form-data">
				<fieldset>
				<legend> Ambiente Origem</legend><br/>
				<label>Ambiente:</label>
				 <select id="ambiente" name="idAmbiente" required="required">
					<option>selecione um ambiente</option>
					<c:forEach items="${ambiente }" var="a">
						<option value="${a.id }">
							<c:if test="${a.id.equals(pr.id) }">selected</c:if>${a}</option>
					</c:forEach>
				</select> <input type="submit" value="Pesquisar"> <br /> <br /> <br />
				</fieldset>
			</form>
			</div>



			<form action="AddSolicitacao" method="post"
				enctype="multipart/form-data">
				<fieldset>
					<legend>Dados da solicitação</legend>
					<br />

					<label>Destino:</label> <input type="text"
						name="ambiente_destino.id" value="${amb.id }" hidden="hidden">
					<h5>${amb }</h5>
					<br /> <label>Data :</label> <input type="date"
						readonly="readonly" name="data"
						value="<fmt:formatDate value="${data}" type="both" pattern="YYYY-MM-dd"/>">
					<br /> <br />
				</fieldset>


				<br />


				<table class="tbHeader">
					<tr>
						<td id="ide">Cód.</td>
						<td id="desc">Descrição</td>
						<td id="sit">Situação</td>
						<td id="esc">Escolha</td>
						<td id="amb">Ambiente Origem</td>

					</tr>
				</table>
				<div id="tableData">
					<table class="tabela">


						<c:forEach items="${patrimonio }" var="p">
							<tr>
								<td id="ide">${p.id }</td>
								<td id="desc">${p.modelo.componente }</td>
								<td id="sit">${p.situacao.toString() }</td>

								<td id="esc"><input type="checkbox" name="patrimonio"
									value=${p.id }></td>


								<td id="amb">${p.ambiente }</td>

							</tr>

						</c:forEach>



					</table>

				</div>

				<br /> <br />

				<fieldset class="fim">
					<br /> <label>Motivo:</label>
					<textarea name="motivo" value="${solicitacao.motivo }"
						required="required"></textarea>

				</fieldset>

				<input id="sl" type="submit" value="Solicitar">

			</form>

			<form action="home" method="post">
				<input id="hm" type="submit" value="Home">
			</form>

		</div>
	</div>
	<c:import url="/WEB-INF/views/template/rodape_geral.jsp" />



</body>
</html>
