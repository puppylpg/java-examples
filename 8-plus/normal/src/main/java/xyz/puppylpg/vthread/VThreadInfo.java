package xyz.puppylpg.vthread;

/**
 * @author puppylpg on 2023/09/24
 */
public class VThreadInfo {

    public static void main(String... args) throws InterruptedException {
        Thread v = Thread.ofVirtual().start(() -> {
            // VirtualThread[#21]/runnable@ForkJoinPool-1-worker-1
            System.out.println(Thread.currentThread());
        });

        // 也可以用countdown latch：https://mail.openjdk.org/pipermail/loom-dev/2023-May/005509.html
        v.join();
    }
}
