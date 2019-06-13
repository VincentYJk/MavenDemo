package demo.model.system;

import lombok.Data;

import javax.persistence.Column;
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
 * @ClassName SysUser
 * @Description 系统用户
 * @Author 梁明辉
 * @Date 2019/6/13 11:41
 * @ModifyDate 2019/6/13 11:41
 * @Version 1.0
 */
@Entity
@Table(name = "sys_user")
@Data
public class SysUserModel implements Serializable {
    private static final long serialVersionUID = -3315677812593011554L;
    @Id
    @GeneratedValue
    private Integer id;
    @Column(unique = true)
    private String userName;
    private String name;
    private String password;
    /**
     * 密码的盐值
     */
    private String salt;
    /**
     * 状态值
     */
    private byte state;
    /**
     * 立即从数据库加载数据
     */
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "sys_user_role", joinColumns = {@JoinColumn(name = "user_id")}, inverseJoinColumns = {@JoinColumn(name = "role_id")})
    private List<SysRoleModel> roleList;
    /**
     * 密码盐.
     * @return
     */
    public String getCredentialsSalt(){
        return this.userName+this.salt;
    }
}
