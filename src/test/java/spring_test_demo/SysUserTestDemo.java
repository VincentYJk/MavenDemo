package spring_test_demo;

import demo.DemoApplication;
import demo.dao.system.SysUserDao;
import demo.model.system.SysUserModel;
import demo.service.system.SysUserService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @ClassName SysUserTestDemo
 * @Description 用户测试demo
 * @Author 梁明辉
 * @Date 2019/6/13 15:30
 * @ModifyDate 2019/6/13 15:30
 * @Version 1.0
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = DemoApplication.class)
@Slf4j
public class SysUserTestDemo {
//    @Autowired
//    SysUserDao sysUserDao;
    @Autowired
    SysUserService sysUserService;

    @Test
    public void testFindByUserName() {
        log.info("开始执行程序");
        SysUserModel sysUserModel = sysUserService.findByUserName("admin");
        log.info(sysUserModel.toString());
    }
}
