package demo.dao;

import demo.model.ApartmentModel;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Component;

/**
 * @author 梁明辉
 * @date 2019/3/29 16:57
 */
public interface ApartmentDao extends ElasticsearchRepository<ApartmentModel, String> {

    /**
     * 查询雇员信息
     *@param  name 名称
     *@return 对应的model
     */
    ApartmentModel queryApartmentValueByName(String name);
}
