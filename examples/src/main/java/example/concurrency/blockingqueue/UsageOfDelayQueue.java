package example.concurrency.blockingqueue;

import java.util.Random;
import java.util.concurrent.*;

/**
 * 由于{@link Delayed}元素是需要实现{@link Comparable}接口的，所以{@link DelayQueue}内部用的是{@link java.util.PriorityQueue}来给元素排序。
 * <p>
 * 当取数据的时候，就是从pq里取值，如果第一个元素的{@link Delayed#getDelay(TimeUnit)}到期，就返回元素；否则就await{@link Delayed#getDelay(TimeUnit)}
 * 这么久，然后再尝试获取第一个元素。所以{@link Delayed#getDelay(TimeUnit)}如果返回定值，是错误的，这样会永远差这么久时间，永远取不到该元素。
 * <p>
 * 还有就是{@link Delayed}的{@link Delayed#compareTo(Object)}必须和{@link Delayed#getDelay(TimeUnit)}是一致的语义，如果下面的{@link Student}接口compareTo
 * 和getDelayed语义相反（`return (int) -(homeworkDuration - other.homeworkDuration);`），就会导致delay最长的排在pq队首，输出如下：
 * <p>
 * Student0: 4470ms
 * Student1: 4257ms
 * Student2: 2667ms
 * Student3: 3386ms
 * Student4: 3612ms
 * Student5: 4625ms
 * Student6: 2717ms
 * Student7: 4080ms
 * Student8: 3896ms
 * Student9: 3613ms
 * Student5(4625ms) finished, time cost=4635ms
 * Student0(4470ms) finished, time cost=4641ms
 * Student1(4257ms) finished, time cost=4635ms
 * Student7(4080ms) finished, time cost=4635ms
 * Student6(2717ms) finished, time cost=4636ms
 * Student3(3386ms) finished, time cost=4636ms
 * Student8(3896ms) finished, time cost=4636ms
 * Student9(3613ms) finished, time cost=4636ms
 * Student4(3612ms) finished, time cost=4636ms
 * Student2(2667ms) finished, time cost=4636ms
 * Total time used: 4659ms.
 *
 * @author puppylpg on 2017/11/28
 */
public class UsageOfDelayQueue {
    private static final int STUDENT_NUM = 10;

    /**
     * Student0: 4617ms
     * Student1: 4761ms
     * Student2: 3564ms
     * Student3: 2636ms
     * Student4: 2386ms
     * Student5: 3156ms
     * Student6: 3408ms
     * Student7: 4363ms
     * Student8: 4187ms
     * Student9: 2311ms
     * Student9(2311ms) finished, time cost=2325ms
     * Student4(2386ms) finished, time cost=2387ms
     * Student3(2636ms) finished, time cost=2637ms
     * Student5(3156ms) finished, time cost=3163ms
     * Student6(3408ms) finished, time cost=3410ms
     * Student2(3564ms) finished, time cost=3565ms
     * Student8(4187ms) finished, time cost=4191ms
     * Student7(4363ms) finished, time cost=4377ms
     * Student0(4617ms) finished, time cost=4632ms
     * Student1(4761ms) finished, time cost=4766ms
     * Total time used: 4779ms.
     */
    public static void main(String[] args) throws InterruptedException {
        long start = System.currentTimeMillis();

        Random r = new Random();
        DelayQueue<Student> students = new DelayQueue<>();
        // to run Student. Thread pool is useful only if the task needs long time to execute.
        ExecutorService service = Executors.newFixedThreadPool(STUDENT_NUM);
//        ExecutorService service = Executors.newSingleThreadExecutor();

        for (int i = 0; i < STUDENT_NUM; i++) {
            long costTime = 2000 + r.nextInt(3000);
            students.put(new Student("Student" + i, costTime));
            System.out.println("Student" + i + ": " + costTime + "ms");
        }

        // start take object from delayQueue
        // in order of `getDelay()`
        while (!students.isEmpty()) {
            // take a Student and execute since it's Runnable
            service.execute(students.take());
        }

        service.shutdown();

        // total time count
        service.awaitTermination(1, TimeUnit.HOURS);
        long stop = System.currentTimeMillis();
        System.out.println("Total time used: " + (stop - start) + "ms.");
    }

    /**
     * DelayQueue的元素必须实现{@link Delayed}接口（同时也实现了{@link Comparable}）接口
     * {@link Delayed#getDelay(TimeUnit)}决定了元素是否可以被取出
     * {@link Comparable#compareTo(Object)}决定了元素在队列中的排列顺序，当很多元素都可以被取出的时候，就按这个顺序进行
     */
    private static class Student implements Runnable, Delayed {
        private final String name;
        // duration to do homework, assigned by Random
        private final long homeworkDuration;
        // time the student can play
        private final long timeToPlay;
        private final long createTime;

        Student(String name, long homeworkDuration) {
            this.name = name;
            this.homeworkDuration = homeworkDuration;
            this.timeToPlay = homeworkDuration + System.currentTimeMillis();
            this.createTime = System.currentTimeMillis();
        }

        @Override
        public void run() {
            System.out.printf("%s(%dms) finished, time cost=%dms%n", name, homeworkDuration, System.currentTimeMillis() - createTime);
        }

        /**
         * to judge the time after which object can be take out of the delay queue
         * 这个值必须是动态的，如果永远返回定值，那每次调用这个方法看看还差多久的时候，
         * 就会发现就是永远都差这么长时间，所以永远都等不到……
         *
         * @param unit
         * @return
         */
        @Override
        public long getDelay(TimeUnit unit) {
            return (timeToPlay - System.currentTimeMillis());
        }

        /**
         * to sort object in the delay queue
         * @param o
         * @return
         */
        @Override
        public int compareTo(Delayed o) {
            Student other = (Student) o;
            return (int) (homeworkDuration - other.homeworkDuration);
            // IMPORTANT: show the meaning of compareTo()
//            return name.compareTo(other.name);
        }

    }
}