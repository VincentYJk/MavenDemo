package shiro.chapter2;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;


/**
 * @ClassName BaseTest
 * @Description 基本test类
 * @Author 梁明辉
 * @Date 2019/5/23 17:12
 * @ModifyDate 2019/5/23 17:12
 * @Version 1.0
 */
public class BaseTest {
    /**
     * @Description 登录逻辑
     * @Author 梁明辉
     * @Date 17:12 2019-05-23
     * @ModifyDate 17:12 2019-05-23
     * @Params []
     * @Return void
     */
    public void login(String configFile, String username, String password) {
        Factory<org.apache.shiro.mgt.SecurityManager> factory
                = new IniSecurityManagerFactory(configFile);
        org.apache.shiro.mgt.SecurityManager securityManager = factory.getInstance();
        SecurityUtils.setSecurityManager(securityManager);
        //获取token
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        subject.login(token);
    }

    public Subject subject() {
        return SecurityUtils.getSubject();
    }
}
