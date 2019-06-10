package thread.waitAndNotify;

public class WaitNotifyDemo {

    public void doWait() {
        synchronized (this) {
            System.out.println("do something in doWait ...");
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("wait end.");
        }
    }

    public void doNotify() {
        synchronized (this) {
            System.out.println("do something in doNotify ...");
            this.notifyAll();
            System.out.println("notify end.");
        }
    }

    public static void main(String[] args) {
        WaitNotifyDemo waitNotifyDemo = new WaitNotifyDemo();
        new Thread(()->{
            waitNotifyDemo.doNotify();
        }).start();
        new Thread(()->{
            waitNotifyDemo.doWait();
        }).start();
    }

}
