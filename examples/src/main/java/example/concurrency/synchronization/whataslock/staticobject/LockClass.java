package example.concurrency.synchronization.whataslock.staticobject;

import example.concurrency.synchronization.whataslock.LockBase;

import java.util.concurrent.CountDownLatch;

/**
 * 把class作为锁其实就是用Class对象当锁。
 *
 * @author puppylpg on 2022/05/18
 */
public class LockClass extends LockBase {

    public void method() {
        synchronized (LockClass.class) {
            work();
        }
    }

    public static void main(String... args) throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(5);
        System.out.println("lock one object is okay...");
        lockOneObject(latch);
        latch.await();
        System.out.println("lock many objects is also okay...");
        lockManyObject();
    }

    /**
     * 能锁住，一个一个来：
     *
     * 当前执行线程:Thread-0,执行时间:Thu May 19 10:08:19 CST 2022
     * 当前执行线程:Thread-3,执行时间:Thu May 19 10:08:20 CST 2022
     * 当前执行线程:Thread-4,执行时间:Thu May 19 10:08:21 CST 2022
     * 当前执行线程:Thread-2,执行时间:Thu May 19 10:08:22 CST 2022
     * 当前执行线程:Thread-1,执行时间:Thu May 19 10:08:23 CST 2022
     */
    public static void lockOneObject(CountDownLatch latch) {
        LockClass lockClass = new LockClass();
        for (int i = 0; i < 5; i++) {
            new Thread(() -> {
                lockClass.method();
                latch.countDown();
            }).start();
        }
    }

    /**
     * 能锁住，一个一个来：
     *
     * 当前执行线程:Thread-5,执行时间:Thu May 19 10:08:24 CST 2022
     * 当前执行线程:Thread-9,执行时间:Thu May 19 10:08:25 CST 2022
     * 当前执行线程:Thread-8,执行时间:Thu May 19 10:08:26 CST 2022
     * 当前执行线程:Thread-7,执行时间:Thu May 19 10:08:27 CST 2022
     * 当前执行线程:Thread-6,执行时间:Thu May 19 10:08:28 CST 2022
     */
    public static void lockManyObject() {
        for (int i = 0; i < 5; i++) {
            LockClass lockClass = new LockClass();
            new Thread(lockClass::method).start();
        }
    }
}
