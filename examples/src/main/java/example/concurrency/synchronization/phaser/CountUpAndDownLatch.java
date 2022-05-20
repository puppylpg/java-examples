package example.concurrency.synchronization.phaser;

import java.util.Random;
import java.util.concurrent.Phaser;

/**
 * {@link java.util.concurrent.CountDownLatch}可以控制任务都完成了再一起行动，
 * 但是必须提前设置好countdown数，也就是说异步任务数量是确定的。
 * {@link Phaser}可以随时动态调整任务数，或者说既可以countdown，又可以count up。
 * （灵活性主要体现在在构造函数时不需要强制指定目前有多少参与协作的线程，可以在运行时动态改变）
 * <p>
 * 有一个异步任务，{@link Phaser#register()}让要完成的任务数+1,相当于count up，
 * 完成一个异步任务，{@link Phaser#arrive()}让已完成任务数+1.相当于countdown，
 * 或者{@link Phaser#arriveAndDeregister()}让要完成的任务数-1,也相当于countdown。
 * 推荐前者，相当于做了完整的数据统计。
 * <p>
 * - https://stackoverflow.com/a/32442754/7676237
 *
 * @author liuhaibo on 2022/05/20
 */
public class CountUpAndDownLatch {

    /**
     * Thread-1: 7
     * Thread-2: 7
     * Thread-0: 5
     * Thread-3: 9
     * Thread-4: 6
     * Thread-5: 4
     * Thread Done: Thread-0
     * Thread-6: 0
     * Thread Done: Thread-6
     * Thread-7: 7
     * waiting...
     * Thread Done: Thread-1
     * total: 10, finished: 2, unfinished:8
     * Thread Done: Thread-7
     * Thread Done: Thread-4
     * Thread Done: Thread-3
     * Thread Done: Thread-5
     * Thread Done: Thread-2
     * Thread-8: 3
     * Thread Done: Thread-8
     * done...
     */
    public static void main(String... args) {
        CountUpAndDownLatch demo = new CountUpAndDownLatch();
        // 最后主线程也要完成任务,所以初始值（待完成任务）为1
        Phaser phaser = new Phaser(1);

        int i = 9;
        while (i-- > 0) {
            // 要等待的异步任务+1
            phaser.register();
            new Thread(() -> demo.work(phaser)).start();
        }
        System.out.println("waiting...");

        showTaskMetrics(phaser);
        // 完成任务数+1，并等待所有异步任务结束
        phaser.arriveAndAwaitAdvance();
        System.out.println("done...");
    }

    private static void showTaskMetrics(Phaser phaser) {
        int registeredParties = phaser.getRegisteredParties();
        int arrivedParties = phaser.getArrivedParties();
        int unArrivedParties = phaser.getUnarrivedParties();
        System.out.printf("total: %s, finished: %s, unfinished:%s\n", registeredParties, arrivedParties, unArrivedParties);
    }

    private void work(Phaser phaser) {
        try {
            int time = new Random().nextInt(10);
            System.out.println(Thread.currentThread().getName() + ": " + time);
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Thread Done: " + Thread.currentThread().getName());
        // 异步任务结束，完成任务数+1
        phaser.arrive();
        // 这个会让待完成任务数-1,而不是已完成任务数+1，不过效果相同
//        phaser.arriveAndDeregister();
    }
}
