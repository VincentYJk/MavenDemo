package demo.dao.system;

import demo.model.system.SysUserModel;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

/**
 * @ClassName SysUserDao
 * @Description 系统用户dao层
 * @Author 梁明辉
 * @Date 2019/6/13 15:14
 * @ModifyDate 2019/6/13 15:14
 * @Version 1.0
 */
@Component
public interface SysUserDao {
    /**
     * 根据用户名查找用户
     *
     * @param userName 用户名
     * @return
     */
    @Select("select * from sys_user where userName = #{userName}")
    SysUserModel findByUserName(@Param("userName") String userName);
    /*
    @Select("<script>select COUNT(p.ID) from MM_LIST p, USER c
    where p.USER_ID = #{userId} and p.USER_ID = c.ID <if test=“status != null and status != ‘’”>
    and p.STATUS = #{status}</if> <if test=“code!= null and code!= ‘’”>and p.CODE = #{code}</if></script>")
     */

}
