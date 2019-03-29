package demo.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.Date;

import static org.springframework.data.elasticsearch.annotations.DateFormat.basic_date_time;
import static org.springframework.data.elasticsearch.annotations.DateFormat.year_month_day;

/**
 * @author 梁明辉
 * @date 2019/3/29 16:16
 */
@Document(indexName = "rent",
            type = "community",
            shards = 3,
            replicas = 1,
            refreshInterval = "5")
@Data
@EqualsAndHashCode(callSuper = false)
public class CommunityModel {
    /**
     * 存储数据时候，不会分词建立索引
     * Text 存储数据时候，会自动分词，并生成索引
     */
    @Field(type = FieldType.Text)
    private String communityname;
    @Field(type = FieldType.Keyword)
    private String city;
    @Field(type = FieldType.Integer)
    private int age;
    @Field(type = FieldType.Date,format = DateFormat.basic_date_time)
    private Date creationdate;
}
