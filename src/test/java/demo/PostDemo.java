package demo;

import demo.util.PostUtil;
import org.junit.Test;

/**
 * @ClassName PostDemo
 * @Description TODO
 * @Author 梁明辉
 * @Date 2019/5/5 11:16
 * @ModifyDate 2019/5/5 11:16
 * @Version 1.0
 */
public class PostDemo {
    @Test
    public void test1() {
        PostUtil postUtil = new PostUtil();
        String str = postUtil.send("http://192.168.1.190:8083/app/search/getCompanyById", "");
        System.out.println(str);
    }
}
