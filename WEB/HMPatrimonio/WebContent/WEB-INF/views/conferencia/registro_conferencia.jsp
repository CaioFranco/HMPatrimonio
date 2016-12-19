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
<title>Registro de Conferencia</title>
<link href="<c:url value="/resources/css/estilo_lista3.css" />"
	rel="stylesheet">
</head>
<body background='<c:url value="/resources/imgs/bg.jpg"/>'>

<c:import url="/WEB-INF/views/template/cabecalho_gerenciamento.jsp"/>

	
<div class="formulario">


<table class="tbHeader">
	<tr >
			<td id="cod">Código</td>
			<td id="amb">Ambiente</td>	
			<td id="dtA">Agendamento</td>		
			<td id="dtC"> Conferência</td>			
			<td id="st">Status</td>
			<td id="det">Detalhar</td>
			
		</tr>
</table>


<div id="tableData">
	<table class="tabela">
	
		
		<c:forEach items="${conferencia }"  var="c">
			<tr>
				<td id="cod"> ${c.id }</td>
				<td id="amb"> ${c.agendamento.ambiente }</td>
				<td id="dtA"><fmt:formatDate value="${c.agendamento.data}" type="both" pattern="dd/MM/yyyy" /></td>
				<td id="dtC">
				<fmt:formatDate value="${c.data}" type="both" pattern="dd/MM/yyyy" />
				<c:if test="${empty c.data }">__/__/_____</c:if>
				
				</td>
				<td id="st"><c:if test="${empty c.data }">Não realizada</c:if>  
				
					<c:if test="${not empty c.data}">Realizada</c:if>
				</td>
			<td id="det"><a href="detalhar_conferencia?id=${c.id }"><img src='<c:url value="/resources/imgs/page.png"/>'></a></td>
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