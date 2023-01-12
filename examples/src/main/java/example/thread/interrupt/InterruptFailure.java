package example.thread.interrupt;

import lombok.extern.slf4j.Slf4j;

/**
 * 当主线程发出interrupt信号的时候，子线程的sleep()被中断，抛出InterruptedException。
 * 在处理该异常的时候，相当于直接把该异常吞了。此时interrupt flag为false，在子线程中检测中断flag的时候，不能成功退出线程，
 * 直到i=11的时候，该子线程将自己的interrupt flag设为true，才再次在检查中断的时候，成功退出子线程。
 *
 * @author liuhaibo on 2018/06/13
 */
@Slf4j
public class InterruptFailure extends Thread {

    @Override
    public void run() {
        caller();
    }

    private void caller() {

        for (int i = 0; i < Integer.MAX_VALUE; i++) {
            log.info("task round: " + i);

            if (i > 10) {
                log.info("alert: process interruption wrongly. can't wait any longer. exit myself");
                Thread.currentThread().interrupt();
            }
            // quit if another thread let me interrupt
            if (Thread.currentThread().isInterrupted()) {
                log.info("thread is interrupted. exit loop");
                break;
            } else {
                task();
            }
        }
    }

    /**
     * task不处理interrupt，但是把interrupt吞了
     */
    private void task() {
        try {
            Thread.sleep(1000);
            log.info("slept for a while!");
        } catch (InterruptedException e) {
            log.info("interruption happens... but I do nothing:D");
        }
    }

    /**
     * 2023-01-12 16:50:24 [Thread-0] INFO  example.thread.interrupt.InterruptFailure:23 - task round: 0
     * 2023-01-12 16:50:25 [Thread-0] INFO  example.thread.interrupt.InterruptFailure:45 - slept for a while!
     * 2023-01-12 16:50:25 [Thread-0] INFO  example.thread.interrupt.InterruptFailure:23 - task round: 1
     * 2023-01-12 16:50:26 [Thread-0] INFO  example.thread.interrupt.InterruptFailure:45 - slept for a while!
     * 2023-01-12 16:50:26 [Thread-0] INFO  example.thread.interrupt.InterruptFailure:23 - task round: 2
     * 2023-01-12 16:50:27 [main] INFO  example.thread.interrupt.InterruptFailure:56 - let me interrupt the task thread:D
     * 2023-01-12 16:50:27 [main] INFO  example.thread.interrupt.InterruptFailure:58 - task thread interrupted? false
     * 2023-01-12 16:50:27 [Thread-0] INFO  example.thread.interrupt.InterruptFailure:47 - interruption happens... but I do nothing:D
     * 2023-01-12 16:50:27 [Thread-0] INFO  example.thread.interrupt.InterruptFailure:23 - task round: 3
     * 2023-01-12 16:50:28 [Thread-0] INFO  example.thread.interrupt.InterruptFailure:45 - slept for a while!
     * 2023-01-12 16:50:28 [Thread-0] INFO  example.thread.interrupt.InterruptFailure:23 - task round: 4
     * 2023-01-12 16:50:29 [Thread-0] INFO  example.thread.interrupt.InterruptFailure:45 - slept for a while!
     * 2023-01-12 16:50:29 [Thread-0] INFO  example.thread.interrupt.InterruptFailure:23 - task round: 5
     * 2023-01-12 16:50:30 [Thread-0] INFO  example.thread.interrupt.InterruptFailure:45 - slept for a while!
     * 2023-01-12 16:50:30 [Thread-0] INFO  example.thread.interrupt.InterruptFailure:23 - task round: 6
     * 2023-01-12 16:50:31 [Thread-0] INFO  example.thread.interrupt.InterruptFailure:45 - slept for a while!
     * 2023-01-12 16:50:31 [Thread-0] INFO  example.thread.interrupt.InterruptFailure:23 - task round: 7
     * 2023-01-12 16:50:32 [Thread-0] INFO  example.thread.interrupt.InterruptFailure:45 - slept for a while!
     * 2023-01-12 16:50:32 [Thread-0] INFO  example.thread.interrupt.InterruptFailure:23 - task round: 8
     * 2023-01-12 16:50:33 [Thread-0] INFO  example.thread.interrupt.InterruptFailure:45 - slept for a while!
     * 2023-01-12 16:50:33 [Thread-0] INFO  example.thread.interrupt.InterruptFailure:23 - task round: 9
     * 2023-01-12 16:50:34 [Thread-0] INFO  example.thread.interrupt.InterruptFailure:45 - slept for a while!
     * 2023-01-12 16:50:34 [Thread-0] INFO  example.thread.interrupt.InterruptFailure:23 - task round: 10
     * 2023-01-12 16:50:35 [Thread-0] INFO  example.thread.interrupt.InterruptFailure:45 - slept for a while!
     * 2023-01-12 16:50:35 [Thread-0] INFO  example.thread.interrupt.InterruptFailure:23 - task round: 11
     * 2023-01-12 16:50:35 [Thread-0] INFO  example.thread.interrupt.InterruptFailure:26 - alert: process interruption wrongly. can't wait any longer. exit myself
     * 2023-01-12 16:50:35 [Thread-0] INFO  example.thread.interrupt.InterruptFailure:31 - thread is interrupted. exit loop
     */
    public static void main(String[] args) throws InterruptedException {
        InterruptFailure thread = new InterruptFailure();
        thread.start();
        Thread.sleep(3000);
        // let me interrupt
        log.info("let me interrupt the task thread:D");
        thread.interrupt();
        log.info("task thread interrupted? " + thread.isInterrupted());
    }

}
