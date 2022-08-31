package yygh.parent.dandp.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;

import java.text.SimpleDateFormat;
import java.time.LocalDate;

import com.baomidou.mybatisplus.annotation.TableId;

import java.time.LocalDateTime;
import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * 系统用户
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_user")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户id
     */
    @TableId(value = "user_id", type = IdType.AUTO)
    private Integer userId;

    /**
     * 用户名
     */
    private String username;

    /**
     * 用户昵称/真实姓名
     */
    private String nickName;

    /**
     * 密码
     */
    private String password;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 联系电话
     */
    private String mobile;

    /**
     * 上次登录Ip
     */
    private String lastLoginIp;

    /**
     * 最近访问时间
     */
    private LocalDateTime lastLoginTime;

    /**
     * 性别 0男 1女 2保密
     */
    private Integer ssex;

    /**
     * 描述
     */
    private String description;

    /**
     * 用户头像
     */
    private String avatar;

    /**
     * 医生毕业院校
     */
    private String school;

    /**
     * 医生科室Id
     */
    private Integer depId;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 修改时间
     */
    private Date modifyTime;

    /**
     * 状态 0锁定 1有效,默认为1
     */
    private Integer status;

    /**
     * 生日
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date birthday;

    /**
     * 角色，0超级管理员，1为医生，2为患者
     */
    private Integer roleId;

    @TableField(exist = false)
    private String depName;

    @TableField(exist = false)
    private Integer age;


    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
        this.setAge(getAgeByBir(birthday));
    }


    private int getAgeByBir(Date birthday) {
        int year = birthday.getYear();
        int now = new Date().getYear();
        int i = now - year;
        return i;
    }

}
