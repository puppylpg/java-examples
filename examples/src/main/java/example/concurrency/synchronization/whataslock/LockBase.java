package example.concurrency.synchronization.whataslock;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * 锁的使用场景是“一个线程使用锁阻塞住另一个线程”。
 * 锁，只是个形象的表述，实际是拿一个对象，当做小黑板。一个线程把自己的id写在小黑板上，
 * 另一个线程看到了，就只能等了。
 * 这个小黑板就是Java对象的mark word。
 * 至于那个对象用来充当锁？可以用class对象，也可以new的普通对象。普通对象既可以是这个类new出来的对象，也可以是别的类new出来的对象。
 *
 * 先改掉一个错误说法：“锁xx”，应该说是“把xx作为锁”。实际是“把xx作为锁，去锁住某些共享资源”。
 *
 * @author puppylpg on 2022/05/19
 */
public abstract class LockBase {

    protected void work() {
        System.out.printf("当前执行线程:%s,执行时间:%s%n", Thread.currentThread().getName(), new Date());
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    protected static void staticWork() {
        System.out.printf("当前执行线程:%s,执行时间:%s%n", Thread.currentThread().getName(), new Date());
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
