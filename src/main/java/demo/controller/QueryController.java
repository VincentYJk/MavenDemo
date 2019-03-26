package demo.controller;

import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.RangeQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 查询controller
 *
 * @author 梁明辉
 * @date 2019/3/26 17:02
 */
@RestController
@RequestMapping("/query")
public class QueryController {
    Logger logger = LoggerFactory.getLogger(QueryController.class);
    @Autowired
    private TransportClient client;

    public static final String PEOPLE_INDEX = "people";
    public static final String PEOPLE_TYPE_MAN = "man";

    @GetMapping("/get/people/man")
    @ResponseBody
    public ResponseEntity get(@RequestParam(name = "id", defaultValue = "") String id) {
        if (id.isEmpty()) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        GetResponse result = this.client.prepareGet("people", "man", id)
                .get();
        if (!result.isExists()) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity(result.getSource(), HttpStatus.OK);
    }

    // 复合查询
    @RequestMapping("/people/man")
    @ResponseBody
    public ResponseEntity query(
            @RequestParam(name = "name", required = false) String name,
            @RequestParam(name = "country", required = false) String country,
            @RequestParam(name = "gt_age", defaultValue = "0") int gtAge,
            @RequestParam(name = "lt_age", required = false) Integer ltAge) {
        BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();
        if (country != null) {
            boolQuery.must(QueryBuilders.matchQuery("country", country));
        }
        if (name != null) {
            boolQuery.must(QueryBuilders.matchQuery("title", name));
        }
        RangeQueryBuilder rangeQuery = QueryBuilders.rangeQuery("age").from(gtAge);
        if (ltAge != null && ltAge > 0) {
            rangeQuery.to(ltAge);
        }
        boolQuery.filter(rangeQuery);
        SearchRequestBuilder builder = client.prepareSearch(PEOPLE_INDEX).setTypes(PEOPLE_TYPE_MAN)
                .setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
                .setQuery(boolQuery)
                .setFrom(0)
                .setSize(10);
        logger.debug(builder.toString());
        SearchResponse response = builder.get();
        List result = new ArrayList<Map<String, Object>>();
        for (SearchHit hit : response.getHits()) {
            result.add(hit.getSourceAsMap());
        }
        return new ResponseEntity(result, HttpStatus.OK);
    }
}
