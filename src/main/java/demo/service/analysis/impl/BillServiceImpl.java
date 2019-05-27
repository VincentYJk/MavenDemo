package demo.service.analysis.impl;

import com.mongodb.client.result.UpdateResult;
import demo.model.analysis.BillModel;
import demo.service.analysis.BillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

/**
 * @ClassName BillServiceImpl
 * @Description 实现账单接口的service
 * @Author 梁明辉
 * @Date 2019/5/27 9:15
 * @ModifyDate 2019/5/27 9:15
 * @Version 1.0
 */
@Service
public class BillServiceImpl implements BillService {
    @Autowired
    MongoTemplate mongoTemplate;

    /**
     * @Description 保存账单的方法
     * @Author 梁明辉
     * @Date 10:33 2019-05-27
     * @ModifyDate 10:33 2019-05-27
     * @Params [billModel]
     * @Return void
     */
    @Override
    public void saveBill(BillModel billModel) {
        mongoTemplate.save(billModel);
    }

    /**
     * @Description 根据银行名称查询的方法
     * @Author 梁明辉
     * @Date 10:33 2019-05-27
     * @ModifyDate 10:33 2019-05-27
     * @Params
     * @Return
     */
    @Override
    public BillModel queryByBankName(String bankName) {
        Query query = new Query(Criteria.where("bankName").is(bankName));
        BillModel billModel = mongoTemplate.findOne(query, BillModel.class);
        return billModel;
    }

    /**
     * @Description 更新对象
     * @Author 梁明辉
     * @Date 10:40 2019-05-27
     * @ModifyDate 10:40 2019-05-27
     * @Params
     * @Return
     */
    @Override
    public long updateBill(BillModel billModel) {
        Query query = new Query(Criteria.where("id").is(billModel.getId()));
        Update update =
                new Update().set("bankName", billModel.getBankName()).set("oweAmount", billModel.getOweAmount());
        UpdateResult result = mongoTemplate.updateFirst(query, update, BillModel.class);
        if (result != null) {
            return result.getMatchedCount();
        } else {
            return 0;
        }
    }
    @Override
    public void deleteBillById(String id) {
        Query query = new Query(Criteria.where("id").is(id));
        mongoTemplate.remove(query, BillModel.class);
    }
}
