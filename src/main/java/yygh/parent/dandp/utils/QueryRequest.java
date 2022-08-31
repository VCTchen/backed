package yygh.parent.dandp.utils;

import lombok.Data;

import java.io.Serializable;

@Data
public class QueryRequest implements Serializable {

    private static final long serialVersionUID = -4869594085374385813L;

    private Integer pageSize = 10;
    private Integer pageNum = 1;

    private String beginTime;
    private String endTime;

    private String oldPassword;
    private String newPassword;
}
