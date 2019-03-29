package api;

import com.alibaba.fastjson.JSON;
import demo.util.PostUtil;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Administrator
 * @date 2019/3/28 15:03
 */
public class ValidateEmail {
    private static final String url = "http://email.qhyt1688.com/Home/EValidation";
    @Test
    public void testSend(){
        String param = "emails=1111111@qq.com";
        String str = new PostUtil().send(url, param);
        Map<String, List<Map<String,Object>>> map= JSON.parseObject(str,HashMap.class);

        /*
        {"r":[{"remark":"emailSate 1:this email exist,0:this email is no exist,2:no Support this email","emails":"1111111@163.com","emailSate":1}]}
         */
        List<Map<String,Object>> resultList = map.get("r");
        if(resultList.get(0)!=null){
            int emailSate = (int)resultList.get(0).get("emailSate");
            if(emailSate!=1){
                System.out.println(">>>>>");
            }else{
                System.out.println(resultList.get(0).get("emailSate"));
            }

        }else{
            System.out.println("邮箱格式错误");
        }
    }
}
