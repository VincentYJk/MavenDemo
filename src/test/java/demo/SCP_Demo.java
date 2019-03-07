package demo;

import org.junit.Test;
import com.alibaba.druid.filter.config.ConfigTools;
/**
 * @author Administrator
 * @className SCP_Demo
 * @description SCP项目自用测试
 * @Date 2019/3/7 16:12
 * @Version 1.0
 */
public class SCP_Demo {
    @Test
    public void testDruidDecode(){
        try {
            ConfigTools.main(new String[]{"supporter"});
        }catch (Exception e){
            System.out.println(e);
        }

    }
}
