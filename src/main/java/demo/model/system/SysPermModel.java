package demo.model.system;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.List;

/**
 * @ClassName SysPerm
 * @Description 系统权限
 * @Author 梁明辉
 * @Date 2019/6/13 11:59
 * @ModifyDate 2019/6/13 11:59
 * @Version 1.0
 */
@Entity
@Table(name = "sys_perm")
@Data
public class SysPermModel {
    @Id
    @GeneratedValue
    private Integer id;
    private String name;
    /**
     * 资源类型，[menu|button]
     */
    @Column(columnDefinition = "enum('menu','button')")
    private String resourceType;
    /**
     * 资源路径
     */
    private String url;
    /**
     * 权限字符串,menu例子：
     * role:*，button例子：role:create,role:update,role:delete,role:view
     */
    private String permission;
    /**
     * 父编号
     */
    private Long parentId;
    /**
     * 父编号列表
     */
    private String parentIds;
    private Boolean available = Boolean.FALSE;
    @ManyToMany
    @JoinTable(name = "SysRolePerm", joinColumns = {@JoinColumn(name = "perm_id")}, inverseJoinColumns = {@JoinColumn(name = "role_id")})
    private List<SysRoleModel> roles;

}