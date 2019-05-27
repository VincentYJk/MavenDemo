package demo.service.analysis;

import demo.model.analysis.BillModel;

/**
 * @ClassName BillService
 * @Description BillService接口
 * @Author 梁明辉
 * @Date 2019/5/27 9:12
 * @ModifyDate 2019/5/27 9:12
 * @Version 1.0
 */
public interface BillService {
    /**
     * @Description 保存billModel
     * @Author 梁明辉
     * @Date 09:13 2019-05-27
     * @ModifyDate 09:13 2019-05-27
     * @Params [billModel]
     * @Return void
     */
    void saveBill(BillModel billModel);

    BillModel queryByBankName(String bankName);

    long updateBill(BillModel billModel);

    void deleteBillById(String id);
}
