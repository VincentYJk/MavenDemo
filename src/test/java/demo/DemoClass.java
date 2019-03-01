package demo;

import demo.util.MySqlConnectUtil;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;

public class DemoClass {
    SimpleDateFormat dateFormat_true = new SimpleDateFormat("yyyy-MM-dd");
    SimpleDateFormat dateFormat_false = new SimpleDateFormat("YYYY");

//    @Test
//    public void test() {
//        String aString = "2017-12-31";
//        long aTime = parseTime(aString);
//        System.out.println(dateFormat_false.format(aTime));
//    }
//
//    private long parseTime(String strTime) {
//        long time = 0L;
//        try {
//            time = dateFormat_true.parse(strTime).getTime();
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//        return time;
//    }


    @Test
    public void test1() {
        String filePath = "d:\\verdictLost.txt";
        readTxtFile(filePath);
    }


    private static void search (String name){
        if(StringUtils.isNotBlank(name)){
            String[] strS = name.split(";");
            if(strS.length == 3){
                Statement stmt = null;
                try {
                    Connection conn = MySqlConnectUtil.getConnect();
                    stmt = conn.createStatement();
                    String sql;
                    //String VERDICTNUMBER = strS[2].trim().replace("(","（").replace(")","）");
                    String VERDICTNUMBER = strS[2].trim();
                    sql = "SELECT count(1) FROM yt_verdict_lost where COMPANYNAME='"+strS[1].trim()+"' and VERDICTNUMBER='"+VERDICTNUMBER+"'";
                    ResultSet rs = stmt.executeQuery(sql);
                    // 展开结果集数据库
                    while(rs.next()){
                        // 通过字段检索
                        int count  = rs.getInt("count(1)");
                        if(count>0){
                            System.out.println(name+ ";存在>>>>>>>>>>>>>>>>>>>>>>>>>>>");
                        }else{
                            System.out.println(name+ ";不存在");
                        }
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
    private static void readTxtFile (String filePath){
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
}
