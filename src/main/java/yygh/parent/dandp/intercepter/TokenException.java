package yygh.parent.dandp.intercepter;

/**
 * 自定义token异常类
 */
public class TokenException extends RuntimeException {
    private Integer code;

    public TokenException(Integer code, String message) {
        super(message);
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

}

