package thread;

import java.util.concurrent.TimeUnit;

public class VolatileDemo {

    //volatile 保证属性的可见性，包含lock()汇编指令，激活内存屏障保证属性的可见性

    private static Boolean stop = false;
    private volatile static Boolean[] bs = new Boolean[1];

    public static void f() {
        int i = 0;
        while(!bs[0]) {
            i++;
        }
        System.out.println("while is finish");
    }

    public static void main(String[] args) throws InterruptedException {
        bs[0] = stop;
        new Thread(()->VolatileDemo.f()).start();
        TimeUnit.SECONDS.sleep(1);
//        VolatileDemo.stop = true;   //直接修改元素的值，未经过数组，不触发可见性
        bs[0] = true;  //修改了数组内元素的地址，触发volatile可见性
    }
}
