<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<jsp:useBean id="data" class="java.util.Date"/>
<hr> <h4 style="text-align:center;"> São Paulo - SP <fmt:formatDate value="${data}" type="both"/> </h4>
