package example.exception;

/**
 * 每一个e由三部分组成：
 * - message；
 * - stacktrace：jdk自然知道当前调用栈是哪儿，所以会把从main函数一直到new exception的地方的调用栈都放到stacktrace里。
 *  既然是栈，所以先记录最里层的stack，最后记main所在的stack；
 * - e：如果new exception的时候不传e，则e=this。也就是说默认exception的e是自己，是个自引用。如果传了，就指向传进来的e。
 *
 * 一般我们在new外层的exception（比如文中的HighLevelException）的时候，会把内层产生的e传入这个exception。所以e一般是这样的：
 * 1. 最外层的e（eg：HighLevelException），它的栈帧就是从main到new它的函数，倒序存放；它的e就是内层的exception；
 *  1.1 内层的e（eg：MidLevelException），它的栈帧就是从main到new它的函数，所以会比外层的e多几层；它的e就是最里层的exception；
 *   1.2 最内层的e（eg：LowLevelException），它的栈帧就是从main到new它的函数，所以它的栈帧最多；它的e就是它自己，没啥意义，感觉jdk设成null或者EMPTY其实就行了……
 *
 * 所以print stacktrace的时候，print的是最外层的e，而它会先打它的message，再打它的stacktrace，最后打它的e；
 * 于是递归地把内层的e也按照message、stack、内层e的顺序打出来；
 * 直到达到最里面的e。
 * 所以打印异常的姿势是：
 * 1，最先打印的是最外层的异常，最后打印的是最里层的；
 * 2. 每个e的stacktrace都是从main到这个异常new的地方；
 * 当然打印最里面的e的时候，可能由于stacktrace太长且和外面重复，会省略最外面的stack，因为之前的e已经打过这些stack了。
 * 所以我们看log里的异常的时候，直接拉到最下面看e就行了，它就是罪魁祸首。
 *
 * 最离谱的是：exception getCause的时候，return (cause==this ? null : cause)……如果e=this，返回null……说明没e了……那为啥不一开始就设为null或者EMPTY……
 *
 * @author liuhaibo on 2019/07/29
 */
public class Junk {

    /**
     * example.exception.HighLevelException: Why me...
     * 	at example.exception.Junk.a(Junk.java:36)
     * 	at example.exception.Junk.main(Junk.java:10)
     * Caused by: example.exception.MidLevelException: Someone not me is to blame, and sorry to hurt anyone else...
     * 	at example.exception.Junk.c(Junk.java:48)
     * 	at example.exception.Junk.b(Junk.java:41)
     * 	at example.exception.Junk.a(Junk.java:34)
     * 	... 1 more
     * Caused by: example.exception.LowLevelException: I am the one who is to blame...
     * 	at example.exception.Junk.e(Junk.java:57)
     * 	at example.exception.Junk.d(Junk.java:53)
     * 	at example.exception.Junk.c(Junk.java:46)
     * 	... 3 more
     * ======= cause.toString =======
     * example.exception.MidLevelException: Someone not me is to blame, and sorry to hurt anyone else...
     * ======= cause.message =======
     * Someone not me is to blame, and sorry to hurt anyone else...
     * ======= cause stack =======
     * example.exception.MidLevelException: Someone not me is to blame, and sorry to hurt anyone else...
     * 	at example.exception.Junk.c(Junk.java:48)
     * 	at example.exception.Junk.b(Junk.java:41)
     * 	at example.exception.Junk.a(Junk.java:34)
     * 	at example.exception.Junk.main(Junk.java:10)
     * Caused by: example.exception.LowLevelException: I am the one who is to blame...
     * 	at example.exception.Junk.e(Junk.java:57)
     * 	at example.exception.Junk.d(Junk.java:53)
     * 	at example.exception.Junk.c(Junk.java:46)
     * 	... 3 more
     * ======= cause'cause =======
     * example.exception.LowLevelException: I am the one who is to blame...
     * 	at example.exception.Junk.e(Junk.java:57)
     * 	at example.exception.Junk.d(Junk.java:53)
     * 	at example.exception.Junk.c(Junk.java:46)
     * 	at example.exception.Junk.b(Junk.java:41)
     * 	at example.exception.Junk.a(Junk.java:34)
     * 	at example.exception.Junk.main(Junk.java:10)
     */
    public static void main(String args[]) {
        try {
            a();
        } catch (HighLevelException e) {
            e.printStackTrace();

            System.err.println("======= cause.toString =======");
            // 仅仅是“类名+message”，没啥卵用，不如直接e.printStackTrace
            System.err.println(e.getCause());
            System.err.println("======= cause.message =======");
            System.err.println(e.getCause().getMessage());
            System.err.println("======= cause stack =======");
            e.getCause().printStackTrace();
            System.err.println("======= cause'cause =======");
            e.getCause().getCause().printStackTrace();
            // NPE. 如果最后的cause是自己，调用getCause直接返回null……
//            e.getCause().getCause().getCause().printStackTrace();

//            if (e.getCause() instanceof MidLevelException) {
//                System.out.println("niubility");
//            }
        }
    }

    static void a() throws HighLevelException {
        try {
            b();
        } catch (MidLevelException e) {
            throw new HighLevelException("Why me...", e);
        }
    }

    static void b() throws MidLevelException {
        c();
    }

    static void c() throws MidLevelException {
        try {
            d();
        } catch (LowLevelException e) {
            throw new MidLevelException("Someone not me is to blame, and sorry to hurt anyone else...", e);
        }
    }

    static void d() throws LowLevelException {
        e();
    }

    static void e() throws LowLevelException {
        throw new LowLevelException("I am the one who is to blame...");
    }
}

class HighLevelException extends Exception {
    HighLevelException(String message, Throwable cause) {
        super(message, cause);
    }
}

class MidLevelException extends Exception {
    MidLevelException(String message, Throwable cause) {
        super(message, cause);
    }
}

class LowLevelException extends Exception {
    LowLevelException(String message) {
        super(message);
    }
}
