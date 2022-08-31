package yygh.parent.dandp.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;
/**
 * 病例表
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_case")
public class Case implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 患者id
     */
    private Integer patientsId;

    /**
     * 医生id
     */
    private Integer doctorId;

    /**
     * 病例详情
     */
    private String remake;

    /**
     * 处方信息
     */
    private String recipe;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 修改时间
     */
    private Date modifyTime;

    /**
     * 患者姓名
     */
    @TableField(exist = false)
    private String patientName;

    /**
     * 医生姓名
     */
    @TableField(exist = false)
    private String doctorName;
    @TableField(exist = false)
    private Integer ssex;
    @TableField(exist = false)
    private Date birthday;
    @TableField(exist = false)
    private String depName;
}
