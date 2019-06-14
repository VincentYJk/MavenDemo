package demo.dao.system;

import demo.model.system.SysUserModel;
import org.springframework.data.repository.CrudRepository;

/**
 * @ClassName SysUserDao
 * @Description 系统用户dao层
 * @Author 梁明辉
 * @Date 2019/6/13 15:14
 * @ModifyDate 2019/6/13 15:14
 * @Version 1.0
 */
public interface SysUserDao extends CrudRepository<SysUserModel, Long> {
    /**
     * 通过username查找用户信息;
     *
     * @param username 用户名
     * @return 用户信息
     */
    SysUserModel findByUsername(String username);
}
