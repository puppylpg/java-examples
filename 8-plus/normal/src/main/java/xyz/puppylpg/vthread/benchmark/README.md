C:\Users\puppylpg\.jdks\openjdk-21\bin\java.exe "-javaagent:C:\Users\puppylpg\AppData\Local\Programs\IntelliJ IDEA Ultimate\lib\idea_rt.jar=2906:C:\Users\puppylpg\AppData\Local\Programs\IntelliJ IDEA Ultimate\bin" -Dfile.encoding=UTF-8 -Dsun.stdout.encoding=UTF-8 -Dsun.stderr.encoding=UTF-8 -classpath C:\Users\puppylpg\Documents\GitHub\java-examples\8-plus\normal\target\classes;C:\Users\puppylpg\.m2\repository\org\apache\tomcat\embed\tomcat-embed-core\11.0.0-M7\tomcat-embed-core-11.0.0-M7.jar;C:\Users\puppylpg\.m2\repository\org\apache\tomcat\tomcat-annotations-api\11.0.0-M7\tomcat-annotations-api-11.0.0-M7.jar;C:\Users\puppylpg\.m2\repository\org\openjdk\jmh\jmh-core\1.37\jmh-core-1.37.jar;C:\Users\puppylpg\.m2\repository\net\sf\jopt-simple\jopt-simple\5.0.4\jopt-simple-5.0.4.jar;C:\Users\puppylpg\.m2\repository\org\apache\commons\commons-math3\3.6.1\commons-math3-3.6.1.jar xyz.puppylpg.vthread.benchmark.ThreadsBenchmark
# JMH version: 1.37
# VM version: JDK 21, OpenJDK 64-Bit Server VM, 21+35-2513
# VM invoker: C:\Users\puppylpg\.jdks\openjdk-21\bin\java.exe
# VM options: -Xms512m -Xmx1024m
# Blackhole mode: compiler (auto-detected, use -Djmh.blackhole.autoDetect=false to disable)
# Warmup: 5 iterations, 2 s each
# Measurement: 5 iterations, 10 s each
# Timeout: 60 s per iteration
# Threads: 2 threads, will synchronize iterations
# Benchmark mode: Throughput, ops/time
# Benchmark: xyz.puppylpg.vthread.benchmark.ThreadsBenchmark.platformThreadPerTask

# Run progress: 0.00% complete, ETA 00:06:00
# Fork: 1 of 1
# Warmup Iteration   1: 0.336 ops/s
# Warmup Iteration   2: 0.846 ops/s
# Warmup Iteration   3: 0.841 ops/s
# Warmup Iteration   4: 0.851 ops/s
# Warmup Iteration   5: 0.851 ops/s
Iteration   1: 0.856 ops/s
Iteration   2: 0.871 ops/s
Iteration   3: 0.861 ops/s
Iteration   4: 0.856 ops/s
Iteration   5: 0.841 ops/s


Result "xyz.puppylpg.vthread.benchmark.ThreadsBenchmark.platformThreadPerTask":
0.857 ±(99.9%) 0.042 ops/s [Average]
(min, avg, max) = (0.841, 0.857, 0.871), stdev = 0.011
CI (99.9%): [0.815, 0.899] (assumes normal distribution)


# JMH version: 1.37
# VM version: JDK 21, OpenJDK 64-Bit Server VM, 21+35-2513
# VM invoker: C:\Users\puppylpg\.jdks\openjdk-21\bin\java.exe
# VM options: -Xms512m -Xmx1024m
# Blackhole mode: compiler (auto-detected, use -Djmh.blackhole.autoDetect=false to disable)
# Warmup: 5 iterations, 2 s each
# Measurement: 5 iterations, 10 s each
# Timeout: 60 s per iteration
# Threads: 2 threads, will synchronize iterations
# Benchmark mode: Throughput, ops/time
# Benchmark: xyz.puppylpg.vthread.benchmark.ThreadsBenchmark.platformThreadPool

# Run progress: 16.67% complete, ETA 00:07:51
# Fork: 1 of 1
# Warmup Iteration   1: 1.494 ops/s
# Warmup Iteration   2: 1.330 ops/s
# Warmup Iteration   3: 1.301 ops/s
# Warmup Iteration   4: 1.341 ops/s
# Warmup Iteration   5: 1.181 ops/s
Iteration   1: 1.430 ops/s
Iteration   2: 1.510 ops/s
Iteration   3: 1.421 ops/s
Iteration   4: 1.513 ops/s
Iteration   5: 1.599 ops/s


