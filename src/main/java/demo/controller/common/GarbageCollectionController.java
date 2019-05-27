package demo.controller.common;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

/**
 * gc回收
 *
 * @author Administrator
 * @date 2019/4/8 13:48
 */
@RestController
@RequestMapping("/gc")
public class GarbageCollectionController {
    @RequestMapping("/index")
    @ResponseBody
    public JSONObject index() {
        Runtime runtime = Runtime.getRuntime();
        runtime.gc();
        DecimalFormat mbFormat = new DecimalFormat("#0.00");
        double freeMemory = (double) runtime.freeMemory() / (1024 * 1024);
        double totalMemory = (double) runtime.totalMemory() / (1024 * 1024);
        double usedMemory = totalMemory - freeMemory;
        Map<String, String> map = new HashMap<>();
        map.put("已用内存",mbFormat.format(usedMemory)+"MB");
        map.put("内存总量", mbFormat.format(totalMemory)+"MB");
        return JSON.parseObject(JSON.toJSONString(map));
    }
}
