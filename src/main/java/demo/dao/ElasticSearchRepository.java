package demo.dao;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexResponse;
import org.elasticsearch.action.admin.indices.exists.indices.IndicesExistsRequest;
import org.elasticsearch.action.admin.indices.exists.indices.IndicesExistsResponse;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequestBuilder;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.UUID;

/**
 * 实体类dao
 *
 * @author Administrator
 * @date 2019/8 10:16
 */
@Component
@Slf4j
public class ElasticSearchRepository {
    @Autowired
    private TransportClient client;
    @Value("${elasticsearch.index}")
    private String index;
    /**
     * 查询数据
     *
     * @param type  类型<----->关系型数据表
     * @param id    数据ID<----->id
     * @return Map<String, Object> 返回map数据
     */
    public Map<String, Object> searchDataById( String type, String id) {
        if (index == null || type == null || id == null) {
            log.info(" 无法查询数据，缺唯一值!!!!!!! ");
            return null;
        }
        //来获取查询数据信息
        GetRequestBuilder getRequestBuilder = client.prepareGet(index, type, id);
        GetResponse getResponse = getRequestBuilder.execute().actionGet();
        //这里也有指定的时间获取返回值的信息，如有特殊需求可以

        return getResponse.getSource();
    }
}
