package demo.selefDemo;

import org.junit.Test;

/**
 * @author Administrator
 * @date 2019/3/14 9:35
 */
public class Demo1 {
    @Test
    public void test1() {
        int i =1000;
        int j = 12590;
        for(int index = 0;index<=j/i;index++){
            System.out.println(index+1);
        }

    }
}
