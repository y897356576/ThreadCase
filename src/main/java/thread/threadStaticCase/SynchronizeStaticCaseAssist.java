package thread.threadStaticCase;

/**
 * Created by 石头 on 2017/7/7.
 */
public class SynchronizeStaticCaseAssist {

    static {
        System.out.println("this is SynchronizeStaticCaseAssist's static block");
    }

    public synchronized static void doTest(){
        System.out.println("in doTest");
    }

}
