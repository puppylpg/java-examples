package example.nio;

import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;

/**
 * 如果您获得一个共享锁，那么其他人可以获得同一个文件或者文件一部分上的共享锁，但是不能获得排它锁
 * 如果您获取一个排它锁，那么其他人就不能获得同一个文件或者文件的一部分上的锁（共享锁、排它锁都不行）
 *
 * 大多数操作系统提供了文件系统锁，但是它们并不都是采用同样的方式。有些实现提供了共享锁，而另一些仅提供了排它锁。
 *
 * 如果要获取一个排它锁，您必须以写方式打开文件。
 *
 * 为了保证代码可移植，尽量只是用共享锁。（不移植就无所谓了。）
 */
public class FileLocks {
    static private final int start = 10;
    static private final int end = 20;

    static public void main(String args[]) throws Exception {
        // Get file channel
        RandomAccessFile raf = new RandomAccessFile("usefilelocks.txt", "rw");
        FileChannel fc = raf.getChannel();

        // Get lock
        System.out.println("trying to get lock");
        FileLock lock = fc.lock(start, end, false);
        System.out.println("got lock!");

        // Pause
        System.out.println("pausing");
        try {
            Thread.sleep(3000);
        } catch (InterruptedException ie) {
        }

        // Release lock
        System.out.println("going to release lock");
        lock.release();
        System.out.println("released lock");

        raf.close();
    }
}
