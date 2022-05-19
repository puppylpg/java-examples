package example.concurrency.synchronization.whataslock.ordinaryobject.otherclassobject;

import example.concurrency.synchronization.whataslock.LockBase;

import java.util.concurrent.CountDownLatch;

/**
 * 使用别的类的对象作为锁，且这个对象是类独有的，那和使用this作为锁没区别。
 *
 * @author liuhaibo on 2022/05/18
 */
public class LockInner3rdObject extends LockBase {

    final Object lock = new Object();

    public void method() {
        synchronized (lock) {
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
     * 当前执行线程:Thread-0,执行时间:Thu May 19 10:01:13 CST 2022
     * 当前执行线程:Thread-4,执行时间:Thu May 19 10:01:14 CST 2022
     * 当前执行线程:Thread-3,执行时间:Thu May 19 10:01:15 CST 2022
     * 当前执行线程:Thread-2,执行时间:Thu May 19 10:01:16 CST 2022
     * 当前执行线程:Thread-1,执行时间:Thu May 19 10:01:17 CST 2022
     */
    public static void lockOneObject(CountDownLatch latch) {
        LockInner3rdObject lockInner3rdObject = new LockInner3rdObject();
        for (int i = 0; i < 5; i++) {
            new Thread(() -> {
                lockInner3rdObject.method();
                latch.countDown();
            }).start();
        }
    }

    /**
     * 不能锁住，一起执行：
     *
     * 当前执行线程:Thread-5,执行时间:Thu May 19 10:01:18 CST 2022
     * 当前执行线程:Thread-6,执行时间:Thu May 19 10:01:18 CST 2022
     * 当前执行线程:Thread-9,执行时间:Thu May 19 10:01:18 CST 2022
     * 当前执行线程:Thread-7,执行时间:Thu May 19 10:01:18 CST 2022
     * 当前执行线程:Thread-8,执行时间:Thu May 19 10:01:18 CST 2022
     */
    public static void lockManyObject() {
        for (int i = 0; i < 5; i++) {
            LockInner3rdObject lockOuter3rdObject = new LockInner3rdObject();
            new Thread(lockOuter3rdObject::method).start();
        }
    }
}
