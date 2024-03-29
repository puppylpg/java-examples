package example.concurrency.synchronization.latch.race;

import java.util.concurrent.CountDownLatch;

public class Player implements Runnable {
    private final CountDownLatch startSignal, finishSignal, playerReadySignal;
    private final int num;

    private final Board board;

    Player(CountDownLatch startSignal, CountDownLatch finishSignal, CountDownLatch playerReadySignal, int num, Board board) {
        this.startSignal = startSignal;
        this.finishSignal = finishSignal;
        this.playerReadySignal = playerReadySignal;
        this.num = num;
        this.board = board;
    }

    @Override
    public void run() {
        warmUp();
        readyAndWait();
        // 发令枪响后
        compete();
    }

    private void warmUp() {
        System.out.println("Player " + num + " is warming up.");
    }

    /**
     * 自己准备好，并等待所有人都准备好
     */
    private void readyAndWait() {
        // 我准备好了
        playerReadySignal.countDown();
        System.out.printf("Player %d is ready.%n", num);
        try {
            // 等裁判信号
            startSignal.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void compete() {
        System.out.println("Player " + num + " finished running.");
        // 我完成了
        finishSignal.countDown();
        board.setWinnerNumber(num);
    }
}
