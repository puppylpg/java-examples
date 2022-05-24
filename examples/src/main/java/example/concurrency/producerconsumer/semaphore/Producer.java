package example.concurrency.producerconsumer.semaphore;

import java.util.List;
import java.util.concurrent.Semaphore;

/**
 * @author puppylpg on 2022/05/24
 */
public class Producer {

    private final Semaphore mutexP;
    private final Semaphore mutexC;

    private final List<Integer> list;

    Producer(Semaphore mutexP, Semaphore mutexC, List<Integer> list) {
        this.mutexP = mutexP;
        this.mutexC = mutexC;
        this.list = list;
    }

    public void produce(int data) throws InterruptedException {
        mutexP.acquire();
        System.out.println(Thread.currentThread().getName() + " produce: " + data);
        list.add(data);
        mutexC.release();
    }
}
