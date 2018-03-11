package thread;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by 石头 on 2017/7/1.
 */
public class ProducerAndConsumer {

    public static void main(String[] args) {
        Depo mDepot = new Depo();   //创建一个仓库
        Producer mPro = new Producer(mDepot);  //创建一个生产者并关联仓库
        Consumer mCus = new Consumer(mDepot);  //创建一个消费者并关联仓库
        mPro.produce(60);  //生产者创建一个生产60 的线程
        doSleep(100);
        mPro.produce(120); //生产者创建一个生产120的线程
        doSleep(100);
        mPro.produce(110); //生产者创建一个生产110的线程
        doSleep(100);
        mCus.consume(90);  //消费者创建一个消费90 的线程
        doSleep(100);
        mCus.consume(150); //消费者创建一个消费150的线程
    }

    private static void doSleep(int ms){
        try{
            Thread.sleep(ms);
        } catch (InterruptedException e){
            System.out.println("***** In Exception");
        }
    }
}

/**
 * 一个仓库
 * 一个生产方法，一个消费方法（共用当前仓库的锁）
 * 创建多个生产/消费线程，用的锁都是当前仓库的锁
 */
class Depo {
    private int capacity = 100;   //容量
    private int amount = 0;     //当前值
    private Lock lock = new ReentrantLock();      //独占锁
    private Condition proCondition = lock.newCondition();   //生产条件
    private Condition conCondition = lock.newCondition();   //消费条件

    public void product(int val) {
        lock.lock();
        try{
            int ori = val;
            while(val > 0){
                if(amount >= capacity){
                    System.out.println("(" + ori + ")pro_await_before");
                    //condition需要在获取锁的前提下的使用，await()时放弃锁
                    //此线程挂起，只有被唤醒才能继续争抢锁，被唤醒后从此处继续执行
                    proCondition.await();
                    System.out.println("(" + ori + ")pro_await_after");
                }
                int doPro = capacity-amount>=val ? val : (capacity-amount);
                amount += doPro;
                val -= doPro;
                System.out.printf("%s produce(%3d) --> size=%3d, inc=%3d, left=%3d\n",
                    Thread.currentThread().getName(), ori, amount, doPro, val);
                System.out.println("(" + ori + ")con_signal_before");
                //线程执行某个condition的signal后会继续向下执行，直到此线程放弃锁后，被唤醒的线程与其他等待线程再竞争锁；
                conCondition.signal();
                System.out.println("(" + ori + ")con_signal_after");
            }
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void consume(int val) {
        lock.lock();
        try{
            int ori = val;
            while(val > 0){
                if(amount == 0){
                    System.out.println("(" + ori + ")con_await_before");
                    //condition需要在获取锁的前提下的使用，await()时放弃锁
                    //此线程挂起，只有被唤醒才能继续争抢锁，被唤醒后从此处继续执行
                    conCondition.await();
                    System.out.println("(" + ori + ")con_await_after");
                }
                int doCon = amount>=val ? val : amount;
                amount -= doCon;
                val -= doCon;
                System.out.printf("%s consume(%3d) <-- size=%3d, dec=%3d, left=%3d\n",
                    Thread.currentThread().getName(), ori, amount, doCon, val);
                System.out.println("(" + ori + ")pro_signal_before");
                //线程执行某个condition的signal后会继续向下执行，直到此线程放弃锁后，被唤醒的线程与其他等待线程再竞争锁；
                proCondition.signal();
                System.out.println("(" + ori + ")pro_signal_after");
            }
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

}

class Producer {
    private Depo depo;

    public Producer(Depo depo){
        this.depo = depo;
    }

    public void produce(final int val){
        new Thread(){
            public void run(){
                depo.product(val);
            }
        }.start();
    }
}

class Consumer {
    private Depo depo;

    public Consumer(Depo depo){
        this.depo = depo;
    }

    public void consume(final int val){
        new Thread(){
            public void run(){
                depo.consume(val);
            }
        }.start();
    }
}