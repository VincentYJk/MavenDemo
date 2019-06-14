package demo.service.system.impl;

import demo.dao.system.SysUserDao;
import demo.model.system.SysUserModel;
import demo.service.system.SysUserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @ClassName SysUserServiceImpl
 * @Description 接口实现类
 * @Author 梁明辉
 * @Date 2019/6/13 15:05
 * @ModifyDate 2019/6/13 15:05
 * @Version 1.0
 */
@Service
public class SysUserServiceImpl implements SysUserService {
    @Resource
    SysUserDao sysUserDao;

    @Override
    public SysUserModel findByUserName(String username) {
        return sysUserDao.findByUsername(username);
    }
}
