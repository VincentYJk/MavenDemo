package demo;

import org.junit.Test;
import com.alibaba.druid.filter.config.ConfigTools;
/**
 * SCP项目自用测试
 * @author Administrator
 * @date  2019/3/7 16:12
 * @version  1.0
 */
public class SCP_Demo {
    @Test
    public void testDruidDecode(){
        try {
            ConfigTools.main(new String[]{"supporter"});
        }catch (Exception e){
            System.out.println(e.getMessage());
        }

    }
}
