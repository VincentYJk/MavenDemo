package demo;

import org.apache.commons.lang3.concurrent.BasicThreadFactory;
import org.junit.Test;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
public class Demo3 {
    private final int i = 3;
    /*
     * 线程计数器
     *     将线程数量初始化
     *     每执行完成一条线程，调用countDown()使计数器减1
     *     主线程调用方法await()使其等待，当计数器为0时才被执行
     */
    private final CountDownLatch latch = new CountDownLatch(i);

    @Test
    public void testScheduledExecutorService(){
        //核心线程池的数量
        int corePoolSize = 3;
        ScheduledExecutorService executorService = new ScheduledThreadPoolExecutor(
                corePoolSize,
                new BasicThreadFactory.Builder()
                    .namingPattern("workerthread-%d")
                    .daemon(true)
                    .build()
        );
        runExecutorServer1(executorService);
        runExecutorServer2(executorService);
        try {
            latch.await(); // 主线程等待
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    private void runExecutorServer1(ScheduledExecutorService executorService){
        executorService.scheduleAtFixedRate(
                ()->System.out.printf("线程ExecutorServer1名称:%s%n", Thread.currentThread().getName())
                ,0,1000, TimeUnit.MILLISECONDS
        );
    }
    private void runExecutorServer2(ScheduledExecutorService executorService){
        executorService.scheduleAtFixedRate(
                ()->{
                    try{
                        String str = "123str";
                        System.out.println(str.split("str")[3]);
                    }catch (ArrayIndexOutOfBoundsException e){
                        System.out.println(e.getMessage());
                    }

                    System.out.printf("线程ExecutorServer2名称:%s%n",Thread.currentThread().getName());

                },0,1000, TimeUnit.MILLISECONDS
        );
    }










    @Test
    public void testTimer() {
        Timer timer1 = new Timer();
        //三个参数
        //第一个执行内容：内容是定时任务的执行内容，通过实现抽象类来完成这个动作
        //第二个参数内容：是在第多少时间之后开始执行定时任务内容，该参数不能小于0
        //第三个参数内容：是在两个任务之间的执行时间间隔，该参数不能小于等于0
        runTimer1(timer1);
        /*
         * timer 内部实现了多线程,在新建对象时会创建一个新的线程,使用多线程时，任务不互相影响
         */
        Timer timer2 = new Timer();
        runTimer2(timer2);
        System.out.println("主线程名称"+Thread.currentThread().getName());
        try {
            latch.await(); // 主线程等待
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    private void runTimer1(Timer timer) {
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                System.out.printf("线程Timer1名称:%s%n",Thread.currentThread().getName());
            }
        }, 0, 2000);
    }

    private void runTimer2(Timer timer) {
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                try{
                    String str = "123str";
                    System.out.println(str.split("str")[3]);
                }catch (ArrayIndexOutOfBoundsException e){
                    System.out.println(e.getMessage());
                }
                System.out.printf("线程Timer2名称:%s%n",Thread.currentThread().getName());
            }
        }, 0, 2000);
    }
}
