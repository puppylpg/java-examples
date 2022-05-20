package example.concurrency.synchronization.latch.race;

import java.util.concurrent.CountDownLatch;

public class Referee {

    private final CountDownLatch startSignal, finishSignal, playerReadySignal;

    private final Board board;

    Referee(CountDownLatch startSignal, CountDownLatch finishSignal, CountDownLatch playerReadySignal, Board board) {
        this.startSignal = startSignal;
        this.finishSignal = finishSignal;
        this.playerReadySignal = playerReadySignal;
        this.board = board;
    }

    public void announceStart() throws InterruptedException {
        System.out.println("Referee: waiting players to be ready.");
        // 等所有运动员ready
        playerReadySignal.await();
        System.out.println("Everyone is ready ~ Game start!!!");
        // 裁判放行
        startSignal.countDown();
    }

    public void waitAllPlayerFinish() throws InterruptedException {
        // 等待所有运动员结束
        finishSignal.await();
    }

    public void announceFinish() {
        System.out.println("Today's competition has FINISHED! The winner is " + board.getWinnerNumber());
    }
}
