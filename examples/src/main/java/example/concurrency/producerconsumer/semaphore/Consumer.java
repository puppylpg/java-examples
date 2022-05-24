package example.concurrency.producerconsumer.semaphore;

import java.util.List;
import java.util.concurrent.Semaphore;

/**
 * @author puppylpg on 2022/05/24
 */
public class Consumer {

    private final Semaphore mutexP;
    private final Semaphore mutexC;

    private final List<Integer> list;

    public Consumer(Semaphore mutexP, Semaphore mutexC, List<Integer> list) {
        this.mutexP = mutexP;
        this.mutexC = mutexC;
        this.list = list;
    }

    public void consume() throws InterruptedException {
        mutexC.acquire();
        int data = list.remove(0);
        System.out.println(Thread.currentThread().getName() + " data consumed: " + data);
        mutexP.release();
    }
}
