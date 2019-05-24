package shiro.chapter2;

import com.gexin.rp.sdk.base.uitls.StringUtils;
import org.apache.shiro.authz.UnauthorizedException;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;

/**
 * @ClassName Chapter2
 * @Description 授权
 * @Author 梁明辉
 * @Date 2019/5/23 17:08
 * @ModifyDate 2019/5/23 17:08
 * @Version 1.0
 */
public class Chapter2 extends BaseTest {
    public void testHasRole() {
        login("classpath:config/chapter2/shiro-role.ini", "liang", "123");
        //是否包含某个角色
        Assert.assertTrue(subject().hasRole("admin"));
        //是否包含某类角色
        Assert.assertTrue(subject().hasAllRoles(Arrays.asList("role1", "role2")));
        boolean result[] = subject().hasRoles(Arrays.asList("role0", "role1", "role2"));
        Assert.assertTrue(result[0]);
        Assert.assertTrue(result[1]);
        Assert.assertTrue(result[2]);
    }
    /**
    *@Description https://jinnianshilongnian.iteye.com/blog/2020017
    *@Author 梁明辉
    *@Date 16:36 2019-05-24
    *@ModifyDate 16:36 2019-05-24
    *@Params []
    *@Return void
    */
    @Test
    public void testCheckRole() {
        login("classpath:config/chapter2/shiro-role.ini", "liang", "123");
        subject().checkRole("role1");
        subject().checkRoles("admin", "role1");
    }

}
