package demo;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName TestDemo
 * @Description TODO
 * @Author 梁明辉
 * @Date 2019/5/21 8:21
 * @ModifyDate 2019/5/21 8:21
 * @Version 1.0
 */
public class TestDemo {
    @Test
    public void test1() {
        List<String[]> excelDataList = getExcelList();
        writeDataToExcel(excelDataList);
    }

    private void writeDataToExcel(List<String[]> list) {
        System.out.println("开始写出数据");
        /**
         * backArr[0] = name;
         *                     backArr[1] = id;
         *                     backArr[2] = code;
         *                     backArr[3] = time;
         *                     backArr[4] = addTime;
         *                     backArr[5] = content;
         */
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet();
        //写入数据
        for (int i = 0; i < list.size(); i++) {
            HSSFRow row = sheet.createRow(i);
            HSSFCell cell;
            //id
            cell = row.createCell(0);
            cell.setCellValue(list.get(i)[1]);
            //名称
            cell = row.createCell(1);
            cell.setCellValue(list.get(i)[0]);
            //类型
            cell = row.createCell(2);
            cell.setCellValue("企业经营异常名录");
            //失信行为
            cell = row.createCell(3);
            cell.setCellValue(list.get(i)[5]);
            //代码
            cell = row.createCell(4);
            cell.setCellValue(list.get(i)[2]);
            //时间
            cell = row.createCell(5);
            cell.setCellValue(list.get(i)[4]);
            //吊销
            cell = row.createCell(6);
            cell.setCellValue("吊销");
            //吊销时间
            cell = row.createCell(7);
            cell.setCellValue(list.get(i)[3]);
        }
        //创建excel文件
        try {
            File file = new File("F:\\MyProject\\MavenDemo\\src\\test\\java\\demo\\template_jingying.xls");
            FileOutputStream outStream = new FileOutputStream(file);
            workbook.write(outStream);
            outStream.flush();
            outStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("结束写出数据");
    }

    private List<String[]> getExcelList() {
        List<String[]> backList = new ArrayList<>();
        List<String> getDataList = new ArrayList<>();
        System.out.println("开始获取名称>>>>>");
        try {
            String excelPath = "F:\\MyProject\\MavenDemo\\src\\test\\java\\demo\\sourse.xlsx";
            File excel = new File(excelPath);
            //开始解析
            Workbook workbook = new XSSFWorkbook(excel);
            //读取sheet 0
            Sheet sheet = workbook.getSheetAt(0);
            int startRow = 1;   //第一行是列名，所以不读
            int totalRow = sheet.getLastRowNum();
            for (int i = startRow; i < totalRow; i++) {
                if ((i + 1) % 1000 == 0) {
                    System.out.println("进行第" + (i + 1) + "行");
                }
                Row row = sheet.getRow(i);
                String id = row.getCell(0).toString();
                String code = row.getCell(1).toString();
                String name = row.getCell(2).toString();
                name = name.replaceAll(" ", "");
                //时间转换
                String time = row.getCell(3).toString();
                if (StringUtils.isNotBlank(time)) {
                    time = time.split(" ")[0].replaceAll("-", "/");
                }
                Class.forName("com.mysql.cj.jdbc.Driver");
                Connection connection = DriverManager.getConnection("jdbc:mysql://192.168.1.251:3306/website_yantai_develop", "develop", "develop251");
                String sql = "select count(ID) from admin_punish WHERE state = 0 and CF_XDR_MC = ? " +
                        "and (cf_gsjzq is null OR cf_gsjzq>=STR_TO_DATE(2019-05-21, '%Y-%m-%d'))";
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setString(1, name);
                //5.执行SQL语句
                ResultSet resultSet = preparedStatement.executeQuery();
                resultSet.next();
                int count = resultSet.getInt(1);
                if (count == 0) {
                    if (name.contains("(") || name.contains(")")) {
                        name = name.replace("(", "（").replace("(", "（");
                    } else if (name.contains("（") || name.contains("）")) {
                        name = name.replace("（", "(").replace("）", ")");
                    } else {
                        name = null;
                    }
                }
                if (count == 0 && name != null) {
                    preparedStatement.setString(1, name);
                    resultSet = preparedStatement.executeQuery();
                    resultSet.next();
                    count = resultSet.getInt(1);
                    if (count == 0) {
                        name = null;
                    }
                }
                if (name != null) {
                    getDataList.add(name + "," + id + "," + code + "," + time);
                    System.err.println(name + "," + id + "," + code + "," + time);
                }
                resultSet.close();
                preparedStatement.close();
                connection.close();
            }
            System.out.println("获取到有效的名称条数为:" + getDataList.size() + "条");
            for (String str : getDataList) {
                String[] strArr = str.split(",");
                String name = strArr[0];
                String id = strArr[1];
                String code = strArr[2];
                String time = strArr[3];
                Class.forName("com.mysql.cj.jdbc.Driver");
                Connection connection = DriverManager.getConnection("jdbc:mysql://192.168.1.251:3306/website_yantai_develop", "develop", "develop251");
                String sql =
                        "select  CF_SY, CF_JDRQ from admin_punish WHERE state = 0 and CF_XDR_MC = ? " +
                                "and (cf_gsjzq is null OR cf_gsjzq>=STR_TO_DATE(2019-05-21, '%Y-%m-%d'))";
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setString(1, name);
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    String content = resultSet.getString(1);
                    String addTime = resultSet.getString(2);
                    String[] backArr = new String[6];
                    backArr[0] = name;
                    backArr[1] = id;
                    backArr[2] = code;
                    backArr[3] = time;
                    backArr[4] = addTime;
                    backArr[5] = content;
                    backList.add(backArr);
                }
                resultSet.close();
                preparedStatement.close();
                connection.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("获取数据程序结束");
        return backList;
    }
}
