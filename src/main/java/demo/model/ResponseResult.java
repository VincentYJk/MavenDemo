package demo.model;

import java.io.Serializable;

/**
 * 定义统一的响应信息
 *
 * @author Administrator
 * @date 2019/4/8 10:44
 */
public class ResponseResult<T> implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 状态码
     */
    private String code;

    /**
     * 状态码对应的信息
     */
    private String message;

    /**
     * 数据对象
     */
    private T data;

    public ResponseResult(String code, String message, T data) {
        super();
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
