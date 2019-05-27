package demo.controller.common;

import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @ClassName BaseController
 * @Description 基本Controller
 * @Author 梁明辉
 * @Date 2019/5/27 16:26
 * @ModifyDate 2019/5/27 16:26
 * @Version 1.0
 */
public class BaseController {
    @Autowired
    protected HttpServletRequest request;
    protected HttpServletResponse response;


}
