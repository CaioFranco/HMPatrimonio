<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<jsp:useBean id="data" class="java.util.Date"/>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Patrimônios</title>
<link href="<c:url value="/resources/css/estilo_lista4.css" />"
	rel="stylesheet">
	
	
	<script type="text/javascript" src="resources/js/jquery.js"></script>
<script type="text/javascript" src="resources/js/jquery.maskedinput.js"></script>
<script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script> 
<script src="http://ajax.googleapis.com/ajax/libs/jqueryui/1.11.2/jquery-ui.min.js"></script>
<script type="text/javascript" src="resources/js/paging.js"></script> 




<script type="text/javascript">
            $(document).ready(function() {
                $('#tableData').paging({limit:20});
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
	
</head>
<body background='<c:url value="/resources/imgs/bg.jpg"/>'>

<c:import url="/WEB-INF/views/template/cabecalho_gerenciamento.jsp"/>



<div class="formulario">
<fieldset>
<legend>Pesquise</legend><br/>

	<form action="busca_identificador" method="post" enctype="multipart/form-data">	
		
		<label>N.I.: </label>			 	
		<input type="text" placeholder="Identificador" required name="identificador" value="${patrimonios.id}"> <input type="submit" value="Pesquisar"> 
	</form>
		<br/>

	<form action="busca_descricao" method="post" enctype="multipart/form-data">	
		
		<label>Descrição: </label>			 	
		<input type="text" placeholder="Descrição" required name="descricao" value="${patrimonios.modelo.descricao }"> <input type="submit" value="Pesquisar"> 
	</form>
		<br/>
		
	<form action="busca_modelo" method="post" enctype="multipart/form-data">
		<label>Modelo: </label>			 	
		<input type="text"  placeholder="Modelo" required name="componente" value="${patrimonios.modelo.componente }"> <input type="submit" value="Pesquisar"> 
	</form>
		<br/>
		
	<form action="busca_ambiente" method="post"	enctype="multipart/form-data">
			 <label>Ambiente:</label> 
			   <select id="ambiente" name="idAmbiente">
						<option>selecione um ambiente </option>
						<c:forEach items="${ambiente }" var="a">
							<option value="${a.id }">
								<c:if test="${a.id.equals(pr.id) }">selected</c:if>${a}</option>
						</c:forEach>
				</select> <input type="submit" value="Pesquisar"> <br /> <br /> <br />
	</form>
	
	<form action="busca_situacao" method="post"	enctype="multipart/form-data">
		 <label>Situação:</label>
		    <select id="situacao"	name="s">
					<option>selecione uma situação</option>
					<option>Novo</option>
					<option>Seminovo</option>
		    </select> <input type="submit" value="Pesquisar"> <br /> <br /> <br />
	</form>
		
		
		
	
</fieldset>	
	
	
		

<br/>

<table class="tbHeader">
	<tr>
			<td id="cod">N.I.</<td>
			<td id="desc">Descrição</td>			
			<td id="mod">Modelo</td>			
			<td id="cat">Categoria</td>
			<td id="amb">Ambiente</td>
			<td id="sit">Situação</td>
			<td id="dtE">Dt Entrada</td>
			<td id="fnd">Fornecedor</td>
			<td id="vlu">Vl. Unitário</td>
			<td id="st">Status</td>
	</tr>
</table>

	<div id="tableData">
		<table class="tabela">
			
		<c:forEach items="${patrimonio }"  var="p">
		
			<tr>
				<td id="cod"> ${p.id }</td>
				<td id="desc"> ${p.modelo.descricao }</td>
				<td id="mod"> ${p.modelo.componente }</td>				
				<td id="cat"> ${p.modelo.categoria.toString() }</td>	
				<td id="amb"> ${p.ambiente }</td>
				<td id="sit"> ${p.situacao.toString()}</td>
				<td id="dtE"><fmt:formatDate value="${p.data_entrada.time}" type="both" pattern="dd/MM/yyyy" /></td>
				<td id="fnd"> ${p.modelo.fornecedor.toString()}</td>
				<td id="vlu"> ${p.valor_unitario}</td>		
				<td id="st">
					<c:if test="${p.status.equals(false) }">Inativo</c:if>
					<c:if test="${p.status.equals(true) }">Ativo</c:if>	
	 			</td>	
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


