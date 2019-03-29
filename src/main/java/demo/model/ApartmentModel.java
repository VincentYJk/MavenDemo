package demo.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

/**
 * house的model
 * @author 梁明辉
 * @date 2019/3/29 17:05
 */
@Document(indexName = "house",
        shards = 5,
        replicas = 1,
        type = "apartment",
        refreshInterval = "5")
@Data
@EqualsAndHashCode(callSuper = false)
public class ApartmentModel {

    @Field(type = FieldType.Text)
    private String apartmenrname;
    @Field(type = FieldType.Text)
    private String apartmenntvalue;

}
