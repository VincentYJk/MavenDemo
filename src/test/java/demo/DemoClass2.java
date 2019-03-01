package demo;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class DemoClass2 {
    @Test
    public void test1() {
        String filePath = "d:\\verdictLostRemove.txt";
        readTxtFile(filePath);
    }
    private  void readTxtFile(String filePath) {
        try {
            String encoding = "UTF-8";
            File file = new File(filePath);
            if (file.isFile() && file.exists()) { // 判断文件是否存在
                InputStreamReader read = new InputStreamReader(new FileInputStream(file), encoding);// 考虑到编码格式
                BufferedReader bufferedReader = new BufferedReader(read);
                String lineTxt;
                StringBuilder sb = new StringBuilder();
                while ((lineTxt = bufferedReader.readLine()) != null) {
                    search(lineTxt);
                }
                read.close();
                System.out.println(sb.toString());
            } else {
                System.out.println("找不到指定的文件");
            }
        } catch (Exception e) {
            System.out.println("读取文件内容出错");
            e.printStackTrace();
        }
    }
    private  void search(String name){

        if(StringUtils.isNotBlank(name)){
            String[] strS = name.split(";");
            if(strS.length == 3){
                Statement stmt = null;
                try {
                    Connection conn = getConnect();
                    stmt = conn.createStatement();
                    String sql;
                    //String VERDICTNUMBER = strS[2].trim().replace("(","（").replace(")","）");
                    String VERDICTNUMBER = strS[2].trim();
                    sql = "SELECT id, MINGCHENG as zhuti,ANHAO,DIYUMINGCHENG as diqu,JUTIQINGXING," +
                            "ZHENGJIANHAOMA,FABUSHIJIAN,ZHIXINGFAYUAN as fayuan,ZHIXINGYIJUDANWEI,SUOSHUWENJIAN FROM yantai_shixinbeizhixingren_jilu where mingcheng='"+strS[1].trim()+"' and anhao='"+VERDICTNUMBER+"'";
                    ResultSet rs = stmt.executeQuery(sql);
                    // 展开结果集数据库
                    while(rs.next()){
                        // 通过字段检索
                        String  id  = rs.getString("id");
                        String  zhuti  = rs.getString("zhuti");
                        String  anhao  = rs.getString("anhao");
                        String  diqu  = rs.getString("diqu");
                        String  jutiqingxing  = rs.getString("jutiqingxing");
                        String zhengjianhaoma =rs.getString("zhengjianhaoma");
                        String fabushijian =rs.getString("fabushijian");
                        String fayuan =rs.getString("fayuan");
                        String zhixingyijudanwei =rs.getString("zhixingyijudanwei");
                        String SUOSHUWENJIAN =rs.getString("SUOSHUWENJIAN");
                        System.out.println("<tr><td>"+id+"</td><td>"+zhuti+"</td><td>"+anhao+"</td><td>"+diqu+"</td><td>"+jutiqingxing+"</td><td>"+zhengjianhaoma+"</td><td>"+fabushijian+"</td><td>"+fayuan+"</td><td>"+zhixingyijudanwei+"</td><td>"+SUOSHUWENJIAN+"</td></tr>");

                    }
                    conn.close();
                    rs.close();
                }catch (Exception e){
                    System.out.println(e.getMessage());
                }
            }
        } else {
            System.out.println(name + ";数据错误");
        }
    }
    // JDBC 驱动名及数据库 URL
    static final String DB_URL = "jdbc:mysql://192.168.1.251:3306/website_yantai_develop?useUnicode=true&characterEncoding=UTF-8&useSSL=false";

    // 数据库的用户名与密码，需要根据自己的设置
    static final String USER = "develop";
    static final String PASS = "develop251";
    public static Connection getConnect()throws Exception{
        Connection conn = null;
        // 注册 JDBC 驱动
        Class.forName("com.mysql.jdbc.Driver");
        return  DriverManager.getConnection(DB_URL, USER, PASS);
    }
}
