package demo.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

/**
 * @author Administrator
 * @date 2019/4/8 17:07
 */
@Data
@ToString
@NoArgsConstructor
public class ElasticSearchModel {
    private String id;
    private String name;
    private int age;
    private Date date;
}

