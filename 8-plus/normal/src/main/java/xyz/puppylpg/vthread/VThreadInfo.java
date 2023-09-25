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

        v.join();
    }
}
