package fun;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * Created by 759517209@qq.com on 2017/5/9.
 * 这个类即实现了进行数据库操作的Servlet类，又实现了Websocket的功能.
 */
//该注解用来指定一个URI，客户端可以通过这个URI来连接到WebSocket，类似Servlet的注解mapping；
// servlet的注册放在了web.xml中。
@ServerEndpoint(value = "/websocket")
public class ManagerServlet extends HttpServlet {
    //concurrent包的线程安全Set，用来存放每个客户端对应的MyWebSocket对象。若要实现服务端与单一客户端通信的话，可以使用Map来存放，其中Key可以为用户标识
    private static CopyOnWriteArraySet<ManagerServlet> webSocketSet = new CopyOnWriteArraySet<ManagerServlet>();
    //这个session不是Httpsession，相当于用户的唯一标识，用它进行与指定用户通讯
    private  javax.websocket.Session session=null;

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException,IOException {
        String msg;
        String name=request.getParameter("name");
        //这里submit是数据库操作的方法，如果插入数据成功，则发送更新信号
        if(submit(name)){
            //发送更新信号
            sendMessage();
            msg="ok!";
        }else {
            msg="error!";
        }
        response.sendRedirect("manager.jsp?msg="+msg);
    }
    public void doPost(HttpServletRequest request,HttpServletResponse reponse) throws ServletException, IOException {
        doGet(request,reponse);
    }

    /**
     * 向数据库插入一个name
     * @param name
     * @return
     */
    public boolean submit(String name){
        DB db=new DB();
        String sql="insert into users(name) values(?)";
        try{
            PreparedStatement pstmt=db.con.prepareStatement(sql);
            pstmt.setString(1,name);
            pstmt.executeUpdate();
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }finally {
            db.close();
        }
    }

    /**
     * @OnOpen allows us to intercept the creation of a new session.
     * The session class allows us to send data to the user.
     * In the method onOpen, we'll let the user know that the handshake was
     * successful.
     * 建立websocket连接时调用
     */
    @OnOpen
    public void onOpen(Session session){
        System.out.println("Session " + session.getId() + " has opened a connection");
        try {
            this.session=session;
            webSocketSet.add(this);     //加入set中
            session.getBasicRemote().sendText("Connection Established");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * When a user sends a message to the server, this method will intercept the message
     * and allow us to react to it. For now the message is read as a String.
     * 接收到客户端消息时使用，这个例子里没用
     */
    @OnMessage
    public void onMessage(String message, Session session){
        System.out.println("Message from " + session.getId() + ": " + message);
    }

    /**
     * The user closes the connection.
     *
     * Note: you can't send messages to the client from this method
     * 关闭连接时调用
     */
    @OnClose
    public void onClose(Session session){
        webSocketSet.remove(this);  //从set中删除
        System.out.println("Session " +session.getId()+" has closed!");
    }

    /**
     * 注意: OnError() 只能出现一次.   其中的参数都是可选的。
     * @param session
     * @param t
     */
    @OnError
    public void onError(Session session, Throwable t) {
        t.printStackTrace();
    }

    /**
     * 这个方法与上面几个方法不一样。没有用注解，是根据自己需要添加的方法。
     * @throws IOException
     * 发送自定义信号，“1”表示告诉前台，数据库发生改变了，需要刷新
     */
    public void sendMessage() throws IOException{
        //群发消息
        for(ManagerServlet item: webSocketSet){
            try {
                item.session.getBasicRemote().sendText("1");
            } catch (IOException e) {
                e.printStackTrace();
                continue;
            }
        }

    }
}
