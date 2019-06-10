package thread;

public class SynchronizedDemo1 {
    int x = 100;

    public synchronized void m1(String mark) {
        System.out.println(mark + " | method-M1 | thread name: " + Thread.currentThread().getName() + " | Object: " + this.toString());
        x = 1000;
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("x=" + x);
    }

    public synchronized void m2(String mark) {
        System.out.println(mark + " | method-M2 | thread name: " + Thread.currentThread().getName() + " | Object: " + this.toString());
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        x = 2000;
    }
    public static void main(String[] args) throws InterruptedException {
        SynchronizedDemo1 sd = new SynchronizedDemo1();
        new Thread(()->{sd.m1("test0");}).start();
        new Thread(()->{sd.m2("test1");}).start();
        sd.m2("main");
        System.out.println("Main x=" + sd.x);
    }
}