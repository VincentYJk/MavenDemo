package demo;

import org.apache.shiro.crypto.hash.SimpleHash;
import org.junit.Test;

/**
 * @ClassName TestUserPass
 * @Description 测试用户密码
 * @Author 梁明辉
 * @Date 2019/6/14 10:16
 * @ModifyDate 2019/6/14 10:16
 * @Version 1.0
 */
public class TestUserPass {
    String salt = "8d78869f470951332959580424d4bf4f";
    String password = "d3c59d25033dbf980d29554025c23a75";
    String userName = "admin";

    @Test
    public void test1() {
        String hashAlgorithmName = "MD5";
        Object crdentials = "123456";
        Object salt = "test8d78869f470951332959580424d4bf4f";
        int hashIterations = 2;
        Object result = new SimpleHash(hashAlgorithmName, crdentials, salt, hashIterations);
        System.out.println(result);
    }
}
