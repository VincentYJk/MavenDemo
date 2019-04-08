package demo.controller;

import demo.enums.ElasticSearchStatusEnum;
import demo.model.ResponseResult;
import demo.service.ElasticSearchService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @author Administrator
 * @date 2019/4/8 10:32
 */
@RestController
@Slf4j
@RequestMapping("/es")
public class ElasticSearchController extends BaseController {
    @Autowired
    private ElasticSearchService esSearchService;

    /**
     * 查询数据
     *
     * @param type
     * @param id
     * @return
     */
    @RequestMapping(value = "/data")
    @ResponseBody
    public ResponseResult<?> search(
            @RequestParam(value = "type", required = false) String type,
            @RequestParam(value = "id", required = false) String id
    ) {
        //搜索具体的数据来源
        Map<String, Object> returnMap = esSearchService.searchDataById("community", "AWniLQb2_IIDsoxIdz1X");
        return generateResponseVo(ElasticSearchStatusEnum.SUCCESS, returnMap);
    }
}
