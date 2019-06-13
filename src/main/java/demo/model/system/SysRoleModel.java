package demo.model.system;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.List;

/**
 * @ClassName SysRole
 * @Description 系统角色
 * @Author 梁明辉
 * @Date 2019/6/13 11:53
 * @ModifyDate 2019/6/13 11:53
 * @Version 1.0
 */
@Entity
@Table(name = "sys_role")
@Data
public class SysRoleModel {
    @Id
    @GeneratedValue
    private Integer role_id;
    /**
     * 角色标识程序中判断使用,如"admin",这个是唯一的
     */
    private String role;
    /**
     * 角色描述,UI界面显示使用
     */
    private String description;
    /**
     * 是否可用,如果不可用将不会添加给用户
     */
    private Boolean available = Boolean.FALSE;

    /**
     * 角色 -- 权限关系：多对多关系
     */
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "SysRolePerm", joinColumns = {@JoinColumn(name = "role_id")}, inverseJoinColumns = {@JoinColumn(name = "perm_id")})
    private List<SysPermModel> permissions;
    /**
     * 用户 - 角色关系定义 一个角色对应多个用户
     */
    @ManyToMany
    @JoinTable(name = "SysUserRole", joinColumns = {@JoinColumn(name = "role_id")}, inverseJoinColumns = {@JoinColumn(name = "user_id")})
    private List<SysUserModel> sysUsers;
}