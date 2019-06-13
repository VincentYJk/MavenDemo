package demo.config;

import demo.model.system.SysPermModel;
import demo.model.system.SysRoleModel;
import demo.model.system.SysUserModel;
import demo.service.system.SysUserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @ClassName MyShiroRealm
 * @Description 自定义shiro验证
 * @Author 梁明辉
 * @Date 2019/6/13 14:48
 * @ModifyDate 2019/6/13 14:48
 * @Version 1.0
 */
@Slf4j
public class MyShiroRealm extends AuthorizingRealm {
    @Autowired
    SysUserService sysUserService;

    /**
     * 从token获取用户并验证
     *
     * @param token
     * @return
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token)
            throws AuthenticationException {
        log.info("用户验证-->MyShiroRealm.deGetAuthenticationInfo()");
        //从token获取输入的账号
        String userName = (String) token.getPrincipal();
        SysUserModel sysUserModel = sysUserService.findByUserName(userName);
        log.info("用户信息>>>" + sysUserModel.toString());
        if (sysUserModel == null) {
            return null;
        }
        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(
                sysUserModel,
                sysUserModel.getPassword(),
                ByteSource.Util.bytes(sysUserModel.getCredentialsSalt()),
                getName()
        );
        return authenticationInfo;
    }

    /**
     * 权限配置
     *
     * @param principals
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        System.out.println("权限配置-->MyShiroRealm.doGetAuthorizationInfo()");
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        SysUserModel sysUserModel = (SysUserModel) principals.getPrimaryPrincipal();
        for (SysRoleModel role : sysUserModel.getRoleList()) {
            authorizationInfo.addRole(role.getRole());
            for (SysPermModel p : role.getPermissions()) {
                authorizationInfo.addStringPermission(p.getPermission());
            }
        }
        return authorizationInfo;
    }
}
