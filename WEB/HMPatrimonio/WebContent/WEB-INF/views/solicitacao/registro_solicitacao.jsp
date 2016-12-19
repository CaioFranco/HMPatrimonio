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
<script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script> 
<script src="http://ajax.googleapis.com/ajax/libs/jqueryui/1.11.2/jquery-ui.min.js"></script>
<script type="text/javascript" src="resources/js/paging.js"></script> 




<script type="text/javascript">
            $(document).ready(function() {
                $('#tableData').paging({limit:10});
            });
        </script>
<script type="text/javascript">

  var _gaq = _gaq || [];
  _gaq.push(['_setAccount', 'UA-36251023-1']);
  _gaq.push(['_setDomainName', 'jqueryscript.net']);
  _gaq.push(['_trackPageview']);

  (function() {
    var ga = document.createElement('script'); ga.type = 'text/javascript'; ga.async = true;
    ga.src = ('https:' == document.location.protocol ? 'https://ssl' : 'http://www') + '.google-analytics.com/ga.js';
    var s = document.getElementsByTagName('script')[0]; s.parentNode.insertBefore(ga, s);
  })();

</script>
<title>Registro de Solicitação</title>
<link href="<c:url value="/resources/css/estilo_lista1.css" />"
	rel="stylesheet">
</head>
<body background='<c:url value="/resources/imgs/bg.jpg"/>'>

	<c:import url="/WEB-INF/views/template/cabecalho_gerenciamento.jsp" />


	

	<div class="formulario">

		<form action="busca_destino" method="post"
			enctype="multipart/form-data">
			<fieldset>
				<legend>Pesquise</legend>
				<br /> <label>Destino:</label> <select id="ambiente" name="idAmbiente">
					<option>selecione um ambiente destino</option>
					<c:forEach items="${ambiente }" var="a">
						<option value="${a.id }">
							<c:if test="${a.id.equals(pr.id) }">selected</c:if>${a}</option>
					</c:forEach>
				</select> <input id="pesq" type="submit" value="Pesquisar"> <br /> <br /> <br />
		</form>
		<form action="busca_resposta" method="post"
			enctype="multipart/form-data">
			<label>Resposta:</label> <select id="resposta"
				name="r">
				<option>Escolha a resposta</option>
				<option>permitada</option>
				<option>rejeitada</option>
			</select> <input id="pesq" type="submit" value="Pesquisar"> <br />
			</fieldset>
			<br/>
		</form>


<table class="tbHeader">
		<tr>		
		<td id="ori" >Origem</td>
		<td id="dest">Destino</td>
		<td id="dts">Data da Solicitação</td>
		<td id="resp">Resposta</td>
		<td id="dtr">Data de Resposta</td>
		<td id="det">Detalhar</td>
			
		</tr>
</table>
		

		<div id="tableData" >
			<table class="tabela" >			
				
				<c:forEach items="${solicitacao }" var="s">
					<tr>
					
						<td id="ori" >${s.ambiente_origem }</td>
						<td id="dest" >${s.ambiente_destino }</td>
						<td id="dts"><fmt:formatDate value="${s.data.time}" type="both"
								pattern="dd/MM/yyyy" /></td>
						<td id="resp"><c:if test="${s.resposta.equals(false) }">Rejeitada</c:if>
							<c:if test="${s.resposta.equals(true) }">Permitida</c:if></td>


						<td id="dtr"><fmt:formatDate value="${s.data.time}" type="both"
								pattern="dd/MM/yyyy" /></td>
						<td id="det"><a href="detalhar_solicitacao?id=${s.id }"><img
								src='<c:url value="/resources/imgs/page.png"/>'></a></td>
					</tr>

				</c:forEach>

			</table>

		</div>
		<form action="home" method="post">
	<input id="hm" type="submit" value="Home">
</form>
		<c:import url="/WEB-INF/views/template/rodape_geral.jsp" />


	</div>
	
</body>
</html>

