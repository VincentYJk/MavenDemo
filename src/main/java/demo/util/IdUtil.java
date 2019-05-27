package demo.util;

import java.util.UUID;

/**
 * @ClassName IdUtil
 * @Description id生成器
 * @Author 梁明辉
 * @Date 2019/5/27 11:13
 * @ModifyDate 2019/5/27 11:13
 * @Version 1.0
 */
public class IdUtil {
    public static String getUUID() {
        String id = UUID.randomUUID().toString();
        id = id.replace("-", "");
        return id;
    }
}
