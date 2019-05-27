package demo.controller.common;

import demo.controller.common.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author 梁明辉
 */
@Controller
public class IndexController extends BaseController {

    @RequestMapping({"/", "/index"})
    public String index() {
        return "index";
    }
}
