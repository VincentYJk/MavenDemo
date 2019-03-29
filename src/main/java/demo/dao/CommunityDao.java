package demo.dao;
import demo.model.CommunityModel;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Component;

/**
 * @author 梁明辉
 * @date 2019/3/29 16:57
 */
@Component
public interface CommunityDao extends ElasticsearchRepository<CommunityModel, String> {

    /**
     * 查询雇员信息
     *
     * @param id id
     * @return model
     */
    CommunityModel queryEmployeeById(String id);
}
