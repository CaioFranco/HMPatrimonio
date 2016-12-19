<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<jsp:useBean id="data" class="java.util.Date"/>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<script type="text/javascript" src="resources/js/jquery.js"></script>
<script type="text/javascript" src="resources/js/jquery.maskedinput.js"></script>
<script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script> 
<script src="http://ajax.googleapis.com/ajax/libs/jqueryui/1.11.2/jquery-ui.min.js"></script>
<script type="text/javascript" src="resources/js/paging.js"></script> 

	<script type="text/javascript">
			function alert() {
    		alert("Resposta enviada com sucesso!");
			}
	</script>


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
<title>Espaço Gerenciamento</title>
<link href="<c:url value="/resources/css/estilo_lista2.css" />"
	rel="stylesheet">

</head>
<body background='<c:url value="/resources/imgs/bg.jpg"/>'>

<c:import url="/WEB-INF/views/template/cabecalho_gerenciamento.jsp"/>

	

<div class="conteudo">

<table class="tbHeader">
		<tr>		
		<td id="cod">Código</td>
		
			<td id="dest" >Solicitante</td>
			<td id="sol" >Ambiente </td>
			<td id="dts">Data </td>
			<td id="det">Detalhar</td>
			
			<td colspan="2">Escolha</td>
			
		</tr>
</table>
	<div id="tableData" >
			<table class="tabela" >			
				
			
		<c:forEach items="${solicitacao }"  var="s">
		
			<tr>
			<td id="cod">${s.id}</td>
			
				<td id="dest"> ${s.ambiente_destino.usuario} </td>
				<td id="sol"> ${s.ambiente_destino} </td>
				<td id="dts"><fmt:formatDate value="${s.data.time}" type="both" pattern="dd/MM/yyyy" /></td>
				<td id="det"><a href="detalhar_solicitacao?id=${s.id }"><img
								src='<c:url value="/resources/imgs/page.png"/>'></a></td>
				<td><a  href="rejeitar?id=${s.id }"> <img src='<c:url value="/resources/imgs/block.png"/>'></a></td>
				<td><a  href="permitir?id=${s.id }"> <img src='<c:url value="/resources/imgs/check.png"/>'></a></td>
	
			</tr>
		
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