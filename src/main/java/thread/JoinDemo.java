package thread;

import java.util.concurrent.TimeUnit;

public class JoinDemo {

    static int x = 0;

    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(()->{
            x = 100;
        });
        t1.start();
        //TimeUnit.MILLISECONDS.sleep(1);
        //t1.join();  //主线程暂停，等待t1线程执行完毕
        System.out.println("x = " + x);
    }

}
