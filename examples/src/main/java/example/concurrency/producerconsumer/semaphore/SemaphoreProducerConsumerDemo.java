package example.concurrency.producerconsumer.semaphore;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Semaphore;

/**
 * @author puppylpg on 2022/05/24
 */
public class SemaphoreProducerConsumerDemo {

    /**
     * semaphore其实就是os里说的mutex。通过两个信号量，实现了类似于
     * {@link java.util.concurrent.locks.Condition}的生产者消费者分别唤醒机制。
     *
     * Thread-1 produce: 4
     * Thread-0 produce: 4
     * Thread-2 produce: 4
     * Thread-3 data consumed: 4
     * Thread-4 data consumed: 4
     * Thread-1 produce: 3
     * Thread-5 data consumed: 4
     * Thread-3 data consumed: 3
     * Thread-0 produce: 3
     * Thread-1 produce: 2
     * Thread-2 produce: 3
     * Thread-5 data consumed: 2
     * Thread-3 data consumed: 3
     * Thread-0 produce: 2
     * Thread-4 data consumed: 3
     * Thread-5 data consumed: 2
     * Thread-1 produce: 1
     * Thread-2 produce: 2
     * Thread-0 produce: 1
     * Thread-3 data consumed: 1
     * Thread-4 data consumed: 2
     * Thread-1 produce: 0
     * Thread-3 data consumed: 1
     * Thread-2 produce: 1
     * Thread-0 produce: 0
     * Thread-3 data consumed: 0
     * Thread-4 data consumed: 0
     * Thread-5 data consumed: 1
     * Thread-2 produce: 0
     * Thread-3 data consumed: 0
     */
    public static void main(String... args) throws InterruptedException {
        SemaphoreProducerConsumerDemo demo = new SemaphoreProducerConsumerDemo();

        Semaphore mutexP = new Semaphore(3);
        Semaphore mutexC = new Semaphore(0);
        List<Integer> list = Collections.synchronizedList(new ArrayList<>());

        new Thread(() -> demo.produceBatch(new Producer(mutexP, mutexC, list))).start();
        new Thread(() -> demo.produceBatch(new Producer(mutexP, mutexC, list))).start();
        new Thread(() -> demo.produceBatch(new Producer(mutexP, mutexC, list))).start();

        new Thread(() -> demo.consumeBatch(new Consumer(mutexP, mutexC, list))).start();
        new Thread(() -> demo.consumeBatch(new Consumer(mutexP, mutexC, list))).start();
        new Thread(() -> demo.consumeBatch(new Consumer(mutexP, mutexC, list))).start();

        Thread.currentThread().join();
    }

    private void produceBatch(Producer producer) {
        int i = 5;
        while (i-- > 0) {
            try {
                producer.produce(i);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void consumeBatch(Consumer consumer) {
        while (true) {
            try {
                consumer.consume();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
