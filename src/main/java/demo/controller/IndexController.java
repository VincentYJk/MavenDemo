package demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * @author 梁明辉
 */
@Controller
public class IndexController {

    @RequestMapping({"/","/index"})
    public String index(HttpServletRequest request){
        String param = request.getParameter("xssSpan");
        request.setAttribute("xssSpan",param==null?"null":param);
        return "index";
    }
}
