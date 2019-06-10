package thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Test {

    public static void doTest() {
        System.out.println("Thread: " + Thread.currentThread().getName() + "  start");
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Thread: " + Thread.currentThread().getName() + "  end");
    }

    /**
     * 线程池内的线程在执行任务时被挂起，会等待挂起完成再继续执行任务
     * 只有任务执行结束或者异常退出，此线程才会重新进入线程池等待
     * BIO在获取到socket后即使用线程获取数据，在数据充满tcp缓冲区或者传输结束这一段时间是无效的
     * 多路复用在tcp缓冲区满或传输结束的时候会才通知线程来处理，因此节省了线程等待数据传输完毕的时间
     * @param args
     */
    public static void main(String[] args) {
        ExecutorService executor = Executors.newFixedThreadPool(3);
        executor.execute(()->{Test.doTest();});
        executor.execute(()->{Test.doTest();});
        executor.execute(()->{Test.doTest();});
        executor.execute(()->{Test.doTest();});
        executor.execute(()->{Test.doTest();});
        executor.execute(()->{Test.doTest();});
        executor.execute(()->{Test.doTest();});
        executor.execute(()->{Test.doTest();});
        executor.execute(()->{Test.doTest();});
        executor.execute(()->{Test.doTest();});
    }

}
