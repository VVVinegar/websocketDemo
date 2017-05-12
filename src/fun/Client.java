package fun;

import model.UserBean;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * Created by 759517209@qq.com on 2017/5/9.
 * 查询数据库
 */
public class Client {
    public java.util.List<UserBean> list (){
        String sql="select * from users";
        DB db=new DB();
        try{
            Statement stmt=db.con.createStatement();
            ResultSet rs=stmt.executeQuery(sql);
            //创建列表
            java.util.List<UserBean> lists = new ArrayList<UserBean>();
            while(rs.next()){
                UserBean user=new UserBean();
                user.setId(rs.getInt("id"));
                user.setName(rs.getString("name"));
                // 将user添加到List集合中
                lists.add(user);
            }
            return lists;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }finally {
            db.close();
        }
    }
}