Result "xyz.puppylpg.vthread.benchmark.ThreadsBenchmark.platformThreadPool":
1.494 ±(99.9%) 0.279 ops/s [Average]
(min, avg, max) = (1.421, 1.494, 1.599), stdev = 0.072
CI (99.9%): [1.215, 1.774] (assumes normal distribution)


# JMH version: 1.37
# VM version: JDK 21, OpenJDK 64-Bit Server VM, 21+35-2513
# VM invoker: C:\Users\puppylpg\.jdks\openjdk-21\bin\java.exe
# VM options: -Xms512m -Xmx1024m
# Blackhole mode: compiler (auto-detected, use -Djmh.blackhole.autoDetect=false to disable)
# Warmup: 5 iterations, 2 s each
# Measurement: 5 iterations, 10 s each
# Timeout: 60 s per iteration
# Threads: 2 threads, will synchronize iterations
# Benchmark mode: Throughput, ops/time
# Benchmark: xyz.puppylpg.vthread.benchmark.ThreadsBenchmark.virtualThreadPerTask

# Run progress: 33.33% complete, ETA 00:05:45
# Fork: 1 of 1
# Warmup Iteration   1: 5.175 ops/s
# Warmup Iteration   2: 8.754 ops/s
# Warmup Iteration   3: 8.863 ops/s
# Warmup Iteration   4: 9.138 ops/s
# Warmup Iteration   5: 8.983 ops/s
Iteration   1: 8.981 ops/s
Iteration   2: 9.124 ops/s
Iteration   3: 9.078 ops/s
Iteration   4: 9.050 ops/s
Iteration   5: 8.954 ops/s


Result "xyz.puppylpg.vthread.benchmark.ThreadsBenchmark.virtualThreadPerTask":
9.038 ±(99.9%) 0.268 ops/s [Average]
(min, avg, max) = (8.954, 9.038, 9.124), stdev = 0.070
CI (99.9%): [8.770, 9.305] (assumes normal distribution)


# JMH version: 1.37
# VM version: JDK 21, OpenJDK 64-Bit Server VM, 21+35-2513
# VM invoker: C:\Users\puppylpg\.jdks\openjdk-21\bin\java.exe
# VM options: -Xms512m -Xmx1024m
# Blackhole mode: compiler (auto-detected, use -Djmh.blackhole.autoDetect=false to disable)
# Warmup: 5 iterations, 2 s each
# Measurement: 5 iterations, 10 s each
# Timeout: 60 s per iteration
# Threads: 2 threads, will synchronize iterations
# Benchmark mode: Average time, time/op
# Benchmark: xyz.puppylpg.vthread.benchmark.ThreadsBenchmark.platformThreadPerTask

# Run progress: 50.00% complete, ETA 00:03:56
# Fork: 1 of 1
# Warmup Iteration   1: 2.577 s/op
# Warmup Iteration   2: 2.357 s/op
# Warmup Iteration   3: 2.371 s/op
# Warmup Iteration   4: 2.357 s/op
# Warmup Iteration   5: 2.382 s/op
Iteration   1: 2.313 s/op
Iteration   2: 2.317 s/op
Iteration   3: 2.356 s/op
Iteration   4: 2.301 s/op
Iteration   5: 2.323 s/op


Result "xyz.puppylpg.vthread.benchmark.ThreadsBenchmark.platformThreadPerTask":
2.322 ±(99.9%) 0.081 s/op [Average]
(min, avg, max) = (2.301, 2.322, 2.356), stdev = 0.021
CI (99.9%): [2.241, 2.402] (assumes normal distribution)


# JMH version: 1.37
# VM version: JDK 21, OpenJDK 64-Bit Server VM, 21+35-2513
# VM invoker: C:\Users\puppylpg\.jdks\openjdk-21\bin\java.exe
# VM options: -Xms512m -Xmx1024m
# Blackhole mode: compiler (auto-detected, use -Djmh.blackhole.autoDetect=false to disable)
# Warmup: 5 iterations, 2 s each
# Measurement: 5 iterations, 10 s each
# Timeout: 60 s per iteration
# Threads: 2 threads, will synchronize iterations
# Benchmark mode: Average time, time/op
# Benchmark: xyz.puppylpg.vthread.benchmark.ThreadsBenchmark.platformThreadPool

