package example.thread.interrupt;

import lombok.extern.slf4j.Slf4j;

/**
 * 当主线程发出interrupt信号的时候，子线程的sleep()被中断，抛出InterruptedException。
 * 不处理该异常，直接交到上级caller。上级caller也一直不处理，最后整个线程直接结束。也相当于成功退出了线程。
 *
 * @author puppylpg on 2018/06/14
 */
@Slf4j
public class InterruptRethrow extends Thread {

    @Override
    public void run() {
        try {
            caller();
        } catch (InterruptedException e) {
           log.info("task exit...", e);
        }
    }

    /**
     * caller也不处理interrupt，交给上层caller。rethrow interrupt的时候会导致循环结束
     */
    private void caller() throws InterruptedException {

        for (int i = 0; i < Integer.MAX_VALUE; i++) {
            log.info("task round: " + i);

            task();
        }
    }

    /**
     * 不处理interrupt，交给caller
     */
    private void task() throws InterruptedException {
            Thread.sleep(1000);
            log.info("slept for a while!");
    }

    /**
     * 2023-01-12 16:47:19 [Thread-0] INFO  example.thread.interrupt.InterruptRethrow:29 - task round: 0
     * 2023-01-12 16:47:20 [Thread-0] INFO  example.thread.interrupt.InterruptRethrow:40 - slept for a while!
     * 2023-01-12 16:47:20 [Thread-0] INFO  example.thread.interrupt.InterruptRethrow:29 - task round: 1
     * 2023-01-12 16:47:21 [Thread-0] INFO  example.thread.interrupt.InterruptRethrow:40 - slept for a while!
     * 2023-01-12 16:47:21 [Thread-0] INFO  example.thread.interrupt.InterruptRethrow:29 - task round: 2
     * 2023-01-12 16:47:22 [main] INFO  example.thread.interrupt.InterruptRethrow:63 - let me interrupt the task thread:D
     * 2023-01-12 16:47:22 [main] INFO  example.thread.interrupt.InterruptRethrow:65 - task thread interrupted? true
     * 2023-01-12 16:47:22 [Thread-0] INFO  example.thread.interrupt.InterruptRethrow:19 - task exit...
     * java.lang.InterruptedException: sleep interrupted
     * 	at java.lang.Thread.sleep(Native Method)
     * 	at example.thread.interrupt.InterruptRethrow.task(InterruptRethrow.java:39)
     * 	at example.thread.interrupt.InterruptRethrow.caller(InterruptRethrow.java:31)
     * 	at example.thread.interrupt.InterruptRethrow.run(InterruptRethrow.java:17)
     */
    public static void main(String[] args) throws InterruptedException {
        InterruptRethrow thread = new InterruptRethrow();
        thread.start();
        Thread.sleep(3000);
        // let me interrupt
        log.info("let me interrupt the task thread:D");
        thread.interrupt();
        log.info("task thread interrupted? " + thread.isInterrupted());
    }
}
