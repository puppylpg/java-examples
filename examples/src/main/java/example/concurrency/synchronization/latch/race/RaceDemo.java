package example.concurrency.synchronization.latch.race;

import java.util.concurrent.CountDownLatch;

/**
 * Latch: 等待启动一组相关操作，或者等待关闭一组相关操作（一波人卡另一波人，“互相掣肘”）
 *
 * Referee等所有运动员就绪；
 * Player等裁判开闸；
 * Referee等所有人结束，然后宣布结束。
 *
 * 所以准备了三个CountDownLatch。
 *
 * @author puppylpg on 2018/06/08
 */
public class RaceDemo {

    private final static int PLAYER_NUM = 5;
    private static final CountDownLatch PLAYER_READY = new CountDownLatch(PLAYER_NUM);
    private static final CountDownLatch START_GATE = new CountDownLatch(1);
    private static final CountDownLatch CLOSE_GATE = new CountDownLatch(PLAYER_NUM);

    private static final Board BOARD = new Board();

    /**
     * Player 4 finished warming up.
     * Player 0 finished warming up.
     * Player 0 is ready.
     * Player 4 is ready.
     * Player 2 finished warming up.
     * Player 2 is ready.
     * Player 1 finished warming up.
     * Player 1 is ready.
     * Player 3 finished warming up.
     * Player 3 is ready.
     * Everyone is ready ~ Game start!!!
     * Player 1 finished running.
     * Player 2 finished running.
     * Player 0 finished running.
     * Player 3 finished running.
     * Player 4 finished running.
     * Today's competition has FINISHED! The winner is 2
     */
    public static void main(String... args) throws InterruptedException {

        for(int i = 0; i < PLAYER_NUM; i++) {
            new Thread(new Player(START_GATE, CLOSE_GATE, PLAYER_READY, i, BOARD)).start();
        }

        Referee referee = new Referee(START_GATE, CLOSE_GATE, PLAYER_READY, BOARD);

        referee.announceStart();
        referee.waitAllPlayerFinish();
        referee.announceFinish();
    }
}
