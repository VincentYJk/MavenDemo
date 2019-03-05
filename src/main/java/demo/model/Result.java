package demo.model;

import lombok.Data;

import java.io.Serializable;
import java.util.Map;

/**
 * 定义统一返回的result
 * @author 梁明辉
 */
@Data
public class Result implements Serializable {
    private static final long serialVersionUID = 3069351481754843898L;
    private int code;
    private boolean flag;
    private String message;
    private Map<Object,Object> map;
    private Object data;
    public Result(){

    }
    public Result(int code, boolean flag, String message, Map<Object,Object> map, Object data){
        this.code=code;
        this.flag=flag;
        this.message=message;
        this.map=map;
        this.data=data;
    }
    public Result(boolean flag, String message){
        this.flag=flag;
        this.message=message;
    }
}
