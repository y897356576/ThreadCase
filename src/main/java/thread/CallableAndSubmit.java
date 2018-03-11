package thread;

import java.util.Random;
import java.util.concurrent.*;

/**
 * Created by 石头 on 2017/7/3.
 */
public class CallableAndSubmit {

    /**
     * Callable可以有返回值，通过submit返回的future可获取Callable执行后的返回值；
     * Callable本身执行为非阻塞，但是future.get()为阻塞过程，会阻塞下面的代码执行；
     */
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService executor = Executors.newCachedThreadPool();
        Random random = new Random();

        Callable callable1 = new CallableExample(random.nextBoolean());
        Future<Integer> future1 = executor.submit(callable1);
        System.out.println("callable1 already submitted");

        Callable callable2 = new CallableExample(random.nextBoolean());
        Future<Integer> future2 = executor.submit(callable2);
        System.out.println("callable2 already submitted");

        System.out.println("-----");

        try {
            System.out.println("being output1");
            System.out.println(future1.get());
        } catch (Exception e){
            System.out.println(e);
        }
        System.out.println("-----");
        try {
            System.out.println("being output2");
            System.out.println(future2.get());
        } catch (Exception e){
            System.out.println(e);
        }
    }
}

class CallableExample implements Callable<Integer>{

    private Boolean hasException;

    CallableExample(Boolean flag) {
        this.hasException = flag;
    }

    public Integer call() throws Exception {
        Integer sum = 0;
        for(int i=1; i<=100; i++){
            sum += i;
            Thread.sleep(50);
            if(i==100 && hasException){
                throw new Exception("中断测试");
            }
        }
        return sum;
    }
}
