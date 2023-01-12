package example.thread.interrupt;

import lombok.extern.slf4j.Slf4j;

/**
 * 当主线程发出interrupt信号的时候，子线程的sleep()被中断，抛出InterruptedException。
 * 在处理该异常的时候，重新设置interrupt flag为true，则在子线程中检测中断flag的时候，成功退出线程。
 *
 * @author liuhaibo on 2018/06/13
 */
@Slf4j
public class InterruptReInterrupt extends Thread {

    @Override
    public void run() {
        caller();
    }

    /**
     * caller检测interrupt状态，并退出循环
     */
    private void caller() {

        for (int i = 0; i < Integer.MAX_VALUE; i++) {
            log.info("task round: " + i);

            // quit if another thread let me interrupt
            if (Thread.currentThread().isInterrupted()) {
                log.info("thread is interrupted. exiting...");
                break;
            } else {
                task();
            }
        }
    }

    /**
     * task捕获了interrupt，但并不想处理，所以恢复interrupt状态
     */
    private void task() {
        try {
            Thread.sleep(1000);
            log.info("slept for a while!");
        } catch (InterruptedException e) {
            log.info("interruption happens...");
            Thread.currentThread().interrupt();
        }
    }

    /**
     * 2023-01-12 16:47:49 [Thread-0] INFO  example.thread.interrupt.InterruptReInterrupt:25 - task round: 0
     * 2023-01-12 16:47:50 [Thread-0] INFO  example.thread.interrupt.InterruptReInterrupt:43 - slept for a while!
     * 2023-01-12 16:47:50 [Thread-0] INFO  example.thread.interrupt.InterruptReInterrupt:25 - task round: 1
     * 2023-01-12 16:47:51 [Thread-0] INFO  example.thread.interrupt.InterruptReInterrupt:43 - slept for a while!
     * 2023-01-12 16:47:51 [Thread-0] INFO  example.thread.interrupt.InterruptReInterrupt:25 - task round: 2
     * 2023-01-12 16:47:52 [main] INFO  example.thread.interrupt.InterruptReInterrupt:55 - let me interrupt the task thread:D
     * 2023-01-12 16:47:52 [main] INFO  example.thread.interrupt.InterruptReInterrupt:57 - task thread interrupted? true
     * 2023-01-12 16:47:52 [Thread-0] INFO  example.thread.interrupt.InterruptReInterrupt:45 - interruption happens...
     * 2023-01-12 16:47:52 [Thread-0] INFO  example.thread.interrupt.InterruptReInterrupt:25 - task round: 3
     * 2023-01-12 16:47:52 [Thread-0] INFO  example.thread.interrupt.InterruptReInterrupt:29 - thread is interrupted. exiting...
     */
    public static void main(String[] args) throws InterruptedException {
        InterruptReInterrupt thread = new InterruptReInterrupt();
        thread.start();
        Thread.sleep(3000);
        // let me interrupt
        log.info("let me interrupt the task thread:D");
        thread.interrupt();
        log.info("task thread interrupted? " + thread.isInterrupted());
    }

}
