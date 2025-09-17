<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head><meta charset="UTF-8"><title>User Page</title></head>
<body>

<jsp:include page="/WEB-INF/header.jsp" />

<%
    String fromUser = (String) request.getAttribute("UserServlet");
    if (fromUser == null) {
%>
<b>Нелегальний запит в обхід сервлету</b>
<%
} else {
%>
<h1>Hello, user</h1>
<h3><b>Передача даних від серверу (контроллера)</b></h3>
<p><%= fromUser %></p>
<% } %>


<jsp:include page="/WEB-INF/footer.jsp" />

</body>
</html>
