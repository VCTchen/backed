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
import java.util.List;

/**
 * 问答表
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_questions")
public class Questions implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 问题/回复
     */
    private String title;

    /**
     * 问题id
     */
    private Integer parentId;

    /**
     * 医生Id
     */
    private Integer doctorId;

    /**
     * 问题所属患者id
     */
    private Integer patientId;

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
    @TableField(exist = false)
    private Integer ssex;
    @TableField
    private Date birthday;
    @TableField(exist = false)
    private String reply;

    @TableField(exist = false)
    private List<Questions> questions;
    @TableField(exist = false)
    private Date replyTime;
    @TableField(exist = false)
    private String replyName;

}
