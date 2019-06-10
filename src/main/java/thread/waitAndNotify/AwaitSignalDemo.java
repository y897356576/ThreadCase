package thread.waitAndNotify;

import java.io.IOException;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class AwaitSignalDemo {

    private Lock lock;
    private Condition condition;

    public AwaitSignalDemo(Lock lock, Condition condition) {
        this.lock = lock;
        this.condition = condition;
    }

    public void doWait() {
        lock.lock();
        try {
            System.out.println("do something in doWait ...");
            condition.await();
            System.out.println("wait end.");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void doSignal() {
        lock.lock();
        try {
            System.out.println("do something in doSignal ...");
            condition.signal();
            System.out.println("signal end.");
        } finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) throws IOException {
        Lock lock = new ReentrantLock();
        Condition condition = lock.newCondition();
        new Thread(()->{
            new AwaitSignalDemo(lock, condition).doSignal();
        }).start();
        new Thread(()->{
            new AwaitSignalDemo(lock, condition).doWait();
        }).start();
    }

}
