package spring_test_demo;

import demo.DemoApplication;
import demo.model.analysis.BillModel;
import demo.service.analysis.impl.BillServiceImpl;
import demo.util.IdUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @ClassName BillTestDemo
 * @Description 测试单元
 * @Author 梁明辉
 * @Date 2019/5/27 11:04
 * @ModifyDate 2019/5/27 11:04
 * @Version 1.0
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = DemoApplication.class)
@Slf4j
public class BillTestDemo {
    @Autowired
    private BillServiceImpl billService;

    @Test
    public void testSave() {
        BillModel billModel = new BillModel();
        billModel.setId(IdUtil.getUUID());
        billModel.setBankName("测试银行2");
        billModel.setOweAmount("2100");
        billModel.setMouth("06");
        billModel.setYear("2019");
        billModel.setAddTime("2019-06-27");
        billService.saveBill(billModel);
    }

    @Test
    public void testQueryByBankName() {
        BillModel billModel = billService.queryByBankName("测试银行");
        log.info(billModel.toString());
    }

    @Test
    public void testUpdate() {
        BillModel billModel = new BillModel();
        billModel.setId("9da66c738e69428e9b1b35994cd4ae95");
        billModel.setBankName("测试银行");
        billModel.setOweAmount("210");
        billModel.setMouth("05");
        billModel.setYear("2019");
        billModel.setAddTime("2019-05-27");
        long num = billService.updateBill(billModel);
        log.info("num>>>" + num);
    }

    @Test
    public void testDel() {
        billService.deleteBillById("9da66c738e69428e9b1b35994cd4ae95");
    }
}
