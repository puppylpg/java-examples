package example.concurrency.synchronization.whataslock.ordinaryobject.thisclassobject;

import example.concurrency.synchronization.whataslock.LockBase;

import java.util.concurrent.CountDownLatch;

/**
 * 把this作为锁，实际是把这个类new的这个对象（this）作为锁。
 * 如果new了多个对象，就没法在他们之间互斥了。
 *
 * @author liuhaibo on 2022/05/18
 */
public class LockThis extends LockBase {

    public void method() {
        synchronized (this) {
            work();
        }
    }

    public static void main(String... args) throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(5);
        System.out.println("lock one object is okay...");
        lockOneObject(latch);
        latch.await();
        System.out.println("lock many objects is not okay...");
        lockManyObject();
    }

    /**
     * 能锁住，一个一个来：
     *
     * 当前执行线程:Thread-0,执行时间:Wed May 18 18:23:01 CST 2022
     * 当前执行线程:Thread-4,执行时间:Wed May 18 18:23:02 CST 2022
     * 当前执行线程:Thread-3,执行时间:Wed May 18 18:23:03 CST 2022
     * 当前执行线程:Thread-2,执行时间:Wed May 18 18:23:04 CST 2022
     * 当前执行线程:Thread-1,执行时间:Wed May 18 18:23:05 CST 2022
     */
    public static void lockOneObject(CountDownLatch latch) {
        LockThis lockThis = new LockThis();
        for (int i = 0; i < 5; i++) {
            new Thread(() -> {
                lockThis.method();
                latch.countDown();
            }).start();
        }
    }

    /**
     * 不能锁住，一起执行：
     *
     * 当前执行线程:Thread-5,执行时间:Wed May 18 18:30:18 CST 2022
     * 当前执行线程:Thread-6,执行时间:Wed May 18 18:30:18 CST 2022
     * 当前执行线程:Thread-7,执行时间:Wed May 18 18:30:18 CST 2022
     * 当前执行线程:Thread-8,执行时间:Wed May 18 18:30:18 CST 2022
     * 当前执行线程:Thread-9,执行时间:Wed May 18 18:30:18 CST 2022
     */
    public static void lockManyObject() {
        for (int i = 0; i < 5; i++) {
            LockThis lockThis = new LockThis();
            new Thread(lockThis::method).start();
        }
    }
}
