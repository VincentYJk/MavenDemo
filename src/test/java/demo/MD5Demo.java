package demo;


import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

import java.io.FileInputStream;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.regex.Pattern;

/**
 * MD5的全称是Message-Digest Algorithm 5（信息-摘要算法）
 * MD5其实不算是加密算法，而是一种信息的摘要
 * 它的特性是不可逆的，所以除了暴力破解 一般逆序算法是得不到结果的
 * 举个例子：比如1+99=100，MD5接到的字符是1和99 然后通过自己的算法最后生成100
 * 但知道结果是100却很难推测出是通过1+99得来的。
 * 推荐文章：https://blog.csdn.net/qq_40006446/article/details/80930113
 */
@Slf4j
public class MD5Demo {
    static Pattern pattern1 = Pattern.compile("^\\d+$|-\\d+$");
    static Pattern pattern2 = Pattern.compile("\\d+\\.\\d+$|-\\d+\\.\\d+$");

    @Test
    public void test4() {
        log.info(isNum("13.0") + "");
    }

    private static boolean isNum(String value) {
        //空值返回true
        if (StringUtils.isBlank(value)) {
            return true;
        }
        return pattern2.matcher(value).matches();
    }

    /**
     * 简单示例
     */
    @Test
    public void test1() {
        String test_str_first = 11315 + "";
        log.info(getMD5(test_str_first));
    }

    /**
     * 常用解决暴力破解办法
     * 1：多次加密
     * 2：加盐
     */
    @Test
    public void test2() {
        String test_str_first = 11315 + "";
        String salt = "666";
        // 1 :多次加密
        // log.info(getMD5(test_str_first, 2));
        //2 : 加盐
        //2.1 密码加盐
        // log.info(getMD5(test_str_first+salt));
        //2.2 加密之后加盐
        // log.info(getMD5(test_str_first,salt));
    }

    @Test
    /**
     * 碰撞:md5值相同
     * 找到了碰撞也没用，你只是发现两个文件可以有相同的MD5，但你无法反推原文是什么
     * 且商业应用中密码都是层层加密，破解不了的。
     */
    public void test3() {
        String path = "F:\\project\\MavenDemo\\src\\test\\java\\demo\\";
        try {
            String str1 = DigestUtils.md5Hex(new FileInputStream(path + "GoodbyeWorld-colliding.exe"));
            String str2 = DigestUtils.md5Hex(new FileInputStream(path + "HelloWorld-colliding.exe"));
            String str1_sha = DigestUtils.sha1Hex(path + "GoodbyeWorld-colliding.exe");
            String str2_sha = DigestUtils.sha1Hex(path + "HelloWorld-colliding.exe");
            log.info("str1 md5>>>>>" + str1);
            log.info("str2 md5>>>>>" + str2);
//            log.info("str1_sha>>>>>"+str1_sha);
//            log.info("str1_sha>>>>>"+str2_sha);
            /*
             * 怎样解决碰撞
             * 解决碰撞其实可以通过 MD5 和 SHA-1 结合使用来实现。
             * 1:首先将文件 A 的 MD5 值记为 B 再把 A 的 SHA-1 记为 C
             * 2:之后用将 B 和 C 相加之后再次运算 MD5 值就好了
             * MD5 值碰撞的几率已经很小，再结合 SHA-1 的话，基本上就不会发生碰撞的问题出现了
             * 在新的算法普及之前，MD5 还是可以继续使用的。
             */
//            log.info(DigestUtils.md5Hex(str1+str1_sha));
//            log.info(DigestUtils.md5Hex(str2+str2_sha));
        } catch (Exception e) {
            log.info(e.toString());
        }

    }

    private String getMD5(String str, int count) {
        if (count == 0) {
            return str;
        } else {
            str = getMD5(str);
            return getMD5(str, count - 1);
        }
    }

    private String getMD5(String str, String salt) {
        try {
            // 生成一个MD5加密计算摘要
            MessageDigest md = MessageDigest.getInstance("MD5");
            // 计算md5函数
            md.update(str.getBytes());
            // digest()最后确定返回md5 hash值，返回值为8为字符串。因为md5 hash值是16位的hex值，实际上就是8位的字符
            // BigInteger函数则将8位的字符串转换成16位hex值，用字符串来表示；得到字符串形式的hash值
            return new BigInteger(1, md.digest()).toString(16) + salt;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 对字符串md5加密(小写+字母)
     *
     * @param str 传入要加密的字符串
     * @return MD5加密后的字符串
     */
    private String getMD5(String str) {
        return DigestUtils.md5Hex(str);
    }

}