# Run progress: 66.67% complete, ETA 00:02:42
# Fork: 1 of 1
# Warmup Iteration   1: 1.378 s/op
# Warmup Iteration   2: 1.417 s/op
# Warmup Iteration   3: 1.357 s/op
# Warmup Iteration   4: 1.402 s/op
# Warmup Iteration   5: 1.372 s/op
Iteration   1: 1.300 s/op
Iteration   2: 1.509 s/op
Iteration   3: 1.540 s/op
Iteration   4: 1.424 s/op
Iteration   5: 1.360 s/op


Result "xyz.puppylpg.vthread.benchmark.ThreadsBenchmark.platformThreadPool":
1.426 ±(99.9%) 0.386 s/op [Average]
(min, avg, max) = (1.300, 1.426, 1.540), stdev = 0.100
CI (99.9%): [1.040, 1.813] (assumes normal distribution)


# JMH version: 1.37
# VM version: JDK 21, OpenJDK 64-Bit Server VM, 21+35-2513
# VM invoker: C:\Users\puppylpg\.jdks\openjdk-21\bin\java.exe
# VM options: -Xms512m -Xmx1024m
# Blackhole mode: compiler (auto-detected, use -Djmh.blackhole.autoDetect=false to disable)
# Warmup: 5 iterations, 2 s each
# Measurement: 5 iterations, 10 s each
# Timeout: 60 s per iteration
# Threads: 2 threads, will synchronize iterations
# Benchmark mode: Average time, time/op
# Benchmark: xyz.puppylpg.vthread.benchmark.ThreadsBenchmark.virtualThreadPerTask

# Run progress: 83.33% complete, ETA 00:01:20
# Fork: 1 of 1
# Warmup Iteration   1: 0.382 s/op
# Warmup Iteration   2: 0.223 s/op
# Warmup Iteration   3: 0.220 s/op
# Warmup Iteration   4: 0.220 s/op
# Warmup Iteration   5: 0.218 s/op
Iteration   1: 0.219 s/op
Iteration   2: 0.219 s/op
Iteration   3: 0.222 s/op
Iteration   4: 0.219 s/op
Iteration   5: 0.219 s/op


Result "xyz.puppylpg.vthread.benchmark.ThreadsBenchmark.virtualThreadPerTask":
0.220 ±(99.9%) 0.005 s/op [Average]
(min, avg, max) = (0.219, 0.220, 0.222), stdev = 0.001
CI (99.9%): [0.215, 0.224] (assumes normal distribution)


# Run complete. Total time: 00:07:46

REMEMBER: The numbers below are just data. To gain reusable insights, you need to follow up on
why the numbers are the way they are. Use profilers (see -prof, -lprof), design factorial
experiments, perform baseline and negative tests that provide experimental control, make sure
the benchmarking environment is safe on JVM/OS/HW level, ask for reviews from the domain experts.
Do not assume the numbers tell you what you want them to tell.

NOTE: Current JVM experimentally supports Compiler Blackholes, and they are in use. Please exercise
extra caution when trusting the results, look into the generated code to check the benchmark still
works, and factor in a small probability of new VM bugs. Additionally, while comparisons between
different JVMs are already problematic, the performance difference caused by different Blackhole
modes can be very significant. Please make sure you use the consistent Blackhole mode for comparisons.

Benchmark                                Mode  Cnt  Score   Error  Units
ThreadsBenchmark.platformThreadPerTask  thrpt    5  0.857 ± 0.042  ops/s
ThreadsBenchmark.platformThreadPool     thrpt    5  1.494 ± 0.279  ops/s
ThreadsBenchmark.virtualThreadPerTask   thrpt    5  9.038 ± 0.268  ops/s
ThreadsBenchmark.platformThreadPerTask   avgt    5  2.322 ± 0.081   s/op
ThreadsBenchmark.platformThreadPool      avgt    5  1.426 ± 0.386   s/op
ThreadsBenchmark.virtualThreadPerTask    avgt    5  0.220 ± 0.005   s/op

Process finished with exit code 0
