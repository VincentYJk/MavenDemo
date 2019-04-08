package demo.controller;

import demo.enums.ElasticSearchStatusEnum;
import demo.model.ResponseResult;

/**
 * @author Administrator
 * @date 2019/4/8 10:38
 */
public class BaseController {
    /**
     * 生成统一的返回响应对象
     *
     * @param webStatusEnum 状态码枚举
     * @param data          数据对象
     * @param <T>           数据对象类型参数
     * @return
     */
    public <T> ResponseResult generateResponseVo(ElasticSearchStatusEnum webStatusEnum, T data) {
        return new ResponseResult(webStatusEnum.getCode(), webStatusEnum.getDesc(), data);
    }
}
