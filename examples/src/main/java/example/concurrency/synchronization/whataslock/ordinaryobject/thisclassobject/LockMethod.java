package example.concurrency.synchronization.whataslock.ordinaryobject.thisclassobject;

import example.concurrency.synchronization.whataslock.LockBase;

import java.util.concurrent.CountDownLatch;

/**
 * 把普通方法作为锁，其实就是把this作为锁。
 *
 * @author liuhaibo on 2022/05/18
 */
public class LockMethod extends LockBase {

    public synchronized void method() {
        work();
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
     * 当前执行线程:Thread-0,执行时间:Thu May 19 10:14:29 CST 2022
     * 当前执行线程:Thread-4,执行时间:Thu May 19 10:14:30 CST 2022
     * 当前执行线程:Thread-3,执行时间:Thu May 19 10:14:31 CST 2022
     * 当前执行线程:Thread-2,执行时间:Thu May 19 10:14:32 CST 2022
     * 当前执行线程:Thread-1,执行时间:Thu May 19 10:14:33 CST 2022
     */
    public static void lockOneObject(CountDownLatch latch) {
        LockMethod lockMethod= new LockMethod();
        for (int i = 0; i < 5; i++) {
            new Thread(() -> {
                lockMethod.method();
                latch.countDown();
            }).start();
        }
    }

    /**
     * 不能锁住，一起执行：
     *
     * 当前执行线程:Thread-5,执行时间:Thu May 19 10:14:34 CST 2022
     * 当前执行线程:Thread-6,执行时间:Thu May 19 10:14:34 CST 2022
     * 当前执行线程:Thread-7,执行时间:Thu May 19 10:14:34 CST 2022
     * 当前执行线程:Thread-8,执行时间:Thu May 19 10:14:34 CST 2022
     * 当前执行线程:Thread-9,执行时间:Thu May 19 10:14:34 CST 2022
     */
    public static void lockManyObject() {
        for (int i = 0; i < 5; i++) {
            LockMethod lockMethod= new LockMethod();
            new Thread(lockMethod::method).start();
        }
    }
}
