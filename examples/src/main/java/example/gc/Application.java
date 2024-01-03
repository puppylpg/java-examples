package example.gc;

import java.util.HashMap;
import java.util.Map;

/**
 * -XX:+UseSerialGC -Xms1024m -Xmx1024m -verbose:gc -XX:+PrintGCDetails -XX:+PrintGCDateStamps -Xloggc:logs/gc.log.%t
 *
 * Output GC detail:
 * - `-verbose:gc`: activates the logging of garbage collection information in its simplest form. By default, the GC log is written to stdout and should output a line for every young generation GC and every full GC.
 * - `-XX:+PrintGCDetails`: more detail, young & old generation size; time used; heap summary etc;
 * - `-XX:+PrintGCDateStamps`: date & time, `-XX:+PrintGCTimeStamps` can be omit;
 * - @Deprecated `-XX:+PrintGCTimeStamps`
 *
 * Output to files:
 * - @Deprecated `-XX:+UseGCLogFileRotation`: log rolling;
 * - @Deprecated `-XX:NumberOfGCLogFiles`: 3 log file;
 * - @Deprecated `-XX:GCLogFileSize=200M`: single log size（好像必须大于等于8K，否则大概就是8k）;
 * - @Deprecated `-Xloggc:logs/gc.log`: output file path;
 * - `-Xloggc:logs/gc.log.%t`: logs/gc.log.YYYY-MM-DD_HH-MM-SS
 *
 * Rolling log的弊端：
 * 行为：假设上述参数，则每个log200M，写满0,1,2之后又开始回写0，原来的0被覆盖了。即使文件名带时间戳，3个文件用的是同一个文件名仅仅后缀1,2,3不同。所以还是会覆盖。
 * 1. 写一圈之后覆盖开始的gc log，log丢了；
 * 2. jdk重启会覆盖同名gc log……；
 * 3. 太碎了，相分析日志得dump多个日志文件；
 *
 * 不如：使用单个带时间戳的log，别rotation了。
 *
 * Start of program!
 * 2019-11-13T16:50:43.329+0800: 0.470: [GC (Allocation Failure) 2019-11-13T16:50:43.329+0800: 0.470: [DefNew: 279616K->34944K(314560K), 0.3509021 secs] 279616K->223100K(1013632K), 0.3509346 secs] [Times: user=0.30 sys=0.05, real=0.35 secs]
 * MAP size: 3000000
 * 2019-11-13T16:50:43.812+0800: 0.954: [Full GC (System.gc()) 2019-11-13T16:50:43.812+0800: 0.954: [Tenured: 188156K->368853K(699072K), 0.5242930 secs] 398466K->368853K(1013632K), [Metaspace: 3171K->3171K(1056768K)], 0.5243403 secs] [Times: user=0.48 sys=0.03, real=0.53 secs]
 * MAP size: 1000000
 * End of program!
 * Heap
 *  def new generation   total 314560K, used 215669K [0x00000000c0000000, 0x00000000d5550000, 0x00000000d5550000)
 *   eden space 279616K,  77% used [0x00000000c0000000, 0x00000000cd29d488, 0x00000000d1110000)
 *   from space 34944K,   0% used [0x00000000d3330000, 0x00000000d3330000, 0x00000000d5550000)
 *   to   space 34944K,   0% used [0x00000000d1110000, 0x00000000d1110000, 0x00000000d3330000)
 *  tenured generation   total 699072K, used 368853K [0x00000000d5550000, 0x0000000100000000, 0x0000000100000000)
 *    the space 699072K,  52% used [0x00000000d5550000, 0x00000000ebd85528, 0x00000000ebd85600, 0x0000000100000000)
 *  Metaspace       used 3186K, capacity 4500K, committed 4864K, reserved 1056768K
 *   class space    used 333K, capacity 388K, committed 512K, reserved 1048576K
 *
 * @author puppylpg on 2019/11/13
 */
public class Application {

    private static Map<String, String> stringContainer = new HashMap<>();

    public static void main(String[] args) throws InterruptedException {
        while (true) {
            System.out.println("Start of program!");
            String stringWithPrefix = "stringWithPrefix";

            // Load Java Heap with 3 M java.lang.String instances
            for (int i = 0; i < 3000000; i++) {
                String newString = stringWithPrefix + i;
                stringContainer.put(newString, newString);
            }
            System.out.println("MAP size: " + stringContainer.size());

            // Explicit GC!
            System.gc();

            // Remove 2 M out of 3 M
            for (int i = 0; i < 2000000; i++) {
                String newString = stringWithPrefix + i;
                stringContainer.remove(newString);
            }

            System.out.println("MAP size: " + stringContainer.size());
            System.out.println("End of program!");

            Thread.sleep(1000);
        }
    }
}
