package thread;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class BlockQueueDemo {

    private Integer capacityVal = 128;
    private List<Object> warehouse = new LinkedList<>();

    private Lock lock = new ReentrantLock();
    private Condition condition_notFull = lock.newCondition();
    private Condition condition_notEmpty = lock.newCondition();

    public void put(Object obj) {
        lock.lock();
        try {
            if(warehouse.size() == capacityVal) {
                System.out.println("warehouse is full, put() will be wait.");
                condition_notFull.wait();
            }
            warehouse.add(obj);
            condition_notEmpty.signal();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public Object take() {
        lock.lock();
        try {
            if(warehouse.size() == 0) {
                System.out.println("warehouse is empty, take() will be wait.");
                condition_notEmpty.await();
                System.out.println("take() been signaled.");
            }
            Object obj = warehouse.remove(0);
            condition_notFull.signal();
            return obj;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
        return null;
    }

    public static void main(String[] args) throws InterruptedException {
        BlockQueueDemo queue = new BlockQueueDemo();
        new Thread(()->{queue.put("1");}).start();
        new Thread(()->{queue.put("2");}).start();
        new Thread(()->{queue.put("3");}).start();
        new Thread(()->{System.out.println(queue.take());}).start();
        new Thread(()->{System.out.println(queue.take());}).start();
        new Thread(()->{System.out.println(queue.take());}).start();
        new Thread(()->{System.out.println(queue.take());}).start();
        TimeUnit.SECONDS.sleep(1);
        new Thread(()->{queue.put("4");}).start();
    }

}
