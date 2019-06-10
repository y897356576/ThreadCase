package thread.threadStaticCase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by 石头 on 2017/7/7.
 */
public class SynchronizeStaticCase {

    public static void main(String[] args) throws ClassNotFoundException, InterruptedException {
        testClazzAndStaticSync();
    }

    /**
     * 类的Class对象与类内的Static代码使用的是同一个同步锁
     */
    private static void testClazzAndStaticSync() throws InterruptedException, ClassNotFoundException {
        ExecutorService executor = Executors.newFixedThreadPool(2);

        final Class clazz = Class.forName("thread.threadStaticCase.SynchronizeStaticCaseAssist");
        Runnable r1 = new Runnable() {
            @Override
            public void run() {
                synchronized (clazz) {
                    System.out.println("in thread1");
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        Runnable r2 = ()->{
            System.out.println("in thread2");
            SynchronizeStaticCaseAssist.doTest();
        };

        executor.execute(r1);
        Thread.sleep(1);
        executor.execute(r2);

        executor.shutdown();
    }

}
