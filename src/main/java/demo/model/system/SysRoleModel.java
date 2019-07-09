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
import java.io.Serializable;
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
public class SysRoleModel implements Serializable {
    private static final long serialVersionUID = -173371192476922470L;
    /**
     * 编号
     */
    @Id
    @GeneratedValue
    private Integer id;
    /**
     * 角色标识程序中判断使用,如"admin",这个是唯一的:
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
     * 角色 -- 权限关系：多对多关系;
     */
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "SysRolePerm", joinColumns = {@JoinColumn(name = "roleId")}, inverseJoinColumns = {@JoinColumn(name = "permId")})
    private List<SysPermModel> permissions;

    /**
     * 用户 - 角色关系定义;
     * 一个角色对应多个用户
     */
    @ManyToMany
    @JoinTable(name = "SysUserRole", joinColumns = {@JoinColumn(name = "roleId")}, inverseJoinColumns = {@JoinColumn(name = "uid")})
    private List<SysUserModel> userInfos;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getAvailable() {
        return available;
    }

    public void setAvailable(Boolean available) {
        this.available = available;
    }

    public List<SysPermModel> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<SysPermModel> permissions) {
        this.permissions = permissions;
    }

    public List<SysUserModel> getUserInfos() {
        return userInfos;
    }

    public void setUserInfos(List<SysUserModel> userInfos) {
        this.userInfos = userInfos;
    }
}