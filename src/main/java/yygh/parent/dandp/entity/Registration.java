package yygh.parent.dandp.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;

/**
 * 挂号表
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_registration")
public class Registration implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 唯一编号
     */
    private String number;

    /**
     * 预约时间，默认为当前时间
     */
    private Date time;

    /**
     * 挂号时间
     */
    private Date createTime;

    /**
     * 挂号医生
     */
    private Integer doctorId;

    /**
     * 挂号患者Id
     */
    private Integer patientsId;

    /**
     * 0:未处理，1已处理
     */
    private Integer status;

    /**
     * 状态，1为采纳，2为不采纳
     */
    private Integer accept;

    /**
     * 满意，一般，不满意
     */
    private String evaluate;

    /**
     * 是否需要病历本
     */
    private Boolean isCases;

    /**
     * 应付金额
     */
    private BigDecimal amountPayable;

    /**
     * 结算类型
     */
    private String type;

    /**
     * 挂号级别
     */
    private String level;

    /**
     * 是否开发票
     */
    private Boolean isInvoice;

    /**
     * 发票代码
     */
    private String invoiceCode;

    /**
     * 发票号
     */
    private String invoiceNumber;

    /**
     * 开票日期
     */
    private LocalDate invoiceDate;


    @TableField(exist = false)
    private Integer depId;
    /**
     * 患者姓名
     */
    @TableField(exist = false)
    private String patientName;
    /**
     * 挂号医生姓名
     */
    @TableField(exist = false)
    private String doctorName;
    @TableField(exist = false)
    private Integer ssex;
    @TableField(exist = false)
    private Date birthday;

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
