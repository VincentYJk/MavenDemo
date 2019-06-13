package demo.service.system;

import demo.model.system.SysUserModel;

/**
 * @ClassName SysUserService
 * @Description 用户service的接口
 * @Author 梁明辉
 * @Date 2019/6/13 15:04
 * @ModifyDate 2019/6/13 15:04
 * @Version 1.0
 */
public interface SysUserService {
    /**
     * 根据用户名查找用户
     *
     * @param userName
     * @return
     */
    SysUserModel findByUserName(String userName);
}
