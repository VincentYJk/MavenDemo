package demo.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @ClassName ShiroConfig
 * @Description shiro权限配置
 * @Author 梁明辉
 * @Date 2019/6/13 14:38
 * @ModifyDate 2019/6/13 14:38
 * @Version 1.0
 */
@Configuration
@Slf4j
public class ShiroConfig {
    @Bean
    public ShiroFilterFactoryBean shiroFilter(SecurityManager securityManager) {
        log.info("进入权限拦截");
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        //拦截器
        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<>();
        //无需拦截的路径
        filterChainDefinitionMap.put("/static/**", "anon");
        //配置退出,具体实现shiro内置实现
        filterChainDefinitionMap.put("/logout", "logout");
        //设置其他路劲需要认证访问
        filterChainDefinitionMap.put("/**", "authc");
        //设置登录页面
        shiroFilterFactoryBean.setLoginUrl("/login");
        //登录成功跳转路径
        shiroFilterFactoryBean.setSuccessUrl("/index");
        //未授权页面
        shiroFilterFactoryBean.setUnauthorizedUrl("/403");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        return shiroFilterFactoryBean;
    }

    /**
     * 凭证匹配器
     * （由于我们的密码校验交给Shiro的SimpleAuthenticationInfo进行处理了
     * ）
     *
     * @return
     */
    @Bean
    public HashedCredentialsMatcher hashedCredentialsMatcher() {
        HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();
        //散列算法:这里使用MD5算法;
        hashedCredentialsMatcher.setHashAlgorithmName("md5");
        //散列的次数，比如散列两次，相当于 md5(md5(""));
        hashedCredentialsMatcher.setHashIterations(2);
        return hashedCredentialsMatcher;
    }

    @Bean
    public MyShiroRealm myShiroRealm() {
        MyShiroRealm myShiroRealm = new MyShiroRealm();
        myShiroRealm.setCredentialsMatcher(hashedCredentialsMatcher());
        return myShiroRealm;

    }

    /**
     * sessionManager设置超时时间，默认30min
     *
     * @return
     */
    @Bean
    public SessionManager sessionManager() {
        DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
        //设置session超时时间单位是毫秒
        sessionManager.setGlobalSessionTimeout(1800000);
        return sessionManager;
    }

    @Bean
    public SecurityManager securityManager() {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(myShiroRealm());
        securityManager.setSessionManager(sessionManager());
        return securityManager;
    }
}
