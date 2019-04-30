package demo.controller.video;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @ClassName M3U8PlayController
 * @Description TODO
 * @Author 梁明辉
 * @Date 2019/4/30 14:13
 * @ModifyDate 2019/4/30 14:13
 * @Version 1.0
 */
@Controller
@RequestMapping("/m3u8")
public class M3U8PlayController {
    @RequestMapping("/index")
    public String index(){
        return "video/m3u8/index";
    }
}

