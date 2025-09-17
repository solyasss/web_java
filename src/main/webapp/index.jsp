<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>JSP Page</title>
</head>
<body>

<jsp:include page="/WEB-INF/header.jsp" />
<h1>Java web. JSP</h1>

<%
    String fromServlet = (String) request.getAttribute("HomeServlet");
    if (fromServlet == null) {
%>
<b>Нелегальний запит в обхід сервлету</b>
<%
} else {
%>

<h2>Java EE</h2>
<p>
    Java Enterprise Edition - Java + додаткові модулі для роботи з мережею.
    Також до складу входить сервер застосунків (Application server),
    проте, може знадобитись встановити його окремо:
    <a href="https://tomcat.apache.org/">Apache Tomcat</a>
    <a href="https://glassfish.org/">Eclipse GlassFish</a>
    <a href="https://www.wildfly.org/">WildFly</a>
</p>

<h2>JSP</h2>
<p>Java Server Pages - технологія створення веб-сторінок</p>

<h3>Вирази та інструкції</h3>
<p>
    <code>&lt;% String str = "Hello, World!"; %&gt;</code>
    <% String str = "Hello, World!"; %><br/>
    <code>&lt;%= str + "!" %&gt; &rarr; <%= str + "!" %></code>
</p>

<h3>Управління виконанням</h3>
<p>
    <% String[] arr = {"Item 1","Item 2","Item 3","Item 4","Item 5"}; %>
    Спеціальних операторів JSP немає, управління формуються засобами інструкцій,
    що розриваються для виведення HTML.
</p>

<pre>
&lt;ul&gt;
    &lt;% for (int i = 0; i &lt; arr.length; i++) { %&gt;  &lt;----|цикл
        &lt;li&gt;&lt;%= arr[i] %&gt;&lt;/li&gt;                         |  тіло циклу
    &lt;% } %&gt;                                       &lt;----|кінець
&lt;/ul&gt;
</pre>

<ul>
    <% for (int i = 0; i < arr.length; i++) { %>
    <li><%= arr[i] %></li>
    <% } %>
</ul>

<p>Вивести парні індекси курсивом, непарні — жирним (0 вважати парним)</p>
<ul>
    <% for (int i = 0; i < arr.length; i++) { %>
    <li>
        <% if (i % 2 == 0) { %>
        <i><%= arr[i] %></i>
        <% } else { %>
        <b><%= arr[i] %></b>
        <% } %>
    </li>
    <% } %>
</ul>

<h3>Шаблонізація, підключення компонентів</h3>
<jsp:include page="fragment.jsp">
    <jsp:param name="key" value="Value"/>
</jsp:include>

<h3>Передача даних від сервлету (контроллера)</h3>
<%= request.getAttribute("HomeServlet") %>

<% } %>

<jsp:include page="/WEB-INF/footer.jsp" />
</body>
</html>
