package demo.controller.common;

import demo.controller.common.BaseController;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @author 梁明辉
 */
@Controller
@RequestMapping("/")
@Slf4j
public class IndexController extends BaseController {

    @RequestMapping({"/", "/index"})
    public String index() {
        return "index";
    }

    @RequestMapping("/login")
    public String login(HttpServletRequest request,
                        Map<String, Object> map) throws Exception {
        log.info("开始登录");
        String exception = (String) request.getAttribute("shiroLoginFailure");
        log.info("exception=" + exception);
        String msg = "";
        if (exception != null) {
            if (UnknownAccountException.class.getName().equals(exception)) {
                log.info("UnknownAccountException -- >账号不存在");
                msg = "UnknownAccountException -- > 账号不存在：";
            } else if (IncorrectCredentialsException.class.getName().equals(exception)) {
                log.info("IncorrectCredentialsException -- 密码不正确");
                msg = "IncorrectCredentialsException -- > 密码不正确：";
            } else if ("kaptchaValidateFailed".equals(exception)) {
                log.info("kaptchaValidateFailed -- > 验证码错误");
                msg = "kaptchaValidateFailed -- > 验证码错误";
            } else {
                log.info("else -- >" + exception);
                msg = "else >> " + exception;
            }
        }
        map.put("msg", msg);
        return "/login";

    }

    @RequestMapping("/403")
    public String unauthorizedRole() {
        log.info("------没有权限-------");
        return "403";
    }
}
