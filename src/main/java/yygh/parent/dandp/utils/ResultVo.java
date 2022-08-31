package yygh.parent.dandp.utils;

import java.io.Serializable;

public class ResultVo implements Serializable {

    private static final long serialVersionUID = 1L;

    // 响应业务状态
    /*
     * 200	成功
     * 201	错误
     * 400	参数错误
     */
    private Integer status;

    // 响应消息
    private String msg;

    // 响应中的数据
    private Object data;


    public static ResultVo build(Integer status, String msg, Object data) {
        return new ResultVo(status, msg, data);
    }

    public static ResultVo failed(Integer status, String msg) {
        return new ResultVo(status, msg);
    }

    public static ResultVo oK(Object data) {
        return new ResultVo(data);
    }

    public static ResultVo oK() {
        return new ResultVo(null);
    }

    public ResultVo() {

    }

    public ResultVo(Integer status, String msg) {
        this.status = status;
        this.msg = msg;
    }


    public ResultVo(Integer status, String msg, Object data) {
        this.status = status;
        this.msg = msg;
        this.data = data;
    }


    public ResultVo(Object data) {
        this.status = 200;
        this.msg = "OK";
        this.data = data;
    }


    public Boolean isOk() {
        return this.status == 200;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}