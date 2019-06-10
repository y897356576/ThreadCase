package thread;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.TimeUnit;

public class CyclicBarrierDemo extends Thread{

    private CyclicBarrier cyclicBarrier;

    public CyclicBarrierDemo(CyclicBarrier cyclicBarrier) {
        this.cyclicBarrier = cyclicBarrier;
    }

    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName() + " do something ...");
        try {
//            TimeUnit.SECONDS.sleep(1);
            cyclicBarrier.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (BrokenBarrierException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName() + " end");
    }


    /*public static void main(String[] args) throws InterruptedException {
        CyclicBarrier cyclicBarrier = new CyclicBarrier(3);
        new CyclicBarrierDemo(cyclicBarrier).start();
        new CyclicBarrierDemo(cyclicBarrier).start();
//        TimeUnit.SECONDS.sleep(1);
        new CyclicBarrierDemo(cyclicBarrier).start();
        new CyclicBarrierDemo(cyclicBarrier).start();
//        TimeUnit.SECONDS.sleep(1);
        new CyclicBarrierDemo(cyclicBarrier).start();
//        TimeUnit.SECONDS.sleep(1);
        System.out.println("main end.");
    }*/


    public static void main(String[] args) throws InterruptedException {
        //构造方法内的Runnable的内容会在屏障配打破时执行，打破几次执行几次
        CyclicBarrier cyclicBarrier = new CyclicBarrier(3, ()->{
            System.out.println("main end.");
        });
        new CyclicBarrierDemo(cyclicBarrier).start();
        new CyclicBarrierDemo(cyclicBarrier).start();
//        TimeUnit.SECONDS.sleep(1);
        new CyclicBarrierDemo(cyclicBarrier).start();
        new CyclicBarrierDemo(cyclicBarrier).start();
//        TimeUnit.SECONDS.sleep(1);
        new CyclicBarrierDemo(cyclicBarrier).start();
        new CyclicBarrierDemo(cyclicBarrier).start();
//        TimeUnit.SECONDS.sleep(1);
    }
}
