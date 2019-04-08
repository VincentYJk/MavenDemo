package demo.service;

import com.alibaba.fastjson.JSONObject;

import java.util.Map;

/**
 * ElasticSearch实体类
 *
 * @author 梁明辉
 */
public interface ElasticSearchService {
    /**
     * 根据id查询对应type的数据
     *
     * @param type  类型<----->关系型数据表
     * @param id    数据ID<----->id
     * @return
     */
    public Map<String, Object> searchDataById(String type, String id);
}
