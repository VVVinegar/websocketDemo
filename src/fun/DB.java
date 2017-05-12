package fun;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 * 数据库连接，断开
 */
public class DB {
    String url = "jdbc:mysql://127.0.0.1:3306/websockettest?characterEncoding=UTF-8";
    String user="root";
    String password="";

    public Connection con = null;

    public DB(){
        try{
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection(url,user,password);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void close(){
        try{
            con.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
