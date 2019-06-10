package thread;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;

public class SynchronizedDemo2 {

    private static Integer count=0;
    private static AtomicInteger aint = new AtomicInteger(0);

    public static void incr(){
        synchronized (count) {
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            count++;    // count = count + 1;   此处 count+1 会新生成一个Integer对象并赋值给 count 导致锁失效
        }
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        for(int i=0;i<1000;i++){
            new Thread(()->SynchronizedDemo2.incr()).start();
        }
        Thread.sleep(2000);
        System.out.println("result:" + count);
    }
}
