<%--
  Created by IntelliJ IDEA.
  User: 759517209@qq.com
  Date: 2017/5/9
  Time: 15:41
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>manager</title>
</head>
<body>
    <%String msg =  request.getParameter("msg" )!=null? request.getParameter("msg"):"";%>
    <p><%=msg%></p>
    <form action="ManagerServlet" method="post">
        请输入姓名：<input type="text" name="name" >
        <input type="submit" value="ok">
    </form>
</body>
</html>
