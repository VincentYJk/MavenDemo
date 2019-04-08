package demo.service.impl;

import com.alibaba.fastjson.JSONObject;
import demo.dao.ElasticSearchRepository;
import demo.service.ElasticSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * ElasticSearch实现类
 *
 * @author Administrator
 * @date 2019/4/8 10:28
 */
@Service
public class ElasticSearchServiceImpl implements ElasticSearchService {
    @Autowired
    private ElasticSearchRepository eSRepository;
    @Override
    public Map<String, Object> searchDataById(String type, String id) {
        return eSRepository.searchDataById(type, id);
    }
}
