package demo.model.analysis;

import lombok.Data;

import java.io.Serializable;

/**
 * @ClassName BillModel
 * @Description 数据分析，账单实体类
 * @Author 梁明辉
 * @Date 2019/5/27 9:02
 * @ModifyDate 2019/5/27 9:02
 * @Version 1.0
 */
@Data
public class BillModel implements Serializable {
    private static final long serialVersionUID = -5854749942309613731L;
    private String id;
    /**
     * 银行名称
     */
    private String bankName;
    /**
     * 欠钱额度
     */
    private String oweAmount;
    /**
     * 年份
     */
    private String mouth;
    /**
     * 月份
     */
    private String year;
    /**
     * 录入时间
     */
    private String addTime;

}
