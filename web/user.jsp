<%@ page import="model.UserBean" %>
<%@ page import="java.util.List" %>
<%@ page import="fun.Client" %>
<%--
  Created by IntelliJ IDEA.
  Client: 759517209@qq.com
  Date: 2017/5/9
  Time: 13:29
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head>
    <title>user</title>
  </head>
  <body>
    <table  id="table" border="1">
      <tr>
        <th >id</th>
        <th >name</th>
      </tr>
      <%
        //得到数据库信息，放在list中
        Client client=new Client();
        List<UserBean> list= client.list();
        if(list != null){
          for(UserBean user : list){
      %>
      <tr >
        <td ><%=user.getId()%></td>
        <td ><%=user.getName()%></td>
      </tr>
      <%
          }
        }
      %>
    </table>
    <div id="message"></div>

  <script>
      var websocket = null;
      //判断当前浏览器是否支持WebSocket
      if ('WebSocket' in window) {
          //建立连接，这里的/websocket ，是ManagerServlet中开头注解中的那个值
          websocket = new WebSocket("ws://localhost:8080/websocket");
      }
      else {
          alert('当前浏览器 Not support websocket')
      }
      //连接发生错误的回调方法
      websocket.onerror = function () {
          setMessageInnerHTML("WebSocket连接发生错误");
      };
      //连接成功建立的回调方法
      websocket.onopen = function () {
          setMessageInnerHTML("WebSocket连接成功");
      }
      //接收到消息的回调方法
      websocket.onmessage = function (event) {
          setMessageInnerHTML(event.data);
          if(event.data=="1"){
              location.reload();
          }
      }
      //连接关闭的回调方法
      websocket.onclose = function () {
          setMessageInnerHTML("WebSocket连接关闭");
      }
      //监听窗口关闭事件，当窗口关闭时，主动去关闭websocket连接，防止连接还没断开就关闭窗口，server端会抛异常。
      window.onbeforeunload = function () {
          closeWebSocket();
      }
      //将消息显示在网页上
      function setMessageInnerHTML(innerHTML) {
          document.getElementById('message').innerHTML += innerHTML + '<br/>';
      }
      //关闭WebSocket连接
      function closeWebSocket() {
          websocket.close();
      }
  </script>
  </body>
</html>
