package example.concurrency.synchronization.whataslock.staticobject;

import example.concurrency.synchronization.whataslock.LockBase;

import java.util.concurrent.CountDownLatch;

/**
 * 把static method作为锁，其实也是把class object作为锁。
 *
 * @author puppylpg on 2022/05/18
 */
public class LockStaticMethod extends LockBase {

    public static synchronized void method() {
        staticWork();
    }

    public static void main(String... args) throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(5);
        System.out.println("lock class object is okay...");
        lockClassMethod(latch);
        latch.await();
    }

    /**
     * 能锁住，一个一个来：
     *
     * 当前执行线程:Thread-0,执行时间:Thu May 19 10:17:23 CST 2022
     * 当前执行线程:Thread-4,执行时间:Thu May 19 10:17:24 CST 2022
     * 当前执行线程:Thread-3,执行时间:Thu May 19 10:17:25 CST 2022
     * 当前执行线程:Thread-2,执行时间:Thu May 19 10:17:26 CST 2022
     * 当前执行线程:Thread-1,执行时间:Thu May 19 10:17:27 CST 2022
     */
    public static void lockClassMethod(CountDownLatch latch) {
        for (int i = 0; i < 5; i++) {
            new Thread(() -> {
                LockStaticMethod.method();
                latch.countDown();
            }).start();
        }
    }
}
