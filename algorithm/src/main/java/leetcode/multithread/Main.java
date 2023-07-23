package leetcode.multithread;

import java.util.function.IntConsumer;

/**
 * @author puppylpg on 2023/07/23
 */
public class Main {

    private static IntConsumer c = x -> System.out.println(Thread.currentThread() + " " + x);
    public static void main(String... args) throws InterruptedException {
        ZeroEvenOdd zeroEvenOdd = new ZeroEvenOdd(7);

        Thread t1 = new Thread(() -> {
            try {
                zeroEvenOdd.odd(c);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }, "t_odd");
        Thread t2 = new Thread(() -> {
            try {
                zeroEvenOdd.even(c);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }, "t_even");
        Thread t3 = new Thread(() -> {
            try {
                zeroEvenOdd.zero(c);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }, "t_zero");

        t1.start();t2.start();t3.start();
        t1.join();t2.join();t3.join();
    }
}
