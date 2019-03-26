//package demo;
//
//import com.alibaba.fastjson.JSON;
//import com.gexin.rp.sdk.base.IPushResult;
//import com.gexin.rp.sdk.base.impl.SingleMessage;
//import com.gexin.rp.sdk.base.impl.Target;
//import com.gexin.rp.sdk.base.payload.APNPayload;
//import com.gexin.rp.sdk.exceptions.RequestException;
//import com.gexin.rp.sdk.http.IGtPush;
//import com.gexin.rp.sdk.template.TransmissionTemplate;
//import demo.model.Result;
//import org.apache.commons.lang3.StringUtils;
//import org.junit.Test;
//
//import java.util.HashMap;
//import java.util.Map;
//
///**
// * @author Administrator
// * @className GeTuiDemo
// * @description 个推案例
// * @Date 2019/3/5 14:01
// * @Version 1.0
// */
//public class GeTuiDemo {
//    private static String appId ="";
//    private static String appKey ="";
//    private static String masterSecret ="";
//    private static String host = "http://sdk.open.api.igexin.com/apiex.htm";
//    @Test
//    public void testAll(){
//        String clientId="";
//        Result result = pushToSingle(clientId,
//                "",
//                "");
//        if(!result.isFlag()){
//            System.out.println(result.getMessage());
//        }
//        System.out.println(result.isFlag());
//
//    }
//   public  Result pushToSingle(String clientId, String title, String text) {
//        Result result = new Result();
//        result.setFlag(false);
//        if (StringUtils.isBlank(appId) ||StringUtils.isBlank(appKey)||StringUtils.isBlank(masterSecret)|| StringUtils.isBlank(host)) {
//            result.setMessage("配置参数错误");
//            return result;
//        }
//        if (StringUtils.isBlank(clientId)) {
//            result.setMessage("clientId不得为空");
//            return result;
//        }
//        IGtPush push = new IGtPush(host, appKey, masterSecret);
//        SingleMessage message = new SingleMessage();
//        message.setOffline(true);
//        // 离线有效时间，单位为毫秒，可选
//        message.setOfflineExpireTime(24 * 3600 * 1000);
//        String[] contentArr= text.split("url=");
//        if (contentArr.length!=2) {
//            result.setMessage("content内容错误必须包含app链接");
//            return result;
//        }
//        TransmissionTemplate template = transmissionTemplate(title,contentArr);
//        message.setData(template);
//        // 可选，1为wifi，0为不限制网络环境。根据手机处于的网络情况，决定是否下发
//        message.setPushNetWorkType(0);
//        Target target = new Target();
//        target.setAppId(appId);
//        target.setClientId(clientId);
//        IPushResult ret = null;
//        try {
//            ret = push.pushMessageToSingle(message, target);
//        } catch (RequestException e) {
//            e.printStackTrace();
//            ret = push.pushMessageToSingle(message, target, e.getRequestId());
//        }
//        if (ret != null) {
//            String responseResult = ret.getResponse().get("result").toString();
//            if ("ok".equals(responseResult)) {
//                System.out.println(ret.getResponse());
//                result.setFlag(true);
//            } else {
//                result.setFlag(false);
//                if ("TokenMD5NoUsers".equals(responseResult)) {
//                    result.setMessage("clientId：" + clientId + ",未通过Token验证");
//                } else {
//                    result.setMessage("发生未知异常,响应信息如下：" + ret.getResponse().get("result").toString());
//                }
//            }
//        } else {
//            result.setFlag(false);
//            result.setMessage("服务器响应异常");
//        }
//
//        return result;
//    }
//
//    public  TransmissionTemplate transmissionTemplate(String title,String[] contentArr) {
//        TransmissionTemplate template = new TransmissionTemplate();
//        template.setAppId(appId);
//        template.setAppkey(appKey);
//        // 透传消息设置，1为强制启动应用，客户端接收到消息后就会立即启动应用；2为等待应用启动
//        template.setTransmissionType(1);
//        Map<String,Object> map=new HashMap();
//        map.put("title",title);
//        map.put("content",contentArr[0]);
//        map.put("payload",contentArr[1]);
//        JSON.toJSONString(map);
//        template.setTransmissionContent(JSON.toJSONString(map));
//        template.setAPNInfo(getAPNPayload(title,contentArr));
//        return template;
//    }
//    public APNPayload getAPNPayload(String title,String[] contentArr) {
//        APNPayload payload = new APNPayload();
//        //在已有数字基础上加1显示，设置为-1时，在已有数字上减1显示，设置为数字时，显示指定数字
//        payload.setAutoBadge("+1");
//        payload.setContentAvailable(1);
//        //ios 12.0 以上可以使用 Dictionary 类型的 sound
//        payload.setSound("default");
//        payload.setCategory("$由客户端定义");
//        payload.addCustomMsg("payload", contentArr[1]);
//
//        //简单模式APNPayload.SimpleMsg
//        payload.setAlertMsg(new APNPayload.SimpleAlertMsg("hello"));
//        //字典模式使用APNPayload.DictionaryAlertMsg
//        payload.setAlertMsg(getDictionaryAlertMsg(title,contentArr));
//        return  payload;
//    }
//    private static APNPayload.DictionaryAlertMsg getDictionaryAlertMsg(String title,String[] contentArr){
//        APNPayload.DictionaryAlertMsg alertMsg = new APNPayload.DictionaryAlertMsg();
//        //内容
//        alertMsg.setBody(contentArr[0]);
//        alertMsg.setActionLocKey("ActionLockey");
//        alertMsg.setLocKey("LocKey");
//        alertMsg.addLocArg("loc-args");
//        alertMsg.setLaunchImage("launch-image");
//        // iOS8.2以上版本支持
//        //标题
//        alertMsg.setTitle(title);
//        alertMsg.setTitleLocKey("TitleLocKey");
//        alertMsg.addTitleLocArg("TitleLocArg");
//        return alertMsg;
//    }
//
//}
