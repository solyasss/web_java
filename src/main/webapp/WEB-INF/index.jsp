<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Java Web Demo</title>
    <style>
        body {
            font-family: 'Segoe UI', sans-serif;
            margin: 0;
            padding: 0;
            background-color: #fffafa;
            color: #333;
        }
        h1, h2, h3 {
            color: #cc6699;
            margin-bottom: 8px;
        }
        p, li {
            line-height: 1.6;
        }
        .container {
            width: 85%;
            margin: 0 auto;
            padding: 20px;
        }
        .card {
            background: #fff;
            border: 1px solid #f2dce4;
            border-radius: 12px;
            padding: 20px;
            margin-bottom: 20px;
            box-shadow: 0 2px 6px rgba(204, 102, 153, 0.1);
        }
        .highlight {
            background: #ffe6f0;
            border-left: 5px solid #cc6699;
            padding: 10px;
            border-radius: 6px;
            margin: 10px 0;
        }
        .timestamp {
            font-size: 1.5em;
            font-weight: bold;
            color: #fff;
            background: #cc6699;
            padding: 8px 14px;
            border-radius: 8px;
            display: inline-block;
            margin-top: 10px;
        }
        a {
            color: #cc6699;
            text-decoration: none;
        }
        a:hover {
            text-decoration: underline;
        }
        code {
            background: #fbeaf0;
            padding: 2px 6px;
            border-radius: 4px;
        }
    </style>
</head>
<body>
<jsp:include page="/WEB-INF/header.jsp" />

<div class="container">
    <h1>Java Web Project</h1>

    <%
        String fromServlet = (String) request.getAttribute("HomeServlet");
        String timestamp = (String) request.getAttribute("UnixTimeStampSeconds");
        if (fromServlet == null) {
    %>
    <div class="card">
        <b>Illegal request bypassing servlet</b>
    </div>
    <%
    } else {
    %>

    <div class="card">
        <h2>Message</h2>
        <div class="highlight"><%= fromServlet %></div>
    </div>

    <div class="card">
        <h2>Unix Timestamp Service</h2>
        <p style="margin-bottom:12px;">Get the number of seconds since the UNIX epoch:</p>

        <form style="display:flex; gap:10px; align-items:center; flex-wrap:wrap;">
            <input type="text"
                   value="<%= timestamp %>"
                   readonly
                   style="flex:1; padding:10px 14px; border:2px solid #cc6699; border-radius:8px;
                      font-size:1.2em; color:#cc3366; background:#fffafc; font-weight:bold;"/>
            <button type="button"
                    onclick="location.reload()"
                    style="padding:10px 18px; border:none; border-radius:8px; background:#cc6699;
                       color:#fff; font-size:1em; cursor:pointer; transition:0.2s;">
                 Refresh
            </button>
        </form>

        <p style="margin-top:10px; color:#888; font-size:0.9em;">
            Updates every time you refresh the page
        </p>
    </div>


    <div class="card">
        <h2>About Java</h2>
        <p>
            Java Enterprise Edition (Jakarta EE) extends Java with modules for networking and enterprise-level applications.
            It includes an application server, though it might need to be installed separately:
        </p>
        <ul>
            <li><a href="https://tomcat.apache.org/">Apache Tomcat</a></li>
            <li><a href="https://glassfish.org/">Eclipse GlassFish</a></li>
            <li><a href="https://www.wildfly.org/">WildFly</a></li>
        </ul>
    </div>

    <div class="card">
        <h2>JSP</h2>
        <p>Java Server Pages is a technology for building dynamic web pages.</p>
        <h3>Expressions & Instructions</h3>
        <p>
            Example: <code>&lt;% String str = "Hello, World!"; %&gt;</code><br/>
            Result: <code>&lt;%= str + "!" %&gt; → <%= "Hello, World!" + "!" %></code>
        </p>
    </div>

    <div class="card">
        <h2>Fragment Example</h2>
        <jsp:include page="../fragment.jsp">
            <jsp:param name="key" value="Value"/>
        </jsp:include>
    </div>

    <% } %>
</div>

<jsp:include page="/WEB-INF/footer.jsp" />
</body>
</html>
