package yygh.parent.dandp.intercepter;

import com.alibaba.fastjson.JSONObject;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartException;

/**
 * 全局异常处理类
 */
@ControllerAdvice
public class GloablExceptionHandler {

    /**
     * 文件上传限制异常413
     *
     * @param e
     * @return
     */
    @ResponseBody
    @ExceptionHandler(MultipartException.class)
    public Object handleMultipartException(MultipartException e) {
        String msg = e.getMessage();
        if (msg == null || msg.equals("")) {
            msg = "服务器出错";
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("status", 413);
        jsonObject.put("msg", "文件太大,不允许上传！" + msg);
        return jsonObject;
    }

    /**
     * token 不合法异常，返回403
     *
     * @param e
     * @return
     */
    @ResponseBody
    @ExceptionHandler(RuntimeException.class)
    public Object handleRuntimeException(RuntimeException e) {
        String msg = e.getMessage();
        if (msg == null || msg.equals("")) {
            msg = "服务器出错";
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("status", 401);
        jsonObject.put("msg", msg);
        return jsonObject;
    }

    /**
     * token 不合法异常，返回403
     *
     * @param e
     * @return
     */
    @ResponseBody
    @ExceptionHandler(TokenException.class)
    public Object handleTokenException(TokenException e) {
        String msg = e.getMessage();
        if (msg == null || msg.equals("")) {
            msg = "服务器出错";
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("status", 403);
        jsonObject.put("msg", msg);
        return jsonObject;
    }
}

