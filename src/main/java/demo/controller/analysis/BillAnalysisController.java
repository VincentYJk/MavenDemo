package demo.controller.analysis;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @ClassName BillAnalysisController
 * @Description 账单数据分析Controller
 * @Author 梁明辉
 * @Date 2019/5/27 15:56
 * @ModifyDate 2019/5/27 15:56
 * @Version 1.0
 */
@Controller
@RequestMapping("/billAnalysis")
public class BillAnalysisController {
    @RequestMapping("/index")
    public String index() {
        return "analysis/bill/index";
    }
}
