<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<link href="resources/css/estilo_geral.css" rel="stylesheet">
<jsp:useBean id="data" class="java.util.Date"/>
<div class="footer">
Copyright © H+Desk
 <hr> <h4 style="text-align:center;"> São Paulo - SP <fmt:formatDate value="${data}" type="both"/> </h4>
</div>